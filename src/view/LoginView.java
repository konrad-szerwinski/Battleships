package view;

import models.PlayerModel;
import services.AuthService;

public class LoginView extends View {

    private AuthService authService = new AuthService();

    @Override
    protected void onStart() throws InterruptedException {

        String username, password;

        clearConsole();
        printLogo();

        System.out.print("Username: ");
        username = getScanner().nextLine();
        System.out.print("Password: ");
        password = getScanner().nextLine();

        try{
            authService.login(username, password);
            System.out.println("Logged In");

            new GameMenuView(authService.loadProfile(username, password)).display();


        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
