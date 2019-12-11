package sample;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Grid extends GridPane {
    private int x, y;
    private boolean isBombed;
    private boolean isOpened = false;
    private boolean isFlagged = false;
    private int squareSize = 40;
    private Rectangle rect = new Rectangle(squareSize, squareSize);
    private Text text = new Text();



    Grid(int x, int y, boolean isBombed, int squareSize) {
        this.x = x;
        this.y = y;
        this.isBombed = isBombed;
        this.squareSize = squareSize;
        rect.getStyleClass().add("rectangle");
        rect.setStroke(Color.WHITE);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        rect.setHeight(squareSize);
        rect.setWidth(squareSize);
//        text.setText(isBombed ? "X" : "");
        rect.setFill(Color.rgb(140, 140, 140));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(squareSize);
        text.setStyle("-fx-font-weight: bold");
        text.setVisible(false);
        add(rect, x, y);
        add(text, x, y);
    }

    protected int getX() {
        return x;
    }

    protected int getY() {
        return y;
    }

    protected boolean isBombed() {
        return isBombed;
    }

    protected boolean isOpened() {
        return isOpened;
    }

    protected void setOpened(boolean opened) {
        isOpened = opened;
    }

    protected boolean isFlagged() {
        return isFlagged;
    }

    protected void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    protected Rectangle getRect() {
        return rect;
    }

    protected Text gettext() {
        return text;
    }

}
