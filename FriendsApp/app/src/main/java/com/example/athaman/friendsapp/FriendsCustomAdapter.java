package com.example.athaman.friendsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by keone on 7/23/2016.
 */
public class FriendsCustomAdapter extends ArrayAdapter<Friend> {
    private LayoutInflater mLayoutInflater;
    private static FragmentManager sFragmentManager;

    public FriendsCustomAdapter(Context context, FragmentManager fragmentManager){
        super(context, android.R.layout.simple_list_item_2);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sFragmentManager = fragmentManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        if(convertView == null){
            view = mLayoutInflater.inflate(R.layout.custom_friend, parent, false);
        }else{
            view = convertView;
        }

        final Friend friend = getItem(position);
        final int _id = friend.getId();
        final String name = friend.getName();
        final String phone = friend.getPhone();
        final String email = friend.getEmail();

        ((TextView) view.findViewById(R.id.friend_name)).setText(name);
        ((TextView) view.findViewById(R.id.friend_phone)).setText(phone);
        ((TextView) view.findViewById(R.id.friend_email)).setText(email);

        Button editButton = (Button) view.findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra(FriendsContract.FriendsColumns.FRIENDS_ID, String.valueOf(_id));
                intent.putExtra(FriendsContract.FriendsColumns.FRIENDS_NAME, name);
                intent.putExtra(FriendsContract.FriendsColumns.FRIENDS_PHONE, phone);
                intent.putExtra(FriendsContract.FriendsColumns.FRIENDS_EMAIL, email);
                getContext().startActivity(intent);
            }
        });

        Button deleteButton = (Button) view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendsDialogue dialogue = new FriendsDialogue();
                Bundle args = new Bundle();
                args.putString(FriendsDialogue.DIALOGUE_TYPE, FriendsDialogue.DELETE_RECORD);
                args.putInt(FriendsContract.FriendsColumns.FRIENDS_ID, _id);
                args.putString(FriendsContract.FriendsColumns.FRIENDS_NAME, name);
                dialogue.setArguments(args);
                dialogue.show(sFragmentManager, "delete-record");
            }
        });

        return view;
    }

    public void setData(List<Friend> friends){
        clear();
        if(friends != null){
            for(Friend friend : friends){
                add(friend);
            }
        }
    }
}
