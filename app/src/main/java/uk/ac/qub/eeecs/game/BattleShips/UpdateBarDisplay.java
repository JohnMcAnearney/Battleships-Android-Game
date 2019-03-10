package uk.ac.qub.eeecs.game.BattleShips;
import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;


public class UpdateBarDisplay extends GameObject {

    private static final int WIDTH_FACTOR = 3;
    private static final int HEIGHT_FACTOR = 5;


    private float minVal, maxVal, value;
    private GameObject[] barBits;

    public UpdateBarDisplay(int numberOfBits, float initialValue, float minValue, float maxValue, float startX, float startY, float scale, GameScreen gameScreen) {
        super(startX, startY, scale * WIDTH_FACTOR * numberOfBits, scale * HEIGHT_FACTOR, null, gameScreen);
        loadAssets();
        init(numberOfBits, initialValue, minValue, maxValue);


    }
    private void loadAssets(){
        mGameScreen.getGame().getAssetManager().loadAndAddBitmap("BitImageDefault", "img/BlankBit.png");
        mGameScreen.getGame().getAssetManager().loadAndAddBitmap("BitImageHigh", "img/BlankBitHigh.png");
        mGameScreen.getGame().getAssetManager().loadAndAddBitmap("BitImageLow", "img/BlankBitLow.png");
        mGameScreen.getGame().getAssetManager().loadAndAddBitmap("BitImageMedium", "img/BlankBitMedium.png");

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
            } else if (i >= barBits.length / 1.5) {
                bitType = "High";
            } else if (i >= barBits.length / 3) {
                bitType = "Medium";
            } else {
                bitType = "Low";
            }

            Bitmap bitBitmap = mGameScreen.getGame().getAssetManager().getBitmap("BitImage" + bitType);
            barBits[i] = new GameObject(getBound().getLeft() + offsetBetweenBits, getBound().y, getBound().getWidth() / barBits.length, getBound().getHeight(), bitBitmap, mGameScreen);
            offsetBetweenBits+= (getBound().getWidth()  / barBits.length) - (getBound().getWidth()  / barBits.length) / 6;
        }

    }


    //draw the bar;
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        for(GameObject bit : barBits){
            if(bit != null){
                bit.draw(elapsedTime, graphics2D);
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
