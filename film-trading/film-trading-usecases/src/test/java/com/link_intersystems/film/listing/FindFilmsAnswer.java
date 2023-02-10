package com.link_intersystems.film.listing;

import com.link_intersystems.film.*;
import com.link_intersystems.film.db.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.stream.Collectors;

public class FindFilmsAnswer implements Answer<List<Film>> {

    private FilmFactory filmFactory;

    private FilmFixture filmFixture;
    private LanguageFixture languageFixture;

    public FindFilmsAnswer(FilmFixture filmFixture, LanguageFixture languageFixture) {
        this.filmFixture = filmFixture;
        this.languageFixture = languageFixture;
        LanguageFactory languageFactory = new LanguageFactory(languageFixture);
        filmFactory = new FilmFactory(languageFactory);
    }

    @Override
    public List<Film> answer(InvocationOnMock invocationOnMock) throws Throwable {
        FilmCriteria filmCriteria = invocationOnMock.getArgument(0, FilmCriteria.class);

        List<FilmRecord> filmRecords = filmFixture.getFilmRecords();

        List<FilmRecord> selectedRecords = applyCriteria(filmRecords, filmCriteria);

        return map(selectedRecords);
    }

    private List<Film> map(List<FilmRecord> records) {
        List<Film> films = new ArrayList<>();

        for (FilmRecord record : records) {
            Film film = map(record);
            films.add(film);
        }

        return films;
    }

    private Film map(FilmRecord record) {
        return filmFactory.newFilm(record);
    }

    private List<FilmRecord> applyCriteria(List<FilmRecord> filmRecords, FilmCriteria filmCriteria) {
        Locale language = filmCriteria.getLanguage();
        language = language == null ? Locale.ENGLISH : language;
        LanguageRecord languageRecord = languageFixture.getLanguageRecord(language);

        List<String> ratingNames = filmCriteria.getRatings().stream().map(Rating::getName).collect(Collectors.toList());

        return filmRecords.stream()
                .filter(fr -> fr.languageId == languageRecord.id)
                .filter(fr -> ratingNames.contains(fr.rating))
                .collect(Collectors.toList());
    }
}
