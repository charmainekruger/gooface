package com.github.chamainekruger.gooface.core;

import com.github.chamainekruger.gooface.common.Campaign;
import com.github.chamainekruger.gooface.common.Lead;
import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;



/**
 * Fetch data from Facebook graph
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Log
@AllArgsConstructor
public class FacebookFetcher {
    
    private String accessToken;
    
    public Campaign getCampaign(Long formId) throws FacebookFetcherException{
        try {
            String json = getFacebookGraphData(formId,this.accessToken);
            JsonMapper mapper = new DefaultJsonMapper();
            return mapper.toJavaObject(json, Campaign.class);
        } catch (IOException ex) {
            throw new FacebookFetcherException(ex);
        } 
    }
    
    public Lead getLead(Long leadId) throws FacebookFetcherException{
        try {
            String json = getFacebookGraphData(leadId,this.accessToken);
            JsonMapper mapper = new DefaultJsonMapper();
            return mapper.toJavaObject(json, Lead.class);
        } catch (IOException ex) {
            throw new FacebookFetcherException(ex);
        }
    }
    
    private String getFacebookGraphData(Long id,String accessToken) throws MalformedURLException, ProtocolException, IOException, FacebookFetcherException{

        String facebookGraphUrl = String.format(URL_TEMPLATE,id,accessToken);
        
        URL url = new URL(facebookGraphUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setReadTimeout(15 * 1000);
        connection.connect();
        int responseCode = connection.getResponseCode();
        
        log.log(Level.INFO, "-------------Sending 'GET' request to URL {0}", accessToken);
        log.log(Level.INFO, "-------------Response Code {0}", responseCode);
        
        if(responseCode == 200){
            return read(connection.getInputStream());
        }else {
            throw new FacebookFetcherException("Invalid response from facebook [" + responseCode + "]");
        }
    }
    
    private String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining(NEW_LINE));
        }
    }
    
    private static final String NEW_LINE = "\n";
    private static final String GET = "GET";
    private static final String URL_TEMPLATE = "https://graph.facebook.com/v2.10/%d?access_token=%s";
}
