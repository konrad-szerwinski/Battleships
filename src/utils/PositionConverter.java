package utils;
import models.CoordinatesModel;

public class PositionConverter {
    public static CoordinatesModel transform(String position) throws IllegalArgumentException{
        if (isValid(position)) {
            final String x = position.substring(0,1);
            final int y = Integer.valueOf(position.substring(1));
            return new CoordinatesModel(x,y);
        }

        throw new IllegalArgumentException("Can not convert position");
    }

    private static boolean isValid(String position) {
        return position.matches("^([A-J]|[a-j])[1-9][0]?$");
    }
}
