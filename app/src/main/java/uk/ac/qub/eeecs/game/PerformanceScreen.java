package uk.ac.qub.eeecs.game;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;

public class PerformanceScreen extends GameScreen{

    //Keep count of the number of objects created
    private int numberOfObjectsCreated = 0;
    private Random random = new Random();
    private AssetManager assetManager;
    private Bitmap rectangle;

    //size of the screen
    private int width;
    private int height;


    private double rectangleLeft,rectangleTop;

    public PerformanceScreen(Game game)
    {super("PerformanceScreen", game);

        assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("rectangle","img/rectangle.jpg");
        rectangle = assetManager.getBitmap("rectangle");

    }
    @Override
    public void update(ElapsedTime elapsedTime) {

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
       graphics2D.clear(Color.WHITE);

         width = graphics2D.getSurfaceWidth();
         height = graphics2D.getSurfaceHeight();

        //draw the rectangle
        drawRectangle(graphics2D);

    }

    //Methods

    public void drawRectangle(IGraphics2D graphics2D)
    {

        if(numberOfObjectsCreated < 10)
        {
            rectangleLeft = random.nextInt(84)+1;
            rectangleTop = random.nextInt(84)+1;
            Rect sourceRect = new Rect(
                    0, 0, rectangle.getWidth(), rectangle.getHeight());
            Rect destRect = new Rect(
                    (int) (width * rectangleLeft/100), (int) (height * rectangleTop/100), (int) (width * (rectangleLeft/100+0.15)), (int) (height * (rectangleTop/100+0.15)));
            graphics2D.drawBitmap(rectangle, sourceRect, destRect,null);
            numberOfObjectsCreated++;
        }
    }
}
