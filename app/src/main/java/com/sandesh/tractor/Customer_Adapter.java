package com.sandesh.tractor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Customer_Adapter extends RecyclerView.Adapter<Customer_Adapter.MyViewHolder> {
    private final List<UsersModel> moviesList;

    Customer_Adapter(List<UsersModel> moviesList) {
        //private final CustomerJobListener listener;
        this.moviesList = moviesList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_work_entry, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UsersModel movie = moviesList.get(position);
        holder.cust_workname.setText(movie.getWorkname());
        holder.cust_date.setText("Work Date : " + movie.getWorkdate());
        holder.totaltime.setText("Total Time : " + movie.getWorktime() + " Hrs.");
        holder.cust_workendtime.setText(movie.getTotalamount() + " - " + movie.getPaidamount() + " = " + movie.getReaminamount());
        holder.circleImageView.setImageResource(R.drawable.sigin_boy_img);
        // holder.materialCardView.setOnClickListener(view -> listener.onDisplayCustomer(movie));
      /*  if (movie.getStatus() == 0) {
            holder.materialCardView.setBackgroundColor(Color.GREEN);
        }*/
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        final CircleImageView circleImageView;
        final AppCompatTextView cust_workname, cust_workendtime, totaltime, cust_date;
        final MaterialCardView materialCardView;

        MyViewHolder(View view) {
            super(view);

            circleImageView = view.findViewById(R.id.circleImageView);
            cust_workname = view.findViewById(R.id.cust_workname);
            totaltime = view.findViewById(R.id.totaltime);
            cust_date = view.findViewById(R.id.cust_date);
            cust_workendtime = view.findViewById(R.id.cust_workendtime);
            materialCardView = view.findViewById(R.id.card_action);

        }
    }
}