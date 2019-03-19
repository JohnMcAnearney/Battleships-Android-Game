//---------------------------------------------
//---- STILL WORKING ON THIS CLASS, EDGARS ----
//---------------------------------------------

/*package uk.ac.qub.eeecs.game.BattleShips;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;

import uk.ac.qub.eeecs.gage.engine.AssetManager;

public class CanvasRender extends Fragment
{
    private CanvasRenderer mCanvasRenderer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mCanvasRenderer = new CanvasRender(getActivity());

        return mCanvasRenderer;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        mCanvasRenderer.pause();
    }

    @Override
    public void onPause()
    {
        mCanvasRenderer.pause();

        super.onPause();
    }

    private Bitmap mImage;
    private Paint mPaint;
    private Rect mRect;

    private void doSetup(String bitmapLocation)
    {
        mRect = new Rect();
        mPaint = new Paint();

        try
        {
            AssetManager assetManager = getActivity().getAssets();
            InputStream inputStream = assetManager.open(bitmapLocation);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            mImage = BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    private void doDraw(Canvas canvas)
    {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.drawBitmap(mImage, null, mRect, null);
    }


}

public class CanvasRenderer extends View implements Runnable
{
    Thread renderThread = null;
    // Boolean flags set-up for subsequent methods
    volatile boolean running = false;
    volatile boolean drawNeeded = false;

    public CanvasRenderer(Context context)
    {
        super(context);

        doSetup();
    }

    @Override
    public void run()
    {
        // While loop which runs whilst the game is running
        while(running)
        {
            // If a draw is needed, if statement resets the boolean flag and starts the draw by posting a invalidate message and this results in the GUI re-drawing the canvas
            // a invalidate message and this results in the GUI re-drawing the canvas
            if(drawNeeded)
            {
                drawNeeded = false;
                postInvalidate();
            }
        }

        // Sleep method to allow for a short rest period
        try
        {
                Thread.sleep(20);
        }
        catch(InterruptedException e)
        {
            System.out.println(e);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
       // Draw whatever is required to be drawn to the canvas
       doDraw(canvas);
       // Change the boolean flag to true to indicate that another draw can be triggered
        drawNeeded = true;
    }

    public void pause()
    {
        // Set the running flag to false as it is no longer running
        running = false;
        while(true)
        {
            // Waiting for the render thread's run method to stop before restarting
            try
            {
                renderThread.join();
                return;
            }
            catch(InterruptedException e)
            {
                System.out.println(e);
            }
        }
    }

    public void resume()
    {
        // Set all boolean flags to restart the checking loop and start a new thread
        running = true;
        drawNeeded = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    private void doSetup()
    {

        try
        {
            AssetManager assetManager = getActivity().getAssets();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
}*/
