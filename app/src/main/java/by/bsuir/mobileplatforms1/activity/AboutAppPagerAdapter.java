package by.bsuir.mobileplatforms1.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import by.bsuir.mobileplatforms1.R;

public class AboutAppPagerAdapter extends RecyclerView.Adapter<AboutAppPagerAdapter.ViewHolder> {
    private int[] textIds = {R.string.about_app_text1, R.string.about_app_text2};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.about_app_viewholder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.textView_info);
        textView.setText(textView.getContext().getString(textIds[position]));
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
