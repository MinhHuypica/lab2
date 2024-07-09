package foply.ph52222.huanph52222.bai2_adr2;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class dbhelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "todoList.db";
        private static final int DATABASE_VERSION = 1;

        public dbhelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TODO_TABLE = "CREATE TABLE todos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "content TEXT," +
                    "date TEXT," +
                    "type TEXT," +
                    "status INTEGER)";
            db.execSQL(CREATE_TODO_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS todos");
            onCreate(db);
        }
    }

