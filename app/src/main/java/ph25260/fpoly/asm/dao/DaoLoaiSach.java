package ph25260.fpoly.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ph25260.fpoly.asm.database.Dbheper;
import ph25260.fpoly.asm.model.LoaiSach;
import ph25260.fpoly.asm.model.User;

public class DaoLoaiSach {
    private Dbheper dbheper;
    private SQLiteDatabase db;

    public DaoLoaiSach(Context context) {
        dbheper = new Dbheper(context);
        db = dbheper.getWritableDatabase();
    }

    public ArrayList<LoaiSach> getAll() {
        ArrayList<LoaiSach> loaiSachArrayList = new ArrayList<>();
        Cursor cursor = db.query("loaisach", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setId(cursor.getInt(0));
                loaiSach.setTenloai(cursor.getString(1));
                loaiSachArrayList.add(loaiSach);  // Add this line
            } while (cursor.moveToNext());
        }
        cursor.close();
        return loaiSachArrayList;
    }
    public LoaiSach getLoaiSachById(int id) {
        Cursor cursor = db.query("loaisach", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            LoaiSach loaiSach = new LoaiSach();
            loaiSach.setId(cursor.getInt(0));
            loaiSach.setTenloai(cursor.getString(1));
            return loaiSach;
        }
        return null;
    }

    public long insert(String tenloai) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        return db.insert("loaisach", null, contentValues);
    }

    public boolean update(LoaiSach loaiSach) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();//dua du lieu vao database
        contentValues.put("tenloai", loaiSach.getTenloai());
        long row = db.update("loaisach", contentValues, "id=?", new String[]{String.valueOf(loaiSach.getId())});
        return (row > 0);
    }

    public boolean delete(int id) {
        long row = db.delete("loaisach", "id=?", new String[]{String.valueOf(id)});
        return (row > 0);
    }
}
