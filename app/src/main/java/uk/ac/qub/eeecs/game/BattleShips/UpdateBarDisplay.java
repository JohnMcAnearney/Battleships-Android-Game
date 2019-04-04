package uk.ac.qub.eeecs.game.BattleShips;
import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;


public class UpdateBarDisplay extends GameObject {
    /*Created 100% by AT: 40207942*/
    /*References: QUBBATTLE, TRIBALHUNTER, AVANT, LECTURE CODE
    /**
     * Define the properties for the settings screen;
     */
    //Properties relating to the dimensions of the bar
    private static final int WIDTH_FACTOR = 4;
    private static final int HEIGHT_FACTOR = 4;
    //Properties relating to the values to be stored
    private float mMinVal;
    private float mMaxVal;
    private float mValue;
    //The array of bits in the bar
    private GameObject[] mBarBits;
    private AssetManager mAssetManager;

    /**
     * CONSTRUCTOR
     * @param numberOfBits
     * @param initialValue
     * @param minValue
     * @param maxValue
     * @param startX
     * @param startY
     * @param scale
     * @param gameScreen
     */
    public UpdateBarDisplay(int numberOfBits, float initialValue, float minValue, float maxValue, float startX, float startY, float scale, GameScreen gameScreen) {
        super(startX, startY, WIDTH_FACTOR * numberOfBits * scale, HEIGHT_FACTOR*scale, null, gameScreen);
        mAssetManager = mGameScreen.getGame().getAssetManager();
        loadAssets();
        init(numberOfBits, initialValue, minValue, maxValue);
    }

    //METHODS
    /**
     * Loads Assets
     */
    private void loadAssets(){
        mAssetManager.loadAndAddBitmap("BitImageDefault", "img/BlankBitPlain.png");
        mAssetManager.loadAndAddBitmap("BitImageColour", "img/BlankBitHigh.png");

    }

    /**
     * Initialises the variables
     * @param numberOfBits
     * @param initialValue
     * @param minValue
     * @param maxValue
     */
    private void init(int numberOfBits, float initialValue, float minValue, float maxValue) {
        this.mBarBits = new GameObject[numberOfBits];
        this.mMinVal=minValue;
        this.mMaxVal=maxValue;
        setValue(initialValue);
        update();
    }

    /**
     * Update method
     * Sets the off set, displays the default colour else the colour bits to show the value of current volume
     * in responce to the bar length. Creates the new game object and increases the offset
     */
    public void update() {
        int offsetBetweenBits = 0 ;
        for (int i = 0; i < mBarBits.length; i++) {
            String bitType;
            if (i >= (mBarBits.length * (mValue - mMinVal)) / (mMaxVal - mMinVal) ) {
                bitType = "Default";
            }else{
                bitType = "Colour";
            }
            Bitmap bitBitmap = mAssetManager.getBitmap("BitImage" + bitType);
            mBarBits[i] = new GameObject(getBound().getLeft() + offsetBetweenBits, getBound().y, getBound().getWidth() / mBarBits.length, getBound().getHeight(), bitBitmap, mGameScreen);
            offsetBetweenBits+= (getBound().getWidth()  / mBarBits.length) - (getBound().getWidth()  / mBarBits.length) / 6;
        }

    }

    /**
     * Draw the bar bit bitmpas of game object array is not empty
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        for(GameObject bit : mBarBits){
             if(bit != null){
                bit.draw(elapsedTime, graphics2D); }
        }
    }

   //GETTERS AND SETTERS
    public float getValue(){
        return mValue;
    }
    public float getMaxVal(){
        return mMaxVal;
    }
    public float getMinVal(){
        return mMinVal;
    }

    /**
     * Sets the input value for the bar
     * If value less than min set to min value else if greater than max set to max value,
     * @param value
     */
    public void setValue(float value){
        if(value<=mMinVal){
         this.mValue = mMinVal;
        }else if(value > mMaxVal){
            this.mValue = mMaxVal;
        }else{
            this.mValue = value;
        }
    }

    /**
     * Sets the max value to greater than the min if newMax less than
     * Sets the new mValue if current mValue out of bounds of new Max
     * Note development done
     * @param newMax
     */
    public void setMaxVal(float newMax){
        if(newMax<=mMinVal){
               this.mMaxVal= mMinVal+0.1f;
               setValue(newMax);
        }
       else if(newMax<this.mValue){
            this.mMaxVal=newMax;
            setValue(newMax);
        }else{
            this.mMaxVal=newMax;
        }
    }

    /**
     * Sets the min value of the bar to less than max if greater than max,
     * sets the new mValle if new min greater then current min
     * Does not allow min to be less than zero
     * //Note development done
     * @param newMin
     */
    public void setMinVal(float newMin){
        if(newMin>=mMaxVal) {
            this.mMinVal = mMaxVal - 0.1f;
            setValue(newMin);
        }
        else if(newMin>=this.mValue){
            this.mMinVal=newMin;
            setValue(newMin);
        }else if(newMin<0){
            this.mMinVal=0;
        }else{
            this.mMinVal=newMin;
        }
    }


}
