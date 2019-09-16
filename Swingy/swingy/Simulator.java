package swingy;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.SwingUtilities;

import javax.swing.*;

import swingy.controller.*;
import swingy.view.*;
import swingy.view.missionwindow.*;
import swingy.model.characters.*;



public class Simulator {

    private static Simulator instance = null; // Singleton for easier function calling from views
    public GameController selectHero;

    //these are for when I add the gui play
    boolean playGUI = false;
    boolean playCLI = false;


    private Simulator() {}

    // --- Entry point of application ----------------------------------------------
    public static void main(String[] args) {
        try { Simulator.getInstance().initGame();}
        catch (InputMismatchException e) {
            System.out.println("Invalid request!");
        }
       
    }

    public static Simulator getInstance() {

        if (instance == null) {
            instance = new Simulator();
        }
        return instance;
    }

    public void initGame() throws InputMismatchException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Mode.\n" + "1. gui\n" + "2. console\n" + "3. exit");
        int input = scanner.nextInt();
        
        if (input == 1) {
            playGUI = true;
            uiReady();
        }
        else if (input == 2) {
            playCLI = true;
            uiReady();
        }
        else if (input == 3 ) {
            System.out.println("Exiting Swingy...");
            System.exit(1);
        }
        else {
            System.out.println("Invalid request");
            initGame();
        }
    }

    private void uiReady() {
        try {
            selectHero = new GameController();
            selectHero.start(playGUI, playCLI);    
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid request");
        }
                 
    }

    //begin gameplay 
    private void uiReadyToPlay() {
        
    }

}
/*
    private void uiReadyToPlay() {
        uiToLoad--;
        if (uiToLoad == 0) {
            mainGame = new MainGame(hero);
            mainGame.start();
        }
    }

    // --- End of HeroSelector controller ------------------------------------------

    public void startMainGame(AHero hero) {
        uiToLoad = (playCLI || playGUI) ? 1 : 2;
        this.hero = hero;
        final AHero gameHero = hero;

        if (guiFrame != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    guiFrame.startMainPanel(gameHero);
                    uiReadyToPlay();
                }
            });
        }
        if (cliFrame != null) {
            cliFrame.startMainPanel(hero);
            uiReadyToPlay();
        }
    }

    // --- Calls from MainGame controller ------------------------------------------

    public void displayMessage(String message) {
        message += "\n";
        final String toDisplay = message;
        if (guiFrame != null && guiFrame.mainPanel != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    guiFrame.mainPanel.storyPanel.addText(toDisplay);
                }
            });
        }
        if (cliFrame != null) {
            cliFrame.mainPanel.storyPanel.addText(message);
        }
    }

    public void updateMap(AMapElement[][] mapElems) {
        if (guiFrame != null)
            guiFrame.mainPanel.mapPanel.update(mapElems);
    }

    public void updateHero() {
        if (guiFrame != null)
            guiFrame.mainPanel.heroPanel.update();
        if (cliFrame != null)
            cliFrame.mainPanel.heroPanel.update();
    }

    public void showDirectionChoices() {
        // displayMessage("Choose which direction to go to");
        if (guiFrame != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    guiFrame.mainPanel.choicePanel.showDirectionChoices();
                }
            });
        }

        if (cliFrame != null) {
            cliFrame.choicePanel.showDirectionChoices();
        }
    }

    public void stopDirectionChoices(int dirIdx) {
        if (cliFrame != null)
            cliFrame.choicePanel.stopDirectionChoice(dirIdx);
    }

    public void showFightChoices() {
        displayMessage("Fight or Flee ?");
        if (guiFrame != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    guiFrame.mainPanel.choicePanel.showFightChoices();
                }
            });
        }
        if (cliFrame != null)
            cliFrame.choicePanel.showFightChoices();
    }

    public void stopFightChoices(String choice) {
        if (cliFrame != null)
            cliFrame.choicePanel.stopFightChoice(choice); 
    }

    public void showArtifactChoices(String artifactType) {
        displayMessage("Equip found " + artifactType + " ?");
        if (guiFrame != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    guiFrame.mainPanel.choicePanel.showArtifactChoices();
                }
            });
        }
        if (cliFrame != null)
            cliFrame.choicePanel.showArtifactChoices();
    }

    public void stopArtifactChoices(String choice) {
        if (cliFrame != null)
            cliFrame.choicePanel.stopArtifactChoice(choice);
    }

    public void showDeathChoices() {
        if (guiFrame != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    guiFrame.mainPanel.choicePanel.showDeathChoices();
                }
            });
        }
        if (cliFrame != null)
            cliFrame.choicePanel.showDeathChoices();
    }

    public void retryGame() {
        if (cliFrame != null)
            cliFrame.choicePanel.stopDeathChoice();
        displayMessage("");

        mainGame.start();
    }

    public void endGame() {
        if (guiFrame != null) {
            guiFrame.dispose();
            guiFrame = null;
        }
        if (cliFrame != null) {
            cliFrame.dispose();
        }

        DatabaseController.saveHero(hero, cliFrame != null);
    }

}
*/
