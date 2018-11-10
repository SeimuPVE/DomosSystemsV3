package com.ayoubidel.domosapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ayoubidel.domosapp.R;
import com.ayoubidel.domosapp.models.Module;
import com.ayoubidel.domosapp.models.ModuleType;

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.moduleLabel.setText(data.get(position).getLabel());
        holder.moduleDescription.setText(data.get(position).getDescription());
        if (data.get(position).getType().equals(ModuleType.LIGHT.toString())) {
            holder.moduleImage.setImageResource(R.drawable.ic_action_add);
        } else if (data.get(position).getType().equals(ModuleType.LED_STRIP.toString())) {
            holder.moduleImage.setImageResource(R.drawable.ic_action_add);
        } else if (data.get(position).getType().equals(ModuleType.ENV_SENSOR.toString())) {
            holder.moduleImage.setImageResource(R.drawable.ic_action_add);
        }
        holder.moduleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Call web service
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView moduleLabel;
        TextView moduleDescription;
        ImageView moduleImage;
        CardView moduleCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            moduleLabel = (TextView) itemView.findViewById(R.id.module_name_id);
            moduleDescription = (TextView) itemView.findViewById(R.id.module_description_id);
            moduleImage = (ImageView) itemView.findViewById(R.id.module_image_id);
            moduleCard = (CardView) itemView.findViewById(R.id.module_cardview_id);
        }
    }
}
