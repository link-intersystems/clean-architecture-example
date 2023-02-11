package com.link_intersystems.film.listing;

import com.link_intersystems.film.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FindFilmsAnswer implements Answer<List<Film>> {

    private FilmFixture filmFixture;
    private LanguageFixture languageFixture;

    public FindFilmsAnswer(FilmFixture filmFixture, LanguageFixture languageFixture) {
        this.filmFixture = filmFixture;
        this.languageFixture = languageFixture;
    }

    @Override
    public List<Film> answer(InvocationOnMock invocationOnMock) throws Throwable {
        FilmCriteria filmCriteria = invocationOnMock.getArgument(0, FilmCriteria.class);

        List<Film> films = filmFixture.getFilms();

        List<Film> selectedRecords = applyCriteria(films, filmCriteria);

        return selectedRecords;
    }

    private List<Film> applyCriteria(List<Film> films, FilmCriteria filmCriteria) {
        Locale languageLocale = filmCriteria.getLanguage();
        Language languageCriterion = languageFixture.getLanguage(languageLocale);
        Category categoryCriterion = filmCriteria.getCategory();

        Predicate<Film> categoryPredicate = categoryCriterion == null ? f -> true : fr -> categoryCriterion.equals(fr.getCategory());

        return films.stream()
                .filter(categoryPredicate)
                .filter(fr -> languageCriterion.equals(fr.getLanguage()))
                .filter(fr -> filmCriteria.getRatings().contains(fr.getRating()))
                .collect(Collectors.toList());
    }
}
