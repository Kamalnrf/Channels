package a9m2broadcast.kamalnrf.broacast.database;

/**
 * Created by kamalnrf on 2/7/16.
 */
public class BroadCastDBSchema
{
    public static final class Group
    {
        public static final String NAME = "groups";

        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
        }
    }

    public static final class UserGroup
    {
        public static final String NAME = "user_group";

        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String GROUPID = "group_id";
            public static final String USERID = "user_id";
        }
    }

    public static final class User
    {
        public static final String NAME = "user";

        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String FIRSTNAME = "first_name";
            public static final String PHONENO = "phone_no";
        }
    }
}
