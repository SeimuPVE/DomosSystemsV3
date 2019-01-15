package domosapp.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.domosapp.R;

import domosapp.utils.STRINGS;


public class AddModuleDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextLabel;
    private EditText editTextCommand;

    private AddModuleDialogListener listener;

    @SuppressLint("CutPasteId")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.layout_add_module_dialog, null);
        editTextName = view.findViewById(R.id.module_name_id);
        editTextLabel = view.findViewById(R.id.module_label_id);
        editTextCommand = view.findViewById(R.id.module_command_id);

        builder.setView(view).setTitle(STRINGS.ADD_MODULE).setNegativeButton(STRINGS.CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).setPositiveButton(STRINGS.ADD, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editTextName.getText().toString().trim();
                        String label = editTextLabel.getText().toString().trim();
                        String command = editTextCommand.getText().toString().trim();
                        listener.addModule(name, label, command);
                    }
                });

        return builder.create();
    }

    public void setListener(AddModuleDialogListener listener) {
        this.listener = listener;
    }

    public interface AddModuleDialogListener {
        void addModule(String name, String label, String command);
    }
}