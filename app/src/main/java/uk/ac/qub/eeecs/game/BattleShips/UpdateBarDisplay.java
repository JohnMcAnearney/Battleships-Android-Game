package uk.ac.qub.eeecs.game.BattleShips;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;


public class UpdateBarDisplay extends GameObject {
    //Created by AT : 40207942

    private static final int WIDTH_FACTOR = 4;
    private static final int HEIGHT_FACTOR = 4;


    private float minVal, maxVal, value;
    private GameObject[] barBits;
    private Bitmap[] bitss;
    private int moveBar = 0;
    private Paint paint = new Paint();

    public UpdateBarDisplay(int numberOfBits, float initialValue, float minValue, float maxValue, float startX, float startY, float scale, GameScreen gameScreen) {
        super(startX, startY, WIDTH_FACTOR * numberOfBits * scale, HEIGHT_FACTOR*scale, null, gameScreen);
        loadAssets();
        init(numberOfBits, initialValue, minValue, maxValue);


    }
    private void loadAssets(){
        mGameScreen.getGame().getAssetManager().loadAndAddBitmap("BitImageDefault", "img/BlankBitPlain.png");
        mGameScreen.getGame().getAssetManager().loadAndAddBitmap("BitImageColour", "img/BlankBitHigh.png");

    }



    private void init(int numberOfBits, float initialValue, float minValue, float maxValue) {

        this.barBits = new GameObject[numberOfBits];
        this.minVal=minValue;
        this.maxVal=maxValue;
        setValue(initialValue);
        update();
    }


    public void update() {
        int offsetBetweenBits = 0 ;
        for (int i = 0; i < barBits.length; i++) {

            String bitType;

            if (i >= (barBits.length * (value - minVal)) / (maxVal - minVal) ) {
                bitType = "Default";
            }else{

                bitType = "Colour";
            }
            Bitmap bitBitmap = mGameScreen.getGame().getAssetManager().getBitmap("BitImage" + bitType);
           // bitss[i]= new Bitmap()
            barBits[i] = new GameObject(getBound().getLeft() + offsetBetweenBits, getBound().y, getBound().getWidth() / barBits.length, getBound().getHeight(), bitBitmap, mGameScreen);
            offsetBetweenBits+= (getBound().getWidth()  / barBits.length) - (getBound().getWidth()  / barBits.length) / 6;
        }

    }


    //draw the bar;
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D){
       /* Matrix barMatric = new Matrix();
        barMatric.setScale(2.5f, 2.5f);
        barMatric.postTranslate(-moveBar,0 );
        moveBar += elapsedTime.stepTime *50.0f;
        if(moveBar>100){
            moveBar=0;
        }
        for(Bitmap bitss: bitss){
            graphics2D.drawBitmap(bitss, barMatric, paint);

        }*/

        for(GameObject bit : barBits){
            if(bit != null){
                bit.draw(elapsedTime, graphics2D);
                //graphics2D.drawBitmap(bit, barMatric, paint);
            }

        }
    }
    //getter for value
    public float getValue(){
        return value;
    }
    //setter for value;
    public void setValue(float value){
        if(value<=minVal){
         this.value = minVal;
        }else if(value > maxVal){
            this.value = maxVal;
        }else{
            this.value = value;
        }
    }
}
