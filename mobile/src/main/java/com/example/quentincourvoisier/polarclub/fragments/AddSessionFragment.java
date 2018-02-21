package com.example.quentincourvoisier.polarclub.fragments;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.common.Constants;
import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.helper.HelperDate;
import com.example.common.model.Session;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;
import static com.example.common.Constants.DB_SESSIONS;
import static com.example.common.Constants.PREF_POLAR;
import static com.example.common.Constants.PREF_USER;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddSessionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddSessionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSessionFragment extends Fragment implements View.OnClickListener {

    private static final String FIELD_DATE_CREATE_SESSION = "field_date_create_session";
    private static final String FIELD_HEURE_CREATE_SESSION = "field_heure_create_session";
    private static final String BTN_CREATE_SESSION = "btn_create_session";

    private View root = null;
    private OnFragmentInteractionListener mListener;

    private EditText fieldSessionDate;
    private EditText fieldSessionHeure;
    private Button buttonCreateSession = null;
    private BottomNavigationView bottomNavigationView;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private SharedPreferences preferences;

    public AddSessionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_session, container, false);

        fieldSessionDate = root.findViewById(R.id.addSessionFrag_date);
        fieldSessionHeure = root.findViewById(R.id.addSessionFrag_heure);
        buttonCreateSession = root.findViewById(R.id.addSessionFrag_btn);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);

        fieldSessionDate.setOnClickListener(this);
        fieldSessionDate.setTag(FIELD_DATE_CREATE_SESSION);
        fieldSessionHeure.setOnClickListener(this);
        fieldSessionHeure.setTag(FIELD_HEURE_CREATE_SESSION);
        buttonCreateSession.setOnClickListener(this);
        buttonCreateSession.setTag(BTN_CREATE_SESSION);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        preferences = getActivity().getSharedPreferences(PREF_POLAR, MODE_PRIVATE);

        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch ((String) view.getTag()) {
            case FIELD_DATE_CREATE_SESSION:
                dateForCreateSession();
                break;
            case FIELD_HEURE_CREATE_SESSION:
                heureForCreateSession();
                break;
            case BTN_CREATE_SESSION:
                createSession();
                break;
        }
    }

    private void dateForCreateSession() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fieldSessionDate.setText(sdf.format(calendar.getTime()));
            }
        };

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.setTitle("Sélectionner la date de la session");
        datePicker.show();
    }

    private void heureForCreateSession() {
        final Calendar myCalendar = Calendar.getInstance();
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                fieldSessionHeure.setText(hourOfDay + ":" + min);
            }
        }, hour, minute, true);
        timePicker.setTitle("Sélectionner l'heure de début de la session");
        timePicker.show();
    }

    private void createSession() {
        String date = HelperDate.datetimeFrToUs(fieldSessionDate.getText().toString());
        String heure = fieldSessionHeure.getText().toString();
        long timestamp = HelperDate.dateToTimestamp(date + " " + heure + ":00.000");

        String uid = database.getReference(DB_SESSIONS).push().getKey();
        String userName = preferences.getString(PREF_USER, "");
        Session session = new Session(uid, timestamp, userName);

        database.getReference(DB_SESSIONS).child(uid).setValue(session).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(getActivity(), "Il y a eu un probleme lors de la création de la session", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "La session est bien crée", Toast.LENGTH_SHORT).show();

                getActivity().setTitle(Constants.TITLE_NAVIGATION_LIST_SESSIONS);
                bottomNavigationView.setSelectedItemId(R.id.navigation_list_session);

                ListSessionFragment fragment = new ListSessionFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content, fragment, "FragmentName").commit();
            }
        });
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
