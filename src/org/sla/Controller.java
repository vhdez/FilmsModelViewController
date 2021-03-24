package org.sla;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class Controller {
    // Box Office Films GUI Elements
    public TextField rankText;
    public TextField titleText;
    public TextField yearText;
    public TextField grossText;
    public TextField peakText;
    public Label filmNumberLabel;
    public Button previousButton;
    public Button nextButton;

    // Home Videos GUI Elements
    public ListView<HomeVideo> homeVideoList;

    public void initialize() {
        BoxOfficeFilm.read("BoxOfficeFilmData");
        BoxOfficeFilm film = BoxOfficeFilm.getFirstFilm();
        int numFilms = BoxOfficeFilm.getNumberOfFilms();
        updateViewWithFilm(film, 1, numFilms);

        HomeVideo.read("HomeVideoData");
        ArrayList<HomeVideo> allHomeVideos = HomeVideo.getAllHomeVideos();
        if (allHomeVideos != null) {
            allHomeVideos.forEach(homeVideo -> {
                homeVideoList.getItems().add(homeVideo);
            });
        }

        Film.describeAll();
    }

    public void previousButtonClicked() {
        BoxOfficeFilm film = BoxOfficeFilm.getPreviousFilm();
        int counter = BoxOfficeFilm.getCurrentFilmNumber();
        int numFilms = BoxOfficeFilm.getNumberOfFilms();
        updateViewWithFilm(film, counter, numFilms);
    }

    public void nextButtonClicked() {
        BoxOfficeFilm film = BoxOfficeFilm.getNextFilm();
        int counter = BoxOfficeFilm.getCurrentFilmNumber();
        int numFilms = BoxOfficeFilm.getNumberOfFilms();
        updateViewWithFilm(film, counter, numFilms);
    }

    private void updateViewWithFilm(BoxOfficeFilm film, int filmNum, int numOfFilms) {
        rankText.setText(Integer.toString(film.getRank()));
        titleText.setText(film.getTitle());
        yearText.setText(Integer.toString(film.getYear()));
        grossText.setText("$" + Long.toString(film.getGross()));
        peakText.setText(Integer.toString(film.getPeak()));
        filmNumberLabel.setText(filmNum + " of " + numOfFilms);
    }
}
