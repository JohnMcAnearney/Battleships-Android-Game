package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.Music;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class   SettingsScreen extends GameScreen {
    //Background
    private Bitmap mSettingsBackground;
    private int screenWidth = 0, screenHeight = 0;
    private Rect rect;
    private PushButton mBackButton, mIncreaseMusicButton, mDecreaseMusicButton, mIncreaseEffectButton, mDecreaseEffectButton, mMuteMusicButton;
    private AudioManager audioManager = getGame().getAudioManager();
    private Music musicOnScreen;
    private Sound effectSound;

    private UpdateBarDisplay musicBarDisplay, effectsBarDisplay;

    //Constructor;
    public SettingsScreen(Game game) {
        super("SettingsScreen", game);
        loadAssets();
        playBackgroundMusic();
        createBarDisplayVolume();
        createButton();
        musicOnScreen=mGame.getAssetManager().getMusic("RickRoll");
    }


    public void loadAssets(){

        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("SettingBackground","img/background.jpg" );
        assetManager.loadAndAddBitmap("SettingsBackButton", "img/BackB.png");
        assetManager.loadAndAddBitmap("SettingsBackButtonP", "img/BackBPressed.png");
        assetManager.loadAndAddBitmap("SettingsTitle", "img/SettingsTitle.png");
        assetManager.loadAndAddBitmap("MuteButton", "img/VolumeOff.png");
        assetManager.loadAndAddBitmap("UnmuteButton", "img/VolumeOn.png");

        assetManager.loadAndAddBitmap("IncreaseMusic", "img/RightArrow.png");
        assetManager.loadAndAddBitmap("DecreaseMusic", "img/LeftArrow.png");
        mSettingsBackground = assetManager.getBitmap("SettingBackground");
        mGame.getAssetManager().loadAssets("txt/assets/SpaceShipDemoSpaceAssets.JSON");

    }
    /*
     * drawing
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        getWidthAndHeightOfScreen(graphics2D);

        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mSettingsBackground, null, rect, null);
        mIncreaseMusicButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mDecreaseMusicButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mMuteMusicButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        musicBarDisplay.draw(elapsedTime, graphics2D);
        mBackButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

    }
    public void getWidthAndHeightOfScreen(IGraphics2D graphics2D) {

        if (screenHeight == 0 || screenWidth == 0) {
            screenWidth = graphics2D.getSurfaceWidth();
            screenHeight = graphics2D.getSurfaceHeight();
            updateRect();
            //createBarDisplayVolume();
           // createButton();

        }

    }
    public void updateRect() {
        rect = new Rect(0, 0, screenWidth, screenHeight);
    }

    public void createBarDisplayVolume(){
        musicBarDisplay = new UpdateBarDisplay(10, audioManager.getMusicVolume(), 0f, 1f, mDefaultLayerViewport.getWidth()+500.0f, mDefaultLayerViewport.getHeight()-50, 20, this );

    }
    public void createButton() {

        //Button for the music up and down
        mIncreaseMusicButton = new PushButton(
                musicBarDisplay.getBound().getLeft() -360,musicBarDisplay.getBound().y-30, 20.0f,
                20.0f, "IncreaseMusic",  this);
        mDecreaseMusicButton=new PushButton(
                musicBarDisplay.getBound().getLeft() -550, musicBarDisplay.getBound().y-30,
                20.0f, 20.0f, "DecreaseMusic",  this);




        //BackButton
        mBackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
                "SettingsBackButton","SettingsBackButtonP",  this);
        mBackButton.setPlaySounds(true, true);
        mMuteMusicButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.5f, mDefaultLayerViewport.getHeight() * 0.30f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f, "MuteButton", this);

    }

    public void playBackgroundMusic() {
        if(!audioManager.isMusicPlaying())
            audioManager.playMusic(
                    getGame().getAssetManager().getMusic("RickRoll"));
    }






    @Override
    public void update(ElapsedTime elapsedTime) {
        //For the touch events since update
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        //playBackgroundMusic();
        if(touchEvents.size()>0){
            updateAllButtons(elapsedTime);
            pressedButtonsActions();
        }
    }

    public void updateAllButtons (ElapsedTime elapsedTime){
        mBackButton.update(elapsedTime);
        mIncreaseMusicButton.update(elapsedTime);
        mDecreaseMusicButton.update(elapsedTime);
        mMuteMusicButton.update(elapsedTime);

//        mDecreaseEffectButton.update(elapsedTime);
        //      mDecreaseEffectButton.update(elapsedTime);

    }
    public void pressedButtonsActions(){
        if(mIncreaseMusicButton.isPushTriggered()){
            audioManager.setMusicVolume(audioManager.getMusicVolume()+0.1f);
            musicBarDisplay.setValue(audioManager.getMusicVolume());
            audioManager.stopMusic();
            audioManager.playMusic(musicOnScreen);
            musicBarDisplay.update();

        }

        if(mDecreaseMusicButton.isPushTriggered()){
            audioManager.setMusicVolume(audioManager.getMusicVolume()-0.1f);
            musicBarDisplay.setValue(audioManager.getMusicVolume());
            audioManager.stopMusic();
            audioManager.playMusic(musicOnScreen);
            musicBarDisplay.update();
        }
/*
        if(mIncreaseEffectButton.isPushTriggered()){
            audioManager.setSfxVolume(audioManager.getSfxVolume()+0.1f);
           //play the volume
        }

        if(mDecreaseEffectButton.isPushTriggered()){
            audioManager.setSfxVolume(audioManager.getSfxVolume()-0.1f);

        }*/
        if(mMuteMusicButton.isPushTriggered()){
            if(audioManager.isMusicPlaying()){
                audioManager.stopMusic();
                mMuteMusicButton.setBitmap(mGame.getAssetManager().getBitmap("MuteButton"));

            }else{
                mMuteMusicButton.setBitmap(mGame.getAssetManager().getBitmap("UnmuteButton"));
                playBackgroundMusic();
            }

            //audioManager.stopMusic();
          //  audioManager.playMusic(musicOnScreen);

        }
        if(mBackButton.isPushTriggered()){ 
            //back to mainmenu
            mGame.getScreenManager().addScreen(new MainMenu(mGame));
        }

    }
}
