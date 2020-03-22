package gameXO;

import javax.swing.*;
import java.awt.*;

class ResultWindow extends JFrame {

    private String winner = " ";
    private static final int WIDTH = 200;
    private static final int HEIGHT = 80;
    private JLabel jLabel = new JLabel(winner);

    ResultWindow(GameWindow gameWindow) {
        setSize(WIDTH, HEIGHT);
        setTitle("Result");

        Rectangle gameWindowBounds = gameWindow.getBounds();
        int posX = (int)(gameWindowBounds.getCenterX() - WIDTH/2);
        int posY = (int)(gameWindowBounds.getCenterY() - HEIGHT/2);
        setLocation(posX, posY);

        add(jLabel);
        setVisible(false);
    }

    void setWinner(String winner) {
        this.winner = winner;
        jLabel.setText(winner);
    }

}
