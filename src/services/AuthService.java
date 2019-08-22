package services;

import models.PlayerModel;
import utils.GetFile;
import view.StartMenuView;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class AuthService extends GetFile {
    private static final String FILE_DIRECTORY = "./data/profiles/";
    private static final String FILE_NAME_SUFIX= "_PROFILE.txt";

    public boolean login(String username, String password) throws InterruptedException {

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Incorrect username or password!");
            wait(2000);
            new StartMenuView().display();
        }

        try{

            if(!isUserExist(username)){
                System.out.println("User does not exist");
                TimeUnit.SECONDS.sleep(1);
                new StartMenuView().display();
            } else if(!isDataValid(username, password)){
                System.out.println("Invalid password");
                TimeUnit.SECONDS.sleep(1);
                new StartMenuView().display();
            }

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean register(String username, String password) throws InterruptedException {

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Incorrect username or password!");
            TimeUnit.SECONDS.sleep(1);
            new StartMenuView().display();
        }

        try{
            if(isUserExist(username)) {
                System.out.println("Username already owned");
                TimeUnit.SECONDS.sleep(1);
            } else {
                PlayerModel player = new PlayerModel(username, password);
                return saveProfile(player);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return false;

    }

//    private boolean isDataValid(final String data) throws IOException {
//        try(BufferedReader reader = new BufferedReader(new FileReader(getFile(FILE_DIRECTORY, FILE_NAME_SUFIX)))){
//            String line;
//            while ((line = reader.readLine()) != null){
//                if(data.equals(line))
//                    return true;
//            }
//        }
//
//        return false;
//    }

    private boolean isDataValid(final String username, final  String password) throws IOException {
        try{
            File file;
            FileInputStream input = new FileInputStream(file = getFile(FILE_DIRECTORY, username + FILE_NAME_SUFIX));
            if(file.length() != 0){
                ObjectInputStream object = new ObjectInputStream(input);
                PlayerModel tempPlayer = (PlayerModel) object.readObject();
                if(tempPlayer.getUsername().equals(username) && tempPlayer.getPassword().equals(password)){
                    return true;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean isUserExist(String username){
        if(findFile(FILE_DIRECTORY, username + FILE_NAME_SUFIX))
            return true;
        return false;
    }


    private boolean saveProfile(PlayerModel player){
        try
        {
            File profileSave = getFile(FILE_DIRECTORY, player.getUsername() + FILE_NAME_SUFIX);
            FileOutputStream fileOutputStream = new FileOutputStream(profileSave);
            ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);
            output.writeObject(player);
            output.close();
            fileOutputStream.close();
            return true;
        } catch (IOException ex){
            System.out.println("Can not save profile");
            ex.printStackTrace();
        }
        return false;
    }

    public PlayerModel loadProfile(String username, String password) {
        PlayerModel player;

        try {
            File profileSave = getFile(FILE_DIRECTORY, username + FILE_NAME_SUFIX);
            FileInputStream fileInputStream = new FileInputStream(profileSave);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            player = (PlayerModel) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return player;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public boolean updateProfile(PlayerModel player) {
        return  saveProfile(player);
    }
}
