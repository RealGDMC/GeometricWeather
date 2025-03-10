package wangdaye.com.geometricweather.common.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import wangdaye.com.geometricweather.R;
import wangdaye.com.geometricweather.common.basic.models.weather.Alert;

/**
 * Alert adapter.
 * */

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {

    private final List<Alert> mAlertList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        // widget
        TextView title;
        TextView subtitle;
        TextView content;

        ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.item_alert_title);
            this.subtitle = itemView.findViewById(R.id.item_alert_subtitle);
            this.content = itemView.findViewById(R.id.item_alert_content);
        }
    }

    public AlertAdapter(List<Alert> list) {
        mAlertList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alert, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(mAlertList.get(position).getDescription());
        holder.subtitle.setText(
                DateFormat.getDateTimeInstance(
                        DateFormat.LONG, DateFormat.DEFAULT
                ).format(mAlertList.get(position).getDate())
        );
        holder.content.setText(mAlertList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mAlertList.size();
    }
}

