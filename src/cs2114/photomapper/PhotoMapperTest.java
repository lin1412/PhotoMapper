package cs2114.photomapper;

import android.content.Intent;
import com.google.android.maps.GeoPoint;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.maps.MapView;

// -------------------------------------------------------------------------
/**
 * Test the methods of the PhotoMapperActivity class.
 *
 * @author Hang
 * @version Oct 12, 2011
 */
public class PhotoMapperTest
    extends student.AndroidTestCase<PhotoMapperActivity>
{
    private MapView  mapView;
    private Button   add;
    private Button   remove;
    private Button   view;
    private TextView statusLabel;


    // ----------------------------------------------------------
    /**
     * Create a new PhotoMapperTest object.
     */
    public PhotoMapperTest()
    {
        super(PhotoMapperActivity.class);

    }

    /**
     * Set up the GUI of the emulator.
     */
    public void setUp()
    {
        mapView = getView(MapView.class, R.id.mapView);
        add = getView(Button.class, R.id.addPhoto);
        remove = getView(Button.class, R.id.removePhoto);
        view = getView(Button.class, R.id.viewPhoto);
        statusLabel = getView(TextView.class, R.id.statusLabel);
        mapView.getOverlays().clear();
        getActivity().getList().clear();

    }

    /**
     * create a method for clicking on the overlays.
     *
     * @param latitude
     *            the latitude coordinate of the photo.
     * @param longitude
     *            the longitude coordinate of the photo.
     */
    private void clickCoordinates(float latitude, float longitude)
    {
        mapView = getView(MapView.class, R.id.mapView);

        mapView.getController()
            .animateTo(
                new GeoPoint(
                    (int)(latitude * 1000000),
                    (int)(longitude * 1000000)));

        click(mapView, mapView.getWidth() / 2, mapView.getHeight() / 2);
    }


    // ----------------------------------------------------------
    /**
     * test the add method.
     */
    public void testAdd()
    {
        setUp();
        // test what happens when calling remove when list is empty.
        click(remove);
        // test the status bar on startup.
        assertEquals(statusLabel.getText(), "Please select or add a photo.");

        // Indicate that the next image chooser that is started should
        // automatically select and return some_image.jpg as its result.
        // Click the button that will cause the above chooser to be started.
        // add a photo without a location date
        prepareToSelectMediaInChooser("photomapper4.jpg");
        click(add);
        assertEquals( statusLabel.getText(),
            "Photo does not have location data.");

        prepareToSelectMediaInChooser("photomapper1.jpg");
        click(add);
        // test the photo added by the imagePath.
        //assertEquals(
           // PhotoMapperActivity.getCurrent().getPath(),
            //"photomapper1.jpg");

        // add a photo without a location date.
        prepareToSelectMediaInChooser("photomapper4.jpg");
        click(add);
        assertEquals( statusLabel.getText(),
            "Photo does not have location data.");

        // add a photo that's already in the map.
        prepareToSelectMediaInChooser("photomapper1.jpg");
        click(add);
        assertEquals(statusLabel.getText(), "Photo already added.");

        // add valid photos.
        prepareToSelectMediaInChooser("photomapper2.jpg");
        click(add);
        prepareToSelectMediaInChooser("photomapper3.jpg");
        click(add);
        // test the size of the list and overlay.
        assertEquals(getActivity().getList().size(), 3);
        assertEquals(getActivity().getList().size(), mapView
            .getOverlays().size());

    }


    // ----------------------------------------------------------
    /**
     * test the remove method.
     */
    public void testRemove()
    {
        setUp();
        // test what happens when calling remove when list is empty.
        click(remove);
        assertEquals(statusLabel.getText(), "Please select or add a photo.");

        // add valid photos.
        prepareToSelectMediaInChooser("photomapper1.jpg");
        click(add);
        prepareToSelectMediaInChooser("photomapper2.jpg");
        click(add);
        prepareToSelectMediaInChooser("photomapper3.jpg");
        click(add);
        // test the size of the list and overlay.
        assertEquals(getActivity().getList().size(), 3);
        assertEquals(getActivity().getList().size(), mapView
            .getOverlays().size());

        clickCoordinates(
            getActivity().getList().get(1).getCoord()[0],
            getActivity().getList().get(1).getCoord()[1]);

        // remove the photo then test the status bar message and size
        click(remove);
        assertEquals(statusLabel.getText(), "Please select or add a photo.");
        assertEquals(getActivity().getList().size(), 2);

        //remove again when nothing is selected.
        click(remove);
        assertEquals(getActivity().getList().size(), 2);
    }

    // ----------------------------------------------------------
    /**
     * test the click method.
     */
    public void testClick()
    {
        setUp();
        //add 2 photos.
        prepareToSelectMediaInChooser("photomapper3.jpg");
        click(add);
        prepareToSelectMediaInChooser("photomapper1.jpg");
        click(add);

        //click on the 1st photo.
        clickCoordinates(
            getActivity().getList().get(0).getCoord()[0],
            getActivity().getList().get(0).getCoord()[1]);
        //compare the coordinate of the photo and the status label.
        assertEquals(statusLabel.getText(), PhotoMapperActivity.getCurrent()
            .getCoord()[0]
            + ", " + PhotoMapperActivity.getCurrent().getCoord()[1]);
    }


    // ----------------------------------------------------------
    /**
     * test the view method when nothing is selected.
     */
    public void testEmptyView()
    {
        setUp();
        click(view);
        assertEquals(statusLabel.getText(), "Please select or add a photo.");
    }


    // ----------------------------------------------------------
    /**
     *  test when an photo is selected.
     */
    public void testView()
    {
        setUp();
        prepareToSelectMediaInChooser("photomapper2.jpg");
        click(add);
        prepareForUpcomingActivity(Intent.ACTION_VIEW);
        click(view);

    }

    // ----------------------------------------------------------
    /**
     * test the isRouthDisplay method, which will always return false.
     */
    public void testRouthDisplay()
    {
        setUp();
        assertEquals(getActivity().isRouteDisplayed(), false);

    }


}
