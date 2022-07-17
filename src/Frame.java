import javax.swing.*;

import java.awt.*;
import java.util.HashMap;

public class Frame {

    int width = 200;
    int height = 200;
    int res = 4;
    int startDelay = 15000;
    int genDelay = 0;
    int startNoise = ((width * height) / 4);

    JFrame fr = new JFrame("ANT");
    JButton jb;
    // Square[][] field = new Square[width][height];
    HashMap<String, Square> field = new HashMap<>();


    void initField() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field.put(i + " " + j, (new Square(i, j, false, new JButton(), 0)));
                field.get(i + " " + j).jb.setBounds(i * res, j * res, res, res);
                field.get(i + " " + j).jb.setBackground(Color.black);
                field.get(i + " " + j).jb.setBorderPainted(false);
                fr.add(field.get(i + " " + j).jb);
                // System.out.println(i + " " + j);
            }
        }
    }

    void initFrame() throws InterruptedException {
        fr.setLayout(new GridLayout(width, height));
        fr.setSize((width) * res, (height) * res);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setResizable(true);
        fr.getContentPane().setBackground(Color.black);
        initField();
        fr.setVisible(true);
        start();
    }

    void calcNb() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int nbCount;
                if (field.get(i + " " + j).alive) {
                    nbCount = -1;
                } else {
                    nbCount = 0;
                }

                // check neighbour cells
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        String currentSqr = (i + k) + " " + (j + l);
                        Square key = field.get(currentSqr);
                        if (key != null) {

                            if (field.get(currentSqr).alive) {
                                nbCount++;
                            }
                        }
                        field.get(i + " " + j).nb = nbCount;

                    }
                }
                // System.out.println("cell " + i + " " + j + " has " + nbCount + " neighbours");
            }

                /* if (field.get(i + " " + j).alive) {
                    if (nbCount < 2) {
                        field.get(i + " " + j).alive = false;
                        field.get(i + " " + j).jb.setBackground(Color.black);
                        System.out.println("cell with 2 or less neighbours has died.");
                    } else if (nbCount == 2 || nbCount == 3) {
                        field.get(i + " " + j).alive = true;
                        field.get(i + " " + j).jb.setBackground(Color.yellow);
                    } else if (nbCount > 3) {
                        field.get(i + " " + j).alive = false;
                        field.get(i + " " + j).jb.setBackground(Color.black);
                        System.out.println("cell with 4 or more neighbours has died .");
                    }
                } else {
                    // dead cell
                    if (nbCount == 3) {
                        field.get(i + " " + j).alive = true;
                        field.get(i + " " + j).jb.setBackground(Color.yellow);
                    }
               }
                    */
        }
    }

    void nextGen() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (field.get(i + " " + j).alive) {
                    switch (field.get(i + " " + j).nb) {
                        case 0:
                        case 1:
                            field.get(i + " " + j).alive = false;
                            field.get(i + " " + j).jb.setBackground(Color.black);
                            // System.out.println("cell with 2 or less neighbours has died.");
                            break;
                        case 2:
                        case 3:
                            field.get(i + " " + j).alive = true;
                            field.get(i + " " + j).jb.setBackground(Color.yellow);
                            break;
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            field.get(i + " " + j).alive = false;
                            field.get(i + " " + j).jb.setBackground(Color.black);
                            // System.out.println("cell with 4 or more neighbours has died .");
                            break;
                    }
                } else {
                    if (field.get(i + " " + j).nb == 3) {
                        field.get(i + " " + j).alive = true;
                        field.get(i + " " + j).jb.setBackground(Color.yellow);
                    }
                }
            }
        }
        fr.validate();
    }

    void spawnNoise() {
        for (int i = 0; i < startNoise; i++) {
            int rx = randRange(0, width);
            int ry = randRange(0, height);
            field.get(rx + " " + ry).alive = true;
            field.get(rx + " " + ry).jb.setBackground(Color.yellow);
        }
        fr.validate();
    }

    void start() throws InterruptedException {
        spawnNoise();
        Thread.sleep(startDelay);
        while (true) {
            Thread.sleep(genDelay);
            calcNb();
            nextGen();
        }
    }

    int randRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
