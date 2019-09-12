package view;

import models.BattleFieldModel;
import models.CoordinatesModel;
import models.GameStateModel;
import models.PlayerModel;
import services.GameService;
import utils.PositionConverter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GameView extends View {

    private GameService gameService;
    private BattleFieldModel playerBattleField;
    private BattleFieldModel enemyBattleField;
    private PlayerModel player;
    private int moves = 0;
    private int playerHits = 0;
    private int enemyHits = 0;
    private boolean playerTurn;

    public GameView(BattleFieldModel playerBattleField, PlayerModel player) {
        this.playerBattleField = playerBattleField;
        this.player = player;
        playerTurn = chooseStartingPlayer();


        gameService = new GameService();
        enemyBattleField = new BattleFieldModel();
    }

    public GameView(GameStateModel save, PlayerModel player){
        gameService = new GameService();

        this.playerBattleField = save.getPlayerBattleField();
        this.enemyBattleField = save.getEnemyBattleField();
        this.moves = save.getMoves();
        this.playerHits = save.getPlayerHits();
        this.enemyHits = save.getEnemyHits();
        this.player = player;
    }

    @Override
    protected void onStart() throws InterruptedException {
        while(true) {
            printFields();
            if (playerTurn){
                playerAttack();
            } else {
                enemyAttack();
            }

            if (playerHits == 20) {
                player.incrementWonGames();
                break;
            } else if (enemyHits == 20){
                player.incrementLoseGames();
                break;
            }

        }
        printFields();
        System.out.println("GAME OVER");

        gameService.updateStats(player);

    }

    private GameStateModel createSaveState() {
        GameStateModel gameState = new GameStateModel();

        gameState.setEnemyBattleField(this.enemyBattleField);
        gameState.setEnemyHits(this.enemyHits);
        gameState.setPlayerBattleField(this.playerBattleField);
        gameState.setPlayerHits(this.playerHits);
        gameState.setMoves(this.moves);
        gameState.setUsername(player.getUsername());

        return  gameState;
    }

    private void printFields() throws InterruptedException{
        clearConsole();
        System.out.print("Moves: " + moves + ".  My hits: " + playerHits + ".  Enemy hits: " + enemyHits +"\n");
        System.out.println("Player Battlefield                                Enemy Battlefield");
        for (int i = 0; i < 11; i++) {
            playerBattleField.printLine(i);
            System.out.print("      ");
            enemyBattleField.printLine(i);
            System.out.println();
        }
    }

    private CoordinatesModel getCoords() {
        System.out.println("S - Save game   q - Quit game");
        System.out.print("Type coords: ");
        String position = getScanner().next();

        if(position.toLowerCase().equals("s")) {
            System.out.print("Saving game: ");
            try {
                gameService.saveGame(createSaveState());
                System.out.println("Save Completed");
                TimeUnit.SECONDS.sleep(2);
                clearConsole();
                printFields();
                return null;
            } catch (IOException ex) {
                System.out.println("Error. Game can't be saved");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (position.toLowerCase().equals("q")) {
            new GameMenuView(player).display();
            return null;
        }
        return PositionConverter.transform(position);
    }

    private boolean getIsShipHit() {
        try {
            System.out.println("Was ship hit? y/n");
            String output = getScanner().next();

            if (!output.matches("^[ynYN]$")) {
                throw new IllegalArgumentException("Wrong answer");
            }

            return output.toLowerCase().equals("y");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            getIsShipHit();
        }
        return false;
    }

    private void playerAttack() throws InterruptedException {
        boolean isShipHit;
        System.out.println("Player Turn.");
        while (true) {
            CoordinatesModel coords = getCoords();

            if (coords == null) {
                continue;
            }

            if (isShipHit = getIsShipHit()) {
                playerHits++;
            }

            enemyBattleField.attack(coords, isShipHit);
            moves++;
            printFields();

            if (!isShipHit) {
                playerTurn = !playerTurn;
                break;
            }
            System.out.println("You hit ship. Your turn again.");
        }
    }

    private void enemyAttack() throws InterruptedException {
        boolean isShipHit;
        System.out.println("Enemy turn.");
        while(true) {
            CoordinatesModel coords = getCoords();
            if(coords == null) {
                continue;
            }

            isShipHit = playerBattleField.isShip(coords);
            if (isShipHit) {
                enemyHits++;
            }

            playerBattleField.attack(coords, isShipHit);
            printFields();

            if(!isShipHit) {
                playerTurn = !playerTurn;
                break;
            }

            System.out.print("Enemy is hitting again: ");
        }
    }

    private boolean chooseStartingPlayer(){
        System.out.println("Who starts the game?");
        System.out.println("1. You    2. Enemy");
        int choice = -1;
        try {
            choice = getScanner().nextInt();
        } catch (Exception ex){
            System.out.println("Wrong answer!");
            return chooseStartingPlayer();
        }
        if (choice == 1) {
            return true;
        } else if (choice == 2){
            return false;
        } else {
            System.out.println("Wrong answer!");
            return chooseStartingPlayer();
        }
    }
}
