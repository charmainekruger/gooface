package com.github.chamainekruger.gooface.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.extern.java.Log;

/**
 * Helping methods
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Log
public class GoofaceHelper {
    
    public String toJson(BufferedReader reader) throws IOException {
        try (BufferedReader buffer = reader) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
    
    public void printAcciiLogo(String message) {
        log.info("\n"
                + "   _____              __               \n"
                + "  / ____|            / _|              \n"
                + " | |  __  ___   ___ | |_ __ _  ___ ___ \n"
                + " | | |_ |/ _ \\ / _ \\|  _/ _` |/ __/ _ \\\n"
                + " | |__| | (_) | (_) | || (_| | (_|  __/\n"
                + "  \\_____|\\___/ \\___/|_| \\__,_|\\___\\___|\n"
                + "\n"
                + "   >>> " + message);
    }
}
