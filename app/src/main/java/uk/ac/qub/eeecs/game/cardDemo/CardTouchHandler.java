package uk.ac.qub.eeecs.game.cardDemo;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.util.Pool;
import uk.ac.qub.eeecs.game.cardDemo.CardTouchEvent;

public class CardTouchHandler implements View.OnTouchListener {

    /**
     * Define the maximum number of touch events that can be supported
     */
    public static final int MAX_TOUCHPOINTS = 10;

    /**
     * Occurred and position information for the supported number of touch
     * events.
     */
    private boolean[] mExistsTouch = new boolean[MAX_TOUCHPOINTS];
    private float mTouchX[] = new float[MAX_TOUCHPOINTS];
    private float mTouchY[] = new float[MAX_TOUCHPOINTS];

    /**
     * Touch event pool and lists of current (for this frame) and unconsumed
     * (occurring since the frame started) touch events.
     */
    private Pool<CardTouchEvent> mPool;
    private List<CardTouchEvent> mTouchEvents = new ArrayList<>();
    private List<CardTouchEvent> mUnconsumedTouchEvents = new ArrayList<>();

    /**
     * Axis scale values - can be used to scale the input range from native
     * pixels to some predefined range.
     */
    private float mScaleX;
    private float mScaleY;

    /**
     * Define the maximum number of touch events that can be retained in the
     * touch store.
     */
    private final int TOUCH_POOL_SIZE = 100;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Configure a new touch handler instance
     */
    public CardTouchHandler() {

        mPool = new Pool<>(new Pool.ObjectFactory<CardTouchEvent>() {
            public CardTouchEvent createObject() {
                return new CardTouchEvent();
            }
        }, TOUCH_POOL_SIZE);

        // Define the input scale factor if mapping onto the -1 to 1 range
        mScaleX = 1.0f;
        mScaleY = 1.0f;
    }

    /**
     * Create a new touch handler instance for the specified view.
     *
     * @param view View whose touch events should be captured by this handler
     */
    public CardTouchHandler(View view) {
        this();
        view.setOnTouchListener(this);
    }


    // /////////////////////////////////////////////////////////////////////////
    // Methods: Configuration
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Set the scaling factor that is to be applied along the x-axis
     *
     * @param scaleX Scale to be applied along the x-axis
     */
    public void setScaleX(float scaleX) {
        mScaleX = scaleX;
    }

    /**
     * Set the scaling factor that is to be applied along the y-axis
     *
     * @param scaleY Scale to be applied along the y-axis
     */
    public void setScaleY(float scaleY) {
        mScaleY = scaleY;
    }

    /**
     * (non-Javadoc)
     *
     * @see android.view.View.OnTouchListener#onTouch(android.view.View,
     * android.view.MotionEvent)
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, final MotionEvent event) {

        // Update the locations of all occurring touch points
        updateEventPositions(event);

        // Extract details of this event
        int eventType = event.getActionMasked();
        int pointerId = event.getPointerId(event.getActionIndex());

        // Retrieve and instantiate a touch event
        CardTouchEvent CardTouchEvent = instantiateCardTouchEvent(event);

        switch (eventType) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                CardTouchEvent.type = CardTouchEvent.TOUCH_DOWN;
                mExistsTouch[pointerId] = true;
                break;

            case MotionEvent.ACTION_MOVE:
                CardTouchEvent.type = CardTouchEvent.TOUCH_DRAGGED;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                CardTouchEvent.type = CardTouchEvent.TOUCH_UP;
                mExistsTouch[pointerId] = false;
                break;
        }

        // Add this touch events to the list of unconsumed events
        addCardTouchEvent(CardTouchEvent);

        return true;
    }

    /**
     * Updates the event positions to include scaling if required
     *
     * @param event Motion event from which to extract touch points
     */
    protected void updateEventPositions(MotionEvent event) {
        // Update the locations of all occurring touch points
        for (int ptrIdx = 0; ptrIdx < event.getPointerCount(); ptrIdx++) {
            // Update the relevant touch point location
            int pointerId = event.getPointerId(ptrIdx);
            mTouchX[pointerId] = event.getX(ptrIdx) * mScaleX;
            mTouchY[pointerId] = event.getY(ptrIdx) * mScaleY;
        }
    }

