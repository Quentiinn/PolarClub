package com.example.quentincourvoisier.polarclub.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.helper.HelperDate;
import com.example.quentincourvoisier.polarclub.model.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by antho on 07/02/2018.
 */

public class SessionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Session> sessions;

    public SessionsAdapter() {
        sessions = new ArrayList<>();
    }

    public void testListSession() {
        sessions.add(new Session("11111", 123, 100));
        sessions.add(new Session("11112", 124, 150));
        sessions.add(new Session("11112", 125, 160));
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
        private TextView itemSessionUDateDebut;

        SessionViewHolder(View itemView) {
            super(itemView);
            itemSessionUid = itemView.findViewById(R.id.itemSession_uid);
            itemSessionUDateDebut = itemView.findViewById(R.id.itemSession_dateDebut);
        }

        void bind(Session session) {
            itemSessionUid.setText(session.getUid());
            itemSessionUDateDebut.setText(HelperDate.timestampToDateString(session.getDate()));
        }
    }
}
