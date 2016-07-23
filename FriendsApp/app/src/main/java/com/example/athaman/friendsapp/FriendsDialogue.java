package com.example.athaman.friendsapp;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by keone on 7/23/2016.
 */
public class FriendsDialogue extends DialogFragment {
    private static final String LOG_TAG = FriendsDialogue.class.getSimpleName();
    private LayoutInflater mLayoutInflater;
    public static final String DIALOGUE_TYPE = "command";
    public static final String DELETE_RECORD = "deleteRecord";
    public static final String DELETE_DATABASE = "deleteDatabase";
    public static final String CONFIRM_EXIT = "confirmExit";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mLayoutInflater = getActivity().getLayoutInflater();
        final View view = mLayoutInflater.inflate(R.layout.friends_dialogue, null);
        String command = getArguments().getString(DIALOGUE_TYPE);
        if(command.equals(DELETE_RECORD)){
            final int _id = getArguments().getInt(FriendsContract.FriendsColumns.FRIENDS_ID);
            String name = getArguments().getString(FriendsContract.FriendsColumns.FRIENDS_NAME);
            TextView popupMessage =(TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to delete " + name + " from your friends list?");
            builder.setView(view).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Uri uri = FriendsContract.Friends.buildFriendUri(String.valueOf(_id));
                    contentResolver.delete(uri, null, null);
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

        }else if(command.equals(DELETE_DATABASE)){
            TextView popupMessage =(TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to delete the entire database?");
            builder.setView(view).setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Uri uri = FriendsContract.URI_TABLE;
                    contentResolver.delete(uri, null, null);
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            })
            .setNegativeButton("Changed My Mind", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        }else if(command.equals(CONFIRM_EXIT)){
            TextView popupMessage =(TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to exit without saving?");
            builder.setView(view).setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getActivity().finish();
                }
            })
                    .setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

        }else{
            Log.d(LOG_TAG, "Invalid command passed as parameter to FriendsDialogue");
        }
        return builder.create();
    }
}
