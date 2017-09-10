package com.github.chamainekruger.gooface.core;

/**
 * Error on Facebook fetch
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
public class FacebookFetcherException extends Exception{

    public FacebookFetcherException() {
    }

    public FacebookFetcherException(String string) {
        super(string);
    }

    public FacebookFetcherException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public FacebookFetcherException(Throwable thrwbl) {
        super(thrwbl);
    }

    public FacebookFetcherException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
}
