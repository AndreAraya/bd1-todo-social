package tec.bd;


import tec.bd.todo.TodoRecord;

public class App
{
    public static void main(String... args )
    {

        var appContext = ApplicationContext.init();
        var todo = appContext.getTodo();

        todo.addTodoRecord(new TodoRecord("Desayuno"));
        todo.addTodoRecord(new TodoRecord("Almuerzo"));
        todo.addTodoRecord(new TodoRecord("Cena"));

        var todos = todo.getAll();

        for (TodoRecord r: todos) {
            System.out.println(r.getId() + " - " + r.getDescription() + " - " + r.getStatus());
        }
    }
}
