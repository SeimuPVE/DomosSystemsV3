package domosapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.domosapp.R;

import domosapp.activities.MainActivity;
import domosapp.adapters.SettingsDatabaseAdapter;


public class SettingsFragment extends Fragment {
    private SettingsDatabaseAdapter settingsDatabaseAdapter;
    private View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings_fragment, container, false);
        Button button = view.findViewById(R.id.btn_settings_update);

        // Set hints.
        EditText text_ip = view.findViewById(R.id.editTextIP);
        EditText text_port = view.findViewById(R.id.editTextPORT);
        EditText text_salt = view.findViewById(R.id.editTextSALT);

        if(SettingsDatabaseAdapter.getSettings() != null) {
            text_ip.setHint(SettingsDatabaseAdapter.getSettings().getIp());
            text_port.setHint(String.valueOf(SettingsDatabaseAdapter.getSettings().getPort()));
            text_salt.setHint(SettingsDatabaseAdapter.getSettings().getSalt());
        }

        // Set onClick.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text_ip = view.findViewById(R.id.editTextIP);
                EditText text_port = view.findViewById(R.id.editTextPORT);
                EditText text_salt = view.findViewById(R.id.editTextSALT);

                String ip = text_ip.getHint().toString(), salt = "";
                int port = Integer.valueOf(text_port.getHint().toString());

                if(!TextUtils.isEmpty(text_ip.getText().toString()))
                    ip = text_ip.getText().toString();
                if(!TextUtils.isEmpty(text_port.getText().toString()))
                    port = Integer.valueOf(text_port.getText().toString());
                if(!TextUtils.isEmpty(text_salt.getText().toString()))
                    salt = text_salt.getText().toString();

                settingsDatabaseAdapter.insertSettings(ip, port, salt);

                MainActivity.launchHomeActivity();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        settingsDatabaseAdapter = new SettingsDatabaseAdapter(context);
    }
}
