package com.company.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class GameReviewGui extends JFrame {

    private final Vector v;
    private final int[] ReviewsPerReview = new int[11];

    public GameReviewGui(Vector v){
        this.v=v;
        setTitle("Reviews Table");
        setSize(600,500);
        setResizable(false);
        setBackground(Color.white);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        find(v);
        GameReviewPanel grp = new GameReviewPanel();
        setContentPane(grp);
        repaint();
    }

    private void find(Vector v) {

        for (int i = 0; i < v.size(); i++) {

            if ((int) v.get(i) == 10) {
                ReviewsPerReview[10] = ReviewsPerReview[10] + 1;
            } else if ((int) v.get(i) == 9) {
                ReviewsPerReview[9] = ReviewsPerReview[9] + 1;
            } else if ((int) v.get(i) == 8) {
                ReviewsPerReview[8] = ReviewsPerReview[8] + 1;
            } else if ((int) v.get(i) == 7) {
                ReviewsPerReview[7] = ReviewsPerReview[7] + 1;
            } else if ((int) v.get(i) == 6) {
                ReviewsPerReview[6] = ReviewsPerReview[6] + 1;
            } else if ((int) v.get(i) == 5) {
                ReviewsPerReview[5] = ReviewsPerReview[5] + 1;
            } else if ((int) v.get(i) == 4) {
                ReviewsPerReview[4] = ReviewsPerReview[4] + 1;
            } else if ((int) v.get(i) == 3) {
                ReviewsPerReview[3] = ReviewsPerReview[3] + 1;
            } else if ((int) v.get(i) == 2) {
                ReviewsPerReview[2] = ReviewsPerReview[2] + 1;
            } else if ((int) v.get(i) == 1) {
                ReviewsPerReview[1] = ReviewsPerReview[1] + 1;
            } else {
                ReviewsPerReview[0] = ReviewsPerReview[0] + 1;
            }
        }

    }

    private class GameReviewPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
                        g.setColor(Color.lightGray);
            g.fillRect(60,100,480,300);

            g.setColor(Color.black);                                            // Ftiaxnei to arxiko panel kai prostheti tous aksones
            g.drawLine(60,400,540,400); //orizontia
            g.drawLine(60,400,60,100); //katheti
            g.drawString("Βαθμολογίες",255,440);
            g.drawString("%",10,250);


            int xvalue=117;
            for(int i=0; i<10; i++){                                            //bazei tis times 0-10 ston orizontio aksona
                g.drawString(String.valueOf(i),xvalue,415);
                xvalue=xvalue+40;
            }
            g.drawString("10",513,415);

            int yvalue=375;
            for(int i=5; i<50; i=i+5){                                          //bazei tis times 5%-50% ston katheto aksona
                g.drawString(i+"%",30,yvalue);
                yvalue=yvalue-30;
            }
            g.drawString("50%",30,105);

            xvalue=98;
            yvalue=98;
            for(int i=0; i<11; i++){                                                        //ftiaxnei ta kikllakia stous aksones(simia ton timon)
                g.drawRoundRect(xvalue,398,4,4,4,4);
                xvalue=xvalue+40;
                g.drawRoundRect(58, yvalue,4,4,4,4);
                yvalue=yvalue+30;
            }
            g.drawRoundRect(540-2,398,4,4,4,4);

            g.setColor(Color.BLUE);

            int x;
            xvalue=101;
            for(int i=0; i<11; i++){                                                        //strogkilopoiei to pososto sto kontinotero akeraio gia na ftiaksei tin steilei
                x = (int) Math.round((double)ReviewsPerReview[i]/v.size()*100);
                g.fillRect(xvalue,400-(x*6) ,38,x*6);
                xvalue+=40;
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Bold", Font.BOLD, 12));

            String tripleDigits;
            xvalue = 108;
            yvalue=390;
            for(int i=0; i<11; i++){                                                        //ektiponei to pososto tis kathe stilis pano stin idia tin stili (me akribeia 3 psifion)
                tripleDigits=Arrays.toString(new double[]{(double) ReviewsPerReview[i]/v.size()*100}).substring(1,5);
                g.drawString(tripleDigits,xvalue,yvalue);
                xvalue+=40;
            }
        }
    }
}


