package com.company;

import com.company.GUI.GameReviewGui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;



public class Main {

    public static void main(String[] args) throws IOException {
        Vector insideNickName = new Vector();
        Vector insideReviews = new Vector();
        Vector AllReviews = new Vector();


        long start = System.nanoTime();
        long elapsedTime = 0;


        String ar [] = new String[3];
        ar[0]="https://www.metacritic.com/game/playstation-4/predator-hunting-grounds/user-reviews"; //https://www.metacritic.com/game/playstation-4/predator-hunting-grounds/user-reviews
        ar[1]="https://www.metacritic.com/game/playstation-4/the-last-of-us-remastered/user-reviews"; //https://www.metacritic.com/game/playstation-4/the-last-of-us-remastered/user-reviews
        ar[2]="https://www.metacritic.com/game/playstation-4/ghost-of-tsushima/user-reviews";

        NickNames_Scores temp = null;

            for (int i=0; i<ar.length-2; i++){

                 temp = new NickNames_Scores(ar[i]);
                 temp.start();

                System.out.println("Thread name: "+Thread.currentThread());
                try {
                    temp.join();
                    insideNickName.addAll(temp.getNickName());
                    insideReviews.addAll(temp.getReview());
                    System.out.println("Join: "+Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        Collections.sort(insideReviews);

        GameReviewGui gui = new GameReviewGui(insideReviews);

        Profiles pr;

        for(int i=0; i<insideNickName.size(); i++){

            pr = new Profiles((String)insideNickName.get(i));
            pr.start();
            try{
                pr.join();
                AllReviews.addAll(pr.getReviewPerNickName());
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        Collections.sort(AllReviews);
        GameReviewGui gui2 = new GameReviewGui(AllReviews);


        int sum=0;
        for (int i=0; i<insideReviews.size(); i++){
            sum=sum+ ((int) insideReviews.get(i));
        }


        if(insideReviews.size()%2==1){
            System.out.println("Median 50% "+insideReviews.get(insideReviews.size()/2+1));
        }else{
            int k=(int)insideReviews.get(insideReviews.size()/2)+(int)insideReviews.get(insideReviews.size()/2+1);
            System.out.println("Median 50% "+k/2);
        }

        System.out.println("Mesos oros "+(float)sum/insideReviews.size());

        System.out.println(insideReviews);
        System.out.println(insideNickName.size());
        elapsedTime = System.nanoTime() - start;
        System.out.println("Time in seconds: "+elapsedTime/1000000000);
    }
}
