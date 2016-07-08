package a9m2broadcast.kamalnrf.broacast.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import a9m2broadcast.kamalnrf.broacast.BroadCastFragment;
import a9m2broadcast.kamalnrf.broacast.R;
import a9m2broadcast.kamalnrf.broacast.model.BroadCastLab;
import a9m2broadcast.kamalnrf.broacast.model.BroadCastUser;
import a9m2broadcast.kamalnrf.broacast.model.Brodcast;
import a9m2broadcast.kamalnrf.broacast.model.BrodcastUserGroup;
import a9m2broadcast.kamalnrf.broacast.util.ComandExe;

/**
 * Created by kamalnrf on 3/7/16.
 */
public class PostingScreenFragment extends Fragment
{
    private static final String ARG_ID = "id";
    private static final String TAG = "PostingScreenFragment";
    private static final String EXTRA_ID = "Paraticipant";
    private static final String name = "Opening_screen";

    private Brodcast mBroadCast;
    private EditText mMessage;
    private Uri uriContact;
    private String contactID;
    private static final int REQUEST_CONTACT = 1;
    private BroadCastUser mBroadCastUser;
    private BrodcastUserGroup mBroadCastGroup;
    private String bot = "Bot\n";
    private boolean isBot = true;
    private boolean showADD = false;
    private AdView adView;
    private ActionBarDrawerToggle mDrawerToggle;
    private static View view;

    private RecyclerView mRecyclerView;
    private PostingScreenAdapter mAdapter;

    List<String> message = new ArrayList<>();
    List<String> user_message = new ArrayList<>();
    String temp;

