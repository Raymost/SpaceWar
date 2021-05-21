package spacewar.data;

public class HighScores  {
    public int score;
    private String name;

    public HighScores(int score, String name){
        this.score = score;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return score + " - " + name;
    }
}
