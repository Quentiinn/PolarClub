package com.example.quentincourvoisier.polarclub.eventListener;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.common.model.Participant;
import com.example.quentincourvoisier.polarclub.adapters.ParticipantsAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by antho on 13/02/2018.
 */

public class ParticipantEventListener implements ChildEventListener {

    private  static final String TAG = "PARTICIPANT_CHILD_EVENT_LISTENER";

    private ParticipantsAdapter participantsAdapter;
    private RecyclerView recyclerView;

    public ParticipantEventListener(ParticipantsAdapter participantsAdapter, RecyclerView recyclerView) {
        this.participantsAdapter = participantsAdapter;
        this.recyclerView = recyclerView;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d(TAG, "onChildAdded");

        Participant participant = dataSnapshot.getValue(Participant.class);
        participantsAdapter.addParticipant(participant);
        recyclerView.scrollToPosition(participantsAdapter.getItemCount() - 1);
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
