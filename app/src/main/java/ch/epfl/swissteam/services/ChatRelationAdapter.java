package ch.epfl.swissteam.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for ChatRelations in {@link OnlineChatFragment}
 *
 * @author Sébastien gachoud
 */
public class ChatRelationAdapter extends RecyclerView.Adapter<ChatRelationAdapter.ChatRelationsViewHolder> {

    private List<ChatRelation> relations_;
    private String currentUserId_;

    /**
     * Adapter for a list of chatRelations
     *
     * @param relations the list of chatRelations to be managed by the adapter
     */
    public ChatRelationAdapter(List<ChatRelation> relations, String currentUserId) {
        relations_ = relations == null ? new ArrayList<>() : relations;
        currentUserId_ = currentUserId;
    }

    @NonNull
    @Override
    public ChatRelationsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatrelation_layout, viewGroup, false);

        return new ChatRelationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatRelationsViewHolder holder, int i) {
        String otherId = relations_.get(i).getOtherId(currentUserId_);
        DBUtility.get().getUser(otherId, oUser -> {
            holder.contactName_.setText(oUser.getName_());
            Picasso.get().load(oUser.getImageUrl_()).into(holder.contactImage_);
        });

        holder.parentLayout_.setOnClickListener((view) -> {
            Intent intent = new Intent(holder.itemView.getContext(), ChatRoom.class);
            intent.putExtra(ChatRelation.RELATION_ID_TEXT, relations_.get(i).getId_());
            holder.itemView.getContext().startActivity(intent);
        });

        holder.parentLayout_.setOnLongClickListener((view) -> {
            askToDeleteRelation(view.getContext(), relations_.get(i), holder.contactName_.getText().toString());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return relations_.size();
    }

    private void askToDeleteRelation(Context context, ChatRelation chatRelation, String othersName){
        Resources res = context.getResources();
        Utility.askToDeleteAlertDialog(context, chatRelation, null,
                res.getString(R.string.chat_relation_delete_alert_title) + " " + othersName,
                res.getString(R.string.chat_relation_delete_alert_text),
                (b) -> {
                    if(b){
                        relations_.remove(chatRelation);
                        notifyDataSetChanged();
                    }

                });
    }

    /**
     * ViewHolder for ChatRelations
     */
    static class ChatRelationsViewHolder extends RecyclerView.ViewHolder {
        
        protected TextView contactName_;
        protected FrameLayout parentLayout_;
        protected ImageView contactImage_;

        /**
         * Create a ChatRelationsViewHolder
         *
         * @param view the current View
         */
        protected ChatRelationsViewHolder(View view) {
            super(view);
            contactName_ = view.findViewById(R.id.chatrelation_adapter_contact_name_view);
            contactImage_ = view.findViewById(R.id.chatrelation_layout_image);
            parentLayout_ = view.findViewById(R.id.framelayout_chatrelation);
        }
    }

}