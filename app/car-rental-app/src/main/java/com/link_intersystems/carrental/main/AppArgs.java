package com.link_intersystems.carrental.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public class AppArgs {

    private Map<String, String> argValues = new HashMap<>();

    public AppArgs(String[] args) {
        ListIterator<String> argIterator = Arrays.asList(args).listIterator();
        while (argIterator.hasNext()) {
            String arg = argIterator.next();


            if (arg.startsWith("-")) {
                String argName = arg.substring(1);

                String argValue;
                if (argIterator.hasNext()) {
                    argValue = argIterator.next();
                } else {
                    argValue = "true";
                }

                argValues.put(argName, argValue);
            }
        }
    }

    public String getArg(String name, String defaultValue) {
        String argValue = argValues.get(name);
        return argValue == null ? defaultValue : argValue;
    }
}
