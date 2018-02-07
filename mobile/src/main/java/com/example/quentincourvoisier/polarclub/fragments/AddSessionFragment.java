package com.example.quentincourvoisier.polarclub.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quentincourvoisier.polarclub.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddSessionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddSessionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSessionFragment extends Fragment implements View.OnClickListener{

    private static final String BTN_CREATE_SESSION = "btn_crete_session";
    private OnFragmentInteractionListener mListener;
    private Button buttonCreateSession = null;

    public AddSessionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSessionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSessionFragment newInstance(String param1, String param2) {
        AddSessionFragment fragment = new AddSessionFragment();
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
        buttonCreateSession = getActivity().findViewById(R.id.buttonCreateSession);
        buttonCreateSession.setOnClickListener(this);
        buttonCreateSession.setTag(BTN_CREATE_SESSION);
        return inflater.inflate(R.layout.fragment_add_session, container, false);
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

    @Override
    public void onClick(View view) {
        if (view.getTag() == BTN_CREATE_SESSION) {

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
