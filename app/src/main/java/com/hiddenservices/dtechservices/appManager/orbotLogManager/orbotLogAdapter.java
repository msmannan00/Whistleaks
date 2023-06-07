package com.hiddenservices.dtechservices.appManager.orbotLogManager;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hiddenservices.dtechservices.appManager.activityContextManager;
import com.hiddenservices.dtechservices.appManager.tabManager.tabEnums;
import com.hiddenservices.dtechservices.constants.constants;
import com.hiddenservices.dtechservices.eventObserver;
import com.hiddenservices.dtechservices.R;

import org.torproject.android.service.wrapper.logRowModel;

import java.util.ArrayList;
import java.util.List;

public class orbotLogAdapter extends RecyclerView.Adapter<orbotLogAdapter.listViewHolder> {
    /*Private Variables*/

    private ArrayList<logRowModel> mModelList;
    private eventObserver.eventListener mEvent;

    orbotLogAdapter(ArrayList<logRowModel> pModelList, eventObserver.eventListener pEvent) {
        this.mModelList = pModelList;
        this.mEvent = pEvent;
    }

    @NonNull
    @Override
    public orbotLogAdapter.listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orbot_row_view, parent, false);
        return new orbotLogAdapter.listViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orbotLogAdapter.listViewHolder holder, int position) {
        holder.bindListView(mModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }


    /*View Holder Extensions*/
    class listViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mOrbotRowHeader;
        TextView mOrbotRowDescription;
        ConstraintLayout mOrbotRowContainer;

        listViewHolder(View itemView) {
            super(itemView);
        }

        void bindListView(logRowModel model) {
            mOrbotRowHeader = itemView.findViewById(R.id.pOrbotRowHeader);
            mOrbotRowDescription = itemView.findViewById(R.id.pOrbotRowDescription);
            mOrbotRowContainer = itemView.findViewById(R.id.pOrbotRowContainer);

            String mLog = (this.getLayoutPosition()+1 + ". " + model.getLog());
            mOrbotRowHeader.setText(mLog);
            mOrbotRowDescription.setText(model.getDate());
            mOrbotRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Uri.parse(constants.CONST_LOG_DUCKDUCK + Uri.encode(" " + mModelList.get(this.getLayoutPosition()).getLog())).toString()));
            activityContextManager.getInstance().getHomeController().startActivity(intent);
            mEvent.invokeObserver(null, orbotLogEnums.eOrbotLogAdapterCommands.M_CLOSE);
        }
    }

    public Object onTrigger(tabEnums.eTabAdapterCommands pCommands, List<Object> pData) {
        return null;
    }
}
