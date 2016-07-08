package a9m2broadcast.kamalnrf.broacast.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import java.util.List;
import java.util.UUID;

import a9m2broadcast.kamalnrf.broacast.BroadCastFragment;
import a9m2broadcast.kamalnrf.broacast.R;
import a9m2broadcast.kamalnrf.broacast.model.BroadCastLab;
import a9m2broadcast.kamalnrf.broacast.model.Brodcast;

/**
 * Created by kamalnrf on 3/7/16.
 */
public class PagerActivity extends AppCompatActivity
{
    private static final String EXTRA_ID = "a9m2broadcast.kamalnrf.broacast.ui";
    private static final String TAG = "PostingScreenFragment";
    private String name = "Posting_Screen";
    private ViewPager mViewPager;
    private List<Brodcast> mBroadCast;

    public static Intent newIntent (Context packageContext, UUID id)
    {
        Intent intent = new Intent(packageContext, PagerActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pager);

        //Adapters for view pager
        mViewPager = (ViewPager) findViewById(R.id.view_pager);



        mBroadCast = BroadCastLab.get(this).getBroadcasts();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position)
            {
                Brodcast brodcast = mBroadCast.get(position);
                return PostingScreenFragment.newInstance(brodcast.getmUuid());
            }

            @Override
            public int getCount() {
                return mBroadCast.size();
            }

        });

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_ID);

        for (int i = 0; i < mBroadCast.size(); i++)
        {
            if (mBroadCast.get(i).getmUuid().equals(crimeId))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
