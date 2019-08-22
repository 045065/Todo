package com.example.todo.todo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo.application.TodoApplication;
import com.example.todo.data.TodoData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.todo.data.TodoData.CONTENT;
import static com.example.todo.data.TodoData.IS_COMPLETED;
import static com.example.todo.data.TodoData.TITLE;
import static com.example.todo.data.TodoData.USERS;

/**
 * Todo一覧表示用Presenter
 */
public class TodoListPresenter implements TodoListContract.Presenter {
    private static final String LOG_TAG = "TodoListPresenter";
    private final TodoListContract.View mTodoView;

    /**
     * Firebase ユーザー別情報
     */
    private FirebaseUser mUser;
    private String mUid;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    public TodoListPresenter(@NonNull final TodoListContract.View todoView) {
        mTodoView = todoView;
        mTodoView.setPresenter(this);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUid = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(TodoApplication.getContext().getString(USERS)).child(mUid);

        // Firebaseとの同期リスナー
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            // データ読込の際はリスナー内で
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {
                Log.d(LOG_TAG, "onChildAdded:" + dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                if (key == null) {
                    return;
                }

                mTodoView.addTodo(createEventTodoData(dataSnapshot));
            }

            @Override
            public void onChildChanged(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {
                Log.d(LOG_TAG, "onChildChanged:" + dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                if (key == null) {
                    return;
                }

                mTodoView.changeTodo(createEventTodoData(dataSnapshot));
            }

            @Override
            public void onChildRemoved(@NonNull final DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "onChildRemoved:" + dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                if (key == null) {
                    return;
                }

                mTodoView.removeTodo(key);
            }

            @Override
            public void onChildMoved(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {
            }
        });
    }

    /**
     * DataSnapshotからTodoデータ作成
     *
     * @param dataSnapshot
     * @return
     */
    private TodoData createEventTodoData(final DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        Context context = TodoApplication.getContext();
        String title = (String) dataSnapshot.child(context.getString(TITLE)).getValue();
        String content = (String) dataSnapshot.child(context.getString(CONTENT)).getValue();
        Boolean isCompleted = (Boolean) dataSnapshot.child(context.getString(IS_COMPLETED)).getValue();
        return new TodoData(key, title, content, isCompleted);
    }

    @Override
    public void start() {
    }

    @Override
    public void logoutAccount() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        mTodoView.showLoginUi();
    }

    @Override
    public void completeTodo(final TodoData toDoData) {
        mDatabaseReference.child(toDoData.getFirebaseKey()).removeValue();
    }
}