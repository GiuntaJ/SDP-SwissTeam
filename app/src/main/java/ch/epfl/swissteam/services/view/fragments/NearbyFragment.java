package ch.epfl.swissteam.services.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.epfl.swissteam.services.view.builders.PostAdapter;
import ch.epfl.swissteam.services.R;
import ch.epfl.swissteam.services.utils.SettingsDbHelper;
import ch.epfl.swissteam.services.models.Post;
import ch.epfl.swissteam.services.providers.DBCallBack;
import ch.epfl.swissteam.services.providers.DBUtility;
import ch.epfl.swissteam.services.providers.GoogleSignInSingleton;
import ch.epfl.swissteam.services.providers.LocationManager;
import ch.epfl.swissteam.services.view.activities.MainActivity;
import ch.epfl.swissteam.services.view.activities.PostsMapActivity;

/**
 * A fragment to display spontaneous posts on the main page, also known as Nearby page for the user
 *
 * @author Julie Giunta
 */
public class NearbyFragment extends Fragment implements View.OnClickListener {

    private SwipeRefreshLayout swipeRefreshLayout_;
    private RecyclerView.Adapter adapter_;
    private List<Post> posts_ = new ArrayList<>();

    public NearbyFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new <code>NearbyFragment</code>.
     *
     * @return new instance of <code>NearbyFragment</code>
     */
    public static NearbyFragment newInstance() {
        return new NearbyFragment();
    }

    private String currentUserId_;
    private SettingsDbHelper helper_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUserId_ = GoogleSignInSingleton.get().getClientUniqueID();
        helper_ = new SettingsDbHelper(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.fragment_home, container, false);

        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_nearby);

        swipeRefreshLayout_ = frag.findViewById(R.id.swiperefresh_homefragment_refresh);
        swipeRefreshLayout_.setOnRefreshListener(this::refresh);
        swipeRefreshLayout_.setColorSchemeResources(R.color.colorAccent);

        //setup recyclerview for posts
        RecyclerView mRecyclerView_ = frag.findViewById(R.id.recyclerview_homefragment_posts);

        if (mRecyclerView_ != null) {
            mRecyclerView_.setLayoutManager(new LinearLayoutManager(this.getContext()));
            adapter_ = new PostAdapter(posts_);
            mRecyclerView_.setAdapter(adapter_);
        }
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        refresh();
        return frag;
    }

    @Override
    public void onClick(View v) {
        refresh();
    }

    /**
     * Refresh the feed of post shown on the main board
     */
    private void refresh(){
        LocationManager.get().getCurrentLocationAsync(getActivity(), userLocation -> {
            if(userLocation != null) {
                DBUtility.get().getPostsFeed(new DBCallBack<ArrayList<Post>>() {
                    @Override
                    public void onCallBack(ArrayList<Post> value) {
                        posts_.clear();
                        Date today = Calendar.getInstance().getTime();
                        for (Post p : value) {
                            if(!p.deleteIfTooOld(today)){
                                posts_.add(p);
                            }
                        }
                        adapter_.notifyDataSetChanged();
                        swipeRefreshLayout_.setRefreshing(false);
                    }
                }, userLocation, helper_);
            }
            else{
                DBUtility.get().getPostsFeed(new DBCallBack<ArrayList<Post>>() {
                    @Override
                    public void onCallBack(ArrayList<Post> value) {
                        posts_.clear();
                        Date today = Calendar.getInstance().getTime();
                        for (Post p : value) {
                            if(!p.deleteIfTooOld(today)){
                                Log.i("POST", "post added");
                                posts_.add(p);
                            }
                        }
                        adapter_.notifyDataSetChanged();
                        swipeRefreshLayout_.setRefreshing(false);
                    }
                }, LocationManager.get().getZeroLocation(), helper_);
            }
            ((MainActivity) getActivity()).showHomeFragment();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        menu.setGroupEnabled(R.id.group_refresh, true);
        menu.setGroupVisible(R.id.group_refresh, true);
        menu.setGroupEnabled(R.id.group_switchtomap, true);
        menu.setGroupVisible(R.id.group_switchtomap, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_refresh){
            refresh();
            return true;
        }
        else if(id == R.id.action_switchtomap) {
            startActivity(new Intent(getContext(), PostsMapActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}