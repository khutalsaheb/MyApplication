package com.sandesh.tractor;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.MyViewHolder> {
    private final List<UsersModel> moviesList;
    private final CompleteJobListener listener;

    User_Adapter(List<UsersModel> moviesList, CompleteJobListener listener) {
        this.moviesList = moviesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_users, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UsersModel movie = moviesList.get(position);
        holder.user_name_txt.setText(movie.getName());
        holder.user_mobile_txt.setText(movie.getMobile());
        holder.circleImageView.setImageResource(R.drawable.sigin_boy_img);
        holder.materialCardView.setOnClickListener(view -> listener.onAddToCartPressed(movie));
      /*  if (movie.getStatus() == 0) {
            holder.materialCardView.setBackgroundColor(Color.GREEN);
        }*/
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public interface CompleteJobListener {
        void onAddToCartPressed(UsersModel listData);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final CircleImageView circleImageView;
        final AppCompatTextView user_name_txt, user_mobile_txt;
        final MaterialCardView materialCardView;

        MyViewHolder(View view) {
            super(view);

            circleImageView = view.findViewById(R.id.circleImageView);
            user_name_txt = view.findViewById(R.id.user_name_txt);
            user_mobile_txt = view.findViewById(R.id.user_mobile_txt);
            materialCardView = view.findViewById(R.id.card_action);

        }
    }
}