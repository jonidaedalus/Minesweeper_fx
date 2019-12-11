package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationBox {
    static boolean answer;
    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Button yes = new Button("Yes");
        Button no = new Button("No");
        Label label = new Label();
        label.setText("Are you sure? Confirm.");

        VBox vbox = new VBox();
        HBox hbox = new HBox();
        vbox.setAlignment(Pos.CENTER);
        hbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, hbox);
        hbox.getChildren().addAll(yes, no);
        yes.setOnAction(e -> {
            answer = true;
            window.close();
        });
        no.setOnAction(e -> {
            answer = false;
            window.close();
        });

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("background.css");
        window.setScene(scene);
        window.showAndWait();

        return answer;

    }
}
