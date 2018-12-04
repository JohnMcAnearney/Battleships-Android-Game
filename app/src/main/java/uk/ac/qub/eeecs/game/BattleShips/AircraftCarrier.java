package uk.ac.qub.eeecs.game.BattleShips;

//Aircraft Carrier class that extends from the Ship class
public class AircraftCarrier extends Ship
{
    public AircraftCarrier()
    {
        super("Ship3", "Aircraft Carrier"); //Ship number 3 and ship type
    }

    public AircraftCarrier(String name, String shipType)
    {
        super(name, shipType);
    }

    public String toString()
    {
        return "Aircraft Carrier" + super.toString();
    }
}