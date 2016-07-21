package a9m2broadcast.kamalnrf.broacast.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sackcentury.shinebuttonlib.ShineButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import a9m2broadcast.kamalnrf.broacast.BroadCastActivity;
import a9m2broadcast.kamalnrf.broacast.R;
import a9m2broadcast.kamalnrf.broacast.model.BroadCastLab;
import a9m2broadcast.kamalnrf.broacast.model.BroadCastUser;
import a9m2broadcast.kamalnrf.broacast.model.Brodcast;
import a9m2broadcast.kamalnrf.broacast.model.BrodcastUserGroup;
import a9m2broadcast.kamalnrf.broacast.util.ComandExe;

import static android.provider.ContactsContract.*;

/**
 * Created by kamalnrf on 3/7/16.
 */
public class ParticipantsActivity extends AppCompatActivity
{
    private static final String name1 = "Participants_screen";
    private static final String EXTRA_ID = "Paraticipant";
    private static final String ARG_ID = "id";
    private static final int REQUEST_CONTACT = 1;
    private static final String TAG = "ParticipantsActivity";
    private Uri uriContact;
    private String contactID;
    private Brodcast mBrodcast;
    private BroadCastUser mBroadCastUser;
    private BrodcastUserGroup mBroadCastGroup;
    private static UUID id;
    private List<String> name;
    private Button mDelete;

    private RecyclerView mRecyclerView;
    private ParticipantsScreenAdapter mAdapter;

    public static Intent newIntent (Context packageContext, UUID id)
    {
        Log.i(TAG, " Participants" + id.toString());
        ParticipantsActivity.id = id;
        Intent intent = new Intent(packageContext, ParticipantsActivity.class);
        intent.putExtra(EXTRA_ID, id);

        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_participants);

        mRecyclerView = (RecyclerView) findViewById(R.id.reycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.i(TAG, " Participants" + id.toString());

        mBrodcast = BroadCastLab.get(this).getBroadcasts(id);
        mBroadCastGroup = BroadCastLab.get(this).getBrodcastUserGroup(id);
        mBroadCastUser = BroadCastLab.get(this).getBrodcastUser(id);

        setTitle(mBrodcast.getmTitle());

        name = mBroadCastUser.getmFirstName();

        mDelete = (Button) findViewById(R.id.delete_broadcast);
        mDelete.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ComandExe comandExe = new ComandExe(mBrodcast, mBroadCastGroup, mBroadCastUser);

                comandExe.comand("/delete", getApplicationContext());

                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                getApplicationContext().startActivity(BroadCastActivity.newIntent(getApplicationContext()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        mDelete.setBackgroundColor(Color.parseColor("#F4511E"));

    }

    private class ParticipantsHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mParticipantsTextView;

        public ParticipantsHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mParticipantsTextView = (TextView)
                    itemView.findViewById(R.id.title);
        }

        public void bind (String participants)
        {
            mParticipantsTextView.setText(participants);
            mParticipantsTextView.setTextColor(Color.BLACK);

        }

        @Override
        public void onClick(View view) {
            try {
                final Dialog dialog = new Dialog(ParticipantsActivity.this.getApplicationContext());
                dialog.setTitle("Phone number:");
                dialog.setContentView(R.layout.number_broad_cast);
                final TextView title1 = (TextView) dialog.findViewById(R.id.phone_num);
                title1.setText(mBroadCastUser.getmNum(mParticipantsTextView.getText().toString()));

                dialog.show();
            }
            catch (Exception e)
            {
                Log.e(TAG, e.getStackTrace().toString());
            }
        }
    }

    public class ParticipantsScreenAdapter extends RecyclerView.Adapter<ParticipantsHolder>
    {
        private List<String> mMemebers = mBroadCastUser.getmFirstName();
        private TextView mTextView;

        public ParticipantsScreenAdapter(List<String> mMemebers) {
            this.mMemebers = mMemebers;
        }

        @Override
        public ParticipantsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

            View view = layoutInflater
                    .inflate(R.layout.broad_cast_list, parent, false);

            mTextView = (TextView) view.findViewById(R.id.title);
            return new ParticipantsHolder(view);
        }

        @Override
        public void onBindViewHolder(ParticipantsHolder holder, int position)
        {
            holder.bind(mMemebers.get(position));
        }

        private void set (List<String> memebers)
        {
            mMemebers = memebers;
        }


        @Override
        public int getItemCount() {
            return mMemebers.size();
        }
    }

    public void updateUI()
    {
        if (mAdapter == null)
        {
            mAdapter = new ParticipantsScreenAdapter(name);
            mRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.set(mBroadCastUser.getmFirstName());
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_broad_cast, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        final Intent pickContact = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);

        switch(item.getItemId())
        {
            case R.id.action_settings:
                startActivityForResult(pickContact, REQUEST_CONTACT);

                onPause();
                onResume();
              return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CONTACT && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            mBroadCastUser.setmPhone(retrieveContactNumber());
            mBroadCastUser.setmFirstName(retrieveContactName());

            BroadCastLab.get(this)
                    .addUser(mBrodcast, mBroadCastGroup, mBroadCastUser);


        }
    }

    private String retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
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
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

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

        BroadCastLab.get(this)
                .upDateBroadCast(mBrodcast, mBroadCastGroup, mBroadCastUser);
    }

    @Override
    public void onResume() {
        super.onResume();

        mBroadCastUser.refreshHashMap();

        updateUI();
    }
}
