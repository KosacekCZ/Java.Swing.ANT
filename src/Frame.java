import javax.swing.*;

import java.awt.*;

public class Frame implements FrameOverride {

    int width = 50;
    int height = 50;
    int delay = 0;

    JFrame fr = new JFrame("ANT");
    JButton jb;
    Square[][] field = new Square[width][height];

    void initField() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = (new Square(i, j, false, new JButton()));
                field[i][j].jb.setBounds(i * 10, j * 10, 10, 10);
                field[i][j].jb.setBackground(Color.black);
                field[i][j].jb.setBorderPainted(false);
                fr.add(field[i][j].jb);
                System.out.println(i + " " + j);
            }
        }
    }


    void initFrame() throws InterruptedException {
        fr.setLayout(new GridLayout(width, height));
        fr.setSize((width + 1) * 10, (height + 1) * 10);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setResizable(false);
        fr.getContentPane().setBackground(Color.black);
        initField();
        fr.setVisible(true);
        for (int i = 0; i < 10; i++) {
            start();
        }
        scan();
        while (true) {
            spread();
        }
    }

    void start() {
        int rx = randRange(0, width);
        int ry = randRange(0, height);
        field[rx][ry].flipped = true;
        // gen sqr
        try {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    field[rx + i][ry + j].flipped = true;
                }
            }
        } catch (Exception e ) {
            // System.out.println("border");
        }
         System.out.println("start");
    }

    void scan() {
        for (Square[] col : field) {
            for (Square sqr : col) {
                if (sqr.flipped) {
                    int x = sqr.x;
                    int y = sqr.y;

                    field[x][y].jb.setBackground(Color.yellow);
                    fr.revalidate();

                }
            }
        }
    }

    void spread() throws InterruptedException {

        for (Square[] col : field) {
            for (Square sqr : col) {
                Thread.sleep(delay);
                // check cell
                int x = sqr.x;
                int y = sqr.y;
                int nbCount = 0;
                    // field[x][y].jb.setBorderPainted(true);
                    // field[x][y].jb.setBorder(BorderFactory.createLineBorder(Color.green));
                    // fr.revalidate();
                // System.out.println("Checking " + x + " | " + y);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        try {
                            if (field[x + i][y + j].flipped) {
                                nbCount++;

                            }
                        } catch (Exception e) {
                            // System.out.println("border");
                        }
                    }
                }
                if (nbCount >= 3) {
                    field[x][y].flipped = true;
                }
                scan();

            }
        }
    }

    public int randRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}

