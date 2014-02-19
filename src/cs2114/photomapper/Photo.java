package cs2114.photomapper;

import java.io.IOException;
import student.android.ExifInterface;
import android.net.Uri;

// -------------------------------------------------------------------------
/**
 * The photo class will contains all the information on each photo.
 *
 * @author Hang Lin
 * @version Oct 10, 2011
 */
public class Photo
{
    /**
     * the uri of the photo.
     */
    Uri    uri;
    /**
     * the imagePath of the photo.
     */
    String imagePath = "";
    /**
     * contains the coordinates for the GPS
     */
    float[]  coord   = new float [2];


    // ----------------------------------------------------------
    /**
     * Create a new Photo object.
     *
     * @param u
     *            the Uri of the photo.
     * @param path
     *            the imagePath of the photo.
     * @throws IOException
     */
    public Photo(Uri u, String path)
        throws IOException
    {
        uri = u;
        imagePath = path;
        ExifInterface exif;
        try
        {
            // get the coordinate from the photo and output it into coord.
            exif = new student.android.ExifInterface(path);
            exif.getLatLong(coord);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    // ----------------------------------------------------------
    /**
     * gets the Uri from the photo.
     *
     * @return the uri of the photo.
     */
    public Uri getUri()
    {
        return uri;
    }


    // ----------------------------------------------------------
    /**
     * gets the imagePath of the photo.
     *
     * @return the imagePath of the photo.
     */
    public String getPath()
    {
        return imagePath;
    }


    // ----------------------------------------------------------
    /**
     * gets the array containing the coordinates of the photo.
     *
     * @return the coord array.
     */
    public float[] getCoord()
    {
        return coord;
    }

}
