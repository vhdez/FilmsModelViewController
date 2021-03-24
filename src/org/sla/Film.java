package org.sla;

import java.util.ArrayList;

class Film {
    // Fields
    private static ArrayList<Film> films;
    private int rank;
    private String title;
    private long gross;
    private int year;

    // Constructors
    Film(int rank, String title, long gross, int year) {
        this.rank = rank;
        this.title = title;
        this.gross = gross;
        this.year = year;

        // store the new object in the films ArrayList
        if (films == null) {
            films = new ArrayList<Film>();
        }
        films.add(this);
    }

    // Setters/Getters

    int getRank() {
        return rank;
    }

    void setRank(int rank) {
        this.rank = rank;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    long getGross() {
        return gross;
    }

    void setGross(long gross) {
        this.gross = gross;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // Methods
    public String toString() {
        String description = "\"" + this.getTitle();
        description = description + "\" has Film ranking #" + this.getRank();
        description = description + " grossing $" + this.getGross();
        return description;
    }

    static void describeAll() {
        if (films != null) {
            films.forEach(film -> {
                System.out.println(film.toString());
            });
        }
    }
}
