package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;



public class MainMenu extends GameScreen {

    private Bitmap mBattleShipBackground;
    private int screenWidth = 0, screenHeight = 0;
    private Rect rect;
    private PushButton mStartButton, mInstructionsButton, mSettingsButton, mPauseButton, mTitle;

    public MainMenu(Game game) {
        super("MenuScreen", game);
        //Load all of the assets
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("NewGameButton", "img/NewGameB.png");
        assetManager.loadAndAddBitmap("BattleshipBackground", "img/background.jpg");
        assetManager.loadAndAddBitmap("InstructionsButton", "img/InstructionsB.png");
        assetManager.loadAndAddBitmap("SettingsButton", "img/SettingsB.png");
        assetManager.loadAndAddBitmap("NewGameButtonP", "img/NewGameBPressed.png");
        assetManager.loadAndAddBitmap("InstructionsButtonP", "img/InstructionsBPressed.png");
        assetManager.loadAndAddBitmap("SettingsButtonP", "img/SettingsBPressed.png");
        assetManager.loadAndAddBitmap("PauseButton", "img/Pause.png");
        assetManager.loadAndAddBitmap("Title", "img/Title.png");
        mBattleShipBackground = assetManager.getBitmap("BattleshipBackground");


    }



    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            // Update each button and transition if needed
            mStartButton.update(elapsedTime);
            mInstructionsButton.update(elapsedTime);
            mSettingsButton.update(elapsedTime);
            mPauseButton.update(elapsedTime);
            //mTitle.update(elapsedTime);

            //Trigger a screen
            if (mStartButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new BoardSetupScreen(mGame));
            } else if (mInstructionsButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));
            } else if (mSettingsButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new SettingsScreen(mGame));
                //Push trigger to check if the Pause button has been pressed, if it is, create new Pause Screen
            } else if (mPauseButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new PauseScreen(mGame));
            }else if(mTitle.isPushTriggered()){
                //add functionalitys - sound plays etc.
            }
        }
    }



    public void getWidthAndHeightOfScreen(IGraphics2D graphics2D) {

        if (screenHeight == 0 || screenWidth == 0) {
            screenWidth = graphics2D.getSurfaceWidth();
            screenHeight = graphics2D.getSurfaceHeight();
            createButton();
            updateRect();

        }

    }

    public void updateRect() {
        rect = new Rect(0, 0, screenWidth, screenHeight);
    }

    //Create the buttons and set the sound to true;
    public void createButton() {

        mStartButton = new PushButton(mDefaultLayerViewport.getWidth() / 2,mDefaultLayerViewport.getHeight()  / 2, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "NewGameButton", "NewGameButtonP", this);
        mStartButton.setPlaySounds(true, true);

        mInstructionsButton = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() / 5.5f, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "InstructionsButton", "InstructionsButtonP", this);
        mInstructionsButton.setPlaySounds(true, true);

        mSettingsButton = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() / 3f, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "SettingsButton", "SettingsButtonP", this);
        mSettingsButton.setPlaySounds(true, true);

        mPauseButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.9f, mDefaultLayerViewport.getHeight() * 0.9f, mDefaultLayerViewport.getWidth() * 0.05f, mDefaultLayerViewport.getHeight() * 0.05f, "PauseButton", this);
        mPauseButton.setPlaySounds(true, true);

        mTitle = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() /1.3f , mDefaultLayerViewport.getWidth() / 2f, mDefaultLayerViewport.getHeight()/ 3f, "Title", this);
    }

    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */


    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mBattleShipBackground, null, rect, null);

        mStartButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mInstructionsButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mSettingsButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mPauseButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mTitle.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
    }
}