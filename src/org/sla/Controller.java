package org.sla;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.awt.event.HierarchyBoundsAdapter;
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
    public TextField rankTextHomeVideo;
    public TextField titleTextHomeVideo;
    public TextField releaseDateTextHomeVideo;
    public TextField grossTextHomeVideo;
    public TextField salesTextHomeVideo;
    public Button editHomeVideoButton;
    public Button newHomeVideoButton;
    // ALL Videos GUI Elements
    public TableView<Film> filmTable;
    public TableColumn<Film, Integer> rankColumn;
    public TableColumn<Film, String> titleColumn;
    public TableColumn<Film, Long> grossColumn;
    public TableColumn<Film, Integer> yearColumn;
    public TableColumn<Film, String> typeColumn;

    public void initialize() {
        // This gets called BEFORE the User ever uses the UI
        Film.setMyController(this);

        // Home Video ListView
        homeVideoList.getSelectionModel().selectedItemProperty().addListener(event -> {
            HomeVideo selectedRow = homeVideoList.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                if (selectedRow.getRank() == 0) {
                    rankTextHomeVideo.setText("");
                } else {
                    rankTextHomeVideo.setText(selectedRow.getRank().toString());
                }
                titleTextHomeVideo.setText(selectedRow.getTitle());
                releaseDateTextHomeVideo.setText(selectedRow.getReleaseDate());
                if (selectedRow.getGross() == 0) {
                    grossTextHomeVideo.setText("");
                } else {
                    grossTextHomeVideo.setText(selectedRow.getGross().toString());
                }
                if (selectedRow.getAllSales() == 0) {
                    salesTextHomeVideo.setText("");
                } else {
                    salesTextHomeVideo.setText(Integer.toString(selectedRow.getAllSales()));
                }
            }
        });

        filmTable.setEditable(true);
        // Tell TableColumn which Model object field has its value
        rankColumn.setCellValueFactory(new PropertyValueFactory<Film,Integer>("rank"));
        // Tell TableColumn how to convert the String text user edits with into the value type for that data
        rankColumn.setCellFactory(TextFieldTableCell.<Film, Integer>forTableColumn(new IntegerStringConverter()));
        // Write code that gets called when a cell in a row gets edited
        rankColumn.setOnEditCommit(editEvent -> {
            // editEvent can identify the Row object being edited, along with the field's old value and new value
            int newValue = editEvent.getNewValue();
            Film editedRowObject = editEvent.getRowValue();
            editedRowObject.setRank(newValue);
        });

        titleColumn.setCellValueFactory(new PropertyValueFactory<Film,String>("title"));
        // Tell TableColumn how to convert the String text user edits with into the value type for that data
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        // Write code that gets called when a cell in a row gets edited
        titleColumn.setOnEditCommit(editEvent -> {
            // editEvent can identify the Row object being edited, along with the field's old value and new value
            String newValue = editEvent.getNewValue();
            Film editedRowObject = editEvent.getRowValue();
            editedRowObject.setTitle(newValue);
        });
        grossColumn.setCellValueFactory(new PropertyValueFactory<Film,Long>("gross"));
        // Tell TableColumn how to convert the String text user edits with into the value type for that data
        grossColumn.setCellFactory(TextFieldTableCell.<Film, Long>forTableColumn(new LongStringConverter()));
        // Write code that gets called when a cell in a row gets edited
        grossColumn.setOnEditCommit(editEvent -> {
            // editEvent can identify the Row object being edited, along with the field's old value and new value
            long newValue = editEvent.getNewValue();
            Film editedRowObject = editEvent.getRowValue();
            editedRowObject.setGross(newValue);
        });
        yearColumn.setCellValueFactory(new PropertyValueFactory<Film,Integer>("year"));
        // Tell TableColumn how to convert the String text user edits with into the value type for that data
        yearColumn.setCellFactory(TextFieldTableCell.<Film, Integer>forTableColumn(new IntegerStringConverter()));
        // Write code that gets called when a cell in a row gets edited
        yearColumn.setOnEditCommit(editEvent -> {
            // editEvent can identify the Row object being edited, along with the field's old value and new value
            int newValue = editEvent.getNewValue();
            Film editedRowObject = editEvent.getRowValue();
            editedRowObject.setYear(newValue);
        });
        typeColumn.setCellValueFactory(new PropertyValueFactory<Film,String>("filmType"));
        // Tell TableColumn how to convert the String text user edits with into the value type for that data
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        // Write code that gets called when a cell in a row gets edited
        typeColumn.setOnEditCommit(editEvent -> {
            // editEvent can identify the Row object being edited, along with the field's old value and new value
            String newValue = editEvent.getNewValue();
            Film editedRowObject = editEvent.getRowValue();
            editedRowObject.setFilmType(newValue);
        });

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
        if (selectedFile != null && selectedFile.exists()) {
            BoxOfficeFilm.read(selectedFile.getPath());

            updateBoxOfficeFilmsUI(BoxOfficeFilm.getFirstFilm(), 1, BoxOfficeFilm.getNumberOfFilms());
            updateFilmsUI();
            myAccordion.setExpandedPane(boxOfficeMoviesPane);
        }
    }

    public void importHomeMovies() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(myStage);
        if (selectedFile != null && selectedFile.exists()) {
            HomeVideo.read(selectedFile.getPath());

            updateHomeVideosUI();
            updateFilmsUI();
            myAccordion.setExpandedPane(homeVideosPane);
        }
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

    // Home Video methods
    public void editHomeVideo() {
        HomeVideo selectedRow = homeVideoList.getSelectionModel().getSelectedItem();
        int index = homeVideoList.getSelectionModel().getSelectedIndex();
        selectedRow.setRank(Integer.parseInt(rankTextHomeVideo.getText()));
        selectedRow.setTitle(titleTextHomeVideo.getText());
        selectedRow.setReleaseDate(releaseDateTextHomeVideo.getText());
        selectedRow.setGross(Long.parseLong(grossTextHomeVideo.getText()));
        selectedRow.setAllSales(Integer.parseInt(salesTextHomeVideo.getText()));
        updateHomeVideosUI();
    }

    public void newHomeVideo() {
        HomeVideo.addNewEmptyFilm();
        updateHomeVideosUI();
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
        if (BoxOfficeFilm.getNumberOfFilms() > 1) {
            updateBoxOfficeFilmsUI(BoxOfficeFilm.getFirstFilm(), 1, BoxOfficeFilm.getNumberOfFilms());
        }
        updateHomeVideosUI();
        updateFilmsUI();
    }

    public void editRank() {
        String newRankAsText = rankText.getText();
        int newRank = Integer.parseInt(newRankAsText);
        BoxOfficeFilm.getCurrentFilm().setRank(newRank);
    }

    public void editTitle() {
        String newTitle = titleText.getText();
        BoxOfficeFilm.getCurrentFilm().setTitle(newTitle);
    }

    public void editYear() {
        String newYearAsText = yearText.getText();
        int newYear = Integer.parseInt(newYearAsText);
        BoxOfficeFilm.getCurrentFilm().setYear(newYear);
    }

    public void editGross() {
        String newGrossAsText = grossText.getText();
        long newGross = Long.parseLong(newGrossAsText);
        BoxOfficeFilm.getCurrentFilm().setGross(newGross);
    }

    public void editPeak() {
        String newPeakAsText = peakText.getText();
        int newPeak = Integer.parseInt(newPeakAsText);
        BoxOfficeFilm.getCurrentFilm().setPeak(newPeak);
    }

    public void newBoxOfficeFilm() {
        BoxOfficeFilm.addNewEmptyFilm();
    }
}
