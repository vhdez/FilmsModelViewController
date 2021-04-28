package org.sla;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class BoxOfficeFilm extends Film implements Serializable {

    // Fields
    private static ArrayList<BoxOfficeFilm> boxOfficeFilms;
    private static int currentFilmNumber;
    private int peak;

    // Constructors
    BoxOfficeFilm(int rank, String title, int releaseYear, long gross, int peak) {
        super(rank, title, gross, releaseYear, "Box Office");
        this.peak = peak;

        // store the new object in the boxOfficeFilms ArrayList
        if (boxOfficeFilms == null) {
            boxOfficeFilms = new ArrayList<BoxOfficeFilm>();
        }
        boxOfficeFilms.add(this);
    }

    // Setters/Getters

    public int getPeak() {
        return peak;
    }

    public void setPeak(int peak) {
        this.peak = peak;
    }

    static public int getCurrentFilmNumber() {
        return currentFilmNumber;
    }

    static public int getNumberOfFilms() {
        return boxOfficeFilms.size();
    }

    // Methods
    public String toString() {
        String description = "Box Office rank #" + this.getRank();
        description = description + " is \"" + this.getTitle() + "\"" ;
        description = description + " from year " + this.getYear();
        description = description + " grossing $" + this.getGross();
        description = description + " and peaking at rank #" + this.getPeak();
        return description;
    }

    static void read(String dataFilePath) {
        // try to create Scanner
        Scanner scanner = null;
        try {
            File file = new File(dataFilePath);
            scanner = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem opening file: " + dataFilePath);
        }
        if (scanner == null) {
            // I can't scan without a scanner, so we're done.
            return;
        }

        // Read from each line in data file until there are no more
        while (scanner.hasNext()) {
            String next = scanner.nextLine();
            // Construct a new scanner for each to get its tokens
            Scanner lineScanner = new Scanner(next);
            // Data tokens are separated by tabs
            lineScanner.useDelimiter("\t");

            // There are 5 data tokens that we need for each BoxOfficeFilm
            int ranking = lineScanner.nextInt();
            int peak = lineScanner.nextInt();
            String title = lineScanner.next();
            long revenue = lineScanner.nextLong();
            int year = lineScanner.nextInt();

            Film film = new BoxOfficeFilm(ranking, title, year, revenue, peak);
        }
    }

    static public BoxOfficeFilm getFirstFilm() {
        currentFilmNumber = 1;
        return boxOfficeFilms.get(currentFilmNumber - 1);
    }

    static public BoxOfficeFilm getNextFilm() {
        if (currentFilmNumber < boxOfficeFilms.size()) {
            currentFilmNumber = currentFilmNumber + 1;
        } else {
            currentFilmNumber = 1;
        }
        return boxOfficeFilms.get(currentFilmNumber - 1);
    }

    static public BoxOfficeFilm getPreviousFilm() {
        if (currentFilmNumber > 1) {
            currentFilmNumber = currentFilmNumber - 1;
        } else {
            currentFilmNumber = boxOfficeFilms.size();
        }
        return boxOfficeFilms.get(currentFilmNumber - 1);
    }

    static void previous() {
        getMyController().updateBoxOfficeFilmsUI(getPreviousFilm(), getCurrentFilmNumber(), getNumberOfFilms());
    }

    static void next() {
        getMyController().updateBoxOfficeFilmsUI(getNextFilm(), getCurrentFilmNumber(), getNumberOfFilms());
    }

    static public void save() {
        if (boxOfficeFilms != null && !boxOfficeFilms.isEmpty()) {
            // write (serialize) the model objects
            try {
                File savedModelFile = new File("serializedAllBoxOfficeFilms");
                FileOutputStream savedModelFileStream = new FileOutputStream(savedModelFile);
                ObjectOutputStream out = new ObjectOutputStream(savedModelFileStream);
                out.writeObject(boxOfficeFilms);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static public boolean restore() {
        // try to read (deserialize) model objects from disk
        File savedModelFile = new File("serializedAllBoxOfficeFilms");
        if (savedModelFile.exists()) {
            try {
                FileInputStream savedModelFileStream = new FileInputStream(savedModelFile);
                ObjectInputStream in = new ObjectInputStream(savedModelFileStream);
                boxOfficeFilms  = (ArrayList<BoxOfficeFilm>)in.readObject();
                if (!boxOfficeFilms.isEmpty()) {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

}
