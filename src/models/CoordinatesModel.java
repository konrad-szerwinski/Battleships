package models;

public class CoordinatesModel {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public CoordinatesModel(String x, int y) {
        if(x.matches("^[A-J]$")) {
            setX(x.charAt(0) - 'A' + 1);
            setY(y);
        } else if(x.matches("^[a-j]$")) {
            setX(x.charAt(0) - 'a' + 1);
            setY(y);
        }
    }

    public CoordinatesModel(int x, int y) {
        this.setY(y);
        this.setX(x);
    }

    public String display() {
        return String.valueOf((char) (x + 'A' - 1)) + y;
    }
}

