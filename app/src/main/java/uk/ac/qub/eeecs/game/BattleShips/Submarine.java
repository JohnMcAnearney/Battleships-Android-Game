package uk.ac.qub.eeecs.game.BattleShips;
import android.graphics.Bitmap;

//Submarine class that extends from the Ship class - Hannah (40201925)
public class Submarine extends Ship
{
    public Submarine(String shipType, float startPositionX, float startPositionY, Bitmap bitmap)
    {
        //super("Ship4", "Submarine"); //This is the old construcotr from the previous sprint (Sprint 3) - Hannah (40201925)

        //This is the new constructor - Sprint 4 - Hannah (40201925)
        super("Submarine", 0,0, "img/Submarine.png" );
    }

    public String toString()
    {
        return "Submarine" + super.toString();
    }
}

