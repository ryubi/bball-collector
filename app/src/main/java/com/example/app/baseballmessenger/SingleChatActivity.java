package com.example.app.baseballmessenger;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pr4h6n on 2/25/18.
 */

public class SingleChatActivity extends AppCompatActivity{
    LinearLayout layout;
    RelativeLayout layout_two;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private User currentUser;
    private User chattingWith;
    private String chat;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_two = (RelativeLayout) findViewById(R.id.layout2);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        currentUser = Handoff.currentUser;
        chattingWith = getIntent().getParcelableExtra("chattingWith");

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("messages");

        //Determine correct order of UIDs
        if(currentUser.uuid.compareTo(chattingWith.uuid) > 0)
        {
            chat = currentUser.uuid + "_" + chattingWith.uuid;
            reference = reference.child(chat);
        }
        else
        {
            chat = chattingWith.uuid + "_" + currentUser.uuid;
            reference = reference.child(chat);
        }

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                String messageText = messageArea.getText().toString();
                String uuid = reference.push().getKey();
                Message newMessage = new Message(uuid, chat, currentUser.uuid, chattingWith.uuid, messageText);
                newMessage.updateFirebase();
            }
        });

        //Update chat conversation to reflect new messages
        Query query = reference.orderByChild("uuid");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);

                if(message.sender.equals(currentUser.uuid))
                {
                    addMessageBox(message.text, 1);
                }
                else
                {
                    addMessageBox(message.text, 2);
                }
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
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
    }

    //Adds message to the chat conversation
    public void addMessageBox(String message, int type)
    {
        TextView textView = new TextView(SingleChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1)
        {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else
        {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }

        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
