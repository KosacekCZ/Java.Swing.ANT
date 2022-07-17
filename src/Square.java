import javax.swing.*;

public class Square {
    int x;
    int y;
    int nb;
    boolean alive;
    JButton jb;

    public Square(int x, int y, boolean alive, JButton jb, int nb) {
        this.x = x;
        this.y = y;
        this.nb = nb;
        this.alive = alive;
        this.jb = jb;

    }

}
