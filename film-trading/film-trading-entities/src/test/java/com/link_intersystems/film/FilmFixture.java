package com.link_intersystems.film;

import java.util.ArrayList;
import java.util.List;

public class FilmFixture {

    private FilmWhitebox filmWhitebox = new FilmWhitebox();
    private List<Film> films = new ArrayList<>();

    public FilmFixture() {
        films.add(createFilmId1());
        films.add(createFilmId2());
        films.add(createFilmId3());
        films.add(createFilmId4());
        films.add(createFilmId1001());
    }

    private Film createFilmId1001() {
        Film film = new Film();
        filmWhitebox.setId(film, 1001);
        filmWhitebox.setTitle(film, "ZORRO ARK");
        filmWhitebox.setLanguage(film, Language.GERMAN);
        filmWhitebox.setRating(film, Rating.NO_ONE_17_AND_UNDER_ADMITTED);
        filmWhitebox.setCategory(film, Category.COMEDY);
        return film;
    }

    private Film createFilmId4() {
        Film film = new Film();
        filmWhitebox.setId(film, 4);
        filmWhitebox.setTitle(film, "AFFAIR PREJUDICE");
        filmWhitebox.setLanguage(film, Language.ENGLISH);
        filmWhitebox.setRating(film, Rating.GENERAL_AUDIENCES);
        filmWhitebox.setCategory(film, Category.HORROR);
        return film;
    }

    private Film createFilmId3() {
        Film film = new Film();
        filmWhitebox.setId(film, 3);
        filmWhitebox.setTitle(film, "ADAPTATION HOLES");
        filmWhitebox.setLanguage(film, Language.ENGLISH);
        filmWhitebox.setRating(film, Rating.NO_ONE_17_AND_UNDER_ADMITTED);
        filmWhitebox.setCategory(film, Category.DOCUMENTARY);
        return film;
    }

    private Film createFilmId2() {
        Film film = new Film();
        filmWhitebox.setId(film, 2);
        filmWhitebox.setTitle(film, "ACE GOLDFINGER");
        filmWhitebox.setLanguage(film, Language.ENGLISH);
        filmWhitebox.setRating(film, Rating.GENERAL_AUDIENCES);
        filmWhitebox.setCategory(film, Category.HORROR);
        return film;
    }

    private Film createFilmId1() {
        Film film = new Film();
        filmWhitebox.setId(film, 1);
        filmWhitebox.setTitle(film, "ACADEMY DINOSAUR");
        filmWhitebox.setLanguage(film, Language.ENGLISH);
        filmWhitebox.setRating(film, Rating.PARENTAL_GUIDANCE);
        filmWhitebox.setCategory(film, Category.DOCUMENTARY);
        return film;
    }

    public List<Film> getFilms() {
        return films;
    }
}
