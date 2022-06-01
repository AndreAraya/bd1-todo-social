package tec.bd.repository.inmemdb;

import tec.bd.todo.Status;
import tec.bd.todo.TodoRecord;
import tec.bd.todo.repository.TodoRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TodoRepositoryListImpl implements TodoRepository {

    private List<TodoRecord> todoData;

    public TodoRepositoryListImpl(List<TodoRecord> todoData) {
        this.todoData = todoData;
    }

    @Override
    public List<TodoRecord> findAll() {
        return this.todoData;
    }

    @Override
    public List<TodoRecord> findAll(Status status) {
        return null;
    }

    @Override
    public TodoRecord findById(String id) {
        var todoRecord = this.todoData
                .stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        return todoRecord.orElse(null);
    }

    @Override
    public TodoRecord save(TodoRecord todoRecord) {
        todoRecord.setId(UUID.randomUUID().toString());
        this.todoData.add(todoRecord);
        return todoRecord;
    }

    @Override
    public void remove(String id) {

        var todoRecord = this.findById(id);
        // TODO: todoRecord podria devolver nulo y esto podria causar NullPointerException. Poner if/else para validar
        this.todoData.remove(todoRecord);
    }

    /**
     *
     *  TodoRecord:
     *   id: 2
     *   title: desayuno
     *
     *  TodoRecord:
     *   id: 2
     *   title: cena
     *
     * @param todoRecord
     * @return
     */
    @Override
    public TodoRecord update(TodoRecord todoRecord) {
        // 1. buscar el id del todoRecord
        // 2. borrar de la lista el todoRecord "viejo"
        // 3. insertar el todoRecord
        return null;
    }

    @Override
    public List<TodoRecord> findByPatternInTitle(String textToSearch) {
        return null;
    }

    @Override
    public List<TodoRecord> findByBetweenStartDates(Date startDate, Date endDate) {
        return null;
    }
}
