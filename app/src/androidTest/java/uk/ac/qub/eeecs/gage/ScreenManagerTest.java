package uk.ac.qub.eeecs.gage;
//@referenced Avant
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import android.support.test.InstrumentationRegistry;


import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.DemoGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScreenManagerTest
{
    private Context context;
    private DemoGame game;
    String mainMenuName = "Main Menu";
    String gameScreenName = "Game Screen";

    @Mock
    GameScreen mainMenu = Mockito.mock(GameScreen.class);
    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);

    @Before
    public void setUp()
    {
        context = InstrumentationRegistry.getTargetContext();
        setupGameManager();
        when(mainMenu.getName()).thenReturn(mainMenuName);
        when(gameScreen.getName()).thenReturn(gameScreenName);
    }

    public void setupGameManager()
    {
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mScreenManager = new ScreenManager(game);
    }

    @Test
    public void addScreen_Validity_TestSuccess() throws Exception
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        assertEquals(mainMenu, manager.getCurrentScreen());
    }

    @Test
    public void setAsCurrentScreen_Validity_TestSuccess() throws Exception
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        manager.addScreen(gameScreen);
        manager.setAsCurrentScreen(gameScreenName);
        assertEquals(gameScreenName, manager.getCurrentScreen().getName());
    }

    @Test
    public void getCurrentScreen_Validity_TestSuccess() throws Exception
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        manager.addScreen(gameScreen);
        assertEquals(mainMenu, manager.getCurrentScreen());
    }

    @Test
    public void getScreen_Validity_TestSuccess() throws Exception
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        manager.addScreen(gameScreen);
        assertEquals(mainMenu, manager.getScreen(mainMenuName));
    }

    @Test
    public void removeScreen_Validity_TestSuccess() throws Exception
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        manager.addScreen(gameScreen);
        assertTrue(manager.removeScreen(mainMenuName));
    }

    @Test
    public void removeScreen_NotFound_TestSuccess() throws Exception
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        manager.addScreen(gameScreen);
        manager.removeScreen(mainMenuName);
        assertFalse(manager.removeScreen(mainMenuName));
    }



}
