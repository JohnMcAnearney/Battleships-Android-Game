package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Rect;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationSettings;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;

/* Author : Edgars (40203154)
* This is a custom animation class which will take an Image Strip/Spritesheet and animate it, allowing for the
* animation to then be used in the LoadingScreen class to allow for a loading animation to be drawn to the
* screen
*/
// Added class for loading the loading screen image strip and animate it
public class LoadingAnimation
{
    // Declaring all the relevant variables to the image strip
    private String mName;
    private Bitmap mImageStrip;
    private int mNumOfRows, mNumOfColumns, mFrameWidth, mFrameHeight, mStartFrame, mEndFrame, mCurrentFrame;
    private double mStartTime;
    private float mTotalTime, mTimeSinceAnimationStart;
    private boolean mPlaying, mLoopAnimation;

    // Declaring the variables used to place the animation on screen
    private float x, y, right, bottom;

    // Declaring the source and screen rectangles
    private Rect sourceRect = new Rect();
    private Rect screenRect = new Rect();

    /**
     * CONSTRUCTOR - for the LoadingAnimation class, which runs a methods which sets up the screen
     * @param animationSettings
     * @param stripIndex
     */
    public LoadingAnimation(AnimationSettings animationSettings, int stripIndex) {
        // Method which initialises all of the variables needed within the class.
        initialiseVariables(animationSettings, stripIndex);
    }

    /**
     * Update method for the LoadingAnimation class
     * @param elapsedTime
     */
    public void update(ElapsedTime elapsedTime)
    {
        // If statement which does nothing if the animation is currently not playing.
        if (!mPlaying)
       {
           return;
       }

        // A line of code which determines how long the animation has been playing for
        mTimeSinceAnimationStart = (float) (elapsedTime.totalTime - mStartTime);

        //Method which makes sure the animation is being displayed correctly
        appropriateAnimationFrame();
    }

    /**
     * Draw method for the LoadingAnimation class
     * @param graphics2D
     */
    public void draw(IGraphics2D graphics2D)
    {
        // If statement which executes the draw method only if the animation is set to playing
       if(mPlaying)
       {
            // Method which builds the screen rectangle, using the specified game object
            buildRectangle();

            // Calculating a source rectangle for a single frame of the image strip
            sourceRect.left = 0;
            sourceRect.right = mFrameWidth;
            sourceRect.top = 0;
            sourceRect.bottom = mFrameHeight;

            // Calculating the location of the current frame within the image strip
            int rowIndex = mCurrentFrame / mNumOfRows;
            int columnIndex = mCurrentFrame % mNumOfColumns;

            // Offset the source rectangle onto the current frame
            sourceRect.left = sourceRect.left + ( columnIndex * mFrameWidth );
            sourceRect.right = sourceRect.right + ( columnIndex * mFrameWidth );
            sourceRect.top = sourceRect.top + ( rowIndex * mFrameHeight );
            sourceRect.bottom = sourceRect.bottom + ( rowIndex * mFrameHeight );

            // Draw the actual frame
            graphics2D.drawBitmap(mImageStrip, sourceRect, screenRect, null);
       }
    }

    //----METHODS----

    /**
     * Method which initialises all of the variables needed within the class
     * @param animationSettings
     * @param stripIndex
     */
    public void initialiseVariables(AnimationSettings animationSettings, int stripIndex)
    {
        // Assign variables with appropriate values from the animationSettings class
        mImageStrip = animationSettings.spritesheet;
        mNumOfRows = animationSettings.numRows;
        mNumOfColumns = animationSettings.numColumns;
        mFrameWidth = animationSettings.spritesheet.getWidth() / mNumOfRows;
        mFrameHeight = animationSettings.spritesheet.getHeight();

        // Assign the name of the given animation image strip
        mName = animationSettings.name[stripIndex];

        // Assign the start and end frame of the given animation image strip
        mStartFrame = animationSettings.startFrame[stripIndex];
        mEndFrame = animationSettings.endFrame[stripIndex];

        // Assign the total time of the animation, and if the animation is a meant to loop
        mTotalTime = animationSettings.totalPeriod[stripIndex];
        mLoopAnimation = animationSettings.loopAnimation[stripIndex];

        // Assign the current frame to the starting frame
        mCurrentFrame = animationSettings.startFrame[stripIndex];

        // Assign the playing variable with false, to have it not play as default
        mPlaying = false;
    }

    /**
     * Method which starts the playing of the image strip in a given location
     * @param elapsedTime
     * @param x
     * @param y
     * @param right
     * @param bottom
     */
    public void playAnimation(ElapsedTime elapsedTime, float x, float y, float right, float bottom)
    {
        this.x = x;
        this.y = y;
        this.right = right;
        this.bottom = bottom;

        if(!mPlaying)
        {
            mStartTime = elapsedTime.totalTime;
            mCurrentFrame = mStartFrame;
            mPlaying = true;
        }
    }

    // Method which stops the animation
    private void stopAnimation()
    {
        mPlaying = false;
    }

    // Method which makes sure the animation has not exceeded the time limit & is not looping, and if so stopping animation, and also runs the selectAnimationFrame() method
    private void appropriateAnimationFrame()
    {
        // If statement which makes sure that the time period has not been exceeded and that the image strip is non-looping, if so, then end playback and default to last frame
        if(mTimeSinceAnimationStart > mTotalTime && !mLoopAnimation)
        {
            mCurrentFrame = mEndFrame;
            stopAnimation();
        }
        // Else clause which selects an appropriate animation frame
        else
        {
            selectAnimationFrame();
        }
    }

    // Method which selects the appropriate animation frame
    private void selectAnimationFrame()
    {
        float positionOfAnimation = mTimeSinceAnimationStart / mTotalTime;
        positionOfAnimation -= (int)positionOfAnimation;

        mCurrentFrame = (int)((float)(mEndFrame - mStartFrame + 1) * positionOfAnimation) + mStartFrame;
    }

    // Method which builds the screen rectangle
    private void buildRectangle()
    {
        // Building the rectangle to the specified game object
        screenRect.left = (int)x;
        screenRect.right = (int)right;
        screenRect.top = (int)y;
        screenRect.bottom = (int)bottom;
    }
}
