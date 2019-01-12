package domosapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.domosapp.R;

import domosapp.adapters.SettingsDatabaseAdapter;


public class SettingsFragment extends Fragment {
    private SettingsDatabaseAdapter settingsDatabaseAdapter;
    private View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings_fragment, container, false);
        Button button = view.findViewById(R.id.btn_settings_update);

        // TODO : manage empty settings.

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text_ip = view.findViewById(R.id.editTextIP);
                EditText text_port = view.findViewById(R.id.editTextPORT);
                EditText text_salt = view.findViewById(R.id.editTextSALT);

                String ip = "192.168.1.1", salt = ""; // TODO : change default values.
                int port = 8080;

                if(!TextUtils.isEmpty(text_ip.getText().toString()))
                    ip = text_ip.getText().toString();
                if(!TextUtils.isEmpty(text_port.getText().toString()))
                    port = Integer.valueOf(text_port.getText().toString());
                if(!TextUtils.isEmpty(text_salt.getText().toString()))
                    salt = text_salt.getText().toString();

                settingsDatabaseAdapter.insertSettings(ip, port, salt);

                Toast.makeText(getContext(), "Settings added !", Toast.LENGTH_SHORT).show(); // TODO : export text.
                // TODO : redirect on Home.
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
