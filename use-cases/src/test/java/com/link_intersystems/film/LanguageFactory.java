package com.link_intersystems.film;

import java.util.HashMap;
import java.util.Map;

public class LanguageFactory {

    Map<Integer, Language> languageMap = new HashMap<>();

    protected LanguageFixture languageFixture;

    public LanguageFactory(LanguageFixture languageFixture) {
        this.languageFixture = languageFixture;

        languageMap.put(1, Language.ENGLISH);
        languageMap.put(6, Language.GERMAN);
    }

    protected Language getLanguage(int languageId) {

        return languageMap.get(languageId);
    }
}
