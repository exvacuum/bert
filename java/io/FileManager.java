package io;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.io.*;

public class FileManager {
    public static void main(String[] args) {
        new FileManager();
    }

    public FileManager(){
        try {
            BufferedReader in = new BufferedReader( new InputStreamReader(System.in));
            File f = new File(getClass().getClassLoader().getResource("filename.txt").getFile());
            PrintWriter prout = new PrintWriter(new BufferedWriter( new FileWriter(f)));


            System.out.println("FILE WRITER: TYPE LINES TO BE ADDED TO THE FILE");
            while (true){
                String s = in.readLine();
                prout.println(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
