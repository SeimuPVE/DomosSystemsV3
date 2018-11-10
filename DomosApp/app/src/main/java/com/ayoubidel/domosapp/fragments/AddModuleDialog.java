package com.ayoubidel.domosapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.ayoubidel.domosapp.R;

public class AddModuleDialog extends AppCompatDialogFragment {

    private EditText editTextIp;
    private EditText editTextPort;
    private EditText editTextLabel;
    private EditText editTextDescription;

    private AddModuleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_module_dialog, null);
        editTextIp = view.findViewById(R.id.module_ip_id);
        editTextPort = view.findViewById(R.id.module_port_id);
        editTextLabel = view.findViewById(R.id.module_label_id);
        editTextDescription = view.findViewById(R.id.module_description_id);
        builder.setView(view)
                .setTitle("Add Module")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ip = editTextIp.getText().toString().trim();
                        String port = editTextPort.getText().toString().trim();
                        String label = editTextLabel.getText().toString().trim();
                        String description = editTextDescription.getText().toString().trim();
                        listener.addModule(ip, port, label, description);
                    }
                });
        return builder.create();
    }

    public void setListener(AddModuleDialogListener listener) {
        this.listener = listener;
    }

    public interface AddModuleDialogListener {
        void addModule(String ip, String port, String label, String description);
    }
}
