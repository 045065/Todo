package com.example.todo.todo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodoListPresenterTest {

    @Mock
    private TodoListContract.View mTodoListView;

    private TodoListPresenter mTodoListPresenter;

    @Before
    public void setupodoListPresenter() {
        MockitoAnnotations.initMocks(this);

        mTodoListPresenter = givenTasksPresenter();

        when(mTodoListView.isActive()).thenReturn(true);
    }

    private TodoListPresenter givenTasksPresenter() {
        return new TodoListPresenter(mTodoListView);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        mTodoListPresenter = givenTasksPresenter();

        verify(mTodoListView).setPresenter(mTodoListPresenter);
    }
}
