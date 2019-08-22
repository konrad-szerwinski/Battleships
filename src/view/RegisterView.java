package view;

import services.AuthService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RegisterView extends View {

    AuthService authService = new AuthService();

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
            if(authService.register(username, password)) {
                System.out.println("User successfully registered");
                TimeUnit.SECONDS.sleep(1);
            }
            new StartMenuView().display();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
