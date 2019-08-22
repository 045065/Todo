package com.example.todo.data;

import com.example.todo.R;
import com.example.todo.application.TodoApplication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Firebase管理のTodoデータ
 */
public class TodoData implements Serializable {
    private static final long serialVersionUID = 819309444244570013L;

    public static final int USERS = R.string.todo_data_users;
    public static final int TITLE = R.string.todo_data_title;
    public static final int CONTENT = R.string.todo_data_content;
    public static final int IS_COMPLETED = R.string.todo_data_isCompleted;

    private String mFirebaseKey;
    private String mTitle;
    private String mContent;
    private Boolean mCompleted;

    public TodoData(final String key, final String title, final String content, final Boolean completed) {
        mFirebaseKey = key;
        mTitle = title;
        mContent = content;
        mCompleted = completed;
    }

    public String getFirebaseKey() {
        return mFirebaseKey;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setTitle(final String title) {
        mTitle = title;
    }

    public void setContent(final String content) {
        mContent = content;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> todoMap = new HashMap<>();
        todoMap.put(TodoApplication.getContext().getString(TITLE), mTitle);
        todoMap.put(TodoApplication.getContext().getString(CONTENT), mContent);
        todoMap.put(TodoApplication.getContext().getString(IS_COMPLETED), mCompleted);
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(mFirebaseKey, todoMap);
        return keyMap;
    }
}
