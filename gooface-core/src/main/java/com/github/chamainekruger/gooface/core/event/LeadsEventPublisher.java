package com.github.chamainekruger.gooface.core.event;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.PagedResponseWrappers.ListTopicsPagedResponse;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ListTopicsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;
import java.io.IOException;
import java.util.logging.Level;
import lombok.extern.java.Log;

/**
 * Produce leads for distribution on pubsub
 *
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Log
public class LeadsEventPublisher {

    private final Gson gson = new Gson();
    
    public Topic createTopic(String projectId, String topicId) {
        TopicName topicName = TopicName.create(projectId, topicId);
        try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {    
            if (!exist(topicAdminClient,projectId,topicName)) {
                return topicAdminClient.createTopic(topicName);
            }else{
                return topicAdminClient.getTopic(topicName);
            }
        }catch(Exception e){
            log.severe(e.getMessage());
            return null;
        }
    }

    public void publishMessages(CampaignLeadEvent campaignLeadEvent, String projectId, String topicId) {
        Topic topic = createTopic(projectId, topicId);
        
        Publisher publisher = null;
        try {
            publisher = Publisher.defaultBuilder(topic.getNameAsTopicName()).build();
            String json = gson.toJson(campaignLeadEvent);
            ByteString data = ByteString.copyFromUtf8(json);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            log.log(Level.INFO, "Published message [{0}] - {1}", new Object[]{json, messageIdFuture});
        } catch (IOException ex) {
            log.severe(ex.getMessage());
        } finally {
            if (publisher != null) {
                try {
                    publisher.shutdown();
                } catch (Exception ex) {
                    log.log(Level.SEVERE, "Swallowing exception [{0}]", ex.getMessage());
                    // O well.
                }
            }
        }

    }

    private boolean exist(TopicAdminClient topicAdminClient, String projectId, TopicName topicName) throws IOException {
        
        ListTopicsRequest listTopicsRequest
                = ListTopicsRequest.newBuilder()
                        .setProjectWithProjectName(ProjectName.create(projectId))
                        .build();
        ListTopicsPagedResponse response = topicAdminClient.listTopics(listTopicsRequest);
        Iterable<Topic> topics = response.iterateAll();
        for (Topic topic : topics) {
            if(topic.getNameAsTopicName().equals(topicName)){
               return true;
            }
        }
        return false;
        

    }
    
}
