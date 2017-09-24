package com.github.chamainekruger.gooface.listeners.bigdata;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import com.github.chamainekruger.gooface.common.GoofaceHelper;

/**
 * BigData Listener
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Log
@WebServlet(
    name = "BigDataListener",
    description = "Pushing leads into Google bigData",
    urlPatterns = "/gooface/bigdata"
)
public class BigDataListener extends HttpServlet {

    public final GoofaceHelper helper = new GoofaceHelper();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String payload = helper.toJson(request.getReader());
        
        log.info(">>>> " + payload);
        
        // TODO: Here the code that push into big data
        
    }
    
    
}
