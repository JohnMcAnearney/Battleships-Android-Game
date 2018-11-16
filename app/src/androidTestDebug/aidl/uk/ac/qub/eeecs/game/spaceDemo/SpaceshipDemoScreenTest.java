package uk.ac.qub.eeecs.game.spaceDemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import org.junit.runners.JUnit4;

//import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SpaceshipDemoScreenTest {

    private Game mGame;
    AudioManager audioManager = mGame.getAudioManager();

//    @Before
//    public void setUp(){
//        AudioManager audioManager = mGame.getAudioManager();
//
//    }

    @Test
    public void playBackgroundMusic_ValidData(){
        //gets and play music
        audioManager.playMusic(mGame.getAssetManager().getMusic("RickRoll"));
        //loaded in music now assert if it is true. The isMusicPlaying() method returns true  if CurrentMusic != null && mCurrentMusic.isPlaying();
        assertTrue(audioManager.isMusicPlaying());
        //therefore this should return true and be ok
    }

    @Test
    public void playBackgroundMusic_inValidData(){
        //gets and play music, but it cant as assetname is wrong
        audioManager.playMusic(mGame.getAssetManager().getMusic("RickRoll6969"));
        //loaded in music now assert if it is true. The isMusicPlaying() method returns true  if CurrentMusic != null && mCurrentMusic.isPlaying();
        assertTrue(audioManager.isMusicPlaying());
        //therefore this should return false and say test failed
    }




}
