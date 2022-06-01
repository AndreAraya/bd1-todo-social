package tec.bd.todo.repository;

import tec.bd.todo.Status;
import tec.bd.todo.TodoRecord;

import java.util.Date;
import java.util.List;

public interface TodoRepository {

    List<TodoRecord> findAll();

    List<TodoRecord> findAll(Status status);

    TodoRecord findById(String id);

    TodoRecord save(TodoRecord todoRecord);

    void remove(String id);

    TodoRecord update(TodoRecord todoRecord);

    List<TodoRecord> findByPatternInTitle(String textToSearch);

    List<TodoRecord> findByBetweenStartDates(Date startDate, Date endDate);


}
