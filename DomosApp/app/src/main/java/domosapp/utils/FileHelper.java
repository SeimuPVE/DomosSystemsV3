package domosapp.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    //TODO : junit tests

    /**
     *
     * @param file
     * @param username
     * @param password
     */
    public static void createFile(String file, String username, String password) {
        try {
            PrintWriter printWriter = new PrintWriter(file);

            printWriter.println("ip_address=192.168.1.1");
            printWriter.println("port=14");
            printWriter.println("username="+username);
            printWriter.println("password="+password);

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file
     * @param ip
     * @param port
     * @param username
     * @param password
     */
    public static void createFile(String file, String ip, String port, String username, String password) {
        try {
            PrintWriter printWriter = new PrintWriter(file);

            printWriter.println("ip_address="+ip);
            printWriter.println("port="+port);
            printWriter.println("username="+username);
            printWriter.println("password="+password);

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file
     * @return
     */
    public static List<String> getInfos(String file) {
        List<String> list = new ArrayList<>();
        try {
            FileInputStream fstream = null;
            fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] split = strLine.split("=");
                list.add(split[1]);
                System.out.println(strLine);
                System.out.println(split[1]);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    // For Test
    public static void main(String[] args) {
        createFile("test.txt","192.168.1.1", "14", "psksh", "123456");
    }


}
