package view.views.components;

import controller.ActionController;
import view.View;

import java.awt.*;
import java.util.ArrayList;

public class MatchHistoryGraphic extends View {
    private final ArrayList<Integer> victoryChanges;
    private final ArrayList<Integer> lossChanges;
    private int lostFirstY;
    private int lostLastY;
    private int victoryFirstY;
    private int victoryLastY;
    private  int xScale;
    private  int yScale;
    private final int axisOffset=1;
    private int index;

    public MatchHistoryGraphic(ArrayList<Integer> victoryChanges, ArrayList<Integer> lossChanges) {
        this.victoryChanges = victoryChanges;
        this.lossChanges= lossChanges;

    }



    public int getxScale() {
        return xScale;
    }

    public int getyScale() {
        return yScale;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int max=0;
        // set the x increment to be relative to the number of elements in the array
        int incrementX = (this.getWidth()-axisOffset) / (victoryChanges.size()-1);
        int xSeparator=0;
        int ySeparator=0;

       for(int i: victoryChanges) {
           if (i > max) {
              max = i;
           }
       }
        for(int i: lossChanges) {
            if (i > max) {
                max = i;
            }
        }
        xScale= incrementX;
        System.out.println(xScale);
        // set the y increment to be relative to the max number
        int incrementY= (this.getHeight()-axisOffset)/max;
        if (incrementY<1) {
            incrementY=max/(this.getHeight()-axisOffset);
        }

        for (int i = 0; i < victoryChanges.size()-1; i++) {
            //put the values that need to be drawn in the first and last y
            victoryFirstY = (victoryChanges.get(i)) * incrementY;
            victoryLastY = (victoryChanges.get(i + 1)) *incrementY;

           /* //put the values that need to be drawn in the first and last y
            lostFirstY=(lossChanges.get(i)) * incrementY;
            lostLastY=(lossChanges.get(i+1)) * incrementY;*/

            //y axis
            g.drawLine(0,0,0,getHeight()-axisOffset);
            // x axis
            g.drawLine(0,getHeight()-axisOffset,getWidth(),getHeight()-axisOffset);

            //paint x the axis separator
            g.drawLine(xSeparator,getHeight()-axisOffset,xSeparator,getHeight()-(5*axisOffset));
            xSeparator+=incrementX;



            //change the color of the line
            g.setColor(Color.GREEN);
            //paint wins graphic line
            g.drawLine(i*incrementX, this.getHeight()- victoryFirstY, (i + 1)*incrementX , this.getHeight()- victoryLastY);


            // change the color of the line


            // set the color back to black
            g.setColor(Color.BLACK);
        }
        for (int i = 0; i < lossChanges.size()-1; i++) {
            //paint the axis values
            lostFirstY=(lossChanges.get(i)) * incrementY;
            lostLastY=(lossChanges.get(i+1)) * incrementY;

            //y axis
            g.drawLine(0,0,0,getHeight()-axisOffset);
            // x axis
            g.drawLine(0,getHeight()-axisOffset,getWidth(),getHeight()-axisOffset);

            //paint x the axis separator
            g.drawLine(xSeparator,getHeight()-axisOffset,xSeparator,getHeight()-(5*axisOffset));
            xSeparator+=incrementX;

            g.setColor(Color.RED);
            //paint lost graphic line
            g.drawLine(i*incrementX, this.getHeight()- lostFirstY, (i + 1)*incrementX , this.getHeight()- lostLastY);
            //g.drawRect(i*incrementX, this.getHeight()- lostFirstY, incrementX, lostLastY-lostFirstY);
            g.fillRect(i*incrementX, this.getHeight()- lostFirstY, incrementX, lostLastY-lostFirstY);
            // change the color of the line
            g.setColor(Color.BLACK);


        }

        // paint the final axis separator
        g.drawLine(xSeparator,getHeight()-axisOffset,xSeparator,getHeight()-(5*axisOffset));


       /* System.out.println(incrementY);
        System.out.println(incrementX);
        System.out.println(getHeight());
*/

        yScale=incrementY;
       /* do{
            //paint the y axis separator
            //System.out.println(ySeparator);
            g.drawLine(0,ySeparator,5*axisOffset,ySeparator);
            ySeparator+=incrementY+1;


        }while (ySeparator<=this.getHeight());*/



    }

    @Override
    public void addActionController(ActionController actionController) {
         // Do nothing
    }
}
