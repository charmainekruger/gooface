//package com.github.chamainekruger.gooface.subscriber.example;
//
//import java.util.logging.Level;
//import javax.xml.ws.BindingProvider;
//import javax.xml.ws.WebServiceException;
//import lombok.NoArgsConstructor;
//import lombok.extern.java.Log;
//
///**
// * Help with endpoint config
// * @author charma37
// */
//@Log
//@NoArgsConstructor
//public class EndpointConfig {
//    
//    public void setEndpointUrl(BindingProvider bindingProvider,String endpointUrl){
//        try {
//            // Load the webservice endpoint url from the wsdl
//            String defaultEndpointAddress = bindingProvider.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
//            
//            // Load the new one, if different from default
//            if (endpointUrl != null && !endpointUrl.isEmpty() && !endpointUrl.equals(defaultEndpointAddress)) {
//                log.log(Level.FINEST, "Changing SOAP endpoint address to [{0}]", endpointUrl);
//                bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
//            }
//        } catch (WebServiceException e) {
//            log.log(Level.SEVERE,"Error while setting SOAP Endpoint [" + endpointUrl + "]", e);
//            throw new RuntimeException(e.getMessage());
//        } 
//    }
//    
//}
