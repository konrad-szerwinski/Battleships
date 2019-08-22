package view;

import models.PlayerModel;
import services.GameService;

import java.util.HashMap;
import java.util.Map;

public class GameMenuView extends View{

    private GameService gameService;

    private PlayerModel player;

    public GameMenuView(PlayerModel player) {
        gameService = new GameService();

        this.player = player;
    }


    @Override
    protected void onStart() throws InterruptedException {
        clearConsole();
        printLogo();
        System.out.println("Logged in as "+ player.getUsername());
        System.out.println("\n1. Start New Game");
        System.out.println("2. Load Last Game");
        System.out.println("3. Show Stats");
        System.out.println("0. Logout");

        try {
            getMenu();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getMenu() throws Exception {
        System.out.print("\nResult: ");
        switch (Menu.valueOf(getScanner().nextInt())) {
            case NEWGAME:
                new SetupGameView(player).display();
                break;
            case LOADGAME:
                new GameView(gameService.loadGame(player.getUsername()), player).display();
                break;
            case PLAYERSTATS:
                new PlayerStatsView(player).display();
            case LOGOUT:
                player = null;
                new StartMenuView().display();
                break;
            default:
                System.out.println("Invalid choice, please try Again.");
                getMenu();
                break;
        }
    }

    private enum Menu {
        NEWGAME(1),
        LOADGAME(2),
        PLAYERSTATS(3),
        LOGOUT(0);

        private int value;
        static Map map = new HashMap<>();

        Menu(int value) {
            this.value = value;
        }

        static {
            for (Menu menu : Menu.values()) {
                map.put(menu.value, menu);
            }
        }

        public static Menu valueOf(int menu) {
            return (Menu) map.get(menu);
        }

        public int getValue() {
            return value;
        }
    }

}
