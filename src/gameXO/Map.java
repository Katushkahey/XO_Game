package gameXO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Map extends JPanel {

    private final GameWindow gameWindow;
    static final int MODE_HUMAN_VS_AI = 0;
    static final int MODE_HUMAN_VS_HUMAN = 1;
    private int fieldSize;
    private int cellHeight;
    private int cellWidth;


    Map(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setBackground(Color.YELLOW);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (Logic.game_mode == MODE_HUMAN_VS_AI) {
                    update(e,1);
                } else {
                    if (!Logic.player1IsPlayed) {
                        update(e, 1);
                        Logic.player1IsPlayed = true;
                    } else {
                        update(e, 2);
                        Logic.player1IsPlayed = false;
                    }
                }
            }
        });

        startGame(Logic.game_mode, Logic.size);
    }

    private void update(MouseEvent e, int playNumber) {
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        Logic.setHumanXY(cellX, cellY, playNumber);
        if (Logic.gameFinished) {
            gameWindow.showResult();
        }
        repaint();
    }

    void startGame(int mode, int fieldSize) {
        this.fieldSize = fieldSize;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        cellHeight = panelHeight / fieldSize;
        cellWidth = panelWidth / fieldSize;

        for (int i = 0; i < fieldSize ; i++) {
            int y = i*cellHeight;
            g.drawLine(0,y,panelWidth,y);
        }

        for (int i = 0; i < fieldSize; i++) {
            int x = i * cellWidth;
            g.drawLine(x,0,x,panelHeight);
        }

        for (int i = 0; i < Logic.size ; i++) {
            for (int j = 0; j < Logic.size; j++) {
                if (Logic.map[i][j] == Logic.DOT_X) {
                    drawX(g, j, i);
                }else if (Logic.map[i][j] == Logic.DOT_O) {
                    drawO(g, j, i);
                }
            }
        }
    }

    private void drawO (Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.drawOval(x * cellWidth + 5, y * cellHeight + 5, cellWidth - 10, cellHeight - 10);
    }

    private void drawX(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.drawLine(x * cellWidth + 5, y * cellHeight + 5, (x+1) * cellWidth - 10, (y + 1) * cellHeight - 10);
        g.drawLine((x+1) * cellWidth - 10, y * cellHeight + 5, x * cellWidth + 5, (y + 1) * cellHeight - 10);
    }
}
