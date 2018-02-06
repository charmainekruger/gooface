package com.github.chamainekruger.gooface.core.event;

import com.github.chamainekruger.gooface.common.CampaignLeadEvent;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.RetryOptions;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gson.Gson;
import lombok.extern.java.Log;

/**
 * Produce leads for distribution on queue
 *
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Log
public class LeadsEventQueue {
    private final Gson gson = new Gson();
    
    public void publishMessages(CampaignLeadEvent campaignLeadEvent, String[] subscribers) {
        String json = gson.toJson(campaignLeadEvent);
        
        Queue queue= QueueFactory.getDefaultQueue();
        
        // TODO: Maybe move this to conf ?
        RetryOptions retry = RetryOptions.Builder
                .withTaskRetryLimit(2)
                .taskAgeLimitSeconds(7201)
                .minBackoffSeconds(3600)
                .maxBackoffSeconds(7200);
        
        for(String subscriber:subscribers){
            
            TaskOptions to = TaskOptions.Builder.withUrl(PRE_URL + subscriber)
                    .retryOptions(retry)
                    .payload(json);
            
            queue.addAsync(to);
        }
    }

    private static final String PRE_URL = "/gooface/";
}
