package ph25260.fpoly.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ph25260.fpoly.asm.database.Dbheper;
import ph25260.fpoly.asm.model.LoaiSach;
import ph25260.fpoly.asm.model.Sach;

public class DaoSach {
    private Dbheper dbheper;
    private SQLiteDatabase db;

    public DaoSach(Context context) {
        dbheper = new Dbheper(context);
        db = dbheper.getWritableDatabase();
    }

    public ArrayList<Sach> getAll() {
        ArrayList<Sach> sachArrayList = new ArrayList<>();
        Cursor cursor = db.query("qlsach", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Sach sach = new Sach();
                sach.setId(cursor.getInt(0));
                sach.setTensach(cursor.getString(1));
                sach.setGiaSach(cursor.getInt(2));
                sach.setLoaiSachId(cursor.getInt(3));
                sachArrayList.add(sach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sachArrayList;
    }

    public LoaiSach layThongTinById(String id) {
        Cursor cursor = db.query("loaisach", null, "id=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            LoaiSach loaiSach = new LoaiSach();
            loaiSach.setId(cursor.getInt(0));
            loaiSach.setTenloai(cursor.getString(1));
            return loaiSach;
        }
        return null;
    }

    public Sach getSachByID(String id){
        Cursor cursor = db.query("qlsach", null, "id=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            Sach sach = new Sach();
            sach.setId(cursor.getInt(0));
            sach.setTensach(cursor.getString(1));
            sach.setGiaSach(cursor.getInt(2));
            sach.setLoaiSachId(cursor.getInt(3));
            return sach;
        }
        return null;
    }

    public Sach getID(String id) {
        List<Sach> list = getAll();
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public boolean insert(String tensach, String giaSach, int loaiSachId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", tensach);
        contentValues.put("giaSach", giaSach);
        contentValues.put("loaiSachId", loaiSachId);
        long row = db.insert("qlsach", null, contentValues);
        return (row > 0);
    }

    public boolean update(Sach sach) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", sach.getTensach());
        contentValues.put("giaSach", sach.getGiaSach());
        contentValues.put("loaiSachId", sach.getLoaiSachId());
        long row = db.update("qlsach", contentValues, "id=?", new String[]{String.valueOf(sach.getId())});
        return (row > 0);
    }

    public boolean delete(int id) {
        long row = db.delete("qlsach", "id=?", new String[]{String.valueOf(id)});
        return (row > 0);
    }

}
