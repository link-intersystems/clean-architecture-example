package com.link_intersystems.sakila.film.listing;

import com.link_intersystems.sakila.film.FilmRecord;
import com.link_intersystems.sakilla.film.listing.Film;

import java.lang.reflect.Field;

public class FilmFactory {
    public Film newFilm(FilmRecord record) {
        Film film = new Film();
        setId(film, record.id);
        return film;
    }

    private void setId(Film film, int id){
        try {
            Field idField = Film.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(film, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
