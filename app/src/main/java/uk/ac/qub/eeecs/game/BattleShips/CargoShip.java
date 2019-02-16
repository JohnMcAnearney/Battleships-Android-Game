package uk.ac.qub.eeecs.game.BattleShips;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import java.lang.String;

//Cargo Ship class that extends from the Ship class - Hannah (40201925)
public class CargoShip extends Ship     //length 4
{
    public CargoShip(String shipType, float startPositionX, float startPositionY, GameScreen gameScreen)
    {
        //super("Ship2", "Cargo Ship"); This is the old constructor from the previous sprint (Sprint 3) - Hannah (40201925)

        //This is the new constructor - Hannah (40201925) Sprint 4
        super("Cargo Ship", startPositionX, startPositionY, gameScreen.getGame().getAssetManager().getBitmap("Cargo Ship"), gameScreen);
    }

    public String toString()
    {
        return "Cargo Ship" + super.toString();
    }
}


