package com.example.quentincourvoisier.polarclub.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.common.model.Session;
import com.example.common.task.TimerSessionTask;
import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.services.WearHearbeatEmulatorService;
import com.example.quentincourvoisier.polarclub.utils.DailyHeartBeat;
import com.example.quentincourvoisier.polarclub.utils.HeartBeatView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;

import static com.example.common.Constants.DB_PARTICIPANTS;
import static com.example.common.Constants.HEART_COUNT_MESSAGE;
import static com.example.common.Constants.HEART_COUNT_VALUE;
import static com.example.common.Constants.TIME_SESSION_MESSAGE;
import static com.example.common.Constants.TIME_SESSION_VALUE;
import static com.example.quentincourvoisier.polarclub.fragments.HomeFragment.ARG_ID_PARTICIPANT;
import static com.example.quentincourvoisier.polarclub.fragments.HomeFragment.ARG_SESSION;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeartRateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HeartRateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeartRateFragment extends Fragment implements View.OnClickListener {

    private static final String BTN_MORE_HEART = "btn_more_heart";
    private static final String BTN_LESS_HEART = "btn_less_heart";

    private Intent heartBeatIntent;
    private BroadcastReceiver brHeartbeat;
    private BroadcastReceiver brFinishSession;
    private TimerSessionTask timerSessionTask;
    private Timer timer;

    private TextView mTextView;
    private HeartBeatView heartbeat;
    private Button increaseButton;
    private Button decreaseButton;

    private String idParticipant;
    private Session session;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private OnFragmentInteractionListener mListener;

    public HeartRateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param argPseudo Parameter 1.
     * @return A new instance of fragment HeartRateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HeartRateFragment newInstance(String argPseudo) {
        HeartRateFragment fragment = new HeartRateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SESSION, argPseudo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            session = (Session) getArguments().getSerializable(ARG_SESSION);
            idParticipant = getArguments().getString(ARG_ID_PARTICIPANT);
        }

        Log.i("idParticipant", idParticipant);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        heartBeatIntent = new Intent(getActivity(), WearHearbeatEmulatorService.class);
        View rootView = inflater.inflate(R.layout.fragment_heart_rate, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.tvheartrate);
        heartbeat = (HeartBeatView) rootView.findViewById(R.id.heartbeat);
        increaseButton = (Button) rootView.findViewById(R.id.increaseButton);
        increaseButton.setOnClickListener(this);
        decreaseButton = (Button) rootView.findViewById(R.id.decreaseButton);
        decreaseButton.setOnClickListener(this);

        HeartBeatView imageView = (HeartBeatView) rootView.findViewById(R.id.heartbeat);
        Animation pulse = AnimationUtils.loadAnimation(getActivity(), R.anim.heart_pulse);
        imageView.startAnimation(pulse);
        setTag();

        timerSessionTask = new TimerSessionTask(getActivity());
        timerSessionTask.setTimestamp(session.getDebut());

        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(timerSessionTask, 1000, 1000);
        }

        brHeartbeat = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int hearbeats = intent.getIntExtra(HEART_COUNT_VALUE, 80);
                mTextView.setText(String.valueOf(hearbeats));
                database.getReference(DB_PARTICIPANTS).child(idParticipant).child("battements").setValue(hearbeats);
            }
        };

        brFinishSession = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isFinish = intent.getBooleanExtra(TIME_SESSION_VALUE, false);
                Log.d("AZERTY", String.valueOf(isFinish));
                if (isFinish) {
                    getActivity().unregisterReceiver(brFinishSession);
                    finishSession();
                }
            }
        };

        return rootView;
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
        getActivity().startService(heartBeatIntent);
        getContext().registerReceiver(brHeartbeat, new IntentFilter(HEART_COUNT_MESSAGE));
        getContext().registerReceiver(brFinishSession, new IntentFilter(TIME_SESSION_MESSAGE));
    }


    @Override
    public void onPause() {
        super.onPause();
        getActivity().stopService(heartBeatIntent);
        getActivity().unregisterReceiver(brHeartbeat);

        try {
            getActivity().unregisterReceiver(brFinishSession);
        } catch (IllegalArgumentException e) {

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
            case BTN_MORE_HEART:
                DailyHeartBeat.setMoreAverage();
                break;
            case BTN_LESS_HEART:
                DailyHeartBeat.setLessAverage();
                break;
        }
    }

    private void setTag() {
        increaseButton.setTag(BTN_MORE_HEART);
        decreaseButton.setTag(BTN_LESS_HEART);
    }

    private void finishSession() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Fin de session");
        alert.setMessage("Bravo vous êtes arrivé au bout de la session. Elle est maintenant terminé");

        alert.setPositiveButton("Finir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HomeFragment fragment = new HomeFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_frame, fragment, "FragmentName").commit();
            }
        });

        alert.show();
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