    //fragment argument
    public static PostingScreenFragment newInstance (UUID id)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ID, id);

        PostingScreenFragment fragment = new PostingScreenFragment();
        fragment.setArguments(args);


        return fragment;
    }


    //Fragment On Create
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        UUID id = (UUID) getArguments().getSerializable(ARG_ID);

        mBroadCast = BroadCastLab.get(getActivity()).getBroadcasts(id);
        mBroadCastGroup = BroadCastLab.get(getActivity()).getBrodcastUserGroup(id);
        mBroadCastUser = BroadCastLab.get(getActivity()).getBrodcastUser(id);

        BroadCastLab.get(getActivity())
                .upDateBroadCast(mBroadCast, mBroadCastGroup, mBroadCastUser);



        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.posting_screen , container, false);

       message.add(bot
                + mBroadCast.toString() +
                "\n" + mBroadCastUser.toString());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.posting_reycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMessage = (EditText) view.findViewById(R.id.message_edit);
        mMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = charSequence.toString();

                if (temp.equals('/'))
                {
                    final Dialog dialog = new Dialog(PostingScreenFragment.this.getActivity());
                    dialog.setTitle("Create new broad cast");
                    dialog.setContentView(R.layout.new_broad_cast);
                    dialog.show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (temp.equals("") || temp.equals(" ") || temp == null)
                    {
                        message.add(bot + "You are not allowed to send empty messages");
                        isBot = true;
                        showADD = true;

                    }
                    else {
                        isBot = false;
                        showADD = false;
                        user_message.add(temp);

                        ComandExe comandExe = new ComandExe(mBroadCast, mBroadCastGroup, mBroadCastUser);

                        if (comandExe.isCommand(temp)) {
                            String helper = comandExe.comand(temp, getActivity());
                            String[] comand = comandExe.splitMessage(temp);

                            message.add(bot + helper);

                            if (helper.equals("Deleted")) {
                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            } else if (helper.equals("Clearing"))
                                message.clear();
                            else if (helper.equals("+91 9515127528"))
                            {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(helper, null, temp, null, null);
                            }
                            else if (comand[0].equals("/shrug"))
                            {
                                for (int counter = 1; counter < mBroadCastUser.getmPhone().size(); counter++) {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(mBroadCastUser.getPhone(counter), null, helper, null, null);
                                }

                                message.add(bot + "Your message has been sent to "
                                        + comandExe.comand("/who", getActivity()));
                            }

                        } else {
                            //To-do shrug

                            for (int counter = 1; counter < mBroadCastUser.getmPhone().size(); counter++) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(mBroadCastUser.getPhone(counter), null, temp, null, null);
                            }

                            message.add(bot + "Your message has been sent to "
                                    + comandExe.comand("/who", getActivity()));

                            isBot = false;
                            showADD = true;
                        }
                    }
                }
                catch (Exception e)
                {
                    message.add(bot + "\"There was an issue while sending your message please check your " +
                            "input and try again\"");
                    isBot = true;
                }
                mMessage.clearComposingText();
                mMessage.getText().clear();

                onPause();
                onResume();
            }
        });


        return view;
    }


    private class PostingHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mMessageTextView;
        RelativeLayout adViewContainer;
        private TextView mUserMessageTextView;

        public PostingHolder(View itemView) {
            super(itemView);

            mMessageTextView = (TextView)
                    itemView.findViewById(R.id.message);

            mUserMessageTextView = (TextView)
                    itemView.findViewById(R.id.message_user);

        }


        //binds the message
        public void bind (String message, String userMessage)
        {
            if (showADD) {
                RelativeLayout adViewContainer = (RelativeLayout) view.findViewById(R.id.adViewContainer);
                adView = new AdView(getActivity(), "1206456892734598_1206782432702044", AdSize.BANNER_320_50);
                adViewContainer.addView(adView);
                adView.loadAd();

                adViewContainer.setVisibility(view.VISIBLE);
            }
            if (!isBot) {
                mUserMessageTextView.setVisibility(View.VISIBLE);
                mUserMessageTextView.setText(userMessage);
            }
            mMessageTextView.setText(message);
        }

        //binds the message
        public void bind (String message)
        {
            if (showADD) {
                RelativeLayout adViewContainer = (RelativeLayout) view.findViewById(R.id.adViewContainer);
                adView = new AdView(getActivity(), "1206456892734598_1206782432702044", AdSize.BANNER_320_50);
                adViewContainer.addView(adView);
                adView.loadAd();

                adViewContainer.setVisibility(view.VISIBLE);
            }
            mMessageTextView.setText(message);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public class PostingScreenAdapter extends RecyclerView.Adapter<PostingHolder>
    {
        private List<String> mMessage = new ArrayList<>();
        private List<String> mUserMessage = new ArrayList<>();

        public PostingScreenAdapter (List<String> mMessage, List<String> mUserMessage)
        {
            this.mMessage = mMessage;
            this.mUserMessage = mUserMessage;
        }

        @Override
        public PostingHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater
                    .inflate(R.layout.message, parent, false);
            return new PostingHolder(view);
        }

        @Override
        public void onBindViewHolder(PostingHolder holder, int position)
        {
            try {
                String message = mMessage.get(position);
                String user_message = mUserMessage.get(position);
                holder.bind(message, user_message);
            }
            catch (Exception e)
            {
                Log.i(TAG, e.getStackTrace().toString());

                String message = mMessage.get(position);
                holder.bind(message);
            }
        }

        private void set (List<String> message)
        {
            mMessage = message;
        }

        @Override
        public int getItemCount() {
            return mMessage.size();
        }
    }

    //Experiment


    public void updateUI()
    {
        if (mAdapter == null)
        {
            mAdapter = new PostingScreenAdapter(message, user_message);
            mRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.set(message);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_posting, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                Intent pick = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pick, REQUEST_CONTACT);
            return true;

            case R.id.action_settings_all:
                Intent intent = ParticipantsActivity
                        .newIntent(getActivity(), mBroadCast.getmUuid());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CONTACT && resultCode == getActivity().RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            mBroadCastUser.setmPhone(retrieveContactNumber());
            mBroadCastUser.setmFirstName(retrieveContactName());

            BroadCastLab.get(getActivity())
                    .addUser(mBroadCast, mBroadCastGroup, mBroadCastUser);

        }
    }

    private String retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getActivity().getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        Log.d(TAG, "Contact Phone Number: " + contactNumber);

        return contactNumber;
    }

    private String retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getActivity().getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);

        return contactName;
    }



    @Override
    public void onPause() {
        super.onPause();

        BroadCastLab.get(getActivity())
                .upDateBroadCast(mBroadCast, mBroadCastGroup, mBroadCastUser);

    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }


}
