package uk.ac.qub.eeecs.gage;
//@referenced package uk.ac.qub.eeecs.gage.Avant

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.DemoGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Hannah Cunningham (40201925)
 */

@RunWith(MockitoJUnitRunner.class)
public class ScreenManagerTest
{

    private DemoGame game;
    private String mainMenuName = "Main Menu";
    private String gameScreenName = "Game Screen";


    @Mock
    private GameScreen mainMenu = Mockito.mock(GameScreen.class);
    @Mock
    private GameScreen gameScreen = Mockito.mock(GameScreen.class);

    @Before
    public void setUp()
    {
        when(mainMenu.getName()).thenReturn(mainMenuName);
        when(gameScreen.getName()).thenReturn(gameScreenName);
    }

    //test method to addScreen successfully
    @Test
    public void addScreen_Test()
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        assertEquals(mainMenu, manager.getCurrentScreen());
    }

    //test method to retrieve current screen successfully
    @Test
    public void getCurrentScreen_Test()
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        manager.addScreen(gameScreen);
        assertEquals(mainMenu, manager.getCurrentScreen());
    }

    //test method to retrieve gameScreen/mainMenu screen successfully
    @Test
    public void getScreen_Test()
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        manager.addScreen(gameScreen);
        assertEquals(mainMenu, manager.getScreen(mainMenuName));
    }

    //test method to remove screen successfully
    @Test
    public void removeScreen_Test()
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        manager.addScreen(gameScreen);
        assertTrue(manager.removeScreen(mainMenuName));
    }

    //test method to remove screen when unsuccessfully
    @Test
    public void removeScreen_NotFound_Test()
    {
        ScreenManager manager = new ScreenManager(game);
        manager.addScreen(mainMenu);
        manager.addScreen(gameScreen);
        manager.removeScreen(mainMenuName);
        assertFalse(manager.removeScreen(mainMenuName));
    }
}
