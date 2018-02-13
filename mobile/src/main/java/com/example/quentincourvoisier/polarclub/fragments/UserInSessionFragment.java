package com.example.quentincourvoisier.polarclub.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.model.Participant;
import com.example.common.model.Session;
import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.activities.MainActivity;
import com.example.quentincourvoisier.polarclub.adapters.ParticipantsAdapter;
import com.example.quentincourvoisier.polarclub.eventListener.ParticipantEventListener;
import com.example.quentincourvoisier.polarclub.helper.HelperDate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.common.Constants.DB_PARTICIPANTS;
import static com.example.quentincourvoisier.polarclub.adapters.SessionsAdapter.ARG_SESSION;

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
    private RecyclerView rv;
    private ParticipantsAdapter pa;

    private Session session;
    private List<Participant> participants;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

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
    public static UserInSessionFragment newInstance(String session) {
        UserInSessionFragment fragment = new UserInSessionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SESSION, session);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        participants = new ArrayList<>();

        if (getArguments() != null) {
            session = (Session) getArguments().getSerializable(ARG_SESSION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user_in_session, container, false);

        rv = root.findViewById(R.id.users_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(manager);
        pa = new ParticipantsAdapter((MainActivity) getActivity(), participants);
        rv.setAdapter(pa);

        textViewUid = root.findViewById(R.id.userInSessionFrag_uid);
        textViewProf = root.findViewById(R.id.userInSessionFrag_prof);
        textViewHeure = root.findViewById(R.id.userInSessionFrag_heure);

        textViewUid.setText(session.getUid());
        textViewProf.setText(session.getProf());
        textViewHeure.setText(HelperDate.timestampToDateString(session.getDebut()));

        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        attachChildListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        detachChildListener();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @SuppressLint("LongLogTag")
    private void attachChildListener() {
        if (childEventListener == null) {
            childEventListener = new ParticipantEventListener(pa, rv);
        }
        databaseReference.child(DB_PARTICIPANTS).orderByChild("uidSession").equalTo(session.getUid()).addChildEventListener(childEventListener);
    }

    private void detachChildListener() {
        if (childEventListener != null) {
            databaseReference.child(DB_PARTICIPANTS).removeEventListener(childEventListener);
            childEventListener = null;
        }
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
}
