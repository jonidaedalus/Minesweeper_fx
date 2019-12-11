package sample;

public class Player {

    private String name = "";
    private int score = 0;
    private int flaggedBombs = 0;

    protected int getFlaggedBombs() {
        return flaggedBombs;
    }

    protected void setFlaggedBombs(int flaggedBombs) {
        this.flaggedBombs = flaggedBombs;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected int getScore() {
        return score;
    }

    protected void setScore(int score) {
        this.score = score;
    }
    protected void incrementScore(){
        score++;
    }
    protected void decrementScore(){
        score--;
    }
    protected void incrementFlaggedBombs(){
        flaggedBombs++;
    }
    protected void decrementFlaggedBombs(){
        flaggedBombs--;
    }

    protected void resetPlayer(){
        score = 0;
        flaggedBombs = 0;
//        name = "";
    }


}
