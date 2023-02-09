package com.link_intersystems.film;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FilmFixture {

    private List<FilmRecord> filmRecords = new ArrayList<>();

    public FilmFixture() {
        filmRecords.add(createFilmId1());
        filmRecords.add(createFilmId2());
        filmRecords.add(createFilmId3());
        filmRecords.add(createFilmId4());
        filmRecords.add(createFilmId1001());
    }

    private FilmRecord createFilmId1001() {
        FilmRecord record = new FilmRecord();
        record.id = 1001;
        record.title = "ZORRO ARK";
        record.description = "A Intrepid Panorama of a Mad Scientist And a Boy who must Redeem a Boy in A Monastery";
        record.releaseYear = LocalDate.of(2006, 1, 1);
        record.languageId = 6;
        record.rentalDuration = 3;
        record.rentalRate = 4.99;
        record.length = 50;
        record.replacementCost = 18.99;
        record.rating = "NC-17";
        record.specialFeatures = "Trailers,Commentaries,Behind the Scenes";
        record.lastUpdate = LocalDateTime.of(2006, 2, 15, 5, 3, 42);
        return record;
    }

    private FilmRecord createFilmId4() {
        FilmRecord record = new FilmRecord();
        record.id = 4;
        record.title = "AFFAIR PREJUDICE";
        record.description = "A Fanciful Documentary of a Frisbee And a Lumberjack who must Chase a Monkey in A Shark Tank";
        record.releaseYear = LocalDate.of(2006, 1, 1);
        record.languageId = 1;
        record.rentalDuration = 5;
        record.rentalRate = 2.99;
        record.length = 117;
        record.replacementCost = 26.99;
        record.rating = "G";
        record.specialFeatures = "Deleted Scenes";
        record.lastUpdate = LocalDateTime.of(2006, 2, 15, 5, 3, 42);
        return record;
    }

    private FilmRecord createFilmId3() {
        FilmRecord record = new FilmRecord();
        record.id = 3;
        record.title = "ADAPTATION HOLES";
        record.description = "A Astounding Reflection of a Lumberjack And a Car who must Sink a Lumberjack in A Baloon Factory";
        record.releaseYear = LocalDate.of(2006, 1, 1);
        record.languageId = 1;
        record.rentalDuration = 7;
        record.rentalRate = 2.99;
        record.length = 50;
        record.replacementCost = 18.99;
        record.rating = "NC-17";
        record.specialFeatures = "Trailers,Deleted Scenes";
        record.lastUpdate = LocalDateTime.of(2006, 2, 15, 5, 3, 42);
        return record;
    }

    private FilmRecord createFilmId2() {
        FilmRecord record = new FilmRecord();
        record.id = 2;
        record.title = "ACE GOLDFINGER";
        record.description = "A Astounding Epistle of a Database Administrator And a Explorer who must Find a Car in Ancient China";
        record.releaseYear = LocalDate.of(2006, 1, 1);
        record.languageId = 1;
        record.rentalDuration = 3;
        record.rentalRate = 4.99;
        record.length = 48;
        record.replacementCost = 12.99;
        record.rating = "G";
        record.specialFeatures = "Trailers,Deleted Scenes";
        record.lastUpdate = LocalDateTime.of(2006, 2, 15, 5, 3, 42);
        return record;
    }

    private FilmRecord createFilmId1() {
        FilmRecord record = new FilmRecord();
        record.id = 1;
        record.title = "ACADEMY DINOSAUR";
        record.description = "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies";
        record.releaseYear = LocalDate.of(2006, 1, 1);
        record.languageId = 1;
        record.rentalDuration = 6;
        record.rentalRate = 0.99;
        record.length = 86;
        record.replacementCost = 20.99;
        record.rating = "PG";
        record.specialFeatures = "Deleted Scenes,Behind the Scenes";
        record.lastUpdate = LocalDateTime.of(2006, 2, 15, 5, 3, 42);
        return record;
    }

    public List<FilmRecord> getFilmRecords() {
        return filmRecords;
    }
}
