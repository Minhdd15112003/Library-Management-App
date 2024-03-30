package ph25260.fpoly.asm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbheper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 3;

    public Dbheper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbLogin = "create table login(id integer primary key autoincrement," +
                "email text NOT NULL," +
                "username text NOT NULL," +
                "password text NOT NULL," +
                "numberphone text NOT NULL)";

        db.execSQL(tbLogin);

        String tbLoaiSach = "create table loaisach(id integer primary key autoincrement," +
                "tenloai text NOT NULL)";

        db.execSQL(tbLoaiSach);

        String tbQlSach = "create table qlsach(id integer primary key autoincrement," +
                "tensach text NOT NULL," +
                "giaSach text NOT NULL," +
                "loaiSachId integer REFERENCES loaisach(id))";

        db.execSQL(tbQlSach);

        String tbPhieuMuon = "create table phieumuon(id integer primary key autoincrement," +
                "nguoiMuon text REFERENCES login(username)," +
                "idSach integer REFERENCES qlsach(id)," +
                "tenSach text REFERENCES qlsach(tensach)," +
                "loaiSachId integer REFERENCES qlsach(loaiSachId)," +
                "giaSach integer REFERENCES qlsach(giaSach)," +
                "ngayThue text NOT NULL," +
                "trangThai boolean NOT NULL)";

        db.execSQL(tbPhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS login");
        db.execSQL("DROP TABLE IF EXISTS loaisach");
        db.execSQL("DROP TABLE IF EXISTS qlsach");
        db.execSQL("DROP TABLE IF EXISTS phieumuon");
        onCreate(db);
    }
}
