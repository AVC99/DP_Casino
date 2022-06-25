package model;

import constants.ResourcesPath;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

public class Horse implements Serializable {
    private String horseColor;
    private int horseX;
    private int horseSpeed;
    private String horseImagePath;
    private int horseY;
    private boolean isWinningHorse;
    private int horseIndex;

    public Horse(String path, int horseY, int index) {
        isWinningHorse = false;
        Random random = new Random();
        this.horseIndex = index;
        this.horseColor = ResourcesPath.HORSE_COLORS[index];

        this.horseX = 0;
        this.horseSpeed = random.nextInt(1000) / 50;
        this.horseImagePath = path;
        this.horseY = horseY;
    }


    public int getHorseIndex() {
        return horseIndex;
    }

    public String getHorseImagePath() {
        return horseImagePath;
    }

    public String getHorseColor() {
        return horseColor;
    }

    public int getHorseX() {
        return horseX;
    }

    public int getHorseSpeed() {
        return horseSpeed;
    }

    public int getHorseY() {
        return horseY;
    }


    public void runHorse() {
        this.horseX += this.horseSpeed;
    }

    public void hasWon() {
        this.isWinningHorse = true;
    }

    public boolean isWinningHorse() {
        return isWinningHorse;
    }
}
