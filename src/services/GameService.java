package services;

import models.GameStateModel;
import models.PlayerModel;
import utils.GetFile;
import java.io.*;


public class GameService extends GetFile {

    private static final String FILE_DESTINATION = "data/saves/";
    private static final String FILE_SUFFIX = "_save.txt";

    public void saveGame(GameStateModel gameState) throws IOException {
        final String fileName = gameState.getUsername() + FILE_SUFFIX;
        File save = getFile(FILE_DESTINATION, fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(save,false))) {
            out.writeObject(gameState);
        }
    }

    public GameStateModel loadGame(String username) throws Exception {
        final String fileName = username + FILE_SUFFIX;
        File save = getFile(FILE_DESTINATION, fileName);
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(save))) {
            return (GameStateModel) input.readObject();
        }

    }

    public void updateStats(PlayerModel player) {
        AuthService auth = new AuthService();

        auth.updateProfile(player);
    }

}
