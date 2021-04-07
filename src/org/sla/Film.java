package org.sla;

import java.util.ArrayList;

public class Film {
    // Fields
    private static Controller myController;
    private static ArrayList<Film> films;
    private Integer rank;
    private String title;
    private Long gross;
    private Integer year;
    private String filmType;

    // Constructors
    Film(int rank, String title, long gross, int year, String filmType) {
        this.rank = rank;
        this.title = title;
        this.gross = gross;
        this.year = year;
        this.filmType = filmType;

        // store the new object in the films ArrayList
        if (films == null) {
            films = new ArrayList<Film>();
        }
        films.add(this);
    }

    static void initialize() {
        getMyController().updateFilmsUI();
    }

    // Setters/Getters

    public static Controller getMyController() {
        return myController;
    }

    public static void setMyController(Controller myController) {
        Film.myController = myController;
    }

    public static ArrayList<Film> getFilms() {
        return films;
    }

    public static void setFilms(ArrayList<Film> films) {
        Film.films = films;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getGross() {
        return gross;
    }

    public void setGross(Long gross) {
        this.gross = gross;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
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
