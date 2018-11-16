package uk.ac.qub.eeecs.gage;

//package uk.ac.qub.eeecs.game.cardDemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.cardDemo.Card;
import uk.ac.qub.eeecs.game.cardDemo.CardDemoScreen;

import static org.junit.Assert.*;
//@RunWith(AndroidJUnit4.class)
public class CardDemoScreenTest {
    private Context context;
    private DemoGame game;
    @Before
    public void setUp(){
        context = InstrumentationRegistry.getTargetContext();
        setupGameManager();
    }

    private void setupGameManager() {
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        AssetManager assetManager = new AssetManager(game);
        game.mAssetManager = new AssetManager(game);
        game.mAssetManager=assetManager;
        game.mScreenManager= new ScreenManager(game);
    }

    @Test
    public void test_ensuresCardCantLeaveWorld_yesNeedMoved() {
        //SetUpTest so that is out of world and moves them back
        CardDemoScreen cardDemoGameScreen = new CardDemoScreen(game);
        //set the card to outside the level.
        float x1 = 5100.0f, y1 = 7100.0f;
        float LEVEL_WIDTH = (1000.0F)*2;
        float LEVEL_HEIGHT = (1000.0F)*2;
        Card card1 = new Card(x1,y1, cardDemoGameScreen);
        //RunTest
        //Run the method that should take the position of x and y and put it back into the level
        cardDemoGameScreen.ensuresCardCantLeaveWorld(card1);
        //ExpectedOutcome
        //Finds the distance between the card and the screen at start and then the end after the method called.
        //The distance at start should be greater than the end when the card is moved back on screen
        Vector2 disStart = new Vector2(x1-LEVEL_WIDTH, y1-LEVEL_HEIGHT);
        Vector2 disEnd = new Vector2 (card1.position.x-LEVEL_WIDTH, card1.position.y-LEVEL_HEIGHT );

        //Distance at start greater meaning the card has been moved back on screen by the method
        Assert.assertTrue(disStart.length()>disEnd.length());
    }
    @Test
    public void test_ensuresCardCantLeaveWorld_noDoesntNeedMoved() {

        //SetUpTest so that is out of world and moves them back
        CardDemoScreen cardDemoGameScreen = new CardDemoScreen(game);
        //set the card to inside the level - nothing should be moved by method
        float x1 = 400.0f, y1 = 200.0f;
        //set the level width
        float LEVEL_WIDTH = (1000.0F)*2;
        float LEVEL_HEIGHT = (1000.0F)*2;
        Card card1 = new Card(x1,y1, cardDemoGameScreen);
        //RunTest
        cardDemoGameScreen.ensuresCardCantLeaveWorld(card1);
        //ExpectedOutcome
        //The distance at the start and the end should be the same as the method shouldn't be called
        Vector2 disStart = new Vector2(x1-LEVEL_WIDTH, y1-LEVEL_HEIGHT);
        Vector2 disEnd = new Vector2 (card1.position.x-LEVEL_WIDTH, card1.position.y-LEVEL_HEIGHT );

        //Calls true that the distance is the same at start and end
        //This becasue the card is on screen and doesn't need moved.
        Assert.assertTrue(disStart.length()==disEnd.length());
    }
}