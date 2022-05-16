package com.example.nhom1_messagemobileapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteMessageFragment extends BottomSheetDialogFragment {
    private String fromUid;
    private String toUid;
    private String messageId;
    private Context context;
    private View view;
    private DatabaseReference refMessage;

    public DeleteMessageFragment(Context context, String fromUid, String toUid, String messageId) {
        this.context = context;
        this.messageId = messageId;
        this.fromUid = fromUid;
        this.toUid = toUid;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delete_message, container, false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        refMessage = database.getReference("message");

        Button btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa tin nhắn")
                    .setMessage("Bạn có chắc chắn muốn xóa tin nhắn này?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            refMessage.child(toUid).child(messageId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        refMessage.child(fromUid).child(messageId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                                                    dismiss();
                                                }else{
                                                    Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }else{
                                        Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });
        return view;
    }
}