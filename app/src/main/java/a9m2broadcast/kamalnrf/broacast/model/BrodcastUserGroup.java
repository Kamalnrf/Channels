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
    private Boolean mTwitter = false;
    private Boolean mFb = false;
    private Boolean mGmail = false;

    public BrodcastUserGroup(UUID mUuid) {
        this.mUuid = mUuid;
    }

    public BrodcastUserGroup()
    {
        this(UUID.randomUUID());
    }

    public Boolean getmTwitter() {
        return mTwitter;
    }

    public void setmTwitter(Boolean mTwitter) {
        this.mTwitter = mTwitter;
    }

    public Boolean getmFb() {
        return mFb;
    }

    public void setmFb(Boolean mFb) {
        this.mFb = mFb;
    }

    public Boolean getmGmail() {
        return mGmail;
    }

    public void setmGmail(Boolean mGmail) {
        this.mGmail = mGmail;
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
