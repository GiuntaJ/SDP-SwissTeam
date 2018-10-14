package ch.epfl.swissteam.services;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CapabilitiesAdapter extends RecyclerView.Adapter<CapabilitiesAdapter.CapabilitiesViewHolder> {
    private List<String> capabilities_;


    public static class CapabilitiesViewHolder extends RecyclerView.ViewHolder {

        public TextView mCapabilityName;
        public CapabilitiesViewHolder(View v) {
            super(v);
            mCapabilityName = (TextView) v.findViewById(R.id.textview_capabilitiesadapter_capabilityname);
        }
    }

    public CapabilitiesAdapter(List<String> capabilities) {
        capabilities_ = capabilities;

    }


    @Override
    public CapabilitiesViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.capabilities_display_adapter, parent, false);


        CapabilitiesViewHolder vh = new CapabilitiesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CapabilitiesViewHolder mViewHolder, int position) {
        mViewHolder.mCapabilityName.setText(capabilities_.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return capabilities_ == null ? 0 : capabilities_.size();
    }

}
