package uk.ac.qub.eeecs.game.BattleShips;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import java.lang.String;

//Submarine class that extends from the Ship class - Hannah (40201925)
public class Submarine extends Ship
{
    public Submarine(String shipType, float startPositionX, float startPositionY, GameScreen gameScreen)
    {
        //super("Ship4", "Submarine"); //This is the old constructor from the previous sprint (Sprint 3) - Hannah (40201925)

        //This is the new constructor - Sprint 4 - Hannah (40201925)
        super("Submarine", startPositionX, startPositionY, gameScreen.getGame().getAssetManager().getBitmap("Submarine"), gameScreen);
}

    public String toString()
    {
        return "Submarine" + super.toString();
    }
}

