package a9m2broadcast.kamalnrf.broacast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class BroadCastActivity extends SingleFragmentActivity
{
    private static final String TAG = "BroadCastActivity";
    private static final String name = "Opening_screen";

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, BroadCastActivity.class);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return new BroadCastFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

    }

}
