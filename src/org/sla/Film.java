package org.sla;

import java.io.*;
import java.util.ArrayList;

public class Film implements Serializable {
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
    static public void save() {
        // write (serialize) the model objects
        if (films != null && !films.isEmpty()) {
            try {
                File savedModelFile = new File("serializedAllFilms");
                FileOutputStream savedModelFileStream = new FileOutputStream(savedModelFile);
                ObjectOutputStream out = new ObjectOutputStream(savedModelFileStream);
                out.writeObject(films);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static public boolean restore() {
        // try to read (deserialize) model objects from disk
        File savedModelFile = new File("serializedAllFilms");
        if (savedModelFile.exists()) {
            try {
                FileInputStream savedModelFileStream = new FileInputStream(savedModelFile);
                ObjectInputStream in = new ObjectInputStream(savedModelFileStream);
                films  = (ArrayList<Film>)in.readObject();
                if (!films.isEmpty()) {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

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
