package a9m2broadcast.kamalnrf.broacast.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import java.util.UUID;

import a9m2broadcast.kamalnrf.broacast.SingleFragmentActivity;
import a9m2broadcast.kamalnrf.broacast.model.BroadCastLab;
import a9m2broadcast.kamalnrf.broacast.model.Brodcast;

/**
 * Created by kamalnrf on 3/7/16.
 */
public class PostingScreenActivity extends SingleFragmentActivity
{
    private static final String EXTRA_ID = "a9m2broadcast.kamalnrf.broacast.ui";
    private static final String TAG = "PostingScreenFragment";
    private Brodcast mBroadCast;

    public static Intent newIntent (Context packageContext, UUID id)
    {
        Intent intent = new Intent(packageContext, PostingScreenActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected Fragment createFragment()
    {
        UUID channelId = (UUID) getIntent().getSerializableExtra(EXTRA_ID);

        mBroadCast = BroadCastLab.get(this).getBroadcasts(channelId);
        setTitle(mBroadCast.getmTitle());

        return PostingScreenFragment.newInstance(channelId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
