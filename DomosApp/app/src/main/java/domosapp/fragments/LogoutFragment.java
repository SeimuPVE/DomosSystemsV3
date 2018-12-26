package domosapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.domosapp.R;

import domosapp.activities.LoginActivity;
import domosapp.adapters.UserDatabaseAdapter;


public class LogoutFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logout_fragment, container, false);
        Button logoutBtn = view.findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserDatabaseAdapter userDatabaseAdapter = new UserDatabaseAdapter(getContext());
                userDatabaseAdapter.deleteUser();
                getActivity().finish();
                if (UserDatabaseAdapter.getUser() == null) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}