    /**
     * Instantiate a touch event for the specified motion event.
     *
     * @param event MotionEvent from which to build a TouchEvent
     * @return Touch event object linked to the specified motion event
     */
    protected CardTouchEvent instantiateCardTouchEvent(final MotionEvent event) {
        int pointerId = event.getPointerId(event.getActionIndex());

        CardTouchEvent CardTouchEvent = mPool.get();
        CardTouchEvent.pointer = pointerId;
        CardTouchEvent.x = mTouchX[pointerId];
        CardTouchEvent.y = mTouchY[pointerId];
        CardTouchEvent.dx = 0;
        CardTouchEvent.dy = 0;
        return CardTouchEvent;
    }

    /**
     * Determine if the touch event exists for the specified pointer ID.
     *
     * @param pointerId ID of the pointer to test for
     * @return Boolean true if there is a touch event, false otherwise
     */
    public boolean existsTouch(int pointerId) {
        synchronized (this) {
            return mExistsTouch[pointerId];
        }
    }

    /**
     * Get the (scaled) x-location of the specified pointer ID
     * <p>
     * Note: The method assumes that the specified pointer ID has a valid touch
     * location (i.e. that existsTouch(pointerID) is true).
     *
     * @param pointerId ID of the pointer to test for
     * @return x-touch location of the specified pointer ID, or Float.NAN if the
     * pointer ID does not currently exist
     */
    public float getTouchX(int pointerId) {
        synchronized (this) {
            // Assumes the user will ensure correct range checking - for speed
            if (mExistsTouch[pointerId])
                return mTouchX[pointerId];
            else
                return Float.NaN;
        }
    }

    /**
     * Get the (scaled) y-location of the specified pointer ID
     * <p>
     * Note: The method assumes that the specified pointer ID has a valid touch
     * location (i.e. that existsTouch(pointerID) is true).
     *
     * @param pointerId ID of the pointer to test for
     * @return y-touch location of the specified pointer ID, or Float.NAN if the
     * pointer ID does not currently exist
     */
    public float getTouchY(int pointerId) {
        synchronized (this) {
            // Assumes the user will ensure correct range checking - for speed
            if (mExistsTouch[pointerId])
                return mTouchY[pointerId];
            else
                return Float.NaN;
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods: Event Accumulation
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Add the specified touch event to the list of unconsumed touch events (to be
     * returned at the start of the next update). It is added in a synchronized
     * manner as a non-GUI threads may be acquiring the unconsumed touch events.
     *
     * @param CardTouchEvent Touch event
     */
    protected void addCardTouchEvent(CardTouchEvent CardTouchEvent) {
        synchronized (this) {
            mUnconsumedTouchEvents.add(CardTouchEvent);
        }
    }

    /**
     * Return the list of touch events accumulated for the current frame.
     * <p>
     * IMPORTANT: A shared list of touch events is returned. The list should be
     * considered read only.
     *
     * @return List of touch events accumulated for the current frame
     */
    public List<CardTouchEvent> getTouchEvents() {
        synchronized (this) {
            return mTouchEvents;
        }
    }

    /**
     * Reset the accumulator - update the current set of frame touch events to
     * those accumulated since the last time the accumulator was reset.
     * <p>
     * Note: It is assumed that this method will be called once per frame.
     */
    public void resetAccumulator() {
        synchronized (this) {
            // Release all existing touch events
            int len = mTouchEvents.size();
            for (int i = 0; i < len; i++)
                mPool.add(mTouchEvents.get(i));
            mTouchEvents.clear();
            // Copy across accumulated events
            mTouchEvents.addAll(mUnconsumedTouchEvents);
            mUnconsumedTouchEvents.clear();
        }
    }
}
