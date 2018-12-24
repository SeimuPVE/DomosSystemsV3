package domosapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.domosapp.R;
import domosapp.models.Module;
import domosapp.models.ModuleType;
import domosapp.utils.AsyncNetworking;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Module> data;

    public RecyclerViewAdapter(Context context, List<Module> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.cardview_item_module, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.moduleName.setText(data.get(position).getName());
        holder.moduleLabel.setText(data.get(position).getLabel());

        if (data.get(position).getType().equals(ModuleType.LIGHT.toString()))
            holder.moduleImage.setImageResource(R.drawable.ic_action_light);
        else if (data.get(position).getType().equals(ModuleType.LED_STRIP.toString()))
            holder.moduleImage.setImageResource(R.drawable.ic_action_strip);
        else if (data.get(position).getType().equals(ModuleType.ENV_SENSOR.toString()))
            holder.moduleImage.setImageResource(R.drawable.ic_action_sensor);

        holder.moduleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncNetworking("192.168.1.21", 5433, data.get(position).getCommand()).execute(); // TODO : unfix IP.
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void refreshList(List<Module> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView moduleCard;
        ImageView moduleImage;
        TextView moduleName;
        TextView moduleLabel;

        MyViewHolder(View itemView) {
            super(itemView);

            moduleCard = itemView.findViewById(R.id.module_cardview_id);
            moduleImage = itemView.findViewById(R.id.module_image_id);
            moduleName = itemView.findViewById(R.id.module_name_id);
            moduleLabel = itemView.findViewById(R.id.module_label_id);
        }
    }
}
