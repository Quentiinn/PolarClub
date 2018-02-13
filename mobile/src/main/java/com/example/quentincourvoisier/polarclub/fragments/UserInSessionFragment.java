package com.example.quentincourvoisier.polarclub.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.model.Session;
import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.adapters.UsersAdapter;
import com.example.quentincourvoisier.polarclub.helper.HelperDate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;

import static com.example.common.Constants.DB_SESSIONS;
import static com.example.quentincourvoisier.polarclub.adapters.SessionsAdapter.ARG_SESSION_UID;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserInSessionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserInSessionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInSessionFragment extends Fragment {

    private static final String TAG = "TAG_USER_IN_SESSION_FRAGMENT";

    private OnFragmentInteractionListener mListener;
    private View root;

    private TextView textViewUid;
    private TextView textViewProf;
    private TextView textViewHeure;

    private Session session;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    public UserInSessionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserInSessionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInSessionFragment newInstance(String sessionUid) {
        UserInSessionFragment fragment = new UserInSessionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SESSION_UID, sessionUid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        if (getArguments() != null) {
            String sessionUid = getArguments().getString(ARG_SESSION_UID, "");
            recupSession(sessionUid);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user_in_session, container, false);
        final RecyclerView rv = root.findViewById(R.id.users_recycler_view);

        textViewUid = root.findViewById(R.id.userInSessionFrag_uid);
        textViewProf = root.findViewById(R.id.userInSessionFrag_prof);
        textViewHeure = root.findViewById(R.id.userInSessionFrag_heure);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        UsersAdapter ua = new UsersAdapter();
        ua.testListUser();
        rv.setAdapter(ua);

        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void recupSession(String sessionUid) {
        reference.child(DB_SESSIONS).child(sessionUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    session = dataSnapshot.getValue(Session.class);

                    textViewUid.setText(session.getUid());
                    textViewProf.setText(session.getProf());
                    textViewHeure.setText(HelperDate.timestampToDateString(session.getDebut()));
                    getActivity().setTitle("Session : " + session.getUid());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
