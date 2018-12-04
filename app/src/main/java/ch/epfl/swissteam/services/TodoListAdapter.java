package ch.epfl.swissteam.services;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {
    
    private List<Post> todoPosts_;

    /**
     * Adapter for a list of Posts
     *
     * @param posts the list of Posts to be managed by the adapter
     */
    public TodoListAdapter(List<Post> posts) {
        this.todoPosts_ = posts;
    }

    @NonNull
    @Override
    public TodoListAdapter.TodoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todolist_layout, viewGroup, false);
        return new TodoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int i) {
        holder.titleView_.setText(todoPosts_.get(holder.getAdapterPosition()).getTitle_());
        holder.bodyView_.setText(todoPosts_.get(holder.getAdapterPosition()).getBody_());

/*
        holder.deleteButton_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtility.get().deletePost(posts_.get(holder.getAdapterPosition()).getKey_());
                posts_.remove(holder.getAdapterPosition());
                ((RecyclerView) v.getParent().getParent().getParent().getParent()).getAdapter().notifyDataSetChanged();
            }
        });
*/
    }


    @Override
    public int getItemCount() {
        return todoPosts_.size();
    }

    /**
     * ViewHolder for list of things to do
     */
    static class TodoViewHolder extends RecyclerView.ViewHolder {

        protected TextView titleView_;
        protected TextView bodyView_;

        /**
         * Create a TodoViewHolder
         *
         * @param v the current View
         */
        protected TodoViewHolder(View v) {
            super(v);
            // TODO : Add a checkbutton
            titleView_ = v.findViewById(R.id.textview_todolistadapter_title);
            bodyView_ = v.findViewById(R.id.textview_todolistadapter_body);

        }
    }
}
