package org.sla;

class FilmsRead {

    public static void main(String[] args) {
        BoxOfficeFilm.read("BoxOfficeFilmData");

        HomeVideo.read("HomeVideoData");

        Film.describeAll();
    }
}
