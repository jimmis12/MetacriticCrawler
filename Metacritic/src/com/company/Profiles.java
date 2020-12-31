package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import java.util.Vector;


public class Profiles extends Thread {
    private String ProfileUrl;
    private BufferedReader br;
    private URLConnection uc;
    private URL url;
    private int Max;
    private int Min;
    private Vector ReviewPerNickName = new Vector();
    private Vector TitlesOfGamesPerNickName = new Vector();
    private int time=1000;

    public Vector getReviewPerNickName() {
        return ReviewPerNickName;
    }

    private WriteToFile n= new WriteToFile();

    synchronized public void run() {

        System.out.println(Thread.currentThread());
        ConnectToProfiles();
        try {
            GetLines();
        } catch (IOException e) {
            e.printStackTrace();
        }
        n.Newline();
    }

    public void setProfileUrl(String profileUrl) {
        ProfileUrl = profileUrl;
    }

    public String getProfileUrl() {
        return ProfileUrl;
    }

    Profiles(String ProfileUrl) throws IOException {
        this.ProfileUrl = ProfileUrl;

    }

    synchronized void ConnectToProfiles()  {

        try {

            url = new URL("https://www.metacritic.com/user/"+ProfileUrl);


            uc = url.openConnection();
//            uc.connect();
//            uc = url.openConnection();
            uc.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc.getInputStream();

            br = new BufferedReader(new InputStreamReader(uc.getInputStream()));

        } catch (Exception e) {
            System.out.println("Exception Inside Method ConnectToProfiles " + e);
            try{
                sleep(time);
            }catch(Exception er){
                System.out.println("ErrorHandler " + er);

            }
            ConnectToProfiles();
        }

    }


    synchronized void GetLines() throws IOException {

        String line=null;
        long Start = 1000;
        long End = 2200;
        long numLine = 0;
        int counter=0;
        String Prof;

        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                System.out.println(e);
            }


            if (numLine >= Start && numLine <=End) {

                if(line.contains("<div class=\"metascore_w user")){
                    counter++;

                    String numOnly=line.replaceAll("[^0-9]","");
                    if(counter==1){
                        Max=Integer.parseInt(numOnly);
                        n.Max(Max);
                    }
                    else if(counter==2){
                        Min=Integer.parseInt(numOnly);
                        n.Min(Min);
                    }
                    else {
                        ReviewPerNickName.add(Integer.parseInt(numOnly));
                        n.ReviewPerNickName(numOnly);
                    }
                    if (counter == 12)
                        counter = 0;

                }else if(line.contains("<div class=\"product_title\"") && line.contains("<a href")){

                    String nameOfGame;
                    int StartIndex = line.trim().lastIndexOf("\"");
                    int endIndex = line.trim().indexOf("</a>");
                    nameOfGame = line.trim().substring(StartIndex + 2, endIndex);
                    TitlesOfGamesPerNickName.add(nameOfGame);
                    n.TitlesOfGamesPerNickName(nameOfGame);

                }
                else if(line.contains("<a class=\"action\" rel=\"next\"")){

                    int StartIndex = line.trim().indexOf("/user");
                    int endIndex = line.trim().indexOf("><span");
                    Prof = line.trim().substring(StartIndex + 6, endIndex - 1);
                    setProfileUrl(Prof);
                    System.out.println("https://www.metacritic.com/user/"+Prof);
                    ConnectToProfiles();
                    GetLines();
                }
            }
            numLine++;


        }
//        n.Newline();
    }
}
