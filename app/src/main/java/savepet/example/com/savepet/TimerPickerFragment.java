package savepet.example.com.savepet;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

import savepet.example.com.savepet.fragments.FragmentVistaAnimal;
import savepet.example.com.savepet.fragments.FragmentVistaEvento;

@SuppressLint("ValidFragment")
public class TimerPickerFragment extends DialogFragment {

    TimePickerDialog.OnTimeSetListener context;
    @SuppressLint("ValidFragment")
    public TimerPickerFragment(TimePickerDialog.OnTimeSetListener context)
    {
        this.context = context;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) context, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}
