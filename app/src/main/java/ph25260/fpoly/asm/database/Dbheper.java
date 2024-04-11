package ph25260.fpoly.asm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbheper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

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
                "giaSach integer NOT NULL," +
                "loaiSachId integer REFERENCES loaisach(id))";

        db.execSQL(tbQlSach);

        String tbPhieuMuon = "create table phieumuon(id integer primary key autoincrement," +
                "idUser integer REFERENCES login(id)," +
                "idSach integer REFERENCES qlsach(id)," +
                "ngayThue DATE NOT NULL," +
                "trangThai integer NOT NULL)";

        db.execSQL(tbPhieuMuon);

//        db.execSQL("INSERT INTO login (id,email, username, password, numberphone) VALUES\n" +
//                "    (1,'user3@email.com', 'user3', 'password3', '0345678901'),\n" +
//                "    (2,'user4@email.com', 'user4', 'password4', '0456789012'),\n" +
//                "    (3,'user5@email.com', 'user5', 'password5', '0567890123');\n");

        db.execSQL("INSERT INTO loaisach (tenloai) VALUES\n" +
                "    ('Lịch sử'),\n" +
                "    ('Văn học'),\n" +
                "    ('Nghệ thuật');\n");

//        db.execSQL("INSERT INTO qlsach (tensach, giaSach, loaiSachId) VALUES\n" +
//                "    ('Lược sử loài người', 220000, 2),\n" +
//                "    ('Nghệ thuật sống', 170000, 3),\n" +
//                "    ('Chiến tranh và hòa bình', 250000, 1),\n" +
//                "    ('Bắt trẻ đồng xanh', 190000, 4),\n" +
//                "    ('Hồn bướm mơ tiên', 160000, 5);");

//        db.execSQL("INSERT INTO phieumuon (idUser, idSach, ngayThue, trangThai) VALUES\n" +
//                "    (1, 4, '2024-03-29', 0),\n" +
//                "    (2, 5, '2024-03-30', 1),\n" +
//                "    (3, 6, '2024-03-27', 0),\n" +
//                "    (1, 7, '2024-03-31', 0),\n" +
//                "    (1, 8, '2024-03-28', 1);\n");
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
