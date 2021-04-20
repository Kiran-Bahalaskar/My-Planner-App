package com.kiranbahalaskar.assisgnmentapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kiranbahalaskar.assisgnmentapp.Models.ModelPlans;
import com.kiranbahalaskar.assisgnmentapp.R;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    List<ModelPlans> modelPlansList;
    Context context;

    public PlanAdapter(List<ModelPlans> modelPlansList, Context context) {
        this.modelPlansList = modelPlansList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemplans, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        ModelPlans modelPlans = modelPlansList.get(position);
        holder.tvPlan.setText(modelPlans.getPlan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_view_plan);

                TextView tvPlanShow = dialog.findViewById(R.id.tvPlanShow);

                tvPlanShow.setText(modelPlans.getPlan());

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelPlansList.size();
    }

    public class PlanViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlan;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPlan = itemView.findViewById(R.id.tvPlan);
        }
    }
}
