package models;

public class Arc {
    private int summit;
    private int distance;

    public Arc(int summit, int distance) {
        this.summit = summit;
        this.distance = distance;
    }

    public int getSummit() {
        return summit;
    }

    public int getDistance() {
        return distance;
    }

    public void setSummit(int summit) {
        this.summit = summit;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
