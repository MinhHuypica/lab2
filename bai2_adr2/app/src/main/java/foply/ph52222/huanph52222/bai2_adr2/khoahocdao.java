package foply.ph52222.huanph52222.bai2_adr2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class khoahocdao {
    private SQLiteDatabase db;

    public khoahocdao(Context context) {

        dbhelper dbhelper = new dbhelper(context);
        db = dbhelper.getWritableDatabase();

    }

    public long addTodo(khoahoc todo) {
        ContentValues values = new ContentValues();
        values.put("title", todo.getTitle());
        values.put("content", todo.getContent());
        values.put("date", todo.getDate());
        values.put("type", todo.getType());
        values.put("status", todo.getStatus());
        return db.insert("todos", null, values);
    }

    public List<khoahoc> getAllTodos() {
        List<khoahoc> todos = new ArrayList<>();
        Cursor cursor = db.query("todos",
                null, null,
                null, null,
                null, "id DESC");
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") khoahoc todo = new khoahoc(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("content")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("type")),
                        cursor.getInt(cursor.getColumnIndex("status"))
                );
                todos.add(todo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return todos;
    }

    public void updateTodoStatus(int id, int status) {
        ContentValues values = new ContentValues();
        values.put("status", status);
        db.update("todos", values,
                "id = ?",
                new String[]{String.valueOf(id)});
    }
    public void updateTodo(khoahoc todo) {
        ContentValues values = new ContentValues();
        values.put("title", todo.getTitle());
        values.put("content", todo.getContent());
        values.put("date", todo.getDate());
        values.put("type", todo.getType());
        values.put("status", todo.getStatus());
        db.update("todos", values,
                "id = ?",
                new String[]{String.valueOf(todo.getId())});
    }

    public void deleteTodo(int id) {
        db.delete("todos",
                "id = ?",
                new String[]{String.valueOf(id)});
    }
}
