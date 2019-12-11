package sample;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NameBox {
    static String name1;

    public static String getName1() {
        return name1;
    }

    public static void setName1(String name1) {
        NameBox.name1 = name1;
    }

    public static String getName2() {
        return name2;
    }

    public static void setName2(String name2) {
        NameBox.name2 = name2;
    }

    static String name2;
    static TextField namePlayerOne;
    static TextField namePlayerTwo;
    public static void display() {

        Stage window = new Stage();
        Button submit = new Button("Start");

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("");
        window.setMinWidth(300);
        window.setMinHeight(101);

        VBox pane = new VBox();
        namePlayerOne = new TextField();
        namePlayerTwo = new TextField();
        namePlayerOne.setPromptText("First Player");
        namePlayerTwo.setPromptText("Second Player");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(12);
        pane.getChildren().addAll(namePlayerOne, namePlayerTwo, submit);
        submit.setOnAction(e -> {
            setName1(namePlayerOne.getText());
            setName2(namePlayerTwo.getText());
            window.close();

        });
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("background.css");
        window.setScene(scene);
        window.showAndWait();
    }



}