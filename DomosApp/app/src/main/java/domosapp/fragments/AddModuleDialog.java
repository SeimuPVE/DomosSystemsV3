package domosapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.domosapp.R;


public class AddModuleDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextLabel;
    private EditText editTextCommand;

    private AddModuleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_add_module_dialog, null);
        editTextName = view.findViewById(R.id.module_name_id);
        editTextLabel = view.findViewById(R.id.module_label_id);
        editTextCommand = view.findViewById(R.id.module_label_id);

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
