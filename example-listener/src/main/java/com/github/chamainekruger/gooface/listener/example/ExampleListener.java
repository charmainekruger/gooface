package com.github.chamainekruger.gooface.listener.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import com.github.chamainekruger.gooface.common.GoofaceHelper;

/**
 * Example Listener
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Log
@WebServlet(
    name = "ExampleListener",
    description = "Example listener",
    urlPatterns = "/gooface/example"
)
public class ExampleListener extends HttpServlet {

    public final GoofaceHelper helper = new GoofaceHelper();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String payload = helper.toJson(request.getReader());
        
        log.info(">>>> " + payload);
        
    }
    
    
}
