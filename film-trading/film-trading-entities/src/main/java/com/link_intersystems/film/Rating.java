package com.link_intersystems.film;

import com.link_intersystems.person.Age;

/**
 * @link https://www.motionpictures.org/film-ratings/
 */
public class Rating {

    private String name;
    private int minAge;

    Rating(String name, int minAge) {
        this.name = name;
        this.minAge = minAge;
    }

    public String getName() {
        return name;
    }

    public boolean isAgeAllowed(Age age) {
        return age.getYears() >= minAge;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "name='" + name + '\'' +
                ", minAge=" + minAge +
                '}';
    }
}
