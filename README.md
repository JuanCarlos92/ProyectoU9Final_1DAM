# Proyecto Final de Programación - Gestión de Departamentos

## Descripción

Este proyecto es parte de la tarea final para la asignatura de Programación en 1º de DAM. La aplicación gestiona departamentos en una base de datos MySQL. Permite realizar operaciones de inserción, modificación, eliminación y consulta de departamentos, así como la validación de campos de entrada en la interfaz gráfica.

## Estructura del Proyecto

El proyecto consta de varias clases que manejan la lógica de la aplicación y la conexión a la base de datos. Las principales clases del proyecto son:

- **CamposVaciosJFrame**: Contiene métodos para controlar si los campos de entrada están vacíos.
- **Departamento**: Representa la entidad Departamento con sus atributos (código, nombre, ID de localización, ID de manager).
- **GestorBD**: Clase encargada de la conexión a la base de datos y la realización de operaciones CRUD sobre la tabla `Departamentos`.
- **VentanaUI**: Interfaz gráfica que permite al usuario interactuar con la base de datos mediante formularios.

## Funcionalidades

La aplicación permite realizar las siguientes operaciones:

- **Insertar**: Agregar un nuevo departamento a la base de datos.
- **Modificar**: Modificar los detalles de un departamento existente.
- **Eliminar**: Eliminar un departamento de la base de datos.
- **Consultar**: Consultar todos los departamentos registrados.
- **Validación de Campos**: Antes de insertar o modificar datos, se validan los campos para asegurarse de que no están vacíos.

## Requisitos

- JDK 22 o superior
- Base de datos MySQL en funcionamiento
- Conector JDBC para MySQL
- Netbeans

## Instalación

1. Clona este repositorio:
   ```bash
   git clone https://github.com/JuanCarlos92/ProyectoU9Final_1DAM.git
   
2. Abre el proyecto en NetBeans

3. Configura la base de datos MySQL con la siguiente estructura:
   ```bash
   CREATE DATABASE BaseDatos;
   USE BaseDatos;
   CREATE TABLE Departamentos (
     codigo INT PRIMARY KEY,
     nombre VARCHAR(255),
     id_localizacion INT,
     id_manager INT
   );
   ```
4. Asegúrate de que el conector JDBC para MySQL esté instalado y configurado en tu proyecto.

5. Ejecuta la clase VentanaUI para iniciar la interfaz gráfica.

## Uso
Al iniciar la aplicación, se presentará una interfaz gráfica con los siguientes elementos:

- **TextFields**: para ingresar datos de código, nombre, ID de localización e ID de manager.
- **Botones**: para realizar las operaciones de insertar, modificar, eliminar y consultar.
- **Tabla**: que muestra los departamentos registrados en la base de datos.
- 
## Funcionalidades Detalladas
- **Insertar**: Al hacer clic en el botón "Insertar", se valida que los campos no estén vacíos. Si son válidos, se inserta un nuevo departamento en la base de datos.
- **Modificar**: Al hacer clic en el botón "Modificar", se consulta un departamento por su código. Si se encuentra, se permite modificar los campos de nombre, localización y manager.
- **Eliminar**: Al hacer clic en el botón "Eliminar", se solicita la confirmación del usuario para eliminar un departamento basado en su código.
- **Consultar**: Al hacer clic en el botón "Consultar", se muestran todos los departamentos registrados en la base de datos en una tabla.


