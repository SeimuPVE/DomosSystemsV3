package msc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


public class Logger {
    private static String filepath;

    public static String LevelSEVERE = "SEVERE";
    public static String LevelWARNING = "WARNING";
    public static String LevelINFO = "INFO";
    public static String LevelCONFIG = "CONFIG";
    public static String LevelFINE = "FINE";

    public Logger(String filepath) {
        Logger.filepath = filepath;
    }

    public static void setFilepath(String filepath) {
        Logger.filepath = filepath;
    }

    public static void log(String level, String classname, String message) {
        try {
            int i = 0;
            String msg = level + ", " + classname + " : " + message;
            FileWriter fileWriter = new FileWriter(filepath, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for(i = 0; i < msg.length(); i++)
                writer.append(msg.charAt(i));
            writer.append('\n');

            System.out.println(msg);

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
