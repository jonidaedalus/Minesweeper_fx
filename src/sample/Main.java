package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.nio.file.Paths;


public class Main extends Application {
    private Button restartButton = new Button("☺");
    private VBox chooseMenu = new VBox();
    private VBox mapAndButton = new VBox();
    private BorderPane FirstMenu = new BorderPane();
    private BorderPane SecondMenu = new BorderPane();
    private HBox topMenu = new HBox();
    private HBox topMenu2 = new HBox();
    private GridField gridField = new GridField();
    private Label welcomeText = new Label("MINESWEEPER");
    private Button startGameButton = new Button("SOLO GAME");
    private Button startDuoButton = new Button("DUO GAME");
    private Button optionsButton = new Button("OPTIONS");
    private ComboBox optionBox = new ComboBox<>();
    private Button quitButton = new Button("QUIT");
    private static final double HEIGHT = Screen.getPrimary().getBounds().getMaxY();
    private static final double WIDTH = Screen.getPrimary().getBounds().getMaxX();
    private Stage stage;
    private Text p1NameField = new Text("");
    private Text p2NameField = new Text("");
    private Text remainingBombs = new Text();
    private Text score = new Text();
    private Text score2 = new Text();
    private AudioClip mediaPlayer = new AudioClip(Paths.get("open.wav").toUri().toString());
    private Media winSound = new Media(new File("vika.mp3").toURI().toString());
    private MediaPlayer winPlayer = new MediaPlayer(winSound);


    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setMinHeight(HEIGHT - 100);
        stage.setMinWidth(WIDTH);
        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        gridField.playSound("open");



        //scene1:
        chooseMenu.getChildren().addAll(startGameButton, startDuoButton, optionsButton, quitButton);
        chooseMenu.setSpacing(20);
        chooseMenu.setAlignment(Pos.CENTER);
        welcomeText.getStyleClass().add("label-1");
        welcomeText.setTextFill(Color.WHITE);
        topMenu.getChildren().add(welcomeText);
        topMenu.setAlignment(Pos.CENTER);
        FirstMenu.setTop(topMenu);
        FirstMenu.setCenter(chooseMenu);
        Scene scene1 = new Scene(FirstMenu, WIDTH, HEIGHT);
        stage.setScene(scene1);
        stage.show();



