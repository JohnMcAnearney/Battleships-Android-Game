package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Rect;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationSettings;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;

/*
* Author : Edgars (40203154)
* This is a custom animation class which will take an Image Strip/Spritesheet and animate it,
* allowing for the animation to then be used in the LoadingScreen class to allow for a loading
* animation to be drawn to the screen, taking reference from Mantas(40203133)
* 'ExplosionAnimation' class as my class wasn't fully functional beforehand
*/
public class LoadingAnimation
{
    // Declaring all the relevant variables to the spritesheet
    private String mName;
    private Bitmap mSpritesheet;
    private int mNumOfRows, mNumOfColumns, mFrameWidth, mFrameHeight, mStartFrame, mEndFrame,
            mCurrentFrame;
    private double mStartTime;
    private float mTotalTime, mTimeSinceAnimationStart;
    private boolean mPlaying, mLoopAnimation;

    // Declaring the variables used to place the animation on screen
    private float x, y, right, bottom;

    // Declaring the source and screen rectangles
    private Rect mSourceRect = new Rect();
    private Rect mScreenRect = new Rect();

    /*
     * CONSTRUCTOR - for the LoadingAnimation class, which runs a methods which sets up the screen
     */
    public LoadingAnimation(AnimationSettings animationSettings, int stripIndex)
    {
        // Method which initialises all of the variables needed within the class.
        initialiseVariables(animationSettings, stripIndex);
    }

    /*
     * Update method for the LoadingAnimation class
     */
    public void update(ElapsedTime elapsedTime)
    {
        // If statement which breaks update if the animation is currently not playing.
        if (!mPlaying)
        {
            return;
        }

        // A line of code which determines how long the animation has been playing for
        mTimeSinceAnimationStart = (float)(elapsedTime.totalTime - mStartTime);

        // Method which makes sure the animation is being displayed correctly
        appropriateAnimationFrame();
    }

    /*
     * Draw method for the LoadingAnimation class
     */
    public void draw(IGraphics2D graphics2D)
    {
        // If statement which executes the draw method only if the animation is set to playing
        if(mPlaying)
        {
            // Method which builds the screen rectangle, using the specified game object
            buildRectangle();

            // Calculating a source rectangle for a single frame of the image strip
            buildSourceRectangle();

            // Calculating the location of the current frame within the image strip
            int rowIndex = mCurrentFrame / mNumOfRows;
            // Reference @ Mantas(40203133) 'ExplosionAnimation' class
            int columnIndex = mCurrentFrame % mNumOfColumns;

            /*
             * Reference @ Mantas(40203133) 'ExplosionAnimation' class
             * Offset the source rectangle onto the current frame
             */
            mSourceRect.left = mSourceRect.left + ( columnIndex * mFrameWidth );
            mSourceRect.right = mSourceRect.right + ( columnIndex * mFrameWidth );
            mSourceRect.top = mSourceRect.top + ( rowIndex * mFrameHeight );
            mSourceRect.bottom = mSourceRect.bottom + ( rowIndex * mFrameHeight );

            // Draw the actual frame
            graphics2D.drawBitmap(mSpritesheet, mSourceRect, mScreenRect, null);
        }
    }

    /*
     * METHODS
     */
    // Method which initialises all of the variables needed within the class
    private void initialiseVariables(AnimationSettings animationSettings, int stripIndex)
    {
        // Assign variables with appropriate values from the animationSettings class
        mSpritesheet = animationSettings.spritesheet;
        mNumOfRows = animationSettings.numRows;
        mNumOfColumns = animationSettings.numColumns;
        mFrameWidth = animationSettings.spritesheet.getWidth() / mNumOfRows;
        mFrameHeight = animationSettings.spritesheet.getHeight() / mNumOfColumns ;

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

    // Method which starts the playing of the image strip in a given location
    public void playAnimation(ElapsedTime elapsedTime, float x, float y, float right, float bottom)
    {
        // Setting the location variables to those specified in the method when it's called
        this.x = x;
        this.y = y;
        this.right = right;
        this.bottom = bottom;

        // If statement which sets mStartTime, mCurrentFrame and also starts playing the animation
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

    /*
     * Reference @ Mantas(40203133) 'ExplosionAnimation' class
     * Method which selects the appropriate animation frame
     */
    private void selectAnimationFrame()
    {
        float positionOfAnimation = mTimeSinceAnimationStart / mTotalTime;
        positionOfAnimation -= (int)positionOfAnimation;

        mCurrentFrame = (int)((float)(mEndFrame - mStartFrame + 1) * positionOfAnimation)
                + mStartFrame;
    }

    /*
     * Method which makes sure the animation has not exceeded the time limit & is not looping, and
     * if so stopping animation, and also runs the selectAnimationFrame() method
     */
    private void appropriateAnimationFrame()
    {
        /*
         * If statement which makes sure that the time period has not been exceeded and that the
         * spritesheet is non-looping, if so, then end playback and default to last frame
         */
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

    // Method which builds the screen rectangle
    private void buildRectangle()
    {
        // Building the rectangle to the specified game object
        mScreenRect.left = (int)x;
        mScreenRect.top = (int)y;
        mScreenRect.right = (int)right;
        mScreenRect.bottom = (int)bottom;
    }

    /*
     * Reference @ Mantas(40203133) 'ExplosionAnimation' class
     * Method which builds the source rectangle
     */
    private void buildSourceRectangle()
    {
        mSourceRect.left = 0;
        mSourceRect.right = mFrameWidth;
        mSourceRect.top = 0;
        mSourceRect.bottom = mFrameHeight;
    }

    /*
     * Getters
     */
    // Getter for the frame height
    public int getFrameHeight() { return mFrameHeight; }

    // Getter for the frame width
    public int getFrameWidth() { return mFrameWidth; }

    // Getter for the current frame
    public int getCurrentFrame() { return mCurrentFrame; }

    // Getter for the end frame
    public int getEndFrame() { return mEndFrame; }

    // Getter for the start frame
    public int getStartFrame() { return mStartFrame; }
}
