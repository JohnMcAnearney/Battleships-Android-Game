package uk.ac.qub.eeecs.game.BattleShips;

//Destoryer class that extends from the Ship class
public class Destroyer extends Ship {
    public Destroyer() {
        super("Ship5", "Destroyer"); //Ship number 5 and ship type
    }

    public Destroyer(String name, String shipType) {
        super(name, shipType);
    }

    public String toString() {
        return "Destroyer" + super.toString();
    }
}

