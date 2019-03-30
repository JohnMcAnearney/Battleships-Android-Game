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
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.BattleShips.InstructionsScreen;
import uk.ac.qub.eeecs.game.BattleShips.UpdateBarDisplay;
import uk.ac.qub.eeecs.game.DemoGame;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UpdateBarDisplayTest {
    private Context mContext;
    private DemoGame mGame;
    //Using the instructions screen to test bar instead of settings as settings screen includes share preferences.

    private InstructionsScreen mInstructionScreen;
    @Before
    public void setUp(){
        mContext = InstrumentationRegistry.getTargetContext();
        setupGameManager();
    }

    private void setupGameManager() {
        mGame = new DemoGame();
        mGame.mFileIO = new FileIO(mContext);
        AssetManager assetManager = new AssetManager(mGame);
        mGame.mAssetManager = new AssetManager(mGame);
        mGame.mAssetManager=assetManager;
        mGame.mScreenManager=new ScreenManager(mGame);
        mInstructionScreen =  new InstructionsScreen(mGame);


        assetManager.loadAndAddBitmap("BitImageDefault", "img/BlankBitPlain.png");
        assetManager.loadAndAddBitmap("BitImageColour", "img/BlankBitHigh.png");
    }

    @Test
    public void updateBarDisplay_normalValue_returnsCorrectValue() {
       UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.25f, 0f, 1f, 25f, 30f, 10,  mInstructionScreen);
       assertEquals(0.25f, updateBarDisplay.getValue());
       assertEquals(0f, updateBarDisplay.getMinVal());
       assertEquals(1f, updateBarDisplay.getMaxVal());

    }

    @Test
    public void updateBarDisplay_initialValueGreaterThanMaxValue_valueSetToMaxValue() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,2f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        assertEquals(0f, updateBarDisplay.getMinVal());
        assertEquals(1f, updateBarDisplay.getMaxVal());
        assertEquals(1f, updateBarDisplay.getValue());
    }

    @Test
    public void updateBarDisplay_initialValueLessThanMinValue_valueSetToMinValue() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,-1f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        assertEquals(0f, updateBarDisplay.getMinVal());
        assertEquals(1f, updateBarDisplay.getMaxVal());
        assertEquals(0f, updateBarDisplay.getValue());
    }

    @Test
    public void updateBarDisplay_setValueWithinLimit_increase() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.75f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        assertEquals(0f, updateBarDisplay.getMinVal());
        assertEquals(1f, updateBarDisplay.getMaxVal());
        updateBarDisplay.setValue(0.95f);
        assertEquals(0.95f, updateBarDisplay.getValue());
    }

    @Test
    public void updateBarDisplay_setValueWithinLimit_decrease() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.75f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        assertEquals(0f, updateBarDisplay.getMinVal());
        assertEquals(1f, updateBarDisplay.getMaxVal());
        updateBarDisplay.setValue(0.5f);
        assertEquals(0.5f, updateBarDisplay.getValue());
    }

    @Test
    public void updateBarDisplay_setValueOutOfLimit_increase() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.75f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        assertEquals(0f, updateBarDisplay.getMinVal());
        assertEquals(1f, updateBarDisplay.getMaxVal());
        updateBarDisplay.setValue(2f);
       assertEquals(1f, updateBarDisplay.getValue());
    }

    @Test
    public void updateBarDisplay_setValueOutOfLimit_decrease() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.75f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        assertEquals(0f, updateBarDisplay.getMinVal());
        assertEquals(1f, updateBarDisplay.getMaxVal());
        updateBarDisplay.setValue(-5f);
        assertEquals(0f, updateBarDisplay.getValue());
    }

    @Test
    public void updateBarDisplay_setMaxValue_increase() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.75f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        updateBarDisplay.setMaxVal(2f);
        assertEquals(2f, updateBarDisplay.getMaxVal());
    }

    @Test
    public void updateBarDisplay_setMaxValue_decrease() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.2f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        updateBarDisplay.setMaxVal(0.5f);
        assertEquals(0.5f, updateBarDisplay.getMaxVal());
    }

    @Test
    public void updateBarDisplay_setMinValue_increase() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.75f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        updateBarDisplay.setMinVal(0.2f);
        assertEquals(0.2f, updateBarDisplay.getMinVal());
    }
    @Test
    public void updateBarDisplay_setMinValue_decrease() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.75f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        updateBarDisplay.setMinVal(-2f);
        assertEquals(0f, updateBarDisplay.getMinVal());
    }

    @Test
    public void updateBarDisplay_setMaxValueLessInitialValue_decrease_expectValueToBeSetToNewMax() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.8f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        updateBarDisplay.setMaxVal(0.5f);
        assertEquals(0.5f, updateBarDisplay.getMaxVal());
        assertEquals(0.5f, updateBarDisplay.getValue());
    }
    @Test
    public void updateBarDisplay_setMinValueGreaterInitialValue_increase_expectValueToBeSetToNewMin() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.2f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        updateBarDisplay.setMinVal(0.5f);
        assertEquals(0.5f, updateBarDisplay.getMinVal());
        assertEquals(0.5f, updateBarDisplay.getValue());
    }

    @Test
    public void updateBarDisplay_setMaxValueLessMinValue_decrease_expectValueToBeSetToMinPlusOne() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,1.8f, 1f, 2f, 25f, 30f, 10, mInstructionScreen);
        updateBarDisplay.setMaxVal(0.5f);
        assertEquals(1.1f, updateBarDisplay.getMaxVal());
        //Changes sound to min now as max has been reduced so initial value is no longer available.
        assertEquals(1.0f, updateBarDisplay.getValue());
    }
    @Test
    public void updateBarDisplay_setMinValueGreaterThanMaxValue_increasse_expectValueToBeSetToMaxMinusOne() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.8f, 0f, 1f, 25f, 30f, 10, mInstructionScreen);
        updateBarDisplay.setMinVal(1.5f);
        assertEquals(0.9f, updateBarDisplay.getMinVal());
        assertEquals(1.0f, updateBarDisplay.getValue());
    }

    @Test
    public void updateBarDisplay_setMinLessThanZero_decreasse_expectValueToBeSetToZero() {
        UpdateBarDisplay updateBarDisplay = new UpdateBarDisplay(10,0.8f, 1f, 2f, 25f, 30f, 10, mInstructionScreen);
        updateBarDisplay.setMinVal(-0.5f);
        assertEquals(0f, updateBarDisplay.getMinVal());
    }

}