package models;

import java.io.Serializable;

public class GameStateModel implements Serializable {

    private int playerHits;
    private int enemyHits;
    private int moves;
    private String username;
    private BattleFieldModel playerBattleField;
    private BattleFieldModel enemyBattleField;

    public int getPlayerHits() {
        return playerHits;
    }

    public void setPlayerHits(int playerHits) {
        this.playerHits = playerHits;
    }

    public int getEnemyHits() {
        return enemyHits;
    }

    public void setEnemyHits(int enemyHits) {
        this.enemyHits = enemyHits;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BattleFieldModel getPlayerBattleField() {
        return playerBattleField;
    }

    public void setPlayerBattleField(BattleFieldModel playerBattleField) {
        this.playerBattleField = playerBattleField;
    }

    public BattleFieldModel getEnemyBattleField() {
        return enemyBattleField;
    }

    public void setEnemyBattleField(BattleFieldModel enemyBattleField) {
        this.enemyBattleField = enemyBattleField;
    }
}
