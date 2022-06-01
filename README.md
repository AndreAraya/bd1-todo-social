BD1 Todo App
============

[![Java CI with Maven](https://github.com/martinicr/bd1-todo/actions/workflows/maven.yml/badge.svg?branch=dev)](https://github.com/martinicr/bd1-todo/actions/workflows/maven.yml)

Aplicación de muestra para probar el desarrollo de aplicaciones por medio de Apache Maven y Java.


## Opciones
```bash
mvn clean compile
# se borra el directorio target/ y se compila el código de producción

mvn clean test
# se borra el directorio target/ y se compila el código de producción, se compila el código de pruebas y se ejecutan las pruebas.

mvn clean package
# se borra el directorio target/ y se compila el código de producción, se compila el código de pruebas, se ejecutan las pruebas, se construye el .jar.

mvn clean install
# se borra el directorio target/ y se compila el código de producción, se compila el código de pruebas, se ejecutan las pruebas, se construye el .jar y se instala en el repositorio local de Maven (.m2/repository).

mvn clean install -Dmaven.test.skip=true
# En este se ignoran los pasos relacionados de las pruebas. 
```

Proyecto en .Net

```bash
dotnet new classlib -n BD1.Todo.API.Client -lang "C#"

dotnet new xunit -n BD1.Todo.API.Client.UnitTest -lang "C#"
```

