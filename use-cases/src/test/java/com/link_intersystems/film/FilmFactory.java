package com.link_intersystems.film;

import java.lang.reflect.Field;

public class FilmFactory {

    private LanguageFactory languageFactory;

    public FilmFactory(LanguageFactory languageFactory) {
        this.languageFactory = languageFactory;
    }

    public Film newFilm(FilmRecord record) {
        Film film = new Film();
        setId(film, record.id);
        setTitle(film, record.title);
        setLanguage(film, languageFactory.getLanguage(record.languageId));
        Rating rating = new MotionPicturesRatingPolicy().getRatingByName(record.rating);
        setRating(film, rating);
        return film;
    }

    private void setRating(Film film, Rating rating) {
        setField(film, "rating", rating);
    }

    private void setLanguage(Film film, Language language) {
        setField(film, "language", language);
    }

    private void setTitle(Film film, String title) {
        setField(film, "title", title);
    }

    private void setId(Film film, int id) {
        setField(film, "id", id);
    }

    private void setField(Film film, String fieldName, Object value) {
        try {
            Field field = Film.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(film, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
