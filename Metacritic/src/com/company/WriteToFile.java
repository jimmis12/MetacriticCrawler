package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteToFile {
    private final PrintWriter out  = new PrintWriter(new FileWriter("C:\\Users\\Jimbo\\Desktop\\GamesPerProfile.txt",true));
    private BufferedWriter bf = new BufferedWriter(out);;

    public WriteToFile() throws IOException {

    }

    void ReviewPerNickName(String Data){
        try {
            bf.write(" "+Data+",");
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void TitlesOfGamesPerNickName(String Data){
        try {

            bf.write(" "+Data+" ->");
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void Newline(){
        try {
            bf.write("\n");
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void Max(int max){
        try {
            bf.write(" {"+max+"}");
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void Min(int min){
        try {
            bf.write("("+min+")");
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
