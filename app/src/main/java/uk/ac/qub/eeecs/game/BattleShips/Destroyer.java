
package uk.ac.qub.eeecs.game.BattleShips;
import android.graphics.Bitmap;

//Destoryer class that extends from the Ship class
public class Destroyer extends Ship
{
    public Destroyer(String shipType, float startPositionX, float startPositionY, Bitmap bitmap)
    {
        //super("Ship5", "Destroyer"); This is the old constructor from the previous sprint (Sprint 3) - Hannah (40201925)

        //This is the new constructor - Hannah (40201925) - Sprint 4
        super("Destroyer",0, 0, "img/Destroyer.png" );
    }

    public String toString()
    {
        return "Destroyer" + super.toString();
    }
}


