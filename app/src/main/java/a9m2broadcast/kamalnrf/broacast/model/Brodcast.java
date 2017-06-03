package a9m2broadcast.kamalnrf.broacast.model;

import java.util.UUID;

/**
 * Created by kamalnrf on 2/7/16.
 */
public class Brodcast
{
    private UUID mUuid;
    private String mTitle = "Untitled";


    public Brodcast ()
    {
        this(UUID.randomUUID());
    }

    public Brodcast(UUID mUuid) {
        this.mUuid = mUuid;
    }


    public String getmTitle() {
        return mTitle;
    }


    public UUID getmUuid() {

        return mUuid;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public String toString() {
        return "You are posting in : " + mTitle + " broadcast";
    }
}
