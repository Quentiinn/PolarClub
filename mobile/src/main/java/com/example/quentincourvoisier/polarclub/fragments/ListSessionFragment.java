package com.example.quentincourvoisier.polarclub.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.adapters.SessionsAdapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_list_session, container, false);
        final RecyclerView rv = root.findViewById(R.id.sessions_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        SessionsAdapter sa = new SessionsAdapter();
        sa.testListSession();
        rv.setAdapter(sa);
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
}
