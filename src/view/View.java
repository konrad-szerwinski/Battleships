package view;

import utils.OsVerification;

import java.io.IOException;
import java.util.Scanner;

public abstract class View extends OsVerification {
    Scanner scanner = new Scanner(System.in);

    public void display()
    {
        try{
            onStart();
        } catch (InterruptedException ex){
            closeApp();
        }
    }

    protected abstract void onStart() throws InterruptedException;

    void onStop() throws InterruptedException{
        clearConsole();
    }

    protected static void closeApp(){
        Runtime.getRuntime().exit(0);
    }

    protected static void clearConsole() throws InterruptedException{
        try{
            if(osVerify().equals("windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else if(osVerify().equals("unix") || osVerify().equals("mac")){
                Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
            }
        }
        catch (IOException ex)
        {
            System.out.println("Clear screen error");
            Runtime.getRuntime().exit(-1);
        }
    }

    public String[] getUsernameAndPassword() {
        String[] data = new String[2];

        System.out.print("Username: ");
        data[0] = getScanner().nextLine();
        System.out.print("Password: ");
        data[1] = getScanner().nextLine();

        return data;
    }

    public Scanner getScanner(){
        return scanner;
    }

    public static void printLogo(){
        System.out.println("__________         __    __  .__           _________.__    .__              \n" +
                "\\______   \\_____ _/  |__/  |_|  |   ____  /   _____/|  |__ |__|_____  ______\n" +
                " |    |  _/\\__  \\\\   __\\   __\\  | _/ __ \\ \\_____  \\ |  |  \\|  \\____ \\/  ___/\n" +
                " |    |   \\ / __ \\|  |  |  | |  |_\\  ___/ /        \\|   Y  \\  |  |_> >___ \\ \n" +
                " |______  /(____  /__|  |__| |____/\\___  >_______  /|___|  /__|   __/____  >\n" +
                "        \\/      \\/                     \\/        \\/      \\/   |__|       \\/\n" +
                "                        )_\n" +
                "                   _____|_|_____+___\n" +
                "                   |    X    X  X  |\n" +
                "         \\-----|---------------------------------------/\n" +
                "          \\   O       O       O       O       O       /\n" +
                "/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\ \n");
    }




}
