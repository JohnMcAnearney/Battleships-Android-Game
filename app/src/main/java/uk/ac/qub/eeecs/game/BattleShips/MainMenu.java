package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

// Created by Edgars(40203154) & Aileen(40207942) & Mantas()
// Class created by Mantas()
// Class populated by Aileen(40207942)
// Class refactored by Edgars(40203154)
public class MainMenu extends GameScreen {

    // Defining variables to be used for the main menu screen background
    private Bitmap mBattleShipBackground;
    private int screenWidth = 0, screenHeight = 0;
    private Rect rect;

    // Defining all the buttons and a list which will store all of the buttons
    private PushButton mStartButton, mInstructionsButton, mSettingsButton, mTitle;
    private List<PushButton> mButtonCollection = new ArrayList<>();

    /*
    CONSTRUCTOR
    */
    public MainMenu(Game game) {
        super("MenuScreen", game);
        // Load all of the assets
        loadAssets();

        // Create all of the push buttons
        createButtons();
    }

    // Update method for the MainMenu class
    @Override
    public void update(ElapsedTime elapsedTime)
    {
        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0)
        {
            // Update all the push buttons if a touch event is detected.
            updateAllPushButtons(elapsedTime);

            // Method which performs all the push button actions
            performPushButtonActions();
        }
    }

    // Draw method for the MainMenu class
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        // Draw the battleship background
        drawScreenBackground(graphics2D);

        // Draw all the push buttons
        drawButtons(elapsedTime, graphics2D);
    }

    /*
    METHODS
    */
    // Method which gets the screen width and height of the device screen
    private void getWidthAndHeightOfScreen(IGraphics2D graphics2D)
    {
        if (screenHeight == 0 || screenWidth == 0)
        {
            screenWidth = graphics2D.getSurfaceWidth();
            screenHeight = graphics2D.getSurfaceHeight();
            updateRect();
        }
    }

    // Method which creates a new rectangle size of the screen
    private void updateRect() {
        rect = new Rect(0, 0, screenWidth, screenHeight);
    }

    // Create the buttons and set the sound to true
    private void createButtons()
    {
        mStartButton = new PushButton(mDefaultLayerViewport.getWidth() / 2,mDefaultLayerViewport.getHeight()  / 2, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "NewGameButton", "NewGameButtonP", this);
        mStartButton.setPlaySounds(true, true);
        mButtonCollection.add(mStartButton);

        mInstructionsButton = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() / 5.5f, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "InstructionsButton", "InstructionsButtonP", this);
        mInstructionsButton.setPlaySounds(true, true);
        mButtonCollection.add(mInstructionsButton);

        mSettingsButton = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() / 3f, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "SettingsButton", "SettingsButtonP", this);
        mSettingsButton.setPlaySounds(true, true);
        mButtonCollection.add(mSettingsButton);

        mTitle = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() /1.3f , mDefaultLayerViewport.getWidth() / 2f, mDefaultLayerViewport.getHeight()/ 3f, "Title", this);
        mButtonCollection.add(mTitle);
        // Why is this a button? I think this is a mistake but it could be used for an easter egg
    }

    // Method which loads all the assets for the screen
    private void loadAssets()
    {
        AssetManager assetManager = mGame.getAssetManager();
        mGame.getAssetManager().loadAssets("txt/assets/MainMenuScreenAssets.JSON");
        mBattleShipBackground = assetManager.getBitmap("BattleshipBackground");
    }

    // Method which draws all of the push buttons
    private void drawButtons(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        for(PushButton button: mButtonCollection)
        {
            button.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        }
    }

    // Method which draws the screen background
    private void drawScreenBackground(IGraphics2D graphics2D)
    {
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mBattleShipBackground,null,rect,null);
    }


    // Method which carries out all the push button updates
    private void updateAllPushButtons(ElapsedTime elapsedTime)
    {
        for(PushButton button : mButtonCollection)
        {
            button.update(elapsedTime);
        }
    }

    // Method which executes the actions of all the push buttons
    private void performPushButtonActions()
    {
        // Push trigger if statement to check if a specific button has been pressed, if it is, create the appropriate screen
        if (mStartButton.isPushTriggered())
        {
            mGame.getScreenManager().addScreen(new LoadingScreen(mGame));
        }
        else if (mInstructionsButton.isPushTriggered())
        {
            mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));
        }
        else if (mSettingsButton.isPushTriggered())
        {
            mGame.getScreenManager().addScreen(new SettingsScreen(mGame));
        }
        else if(mTitle.isPushTriggered())
        {
            // Add functionality - sound plays etc.
        }
    }
}