package tec.bd.repository.inmemdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import tec.bd.todo.TodoRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

public class TodoRepositoryListImplTest {

    TodoRepositoryListImpl repository;

    @BeforeEach
    public void setup() {
        var simpleTodoRecord = mock(TodoRecord.class);

        given(simpleTodoRecord.getId()).willReturn("simple-todo");

        List<TodoRecord> todoRecordList = new ArrayList<>();
        todoRecordList.add(simpleTodoRecord);
        this.repository = new TodoRepositoryListImpl(todoRecordList);
    }

    @Test
    public void findAll() throws Exception {

        var actual = repository.findAll();

        assertThat(actual).isNotEmpty();
    }

    @Test
    public void findById() throws Exception {
        var actual = repository.findById("simple-todo");

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo("simple-todo");
    }

    @Test
    public void whenIdNotFoundThenNull() throws Exception {

        var actual = repository.findById("no-exists");

        assertThat(actual).isNull();
    }

    @Test
    public void save() throws Exception {

        var todoRecord = new TodoRecord("Desayuno");

//        var todoRecordMock = mock(TodoRecord.class);

//        var previousSize = repository.findAll().size();
        var newTodoRecord = repository.save(todoRecord);
        var actual = repository.findAll();

        assertThat(newTodoRecord).isNotNull();
        assertThat(newTodoRecord.getId()).isNotBlank();
//        assertThat(actual.size()).isGreaterThan(previousSize);
        assertThat(actual).contains(todoRecord);
    }

    @Test
    public void remove() throws Exception {

        repository.remove("simple-todo");
        var actual = repository.findById("simple-todo");

        assertThat(actual).isNull();
    }

}
