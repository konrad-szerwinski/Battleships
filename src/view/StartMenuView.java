package view;

import java.util.HashMap;
import java.util.Map;

public class StartMenuView extends View {

    @Override
    protected void onStart() throws InterruptedException {
        clearConsole();
        printLogo();
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");

        switchMenu();
    }

    private void switchMenu(){
        System.out.print("\nResult: ");
        switch(Menu.valueOf(getScanner().nextInt())){
            case LOGIN:
                new LoginView().display();
                break;
            case REGISTER:
                new RegisterView().display();
                break;
            case EXIT:
                closeApp();
                break;
            default:
                System.out.println("Bledna opcja");
        }
    }

    private enum Menu {
        LOGIN(1),
        REGISTER(2),
        EXIT(0);

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