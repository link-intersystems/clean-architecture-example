package com.link_intersystems.film;

import com.link_intersystems.person.Age;

/**
 * @link https://www.motionpictures.org/film-ratings/
 */
public class Rating {

    public static final Rating GENERAL_AUDIENCES = new Rating("G", 0);
    public static final Rating PARENTAL_GUIDANCE = new Rating("PG", 0);
    public static final Rating PARENTAL_GUIDANCE_13 = new Rating("PG-13", 13);
    public static final Rating RESTRICTED = new Rating("R", 17);
    public static final Rating NO_ONE_17_AND_UNDER_ADMITTED = new Rating("NC-17", 18);

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
