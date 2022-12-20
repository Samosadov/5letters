package com.company;

import java.io.*;

public class Main {

    public static boolean check(String word) {
        char[] ch = {'у', 'р', 'з', 'б', 'в', 'е', 'н', 'п', 'г', 'л', 'и', 'с', 'т'};
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < ch.length; j++) {
                if (word.charAt(i) == ch[j])
                    return false;
            }
        }
        return true;
    }

    public static void init() throws IOException{

        BufferedReader reader = new BufferedReader(new FileReader("c:/temp/russian_nouns.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("c:/temp/5letters.txt"));
        String word = null;
        while ((word = reader.readLine()) != null) {
            if (word.length() == 5) writer.write(word + "\n");
        }
        writer.flush();
        writer.close();
        reader.close();
        System.out.println("Job is done.");
    }

    public static void proc() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("c:/temp/5letters.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("c:/temp/result.txt"));
        String word = null;
        while ((word = reader.readLine()) != null) {
            if (word.charAt(0) == 'к' && check(word)) writer.write(word + "\n");
        }
        writer.flush();
        writer.close();
        reader.close();
        System.out.println("Done.");
    }

    public static void main(String[] args) throws IOException {
	// write your code here
//        init();
        proc();
    }
}
