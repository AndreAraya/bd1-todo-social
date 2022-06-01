package tec.bd.todo;

import tec.bd.todo.repository.TodoRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Todo {

    private TodoRepository todoRepository;

    public Todo(TodoRepository todoRepo) {
        this.todoRepository = todoRepo;
    }

    public List<TodoRecord> getAll() {
        return this.todoRepository.findAll();
    }

    public List<TodoRecord> getAll(Status status) {
        return this.todoRepository.findAll(status);
    }

    public TodoRecord getById(String id) {
        // TODO: validar que el id no sea nulo y si es nulo lanzar una excepcion
        return this.todoRepository.findById(id);
    }

    public TodoRecord addTodoRecord(TodoRecord record) {
        Objects.requireNonNull(record);
        // Si el titulo es nulo, devolver exception
        if(null == record.getStartDate()) {
            record.setStartDate(new Date());
        }
        record.setId(UUID.randomUUID().toString());
        record.setStatus(Status.NEW);
        return this.todoRepository.save(record);
    }

    public TodoRecord update(TodoRecord record) {
        Objects.requireNonNull(record);

        // TODO: si el todoRecord.Id no existe, lanzar exception
        // el registro a actualizar tiene que existir.

        // Si el titulo es nulo, devolver exception
        if(null == record.getStartDate()) {
            record.setStartDate(new Date());
        }

        return this.todoRepository.update(record);
    }

    public void deleteTodoRecord(String id) {

        // TODO: buscar si el record existe, y si existe borrarlo
        this.todoRepository.remove(id);
    }

    public List<TodoRecord> getStartDateRange(Date startDate, Date endDate) {
        // TODO: validamos que startDAte y endDate no sean nulos
        // TODO: validamos que el endDate es mayor o igual que el startDate
        // TODO: validamos que las fechas tienen que estar en un rango definido por alguien
        return this.todoRepository.findByBetweenStartDates(startDate, endDate);
    }

    public List<TodoRecord> searchInTitle(String textToSearch) {
        // TODO: validar que el textToSearch no sea nulo ni vacio
        return this.todoRepository.findByPatternInTitle(textToSearch);
    }

    public TodoRecord updateTodoRecord(TodoRecord todoRecord) {
        // TODO: que el ID del todoRecord Exista
        // TODO: validar que como minimo el todoRecord tenga ID, titulo, startDate y Status
        // TODO: el titulo, startDate, Status no pueden nulos (o vacios)
        return this.todoRepository.update(todoRecord);
    }

}
