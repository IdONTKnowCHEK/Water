package niu.edu.water;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    private Context context;
    private List<String> memberList;

    LogAdapter(Context context, List<String> memberList) {
        this.context = context;
        this.memberList = memberList;
    }

    @Override
    public LogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LogAdapter.ViewHolder holder, int position) {
        final String member = memberList.get(position);

        holder.textId.setText(String.valueOf(member));
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textId;
        ViewHolder(View itemView) {
            super(itemView);
            textId = (TextView) itemView.findViewById(R.id.date_text);
        }
    }
}