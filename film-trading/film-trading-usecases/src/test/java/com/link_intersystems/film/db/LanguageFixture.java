package com.link_intersystems.film.db;

import java.time.LocalDateTime;
import java.util.*;

public class LanguageFixture {

    private Map<Locale, Integer> localToIds = new HashMap<>();
    List<LanguageRecord> languageRecords = new ArrayList<>();

    public LanguageFixture() {
        languageRecords.add(createEnglish());
        languageRecords.add(createGerman());

        localToIds.put(Locale.ENGLISH, 1);
        localToIds.put(Locale.GERMAN, 6);
    }

    public LanguageRecord getLanguageRecord(int id) {
        return getLanguageRecords().stream().filter(l -> l.id == id).findFirst().orElse(null);
    }

    public List<LanguageRecord> getLanguageRecords() {
        return languageRecords;
    }

    private LanguageRecord createGerman() {
        LanguageRecord record = new LanguageRecord();
        record.id = 6;
        record.name = "German";
        record.lastUpdate = LocalDateTime.of(2006, 2, 15, 5, 2, 19);
        return record;
    }

    private LanguageRecord createEnglish() {
        LanguageRecord record = new LanguageRecord();
        record.id = 1;
        record.name = "English";
        record.lastUpdate = LocalDateTime.of(2006, 2, 15, 5, 2, 19);
        return record;
    }

    public LanguageRecord getLanguageRecord(Locale locale) {
        return getLanguageRecord(localToIds.get(locale));
    }
}
