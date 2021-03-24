package org.sla;

class FilmsRead {

    public static void main(String[] args) {
        String boxOfficeFilmsData = args[0];
        BoxOfficeFilm.read(boxOfficeFilmsData);

        String homeVideoData = args[1];
        HomeVideo.read(homeVideoData);

        Film.describeAll();
    }
}
