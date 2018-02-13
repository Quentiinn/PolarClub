package com.example.quentincourvoisier.polarclub.adapters;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.activities.MainActivity;
import com.example.quentincourvoisier.polarclub.fragments.UserInSessionFragment;
import com.example.quentincourvoisier.polarclub.helper.HelperDate;
import com.example.common.model.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.common.Constants.DB_SESSIONS;

/**
 * Created by antho on 07/02/2018.
 */

public class SessionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String ARG_SESSION_UID = "session_uid";

    private MainActivity context;
    private View view;

    private List<Session> sessions;

    public SessionsAdapter(MainActivity context, List<Session> sessions) {
        this.context = context;
        this.sessions = sessions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_session, parent, false);
        return new SessionViewHolder(view);
    }

    @SuppressLint("CommitTransaction")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Session session = sessions.get(position);
        ((SessionViewHolder)holder).bind(session);

        view.setOnClickListener((arg0 -> {
            Bundle bundle = new Bundle();
            bundle.putString(ARG_SESSION_UID, session.getUid() );
            UserInSessionFragment uif = UserInSessionFragment.newInstance(ARG_SESSION_UID);
            uif.setArguments(bundle);

            FragmentTransaction fragmentTransaction = context.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, uif, "FragmentName").commit();
        }));
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public void addSession(Session session) {
        sessions.add(session);
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
            itemSessionUid.setText(Html.fromHtml("<b>" + session.getUid() + "</b>"));
            itemSessionUDateDebut.setText(HelperDate.timestampToDateString(session.getDebut()));
        }
    }
}
