package view;

import models.BattleFieldModel;
import models.CoordinatesModel;
import models.PlayerModel;
import models.ShipModel;
import utils.PositionConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SetupGameView extends View{

    private BattleFieldModel playerBattlefield;

    private PlayerModel player;


    private static List<ShipModel> ships = new ArrayList<>(Arrays.asList(
            new ShipModel(4),
            new ShipModel(3),
            new ShipModel(3),
            new ShipModel(2),
            new ShipModel(2),
            new ShipModel(2),
            new ShipModel(1),
            new ShipModel(1),
            new ShipModel(1),
            new ShipModel(1)
    ));

    SetupGameView(PlayerModel player) {
        this.player = player;
        playerBattlefield = new BattleFieldModel();
    }


    @Override
    protected void onStart() throws InterruptedException {
        clearConsole();
        System.out.println("Choose field \n");
        for (ShipModel ship : ships) {
            clearConsole();
            playerBattlefield.print();
            boolean isShipPlaced;
            do {
                CoordinatesModel coords;
                try {
                    coords = getCoordinatesForShip(ship);
                    List<CoordinatesModel[]> availables = getAvailables(playerBattlefield, ship, coords);
                    if( availables == null || availables.isEmpty()) {
                        throw new IllegalArgumentException("Field already taken. Choose the other one.");
                    }

                    if(ship.getWidth() == 1) {
                        placeShip(playerBattlefield, ship, availables);
                        break;
                    }

                    System.out.println("Avaliable ship placements: ");
                    int howMany = 0;
                    for(CoordinatesModel[] avaliableArray : availables) {
                        if(avaliableArray.length > 0 ) {
                            howMany++;
                            for (int i = 0; i< avaliableArray.length; i++) {
                                if ( i==0 ) {
                                    System.out.print(howMany + ". Coords: ");
                                }

                                System.out.print(avaliableArray[i].display() + ", ");

                                if ( i == avaliableArray.length - 1) {
                                    System.out.print("\n");
                                }
                            }
                        } else {
                            System.out.println("Can not plase ship here, choose other field.");
                        }
                    }
                    placeShip(playerBattlefield, ship, availables);
                    isShipPlaced = true;

                } catch (IllegalArgumentException ex) {
                    isShipPlaced = false;
                    System.out.println(ex.getMessage());
                }

            } while (!isShipPlaced);
        }
        clearConsole();
        playerBattlefield.print();
        System.out.println("All ships placed :)");
        TimeUnit.SECONDS.sleep(1);

        new GameView(playerBattlefield, player).onStart();
    }

    private CoordinatesModel getCoordinatesForShip(ShipModel ship){
        System.out.print("\nAdd " + ship.getWidth() + " segment ship \n\nResult: ");
        return PositionConverter.transform(getScanner().next());
    }

    private List<CoordinatesModel[]> getAvailables(BattleFieldModel battlefield, ShipModel ship, CoordinatesModel coords) {
        List<CoordinatesModel[]> avaliables = new ArrayList<>();

        if (battlefield.getFields()[coords.getX()][coords.getY()].isShip()
            || battlefield.getFields()[coords.getX()][coords.getY()].isBlocked()) {
            return null;
        }

        if(ship.getWidth() == 1) {
            CoordinatesModel coordinatesModel = new CoordinatesModel(coords.getX(),coords.getY());
            avaliables.add(new CoordinatesModel[]{coordinatesModel});
            return avaliables;
        }

        for (int i = 1; i < 5; i++) {
            try {
                int beginPosition;
                int endPosition;
                switch(i) {
                    case 1: {
                        endPosition = coords.getY();
                        beginPosition = coords.getY() - ship.getWidth() + 1;
                        break;
                    }
                    case 2: {
                        beginPosition = coords.getX();
                        endPosition = coords.getX() + ship.getWidth() - 1;
                        break;
                    }
                    case 3: {
                        beginPosition = coords.getY();
                        endPosition = coords.getY() + ship.getWidth() - 1;
                        break;
                    }
                    default: {
                        endPosition = coords.getX();
                        beginPosition = coords.getX() - ship.getWidth() + 1;
                    }
                }

                if (endPosition <= 0 || endPosition > 10 || beginPosition <= 0 || beginPosition > 10) {
                    throw new ArrayIndexOutOfBoundsException();
                }

                CoordinatesModel[] coordsArray = new CoordinatesModel[ship.getWidth()];
                int index = 0;
                for (int j = beginPosition; j<= endPosition; j++) {
                    CoordinatesModel available;
                    if (i%2 != 1) {
                        if (battlefield.getFields()[j][coords.getY()].isShip()) {
                            throw new ArrayIndexOutOfBoundsException();
                        }

                        available = new CoordinatesModel(j, coords.getY());
                    } else {
                        if (battlefield.getFields()[coords.getX()][j].isShip()) {
                            throw new ArrayIndexOutOfBoundsException();
                        }

                        available = new CoordinatesModel(coords.getX(), j);

                    }
                    coordsArray[index] = available;
                    index++;
                }

                avaliables.add(coordsArray);
            }catch (ArrayIndexOutOfBoundsException e){}
        }
        return avaliables;
    }

    private void placeShip(BattleFieldModel battlefiel, ShipModel ship, List<CoordinatesModel[]> avaliables) {
        int selectCoords = 1;
        if(ship.getWidth() != 1) {
            selectCoords = getScanner().nextInt();
            if(selectCoords < 0 || selectCoords > avaliables.size()) {
                throw new IllegalArgumentException("No one choice selected. Try again.");
            }
        }

        for (CoordinatesModel coords : avaliables.get(selectCoords -1)) {
            battlefiel.getFields()[coords.getX()][coords.getY()].setShip(true);
        }
    }

}
