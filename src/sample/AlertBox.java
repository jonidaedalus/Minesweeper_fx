package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;


public class AlertBox {
    private static Media winSound = new Media(new File("vika.mp3").toURI().toString());
    private static MediaPlayer winPlayer = new MediaPlayer(winSound);
    private static Button button;

    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(100);

        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(12);

        button = new Button(message);

        window.setOnCloseRequest(e-> {
            window.close();
            winPlayer.stop();
        });
        button.setOnAction(e -> {
            window.close();
            winPlayer.stop();

        });



        pane.getChildren().addAll(button);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("background.css");
        window.setScene(scene);
        window.showAndWait();
    }

    protected void victoryMusic() {

        winPlayer.play();
    }

}