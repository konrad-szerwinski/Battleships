package view;

import models.PlayerModel;

public class PlayerStatsView extends View {

    PlayerModel player;

    public PlayerStatsView (PlayerModel player) {
        this.player = player;
    }


    @Override
    protected void onStart() throws InterruptedException {
        clearConsole();
        printLogo();
        System.out.println("Logged in as " + player.getUsername());
        System.out.println("Games played: " + player.getPlayedGames() + "  Won games: " + player.getWonGames() + "  Lost Games: " + player.getLoseGames());
        System.out.println("Press any key to return to menu: ");
        getScanner().nextLine();
        new GameMenuView(player).display();
    }
}
