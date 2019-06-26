package domosapp.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.domosapp.R;

import domosapp.models.Module;
import domosapp.utils.Constants;


public class ActionModuleDialog extends AppCompatDialogFragment {
    private Module module;

    private ActionModuleDialogListener listener;

    @SuppressLint("CutPasteId")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        module = (Module) this.getArguments().get(Constants.BUNDLE_MODULE_KEY);

        if (module != null)
            System.out.println("MACHI NULLLLL"); // TODO : correct the message.

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.layout_action_module_dialog, null);
        Button triggerButton = view.findViewById(R.id.module_trigger_id);
        Button updateButton = view.findViewById(R.id.module_update_id);
        Button deleteButton = view.findViewById(R.id.module_delete_id);

        triggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.trigger(module);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.update(module);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.delete(module);
            }
        });

        builder.setView(view).setTitle(Constants.ACTION_MODULE_TITLE);

        return builder.create();
    }

    public void setListener(ActionModuleDialogListener listener) {
        this.listener = listener;
    }

    public interface ActionModuleDialogListener {
        void trigger(Module module);

        void update(Module module);

        void delete(Module module);
    }
}
