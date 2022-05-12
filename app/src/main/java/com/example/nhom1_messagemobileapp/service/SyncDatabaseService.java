package com.example.nhom1_messagemobileapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.example.nhom1_messagemobileapp.HomeFragment;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.utils.converter.TimestampConverter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
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
    private FirebaseDatabase database;
    private String uid;

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
        database = FirebaseDatabase.getInstance();
        refMessage = database.getReference("message");
        refUser = database.getReference("user");
        uid = intent.getExtras().getString("uid");
        Log.e("uid", uid);
        getFriends();
    }

    public void getFriends(){
        Map<String, Message> userLastMessages = new HashMap<>();

        refMessage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Message message = new Message();
                    System.out.println(snapshot);
                    String content = snapshot.child("content").getValue(String.class);
                    String uidFrom = snapshot.child("from").getValue(String.class);
                    String uidTo = snapshot.child("to").getValue(String.class);
                    Long timestamp = snapshot.child("time").getValue(Long.class);
//                    Log.d("date", timestamp.toString());
                    Date time = TimestampConverter.fromTimestamp(timestamp);
                    message.setContent(content);
                    message.setTime(time);
                    message.setFrom(uidFrom);
                    message.setTo(uidTo);
                    if(uidFrom.equals(uid)){
                        userLastMessages.put(uidTo, message);
                    }else if(uidTo.equals(uid)){
                        userLastMessages.put(uidFrom, message);
                    }
                }
                Log.e("friends", userLastMessages.toString());
//                HomeFragment.ShowListUserTask showListUserTask = new HomeFragment.ShowListUserTask(userLastMessages);
//                showListUserTask.execute();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
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