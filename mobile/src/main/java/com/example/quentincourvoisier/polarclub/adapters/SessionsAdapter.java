package com.example.quentincourvoisier.polarclub.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    private class SessionViewHolder extends RecyclerView.ViewHolder {

        public SessionViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Session session) {

        }
    }
}
