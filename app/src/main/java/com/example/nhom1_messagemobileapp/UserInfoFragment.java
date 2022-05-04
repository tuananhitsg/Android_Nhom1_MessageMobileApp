package com.example.nhom1_messagemobileapp;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.entity.User;
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
import com.squareup.picasso.Picasso;

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

    private TextView tvName;
    private ImageView imgAvatar;
    private Button btnDarkMode, btnEditInfo, btnChangePassword, btnLogout;
    private String uid;
    private User theUser;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        // Inflate the layout for this fragment

//        tìm đối tượng trong view
        tvName = view.findViewById(R.id.userInfo_tvName);
        imgAvatar = view.findViewById(R.id.userInfo_imgAvatar);
        btnDarkMode = view.findViewById(R.id.userInfo_btnDarkMode);
        btnEditInfo = view.findViewById(R.id.userInfo_btnEditInfo);
        btnChangePassword = view.findViewById(R.id.userInfo_btnChangePassword);
        btnLogout = view.findViewById(R.id.userInfo_btnLogout);

//        cấu hình firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        myRef = database.getReference("user").child(uid);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

//        set value
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                theUser = dataSnapshot.getValue(User.class);
                tvName.setText(theUser.getName());
                Picasso.get().load(theUser.getAvatar()).into(imgAvatar);
                imgAvatar.setClipToOutline(true);
                progressBar.setVisibility(View.GONE);
                imgAvatar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(), "Kết nối internet không ổn định", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

//        bắt sự kiện button
        btnDarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Đang phát triển", Toast.LENGTH_LONG).show();
            }
        });

        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUpdateUserInfo = new Intent(getActivity(), UpdateUserInfo.class);
                intentUpdateUserInfo.putExtra("user", theUser);
                intentUpdateUserInfo.putExtra("uid", uid);
                getActivity().startActivity(intentUpdateUserInfo);
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

    private void openChangePasswordDialog(int gravity) {
        final Dialog dialog = createDialog(gravity, R.layout.layout_dialog_change_password);

        if (dialog == null)
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
//                xác thực email + password vừa được nhập
                AuthCredential credential = EmailAuthProvider.getCredential(theUser.getEmail(), password);
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Cập nhật mật khẩu thành công", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(getActivity(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
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