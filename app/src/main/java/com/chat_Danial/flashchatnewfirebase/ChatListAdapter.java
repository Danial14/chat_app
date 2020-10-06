package com.chat_Danial.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by club on 4/5/2018.
 */

public class ChatListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotList;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mSnapshotList.add(dataSnapshot);
            Log.d("ChildAdded", "Yes!");
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ChatListAdapter(Activity activity, DatabaseReference ref, String name){

        mActivity = activity;
        mDisplayName = name;
        mDatabaseReference = ref.child("messages");
        mDatabaseReference.addChildEventListener(mListener);
        mSnapshotList = new ArrayList<DataSnapshot>();

    }
    static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }
    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_msg_row, parent,false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.authorName = (TextView) convertView.findViewById(R.id.author);
            viewHolder.body = (TextView) convertView.findViewById(R.id.message);
            viewHolder.params = (LinearLayout.LayoutParams) viewHolder.authorName.getLayoutParams();
            convertView.setTag(viewHolder);
        }
        final InstantMessage instantMessage = getItem(position);
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        String author = instantMessage.getAuthor();
        String message = instantMessage.getMessage();

        boolean isMe = author.equals(mDisplayName);
        setChatRowAppearence(isMe, viewHolder);

        viewHolder.authorName.setText(author);
        viewHolder.body.setText(message);
        
        return convertView;
    }
    private void setChatRowAppearence(boolean isItMe, ViewHolder viewHolder){
        if(isItMe){
            viewHolder.params.gravity = Gravity.END;
            viewHolder.authorName.setTextColor(Color.GREEN);
            viewHolder.body.setBackgroundResource(R.drawable.bubble2);
        }
        else {
            viewHolder.params.gravity = Gravity.START;
            viewHolder.authorName.setTextColor(Color.BLUE);
            viewHolder.body.setBackgroundResource(R.drawable.bubble1);
        }

        viewHolder.authorName.setLayoutParams(viewHolder.params);
        viewHolder.body.setLayoutParams(viewHolder.params);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public InstantMessage getItem(int position) {
        DataSnapshot snapshot = mSnapshotList.get(position);
        return snapshot.getValue(InstantMessage.class);
    }
    public void cleanup(){
        mDatabaseReference.removeEventListener(mListener);
    }
}
