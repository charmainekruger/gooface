package com.github.chamainekruger.gooface.core;

import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.types.webhook.WebhookObject;
import java.io.IOException;
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

    private final String HUB_CHALLENGE = "hub.challenge";
    private final String HUB_VERIFY_TOKEN = "hub.verify_token";
    private final String VERIFY_TOKEN = "45be8cb2-8daf-11e7-bb31-be2e44b06b34";
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        log.severe(">>>>>>>>>>>>>>>>>>>>>>> GOOFACE <<<<<<<<<<<<<<<<<<<");
        log.log(Level.SEVERE, "foo = {0}", System.getenv("foo"));
        log.log(Level.SEVERE, "bla = {0}", System.getProperty("bla"));
        log.severe(">>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<");
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter(HUB_VERIFY_TOKEN);
        if (token != null && !token.isEmpty() && token.equals(VERIFY_TOKEN)) {
            String challenge = request.getParameter(HUB_CHALLENGE);
            response.getWriter().write(challenge);
            response.getWriter().flush();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String body = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        log.log(Level.INFO, "Receiving lead from Facebook [{0}]", body);

        JsonMapper mapper = new DefaultJsonMapper();
        WebhookObject webhook = mapper.toJavaObject(body, WebhookObject.class);
        log.log(Level.INFO, "webhook [{0}]", webhook);
        
        // TODO: Fire event
//
//        List<WebhookEntry> entryList = webhook.getEntryList();
//        entryList.stream().map((entry) -> entry.getChanges()).forEachOrdered((changes) -> {
//            changes.stream().map((change) -> change.getValue()).map((value) -> (PageLeadgen) value).forEachOrdered((pageLeadgen) -> {
//
//                try {
//                    String campaignName = getLeadCampaignName(pageLeadgen.getFormId());
//                    logLead(pageLeadgen.getLeadgenId(), campaignName);
//
//                } catch (Exception ex) {
//                    Logger.getLogger(FacebookLeadsServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//        });

    }
    
}
