package models;


import java.io.Serializable;

public class BattleFieldModel implements Serializable {

    private FieldModel[][] fields;


    public BattleFieldModel() {
        fields = new FieldModel[11][11];
        for (int i = 0; i<11; i++) {
            for (int j = 0; j<11; j++) {
                FieldModel field = new FieldModel(i,j);
                fields[i][j] = field;
            }
        }
    }

    public FieldModel[][] getFields() {
        return fields;
    }

    public void print(){
        for (int i = 0; i< 11; i++)
        {
            printLine(i);
            System.out.print("\n");
        }
    }

    public void printLine(int number) {
        for (int i = 0; i < 11; i++) {
            if (number == 0 && i == 0) {
                System.out.print("\\");
            } else if (number == 0) {
                char widthSigh = (char) (fields[number][i].getY() + 64);
                System.out.print(widthSigh);
            } else if (i == 0) {
                System.out.print(fields[number][i].getX());
            } else {
                if (fields[i][number].isShip() && !fields[i][number].isHit())
                    System.out.print("\u25A0");

                else if (!fields[i][number].isShip() && fields[i][number].isHit())
                    System.out.print("*");

                else if (!fields[i][number].isShip() && !fields[i][number].isHit())
                    System.out.print(" ");

                else if (fields[i][number].isShip() && fields[i][number].isHit())
                    System.out.print("#");
            }

            if (number >= 10 && i == 0)
                System.out.print("| ");

            else
                System.out.print(" | ");
        }
    }

    public void attack(CoordinatesModel coords, boolean isShip) {
        fields[coords.getX()][coords.getY()].setHit(true);
        fields[coords.getX()][coords.getY()].setShip(isShip);
    }

    public boolean isShip(CoordinatesModel coordinates) {
        return fields[coordinates.getX()][coordinates.getY()].isShip();
    }

    public boolean isBlocked(CoordinatesModel coordinates) {
        return fields[coordinates.getX()][coordinates.getY()].isBlocked();
    }

}
