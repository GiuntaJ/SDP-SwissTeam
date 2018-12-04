package ch.epfl.swissteam.services;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that display for the user a list of the post he is involved in.
 *
 * @author Ghali Chraïbi
 */
public class TodoListFragment extends Fragment {

    private RecyclerView.Adapter adapter_;
    private List<Post> posts_ = new ArrayList<>();

    public TodoListFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new {@link TodoListFragment}.
     *
     * @return new instance of <code>TodoListFragment</code>
     */
    public static TodoListFragment newInstance() {
        return new TodoListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.fragment_todo_list, container, false);

        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_TodoList);


        // Setup recyclerview for posts to do
        RecyclerView recyclerView = frag.findViewById(R.id.recyclerview_todofragment);

        if (recyclerView != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(layoutManager);

            adapter_ = new MyPostAdapter(posts_);
            recyclerView.setAdapter(adapter_);
        }

        return frag;
    }
}
