package a9m2broadcast.kamalnrf.broacast.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import a9m2broadcast.kamalnrf.broacast.database.BroadCastBaseHelper;
import a9m2broadcast.kamalnrf.broacast.database.BroadCastCursorWrapper;
import a9m2broadcast.kamalnrf.broacast.database.BroadCastDBSchema;
import a9m2broadcast.kamalnrf.broacast.database.BroadCastDBSchema.Group;
import a9m2broadcast.kamalnrf.broacast.database.BroadCastDBSchema.UserGroup;

import static a9m2broadcast.kamalnrf.broacast.database.BroadCastDBSchema.*;

/**
 * Created by kamalnrf on 2/7/16.
 */
public class BroadCastLab
{
    private static final String TAG = "BroadCastLab";
    private static BroadCastLab sBroadCastLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public BroadCastLab(Context mContext) {
        this.mContext = mContext;

       mDatabase = new BroadCastBaseHelper(mContext)
               .getWritableDatabase();
    }

    public static BroadCastLab get (Context context)
    {
        if (sBroadCastLab == null)
            sBroadCastLab = new BroadCastLab(context);

        return sBroadCastLab;
    }

    //Values will added to the groups data base database
    public static ContentValues getGroupValues (Brodcast brodcast)
    {
        ContentValues values = new ContentValues();

        values.put(Group.Cols.UUID, brodcast.getmUuid().toString());
        values.put(Group.Cols.TITLE, brodcast.getmTitle());

        return values;
    }

    //Values will be Added to the UserGroup
    public static ContentValues getUserGroupValues (BrodcastUserGroup brodcastUserGroup, Brodcast brodcast)
    {
        ContentValues values = new ContentValues();

        values.put(UserGroup.Cols.UUID, UUID.randomUUID().toString());
        values.put(UserGroup.Cols.GROUPID, brodcast.getmUuid().toString());
        values.put(UserGroup.Cols.USERID, brodcast.getmUuid().toString());

        return values;
    }

    //Values will be Added to the UserGroup
    public static ContentValues getUserValues (BroadCastUser broadCastUser, BrodcastUserGroup brodcastUserGroup)
    {
        ContentValues values = new ContentValues();

        values.put(User.Cols.UUID, brodcastUserGroup.getmUserID().toString());

        try {
            if (broadCastUser.getmFirstName() != null) {
                for (int counter = 0; counter < broadCastUser.getmFirstName().size(); counter++) {
                    values.put(User.Cols.FIRSTNAME, broadCastUser.getmFirstName().get(counter));
                }

                for (int counter = 0; counter < broadCastUser.getmPhone().size(); counter++) {
                    values.put(User.Cols.PHONENO, broadCastUser.getmPhone().get(counter));
                }
            }
        }
        catch (Exception e)
        {
            Log.i(TAG , "Verify the fucking data : " + brodcastUserGroup.getmUserID() + " " + broadCastUser.getmUuid());
        }

        Log.i(TAG , "Verify the fucking data : " + brodcastUserGroup.getmUserID() + " " + broadCastUser.getmUuid());

        return values;
    }

    //Retrives the broadcast groups.
    public List<Brodcast> getBroadcasts()
    {
        List<Brodcast> brodcasts = new ArrayList<>();

        BroadCastCursorWrapper cursor = queryGroups(Group.NAME, null, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                brodcasts.add(cursor.getBroadCast());
                cursor.moveToNext();
            }

        }
        finally
        {
            cursor.close();
        }

