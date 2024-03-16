package Models;

public class Performance {
    private double runsScored;
    private double ballsFaced;
    private double fours;
    private double sixes;
    private double wicketsTaken;
    private double ballsBowled;
    private double runsGave;

    public Performance(double runsScored, double ballsFaced, double fours, double sixes, double wicketsTaken, double ballsBowled, double runsGave) {
        this.runsScored = runsScored;
        this.ballsFaced = ballsFaced;
        this.fours = fours;
        this.sixes = sixes;
        this.wicketsTaken = wicketsTaken;
        this.ballsBowled = ballsBowled;
        this.runsGave = runsGave;
    }

    public double getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(double runsScored) {
        this.runsScored = runsScored;
    }

    public double getBallsFaced() {
        return ballsFaced;
    }

    public void setBallsFaced(double ballsFaced) {
        this.ballsFaced = ballsFaced;
    }

    public double getFours() {
        return fours;
    }

    public void setFours(double fours) {
        this.fours = fours;
    }

    public double getSixes() {
        return sixes;
    }

    public void setSixes(double sixes) {
        this.sixes = sixes;
    }

    public double getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(double wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public double getBallsBowled() {
        return ballsBowled;
    }

    public void setBallsBowled(double ballsBowled) {
        this.ballsBowled = ballsBowled;
    }

    public double getRunsGave() {
        return runsGave;
    }

    public void setRunsGave(double runsGave) {
        this.runsGave = runsGave;
    }
}