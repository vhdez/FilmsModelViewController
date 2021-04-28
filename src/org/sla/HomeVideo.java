package org.sla;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class HomeVideo extends Film implements Serializable {
    // Fields
    static ArrayList<HomeVideo> allHomeVideos;
    private String releaseDate;
    private int allSales;
    private int vhsSales;
    private int dvdSales;
    private int bluraySales;

    // Constructors
    HomeVideo(int rank, String title, long gross, String releaseDate, int year, int allSales, int vhsSales, int dvdSales, int bluraySales) {
        super(rank, title, gross, year, "Home Video");
        this.releaseDate = releaseDate;
        this.allSales = allSales;
        this.vhsSales = vhsSales;
        this.dvdSales = dvdSales;
        this.bluraySales = bluraySales;

        // store the new object in the allHomeVideos ObservableList
        if (allHomeVideos == null) {
            allHomeVideos = new ArrayList<HomeVideo>();
        }
        allHomeVideos.add(this);
    }

    // Setters/Getters

    String getReleaseDate() {
        return releaseDate;
    }

    void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    int getAllSales() {
        return allSales;
    }

    void setAllSales(int allSales) {
        this.allSales = allSales;
    }

    int getVhsSales() {
        return vhsSales;
    }

    void setVhsSales(int vhsSales) {
        this.vhsSales = vhsSales;
    }

    int getDvdSales() {
        return dvdSales;
    }

    void setDvdSales(int dvdSales) {
        this.dvdSales = dvdSales;
    }

    int getBluraySales() {
        return bluraySales;
    }

    void setBluraySales(int bluraySales) {
        this.bluraySales = bluraySales;
    }

    static ArrayList<HomeVideo> getAllHomeVideos() {
        return allHomeVideos;
    }
    // Methods
    public String toString() {
        String description = "Home Video rank #" + this.getRank();
        description = description + " is \"" + this.getTitle() + "\"";
        description = description + " (released " + this.getReleaseDate();
        description = description + ") grossing $" + this.getGross();
        description = description + " (based on " + this.getAllSales() + " sales)";
        return description;
    }

    static void read(String datafilePath) {
        // scan data file line-by-line
        File dataFile = new File(datafilePath);
        Scanner scanner;
        try {
            scanner = new Scanner(dataFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        int ranking = 1;
        while (scanner.hasNextLine()){
            String str = scanner.nextLine();
            Scanner lineScanner = new Scanner(str);
            // scan data files line by separating text between tabs \t
            lineScanner.useDelimiter("\t");

            String title = lineScanner.next();

            String releaseDate = lineScanner.next();
            int yearStartIndex = releaseDate.lastIndexOf(",");
            String yearString = releaseDate.substring(yearStartIndex + 1, releaseDate.length()).trim();
            int year = Integer.parseInt(yearString);

            String allSales = lineScanner.next();
            allSales = allSales.replaceAll(",", "").trim();
            int salesNum = Integer.parseInt(allSales);

            String vhsSales = lineScanner.next();
            if (vhsSales.contains("N/A")) {
                vhsSales = "0";
            }
            if (vhsSales.contains("[")) {
                vhsSales = vhsSales.substring(0, vhsSales.indexOf("["));
            }
            vhsSales = vhsSales.replaceAll(",", "").trim();
            int vhsSalesNum = Integer.parseInt(vhsSales);

            String dvdSales = lineScanner.next();
            if (dvdSales.contains("N/A")) {
                dvdSales = "0";
            }
            if (dvdSales.contains("[")) {
                dvdSales = dvdSales.substring(0, dvdSales.indexOf("["));
            }
            dvdSales = dvdSales.replaceAll(",", "").trim();
            int dvdSalesNum = Integer.parseInt(dvdSales);

            String bluraySales = lineScanner.next();
            if (bluraySales.contains("N/A")) {
                bluraySales = "0";
            }
            if (bluraySales.contains("[")) {
                bluraySales = bluraySales.substring(0, bluraySales.indexOf("["));
            }
            bluraySales = bluraySales.replaceAll(",", "").trim();
            int bluraySalesNum = Integer.parseInt(bluraySales);

            String revenue = lineScanner.next();
            revenue = revenue.substring(1, revenue.indexOf("[")).replaceAll(",", "").trim();
            long revenueNum = Long.parseLong(revenue);

            new HomeVideo(ranking, title, revenueNum, releaseDate, year, salesNum, vhsSalesNum, dvdSalesNum, bluraySalesNum);
            ranking = ranking + 1;
        }
    }

    static public void save() {
        if (allHomeVideos != null && !allHomeVideos.isEmpty()) {
            // write (serialize) the model objects
            try {
                File savedModelFile = new File("serializedAllHomeVideos");
                FileOutputStream savedModelFileStream = new FileOutputStream(savedModelFile);
                ObjectOutputStream out = new ObjectOutputStream(savedModelFileStream);
                out.writeObject(allHomeVideos);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static public boolean restore() {
        // try to read (deserialize) model objects from disk
        File savedModelFile = new File("serializedAllHomeVideos");
        if (savedModelFile.exists()) {
            try {
                FileInputStream savedModelFileStream = new FileInputStream(savedModelFile);
                ObjectInputStream in = new ObjectInputStream(savedModelFileStream);
                allHomeVideos  = (ArrayList<HomeVideo>)in.readObject();
                if (!allHomeVideos.isEmpty()) {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

}
