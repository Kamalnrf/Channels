package a9m2broadcast.kamalnrf.broacast.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static a9m2broadcast.kamalnrf.broacast.database.BroadCastDBSchema.*;

/**
 * Created by kamalnrf on 2/7/16.
 */
public class BroadCastBaseHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "broadCast.db";

    public BroadCastBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table "+ Group.NAME + "(" +
                " _id integer primary key autoincrement, " +
                Group.Cols.TITLE + ", " +
                Group.Cols.UUID + ")"
        );

        db.execSQL("create table "+ UserGroup.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserGroup.Cols.GROUPID + ", " +
                UserGroup.Cols.USERID + ", " +
                User.Cols.UUID + ")"
        );

        db.execSQL("create table "+ User.NAME + "(" +
                " _id integer primary key autoincrement, " +
                User.Cols.FIRSTNAME + ", " +
                User.Cols.PHONENO + ", " +
                User.Cols.UUID + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
