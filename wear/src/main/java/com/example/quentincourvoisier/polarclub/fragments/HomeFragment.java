package com.example.quentincourvoisier.polarclub.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.common.model.Participant;
import com.example.common.model.Session;
import com.example.quentincourvoisier.polarclub.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.common.Constants.DB_PARTICIPANTS;
import static com.example.common.Constants.DB_SESSIONS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_SESSION = "arg_session";
    public static final String ARG_ID_PARTICIPANT = "arg_id_participant";

    private static final String BTN_REJOINDRE = "btn_rejoindre";

    private OnFragmentInteractionListener mListener;

    private Button rejoindreButton;

    private EditText session;
    private EditText pseudo;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rejoindreButton = (Button) rootView.findViewById(R.id.rejoindreButton);
        rejoindreButton.setOnClickListener(this);

        session = (EditText) rootView.findViewById(R.id.session);
        pseudo = (EditText) rootView.findViewById(R.id.pseudo);


        setTag();
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch ((String) v.getTag()) {
            case BTN_REJOINDRE:
                final String nomSession = session.getText().toString();
                final String pseudoSession = pseudo.getText().toString();

                database.getReference(DB_SESSIONS).child(nomSession).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Participant participant = new Participant(pseudoSession, 80);
                            participant.setUidSession(nomSession);
                            String uid = database.getReference(DB_PARTICIPANTS).push().getKey();
                            participant.setUid(uid);

                            database.getReference(DB_PARTICIPANTS).child(uid).setValue(participant);

                            HeartRateFragment hr = HeartRateFragment.newInstance(ARG_SESSION);
                            Bundle bundle = new Bundle();
                            bundle.putString(ARG_ID_PARTICIPANT, uid);
                            bundle.putSerializable(ARG_SESSION, dataSnapshot.getValue(Session.class));
                            hr.setArguments(bundle);
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_frame, hr).commit();
                        } else {
                            Toast.makeText(getActivity(), "La session n'éxiste pas ou est terminé", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;

        }
    }

    public void setTag() {
        rejoindreButton.setTag(BTN_REJOINDRE);
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
