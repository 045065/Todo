package com.example.todo.save;

import com.example.todo.data.TodoData;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SavePresenterTest {
    @Mock
    private SaveContract.View mSaveView;

    @Mock
    private TodoData mFakeTodoData;

    private SavePresenter mSavePresenter;

    @Before
    public void setupSavePresenter() {
        MockitoAnnotations.initMocks(this);

        mSavePresenter = givenTasksPresenter();

        mFakeTodoData = new TodoData("Key1", "Title1", "Content1", false);

        when(mSaveView.isActive()).thenReturn(true);
    }

    private SavePresenter givenTasksPresenter() {
        return new SavePresenter(mSaveView, mFakeTodoData);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        mSavePresenter = givenTasksPresenter();

        verify(mSaveView).setPresenter(mSavePresenter);
    }
}
