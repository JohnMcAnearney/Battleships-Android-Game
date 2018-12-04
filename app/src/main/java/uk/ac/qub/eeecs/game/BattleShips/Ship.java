package uk.ac.qub.eeecs.game.BattleShips;
//Sprint 3 User Story 13 - Creating the four different battleship
//classes via inheritance

//I will first create the main Ship class
//I will further implement each ship size at a later stage
public class Ship
{
    private String name; //data filed (int) for the name of ship e.g. Ship1
    private String shipType; //data field for the type of ship e.g. Cargo Ship

    public Ship(String name, String shipType) //Constructor
    {
        this.name = name;
        this.shipType = shipType;
    }

    //accessors and mutators
    public String returnName()
    {
        return this.name;
    }

    public String returnType()
    {
        return this.shipType;
    }

    public boolean equals(Ship other)
    {
        return false;
    }

    public String toString() //This toString displays the ship name alongside they type of ship it is
    {
        return "[Name: " + this.name + ". Ship Type: " + this.shipType + "]";
    }

}

