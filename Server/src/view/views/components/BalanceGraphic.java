package view.views.components;

import controller.ActionController;
import view.View;

import java.awt.*;
import java.util.ArrayList;

public class BalanceGraphic extends View {

    private final ArrayList<Integer> balanceChanges;
    private ArrayList<Integer> balanceChangesInt;
    private int balanceFirstY;
    private int balanceLastY;
    private  int xScale;
    private  int yScale;
    private final int axisOffset=1;


    public BalanceGraphic(ArrayList<Integer> balanceChanges) {
        //startIndex();
        this.balanceChanges = balanceChanges;


    }

    private void parseBalanceChanges() {
    balanceChangesInt = new ArrayList<>();
        for (Integer balance : balanceChanges) {
            balanceChangesInt.add(balance);
        }

    }

    public int getxScale() {
        return xScale;
    }

    public int getyScale() {
        return yScale;
    }

    @Override
    public void paintComponent(Graphics g) {

        parseBalanceChanges();
        super.paintComponent(g);
        int max=1;
        // set the x increment to be relative to the number of elements in the array
        int incrementX = (this.getWidth()-axisOffset) / (balanceChanges.size());


        int xSeparator=0;
        int ySeparator=0;

        for(int i: balanceChangesInt) {
            if (i > max) {
                max = i;
            }
        }
        xScale= incrementX;
        System.out.println(xScale);
        // set the y increment to be relative to the max number
        int incrementY= (this.getHeight()-axisOffset)/max;

        if (incrementY<1) {
            incrementY=this.getHeight();
        }
        int printcounter=0;

        for (int i = 0; i < balanceChangesInt.size(); i++) {
            //put the values that need to be drawn in the first and last y
            balanceFirstY = (balanceChangesInt.get(i)/10);
            balanceLastY = (balanceChangesInt.get(i + 1)/10);
            //balanceFirstY = (balanceChangesInt.get(i)) * incrementY;
            //balanceLastY = (balanceChangesInt.get(i + 1)) * incrementY;
            System.out.println(printcounter);
            printcounter++;
            //y axis
            g.drawLine(0,0,0,getHeight()-axisOffset);
            // x axis
            g.drawLine(0,getHeight()-axisOffset,getWidth(),getHeight()-axisOffset);

            //paint x the axis separator
            g.drawLine(xSeparator,getHeight()-axisOffset,xSeparator,getHeight()-(5*axisOffset));
            xSeparator+=incrementX;

            //paint the graphic line
            g.drawLine(i*incrementX, this.getHeight()- balanceFirstY, (i + 1)*incrementX , this.getHeight()- balanceLastY);

        }

        // paint the final axis separator
        //g.drawLine(xSeparator,getHeight()-axisOffset,xSeparator,getHeight()-(5*axisOffset));


       /* System.out.println(incrementY);
        System.out.println(incrementX);
        System.out.println(getHeight());
*/

        yScale=incrementY;

        /*do{
            //paint the y axis separator
            //System.out.println(ySeparator);
            g.drawLine(0,ySeparator,5*axisOffset,ySeparator);
            ySeparator+=incrementY+1;


        }while (ySeparator<=this.getHeight());
*/


    }
    
    
    @Override
    public void addActionController(ActionController actionController) {
        // Do nothing
    }
}
