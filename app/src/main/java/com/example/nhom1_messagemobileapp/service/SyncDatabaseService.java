package com.example.nhom1_messagemobileapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.nhom1_messagemobileapp.R;
import com.example.nhom1_messagemobileapp.dao.MessageSqlDAO;
import com.example.nhom1_messagemobileapp.dao.UserSqlDAO;
import com.example.nhom1_messagemobileapp.database.Database;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.converter.TimestampConverter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SyncDatabaseService extends IntentService {

    private DatabaseReference refMessage;
    private DatabaseReference refUser;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.nhom1_messagemobileapp.service.action.FOO";
    private static final String ACTION_BAZ = "com.example.nhom1_messagemobileapp.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.nhom1_messagemobileapp.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.nhom1_messagemobileapp.service.extra.PARAM2";
    private FirebaseDatabase firebaseDatabase;
    private String uid;
    private Database sqlDatabase;
    private MessageSqlDAO messageSqlDAO;
    private UserSqlDAO userSqlDAO;
    List<User> users = new ArrayList<>();
    private MediaPlayer mediaPlayer;

    public SyncDatabaseService() {
        super("SyncDatabaseService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SyncDatabaseService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SyncDatabaseService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("intent", intent.toString());
        if (intent == null) {
            return;
        }

        sqlDatabase = Room.databaseBuilder(this, Database.class, "mydb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        messageSqlDAO = sqlDatabase.getMessageSqlDAO();
        userSqlDAO = sqlDatabase.getUserSqlDAO();

        firebaseDatabase = FirebaseDatabase.getInstance();
        refMessage = firebaseDatabase.getReference("message");
        refUser = firebaseDatabase.getReference("user");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.nhac_chuong);

        sync();
    }

    public void sync(){
        // sync user

        syncUsers();
        // sync messages
        syncMessages();
    }

    public  void syncMessages(){
        refMessage.child(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String fromUid = snapshot.child("fromUid").getValue(String.class);
                String toUid = snapshot.child("toUid").getValue(String.class);
                if(!userSqlDAO.checkExits(fromUid)){
                    refUser.child(fromUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User friend = new User(snapshot);
                            userSqlDAO.insertOrUpdate(friend);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
                if(!userSqlDAO.checkExits(toUid)){
                    refUser.child(toUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User friend = new User(snapshot);
                            userSqlDAO.insertOrUpdate(friend);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }

                if(!messageSqlDAO.checkExits(snapshot.getKey())){
                    Log.e("add message", snapshot.toString());
//                    Message message = new Message(snapshot);
                    Message message = snapshot.getValue(Message.class);
                    Log.e("->>>>>>", message.toString());
                    messageSqlDAO.insert(message);
                    if(message.getToUid().equals(uid)){
                        mediaPlayer.start();
                    }
                    Log.e("messagesssssss", messageSqlDAO.findAll().toString());
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("onChildChanged", snapshot.toString());
                if(messageSqlDAO.checkExits(snapshot.getKey())){
                    Log.e("change message", snapshot.toString());
                    Message message = snapshot.getValue(Message.class);
                    messageSqlDAO.update(message);
                    Log.e("messagesssssss", messageSqlDAO.findAll().toString());
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.e("onChildRemoved", snapshot.toString());
                if(messageSqlDAO.checkExits(snapshot.getKey())){
                    Log.e("delete message", snapshot.toString());
                    Message message = snapshot.getValue(Message.class);
                    messageSqlDAO.delete(message);
                    Log.e("messagesssssss", messageSqlDAO.findAll().toString());
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void syncUsers(){
        List<User> users = userSqlDAO.findAll();
        users.forEach(user -> {
            refUser.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userFirebase = new User(snapshot);
                    userSqlDAO.update(userFirebase);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }




    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}