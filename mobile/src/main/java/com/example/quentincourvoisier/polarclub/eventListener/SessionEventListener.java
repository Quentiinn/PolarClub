package com.example.quentincourvoisier.polarclub.eventListener;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.common.model.Session;
import com.example.quentincourvoisier.polarclub.adapters.SessionsAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by antho on 13/02/2018.
 */

public class SessionEventListener implements ChildEventListener {

    private static final String TAG = "SESSION_CHILD_EVENT_LISTENER";

    private SessionsAdapter sessionsAdapter;
    private RecyclerView recyclerView;

    public SessionEventListener(SessionsAdapter sessionsAdapter, RecyclerView recyclerView) {
        this.sessionsAdapter = sessionsAdapter;
        this.recyclerView = recyclerView;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d(TAG, "onChildAdded");

        Session session = dataSnapshot.getValue(Session.class);
        sessionsAdapter.addSession(session);
        recyclerView.scrollToPosition(sessionsAdapter.getItemCount() - 1);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
