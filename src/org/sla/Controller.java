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

    // Home Videos GUI Elements
    public TableView<Film> filmTable;
    public TableColumn<Film, Integer> rankColumn;
    public TableColumn<Film, String> titleColumn;
    public TableColumn<Film, Long> grossColumn;
    public TableColumn<Film, Integer> yearColumn;
    public TableColumn<Film, String> typeColumn;

    public void initialize() {
        // This gets called BEFORE the User ever uses the UI
        Film.setMyController(this);

        // Wire table's columns with fields in ToDoItem object
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        grossColumn.setCellValueFactory(new PropertyValueFactory<>("gross"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("filmType"));

        BoxOfficeFilm.initialize();
        HomeVideo.initialize();
        Film.initialize();

        Film.describeAll();
    }

    public void previousButtonClicked() {
        BoxOfficeFilm.previous();
    }

    public void nextButtonClicked() {
        BoxOfficeFilm.next();
    }

    void updateBoxOfficeFilmsUI(BoxOfficeFilm film, int filmNum, int numOfFilms) {
        rankText.setText(Integer.toString(film.getRank()));
        titleText.setText(film.getTitle());
        yearText.setText(Integer.toString(film.getYear()));
        grossText.setText("$" + Long.toString(film.getGross()));
        peakText.setText(Integer.toString(film.getPeak()));
        filmNumberLabel.setText(filmNum + " of " + numOfFilms);
    }

    void updateHomeVideosUI() {
        // Delete every item from UI
        homeVideoList.getItems().clear();
        ArrayList<HomeVideo> allHomeVideos = HomeVideo.getAllHomeVideos();
        if (allHomeVideos != null) {
            allHomeVideos.forEach(homeVideo -> {
                homeVideoList.getItems().add(homeVideo);
            });
        }
    }

    void updateFilmsUI() {
        // Delete every item from UI
        filmTable.getItems().clear();
        ArrayList<Film> allFilms = Film.getFilms();
        if (allFilms != null) {
            allFilms.forEach(film -> {
                filmTable.getItems().add(film);
            });
        }
    }

}
