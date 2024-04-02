package ph25260.fpoly.asm.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ph25260.fpoly.asm.database.Dbheper;
import ph25260.fpoly.asm.model.PhieuMuon;
import ph25260.fpoly.asm.model.Sach;
import ph25260.fpoly.asm.model.Top;

public class DaoPhieuMuon {
    private Context context;
    private Dbheper dbheper;
    private SQLiteDatabase db;

    public DaoPhieuMuon(Context context) {
        dbheper = new Dbheper(context);
        db = dbheper.getWritableDatabase();
    }

    public ArrayList<PhieuMuon> getAll() {
        ArrayList<PhieuMuon> phieuMuonArrayList = new ArrayList<>();
        Cursor cursor = db.query("phieumuon", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setId(cursor.getInt(0));
                phieuMuon.setIdUser(cursor.getInt(1));
                phieuMuon.setIdSach(cursor.getInt(2));
                phieuMuon.setNgayThue(cursor.getString(3));
                phieuMuon.setTrangThai(cursor.getInt(4));
                phieuMuonArrayList.add(phieuMuon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return phieuMuonArrayList;
    }

    public boolean insert(PhieuMuon phieuMuon) {
        db = dbheper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUser", phieuMuon.getIdUser());
        contentValues.put("idSach", phieuMuon.getIdSach());
        contentValues.put("ngayThue", phieuMuon.getNgayThue());
        contentValues.put("trangThai", phieuMuon.getTrangThai());
        long row = db.insert("phieumuon", null, contentValues);
        return (row > 0);
    }

    public boolean update(PhieuMuon phieuMuon) {
        db = dbheper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUser", phieuMuon.getIdUser());
        contentValues.put("idSach", phieuMuon.getIdSach());
        contentValues.put("ngayThue", phieuMuon.getNgayThue());
        contentValues.put("trangThai", phieuMuon.getTrangThai());
        long row = db.update("phieumuon", contentValues, "id=?", new String[]{String.valueOf(phieuMuon.getId())});
        return (row > 0);
    }

    public boolean delete(int id) {
        long row = db.delete("phieumuon", "id=?", new String[]{String.valueOf(id)});
        return (row > 0);
    }

    public List<Top> getTop10() {
        List<Top> list = new ArrayList<>();
        String query = "SELECT idSach, COUNT(idSach) as soLuong " +
                "FROM phieumuon " +
                "GROUP BY idSach " +
                "ORDER BY soLuong DESC " +
                "LIMIT 10";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Top top = new Top();
                top.setTenSach(cursor.getString(0));
                top.setSoLuong(cursor.getInt(1));
                list.add(top);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public int getDoanhThu(String fromDate, String toDate ) {
        int doanhThu = 0;
        String query = "SELECT SUM(giasach) FROM qlsach WHERE id = (SELECT idsach FROM phieumuon WHERE ngaythue BETWEEN ? AND ?)";
        Cursor cursor = db.rawQuery(query, new String[]{fromDate, toDate});
        if (cursor.moveToFirst()) {
            doanhThu = cursor.getInt(0);
        }
        cursor.close();
        return doanhThu;
    }
}
