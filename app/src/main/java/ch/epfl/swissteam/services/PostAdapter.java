package ch.epfl.swissteam.services;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public static final String POST_TAG = "ch.epfl.swissteam.services.post";

    private List<Post> posts_;

    static class PostViewHolder extends RecyclerView.ViewHolder{
        protected TextView postView_;
        protected ImageButton editButton_;
        protected ImageButton deleteButton_;

        protected PostViewHolder(View v){
            super(v);
            postView_ = v.findViewById(R.id.textview_postadapter_post);
            editButton_ = v.findViewById(R.id.button_postadapter_edit);
            deleteButton_= v.findViewById(R.id.button_postadapter_delete);
        }
    }

    public PostAdapter(List<Post> posts){
        this.posts_ = posts;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_layout, viewGroup, false);

        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int i) {
        holder.postView_.setText(posts_.get(holder.getAdapterPosition()).getTitle_()
                + "\n" + posts_.get(holder.getAdapterPosition()).getBody_());
        holder.editButton_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "EDIT CLICKED", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(holder.itemView.getContext(), MyPostEdit.class);
                intent.putExtra(POST_TAG, posts_.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.deleteButton_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "DELETE CLICKED", Toast.LENGTH_SHORT).show();
                DBUtility.get().deletePost(posts_.get(holder.getAdapterPosition()).getKey_());
                posts_.remove(holder.getAdapterPosition());
                ((RecyclerView)v.getParent().getParent().getParent().getParent()).getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts_.size();
    }
}
