package com.kiranbahalaskar.assisgnmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiranbahalaskar.assisgnmentapp.Adapters.PlanAdapter;
import com.kiranbahalaskar.assisgnmentapp.Models.ModelPlans;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // MainActivity Contents
    private Toolbar toolbar;
    private RecyclerView recyclerviewPlans;
    private FloatingActionButton btnAdd;

    // Adapter Contents
    private PlanAdapter planAdapter;
    private ArrayList<ModelPlans> list;


    //Alert Dialog Contents
    private ImageView btnClose;
    private EditText etPlan;
    private Button btnAddPlanDialog;
    private String stretPlan;

    //Firebase Content
    private DatabaseReference plansDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup Toolbar
        toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("My Planner App");
        setSupportActionBar(toolbar);

        plansDbRef = FirebaseDatabase.getInstance().getReference().child("Plans");

        // attach view item to objects
        recyclerviewPlans = findViewById(R.id.recyclerviewPlans);
        btnAdd = findViewById(R.id.btnAdd);

        recyclerviewPlans.setLayoutManager(new LinearLayoutManager(this));

        //Call Alert Dialog Method
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlans();
            }
        });
        //setup Recyclerview and fetch data into firebase
        getPlanData();
    }

    // AlertDialog With Insert Function
    private void addPlans() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_plans_dialog);

        btnClose = dialog.findViewById(R.id.btnClose);
        etPlan = dialog.findViewById(R.id.etPlan);
        btnAddPlanDialog = dialog.findViewById(R.id.btnAddPlanDialog);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAddPlanDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stretPlan = etPlan.getText().toString().trim();

                if (stretPlan.length() == 0) {

                    etPlan.setError("Plan Required");
                    etPlan.requestFocus();

                } else {

                    HashMap<String, String> userMap = new HashMap<>();

                    userMap.put("plan", stretPlan);

                    plansDbRef.push().setValue(userMap);
                    Toast.makeText(MainActivity.this, "Plan Added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });


        dialog.show();
    }
    // fetch Data from firebase
    private void getPlanData() {

        list = new ArrayList<>();
        planAdapter = new PlanAdapter(list, this);
        recyclerviewPlans.setAdapter(planAdapter);

        plansDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelPlans modelPlans = dataSnapshot.getValue(ModelPlans.class);
                    list.add(modelPlans);
                }
                planAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}