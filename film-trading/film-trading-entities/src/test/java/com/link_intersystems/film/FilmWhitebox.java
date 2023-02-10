package com.link_intersystems.film;

import com.link_intersystems.film.Film;
import com.link_intersystems.film.Language;
import com.link_intersystems.film.Rating;

import java.lang.reflect.Field;

public class FilmWhitebox {
    public void setRating(Film film, Rating rating) {
        setField(film, "rating", rating);
    }

    public void setLanguage(Film film, Language language) {
        setField(film, "language", language);
    }

    public void setTitle(Film film, String title) {
        setField(film, "title", title);
    }

    public void setId(Film film, int id) {
        setField(film, "id", id);
    }

    public void setCategory(Film film, Category category) {
        setField(film, "category", category);
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
