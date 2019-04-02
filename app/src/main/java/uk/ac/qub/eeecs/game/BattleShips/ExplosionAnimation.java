package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Rect;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationSettings;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;


/** Author: Mantas Stadnik 40203133
 *This class is based on the uk.ac.qub.eeecs.gage.engine.animation.Animation class
 *As the existing Animation class was not suitable for the game in development as draw methods were
 *based on gameobjects or else layer viewpoorts and screen viewports.
 * Therefore I created a custom animation class to suit the game.
 *
 * Purpose: Animate a sequence of frames from an image sheet
 */
public class ExplosionAnimation {

    // /////////////////////////////////////////////////////////////////////////
    // Properties:
    // /////////////////////////////////////////////////////////////////////////
    /**
     * Name of the Animation
     */
    private String name;
    /**
     * The location of the bitmap and file name
     */
    private Bitmap spritesheet;

    /**
     * number of rows in the image spritesheet
     */
    private int numRows;
    /**
     * number of columns in the image spritesheet
     */
    private int numColumns;
    /**
     * indicator if the animation shall be looped
     */
    private boolean loopAnimation;

    /**
     * width of each animation frame from image spritesheet
     */
    private int frameWidth;
    /**
     * height of each animation frame from image spritesheet
     */
    private int frameHeight;

    /**
     * Start and end frames for the associated animation. It is assumed that
     * the frame will be expressed as a single integer index into a row-ordered
     * sprite sheet.
     */
    private int startFrame;
    private int endFrame;

    /**
     * index of the current animation frame
     */
    private int currentFrame;

    /**
     * total duration of a single animation
     */
    private float totalPeriod;

    /**
     * indicator if the animation is currently playing
     */
    private boolean isPlaying;

    /**
     * Timestamp of when the animation started playing
     */
    private double animationStartTime;


    /**
     * Coordinates of a square where the animation frames will be drawn
     * source and screen rectangles used to draw the animation frames
     */
    float x,  y,  right,  bottom;
    private Rect sourceRect = new Rect();
    private Rect screenRect = new Rect();


    /**
     * Constructor of the Explosion Animation class
     * Create an animation, extracting details from animationSettings parameter
     * @param animationSettings
     * @param animationIdx
     */
    public ExplosionAnimation(AnimationSettings animationSettings,int animationIdx)
    {

        spritesheet = animationSettings.spritesheet;
        numRows = animationSettings.numRows;
        numColumns = animationSettings.numColumns;

        frameWidth = animationSettings.spritesheet.getWidth() / numColumns;
        frameHeight = animationSettings.spritesheet.getHeight() / numRows;

        // Store details of the selected animation
        name = animationSettings.name[animationIdx];

        startFrame = animationSettings.startFrame[animationIdx];
        endFrame = animationSettings.endFrame[animationIdx];

        totalPeriod = animationSettings.totalPeriod[animationIdx];
        loopAnimation = animationSettings.loopAnimation[animationIdx];

        // Set the current frame equal to the starting frame and use the default facing
        currentFrame = animationSettings.startFrame[animationIdx];

        // Initially set playback to false
        isPlaying = false;
    }

    /**
     * Begin playing the animation, and store the co-ordinates of the square that the
     * animation will be played in
     * @param elapsedTime
     * @param x
     * @param y
     * @param right
     * @param bottom
     */
    public void play(ElapsedTime elapsedTime, float x, float y, float right, float bottom) {

        this.x = x;
        this.y = y;
        this.right = right;
        this.bottom = bottom;

        // Only commence playback is the animation is not currently playing
        if(!isPlaying) {
            animationStartTime = elapsedTime.totalTime;
            currentFrame = startFrame;
            isPlaying = true;
        }
    }

    /**
     * Set the isPlaying flag to false to stop playing the animation
     */
    public void stop() {
        isPlaying = false;
    }

    /**
     * Update the the frame of the animation assuring an appropriate animation frame will be played
     * @param elapsedTime
     */
    public void update(ElapsedTime elapsedTime) {
        // Do nothing if the animation is not currently playing
        if (!isPlaying) return;

        // Determine the length of time the animation has been playing for
        float timeSinceAnimationStart =
                (float) (elapsedTime.totalTime - animationStartTime);

        // If the animation period has been exceeded and the animation is not
        // looping, then end playback and default to the final frame of the animation
        if (!loopAnimation
                && timeSinceAnimationStart > totalPeriod) {
            currentFrame = endFrame;
            stop();
        } else {
            // Select an appropriate animation frame
            float animationPosition =
                    timeSinceAnimationStart / totalPeriod;
            animationPosition -= (int) animationPosition;

            currentFrame = (int) (
                    (float) (endFrame - startFrame + 1) * animationPosition) + startFrame;
        }
    }


    /**
     * Draw the current animation to a specified square ensuring the correct scale is used
     * @param elapsedTime
     * @param graphics2D
     */
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        if (isPlaying) {
            // Build a screen rect using the stored co-ordinates
            screenRect.left = (int) x;
            screenRect.right = (int) right;
            screenRect.top = (int) y;
            screenRect.bottom = (int) bottom;

            // Calculate a source rectangle for a single frame
            sourceRect.left = 0;
            sourceRect.right = frameWidth;
            sourceRect.top = 0;
            sourceRect.bottom = frameHeight;

            // Determine the location of the current frame within the sprite sheet
            int rowIdx = currentFrame / numColumns;
            int colIdx = currentFrame % numColumns;

            // Offset the shrunk source rectangle onto the current frame
            sourceRect.left = sourceRect.left + colIdx * frameWidth;
            sourceRect.right = sourceRect.right + colIdx * frameWidth;
            sourceRect.top = sourceRect.top + rowIdx * frameHeight;
            sourceRect.bottom = sourceRect.bottom + rowIdx * frameHeight;


            // Draw the frame
            graphics2D.drawBitmap(spritesheet, sourceRect, screenRect, null);
        }
    }

    public String getName() {
        return name;
    }

    public Bitmap getSpritesheet() {
        return spritesheet;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getNumRows(){
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public float getTotalPeriod() {
        return totalPeriod;
    }

    public int getStartFrame() {
        return startFrame;
    }
}