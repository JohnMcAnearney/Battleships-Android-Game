package uk.ac.qub.eeecs.game.BattleShips;

//this class will be responsible for displaying the board. On this screen the user will be able to setup their
//fleet with their ships in whatever way they wish

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class BoardSetupScreen extends GameScreen {

    private Bitmap BoardSetupBackground;
    private PushButton mBackButton;
    private Paint paint = new Paint();
    private Canvas canvas = new Canvas();
    private String message = "Not Detected", message2  ="";
    private float x,y;
    private float bigBoxLeftCoor=0;       //i could do a test method for these, testing if i change these variables that they all still fit in the
    private float bigBoxTopCoor=0;         //screen and if not set it back so it fits in screen
    private float bigBoxRightCoor=0;     //simple if right>screenwidth then return false and fix
    private float bigBoxBottomCoor=0;



    public BoardSetupScreen(Game game){
        super("BoardSetupBackground", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        assetManager.loadAndAddBitmap("WaterBackground", "img/Water_Tile.png");
        BoardSetupBackground = assetManager.getBitmap("WaterBackground");


    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();
       List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            for(TouchEvent touchEvent: touchEvents) {
                y = touchEvent.y;
                x = touchEvent.x;

            }
            if( y < bigBoxBottomCoor )
            {
                message = "detected big box";
            }
            else
                message = "Not detected";

//            // Update each button and transition if needed
//            mBackButton.update(elapsedTime);
//            if (mBackButton.isPushTriggered()){
//                mGame.getScreenManager().removeScreen(this);
//            }

       }
       message2 = "X CoOr: "+String.valueOf(x) + "\n" +"YcoOr:" + String.valueOf(y);
    }

    Paint textPaint = new Paint();
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);
        drawBoard(graphics2D);
        drawShips();

        textPaint.setTextSize(50.0f);
        textPaint.setTextAlign(Paint.Align.LEFT);
        graphics2D.drawText(message, 100.0f, 100.0f, textPaint);
        graphics2D.drawText(message2, 100.0f, 200.0f, textPaint);

    }

    public void drawBoard(IGraphics2D graphics2D){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        int screenWidth = graphics2D.getSurfaceWidth();
        int screenHeight = graphics2D.getSurfaceHeight();

         bigBoxLeftCoor = (screenWidth/14f);       //i could do a test method for these, testing if i change these variables that they all still fit in the
         bigBoxTopCoor = screenHeight/5f;          //screen and if not set it back so it fits in screen
         bigBoxRightCoor = bigBoxLeftCoor*6f;      //simple if right>screenwidth then return false and fix
         bigBoxBottomCoor = (bigBoxTopCoor*4.5f);

        float smallBoxWidth = (bigBoxRightCoor - bigBoxLeftCoor)/10f;       // these two must be added to the value as they are the square dimensions
        float smallBoxHeight = (bigBoxBottomCoor - bigBoxTopCoor)/10f;

        //step 1 - draw rectangle           for these values i could do another method
        graphics2D.drawRect(bigBoxLeftCoor, bigBoxTopCoor,
                bigBoxRightCoor, bigBoxBottomCoor, paint);

        //Step 2 - draw lots of smaller squares
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLUE);

        for(int rows =0; rows<10; rows++) {
            float moveConstLeft = 0;
            for (int column = 0; column < 10; column++) {


                //draw each of the small boxes
                graphics2D.drawRect((bigBoxLeftCoor + moveConstLeft), bigBoxTopCoor,      //same start position
                        (bigBoxLeftCoor + smallBoxWidth + moveConstLeft), (bigBoxTopCoor + smallBoxHeight), paint);
                moveConstLeft += smallBoxWidth;

                //step 2 - draw horizontal lines
//        for (int horLines = 0; horLines<9;horLines++){
//            //make an array of points, each consecutive 4 points constitutes to a single line therefore, for loop with four variables, increment by whatever each time
//            float x1, y1, x2, y2;
//            float y1y2ChangeConstant  = 30; //each block is 30 in height therefore change both y's by block height
//
//            x1 = graphics2D.getSurfaceWidth()/14;
//            y1 = (graphics2D.getSurfaceHeight()/5) + y1y2ChangeConstant;
//            x2 = (graphics2D.getSurfaceWidth()/14)*5;
//            y2 = (graphics2D.getSurfaceHeight()/5)*3;
//
//                                                                        THIS IS A BETTER METHOD TO DRAW BUT I JUST NEED SOMETHING FOR SPRINT 4
//                                                                        NEED TO FIGURE OUT HOE TO DRAW LINES THAT AC DISPLAY BECAUSE CURRENTLY ONLY DISPLAY WHEN USING GRAPHICS2D
//            final float[] linePoints = {x1,y1,x2,y2};
//            canvas.drawLines(linePoints, paint);
//        }
            }
            bigBoxTopCoor+=smallBoxHeight;
            bigBoxBottomCoor+=smallBoxHeight;
        }
    }

    public void drawShips(){
        //draw them to the screen for now using bitmaps
    }
}
