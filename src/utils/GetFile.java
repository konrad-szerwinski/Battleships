package utils;

import java.io.File;
import java.io.IOException;

public class GetFile {

    public static File getFile(String FILE_DIRECTORY, String FILE_NAME) throws IOException {

        File directory = new File(FILE_DIRECTORY);
        File file = new File(FILE_DIRECTORY + FILE_NAME);


        try{
            if(!directory.exists())
                directory.mkdirs();
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return file;
    }

    public static boolean findFile(String FILE_DIRECTORY, String FILE_NAME){
        File directory = new File(FILE_DIRECTORY);
        if(!directory.exists())
            directory.mkdirs();
        return new File(FILE_DIRECTORY, FILE_NAME).exists();
    }
}
