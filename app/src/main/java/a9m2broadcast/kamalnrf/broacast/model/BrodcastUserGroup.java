package a9m2broadcast.kamalnrf.broacast.model;

import java.util.UUID;

/**
 * Created by kamalnrf on 3/7/16.
 */
public class BrodcastUserGroup
{
    private UUID mUuid;
    private UUID mGroupID;
    private UUID mUserID;

    public BrodcastUserGroup(UUID mUuid) {
        this.mUuid = mUuid;
    }

    public BrodcastUserGroup()
    {
        this(UUID.randomUUID());
    }

    public UUID getmUserID() {
        return mUserID;
    }

    public void setmUserID(UUID mUserID) {
        this.mUserID = mUserID;
    }

    public UUID getmGroupID() {
        return mGroupID;
    }

    public void setmGroupID(UUID mGroupID) {
        this.mGroupID = mGroupID;
    }
}
