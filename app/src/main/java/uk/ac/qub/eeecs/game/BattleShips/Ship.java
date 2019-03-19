package uk.ac.qub.eeecs.game.BattleShips;
//Sprint 3 User Story 13 - Creating the four different battleship
//classes via inheritance

//I will first create the main Ship class
//I will further implement each ship size at a later stage

//Sprint 4 - (40201925) User Story 3 - Placement of entity object
// Further implementation to the Ship class

import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import java.lang.String;

public class Ship //extends Sprite
{
    //Instance variables for the Ship class
    private String shipType; //data field for the type of ship e.g. Cargo Ship
    //Sprint 4 - added two additional instance variables to define the position of the ship entity object (40201925)
    private float scaleRatioX ;
    private float scaleratioY;
    private Vector2 targetPosition = new Vector2();
    private Vector2 screenCentre = new Vector2();
    protected BoundingBox mBound = new BoundingBox();
    private boolean selected, afterDrag;
    public Bitmap bitmap;
    private Paint paint;
    public boolean rotate, isRotated, boundingBoxSetAfterRotation, undoBoundingBoxSetAfterRotation =true;
    private Matrix matrix = new Matrix();
    private int shipLength;


    //Constructor
    //Sprint 4 - Implemented additional code to the constructor of the ship class (40201925)
    public Ship(String shipType,float scaleRatioX ,float scaleratioY, Bitmap bitmap, int shipLength)//, GameScreen gameScreen)
    //{
    // super(startPositionX, startPositionY, bitmap, gameScreen);
    //}
    {
        this.shipType = shipType;
        this.scaleRatioX = scaleRatioX;
        this.scaleratioY = scaleratioY;
        this.bitmap = bitmap;
        this.shipLength = shipLength;
    }

    private void rotate()
    {
        matrix.reset();
        matrix.setScale(scaleRatioX,scaleratioY);
        matrix.postRotate(90.0f, mBound.halfWidth, mBound.halfWidth);
        matrix.postTranslate(mBound.x,mBound.y);
    }

    public void drawShip(IGraphics2D graphics2D)
    {
        if(rotate)
        {
            rotate = false;
            isRotated = !isRotated;
        }

        if(isRotated) {

            if (!boundingBoxSetAfterRotation){
                updateBoundingBoxAfterRotation(); }
            rotate();
            graphics2D.drawBitmap(bitmap, matrix, null);
        }
        else {

            matrix.reset();
            matrix.setScale(scaleRatioX,scaleratioY);
            matrix.postTranslate(mBound.x,mBound.y);
            graphics2D.drawBitmap(bitmap, matrix, null);

            if(!undoBoundingBoxSetAfterRotation){
                reverseUpdateBoundingBoxAfterRotation();}
        }
    }

    private void updateBoundingBoxAfterRotation()
    {
        float temp = mBound.halfHeight;
        mBound.halfHeight = mBound.halfWidth;
        mBound.halfWidth = temp;
        boundingBoxSetAfterRotation = true;
        undoBoundingBoxSetAfterRotation = false;
    }

    private void reverseUpdateBoundingBoxAfterRotation()
    {
        float temp = mBound.halfHeight;
        mBound.halfHeight = mBound.halfWidth;
        mBound.halfWidth = temp;
        undoBoundingBoxSetAfterRotation = true;
        boundingBoxSetAfterRotation = false;
    }

    private void setTargetPosition(float startPositionX, float startPositionY)
    {
        mBound.x = targetPosition.x = startPositionX;
        mBound.y = targetPosition.y = startPositionY;
    }

    /*public void boundingBoat(LayerViewport mLayerViewport)
    {
        BoundingBox bound = getBound();

        if (bound.getLeft() < 0)
            setTargetPosition(startPositionX -= bound.getLeft(), startPositionY);
        else if (bound.getTop() > mLayerViewport.getHeight())
            setTargetPosition(startPositionX, startPositionY -= (bound.getTop() - mLayerViewport.getHeight()));
    }*/
    //Getters
    //Sprint 4 - Implemented and improved on the Ship class' Getters (40201925)
    public String getShipType()
    {
        return shipType;
    }



    public Vector2 getTargetPosition() { return targetPosition;}

    public Vector2 getScreenCentre() { return screenCentre;}

    public Bitmap getBitmap(){return bitmap;}

    public BoundingBox getmBound() { return mBound;}

    public boolean getSelected() {return selected;}

    public boolean getAfterDrag() { return afterDrag;}

    public int getShipLength(){return shipLength;}


    //Setters
    //Sprint 4 - Implemented and improved on the Ship class' Setters (40201925)
    public void setShipType(String shipType)
    {
        this.shipType = shipType;
    }

    public void setTargetPosition(Vector2 targetPosition) { this.targetPosition = targetPosition;}

    public void setScreenCentre(Vector2 screenCentre) { this.screenCentre = screenCentre;}

    public void setShipLength(int shipLength) {this.shipLength = shipLength;}

    public void setmBound(float x, float y, float halfWidth, float halfHeight) {
        mBound.x = x;
        mBound.y = y;
        mBound.halfWidth = halfWidth;
        mBound.halfHeight = halfHeight;
    }


    public void setSelected(boolean selected) { this.selected = selected;}

    public void setAfterDrag(boolean afterDrag) { this.afterDrag = afterDrag;}

    public boolean isSelected() { return selected;}

    public boolean isAfterDrag() { return afterDrag;}




}