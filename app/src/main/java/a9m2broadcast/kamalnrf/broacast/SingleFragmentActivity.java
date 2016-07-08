package a9m2broadcast.kamalnrf.broacast;

//Used for reusing the onCreat method in different classes
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kamalnrf on 8/4/16.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity
{
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //restores the data from savedInstanceState...
        super.onCreate(savedInstanceState);

        //Displays the content in activity_fragment.xml ..
        setContentView(R.layout.activity_fragment);

        //Calling FragmentManager to add fragments into activity code..
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        /*
        BroadCastActivity is a controller that interacts with model and view objects.
        Its job is to present the details of a specific Broadcast and update those
        details as the user changes them.
         */

        if (fragment == null)
        {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}