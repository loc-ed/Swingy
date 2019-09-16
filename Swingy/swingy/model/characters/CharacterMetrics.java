package swingy.model.characters;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

//import javax.validation.constraints.NotNull;

import swingy.model.artifacts.*;

public class CharacterMetrics  {

    //@NotNull
    private String name;
    private int ID;
    private double attackPoints = 1;
    private double defensePoints = 1;
    private double health = 100;
    private double evasivePoints ; //chance of evading factor
    private static long XP = 0L;

    //public static int level = 1;

    public static List<Artifacts> inventory = new ArrayList<>();

    //default constructor
    public CharacterMetrics() {};

    //console and gui buildHero constructor
    public CharacterMetrics(int ID, String name) {
        this.name = name;
        this.ID = ID;

        if (this.ID == 1) { Loire();}
        else if (this.ID == 2) { Sanchez();}
        else if (this.ID == 3) {theMan();}
        else if (this.ID == 4) {Joe();}
        else {
            System.out.println("invalid character selection");
        }
    }

    //console and gui existing game constructor
    public CharacterMetrics(int ID, String name, long experience, double attack, double defense, double health, double evasion) {
        this.ID = ID;
        this.name = name;
        XP = experience;
        this.attackPoints = attack;
        this.defensePoints = defense;
        this.health = health;
        this.evasivePoints = evasion;
    }

    public void Loire() { //Loire the priestess specific leanings (so to speak)
        this.attackPoints = this.attackPoints * 2;
        this.defensePoints = this.defensePoints * 0.5;
        this.evasivePoints = 0.75;
    }

    public void  Sanchez() {
        this.attackPoints = this.attackPoints * 5;
        this.defensePoints = this.defensePoints * 0.25;
        this.evasivePoints = 0.4;
    }

    public void  theMan() {
        //i think the man will have riddles or somerhing that the player has to solve
    }

    public void Joe() {
        this.attackPoints = attackPoints * 1;
        this.defensePoints = defensePoints * 1;
        this.evasivePoints = 0.5;
    }

    public long getXP() {return XP;}
    public String getName() {return this.name;}
    public int getID() {return this.ID;}
    public double getAttack() {return this.attackPoints;}
    public double getDefense() {return this.defensePoints;}
    public double getHealth() {return this.health;}
    public double getEvasion() {return this.evasivePoints;}

    public void setXP(long experience) {
        XP = XP + experience;
    }

    public void setHealth(int point , double health) {
        if (point == 0) {
            this.health = this.health - (health * this.defensePoints);
        }
        if (point == 1) {
            this.health = this.health + (health * this.attackPoints);
        } 
        if (point == 3) {
            this.health = 100;
        }
    }

}
