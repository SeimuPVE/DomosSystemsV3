package domosapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.domosapp.R;

import domosapp.adapters.ModuleDatabaseAdapter;
import domosapp.adapters.RecyclerViewAdapter;
import domosapp.models.Module;
import domosapp.models.ModuleType;
import domosapp.utils.STRINGS;
import io.github.yavski.fabspeeddial.FabSpeedDial;

import java.util.List;


public class HomeFragment extends Fragment {
    private List<Module> modules;
    private RecyclerViewAdapter adapter;
    private ModuleDatabaseAdapter moduleDatabaseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.home_fragment, container, false);
        RecyclerView recyclerView = myView.findViewById(R.id.recyclerview_id);
        FabSpeedDial fabSpeedDial = myView.findViewById(R.id.fab_menu_id);

        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_light:
                        openAddModuleDialog(ModuleType.LIGHT);
                        break;
                    case R.id.action_strip_light:
                        openAddModuleDialog(ModuleType.LED_STRIP);
                        break;
                    case R.id.action_env_sensor:
                        openAddModuleDialog(ModuleType.ENV_SENSOR);
                        break;
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });

        moduleDatabaseAdapter = new ModuleDatabaseAdapter(getContext());
        modules = moduleDatabaseAdapter.getAllModules();

        adapter = new RecyclerViewAdapter(myView.getContext(), modules);
        recyclerView.setLayoutManager(new GridLayoutManager(myView.getContext(), 3));
        recyclerView.setAdapter(adapter);

        return myView;
    }

    private void openAddModuleDialog(final ModuleType type) {
        AddModuleDialog addModuleDialog = new AddModuleDialog();

        addModuleDialog.setListener(new AddModuleDialog.AddModuleDialogListener() {
            @Override
            public void addModule(String name, String label, String command) {
                moduleDatabaseAdapter.insertEntry(type.toString(), name, label, command);
                modules = moduleDatabaseAdapter.getAllModules();
                adapter.refreshList(modules);
            }
        });

        addModuleDialog.show(getFragmentManager(), STRINGS.ADD_MODULE_DIALOG);
    }
}
