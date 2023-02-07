package com.link_intersystems.sakila.film.listing;

import com.link_intersystems.sakila.film.FilmRecord;
import com.link_intersystems.sakilla.film.listing.Film;

import java.lang.reflect.Field;

public class FilmFactory {
    public Film newFilm(FilmRecord record) {
        Film film = new Film();
        setId(film, record.id);
        setTitle(film, record.title);
        return film;
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
