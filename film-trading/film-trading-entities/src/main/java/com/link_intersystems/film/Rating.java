package com.link_intersystems.film;

import com.link_intersystems.lender.Age;

import java.time.Clock;

/**
 * @link https://www.motionpictures.org/film-ratings/
 */
public class Rating   {

    private String name;
    private int minAge;

    Rating(String name, int minAge) {
        this.name = name;
        this.minAge = minAge;
    }

    public String getName() {
        return name;
    }

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
