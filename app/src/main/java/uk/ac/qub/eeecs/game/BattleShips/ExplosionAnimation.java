package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Rect;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationSettings;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;

public class ExplosionAnimation {

    private String mName;
    private Bitmap mSpritesheet;
    private int mNumRows;
    private int mNumColumns;
    private int mFrameWidth;
    private int mFrameHeight;
    private int mStartFrame;
    private int mEndFrame;
    private int mCurrentFrame;
    private float mTotalPeriod;
    private boolean mIsPlaying;
    private double mAnimationStartTime;
    private boolean mLoopAnimation;

    float x,  y,  right,  bottom;

    private Rect sourceRect = new Rect();
    private Rect screenRect = new Rect();


    public ExplosionAnimation( AnimationSettings animationSettings,int animationIdx)
    {

        mSpritesheet = animationSettings.spritesheet;
        mNumRows = animationSettings.numRows;
        mNumColumns = animationSettings.numColumns;

        mFrameWidth = animationSettings.spritesheet.getWidth() / mNumColumns;
        mFrameHeight = animationSettings.spritesheet.getHeight() / mNumRows;

        // Store details of the selected animation
        mName = animationSettings.name[animationIdx];

        mStartFrame = animationSettings.startFrame[animationIdx];
        mEndFrame = animationSettings.endFrame[animationIdx];

        mTotalPeriod = animationSettings.totalPeriod[animationIdx];
        mLoopAnimation = animationSettings.loopAnimation[animationIdx];

        // Set the current frame equal to the starting frame and use the default facing
        mCurrentFrame = animationSettings.startFrame[animationIdx];

        // Initially set playback to false
        mIsPlaying = false;
    }

    public void play(ElapsedTime elapsedTime, float x, float y, float right, float bottom) {

        this.x = x;
        this.y = y;
        this.right = right;
        this.bottom = bottom;

        // Only commence playback is the animation is not currently playing
        if(!mIsPlaying) {
            mAnimationStartTime = elapsedTime.totalTime;
            mCurrentFrame = mStartFrame;
            mIsPlaying = true;
        }
    }

    public void stop() {
        mIsPlaying = false;
    }

    public void update(ElapsedTime elapsedTime) {
        // Do nothing if the animation is not currently playing
        if (!mIsPlaying) return;

        // Determine the length of time the animation has been playing for
        float timeSinceAnimationStart =
                (float) (elapsedTime.totalTime - mAnimationStartTime);

        // If the animation period has been exceeded and the animation is not
        // looping, then end playback and default to the final frame of the animation
        if (!mLoopAnimation
                && timeSinceAnimationStart > mTotalPeriod) {
            mCurrentFrame = mEndFrame;
            stop();
        } else {
            // Select an appropriate animation frame
            float animationPosition =
                    timeSinceAnimationStart / mTotalPeriod;
            animationPosition -= (int) animationPosition;

            mCurrentFrame = (int) (
                    (float) (mEndFrame - mStartFrame + 1) * animationPosition) + mStartFrame;
        }
    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        if (mIsPlaying) {
            // Build a screen rect using the specified game object
            screenRect.left = (int) x;
            screenRect.right = (int) right;
            screenRect.top = (int) y;
            screenRect.bottom = (int) bottom;

            // Calculate a source rectangle for a single frame
            sourceRect.left = 0;
            sourceRect.right = mFrameWidth;
            sourceRect.top = 0;
            sourceRect.bottom = mFrameHeight;

            // Determine the location of the current frame within the sprite sheet
            int rowIdx = mCurrentFrame / mNumColumns;
            int colIdx = mCurrentFrame % mNumColumns;

            // Offset the shrunk source rectangle onto the current frame
            sourceRect.left = sourceRect.left + colIdx * mFrameWidth;
            sourceRect.right = sourceRect.right + colIdx * mFrameWidth;
            sourceRect.top = sourceRect.top + rowIdx * mFrameHeight;
            sourceRect.bottom = sourceRect.bottom + rowIdx * mFrameHeight;


            // Draw the frame
            graphics2D.drawBitmap(mSpritesheet, sourceRect, screenRect, null);
        }
    }
}