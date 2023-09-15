package com.link_intersystems.carrental.main;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Properties;

public class AppArgsParser {

    public Properties parse(String[] args) {
        Properties properties = new Properties();

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

                properties.put(argName, argValue);
            }
        }

        return properties;
    }

}
