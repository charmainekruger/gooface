//package com.github.chamainekruger.gooface.subscriber.example;
//
//import com.github.chamainekruger.gooface.common.Campaign;
//import com.github.chamainekruger.gooface.common.CampaignLeadEvent;
//import com.github.chamainekruger.gooface.common.FieldData;
//import com.github.chamainekruger.gooface.common.Lead;
//import com.google.gson.Gson;
//import com.google.pubsub.v1.PubsubMessage;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.extern.java.Log;
//import za.co.discovery.schemas.leads.AttributeDetail;
//import za.co.discovery.schemas.leads.LeadFlexDetail;
//import za.co.discovery.schemas.leads.LeadSource;
//import za.co.discovery.schemas.leads.Leads;
//import za.co.discovery.schemas.leads.ObjectFactory;
//import za.co.discovery.schemas.leads.ProductCode;
//import za.co.discovery.schemas.leads.RegisterFlexLeadRequest;
//import za.co.discovery.schemas.leads.RegisterFlexLeadRequestMessage;
//import za.co.discovery.schemas.leads.RegisterFlexLeadResponseMessage;
//
//
//
///**
// * Log leads to LMS
// *
// * @author Charmaine Kruger (charmaine.kruger@gmail.com)
// */
//@AllArgsConstructor @NoArgsConstructor
//@Log
//public class DiscoveryLeadsLogger {
//    private final Gson gson = new Gson();
//    
//    private Leads leads = null;
//    
//    public void logLead(PubsubMessage pubsubMessage) throws Exception {
//        
//        CampaignLeadEvent campaignLeadEvent = gson.fromJson(pubsubMessage.getData().toStringUtf8(), CampaignLeadEvent.class);
//
//        Campaign campaign = campaignLeadEvent.getCampaign();
//        
//        
//        ObjectFactory objectFactory = new ObjectFactory();
//        RegisterFlexLeadRequestMessage message = objectFactory.createRegisterFlexLeadRequestMessage();
//        RegisterFlexLeadRequest.Attributes attributes = objectFactory.createRegisterFlexLeadRequestAttributes();
//        
//        Lead lead = campaignLeadEvent.getLead();
//        List<AttributeDetail> attributeDetails = attributes.getAttributeDetail();
//        attributeDetails.addAll(toAttributeDetails(lead.getFieldData()));
//        
//        LeadFlexDetail leadFlexDetail = objectFactory.createLeadFlexDetail();
//        leadFlexDetail.setMedium(LEAD_MEDIUM);
//        leadFlexDetail.setProductCode(getProductCode(campaign.getName()));
//        leadFlexDetail.setSource(LeadSource.EXTERNAL_CLIENT);
//        leadFlexDetail.setCampaign(campaign.getName());
//        message.setAttributes(attributes);
//        message.setLeadDetail(leadFlexDetail);
//        message.setSchemaVersion((float) 1.0);
//        Long leadId = null;
//        RegisterFlexLeadResponseMessage rflrm = this.leads.registerFlexLead(message);
//        log.log(Level.INFO, "------------- rflrm {0}", rflrm.getLeadReferenceNumber());
//        leadId = rflrm.getLeadReferenceNumber();
//
//    }
//
//    private List<AttributeDetail> toAttributeDetails(List<FieldData> fieldDatas){
//        List<AttributeDetail> attributeDetails = new ArrayList<>();
//        for (FieldData fieldData : fieldDatas) {
//            log.log(Level.INFO, "------------- fieldData {0}", fieldData.getName());
//            attributeDetails.addAll(getAttributeDetails(fieldData, fieldData.getValues()));
//        }
//        return attributeDetails;
//    }
//    
//    private List<AttributeDetail> getAttributeDetails(FieldData fieldData,List<String> values){
//        List<AttributeDetail> attributeDetails = new ArrayList<>();
//        for(String value:values){
//            log.log(Level.INFO, "------------- value {0}", value);
//            attributeDetails.add(getAttributeDetail(fieldData, value));
//        }
//        return attributeDetails;
//    }
//    
//    
//    private AttributeDetail getAttributeDetail(FieldData fieldData,String value){
//        String name = fieldData.getName();
//        if (name.equalsIgnoreCase(FIRST_NAME)) {
//            return createAttributeDetail("First_Name",value);
//        }else if (name.equalsIgnoreCase(LAST_NAME)) {
//            return createAttributeDetail("Last_Name",value);
//        }else if (name.equalsIgnoreCase(EMAIL)) {
//            return createAttributeDetail("Primary_Email",value);
//        }else if (name.equalsIgnoreCase(PHONE_NUMBER)) {
//            return createAttributeDetail("Primary_Contact",value);
//        }
//        return null;
//    }
//    
//    private ProductCode getProductCode(String name){
//        // TODO: Change to Enum.
//        if (startsWith(name,"Insure_")) {
//            return ProductCode.STIP;
//        }else if (startsWith(name,"Health_")) {
//            return ProductCode.STIP;
//        }else if (startsWith(name,"Life_")) {
//            return ProductCode.STIP;
//        }else if (startsWith(name,"Gap_")) {
//            return ProductCode.STIP;
//        }else if (startsWith(name,"Card_")) {
//            return ProductCode.STIP;
//        }else if (startsWith(name,"Vitality_")) {
//            return ProductCode.STIP;
//        }else if (startsWith(name,"Invest_")) {
//            return ProductCode.STIP;
//        }
//        return null;
//    }
//    
//    private boolean startsWith(String name,String with){
//        return name.startsWith(with);
//    }
//    
//    private AttributeDetail createAttributeDetail(String name, String value){
//        AttributeDetail attribute = new AttributeDetail();
//        attribute.setAttributeName(name);
//        attribute.setAttributeValue(value);
//        return attribute;
//        
//    }
//    
//    private static final String LEAD_MEDIUM = "FACEBOOK";
//    private static final String FIRST_NAME = "first_name";
//    private static final String LAST_NAME = "last_name";
//    private static final String PHONE_NUMBER = "phone_number";
//    private static final String EMAIL = "email";
//
//}
