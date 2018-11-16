package uk.ac.qub.eeecs.game;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import java.util.Random;
import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;

import uk.ac.qub.eeecs.gage.world.GameScreen;


public class PerformanceScreen extends GameScreen {


    //Create random object
    private Random random = new Random();
    //Create blank assetManager
    private AssetManager assetManager;
    //to store the bitmap of the rectangle
    private Bitmap rectangle;

    //store the number of objects created
    private int numberOfObjectsCreated = 0;
    //total number of objects to create
    private  int numberOfObjectsToCreate = 10;
    //array to store rectangles
    private Rect[] rectArray = new Rect[numberOfObjectsToCreate];
    //size of the screen
    private int width;
    private int height;

    private Rect sourceRect;


    private double rectangleLeft, rectangleTop,rectangleBottom,rectangleRight;

    public PerformanceScreen(Game game) {
        super("PerformanceScreen", game);

        //Get asset manager to load and store the image of rectangle
        assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("rectangle", "img/rectangle.jpg");
        rectangle = assetManager.getBitmap("rectangle");
        //object rectangle which will be used to draw on the screen
        sourceRect = new Rect(0, 0, rectangle.getWidth(), rectangle.getHeight());
    }

    @Override
    public void update(ElapsedTime elapsedTime) {

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);

        //getting the width and height of the screen used
        width = graphics2D.getSurfaceWidth();
        height = graphics2D.getSurfaceHeight();

        //If the desired number of objects haven't been created call method createRectangle
        if(numberOfObjectsCreated < numberOfObjectsToCreate)
        {
            createRectangle();
        }

        //Draw all of the stored rectangles
        for(int i = 0; i < numberOfObjectsCreated; i++)
        {
            graphics2D.drawBitmap(rectangle,sourceRect,rectArray[i],null);
        }
    }

    //Methods

    public void createRectangle()
    {
            //Randomizing the initial position of the image rectangle
            rectangleLeft = random.nextInt(55)+20;
            rectangleTop = random.nextInt(55)+20;

            //randomizing the height and width of the image rectangle
            rectangleBottom = random.nextInt(12)+11;
            rectangleRight = random.nextInt(12)+11;
            Rect destRect = new Rect(
                    (int) (width * rectangleLeft/100), (int) (height * rectangleTop/100), (int) (width * (rectangleLeft/100+rectangleRight/100)), (int) (height * (rectangleTop/100+rectangleBottom/100)));

            // Storing the destRect object to an array for drawing to screen
            rectArray[numberOfObjectsCreated] = destRect;
            //incrementing the variable numberOfObjectsCreated as a rectangle has been created
            numberOfObjectsCreated++;

    }
}
