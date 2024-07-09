package foply.ph52222.huanph52222.bai2_adr2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText etTitle, etContent, etDate, etType;
    private Button btnAdd;
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private khoahocdao todoDAO;
    private List<khoahoc> todoList;
    private khoahoc currentEditingTodo = null; // Biến theo dõi công việc đang chỉnh sửa
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        etDate = findViewById(R.id.etDate);
        etType = findViewById(R.id.etType);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.draft21_recyclerView);

        todoDAO = new khoahocdao(this);
        todoList = todoDAO.getAllTodos();

        adapter = new TodoAdapter(todoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                MainActivity.this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentEditingTodo == null) {
                    addTodo();
                } else {
                    updateTodo();
                }
            }
        });

        adapter.setOnItemClickListener(
                new TodoAdapter.OnItemClickListener() {
                    @Override
                    public void onDeleteClick(int position) {
                        deleteTodo(position);
                    }

                    @Override
                    public void onEditClick(int position) {
                        editTodo(position);
                    }

                    @Override
                    public void onStatusChange(int position, boolean isDone) {
                        updateTodoStatus(position, isDone);
                    }
                });
    }

    private void addTodo() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String date = etDate.getText().toString();
        String type = etType.getText().toString();

        khoahoc todo = new khoahoc(0, title, content, date,
                type, 0);
        todoDAO.addTodo(todo);
        todoList.add(0, todo);
        adapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
        clearFields();
    }
    private void updateTodo() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String date = etDate.getText().toString();
        String type = etType.getText().toString();
        currentEditingTodo.setTitle(title);
        currentEditingTodo.setContent(content);
        currentEditingTodo.setDate(date);
        currentEditingTodo.setType(type);
        todoDAO.updateTodo(currentEditingTodo);
        int position = todoList.indexOf(currentEditingTodo);
        adapter.notifyItemChanged(position);

        currentEditingTodo = null; // Reset biến theo dõi
        btnAdd.setText("Add"); // Đổi text button thành Add
        clearFields();
    }
    private void editTodo(int position) {
        currentEditingTodo = todoList.get(position);

        etTitle.setText(currentEditingTodo.getTitle());
        etContent.setText(currentEditingTodo.getContent());
        etDate.setText(currentEditingTodo.getDate());
        etType.setText(currentEditingTodo.getType());

        btnAdd.setText("Update"); // Đổi text button thành Update
    }
    private void clearFields() {
        etTitle.setText("");
        etContent.setText("");
        etDate.setText("");
        etType.setText("");
    }

    private void deleteTodo(int position) {
        khoahoc todo = todoList.get(position);
        todoDAO.deleteTodo(todo.getId());
        todoList.remove(position);
        adapter.notifyItemRemoved(position);
    }


    private void updateTodoStatus(int position, boolean isDone) {
        khoahoc todo = todoList.get(position);
        todo.setStatus(isDone ? 1 : 0);
        todoDAO.updateTodoStatus(todo.getId(), todo.getStatus());
        // Sử dụng Handler để thực hiện notifyItemChanged bên ngoài vòng đời của RecyclerView
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(position);
            }
        });
        //adapter.notifyItemChanged(position);
        Toast.makeText(this, "Đã update status thành công", Toast.LENGTH_SHORT).show();
    }
}