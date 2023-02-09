package com.link_intersystems.sakila.film;

import com.link_intersystems.sakila.film.LanguageFixture;
import com.link_intersystems.sakilla.film.listing.Language;

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
