package view.views;

import controller.ActionController;
import model.Horse;
import view.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HorseRaceField extends View implements Runnable{
    private BufferedImage backGroundImage ;
    private int horseY;
    private ArrayList<Horse> horseList;
    private boolean continueRunning;
    private int raceFinal;
    private ArrayList<BufferedImage> horseImages;

    public HorseRaceField() {
        this.horseList= new ArrayList<>();
        this.horseImages= new ArrayList<>();

        continueRunning=true;

        try{
            backGroundImage= ImageIO.read(new File("./images/horseRaceBackground.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        this.setBackground(Color.CYAN);

        //initializeHorses();

    }

    public void setHorseList(ArrayList<Horse> horseList) {
        setHorseImages(horseList);
        this.horseList = horseList;
    }

    private void setHorseImages(ArrayList<Horse> horseList) {
        for(Horse h: horseList){
            try{
                horseImages.add(ImageIO.read(new File(h.getHorseImagePath())));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



    public ArrayList<Horse> getHorseList() {
        return horseList;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGroundImage,0,0,this.getWidth(),this.getHeight(),null);
        this.raceFinal=this.getWidth();

        for(Horse h: horseList){
            g.drawImage(this.horseImages.get(h.getHorseIndex()),h.getHorseX(),h.getHorseY(),this.horseImages.get(h.getHorseIndex()).getWidth()*2,this.horseImages.get(h.getHorseIndex()).getHeight()*2,null);
        }
    }

    @Override
    public void addActionController(ActionController actionController) {
        //do nothing
    }

    @Override
    public void run() {
        while(continueRunning==true){
            try {
                for(Horse h: horseList){
                    h.runHorse();
                    if(h.getHorseX()>=this.raceFinal){
                        h.hasWon();
                        stopRunning();
                        break;
                    }
                    repaint();
                    Thread.sleep(50);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRunning() {
        this.continueRunning=false;
        repaint();
    }

    public void startRace() {this.continueRunning=true;}
}
