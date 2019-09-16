package swingy.model.characters;

public class Enemy {

    private static String[] names = {"Kiki", "Chin", "Gary", "Pablo", "Lolita", "PoohBear", "Jose", "T2-J8", "Claud"};
    private static String[] modes = {"The Kleaner", "cause if you think about it", "the smelly hipster", "professional ninja","the local noodle vendor", "from next door", "your tax consultant", "and his oatmeal biscuits", "WAAAGHHH!", "the Other Jonas Brother"};
    private int ID;
    private int attack = 1;
    private int health = 100;



    public Enemy() {
    };

    public String getName() {
        int index = (int)(Math.random() * 9);
        return names[index];
    }

    public String getMode() {
        int i = (int)(Math.random() * 10);
        return modes[i];
    }
}
