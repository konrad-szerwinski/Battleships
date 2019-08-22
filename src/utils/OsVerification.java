package utils;

public class OsVerification {
    public static String osVerify(){
        final String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win")){
            return "windows";
        }

        else if(os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0 ){
            return "unix";
        }

        else return "mac";
    }
}
