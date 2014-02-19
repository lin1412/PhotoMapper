package cs2114.photomapper;

import java.util.ArrayList;
import android.widget.TextView;
import student.android.ImageOverlay;
import java.io.IOException;
import student.android.MediaUtils;
import android.net.Uri;
import android.content.Intent;
import android.view.View;
import com.google.android.maps.MapView;
import android.os.Bundle;
import com.google.android.maps.MapActivity;

// -------------------------------------------------------------------------
/**
 * An Photo Mapper application for Android. It uses the geocoding embed by a GPS
 * phone on the photo to place it on the coordinate on a map.
 *
 * @author Hang Lin (lin1412)
 * @version 2011/10/6
 */
public class PhotoMapperActivity
    extends MapActivity
{
    // ~ Instance/static variables ............................................

    private MapView                 mapView;
    private TextView                statusLabel;
    private static final int        IMAGE_PICKED = 1;
    private static Photo            current      = null;
    private static ArrayList<Photo> list         = new ArrayList<Photo>();
    private PhotoModel              model        = new PhotoModel();


    // ~ Methods ...............................................................

    // ----------------------------------------------------------
    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState
     *            information that was saved the last time the activity was
     *            suspended.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // creates a GUI where the map and the photo on it is viewed.
        mapView = (MapView)findViewById(R.id.mapView);

        // create a status bar, and enter the message.
        statusLabel = (TextView)findViewById(R.id.statusLabel);
        statusLabel.setText("Please select or add a photo.");

        // makes the map zoom-able.
        mapView.setBuiltInZoomControls(true);

    }


    // ----------------------------------------------------------
    /**
     * Add a photo to the GUI.
     *
     * @param view
     *            the GUI of the app(mapView)
     */
    public void addButtonClicked(View view)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");

        // calls the onActivityResult method.
        startActivityForResult(intent, IMAGE_PICKED);

    }


    /**
     * Adds the photo to the mapView and pan to that location.
     *
     * @param requestC
     *            check if the image is the correct type.
     * @param resultC
     *            check if the image is added.
     * @param data
     *            the photo's data.
     */
    @Override
    protected void onActivityResult(int requestC, int resultC, Intent data)
    {

        // if (requestC == IMAGE_PICKED && resultC == RESULT_OK)
        {
            // gets the Uri and imagePath of the photo.
            Uri uri = data.getData();

            String imagePath =
                MediaUtils.pathForMediaUri(getContentResolver(), uri);
            Photo temp;
            try
            {
                temp = new Photo(uri, imagePath);
                model.add(temp, mapView, statusLabel, list);
                if (model.overlay != null)
                {
                    current = model.current;
                    list = model.photoList;
                    model.overlay.setOnClickListener(new Listener());
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestC, resultC, data);
    }


    // ----------------------------------------------------------
    /**
     * removes the photo from the list and the overlay
     *
     * @param view
     *            the GUI of the app (mapView)
     */

    public void removeButtonClicked(View view)
    {
        // call the remove method from the model.

        if (list != null)
        {
            assert current !=null;
            model.remove(current, mapView, list);
            current = null;
            list = model.photoList;
        }

        // Change the status bar and update the view.
        statusLabel.setText("Please select or add a photo.");
        mapView.postInvalidate();

    }


    // ----------------------------------------------------------
    /**
     * view the photo in normal media viewing app
     *
     * @param view
     *            the GUI of the app (mapView)
     */

    public void viewButtonClicked(View view)
    {
        // if the list size is not 0, and current photo is not null
        // then pop up the displaying photo window.
        if (list.size() != 0 && current != null)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(current.getUri());
            startActivity(intent);
        }
        else
        {
            // change the status bar.
            statusLabel.setText("Please select or add a photo.");
            mapView.postInvalidate();
        }
    }


    // ----------------------------------------------------------
    /**
     * Gets the photo that's currently selected.
     *
     * @return the current photo
     */
    public static Photo getCurrent()
    {
        return current;
    }


    // ----------------------------------------------------------
    /**
     * Gets the ArrayList thats holing the photos.
     *
     * @return the list with the photos within them.
     */
    public ArrayList<Photo> getList()
    {
        return list;
    }


    /**
     * used to update the statusLabel with a coordinate or a message.
     *
     * @author Hang Lin
     * @version Oct 12, 2011
     */
    public class Listener
        implements ImageOverlay.OnClickListener
    {
        /**
         * The method that will be run when an photo is clicked
         *
         * @param overlay
         *            the photo selected.
         * @param view
         *            the GUI the photo is in.
         */
        public void onClick(ImageOverlay overlay, MapView view)
        {
            // cast the overlay using own Overlay class to use the getPhoto
            // method and check if the photo is null.
            Overlay ov = (Overlay)overlay;
            current = ov.getPhoto();
            // display in the status bar the latitude and longitude gotten
            // from own Overlay method, then update view.
            statusLabel
                .setText(ov.getLat(current) + ", " + ov.getLong(current));
            view.getController().animateTo(ov.getPoint(current));
            view.postInvalidate();
        }
    }


    // ----------------------------------------------------------
    /**
     * This method is required by the MapActivity class. Just return false.
     *
     * @return false
     */
    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }
}
