package com.link_intersystems.film;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageFixture {
    private Map<Locale, Language> languagesByLocale = new HashMap<>();

    public LanguageFixture() {

        languagesByLocale.put(Locale.ENGLISH, Language.ENGLISH);
        languagesByLocale.put(Locale.GERMAN, Language.GERMAN);
    }

    public Language getLanguage(Locale locale) {
        return languagesByLocale.get(locale);
    }

}