        //scene2
        Button goBackButton = new Button("back");
        VBox duoLeftMenu = new VBox();
        VBox duoRightMenu = new VBox();
        gridField.createMap();
        remainingBombs.setText(String.valueOf(gridField.getAllBombs()));
        remainingBombs.setFill(Color.WHITE);
        remainingBombs.setStyle("-fx-font-size:50");
        remainingBombs.setTextAlignment(TextAlignment.CENTER);
        score.setFill(Color.WHITE);
        score.setStyle("-fx-font-size: 25");
        score2.setStyle("-fx-font-size: 25");
        score2.setFill(Color.WHITE);
        p1NameField.setFill(Color.WHITE);
        p1NameField.setStyle("-fx-font-size:30");
        p2NameField.setFill(Color.WHITE);
        p2NameField.setStyle("-fx-font-size:30");
        gridField.getTimerText().setFill(Color.WHITE);
        gridField.getTimerText().setStyle("-fx-font-size:50");
        gridField.setAlignment(Pos.CENTER);
        gridField.setOnMouseClicked(e -> {
            remainingBombs.setText(String.valueOf(gridField.getAllBombs() - gridField.getFlagsPut()));
            if (!gridField.isSolo()) {
                score.setText("" + (gridField.getMainPlayer().getScore() + 10 * gridField.getMainPlayer().getFlaggedBombs()));
                score2.setText("" + (gridField.getSecondPlayer().getScore() + 10 * gridField.getSecondPlayer().getFlaggedBombs()));
            }
        });
        gridField.setOnMousePressed(e -> restartButton.setText("\uD83D\uDE2F"));
        gridField.setOnMouseReleased(e -> restartButton.setText("\u263A"));
        restartButton.setStyle("-fx-font-size:20");
        restartButton.setMinHeight(60);
        mapAndButton.getChildren().addAll(restartButton, gridField);
        mapAndButton.setAlignment(Pos.CENTER);
        mapAndButton.setSpacing(3);
        SecondMenu.setCenter(mapAndButton);
        topMenu2.setPadding(new Insets(10,10,10,10));
        topMenu2.getChildren().addAll(goBackButton, remainingBombs, gridField.getTimerText());
        topMenu2.setSpacing(530);
        SecondMenu.setTop(topMenu2);
        SecondMenu.setLeft(duoLeftMenu);
        SecondMenu.setRight(duoRightMenu);
        duoLeftMenu.setAlignment(Pos.CENTER);
        duoRightMenu.setAlignment(Pos.CENTER);
        Scene scene2 = new Scene(SecondMenu);
        restartButton.setOnAction(e -> restartGame());
        gridField.updateTimer();
        startGameButton.setOnAction(e -> {
            gridField.playSound("open");
            stage.setScene(scene2);
            gridField.setSolo(true);
            gridField.getTimeLine().play();
        });
        duoLeftMenu.getChildren().addAll(score, p1NameField);
        duoRightMenu.getChildren().addAll(score2, p2NameField);
        p1NameField.setTextAlignment(TextAlignment.RIGHT);
        p2NameField.setTextAlignment(TextAlignment.CENTER);
        startDuoButton.setOnAction(e -> {
            gridField.getTimerText().setText("");
            gridField.setSolo(false);
            gridField.playSound("open");
            score.setText("0");
            score2.setText("0");
            NameBox.display();
            gridField.getMainPlayer().setName(NameBox.getName1());
            p1NameField.setText(gridField.getMainPlayer().getName());
            score.setFill(Color.AQUAMARINE);
            p1NameField.setFill(Color.AQUAMARINE);
            gridField.getSecondPlayer().setName(NameBox.getName2());
            p2NameField.setText(gridField.getSecondPlayer().getName());
            scene2.setOnMouseClicked(ev -> {
                if (gridField.getPlayerTurn() % 2 == 1) {
                    score.setFill(Color.AQUAMARINE);
                    p1NameField.setFill(Color.AQUAMARINE);
                    score2.setFill(Color.WHITE);
                    p2NameField.setFill(Color.WHITE);
                } else {
                    p1NameField.setFill(Color.WHITE);
                    score.setFill(Color.WHITE);
                    p2NameField.setFill(Color.AQUAMARINE);
                    score2.setFill(Color.AQUAMARINE);


                }
            });
            stage.setScene(scene2);
        });
        quitButton.setOnAction(e -> {
            closeProgram();
            gridField.playSound("open");

        });
        goBackButton.setOnAction(e -> {
            gridField.setSolo(true);
            restartGame();
            stage.setScene(scene1);
        });


        //scene3
        optionBox.setPromptText("Difficulty");
        optionBox.getItems().addAll("Easy", "Medium", "Hard", "Insane");
        optionBox.setOnAction(e -> {
            if (optionBox.getValue().equals("Easy")) {
                gridField.setDifficulty(0.07);
                restartGame();
            }
            if (optionBox.getValue().equals("Medium")) {
                gridField.setDifficulty(0.17);
                restartGame();
            }
            if (optionBox.getValue().equals("Hard")) {
                gridField.setDifficulty(0.28);
                restartGame();
            }
            if (optionBox.getValue().equals("Insane")) {
                gridField.setDifficulty(0.4);
                restartGame();
            }
        });

