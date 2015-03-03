package androidgroup.com.googleimagesearch.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;

import androidgroup.com.googleimagesearch.Interface.DialogFragmentListener;
import androidgroup.com.googleimagesearch.R;

public class SettingsDialog extends DialogFragment {

    HashMap<String,String> map = new HashMap<String,String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_settings, container);
        getDialog().setTitle("Advanced Filters");
        Spinner spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        Spinner spImageColor = (Spinner) view.findViewById(R.id.spColors);
        Spinner spImageType = (Spinner) view.findViewById(R.id.spImageType);
        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        spImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = parent.getItemAtPosition(position).toString();
                map.put("ImageSize", val);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spImageColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = parent.getItemAtPosition(position).toString();
                map.put("ImageColor", val);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spImageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = parent.getItemAtPosition(position).toString();
                map.put("ImageType",val);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentListener listener = (DialogFragmentListener) getActivity();
                listener.onReturnValue(map);
                getDialog().dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return view;

    }
}
