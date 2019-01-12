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

import domosapp.models.Module;
import domosapp.utils.Constants;


public class SaveModuleDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextLabel;
    private EditText editTextCommand;

    private SaveModuleDialogListener listener;

    @SuppressLint("CutPasteId")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String buttonLabel = Constants.ADD;
        final Module module;

        if (this.getArguments() != null)
            module = (Module) this.getArguments().get(Constants.BUNDLE_MODULE_KEY);
        else
            module = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.layout_add_module_dialog, null);

        editTextName = view.findViewById(R.id.module_name_id);
        editTextLabel = view.findViewById(R.id.module_label_id);
        editTextCommand = view.findViewById(R.id.module_command_id);

        if (module != null) {
            editTextName.setText(module.getName());
            editTextLabel.setText(module.getLabel());
            editTextCommand.setText(module.getCommand());
            buttonLabel = Constants.UPDATE;
        }

        builder.setView(view).setTitle(Constants.ADD_MODULE).setNegativeButton(Constants.CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton(buttonLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = editTextName.getText().toString().trim();
                String label = editTextLabel.getText().toString().trim();
                String command = editTextCommand.getText().toString().trim();

                if (module == null)
                    listener.addModule(name, label, command);
                else
                    listener.updateModule(module.getId(), name, label, command);
            }
        });

        return builder.create();
    }

    public void setListener(SaveModuleDialogListener listener) {
        this.listener = listener;
    }

    public interface SaveModuleDialogListener {
        void addModule(String name, String label, String command);

        void updateModule(int id, String name, String label, String command);
    }
}
