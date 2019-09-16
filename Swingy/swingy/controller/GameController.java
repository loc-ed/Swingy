package swingy.controller;

import swingy.Simulator;
import swingy.model.characters.*;
import swingy.view.missionwindow.*;

import javax.swing.*;

import java.awt.Choice;
import java.awt.HeadlessException;
import java.awt.Point;
import java.io.*;
import java.util.*;

public class GameController {

    public static String[] swingy;
    public static CharacterMetrics fighter;
    public static char[][] map;
    public static long XP;
    public static double health;
    public static int currentLevel = 1;
    Scanner scan;
    public static int mapSize;
    public static Point point;
    public static PrintWriter writer = null;

    public static Enemy resistance;

    private String[] movement = {
        "         UP:          ",
        "          w           ",
        "LEFT:         RIGHT:  ",
        "  a               d   ",
        "        DOWN:         ",
        "          s           ",
        "                      ",
        "      QUIT: quit      "
};
    
    public  GameController() {          //constructor 
    }

    public void welcome() {
        clearScreen();
        swingyWelcome();
    }

    public void start(boolean playGui, boolean playCli) throws InputMismatchException {

        welcome();
        scan = new Scanner(System.in);
        if (playGui == true) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    FrameGUI guiFrame = new FrameGUI("Swingy");
                }
            });
        }
        if (playCli == true) {
            System.out.println("1. Select character\n" +  "2. Resume game");

            int input = scan.nextInt();
            switch (input) {
                case 1:
                    BuildHero();
                    break;

                case 2:
                    System.out.println("Loading ...");
                    try {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    } 
                    resumeGame();
                    break;

                 default:
                     System.out.println("Invalid request");
            }
        }
    }

    public void resumeGame() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("swingy/storage/saved_game.txt"));
            String line ;
            while ((line = reader.readLine()) != null) {
                String[] inline = line.split(" ");
                if (inline != null) {
                    fighter = new CharacterMetrics(Integer.parseInt(inline[0]), inline[1], Long.parseLong(inline[2]), Integer.parseInt(inline[3]),
                                                Integer.parseInt(inline[4]), Integer.parseInt(inline[5]), Double.parseDouble(inline[6]));
                }
            }
            MainGame();
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("No saved games.");
        }
        catch (IOException e) {
            System.out.println("Error resuming game");
        }
    }

    public void BuildHero() {  //this one is just for the console
        welcome();
        try {
            Hashtable<Integer, String> characters = new Hashtable<Integer, String>();
            characters.put(1, "Loire");
            characters.put(2, "Sanchez");
            characters.put(3, "TheMan");
            characters.put(4, "Joe");
            System.out.println("Choose character ...\n");
            System.out.println("1. Loire the Priestess \n" + "Abandoned on the steps of Red Dragon Monastery, she is skilled  in evasive techniques and  Moroccon cooking");
            System.out.println("2. Sanchez  \n" + "Disgraced martial arts master . Moonlights as a Kabuki performer and boy can he sing");
            System.out.println("3. The man  \n" + "Nothing is known about the man (unplayable)");
            System.out.println("4. Just your average Joe\n");

            int type = scan.nextInt();
            fighter = new CharacterMetrics(type, characters.get(type));
            MainGame();
        }
        catch (InputMismatchException ex) {
            System.out.println("Invalid input.");
        }
        
    }

    public void MainGame() {

        if (resistance == null) {
            resistance = new Enemy();
        }
        initMap();
        gameScreen();
    }

    //takes XP as input to generate size and difficulty of level
    public void initMap() {
    
       int x = 0;
       int y = 0;
       int startPoint = 0;

       getStats();
       mapSize = (int)((currentLevel - 1) * 5 + 10 - (currentLevel % 2));
       startPoint = Math.round((mapSize - 1)/2);
       point = new Point(startPoint, startPoint);
       map = new char[mapSize + 1][mapSize];
       while (y < mapSize) {
           x = 0;
           while (x <= mapSize) {
               if (x == mapSize) {map[x][y] = '\n';}
               else if (x == startPoint && y == startPoint) {map[x][y] = 'o';}
               else { map[x][y] = '*';}
               x++;
           }
           y++;
       }
    }

    public void gameScreen() {

        welcome();
        getStats();
        int x = 0;
        int y = 0;
        int i = 0;

        String name = fighter.getName();
        System.out.println ("       " + name  + "     XP : " + XP + "      | Health : " + health);
        System.out.print("\n\n");

        while (y < mapSize) {
            x = 0;
            if (i < 8) {
                System.out.print(movement[i]);
                i++;
            }
            else {
                System.out.print(movement[6]);
            }
            while (x <= mapSize) {
                System.out.print(" ");
                System.out.print(map[x][y]);
                x++;
            }
            y++;
        }

        System.out.print( "\n\n" );
        System.out.println("Choose Direction :");
        char move = scan.next().charAt(0);

        playersChoice(move);
        collisionCourse();
        gameScreen();        
    }

    public void collisionCourse() {

        Random random = new Random();
        int n = random.nextInt(3);
        
        if (n == 2) {
            welcome();
            System.out.println("You've encoounterd an enemy..." + resistance.getName() + " " + resistance.getMode());
            System.out.println("\n");
            System.out.println("Whatcha gonna do ?\n" + "1.brawl | 2.do a runner");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    n = random.nextInt(2);
                    if (n == 1) {
                        System.out.println("Well that was awkward!");
                        fighter.setHealth(0, 10);
                    }
                    else {
                        System.out.println(fighter.getName() + " has emerged victorious.");
                        fighter.setHealth(1, 50);
                    }
                    break;

                case 2:
                    escapeSequence();
                    break;
            }
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }  
        }
    }

    public void escapeSequence() {
        double evade = fighter.getEvasion;
        Random random = new Random();
        int n;

        if (evade == 0.75) {
            n = random.nextInt(4);
            if (n == 3) {
                System.out.println("You were caught and subsequently thumped");
                fighter.setHealth(0, 20);
            }
            else {
                System.out.println("You gave him the slip.");
            }
        }
        if (evade == 0.4) {
            n = random.nextInt(5);
            if (n == 2 || n == 4) {
                System.out.println("Like a glove");
            }
            else {
                System.out.println("You were caught and subsequently thumped");
                fighter.setHealth(0, 20);
            }
        }
        if (evade == 0.5) {
            n = random.nextInt(2);
            if (n == 1) {
                System.out.println("You were caught and subsequently thumped");
                fighter.setHealth(0, 20);
            }
            else {
                System.out.println("Scott free but the batteries are extra");
            }
        }
    }

    public void getStats() {

        // periodically get the game stats 
        XP = fighter.getXP();
        health = fighter.getHealth();
        int level = 0;

        if (health <= 0) {
            System.out.println("GAME OVER");
            System.exit(1);
        }
        if (health >= 100) {
            fighter.setHealth(3, 0);
        }

        if (XP <= 1000){
            level = 1;
        }
        else if (XP <= 2450) {
            level= 2;
        }
        else if (XP <= 4800) {
            level = 3;
        }
        else if (XP <= 8050) {
            level = 4;
        }
        else if (XP <= 12200) {
            level = 5;
        }
        else {
            //gameOver = true;
            System.out.println("Huzzah! You've won or whatever \n");
            System.out.println("1.Play again\n" + "2.exit");
            int input = scan.nextInt();
            switch (input) {
                case 1:
                     //take them back to start of game
                     break;
 
                 case 2:
                     System.out.println("Exiting game...");
                     System.exit(1);
                     break;
            }
 
        }

        if (currentLevel < level && level != 0) {
            welcome();
            //gameOver  = true;
            System.out.println("You've advanced to the next level!");
            currentLevel = level;
            initMap();
        }
        else {
            currentLevel = level;
        }
    }

    public void playersChoice(char move) {

        int x = (int)point.getX();
        int y = (int)point.getY();
        
        if (x == 0 || y == 0 || x == (mapSize - 2) || y == (mapSize - 1))  {
            
        }
        if (move == 'w') {
            if (y == 0) {
                point.setLocation(x, y);
                borders();
            }
            else {point.setLocation(x, y - 1);}
            fighter.setXP(20L);
        }
        else if (move == 's') {
            if (y == (mapSize - 1)) {
                point.setLocation(x, mapSize - 1);
                borders();
            } 
            else { point.setLocation(x, y + 1);}
            fighter.setXP(20L);
        }
        else if (move == 'a') {
            if (x == 0) { 
                point.setLocation(x, y);
                borders();
            }
            else {point.setLocation(x - 1, y);}
            fighter.setXP(20L); 
        }
        else if (move == 'd') {
            if (x == (mapSize - 1)) {
                point.setLocation(mapSize - 2, y);
                borders();
            }
            else {point.setLocation(x + 1, y);}
            fighter.setXP(20L);
        }
        else if (move == 'q') {exodus();}
        else {
            try {
                System.out.println("Try again dummy");
                Thread.sleep(1000);    
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }  
            gameScreen();
        }
        
        x = (int)point.getX();
        y = (int)point.getY();
        map[x][y] = 'o';
    }

    public void borders() {
        welcome();
        System.out.println("You shall not pass! Not enough experience");
        System.out.println("Keep within the borders.");
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }  
    }

    public void exodus() {
   
        System.out.println("Save game ?\n" + "1.yes | 2.nah ");
        int choice = scan.nextInt();
        if (choice == 1) { 
            saveGame();
        }
        if (choice == 2) {
            System.out.println("Exiting Swingy...");
            System.exit(1);
        } 
    }

     public void saveGame() {

        try {
            writer = new PrintWriter(new File("swingy/storage/saved_game.txt"));
            String name = fighter.getName();
            int id = fighter.getID();
            double attack = fighter.getAttack();
            double defense = fighter.getDefense();
            double health = fighter.getHealth();
            double evasion = fighter.getEvasion();
            long XP = fighter.getXP();

            //just to test that its saving correctly
            System.out.println("HeroStats are :\n" + "name : " + name + " ID : " + id + " attack : " + attack + " defense: " + defense + " health: " + health + " evasion : " + evasion + " XP : " + XP);
            writer.write(id + " " + name + " " + XP + " " + attack + " " + defense + " " + health + " " + evasion );
            writer.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
        }
        System.out.println("Exiting Swingy...");
        System.exit(1);
        
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void swingyWelcome() {
        if (swingy == null) {
            swingy = new String[9];
            swingy[0] = "\u001B[37m" + "   .-'''-. .--.      .--..-./`) ,---.   .--.  .-_'''-.      ____     __"  + "\u001B[0m";       
            swingy[1] = "\u001B[31m" + "  /        \\  |_     |  |\\.-.')|    \\ |  | '_( )_   \\    \\ \\   /  /" + "\u001B[0m"; 
            swingy[2] = "\u001B[35m" + " (`' )/`--'| _( )_   |  |/ `-'\\|  ,  \\|  ||(_ o _)|  '     \\  _. /  ' " + "\u001B[0m";       
            swingy[3] = "\u001B[33m" + "(_ o _).   |(_ o _)  |  | `-'`''|  |\\\\|  |. (_,_)/___|      _( )_ .'" + "\u001B[0m";        
            swingy[4] = "\u001B[34m" + " (_,_). '. | (_,_)\\ |  | .---. |  _( )\\  ||  |  .-----. ___(_ o _)'" + "\u001B[0m";           
            swingy[5] = "\u001B[36m" + " .---. \\  :|  |/   \\|  | |   | | (_ o _)  |' \\  '-   .'|   |(_,_)'" + "\u001B[0m";           
            swingy[6] = "\u001B[33m" + " \\    `-'  ||  '  /\\ `  | |   | |  (_,_)\\ |\\  `-'`   | |   `-'  /" + "\u001B[0m";             
            swingy[7] = "\u001B[31m" + "  \\       / |    /  \\   | |   | |  |    |  | \\        / \\      /" + "\u001B[0m";          
            swingy[8] = "\u001B[35m" + "  `-...-'  `---'    `---` '---' '--'    '--'   `'-...-'    `-..-'" + "\u001B[0m";  
 
                                                                               
 
        }
        for (int i = 0; i < 9; i++) {
            System.out.println(swingy[i]);
        }
        System.out.print( "\n\n" );
    }
}