        return brodcasts;
    }

    //Retrives the broadcast groups.
    public Brodcast getBroadcasts(UUID id)
    {
        BroadCastCursorWrapper cursor = queryGroups(Group.NAME
                ,
                Group.Cols.UUID + " =? "
                , new String[]
                        {
                                id.toString()
                        }
        );

        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getBroadCast();
        }
        finally
        {
            cursor.close();
        }
    }

    public BrodcastUserGroup getBrodcastUserGroup (UUID id)
    {
        BroadCastCursorWrapper cursor = queryGroups(UserGroup.NAME
                ,
                UserGroup.Cols.USERID + " =? "
                , new String[]
                        {
                                id.toString()
                        }
        );

        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getUserGroup();
        }
        finally
        {
            cursor.close();
        }
    }

    public BroadCastUser getBrodcastUser (UUID id)
    {
        BroadCastCursorWrapper cursor = queryGroups(User.NAME
                ,
                User.Cols.UUID + " =? "
                , new String[]
                        {
                                id.toString()
                        }
        );

        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getUser(cursor);
        }
        finally
        {
            cursor.close();
        }
    }

    private BroadCastCursorWrapper queryGroups (String name, String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                name,
                null, // Colums - null select all colums
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null // order

        );

        return new BroadCastCursorWrapper(cursor);
    }

    //Delete broadcast group
    public void DeleteBroadCast (UUID brodcastID)
    {
        String uuidString = brodcastID.toString();

        mDatabase.delete(Group.NAME, Group.Cols.UUID + " = ?", new String[]{uuidString});
    }

    //Update broadcast group
    public void upDateBroadCast(Brodcast brodcast, BrodcastUserGroup brodcastUserGroup, BroadCastUser brodcastUser)
    {
        String uuidString = brodcast.getmUuid().toString();
        Log.i(TAG, "What the fuck " +Group.Cols.UUID +" uuid" +uuidString );
        ContentValues values = getGroupValues(brodcast);
        ContentValues values1 = getUserGroupValues(brodcastUserGroup, brodcast);
        //ContentValues values2 = getUserValues(brodcastUser, brodcastUserGroup);

        try{
            mDatabase.update(Group.NAME, values,
                    "uuid" + " =?"
                    , new String[]{uuidString});
            Log.i(TAG, "Data has been updated into this table :" + Group.NAME );

        }
        catch (Exception e)
        {
            Log.e(TAG, "There was an error while updating data into this table :" + Group.NAME );
        }

        try{
            mDatabase.update(UserGroup.NAME, values1,
                    UserGroup.Cols.USERID + " =?"
                    , new String[]{uuidString});

            Log.i(TAG, "Data has been updated into this table :" + UserGroup.NAME );
        }
        catch (Exception e)
        {
            Log.e(TAG, "There was an error while updating data into this table :" + UserGroup.NAME );
        }

        //This will update user data base with only last user.
       /* try{
            mDatabase.update(User.NAME, values2,
                    User.Cols.UUID + " =?"
                    , new String[]{uuidString});

            Log.i(TAG, "Data has been Updated into this table :" + User.NAME );
        }
        catch (Exception e)
        {
            Log.e(TAG, "There was an error while updating data into this table :" + User.NAME );
        }*/
    }


    //Add BroadCast
    public void addBroadCast (Brodcast brodcast, BrodcastUserGroup brodcastUserGroup, BroadCastUser broadCastUser)
    {
        ContentValues values = getGroupValues(brodcast);
        ContentValues values1 = getUserGroupValues(brodcastUserGroup, brodcast);
        ContentValues values2 = getUserValues(broadCastUser, brodcastUserGroup);

        try{
            mDatabase.insert(BroadCastDBSchema.Group.NAME, null, values);
            Log.i(TAG, "Data has been inserted into this table :" + Group.NAME );

        }
        catch (Exception e)
        {
            Log.e(TAG, "There was an error while inserting data into this table :" + Group.NAME );
        }

        try{
            mDatabase.insert(UserGroup.NAME, null, values1);

            Log.i(TAG, "Data has been inserted into this table :" + UserGroup.NAME );
        }
        catch (Exception e)
        {
            Log.e(TAG, "There was an error while inserting data into this table :" + UserGroup.NAME );
        }

        try{
            mDatabase.insert(User.NAME, null, values2);

            Log.i(TAG, "Data has been inserted into this table :" + User.NAME );
        }
        catch (Exception e)
        {
            Log.e(TAG, "There was an error while inserting data into this table :" + User.NAME );
        }
    }

    public void addUser (Brodcast brodcast, BrodcastUserGroup brodcastUserGroup, BroadCastUser broadCastUser)
    {
        ContentValues values = getGroupValues(brodcast);
        ContentValues values1 = getUserGroupValues(brodcastUserGroup, brodcast);
        ContentValues values2 = getUserValues(broadCastUser, brodcastUserGroup);

        try{
            mDatabase.insert(User.NAME, null, values2);

            Log.i(TAG, "Data has been inserted into this table :" + User.NAME );
        }
        catch (Exception e)
        {
            Log.e(TAG, "There was an error while inserting data into this table :" + User.NAME );
        }
    }

    public void deleteUser (Brodcast brodcast, BrodcastUserGroup brodcastUserGroup, BroadCastUser broadCastUser
    , String name)
    {
        ContentValues values = getGroupValues(brodcast);
        ContentValues values1 = getUserGroupValues(brodcastUserGroup, brodcast);
        ContentValues values2 = getUserValues(broadCastUser, brodcastUserGroup);

        try{
            mDatabase.delete(User.NAME, "first_name=?", new String[] {
                    name
            });

            Log.i(TAG, "Data has been deleted from this table :" + User.NAME );
        }
        catch (Exception e)
        {
            Log.e(TAG, "There was an error while deleting data into this table :" + User.NAME );
        }
    }

}
