package a9m2broadcast.kamalnrf.broacast;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.appevents.AppEventsLogger;

import java.util.List;

import a9m2broadcast.kamalnrf.broacast.model.BroadCastLab;
import a9m2broadcast.kamalnrf.broacast.model.BroadCastUser;
import a9m2broadcast.kamalnrf.broacast.model.Brodcast;
import a9m2broadcast.kamalnrf.broacast.model.BrodcastUserGroup;
import a9m2broadcast.kamalnrf.broacast.ui.PostingScreenActivity;

/**
 * Created by kamalnrf on 2/7/16.
 */
public class BroadCastFragment extends Fragment {
    public static final String TAG = "BroadCastFragment";

    private RecyclerView mRecyclerView;
    private BroadCastAdapter mAdapter;
    private String title;
    private AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getContext());
        AppEventsLogger.activateApp(getActivity().getApplication());


        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_broad_cast, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.reycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(BroadCastFragment.this.getActivity());
                dialog.setContentView(R.layout.new_broad_cast);
                dialog.show();

                final EditText title1 = (EditText) dialog.findViewById(R.id.broacast_title);
                title1.setFilters(new InputFilter[]
                        {
                                new InputFilter.LengthFilter(20)
                        });
                title1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        title = charSequence.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                TextView create = (TextView) dialog.findViewById(R.id.button_create);
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Brodcast brodcast = new Brodcast();
                        BrodcastUserGroup brodcastUserGroup = new BrodcastUserGroup();
                        brodcast.setmTitle(title);

                        brodcastUserGroup.setmGroupID(brodcast.getmUuid());
                        brodcastUserGroup.setmUserID(brodcast.getmUuid());

                        BroadCastUser broadCastUser = new BroadCastUser();
                        broadCastUser.setmUuid(brodcastUserGroup.getmUserID());

                        //BroadCastLab.get(getActivity()).addBroadCast(brodcast, brodcastUserGroup, broadCastUser);

                        Intent intent = PostingScreenActivity
                                .newIntent(getActivity(), brodcast.getmUuid());
                        startActivity(intent);

                        BroadCastLab.get(getActivity()).addBroadCast(brodcast, brodcastUserGroup, broadCastUser);


                        dialog.cancel();

                        onPause();
                        onResume();

                Log.i(TAG, "A new database has been created");
                    }
                });
            }
        });

        RelativeLayout adViewContainer = (RelativeLayout) view.findViewById(R.id.adViewContainer);

        adView = new AdView(getActivity(), "1206456892734598_1206462002734087", AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        adView.loadAd();

        return view;
    }

    //Inner class for holder to hold the views
    private class BroadCastHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Brodcast mBrodcast;
        private TextView mTitleTextView;

        public BroadCastHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.title);
        }

        //binds the title,date and check box
        public void bind(Brodcast brodcast) {
            mBrodcast = brodcast;

            mTitleTextView.setText(mBrodcast.getmTitle());

        }

        @Override
        public void onClick(View v) {
            Intent intent = PostingScreenActivity.newIntent(getActivity(), mBrodcast.getmUuid());
            startActivity(intent);

        }
    }

    //Recycler view will comunicate with this class when holder is needed to be created
    private class BroadCastAdapter extends RecyclerView.Adapter<BroadCastHolder> {
        private List<Brodcast> mBrodcastList;

        public BroadCastAdapter(List<Brodcast> brodcastList) {
            mBrodcastList = brodcastList;
        }

        //This method is called to create a view
        @Override
        public BroadCastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());


            View view = layoutInflater
                    .inflate(R.layout.broad_cast_list, parent, false);
            return new BroadCastHolder(view);

        }

        //binds them together
        @Override
        public void onBindViewHolder(BroadCastHolder holder, int position) {
            Brodcast brodcast = mBrodcastList.get(position);
            holder.bind(brodcast);
        }

        private void set(List<Brodcast> brodcastList) {
            mBrodcastList = brodcastList;
        }

        //RecycleView Calls this method to get the size of mCrimes
        @Override
        public int getItemCount() {
            return mBrodcastList.size();
        }
    }

    public void updateUI() {
        BroadCastLab broadCastLab = BroadCastLab.get(getActivity());
        List<Brodcast> brodcasts = broadCastLab.getBroadcasts();

        if (mAdapter == null) {
            mAdapter = new BroadCastAdapter(brodcasts);
            mRecyclerView.setAdapter(mAdapter);
        }

            else
            {
                mAdapter.set(brodcasts);
                mAdapter.notifyDataSetChanged();
            }

    }


    @Override
    public void onResume () {
        super.onResume();

        updateUI();
    }


    @Override
    public void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);
    }
    }
