package com.example.quentincourvoisier.polarclub.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.model.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antho on 07/02/2018.
 */

public class SessionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Session> sessions;

    public SessionsAdapter() {
        sessions = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_session, parent, false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Session session = sessions.get(position);
        ((SessionViewHolder)holder).bind(session);
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    private class SessionViewHolder extends RecyclerView.ViewHolder {

        private TextView itemSessionUid;

        SessionViewHolder(View itemView) {
            super(itemView);
            itemSessionUid = itemView.findViewById(R.id.itemSession_uid);
        }

        void bind(Session session) {
            itemSessionUid.setText(session.getUid());
        }
    }
}
