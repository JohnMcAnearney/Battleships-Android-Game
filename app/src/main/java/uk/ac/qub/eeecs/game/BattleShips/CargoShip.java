package uk.ac.qub.eeecs.game.BattleShips;
import android.graphics.Bitmap;

//Cargo Ship class that extends from the Ship class - Hannah (40201925)
public class CargoShip extends Ship
{
    public CargoShip(String shipType, float startPositionX, float startPositionY, Bitmap bitmap)
    {
        //super("Ship2", "Cargo Ship"); This is the old constructor from the previous sprint (Sprint 3) - Hannah (40201925)

        //This is the new constructor - Hannah (40201925) Sprint 4
        super("CargoShip", 0, 0, "img/CargoShip.png");
    }

    public String toString()
    {
        return "Cargo Ship" + super.toString();
    }
}

