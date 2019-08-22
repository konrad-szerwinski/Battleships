package models;

import java.io.Serializable;

public class PlayerModel implements Serializable {
    private String username;
    private String password;
    private int playedGames = 0;
    private int wonGames = 0;
    private int loseGames = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }

    public int getLoseGames() {
        return loseGames;
    }

    public void setLoseGames(int loseGames) {
        this.loseGames = loseGames;
    }

    public PlayerModel(){}

    public PlayerModel(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void incrementWonGames() {
        wonGames++;
        incrementPlayedGames();
    }

    public void incrementLoseGames() {
        loseGames++;
        incrementPlayedGames();
    }

    private void incrementPlayedGames() {
        playedGames++;
    }

}
