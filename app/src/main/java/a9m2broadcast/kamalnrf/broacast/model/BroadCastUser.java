package a9m2broadcast.kamalnrf.broacast.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by kamalnrf on 3/7/16.
 */
public class BroadCastUser
{
    private UUID mUuid;
    List<String> mFirstName;
    List<String> mPhone;
    List<String> mEmailIDs;
    Map<String, String> mNum = new HashMap<>();

    public void setmUuid(UUID mUuid) {
        this.mUuid = mUuid;
    }

    public  BroadCastUser ()
    {
        //Do-nothing constructor
    }

    public void refreshHashMap ()
    {
        for (int counter = 1; counter < mFirstName.size(); counter++)
            mNum.put(mFirstName.get(counter), mNum.get(counter));
    }

    //---GETTERS---
    public Map<String, String> getmNum() {
        return mNum;
    }

    public String getmNum(String name) {
        return mNum.get(name);
    }

    public String getFirstName (int positon)
    {
        return mFirstName.get(positon);
    }

    public String getPhone (int positon)
    {
        return mPhone.get(positon);
    }


    public String getEmailIDs (int position){
        return mEmailIDs.get(position);
    }

    public UUID getmUuid() {
        return mUuid;
    }

    public List<String> getmPhone() {

        return mPhone;
    }

    public List<String> getmEmailIDs() {
        return mEmailIDs;
    }

    //--Setters--

    public void setmEmailIDs (String emailID){
        this.mEmailIDs.add(emailID);
    }

    public void setmEmailIDs (List<String> emailID){
        this.mEmailIDs = emailID;
    }

    public void setmPhone(String phoneNo) {
        this.mPhone.add(phoneNo);
    }


    public List<String> getmFirstName() {

        return mFirstName;
    }

    public void setmFirstName(String name) {
        this.mFirstName.add(name);
    }

    public void setmFirstName(List<String> mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setmPhone(List<String> mPhone) {
        this.mPhone = mPhone;
    }

    public void removeContact (int id) {
        mFirstName.remove(id);
        mPhone.remove(id);
        mNum.remove(id);
    }

    @Override
    public String toString() {
        try {
            int size = mPhone.size();

            if (size > 1) {
                return "There are  " + (size - 1) + " participants in your list \n " +
                        "For more options type and send /help";
            }
            else
                return "It seems there are no user in your broadcast so add few contacts. \n" +
                        "For more options type and send /help";

        }
        catch (Exception e) {
            return "It seems there are no user in your broadcast so add few contact in our edit section \n" +
                    "For more options type and send /help";
        }
    }
}
