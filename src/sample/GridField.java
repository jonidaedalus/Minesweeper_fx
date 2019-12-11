package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GridField extends GridPane {
    private int rows = 10;
    private int columns = 10;
    private Grid[][] tiles;
    private Image img = new Image("flag.png");
    private ImagePattern pattern = new ImagePattern(img);
    private boolean difficulty;
    private double difficultyValue = 0.07;
    private boolean solo = true;
    private Image bombFailImg = new Image("bombFail.jpg");
    private ImagePattern bombFailPattern = new ImagePattern(bombFailImg);
    private Player mainPlayer = new Player();
    private Player secondPlayer = new Player();
//    private int Score1 = 0;
//    private int Score2 = 0;
//    private String player1 = "Player 1";
//    private String player2 = "Player 2";
    private int flaggedBombs = 0;
    private int flagsPut = 0;
//    private int flaggedBombs1 = 0;
//    private int flaggedBombs2 = 0;
    private int allBombs = 0;
    private int playerTurn = 1;
    private boolean end = false;
    private AlertBox alertBox = new AlertBox();
    private AudioClip openSound = new AudioClip(Paths.get("open.wav").toUri().toString());
    private AudioClip bombSound = new AudioClip(Paths.get("Explosion+1.wav").toUri().toString());
    private Timeline timeLine= new Timeline();
    private Text timerText = new Text("time : 0");
    private int seconds = 0;


    protected void createMap() {
        tiles = new Grid[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                difficulty = Math.random() < difficultyValue;
                if(rows <= 15 && columns <=15)
                    tiles[i][j] = new Grid(i, j, difficulty, 40);
                if(rows > 15 || columns > 15){
                    tiles[i][j] = new Grid(i, j, difficulty, rows>columns ? 600/rows : 600/columns);
                    tiles[i][j].gettext().setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
                }
//                tiles[i][j] = new Grid(i, j, difficulty, (columns <= 15 || rows <=15) ? 40 : (600 / columns));
                add(tiles[i][j], i, j);
            }
        }
        countBombs();
        setHgap(0.5);
        setVgap(0.5);
    }

    protected void countBombs() {
        allBombs = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int a = i;
                int b = j;
                if (tiles[a][b].isBombed())
                    allBombs++;
                tiles[i][j].setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY && !tiles[a][b].isFlagged() && !tiles[a][b].isOpened()) {
                        playerTurn++;
                        playSound("open");
                        openNeighbours(tiles[a][b]);
                        endGame();
//                        System.out.println("P1    " + (mainPlayer.getScore()+10*mainPlayer.getFlaggedBombs()) + " P2   " + (secondPlayer.getScore()+10*secondPlayer.getFlaggedBombs()));

                    }
                    if (e.getButton() == MouseButton.SECONDARY && !tiles[a][b].isOpened()) {
                        playerTurn++;
                        playSound("open");
                        flag(tiles[a][b]);
                        endGame();
//                        System.out.println("P1    " + (mainPlayer.getScore()+10*mainPlayer.getFlaggedBombs()) + " P2   " + (secondPlayer.getScore()+10*secondPlayer.getFlaggedBombs()));

                    }
                });
                int bombs = 0;
                if (!tiles[i][j].isBombed()) {
                    List<Grid> neighbours = getNeigbours(tiles[i][j]);
                    for (Grid s : neighbours) {
                        if (s.isBombed()) {
                            bombs++;
                            tiles[i][j].gettext().setText(bombs + "");
                            if (bombs == 1) {
                                tiles[i][j].gettext().setFill(Color.BLUE);
                            }
                            if (bombs == 2) {
                                tiles[i][j].gettext().setFill(Color.GREEN);
                            }
                            if (bombs == 3) {
                                tiles[i][j].gettext().setFill(Color.RED);
                            }
                            if (bombs == 4) {
                                tiles[i][j].gettext().setFill(Color.DARKBLUE);
                            }
                            if (bombs == 5) {
                                tiles[i][j].gettext().setFill(Color.BROWN);
                            }
                            if (bombs == 6) {
                                tiles[i][j].gettext().setFill(Color.rgb(0, 186, 180));
                            }

                        }
                    }
                }
            }
        }
        System.out.println(allBombs);
    }

    protected void flag(Grid grid) {
        if (!grid.getRect().getFill().equals(pattern) && end == false) {
            grid.gettext().setVisible(false);
            grid.setFlagged(true);
            flagsPut++;
            grid.getRect().setFill(pattern);
            if (grid.isBombed()) {
                flaggedBombs++;
                if (playerTurn % 2 == 0 && solo == false)
                    mainPlayer.incrementFlaggedBombs();
                else if (playerTurn % 2 == 1 && solo == false)
                    secondPlayer.incrementFlaggedBombs();
            }
        } else if (grid.isFlagged() == true && end == false) {
            grid.setFlagged(false);
            flagsPut--;
            if (grid.isBombed()) {
                if (playerTurn % 2 == 0 && solo == false)
                    mainPlayer.decrementFlaggedBombs();
                else if (playerTurn % 2 == 1 && solo == false)
                    secondPlayer.decrementFlaggedBombs();
                flaggedBombs--;
            }
            grid.getRect().setFill(Color.rgb(140, 140, 140));
        }

    }

    protected void endGame() {

        if (solo == false) {
            if (allBombs == flaggedBombs && ((mainPlayer.getScore() + secondPlayer.getScore()) == rows * columns - flaggedBombs)) {

                alertBox.victoryMusic();
                if ((mainPlayer.getScore() + 10 * mainPlayer.getFlaggedBombs()) > (secondPlayer.getScore() + 10 * secondPlayer.getFlaggedBombs()))
                    alertBox.display("Congrats", (mainPlayer.getName() + " won the game"));

                else
                    alertBox.display("Congrats", (secondPlayer.getName() + " won the game"));
                System.out.println("P1   " + (mainPlayer.getScore() + 10 * mainPlayer.getFlaggedBombs()));
                System.out.println("P2   " + (secondPlayer.getScore() + 10 * secondPlayer.getFlaggedBombs()));
                getChildren().clear();
                clearScore();
                createMap();
                System.out.println("END");

            } else if (end == true) {
                alertBox.victoryMusic();
                if (playerTurn % 2 == 1)
                    alertBox.display("Congrats", (mainPlayer.getName() + " won the game"));
                else
                    alertBox.display("Congrats", (secondPlayer.getName() + " won the game"));
                getChildren().clear();
                clearScore();
                createMap();
            }
        } else if (allBombs == flaggedBombs && (mainPlayer.getScore() == rows * columns - flaggedBombs)) { //solo
            end = true;
            alertBox.victoryMusic();
            alertBox.display("Congrats", "You won the game");
            seconds = 0;
            timerText.setText("time: 0");
            timeLine.play();
            getChildren().clear();
            clearScore();
            createMap();
            System.out.println("END");
        }
    }

    protected void loseEvent() {
        end = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                tiles[i][j].gettext().setVisible(true);
                if (tiles[i][j].isBombed()) {
                    tiles[i][j].getRect().setFill(bombFailPattern);
                    tiles[i][j].setOpened(true);
                } else {
                    tiles[i][j].setOpened(true);
                    tiles[i][j].gettext().setVisible(true);
                    tiles[i][j].getRect().setFill(Color.rgb(209, 209, 209));
                }
            }
        }
    }

    protected List<Grid> getNeigbours(Grid grid) {
        List<Grid> neighbours = new ArrayList<>();
        int[] neighbourPoints = new int[]{
                -1, -1, // bottom left
                -1, 0, // bottom center
                -1, 1, // bottom right
                0, -1, // left
                0, 1, // right
                1, -1, // top left
                1, 0, // top center
                1, 1 // top right
        };
        for (int i = 0; i < neighbourPoints.length; i++) {
            int x = neighbourPoints[i];
            int y = neighbourPoints[++i];

            int neighbourX = grid.getX() + x;
            int neighbourY = grid.getY() + y;

            if (neighbourX >= 0 && neighbourX < rows && neighbourY >= 0 && neighbourY < columns) {
                neighbours.add(tiles[neighbourX][neighbourY]);
            }
        }

        return neighbours;
    }

    protected void openNeighbours(Grid grid) {
        if (grid.isBombed()) {
            grid.getRect().setFill(bombFailPattern);
            if (!grid.isOpened())
                playSound("bomb");
            grid.setOpened(true);
            loseEvent();
            System.out.println("Game Over");
            return;
        }
        if (grid.isOpened() || grid.isFlagged())
            return;

        if (!grid.isOpened()) {
            grid.setOpened(true);
            if (playerTurn % 2 == 0 && solo == false)
                mainPlayer.incrementScore();
            else if (playerTurn % 2 == 1 && solo == false)
                secondPlayer.incrementScore();
            else if (solo == true)
                mainPlayer.incrementScore();
        }
        grid.setOpened(true);
        grid.gettext().setVisible(true);
        grid.getRect().setFill(Color.rgb(209, 209, 209));
        if (!grid.gettext().getText().isEmpty())
            return;

        grid.gettext().setVisible(true);

        if (grid.gettext().getText().isEmpty()) {
            for (Grid neighbour : getNeigbours(grid)) {
                openNeighbours(neighbour);
            }
        }
        return;
    }

    protected void clearScore() {
        playerTurn = 1;
        mainPlayer.resetPlayer();
        secondPlayer.resetPlayer();
        flaggedBombs = 0;
        flagsPut = 0;
        end = false;
    }

    protected void setDifficulty(double d) {
        difficultyValue = d;
    }

    protected void setColumns(int columns) {
        this.columns = columns;
    }

    protected void setRows(int rows) {
        this.rows = rows;
    }

    protected boolean isSolo() {
        return solo;
    }

    protected void setSolo(boolean solo) {
        this.solo = solo;
    }

    protected boolean getEndBool() {
        return end;
    }

    protected void setEndBool(boolean bool) {
        end = bool;
    }

    protected int getAllBombs() {
        return allBombs;
    }

    protected int getFlagsPut() {
        return flagsPut;
    }

    protected int getPlayerTurn() {
        return playerTurn;
    }

    protected void updateTimer() {
        KeyFrame frame= new KeyFrame(Duration.seconds(1), e->{
            seconds++;
            if(isSolo())
                timerText.setText("time: "+seconds);
            else
                timerText.setText("");

            if(getEndBool() == true)
                timeLine.stop();

        });
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.getKeyFrames().add(frame);
        if(timeLine!=null){
            timeLine.stop();
        }
        timeLine.stop();


    }
    protected Timeline getTimeLine() {
        return timeLine;
    }

    protected Text getTimerText() {
        return timerText;
    }

    protected void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    protected void playSound(String sound) {
        if(sound.equals("open"))
            openSound.play();
        if(sound.equals("bomb"))
            bombSound.play();
    }

    protected Player getMainPlayer(){
        return mainPlayer;
    }
    protected Player getSecondPlayer(){
        return secondPlayer;
    }
}