        Button customizeButton = new Button("Customize");
        BorderPane options = new BorderPane();
        VBox optionList = new VBox();
        optionList.setSpacing(25);
        Button goToMenuButton = new Button("Back to menu");
        goToMenuButton.setOnAction(e -> {
            stage.setScene(scene1);
            gridField.playSound("open");

        });
        optionList.getChildren().addAll(optionBox, customizeButton, goToMenuButton);
        optionList.setAlignment(Pos.CENTER);
        options.setCenter(optionList);
        Scene scene3 = new Scene(options);
        optionsButton.setOnAction(e -> {
            stage.setScene(scene3);
            gridField.playSound("open");

        });


        //scene4 customize;
        TextField rowField = new TextField();
        TextField columnField = new TextField();
        rowField.setPromptText("#ROWS");
        rowField.setMaxHeight(60);
        rowField.setMaxWidth(160);
        columnField.setMaxHeight(60);
        columnField.setMaxWidth(160);
        columnField.setPromptText("#COLUMNS");
        Button applyButton = new Button("Apply");
        VBox customizeField = new VBox();
        customizeField.getChildren().addAll(rowField, columnField, applyButton);
        customizeField.setAlignment(Pos.CENTER);
        Scene scene4 = new Scene(customizeField, WIDTH, HEIGHT);
        customizeButton.setOnAction(e -> {
            stage.setScene(scene4);
            gridField.playSound("open");
        });
        applyButton.setOnAction(e -> {
            try {
                if (Integer.parseInt(rowField.getText()) < 0 || Integer.parseInt(columnField.getText()) < 0)
                    AlertBox.display("Enter positive numbers", "OK!!!");
                gridField.setRows(Integer.parseInt(rowField.getText()));
                gridField.setColumns(Integer.parseInt(columnField.getText()));
                if(Integer.parseInt(rowField.getText()) > 50){
                    gridField.setRows(50);
                    rowField.setText("50");
                }
                if(Integer.parseInt(columnField.getText()) > 50){
                    gridField.setColumns(50);
                    columnField.setText("50");
                }
                restartGame();
                stage.setScene(scene3);
            } catch (NumberFormatException ex) {
                AlertBox.display("!!!", "Enter the number, not text");
            } catch (NegativeArraySizeException ex) {
            }
        });
        scene1.getStylesheets().add("style.css");
        scene2.getStylesheets().add("style.css");
        scene3.getStylesheets().add("style.css");
        scene4.getStylesheets().add("style.css");
        scene1.setOnMouseClicked(e -> gridField.playSound("open"));
        scene3.setOnMouseClicked(e -> gridField.playSound("open"));
        scene4.setOnMouseClicked(e -> gridField.playSound("open"));

    }


    public static void main(String[] args) {

        launch(args);
    }


    private void closeProgram() {
        Boolean answer = ConfirmationBox.display("Confirm", "Are you sure?");
        if (answer)
            stage.close();
    }

    private void restartGame() {
        if (gridField.isSolo()) {
            gridField.getMainPlayer().resetPlayer();
            gridField.getSecondPlayer().resetPlayer();
            gridField.getChildren().clear();
            gridField.clearScore();
            gridField.createMap();
            p1NameField.setText("");
            p2NameField.setText("");
            score.setText("");
            score2.setText("");
            remainingBombs.setText(String.valueOf(gridField.getAllBombs()));
            gridField.setSeconds(0);
            gridField.getTimerText().setText("time: 0");
            gridField.getTimeLine().stop();
            gridField.getTimeLine().play();

        } else {
            gridField.getTimerText().setText("");
            gridField.getChildren().clear();
            gridField.clearScore();
            gridField.createMap();
            NameBox.display();
            gridField.getMainPlayer().setName(NameBox.getName1());
            p1NameField.setText(gridField.getMainPlayer().getName());
            gridField.getSecondPlayer().setName(NameBox.getName2());
            p2NameField.setText(gridField.getSecondPlayer().getName());
            score.setText("0");
            score2.setText("0");
            remainingBombs.setText(String.valueOf(gridField.getAllBombs()));

        }
        gridField.setEndBool(false);
        gridField.playSound("open");

    }
}



