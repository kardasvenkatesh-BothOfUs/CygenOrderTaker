package com.cygen.cygendineinpos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_CALL = 1, TYPE_SMS = 2;
    private List<Object> callSMSFeed = new ArrayList();
    // Context is not used here but may be required to
    // perform complex operations or call methods from outside
    private Context context;

    // Constructor
    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setCallSMSFeed(List<Object> callSMSFeed) {
        this.callSMSFeed = callSMSFeed;
    }

    // We need to override this as we need to differentiate
    // which type viewHolder to be attached
    // This is being called from onBindViewHolder() method
    @Override
    public int getItemViewType(int position) {
        if (callSMSFeed.get(position) instanceof Categories) {
            return TYPE_CALL;
        } else if (callSMSFeed.get(position) instanceof Categories) {
            return TYPE_SMS;
        }
        return -1;
    }

    // Invoked by layout manager to replace the contents of the views
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case TYPE_CALL:
                Categories call = (Categories) callSMSFeed.get(position);
                ((SMSViewHolder) holder).showSmsDetails(call);
                break;
            case TYPE_SMS:
                Categories sms = (Categories) callSMSFeed.get(position);
                ((SMSViewHolder) holder).showSmsDetails(sms);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return callSMSFeed.size();
    }

    // Invoked by layout manager to create new views
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Attach layout for single cell
        int layout = 0;
        RecyclerView.ViewHolder viewHolder;
        // Identify viewType returned by getItemViewType(...)
        // and return ViewHolder Accordingly
        switch (viewType) {
            case TYPE_CALL:
                layout = R.layout.categories_item;
                View callsView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder = new SMSViewHolder(callsView);
                break;

            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    // First ViewHolder of object type Call
    // Reference to the views for each call items to display desired information

    // Second ViewHolder of object type SMS
    // Reference to the views for each call items to display desired information
    public class SMSViewHolder extends RecyclerView.ViewHolder {

        private TextView itemNameTv;
        public SMSViewHolder(View itemView) {
            super(itemView);
            // Initiate view
            itemNameTv = (TextView) itemView.findViewById(R.id.productNameTv);

        }

        public void showSmsDetails(Categories sms) {
            // Attach values for each item
            String itemName = sms.getItemName();


            itemNameTv.setText(itemName);
        }
    }
}
