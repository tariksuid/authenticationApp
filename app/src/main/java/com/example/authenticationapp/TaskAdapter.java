package com.example.authenticationapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter
        extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<TaskModel> ar;

    public TaskAdapter(ArrayList<TaskModel> ar) {
        this.ar = ar;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        //for (int i =0  ; i<ar.size();i++)
        TextView txt = cardView.findViewById(R.id.txtName);

        txt.setText(ar.get(position).getTask() + " ------ " + ar.get(position).getCh());
        Log.d("TEXT", txt.getText().toString());

        //CheckBox ck = cardView.findViewById(R.id.chk);
        // ck.setChecked(ar.get(position).);

        Log.d("STATUS", String.valueOf(ar.get(position).getCh()));

        // txt.setVisibility((boolean)txt.getVisibility() ? View.GONE : View.VISIBLE);
        //   txtg.setVisibility(isTypeText ? View.VISIBLE : View.GONE);


    }

    @Override
    public int getItemCount() {
        return ar.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}