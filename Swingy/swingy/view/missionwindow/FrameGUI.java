package swingy.view.missionwindow;

import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import swingy.view.*;

public class FrameGUI extends JFrame implements  ActionListener  {

    JFrame window;
    Container con;
    ImagePanel img;
    JPanel titlePanel, startPanel, mainPanel, playerPanel, choicePanel, menuPanel;
    JLabel titleLabel, hpLabel, hpNumber, weaponLabel, weapon;
    JButton startButton, consoleButton, choice1, choice2;
    JTextArea gameText;
    static String screen, position;
    Graphics g;

    Font titleFont = new Font("Times New Roman", Font.PLAIN, 90);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 28);
    //ScreenHandler screenHandler = new ScreenHandler();
    //ChoiceHandler choiceHandler = new ChoiceHandler();

    public  FrameGUI(String name) {

        screen = "startscreen";
        window = new JFrame(name);
        window.setSize(800, 600);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        con = window.getContentPane();
        con.setVisible(true);
        
        // try {
        //    g.drawImage(ImageIO.read(new File("distopia.jpg")), 0, 0, null);
        // }
        // catch (IOException e) {
        //     e.printStackTrace();
        // }
        img = new ImagePanel(new ImageIcon("distopia.jpg").getImage());

        titlePanel = new JPanel();
        titlePanel.setBounds(100, 100, 600, 150);
        titlePanel.setBackground(Color.black);
        titlePanel.setOpaque(false);
        titleLabel = new JLabel("SwingBaby!");
        titleLabel.setForeground(Color.white);

        startPanel = new JPanel();
        startPanel.setBounds(300, 400, 200, 100);
        startPanel.setBackground(Color.black);
        startPanel.setOpaque(false);

        startButton = new JButton("START");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.black);
        startButton.addActionListener(this);
        startButton.setActionCommand("start");
        startButton.setFocusPainted(false);

        // consoleButton = new JButton("CONSOLE");
        // consoleButton.setBackground(Color.black);
        // consoleButton.setForeground(Color.black);
        // consoleButton.addActionListener(this);
        // consoleButton.setActionCommand("console");
        // consoleButton.setFocusPainted(false);

        titlePanel.add(titleLabel);
        startPanel.add(startButton);
        //startPanel.add(consoleButton);

        con.add(img);
        con.add(titlePanel);
        con.add(startPanel);

    }

    public void MenuScreen() {
        screen  = "menuscreen";
        startPanel.setVisible(false);
        //con.remove(startPanel);

        menuPanel = new JPanel();
        menuPanel.setBounds(250, 350, 300, 150);
        menuPanel.setBackground(Color.black);
        //menuPanel.setLayout(new GridLayout(4,1));
        con.add(menuPanel);

        choice1 = new JButton("Create new character");
        choice1.setBackground(Color.black);
        choice1.setForeground(Color.black);
        choice1.setFocusPainted(false);
        choice1.addActionListener(this);
        choice1.setActionCommand("c1");
        menuPanel.add(choice1);
        choice2 = new JButton("Load existing game");
        choice2.setBackground(Color.black);
        choice2.setForeground(Color.black);
        choice2.setFocusPainted(false);
        choice2.addActionListener(this);
        choice2.setActionCommand("c2");
        menuPanel.add(choice2);

    }

    public void GameScreen() {

        screen = "mainscreen";
        con.remove(menuPanel);

        titlePanel.setVisible(false);
        startPanel.setVisible(false);

        mainPanel = new JPanel();
        mainPanel.setBounds(100, 100, 600, 250);
        mainPanel.setBackground(Color.black);
        con.add(mainPanel);
        gameText = new JTextArea("This is where the magic happens");
        gameText.setBounds(100, 100, 600, 250);
        gameText.setBackground(Color.black);
        gameText.setForeground(Color.white);
        gameText.setFont(normalFont);
        gameText.setLineWrap(true);
        mainPanel.add(gameText);

        choicePanel = new JPanel();
        choicePanel.setBounds(250, 350, 300, 150);
        choicePanel.setBackground(Color.black);
        choicePanel.setLayout(new GridLayout(0,1));
        con.add(choicePanel);
        choice1 = new JButton("Choice 1");
        choice1.setBackground(Color.blue);
        choice1.setForeground(Color.black);
        choice1.setFocusPainted(false);
        choice1.addActionListener(this);
        choice1.setActionCommand("c1");
        choicePanel.add(choice1);
        choice2 = new JButton("Choice 2");
        choice2.setBackground(Color.black);
        choice2.setForeground(Color.red);
        choice2.setFocusPainted(false);
        choice2.addActionListener(this);
        choice2.setActionCommand("c2");
        choicePanel.add(choice2);

        playerPanel = new JPanel();
        playerPanel.setBounds(100, 15, 600, 50);
        playerPanel.setBackground(Color.black);
        playerPanel.setLayout(new GridLayout(1,4));
        con.add(playerPanel);

        hpLabel = new JLabel("HP:");
        hpLabel.setFont(normalFont);
        hpLabel.setForeground(Color.white);
        playerPanel.add(hpLabel);
        hpNumber = new JLabel();
        hpNumber.setFont(normalFont);
        hpNumber.setForeground(Color.white);
        playerPanel.add(hpNumber);
        weaponLabel = new JLabel("Weapon:");
        weaponLabel.setFont(normalFont);
        weaponLabel.setForeground(Color.white);
        playerPanel.add(weaponLabel);
        weapon = new JLabel();
        weapon.setFont(normalFont);
        weapon.setForeground(Color.white);
        playerPanel.add(weapon);

    }

    public void NewCharacter() {

    }

    public void LoadGame() {

    }


    public void actionPerformed(ActionEvent e) {

        System.out.println("Testing actionlistener");
        System.out.println(screen);
        String event = e.getActionCommand();

        System.out.println(event);

        switch (screen) {
            case "startscreen" :
                if (event.equals("start")) {
                    MenuScreen();
                }
                else {
                    System.out.println("invalid selection");
                }
                // switch (event) {
                //     case "start":
                //         MenuScreen();
                //         break;

                //     case "console" :
                //         System.out.println("ben");
                //         //go to the console
                //         break;
                // }
                break;

            case  "menuscreen"  :
                switch (event) {
                    case "c1" :
                        NewCharacter();
                        break;

                    case "c2" :
                        LoadGame();
                        break;
                }
                break;

            case "mainscreen"  :
                System.out.println("poop poop poop");
                break;
        }

    }

}
