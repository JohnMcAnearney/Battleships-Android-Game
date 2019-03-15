package uk.ac.qub.eeecs.gage.util;

/**
 * Bounding box shape.
 *
 * @version 1.0
 */
public class BoundingBox {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Centre x location of the bounding box
     */
    public float x;

    /**
     * Centre y location of the bounding box
     */
    public float y;

    /**
     * Half-width of the bounding box
     */
    public float halfWidth;

    /**
     * Half-height of the bounding box
     */
    public float halfHeight;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors and Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Constructor a new bounding box.
     * <p>
     * By default a x,y location of [0,0] is assumed and a half width/height of
     * 1
     */
    public BoundingBox() {
        x = 0;
        y = 0;
        halfWidth = 1.0f;
        halfHeight = 1.0f;
    }

    /**
     * Create a bounding box of the specified dimensions
     *
     * @param x          Top Left x location of the box
     * @param y          Top Left y location of the box
     * @param halfWidth  Half width of the box
     * @param halfHeight Half height of the box
     */
    public BoundingBox(float x, float y, float halfWidth, float halfHeight) {
        this.x = x;
        this.y = y;
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    /**
     * Return the width of the bounding box
     *
     * @return Width of the bounding box
     */
    public float getWidth() {
        return halfWidth * 2.0f;
    }

    /**
     * Return the height of the bounding box
     *
     * @return Height of the bounding box
     */
    public float getHeight() {
        return halfHeight * 2.0f;
    }

    /**
     * Return the left bound
     *
     * @return left side location of the bound
     */
    public float getLeft() {
        return x - halfWidth;
    }

    /**
     * Return the right bound
     *
     * @return right side location of the bound
     */
    public float getRight() {
        return x + halfWidth;
    }

    /**
     * Return the top bound
     *
     * @return top side location of the bound
     */
    public float getTop() {
        return y + halfHeight;
    }

    /**
     * Return the bottom bound
     *
     * @return bottom side location of the bound
     */
    public float getBottom() {
        return y - halfHeight;
    }

    /**
     * Determine if the bounding box contains the specified point
     *
     * @param x Test point x-coordinate
     * @param y Test point y-coordinate
     * @return boolean true if the point is contained within the bound,
     * otherwise false
     */
    public boolean contains(float x, float y) {
        return (this.x  < x
                && this.x + this.halfWidth*2 > x
                && this.y  < y
                && this.y + this.halfHeight*2 > y);
    }

    /**
     * Determine if the bounding box intersects with the other specified bound
     *
     * @param other Bounding box to test for intersection with this bound
     * @return boolean true if the boxes overlap, otherwise false
     */


    /**
     * Return a string formatted representation of this bound
     *
     * @return String representation of this bound
     */
    @Override
    public String toString() {
        return String.format("Pos[%.1f,%.1f],Dim[%.1fx%.1f]", x, y, halfWidth*2.0f, halfHeight*2.0f);
    }
}