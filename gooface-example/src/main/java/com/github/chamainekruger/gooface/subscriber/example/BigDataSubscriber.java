//package com.github.chamainekruger.gooface.subscriber.example;
//
//import com.google.cloud.pubsub.v1.AckReplyConsumer;
//import com.google.cloud.pubsub.v1.MessageReceiver;
//import com.google.cloud.pubsub.v1.PagedResponseWrappers;
//import com.google.cloud.pubsub.v1.Subscriber;
//import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
//import com.google.common.util.concurrent.MoreExecutors;
//import com.google.pubsub.v1.ListSubscriptionsRequest;
//import com.google.pubsub.v1.ProjectName;
//import com.google.pubsub.v1.PubsubMessage;
//import com.google.pubsub.v1.PushConfig;
//import com.google.pubsub.v1.Subscription;
//import com.google.pubsub.v1.SubscriptionName;
//import com.google.pubsub.v1.TopicName;
//import java.io.IOException;
//import java.util.logging.Level;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import javax.xml.ws.BindingProvider;
//import lombok.extern.java.Log;
//import za.co.discovery.schemas.leads.Leads;
//import za.co.discovery.schemas.leads.LeadsService;
//
///**
// * BigData Subscriber
// * @author Charmaine Kruger (charmaine.kruger@gmail.com)
// */
//@WebListener
//@Log
//public class BigDataSubscriber implements ServletContextListener {
//    
//    private static final String PROJECT_ID = "project.id";
//    private static final String TOPIC_ID = "topic.id";
//    private Subscriber subscriber = null;
//    
//    private final LeadsService leadsService = new LeadsService();
//    
//    private final EndpointConfig endpointConfig = new EndpointConfig();
//    
//    @Override
//    public void contextInitialized(ServletContextEvent event) {
//        String projectId = System.getProperty(PROJECT_ID);
//        String topicId = System.getProperty(TOPIC_ID);
//        createSubscription(projectId, topicId);
//        
//        printAcciiLogo("Discovery Listener waiting for messages...");
//
//        Leads leads = leadsService.getLeadsSoap11();
//        BindingProvider prov = (BindingProvider)leads;
//        endpointConfig.setEndpointUrl(prov, SOAP_ENDPOINT);   
//        DiscoveryLeadsLogger discoveryLeadsLogger = new DiscoveryLeadsLogger(leads);
//        
//        SubscriptionName subscriptionName = SubscriptionName.create(projectId, topicId);
//
//        MessageReceiver messageReceiver = createMessageReceiver(discoveryLeadsLogger,subscriptionName);
//        //SubscriptionName subscriptionName = SubscriptionName.create(projectId, topicId);
//        //subscriber = Subscriber.defaultBuilder(subscriptionName, messageReceiver).build();
//        
//        
//        //subscriber.startAsync();
//        
//        
//
//        
//
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent event) {
//        printAcciiLogo("Example Listener shutting down...");
//        subscriber.stopAsync();
//    }
//
//    private MessageReceiver createMessageReceiver(final DiscoveryLeadsLogger discoveryLeadsLogger,SubscriptionName subscriptionName) {
//
//        // Instantiate an asynchronous message receiver
//        MessageReceiver receiver = new MessageReceiver() {
//            @Override
//            public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
//                // handle incoming message, then ack/nack the received message
//
//                log.info("=========================================================");
//                log.info("Discovery message: " + message.getData().toStringUtf8());
//                try {
//                    discoveryLeadsLogger.logLead(message);
//                    // TODO: Here call LeadsLogger
//                } catch (Exception ex) {
//                    log.log(Level.SEVERE, null, ex);
//                }
//                consumer.ack();
//                log.info("=========================================================");
//            }
//        };
//        
//        
//        subscriber = Subscriber.defaultBuilder(subscriptionName, receiver).build();
//        
//        
//        subscriber.addListener(
//            new Subscriber.Listener() {
//                @Override
//                public void failed(Subscriber.State from, Throwable failure) {
//                // Handle failure. This is called when the Subscriber encountered a fatal error and is shutting down.
//                 log.log(Level.SEVERE, null, failure);
//            }
//        },
//        MoreExecutors.directExecutor());
//        subscriber.startAsync().awaitRunning();
//
//        return receiver;
//    }
//
//    private void createSubscription(String projectId, String topicId) {
//
//        TopicName topicName = TopicName.create(projectId, topicId);
//        SubscriptionName subscriptionName = SubscriptionName.create(projectId, topicId);
//        try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
//            if (!exist(subscriptionAdminClient, projectId, subscriptionName)) {
//                subscriptionAdminClient.createSubscription(subscriptionName, topicName, PushConfig.getDefaultInstance(), 0);
//            }
//        } catch (IOException ex) {
//            log.log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            log.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private boolean exist(SubscriptionAdminClient subscriptionAdminClient, String projectId, SubscriptionName subscriptionName) {
//
//        ListSubscriptionsRequest listSubscriptionsRequest
//                = ListSubscriptionsRequest.newBuilder()
//                        .setProjectWithProjectName(ProjectName.create(projectId))
//                        .build();
//        PagedResponseWrappers.ListSubscriptionsPagedResponse response
//                = subscriptionAdminClient.listSubscriptions(listSubscriptionsRequest);
//        Iterable<Subscription> subscriptions = response.iterateAll();
//        for (Subscription subscription : subscriptions) {
//            if (subscription.getNameAsSubscriptionName().equals(subscriptionName)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void printAcciiLogo(String message) {
//        log.info("\n"
//                + "   _____              __               \n"
//                + "  / ____|            / _|              \n"
//                + " | |  __  ___   ___ | |_ __ _  ___ ___ \n"
//                + " | | |_ |/ _ \\ / _ \\|  _/ _` |/ __/ _ \\\n"
//                + " | |__| | (_) | (_) | || (_| | (_|  __/\n"
//                + "  \\_____|\\___/ \\___/|_| \\__,_|\\___\\___|\n"
//                + "\n"
//                + "   >>> " + message);
//    }
//
//    private static final String SOAP_URL = "https://services.discovery.co.za/leads/services/leads?wsdl";
//    private static final String SOAP_ENDPOINT = "https://services.discovery.co.za/leads/services/leads";
//}
