package tec.bd.todo;


import org.junit.jupiter.api.Test;
import tec.bd.todo.repository.TodoRepository;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

public class TodoTest {

    @Test
    public void addTodoRecord() throws Exception {

        TodoRecord todoRecord = mock(TodoRecord.class);
        var todoRepository = mock(TodoRepository.class);
        given(todoRecord.getId()).willReturn(UUID.randomUUID().toString());
        given(todoRecord.getDescription()).willReturn("Tomar desayuno");
        given(todoRepository.save(any(TodoRecord.class))).willReturn(todoRecord);

        Todo todo = new Todo(todoRepository);
        TodoRecord newRecord = new TodoRecord("Tomar desayuno");
        var actual = todo.addTodoRecord(newRecord);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getDescription()).isEqualTo("Tomar desayuno");
    }

    @Test
    public void listTodos() throws Exception {

        TodoRecord desayuno = new TodoRecord("Tomar desayuno");
        TodoRecord almuerzo = new TodoRecord("Hacer almuerzo");

        var todoRepository = mock(TodoRepository.class);
        given(todoRepository.findAll()).willReturn(List.of(desayuno, almuerzo));

        Todo todo = new Todo(todoRepository);

        var actual = todo.getAll();

        assertThat(actual.size()).isEqualTo(2);
    }

//    @Test
//    public void deleteTodo() throws Exception {
//
//        TodoRecord desayuno = mock(TodoRecord.class);
//        TodoRecord almuerzo = mock(TodoRecord.class);
//        var todoRepository = mock(TodoRepository.class);
//
//        Todo todo = new Todo(todoRepository);
//        todo.add(desayuno);
//        todo.add(almuerzo);
//        //todo.delete(desayuno);
//
//        verify(todoRepository, times(1)).remove(desayuno);
//    }


    @Test
    public void getById() throws Exception {

        TodoRecord todoRecord = mock(TodoRecord.class);
        var todoRepository = mock(TodoRepository.class);
        given(todoRecord.getId()).willReturn(UUID.randomUUID().toString());
        given(todoRecord.getDescription()).willReturn("Tomar desayuno");
        given(todoRepository.findById(anyString())).willReturn(todoRecord);

        Todo todo = new Todo(todoRepository);

        var actual = todo.getById(todoRecord.getId());

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(todoRecord.getId());
    }

    @Test
    public void filterByInProgress() {
        TodoRecord desayuno = mock(TodoRecord.class);
        TodoRecord almuerzo = mock(TodoRecord.class);
        TodoRecord cena = mock(TodoRecord.class);

        var todoRepository = mock(TodoRepository.class);

        given(desayuno.getStatus()).willReturn(Status.IN_PROGRESS);
        given(almuerzo.getStatus()).willReturn(Status.IN_PROGRESS);
        given(cena.getStatus()).willReturn(Status.BLOCKED);
        given(todoRepository.findAll(any(Status.class))).willReturn(List.of(desayuno, almuerzo));

        Todo todo = new Todo(todoRepository);


        todo.addTodoRecord(desayuno);
        todo.addTodoRecord(almuerzo);
        todo.addTodoRecord(cena);

        var actual = todo.getAll(Status.IN_PROGRESS);

        assertThat(actual).containsExactly(desayuno, almuerzo);
    }

    @Test
    public void filterByFinished() {

        TodoRecord desayuno = mock(TodoRecord.class);
        TodoRecord almuerzo = mock(TodoRecord.class);
        TodoRecord cena = mock(TodoRecord.class);

        var todoRepository = mock(TodoRepository.class);

        given(desayuno.getStatus()).willReturn(Status.FINISHED);
        given(almuerzo.getStatus()).willReturn(Status.FINISHED);
        given(cena.getStatus()).willReturn(Status.BLOCKED);
        given(todoRepository.findAll(any(Status.class))).willReturn(List.of(desayuno, almuerzo));

        Todo todo = new Todo(todoRepository);

        todo.addTodoRecord(desayuno);
        todo.addTodoRecord(almuerzo);
        todo.addTodoRecord(cena);

        var actual = todo.getAll(Status.FINISHED);

        assertThat(actual).containsExactly(desayuno, almuerzo);

//        Todo todo = new Todo();
//        TodoRecord desayuno = new TodoRecord("Tomar desayuno");
//        TodoRecord almuerzo = new TodoRecord("Hacer almuerzo");
//        TodoRecord cena = new TodoRecord("Hacer cena");
//        desayuno.setStatus(Status.FINISHED);
//        almuerzo.setStatus(Status.FINISHED);
//        todo.add(desayuno);
//        todo.add(almuerzo);
//        todo.add(cena);
//
//        var actual = todo.getAll(Status.FINISHED);
//
//        assertThat(actual.size()).isEqualTo(2);
//        assertThat(actual).containsExactly(desayuno, almuerzo);
    }
}
