package foply.ph52222.huanph52222.bai2_adr2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<khoahoc> todoList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onEditClick(int position);
        void onStatusChange(int position, boolean isDone);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TodoAdapter(List<khoahoc> todoList) {
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mail,
                        parent, false);
        return new TodoViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(
            @NonNull TodoViewHolder holder,
            @SuppressLint("RecyclerView") int position) {
        khoahoc currentTodo = todoList.get(position);
        holder.tvToDoName.setText(currentTodo.getTitle());
        holder.checkBox.setChecked(currentTodo.getStatus() == 1);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (listener != null) {
                    listener.onStatusChange(position, isChecked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        public TextView tvToDoName;
        public Button btnEdit, btnDelete;
        public CheckBox checkBox;

        public TodoViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvToDoName = itemView.findViewById(R.id.tvToDoName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            checkBox = itemView.findViewById(R.id.checkBox);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }
}