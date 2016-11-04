package com.wikrama.masis;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by I Can Do That on 11/5/2016.
 */

public class MadingViewHolder extends RecyclerView.ViewHolder {
    TextView listJudul,listUniv;
    public MadingViewHolder(View itemView) {
        super(itemView);
        listJudul = (TextView)itemView.findViewById(R.id.rvJudul);
        listUniv = (TextView)itemView.findViewById(R.id.rvUniv);
    }
}
