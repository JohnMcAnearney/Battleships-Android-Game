/*
package uk.ac.qub.eeecs.game.BattleShips;
*/
/*
@author Hannah Cunningham (40201925) - Unit test for the splash screen class
 *//*


import android.content.Context;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.DemoGame;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidUnit4.class)
public class SplashScreenTest
{

    Context mContext;
    @Mock
    DemoGame mGame;
    ElapsedTime mElapsedTime;

    //I will first initialise the game's content
    @Before
    public void startup() {
        //the method below will initialise the game's content
        mContext = InstrumentationRegistry.getTargetContext();
        mGame = mock(DemoGame.class);

//the method below will act as a mock to the assetManager class to aid with testing
        AssetManager testAssetManager = mock(AssetManager.class);
        when(mGame.getAssetManager()).thenReturn(testAssetManager);

//the method below will act as a mock to the screenManager class to allow for testing of the screen transitions within the splash screen class
        ScreenManager testScreenManager = new ScreenManager(mGame);
        when(mGame.getScreenManager()).thenReturn(testScreenManager);

//the method below will act as a mock to the FileIO class to aid witht testing
        FileIO testFileIO = mock(FileIO.class);
        when(mGame.getFileIO()).thenReturn(testFileIO);

//variables for the test class
        mElapsedTime = new ElapsedTime();
        AssetManager assetManager = new AssetManager(mGame);
        mGame.mAssetManager = new AssetManager(mGame);
        mGame.mAssetManager = assetManager;

// Loading the splashScreen Assets
        String assetsToLoadJSONFile = "txt/assets/SplashScreenAssets.JSON";
        mGame.getAssetManager().loadAssets("txt/assets/SplashScreenAssets.JSON");

//FileIO initialisation
        FileIO fileIO = new FileIO(mContext);
        mGame.mFileIO = fileIO;
    }
        @Test
    public void testGoToMenuScreenMethod()
    {
        SplashScreen splashScreen = new SplashScreen(mGame);
        mGame.getScreenManager().addScreen(splashScreen);

        MainMenu mainMenu = new MainMenu(mGame);
        mGame.getScreenManager().addScreen(mainMenu);

        splashScreen.goToMenuScreen();

        Assert.assertEquals(mGame.getScreenManager().getCurrentScreen().getName(),mainMenu.getName());
    }

}
*/
