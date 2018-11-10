package com.ayoubidel.domosapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ayoubidel.domosapp.R;
import com.ayoubidel.domosapp.activities.MainActivity;
import com.ayoubidel.domosapp.adapters.RecyclerViewAdapter;
import com.ayoubidel.domosapp.models.Module;
import com.ayoubidel.domosapp.models.ModuleType;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private View myView;
    private FloatingActionButton fab;
    private Activity myActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home_fragment, container, false);

        fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Psksh", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                getFragmentManager().beginTransaction().replace(R.id.content_frame, new AddButtonFragment()).commit();

            }
        });

        List<Module> modules = new ArrayList<>();
        modules.add(new Module(ModuleType.LIGHT.toString(), "Light Bulb 1", "Bola dyal dar"));
        modules.add(new Module(ModuleType.LIGHT.toString(), "Light Bulb 2", "Bola dyal dar"));
        modules.add(new Module(ModuleType.LIGHT.toString(), "Light Bulb 3", "Bola dyal dar"));
        modules.add(new Module(ModuleType.LIGHT.toString(), "Light Bulb 4", "Bola dyal dar"));

        RecyclerView recyclerView = myView.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(myView.getContext(), modules);
        recyclerView.setLayoutManager(new GridLayoutManager(myView.getContext(), 3));
        recyclerView.setAdapter(adapter);
        return myView;
    }


}
