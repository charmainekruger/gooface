package com.github.chamainekruger.gooface.core.event;

import com.github.chamainekruger.gooface.common.CampaignLeadEvent;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
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
    
    private static final String PRE_URL = "/gooface/";
    
    public void publishMessages(CampaignLeadEvent campaignLeadEvent, String[] subscribers) {
        String json = gson.toJson(campaignLeadEvent);
        
        Queue queue = QueueFactory.getDefaultQueue();
        
        for(String subscriber:subscribers){
            queue.addAsync(TaskOptions.Builder.withUrl(PRE_URL + subscriber).payload(json));
        }
    }

    
}
