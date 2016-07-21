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
import com.instabug.library.IBGInvocationEvent;
import com.instabug.library.Instabug;

public class BroadCastActivity extends SingleFragmentActivity
{
    private static final String TAG = "BroadCastActivity";
    private static final String name = "Opening_screen";
    private static final String key = "62c5a1534c9c7f60ab3472c3f04889b3";

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

        new Instabug.Builder(this.getApplication(), key)
                .setInvocationEvent(IBGInvocationEvent.IBGInvocationEventShake)
                .build();

    }

}
