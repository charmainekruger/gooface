package com.github.chamainekruger.gooface.core;

import com.github.chamainekruger.gooface.common.CampaignLeadEvent;
import com.github.chamainekruger.gooface.common.Campaign;
import com.github.chamainekruger.gooface.common.Lead;
import com.github.chamainekruger.gooface.core.event.LeadsEventQueue;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.types.webhook.PageLeadgen;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

/**
 * Receive real-time leads from Facebook
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@WebServlet(name = "FacebookLeadsWebhook", value = "/webhook")
@Log
public class FacebookLeadsWebhook extends HttpServlet {

    private FacebookFetcher facebookFetcher = null;
    private final LeadsEventQueue leadsEventQueue = new LeadsEventQueue();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.verifyToken = System.getProperty(VERIFY_TOKEN_KEY);
        this.accessToken = System.getProperty(ACCESS_TOKEN_KEY);
        this.facebookFetcher = new FacebookFetcher(accessToken);
        this.listeners = System.getProperty(LISTENERS).split(COMMA);
        
        
        String d = System.getProperty(DUPLICATE_TIMEOUT);
        if(d!=null && !d.isEmpty()){
            d = d.trim();
            int i = Integer.valueOf(d);
            if(i>0)this.duplicateTimeoutSeconds=i;
        }
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter(HUB_VERIFY_TOKEN);
        if (token != null && !token.isEmpty() && token.equals(this.verifyToken)) {
            String challenge = request.getParameter(HUB_CHALLENGE);
            response.getWriter().write(challenge);
            response.getWriter().flush();
        }
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String body = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        log.log(Level.INFO, "Receiving lead from Facebook [{0}]", body);

        JsonMapper mapper = new DefaultJsonMapper();
        WebhookObject webhookObject = mapper.toJavaObject(body, WebhookObject.class);
        log.log(Level.INFO, "webhookObject [{0}]", webhookObject);
        
        List<Webhook> webhooks = toWebhookList(webhookObject);
        
        for(Webhook webhook : webhooks){
            String ref = UUID.randomUUID().toString();
            try {
                Campaign campaign = facebookFetcher.getCampaign(webhook.getFormId());
                Lead lead = facebookFetcher.getLead(webhook.getLeadId());
                CampaignLeadEvent campaignLeadEvent = new CampaignLeadEvent(ref,campaign,lead);
                
                // Check for duplicates (if needed)
                if(this.duplicateTimeoutSeconds>0){
                    
                    int hash = campaignLeadEvent.hashCode();
                    MemcacheService duplicateCache = MemcacheServiceFactory.getMemcacheService("duplicateCache");
                    duplicateCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
                    
                    if(duplicateCache.contains(hash)){
                        log.log(Level.WARNING, "Duplicate lead - not puplishing message \n[{0}]", duplicateCache.get(hash));
                    }else{
                        duplicateCache.put(hash, campaignLeadEvent,Expiration.byDeltaSeconds(duplicateTimeoutSeconds));
                        leadsEventQueue.publishMessages(campaignLeadEvent,listeners);
                    }
                    
                }else{
                    leadsEventQueue.publishMessages(campaignLeadEvent,listeners);
                }
            } catch (FacebookFetcherException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private List<Webhook> toWebhookList(WebhookObject webhookObject){
        List<Webhook> webhooks = new ArrayList<>();
        List<WebhookEntry> entryList = webhookObject.getEntryList();
        entryList.stream().map((entry) -> entry.getChanges()).forEachOrdered((changes) -> {
            changes.stream().map((change) -> change.getValue()).map((value) -> (PageLeadgen) value).forEachOrdered((pageLeadgen) -> {
                Webhook webhook = new Webhook(Long.valueOf(pageLeadgen.getLeadgenId()), Long.valueOf(pageLeadgen.getFormId()));
                webhooks.add(webhook);
            });
        });
        return webhooks;
    }
     
    private static final String HUB_CHALLENGE = "hub.challenge";
    private static final String HUB_VERIFY_TOKEN = "hub.verify_token";
    private static final String VERIFY_TOKEN_KEY = "verify.token";
    private static final String ACCESS_TOKEN_KEY = "access.token";
    private static final String DUPLICATE_TIMEOUT = "duplicateCheckTimeout";
    private static final String LISTENERS = "listeners";
    private static final String COMMA = ",";
    
    // To be configured
    private String verifyToken = null;
    private String accessToken = null;
    private String[] listeners = null;
    private int duplicateTimeoutSeconds = 0;
}
