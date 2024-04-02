package ph25260.fpoly.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import ph25260.fpoly.asm.database.Dbheper;
import ph25260.fpoly.asm.model.User;

public class DaoLogin {
    private Dbheper dbheper;
    private SQLiteDatabase db;

    public DaoLogin(Context context) {
        dbheper = new Dbheper(context);
        db = dbheper.getWritableDatabase();
    }

    public User getUserById(int id) {
        Cursor cursor = db.query("login", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setEmail(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setNumberphone(cursor.getString(4));
            return user;
        }
        return null;
    }

    public User getID(int id) {
        List<User> list = getAllUsers();
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public long addUser(String email, String username, String password, String numberphone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("numberphone", numberphone);
        return db.insert("login", null, contentValues);
    }


    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = db.query("login", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setEmail(cursor.getString(1));
                user.setUsername(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setNumberphone(cursor.getString(4));
                userList.add(user);  // Add this line
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    public boolean update(User user) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();//dua du lieu vao database
        values.put("email", user.getEmail());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("numberphone", user.getNumberphone());
        //neu add thanh cong thi tra ve so dong duoc chen vao bang
        long row = db.update("login", values, "id=?", new String[]{String.valueOf(user.getId())});
        return (row > 0);
    }

    public boolean deleteUser(int id) {
        long row = db.delete("login", "id=?", new String[]{String.valueOf(id)});
        return (row > 0);
    }

    public User checkLogin(String email, String password) {
        User user = null;
        Cursor cursor = db.rawQuery("SELECT * FROM login WHERE email = ? AND password = ?", new String[]{email, password});
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setEmail(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setNumberphone(cursor.getString(4));
        }
        cursor.close();
        return user;
    }


}
