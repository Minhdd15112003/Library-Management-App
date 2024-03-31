package ph25260.fpoly.asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.dao.DaoLogin;
import ph25260.fpoly.asm.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {
    private final Context context;
    private final ArrayList<User> list;
    DaoLogin daoLogin;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
        daoLogin = new DaoLogin(context);
    }

    @NonNull
    @Override
    public UserAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_list_user, null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewholder holder, int position) {
        holder.txtMasv.setText(list.get(position).getId() + "");
        holder.txtEmail.setText(list.get(position).getEmail());
        holder.txtName.setText(list.get(position).getUsername());
        holder.txtPass.setText(list.get(position).getPassword());
        holder.txtNumberPhome.setText(list.get(position).getNumberphone());
        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(position));
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showDialogDelete(list.get(position));
            }
        });
    }

    private void showDialogUpdate(User user) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_user, null);
        TextView txtEmail = view.findViewById(R.id.txtAddEmailDialog);
        TextView txtName = view.findViewById(R.id.txtAddUsernameDialog);
        TextView txtPass = view.findViewById(R.id.txtAddPasswordDialog);
        TextView txtNumberPhome = view.findViewById(R.id.txtAddNumberPhoneDialog);
        Button btnThem = view.findViewById(R.id.btnthemA);
        txtEmail.setText(user.getEmail());
        txtName.setText(user.getUsername());
        txtPass.setText(user.getPassword());
        txtNumberPhome.setText(user.getNumberphone());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String name = txtName.getText().toString();
                String pass = txtPass.getText().toString();
                String numberphone = txtNumberPhome.getText().toString();
                if (email.isEmpty() && name.isEmpty() && pass.isEmpty() && numberphone.isEmpty()) {
                    txtEmail.setError("Email không được để trống");
                    txtName.setError("Tên không được để trống");
                    txtPass.setError("Mật khẩu không được để trống");
                    txtNumberPhome.setError("Số điện thoại không được để trống");
                    return;
                }

                // Update the user object with the new values
                user.setEmail(email);
                user.setUsername(name);
                user.setPassword(pass);
                user.setNumberphone(numberphone);

                if (daoLogin.update(user)) {
                    list.clear();
                    list.addAll(daoLogin.getAllUsers());
                    notifyDataSetChanged();
                    Toast.makeText(context, "cập nhật thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showDialogDelete(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (daoLogin.deleteUser(user.getId())) {
                    list.clear();
                    list.addAll(daoLogin.getAllUsers());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtMasv;
        TextView txtEmail;
        TextView txtName;
        TextView txtPass;
        TextView txtNumberPhome;
        ImageView imgUpdate;
        ImageView imgDelete;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtMasv = itemView.findViewById(R.id.txtMasv);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtName = itemView.findViewById(R.id.txtName);
            txtPass = itemView.findViewById(R.id.txtPass);
            txtNumberPhome = itemView.findViewById(R.id.txtNumberPhome);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }

    public void reloadData() {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
