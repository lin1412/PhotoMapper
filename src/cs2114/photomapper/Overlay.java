package cs2114.photomapper;

import com.google.android.maps.GeoPoint;
import student.android.ImageOverlay;

// -------------------------------------------------------------------------
/**
 * Using a photo to create a Overlay, this Overlay class extends the
 * ImageOverlay class and contain some additional helper methods.
 *
 * @author Hang Lin
 * @version Oct 11, 2011
 */
public class Overlay
    extends ImageOverlay
{
    private Photo photo;


    // ----------------------------------------------------------
    /**
     * Create a new Overlay object.
     *
     * @param image
     *            the photo the information is from.
     */
    public Overlay(Photo image)
    {
        // calling the parent constructor method using the input photo's data.
        super(image.getPath(), new GeoPoint(
            (int)(image.getCoord()[0] * 1000000),
            (int)(image.getCoord()[1] * 1000000)));
        photo = image;
    }


    // ----------------------------------------------------------
    /**
     * get the Latitude
     *
     * @param image
     *            the photo the information is from.
     * @return the latitude of the photo
     */
    public float getLat(Photo image)
    {
        return image.getCoord()[0];
    }


    // ----------------------------------------------------------
    /**
     * get the longitude
     *
     * @param image
     *            the photo the information is from.
     * @return the longitude of the photo
     */
    public float getLong(Photo image)
    {
        return image.getCoord()[1];
    }


    // ----------------------------------------------------------
    /**
     * create a GeoPoint based of the coord of the photo and returns it
     *
     * @param image
     *            the photo the information is from.
     * @return the GeoPoint of the photo
     */
    public GeoPoint getPoint(Photo image)
    {
        return new GeoPoint(
            (int)(image.getCoord()[0] * 1000000),
            (int)(image.getCoord()[1] * 1000000));
    }


    // ----------------------------------------------------------
    /**
     * use this helper method to removed photos from the overlay list
     *
     * @return the photo that was used
     */
    public Photo getPhoto()
    {
        return photo;
    }
}
