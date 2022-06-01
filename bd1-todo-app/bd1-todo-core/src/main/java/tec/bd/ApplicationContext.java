package tec.bd;

import tec.bd.todo.Todo;
import tec.bd.todo.TodoRecord;
import tec.bd.todo.repository.TodoRepository;
//import tec.bd.todo.repository.TodoRepositoryListImpl;

import java.util.ArrayList;
import java.util.List;

public class ApplicationContext {


    private static List<TodoRecord> todoRecordList = new ArrayList<>();
    private TodoRepository todoRepository;

    private Todo todo;

    private ApplicationContext() {

    }

    public static ApplicationContext init() {
        return init(null);
    }

    public static ApplicationContext init(TodoRepository todoRepository) {
        var appContext = new ApplicationContext();
        initTodoRepository(appContext, todoRepository);
        appContext.todo = initTodo(appContext.todoRepository);
        return appContext;
    }

    private static void initTodoRepository(ApplicationContext appContext, TodoRepository todoRepository) {
        if(null != todoRepository) {
            appContext.todoRepository = todoRepository;
        }
//        } else {
//            appContext.todoRepository = new TodoRepositoryListImpl(todoRecordList);
//        }
    }

    private static Todo initTodo(TodoRepository todoRepository) {
        return new Todo(todoRepository);
    }

    public Todo getTodo() {
        return this.todo;
    }
}
