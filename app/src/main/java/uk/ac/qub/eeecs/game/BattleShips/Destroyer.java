package uk.ac.qub.eeecs.game.BattleShips;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import java.lang.String;

//Destoryer class that extends from the Ship class
public class Destroyer extends Ship         //length 3
{
    public Destroyer(String shipType, float startPositionX, float startPositionY, GameScreen gameScreen)
    {
        //super("Ship5", "Destroyer"); This is the old constructor from the previous sprint (Sprint 3) - Hannah (40201925)

        //This is the new constructor - Hannah (40201925) - Sprint 4
        super("Destroyer", startPositionX, startPositionY, gameScreen.getGame().getAssetManager().getBitmap("Destroyer"), gameScreen);
    }

    public String toString()
    {
        return "Destroyer" + super.toString();
    }
}


