package cs2114.photomapper;

import android.widget.TextView;
import java.util.ArrayList;
import com.google.android.maps.MapView;

// -------------------------------------------------------------------------
/**
 * The Model class for the PhotoMapperActivity class. holds the methods used by
 * the activity class.
 *
 * @author Hang
 * @version Oct 13, 2011
 */
public class PhotoModel
{
    /**
     * the current selected photo.
     */
    public Photo            current;
    /**
     * the GUI of the view.
     */
    public MapView          mapView;
    /**
     * the overlay of the photo
     */
    public Overlay          overlay;
    /**
     * the list of photos.
     */
    public ArrayList<Photo> photoList = new ArrayList<Photo>();


    // ----------------------------------------------------------
    /**
     * Adds a photo if it's not already in the list and if it contains a
     * coordinate.
     *
     * @param temp
     *            the input photo.
     * @param view
     *            the GUI mapView.
     * @param statusLabel
     *            the status bar.
     * @param list
     *            the input list of photos.
     */
    public void add(
        Photo temp,
        MapView view,
        TextView statusLabel,
        ArrayList<Photo> list)
    {

        // check if the photo is already added.
        boolean added = false;
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getPath().equals(temp.getPath()))
            {
                added = true;
            }
        }
        // creates a new overLay then adding it to the mapView if it
        // contains a coordinate and it's not already in the mapView.
        if (temp.getCoord()[0] != 0 && temp.getCoord()[1] != 0)
        {
            if (!added)
            {
                // set current photo to temp and use that to create a
                // new Overlay which will be added into the view,
                // and adds the photo into a list.
                current = temp;
                overlay = new Overlay(current);
                list.add(current);
                view.getOverlays().add(overlay);
                // display in the status bar the photo's coordinate
                // then update the view.
                view.getController().animateTo(overlay.getPoint(current));
                statusLabel.setText(overlay.getLat(current) + ", "
                    + overlay.getLong(current));
                photoList = list;
                view.postInvalidate();
            }
            else
            {
                // if the photo is already in the overlay.
                statusLabel.setText("Photo already added.");
                photoList = list;
                view.postInvalidate();
            }
        }
        else
        {
            // if the photo does not contain a coordinate.
            statusLabel.setText("Photo does not have location data.");
            view.postInvalidate();
        }

    }


    // ----------------------------------------------------------
    /**
     * remove the photo from the overlay and list is it is selected.
     *
     * @param photo
     *            the photo thats going to be removed.
     * @param view
     *            the GUI where the photo is.
     * @param list
     *            the list of photos.
     */
    public void remove(Photo photo, MapView view, ArrayList<Photo> list)
    {

        // search through the list and find the location of a photo thats
        // the same as the photo current photo, and remove it from both the
        // list and overlay.
        assert list != null;
        for (int i = 0; i < list.size(); i++)
        {
            //((Overlay)view.getOverlays().get(i)).getPhoto().equals(photo)
            if (list.get(i).equals(photo))
            {
                view.getOverlays().remove(i);
                list.remove(i);
            }
            photoList = list;
        }

    }

}
