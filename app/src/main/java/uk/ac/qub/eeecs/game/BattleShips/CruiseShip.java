
package uk.ac.qub.eeecs.game.BattleShips;
import android.graphics.Bitmap;

//Cruise Ship class that extends from the Ship class - Hannah (40201925)
    public class CruiseShip extends Ship
    {
        public CruiseShip(String shipType, float startPositionX, float startPositionY, Bitmap bitmap)
        {
            //super("Ship1", "Cruise Ship");  - This is an old constructor from the previous sprint - Hannah (40201925)
            //This is the new constructor - Hannah (40201925) Sprint 4
            super("CruiseShip", 0, 0, "img/CruiseShip.png"); //Ship number 1 and ship type
        }

        public String toString() //this overrides the toString method in the base class
        {
            return "Cruise Ship" + super.toString();
        }
    }


