package a9m2broadcast.kamalnrf.broacast.util;

import android.content.Context;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import a9m2broadcast.kamalnrf.broacast.model.BroadCastLab;
import a9m2broadcast.kamalnrf.broacast.model.BroadCastUser;
import a9m2broadcast.kamalnrf.broacast.model.Brodcast;
import a9m2broadcast.kamalnrf.broacast.model.BrodcastUserGroup;

/**
 * Created by kamalnrf on 4/7/16.
 */
public class ComandExe
{
    private static Brodcast mBrodcast;
    private static BrodcastUserGroup mBrodcastUserGroup;
    private static BroadCastUser mBroadCastUser;
    private String who = "/who :- This will show all  members in the broadcast. \n";

    private String add = "/add <name> <country code> <phone number> :- This will add the contact into the broadcast. \n";

    private String feedBack = "/feedback <message>:- You can send feedback to our team \n";

    private String delete =  "/delete :- This will delete the current broadcast. \n";

    private String clear = "/clear :- This will be used to remove all messages on the screen. \n";

    private String pwd = "/pwb :- This will show the present working broadcast. \n";

    private String shrug = "/shrug :- appends  ¯\\_(ツ)_/¯ to the message. \n";

    private String help = "/help :- This will list all the commands and their functionality. \n";


    public ComandExe(Brodcast brodcast, BrodcastUserGroup brodcastUserGroup, BroadCastUser broadCastUser)
    {
        mBrodcast = brodcast;
        mBrodcastUserGroup = brodcastUserGroup;
        mBroadCastUser = broadCastUser;
    }

    public static boolean isCommand (String message)
    {
        char[] messageChar = message.toCharArray();
        char temp = messageChar[0];

        if (temp == '/')
            return true;
        else
            return false;
    }

    public String[] splitMessage (String message)
    {
        String[] name = message.split(" ");

        return name;
    }


    public String comand (String message, Context context)
    {
        String[] comands = splitMessage(message);

        if (comands[0].equalsIgnoreCase("/delete"))
            return delete(context);

        else if (comands[0].equalsIgnoreCase("/clear"))
            return clear();

        else if (comands[0].equalsIgnoreCase("/pwb"))
            return pwb();

        else if (comands[0].equalsIgnoreCase("/who"))
            return participants();

        else if (comands[0].equalsIgnoreCase("/who_num"))
            return who_num();

        else if (comands[0].equalsIgnoreCase("/shrug"))
        {
            StringBuilder message1 = new StringBuilder();
            for (int counter = 1; counter < comands.length; counter++)
            {
                message1.append(comands[counter] + " ");
            }

            String message2 = message1.toString();

            return shrug(message2);
        }

        else if (comands[0].equalsIgnoreCase("/help"))
        {
            try {
                if (!comands[1].equals(null))
                    return command(comands[1]);
            }
            catch (Exception e) {

                command(comands[0]);
            }

            return command(comands[0]);
        }

        else if (comands[0].equalsIgnoreCase("/add"))
        {
            char[] country_code = comands[2].toCharArray();
            if (country_code.length >= 8)
                return "Looks like you gave a wrong country code";
            else
                return add(comands[1], comands[2], comands[3], context);
        }

        else if (comands[0].equalsIgnoreCase("/remove"))
            return remove(comands[1], context);

        return null;
    }

    //To-Do
    public void history (String message)
    {

    }

    public String participants ()
    {
        List<String> name = mBroadCastUser.getmFirstName();

        name.remove(0);

        String resultName = Joiner.on(", ").useForNull("Emptly list").join(name);

        return resultName;
    }

    public String who_num ()
    {
        List<String> phone = mBroadCastUser.getmPhone();

        phone.remove(0);

        String resultName = Joiner.on(", ").useForNull("Empty number").join(phone);

        return resultName;
    }

    //To-Do
    public String add (String name, String countryCode, String phoneNumber, Context context)
    {
        mBroadCastUser.setmFirstName(name);
        mBroadCastUser.setmPhone(countryCode + phoneNumber);

        BroadCastLab.get(context)
                .addUser(mBrodcast, mBrodcastUserGroup, mBroadCastUser);

        return "Added new contact name: " + name +" PhoneNo:- " +countryCode + phoneNumber ;
    }

    //Separating message and command
    public String feedBack (String message)
    {
        return "+91 9515127528";
    }

    public String delete (Context context)
    {
        BroadCastLab.get(context).DeleteBroadCast(mBrodcast.getmUuid());

        return "Deleted";
    }

    //Separating message and command
    public String remove (String name, Context context)
    {
        List<String> nameCon = mBroadCastUser.getmFirstName();
        for (int counter = 1; counter < mBroadCastUser.getmPhone().size(); counter++)
        {
            if (nameCon.get(counter).equalsIgnoreCase(name))
            {
                BroadCastLab.get(context)
                        .deleteUser(mBrodcast, mBrodcastUserGroup, mBroadCastUser, name);

                mBroadCastUser.removeContact(counter);

                return "Removed contact name: " + name ;
            }

        }
        return "There is no contact with name: " + name + " in your broadcast";
    }

    public String clear ()
    {
        return "Clearing";
    }

    public String pwb ()
    {
        return mBrodcast.getmTitle();
    }

    public void everything()
    {

    }

    public String shrug (String message)
    {
        return message + " ¯\\_(ツ)_/¯";
    }

    public String command (String message)
    {
        if (message.equals("/help"))
        return who + "\n" + add + "\n" + feedBack + "\n" + delete + "\n" +
             clear + "\n" + pwd + "\n" + shrug + "\n" + help + "\n" +
            "/help <name> :- This will show specific comand functionality.";

        else if (message.equals("who"))
        return who;

        else if (message.equals("add"))
            return add;

        else if (message.equals("feedback"))
            return feedBack;

        else if (message.equals("pwb"))
            return pwd;

        else if (message.equals("delete"))
            return delete;

        else if (message.equals("clear"))
            return clear;

        else if (message.equals("pwd"))
            return pwd;

        else if (message.equals("shrug"))
            return shrug;

        else if (message.equals("help"))
            return help;

        return "Wrong input";
    }
}
