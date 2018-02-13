package com.example.quentincourvoisier.polarclub.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.common.model.Session;
import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.activities.MainActivity;
import com.example.quentincourvoisier.polarclub.adapters.SessionsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.common.Constants.DB_SESSIONS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListSessionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListSessionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListSessionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View root;
    private RecyclerView rv;

    private List<Session> sessions;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public ListSessionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListSessionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListSessionFragment newInstance(String param1, String param2) {
        ListSessionFragment fragment = new ListSessionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        sessions = new ArrayList<>();


        root = inflater.inflate(R.layout.fragment_list_session, container, false);

        rv = root.findViewById(R.id.sessions_recycler_view);
        recupSessions();

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

    private void recupSessions() {
        database.getReference(DB_SESSIONS).orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Session session = postSnapshot.getValue(Session.class);
                        sessions.add(session);
                    }

                    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(manager);
                    SessionsAdapter sa = new SessionsAdapter((MainActivity)getActivity(), sessions);
                    rv.setAdapter(sa);
                } else {
                    Toast.makeText(getActivity(), "Pas de résultat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Pas de résultat", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
