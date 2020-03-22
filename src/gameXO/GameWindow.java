package gameXO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameWindow extends JFrame {

    private static final int  WIN_HEIGHT = 555;
    private static final int  WIN_WIDTH = 505;
    private static final int  WIN_POS_X = 500;
    private static final int  WIN_POS_Y = 300;
    private SettingsWindow sw;
    private Map map;
    private ResultWindow resultWindow;


    GameWindow()  {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(WIN_POS_X, WIN_POS_Y, WIN_WIDTH, WIN_HEIGHT);
        setTitle("XO_Game");
        setVisible(true);
        setAlwaysOnTop(true);

        JPanel jp = new JPanel(new GridLayout(1, 3));
        sw = new SettingsWindow(this);
        map = new Map(this);
        resultWindow = new ResultWindow(this);

        JButton btnNewGame = new JButton("Start new game");
        JButton btnExit = new JButton("Exit");
        JButton btnSettings = new JButton("Settings");

        jp.add(btnSettings);
        jp.add(btnNewGame);
        jp.add(btnExit);

        add(jp, BorderLayout.SOUTH);
        add(map, BorderLayout.CENTER);
        Logic.initMap();
        Logic.gameFinished = false;
        startGame(Logic.game_mode, Logic.size);

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               sw.setVisible(true);
            }
        });

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logic.initMap();
                Logic.player1IsPlayed = false;
                Logic.gameFinished = false;
                startGame(Logic.game_mode, Logic.size);
            }
        });
    }

    public void startGame (int mode, int fieldSize) {
        map.startGame(mode, fieldSize);
    }

    public void showResult() {
        resultWindow.setWinner(Logic.winnerName);
        resultWindow.setVisible(true);
    }
}
