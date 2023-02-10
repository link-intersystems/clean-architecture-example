package com.link_intersystems.film;

import com.link_intersystems.person.Age;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return minAge == rating.minAge && name.equals(rating.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, minAge);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "name='" + name + '\'' +
                ", minAge=" + minAge +
                '}';
    }

    public boolean includes(Rating o) {
        return this.minAge >= o.minAge;
    }


}
