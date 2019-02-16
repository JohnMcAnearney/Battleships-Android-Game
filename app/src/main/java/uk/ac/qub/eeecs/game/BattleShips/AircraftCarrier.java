package uk.ac.qub.eeecs.game.BattleShips;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import java.lang.String;

//Aircraft Carrier class that extends from the Ship class - Hannah (40201925)
public class AircraftCarrier extends Ship   //length 5
{
    public AircraftCarrier(String shipType, float startPositionX, float startPositionY, GameScreen gameScreen)
    {
//        super("Ship3", "Aircraft Carrier");  - This is an old constructor from the previous sprint

        //New Sprint 4 constructor below - Hannah (40201925)
        super("Aircraft Carrier", startPositionX, startPositionY, gameScreen.getGame().getAssetManager().getBitmap("Aircraft Carrier"), gameScreen);
    }

    public String toString()
    {
        return "Aircraft Carrier" + super.toString();
    }
}
