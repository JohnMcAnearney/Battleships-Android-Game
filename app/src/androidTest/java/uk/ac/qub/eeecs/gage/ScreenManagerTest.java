//package uk.ac.qub.eeecs.gage;
////@referenced Avant
//
//import android.content.Context;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//import android.support.test.InstrumentationRegistry;
//
//
//import uk.ac.qub.eeecs.gage.engine.ScreenManager;
//import uk.ac.qub.eeecs.gage.engine.io.FileIO;
//import uk.ac.qub.eeecs.gage.world.GameScreen;
//import uk.ac.qub.eeecs.game.DemoGame;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.when;
//
///**
// * @author Hannah Cunningham (40201925)
// */
//
//@RunWith(MockitoJUnitRunner.class)
//public class ScreenManagerTest
//{
//
//    private Context context;
//    private DemoGame game;
//    String mainMenuName = "Main Menu";
//    String gameScreenName = "Game Screen";
//
//    @Mock
//    GameScreen mainMenu = Mockito.mock(GameScreen.class);
//    @Mock
//    GameScreen gameScreen = Mockito.mock(GameScreen.class);
//
//    @Before
//    public void setUp()
//    {
//        //I will initialise the game's content
//        context = InstrumentationRegistry.getTargetContext();
//        setupGameManager();
//        when(mainMenu.getName()).thenReturn(mainMenuName);
//        when(gameScreen.getName()).thenReturn(gameScreenName);
//    }
//
//    public void setupGameManager()
//    {
//        //the below will initialise the Game variable with a new DemoGame()
//        game = new DemoGame();
//        //initialising the FileIO class to aid with functionality in the test class
//        game.mFileIO = new FileIO(context);
//        //initialising ScreenManager class to aid with functionality in the test class
//        game.mScreenManager = new ScreenManager(game);
//    }
//
//    //test method to addScreen successfully
//    @Test
//    public void addScreen_Test() throws Exception
//    {
//        ScreenManager manager = new ScreenManager(game);
//        manager.addScreen(mainMenu);
//        assertEquals(mainMenu, manager.getCurrentScreen());
//    }
//
//    //test method to set the current screen successfully
//    @Test
//    public void setAsCurrentScreen_Test() throws Exception
//    {
//        ScreenManager manager = new ScreenManager(game);
//        manager.addScreen(mainMenu);
//        manager.addScreen(gameScreen);
//        manager.setAsCurrentScreen(gameScreenName);
//        assertEquals(gameScreenName, manager.getCurrentScreen().getName());
//    }
//
//    //test method to retrieve current screen successfully
//    @Test
//    public void getCurrentScreen_Test() throws Exception
//    {
//        ScreenManager manager = new ScreenManager(game);
//        manager.addScreen(mainMenu);
//        manager.addScreen(gameScreen);
//        assertEquals(mainMenu, manager.getCurrentScreen());
//    }
//
//    //test method to retrieve gameScreen/mainMenu screen successfully
//    @Test
//    public void getScreen_Test() throws Exception
//    {
//        ScreenManager manager = new ScreenManager(game);
//        manager.addScreen(mainMenu);
//        manager.addScreen(gameScreen);
//        assertEquals(mainMenu, manager.getScreen(mainMenuName));
//    }
//
//    //test method to remove screen successfully
//    @Test
//    public void removeScreen_Test() throws Exception
//    {
//        ScreenManager manager = new ScreenManager(game);
//        manager.addScreen(mainMenu);
//        manager.addScreen(gameScreen);
//        assertTrue(manager.removeScreen(mainMenuName));
//    }
//
//    //test method to remove screen when unsuccessfully
//    @Test
//    public void removeScreen_NotFound_Test() throws Exception
//    {
//        ScreenManager manager = new ScreenManager(game);
//        manager.addScreen(mainMenu);
//        manager.addScreen(gameScreen);
//        manager.removeScreen(mainMenuName);
//        assertFalse(manager.removeScreen(mainMenuName));
//    }
//}
