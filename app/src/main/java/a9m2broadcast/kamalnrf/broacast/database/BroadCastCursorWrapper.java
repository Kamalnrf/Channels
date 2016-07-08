package a9m2broadcast.kamalnrf.broacast.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import a9m2broadcast.kamalnrf.broacast.database.BroadCastDBSchema.Group;
import a9m2broadcast.kamalnrf.broacast.database.BroadCastDBSchema.UserGroup;
import a9m2broadcast.kamalnrf.broacast.model.BroadCastLab;
import a9m2broadcast.kamalnrf.broacast.model.BroadCastUser;
import a9m2broadcast.kamalnrf.broacast.model.Brodcast;
import a9m2broadcast.kamalnrf.broacast.model.BrodcastUserGroup;

import static a9m2broadcast.kamalnrf.broacast.database.BroadCastDBSchema.*;

/**
 * Created by kamalnrf on 2/7/16.
 */
public class BroadCastCursorWrapper extends CursorWrapper
{
    private SQLiteDatabase mDatabase;

    public BroadCastCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Brodcast getBroadCast ()
    {
        String uuidString = getString(getColumnIndex(Group.Cols.UUID));
        String title = getString(getColumnIndex(Group.Cols.TITLE));


        Brodcast brodcast = new Brodcast(UUID.fromString(uuidString));
        brodcast.setmTitle(title);

        return brodcast;
    }

    public BrodcastUserGroup getUserGroup ()
    {
        String uuid = getString(getColumnIndex(UserGroup.Cols.UUID));
        String groupID = getString(getColumnIndex(UserGroup.Cols.GROUPID));
        String userID = getString(getColumnIndex(UserGroup.Cols.USERID));

        BrodcastUserGroup brodcastUserGroup = new BrodcastUserGroup(UUID.fromString(uuid));

        brodcastUserGroup.setmGroupID(UUID.fromString(groupID));
        brodcastUserGroup.setmUserID(UUID.fromString(userID));

        return brodcastUserGroup;
    }

    public BroadCastUser getUser (Cursor cursor)
    {
        List<String> firstName = new ArrayList<>();
        List<String> lastName = new ArrayList<>();
        List<String> phoneNo = new ArrayList<>();

        String uuerID = getString(getColumnIndex(User.Cols.UUID));

        try {
            while (!cursor.isAfterLast()) {
                firstName.add(getString(getColumnIndex(User.Cols.FIRSTNAME)));
                phoneNo.add(getString(getColumnIndex(User.Cols.PHONENO)));

                cursor.moveToNext();
            }
        }
        catch (Exception e)
        {
            Log.e("BroadCastCursorWrapper ", "you have fucked up in the broadCastWrapper at getUser function" );
        }

        BroadCastUser broadCastUser = new BroadCastUser();
        broadCastUser.setmUuid(UUID.fromString(uuerID));
        broadCastUser.setmFirstName(firstName);
        broadCastUser.setmPhone(phoneNo);

        return broadCastUser;
    }


    private Cursor query (String name, String whereClause, String[] whereArgs)
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

        return cursor;
    }
}
