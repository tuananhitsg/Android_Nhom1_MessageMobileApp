package com.example.nhom1_messagemobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText edtPassword, edtEmail;
    Button btnLogin, btnToRegisterPage,  btnForgotPass;
    private Context context;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser mUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //khai bao
        initUi();

        //lưu đăng nhập

        if (user != null) {
            // User is signed in
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d("bay màuuuuuuuu", "onAuthStateChanged:signed_out");
        }

        btnToRegisterPage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                boolean result = checkData(email, password);
                if (result) {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        SharedPreference sp = SharedPreference.getInstance(LoginActivity.this);
                                        sp.saveData("uid", task.getResult().getUser().getUid());

                                        Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        WelcomeActivity.fa.finish();
                                    } else {
                                        Toast.makeText(context, "Tài khoản/ mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void initUi() {
        context = this;
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        btnToRegisterPage = findViewById(R.id.btn_to_register_page);
        edtEmail = findViewById(R.id.login_edtEmailInput);
        edtPassword = findViewById(R.id.login_edtPassInput);
        btnLogin = findViewById(R.id.btnLogin);

        btnForgotPass = findViewById(R.id.btnForgotPass);
//        btnCancel = findViewById(R.id.dialogResetPassword_btnCancel);
//        btnSubmit = findViewById(R.id.dialogResetPassword_btnSubmit);
    }

    public boolean checkData(String email, String password) {
        if (email.isEmpty() || email.length() <= 0) {
            Toast.makeText(context, "Chưa nhập email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty() || password.length() <= 0) {
            Toast.makeText(context, "Chưa nhập mật khẩu ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    private void onClickForgotPass(String gmail) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String emailAddress = gmail;
//
//        auth.sendPasswordResetEmail(emailAddress)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    private void openDialog(){
        ResetPassDialog resetPassDialog = new ResetPassDialog();
        resetPassDialog.show(getSupportFragmentManager(),"Dialog");
    }


}