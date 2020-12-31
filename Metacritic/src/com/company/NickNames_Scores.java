package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NickNames_Scores extends Thread {

    URL url = null;
    BufferedReader br;
    URLConnection uc;
    String urlString;
    Vector NickName = new Vector();
    Vector Review = new Vector();
    Logger log = null;


    synchronized public Vector getNickName() {
        return NickName;
    }

    synchronized public void run(){

        System.out.println(Thread.currentThread());
        ConnectToMetacritic();
        try {
            GetLines();

        } catch (IOException e) {
            System.out.println("Inside Void Thread Class"+e);
        }
    }

    synchronized public Vector getReview() {
        return Review;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public NickNames_Scores(String urlString) {
        this.urlString = urlString;
    }

    synchronized void ConnectToMetacritic() {

        try {

            url = new URL(urlString);

            uc = url.openConnection();
//            uc.connect();
//            uc = url.openConnection();
            uc.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc.getInputStream();

            br = new BufferedReader(new InputStreamReader(uc.getInputStream()));

        } catch (Exception e) {
             System.out.println("Exception Inside Method ConnectToMetacritic "+e);
             ConnectToMetacritic();
        }

    }

    synchronized  void GetLines() throws IOException {

        String line;
        long Start = 2000;
        long end = 7500;
        long numLine=0;

        String nextUrl;
        int startUrl=0;
        int endUrl=0;

        while ((line = br.readLine()) != null) {

           if(numLine>=Start && numLine<=end){

               if (line.contains("<a href=\"/user/") && !line.contains("All")) {

                        int StartOfName=line.trim().indexOf("\">");
                        int EndOfName=line.trim().indexOf("</");
                        String y=line.trim().substring(StartOfName+2,EndOfName);
                        NickName.add(y);

               }
               else if(line.contains("<div class=\"metascore_w user") && !line.contains(".")){

                        String numOnly=line.replaceAll("[^0-9]","");
                        Review.add(Integer.parseInt(numOnly));
                    }
               else if (line.contains("\"action\" rel=\"next\" href=\"/game")){

                        startUrl=line.trim().indexOf("/game");
                        endUrl=line.trim().indexOf("\"><span");
                        nextUrl=line.trim().substring(startUrl,endUrl);
                        System.out.println(nextUrl);
                        nextUrl="https://www.metacritic.com"+nextUrl;
                        System.out.println(nextUrl);
                        setUrlString(nextUrl);

                        ConnectToMetacritic();
                        GetLines();

                    }

           }
            numLine++;
        }

//        System.out.println(NickName);
//        System.out.println(NickName.size());
//        System.out.println(Review);
//        System.out.println(Review.size());

    }
}



