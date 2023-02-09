package com.link_intersystems.sakilla.film.rating;

import com.link_intersystems.sakilla.person.Age;

import java.time.Clock;

/**
 * @link https://www.motionpictures.org/film-ratings/
 */
public class MotionPicturesRating implements Rating {

    private String name;
    private int minAge;

    MotionPicturesRating(String name, int minAge) {
        this.name = name;
        this.minAge = minAge;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isAgeAllowed(Age age, Clock clock) {
        return age.getYears(clock) >= minAge;
    }

    @Override
    public String toString() {
        return "MotionPicturesRating{" +
                "name='" + name + '\'' +
                ", minAge=" + minAge +
                '}';
    }
}
