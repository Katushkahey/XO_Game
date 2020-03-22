package gameXO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SettingsWindow extends JFrame {

     private final GameWindow gameWindow;

     private static final int STNG_HEIGHT = 230;
     private static final int STNG_WIDTH = 200;
     private static final int STNG_POS_X = 500;
     private static final int STNG_POS_Y = 300;
     private static final int MIN_FIELD_SIZE = 2;
     private static final int MAX_FIELD_SIZE = 10;
     private static final String STR_FIELD_SIZE = "Field size: ";

     private JRadioButton hvi = new JRadioButton("Human VS Ai", true);
     private JRadioButton hvh = new JRadioButton("Human VS Human");
     private ButtonGroup gameMode = new ButtonGroup();
     private JSlider slFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, Logic.size);

     public SettingsWindow(GameWindow gameWindow) throws HeadlessException {
        this.gameWindow = gameWindow;
        setBounds(STNG_POS_X, STNG_POS_Y, STNG_WIDTH, STNG_HEIGHT);
        setTitle("Game parameters");
        setLayout(new GridLayout(7,1));
        setVisible(false);
        addGameMode();
        addFieldSize();
        JButton save = new JButton("Save");
        JButton cancel  = new JButton("Cancel");
        JPanel jp = new JPanel(new GridLayout(1,2));
        jp.add(save);
        jp.add(cancel);
        add(jp);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSaveIsClicked();
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
     }

     private void addGameMode() {
         add(new JLabel("Choose game mode"));
         gameMode.add(hvi);
         gameMode.add(hvh);
         add(hvi);
         add(hvh);
     }

     private void addFieldSize () {
         add(new JLabel("Choose field size"));
         JLabel labelSize = new JLabel(STR_FIELD_SIZE + slFieldSize.getValue());
         add(labelSize);
         add(slFieldSize);
         slFieldSize.addChangeListener(new ChangeListener() {
             @Override
             public void stateChanged(ChangeEvent e) {
                 int currentFieldSize = slFieldSize.getValue();
                 labelSize.setText(STR_FIELD_SIZE + currentFieldSize);
             }
         });
     }

     private void buttonSaveIsClicked () {
         Logic.player1IsPlayed = false;
         Logic.game_mode = (hvh.isSelected()) ? Map.MODE_HUMAN_VS_HUMAN : Map.MODE_HUMAN_VS_AI;
         Logic.size = slFieldSize.getValue();
         Logic.initMap();
         Logic.gameFinished = false;
         gameWindow.startGame(Logic.game_mode, Logic.size);
         setVisible(false);
     }

}
