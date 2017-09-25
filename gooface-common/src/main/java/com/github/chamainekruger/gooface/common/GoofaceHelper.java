package com.github.chamainekruger.gooface.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import lombok.extern.java.Log;

/**
 * Helping methods
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Log
public class GoofaceHelper {
    
    public Map<String,String> toMap(List<FieldData> fieldDatas){
        Map<String,String> m = new HashMap<>();
                
        fieldDatas.forEach((fieldData) -> {
            fieldData.getValues().forEach((field) -> {
                m.put(fieldData.getName(), field);
            });
        });
        return m;
    }
    
    public String toJson(BufferedReader reader) throws IOException {
        try (BufferedReader buffer = reader) {
            return buffer.lines().collect(Collectors.joining(NEW_LINE));
        }
    }
    
    public void printAcciiLogo(String message) {
        log.log(Level.INFO,LOGO + "   >>> {0}", message);
    }
    
    public static final String REF = "ref";
    private static final String NEW_LINE = "\n";
    
    private static final String LOGO = "\n"
                + "   _____              __               \n"
                + "  / ____|            / _|              \n"
                + " | |  __  ___   ___ | |_ __ _  ___ ___ \n"
                + " | | |_ |/ _ \\ / _ \\|  _/ _` |/ __/ _ \\\n"
                + " | |__| | (_) | (_) | || (_| | (_|  __/\n"
                + "  \\_____|\\___/ \\___/|_| \\__,_|\\___\\___|\n"
                + "\n";
}
