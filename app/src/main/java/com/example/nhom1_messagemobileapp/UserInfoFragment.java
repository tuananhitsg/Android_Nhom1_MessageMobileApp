package com.example.nhom1_messagemobileapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfoFragment newInstance(String param1, String param2) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView tvName, tvHandleName;
    private ImageView imgAvatar;
    private Button btnDarkMode, btnChangeUsername, btnEditInfo, btnChangePassword, btnLogout;
    private String uid = "6kBO5yFQu1Y355mxKuc1YSaLtSZ2";
    private String handleName;
    private String email;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        // Inflate the layout for this fragment

        tvName = view.findViewById(R.id.userInfo_tvName);
        tvHandleName = view.findViewById(R.id.userInfo_tvHandleName);
        imgAvatar = view.findViewById(R.id.userInfo_imgAvatar);
        btnDarkMode = view.findViewById(R.id.userInfo_btnDarkMode);
        btnChangeUsername = view.findViewById(R.id.userInfo_btnChangeUsername);
        btnEditInfo = view.findViewById(R.id.userInfo_btnEditInfo);
        btnChangePassword = view.findViewById(R.id.userInfo_btnChangePassword);
        btnLogout = view.findViewById(R.id.userInfo_btnLogout);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user").child(uid);

        imgAvatar.setImageResource(R.drawable.logo);
        imgAvatar.setClipToOutline(true);

        handleName = "phamdangdan";

        tvName.setText("Phạm Đăng Đan");
        tvHandleName.setText(handleName);

        btnDarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Đang phát triển", Toast.LENGTH_LONG).show();
            }
        });

        btnChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangeHandleNameDialog(Gravity.CENTER, handleName);
            }
        });

        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Đang phát triển", Toast.LENGTH_LONG).show();

            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangePasswordDialog(Gravity.CENTER);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });

        return view;
    }

    private Dialog createDialog(int gravity, int layoutId) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutId);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = gravity;
            window.setAttributes(windowAttributes);
    //        click ra ngoài để tắt
            dialog.setCancelable(true);
            return dialog;
        }
        return null;
    }

    private void openChangeHandleNameDialog(int gravity, String oldHandleName) {
        final Dialog dialog = createDialog(gravity, R.layout.layout_dialog_change_handle_name);

        if(dialog == null)
            return;

        EditText editHandlerName = dialog.findViewById(R.id.dialogChangeHandleName_edt);
        Button btnSave = dialog.findViewById(R.id.dialogChangeHandleName_btnSave);
        Button btnCancel = dialog.findViewById(R.id.dialogChangeHandleName_btnCancel);

        editHandlerName.setText(oldHandleName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String handleName = editHandlerName.getText().toString();
                Toast.makeText(getActivity(), handleName, Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void openChangePasswordDialog(int gravity) {
        final Dialog dialog = createDialog(gravity, R.layout.layout_dialog_change_password);

        if(dialog == null)
            return;

        EditText edtPassword = dialog.findViewById(R.id.dialogChangePassword_edtPassword);
        EditText edtNewPassword = dialog.findViewById(R.id.dialogChangePassword_edtNewPassword);
        EditText edtReNewPassword = dialog.findViewById(R.id.dialogChangePassword_edtNewRePassword);
        Button btnSave = dialog.findViewById(R.id.dialogChangePassword_btnSave);
        Button btnCancel = dialog.findViewById(R.id.dialogChangePassword_btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = edtPassword.getText().toString();
                String newPassword = edtNewPassword.getText().toString();
                String reNewPassword = edtReNewPassword.getText().toString();

                Toast.makeText(getActivity(), password, Toast.LENGTH_LONG).show();
                if (password.isEmpty() || password.length() <= 0) {
                    Toast.makeText(getActivity(), "Mật khẩu không được để trống", Toast.LENGTH_LONG).show();
                    return;
                }
                if (newPassword.isEmpty() || newPassword.length() <= 0) {
                    Toast.makeText(getActivity(), "Mật khẩu mới không được để trống", Toast.LENGTH_LONG).show();
                    return;
                }
                if (reNewPassword.isEmpty() || reNewPassword.length() <= 0) {
                    Toast.makeText(getActivity(), "Mật khẩu mới nhập lại không được để trống", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!newPassword.equals(reNewPassword)) {
                    Toast.makeText(getActivity(), "Mật khẩu mới và mật khẩu nhập lại không giống nhau", Toast.LENGTH_LONG).show();
                    return;
                }

                DatabaseReference ref = database.getReference("user").child(uid);
                ref.child("email").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        email = dataSnapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });

//                AuthCredential credential = EmailAuthProvider.getCredential(email, password);
//
//                user.reauthenticate(credential)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(getActivity(), "Cập nhật mật khẩu thành công", Toast.LENGTH_LONG).show();
//                                            } else {
//                                                Toast.makeText(getActivity(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_LONG).show();
//                                            }
//                                        }
//                                    });
//                                } else {
//                                    Toast.makeText(getActivity(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}