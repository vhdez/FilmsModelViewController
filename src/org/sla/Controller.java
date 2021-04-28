package org.sla;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Controller {
    private Stage myStage;

    // Data action buttons
    public Button importBoxOfficeFilmsButton;
    public Button importHomeMoviesButton;
    public Button saveDataButton;

    // Accordion layout with panes
    public Accordion myAccordion;
    public TitledPane homeVideosPane;
    public TitledPane boxOfficeMoviesPane;
    public TitledPane allFilmsPane;

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

        // Wire table's columns with fields of Films object
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        grossColumn.setCellValueFactory(new PropertyValueFactory<>("gross"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("filmType"));

        boolean thereWasData = restoreData();
        if (thereWasData) {
            updateAllUIs();
            myAccordion.setExpandedPane(allFilmsPane);
        }
    }

    // Setters/getters
    public Stage getMyStage() {
        return myStage;
    }

    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    // Data onAction methods
    public void importBoxOfficeFilms() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(myStage);
        BoxOfficeFilm.read(selectedFile.getPath());

        updateBoxOfficeFilmsUI(BoxOfficeFilm.getFirstFilm(), 1, BoxOfficeFilm.getNumberOfFilms());
        updateFilmsUI();
        myAccordion.setExpandedPane(boxOfficeMoviesPane);
    }

    public void importHomeMovies() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(myStage);
        HomeVideo.read(selectedFile.getPath());

        updateHomeVideosUI();
        updateFilmsUI();
        myAccordion.setExpandedPane(homeVideosPane);
    }

    public void saveData() {
        Film.save();
        BoxOfficeFilm.save();
        HomeVideo.save();
    }

    public boolean restoreData() {
        boolean filmsRestored = Film.restore();
        boolean boxOfficeFilmsRestored = BoxOfficeFilm.restore();
        boolean homeVideosRestored = HomeVideo.restore();
        if (filmsRestored || boxOfficeFilmsRestored || homeVideosRestored) {
            Film.describeAll();
            return true;
        }

        return false;
    }

    // Box Office Film text field and buttons onAction methods
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

    // Home Video ListView and buttons onAction methods
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

    // All Films tableView and buttons onAction methods
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

    void updateAllUIs() {
        updateBoxOfficeFilmsUI(BoxOfficeFilm.getFirstFilm(), 1, BoxOfficeFilm.getNumberOfFilms());
        updateHomeVideosUI();
        updateFilmsUI();
    }
}
