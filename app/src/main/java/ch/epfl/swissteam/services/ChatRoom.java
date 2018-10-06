package ch.epfl.swissteam.services;

import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This activity is a chat room, it display messages and allow to write and send messages
 *
 * @author Sébastien Gachoud
 */
public class ChatRoom extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        dataBase_ = FirebaseDatabase.getInstance().getReference();

        displayMessages();
    }

    /**
     * display messages retrieved form the database
     */
    private void displayMessages(){
        RecyclerView chatRoom = findViewById(R.id.recycler_view_message);
        chatRoom.setHasFixedSize(true);
        chatRoom.setLayoutManager(new LinearLayoutManager(this));

        adapter_ = new FirebaseRecyclerAdapter<ChatMessage, MessageHolder>
                (ChatMessage.class, R.layout.chat_message_layout, MessageHolder.class, dataBase_.child("chatTest"))
        {
            @Override
            protected void populateViewHolder(MessageHolder viewHolder, ChatMessage message, int position){
                viewHolder.messageText_.setText(message.getText_());
                viewHolder.timeUserText_.setText(DateFormat.format("dd-mm-yyyy (hh:mm:ss)", message.getTime_()) +
                        message.getUser_());
            }
        };
        chatRoom.setAdapter(adapter_);
    }

    /**
     * Send message to the database
     * @param view
     */
    public void sendMessage(View view){
        TextInputEditText textInput = findViewById(R.id.message_input);
        String message = textInput.getText().toString();
        //TODO set user
        ChatMessage chatMessage = new ChatMessage(message, "pablo", "pabinou");
        dataBase_.child("chatTest").push().setValue(chatMessage);

        textInput.getText().clear();
    }

    FirebaseRecyclerAdapter<ChatMessage, MessageHolder> adapter_;
    DatabaseReference dataBase_;

    /**
     * ViewHolder class to handle the RecyclerView
     */
    public static class MessageHolder extends RecyclerView.ViewHolder{
        TextView messageText_;
        TextView timeUserText_;

        public MessageHolder(View view) {
            super(view);
            messageText_ = view.findViewById(R.id.message_message);
            timeUserText_ = view.findViewById(R.id.message_time_stamp_user);
        }

    }
}
