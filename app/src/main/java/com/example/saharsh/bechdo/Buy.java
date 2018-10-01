package com.example.saharsh.bechdo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Buy extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference myref;
    private String contact;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myref= FirebaseDatabase.getInstance().getReference().child("Database");
        FirebaseRecyclerAdapter<Cycle,CycleViewHolder> recyclerAdapter=new FirebaseRecyclerAdapter<Cycle,CycleViewHolder>(
                Cycle.class,
                R.layout.cycle_row,
                CycleViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(CycleViewHolder viewHolder, Cycle model, int position) {
                viewHolder.setCost(model.getCost());
                viewHolder.setContact(model.getContact());
                viewHolder.setUrl(model.getUrl());
                viewHolder.setModel(model.getModel());
                contact=model.getContact();
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+contact));
                        startActivity(intent);
                    }
                });
            }
        };




        recyclerView.setAdapter(recyclerAdapter);

    }

    public static class CycleViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView cycle_cost;
        TextView cycle_contact;
        TextView cycle_model;
        ImageView image_cycle;
        Button button;
        public CycleViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            cycle_cost = (TextView)itemView.findViewById(R.id.cycle_cost);
            cycle_contact = (TextView) itemView.findViewById(R.id.cycle_contact);
            image_cycle=(ImageView)itemView.findViewById(R.id.image_cycle);
            cycle_model=(TextView)itemView.findViewById(R.id.cycle_model);
            button=(Button)itemView.findViewById(R.id.button);
        }
        public void setCost(String title)
        {
            cycle_cost.setText("Cost: "+title+" Rs.");
        }
        public void setContact(String description)
        {
            cycle_contact.setText("Contact No: "+description);
        }

        public void setModel(String model)
        {
            cycle_model.setText("Model: "+model);
        }

        public void setUrl(String image)
        {
            Picasso.get().load(image).into(image_cycle);
        }
    }
}
