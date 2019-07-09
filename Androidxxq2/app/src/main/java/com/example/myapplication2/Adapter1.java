package com.example.myapplication2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

    public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder>{
        @NonNull

        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.recycle, parent, false);
            return new ViewHolder(itemView);
        }


        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        public int getItemCount() {
            return 100;
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

