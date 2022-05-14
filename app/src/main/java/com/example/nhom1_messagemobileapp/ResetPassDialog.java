package com.example.nhom1_messagemobileapp;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassDialog extends AppCompatDialogFragment {
    private EditText edtEmailConfirm;
   // private ResetPassListener listener;
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_reset_pass,null);
        edtEmailConfirm = view.findViewById(R.id.dialogResetPassword_edtConfirmMail);

        builder.setView(view).setTitle("Đặt lại mật khẩu").setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String gmail = edtEmailConfirm.getText().toString().trim();
                onClickForgotPass(gmail);
                Toast.makeText(getActivity(),"Vui lòng kiểm tra email để đổi mật khẩu",Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
    private void onClickForgotPass(String gmail) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = gmail;

        auth.sendPasswordResetEmail(emailAddress);
    }

}
