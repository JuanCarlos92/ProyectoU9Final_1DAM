
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Juan Carlos
 */
public class GestorBD {

    private Connection connection;
    private PreparedStatement prepareStatement;
    private ResultSet resultSet;

    //Constructor sin parámetros
    public GestorBD() {

    }

    //Metodo para conectar con la BD
    public void conectar() {
        try {
            //Decir que tipo de bbdd tengo
            Class.forName("com.mysql.jdbc.Driver");
            //Se crea la conexión (url, usuario, pwd)
            connection = DriverManager.getConnection("jdbc:mysql://localhost/BaseDatos", "admin", "admin");

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo para desconectar con la BD
    public void desconectar() {
        try {
            //En caso de que Connection, preparedStatement o ResultSet no sea null se desconecta con la BD
            if (connection != null) {
                connection.close();
            }
            if (prepareStatement != null) {
                prepareStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            InfoshowDialog("Error de desconexión: " + e.getMessage());
        }
    }

    //Metodo para insertar Departamentos
    public void alta(int codigo, String nombre, int id_localizacion, int id_manager) {

        try {
            conectar();
            String resultado;

            //Variable que almacena el INSERT INTO
            //Se le dice a java que en un punto dado las "?" deben ser sustituidas
            String sql = "INSERT INTO Departamentos VALUES (?,?,?,?)";

            //Se Prepara el statement mandandole la variable que contiene el INSERT INTO
            prepareStatement = connection.prepareStatement(sql);

            ////Indicamos cada posicion del placeholder y a que hace referencia
            prepareStatement.setInt(1, codigo);       //1º ?
            prepareStatement.setString(2, nombre);    // 2º ?
            prepareStatement.setInt(3, id_localizacion);  // 3º ?
            prepareStatement.setInt(4, id_manager);   // 4º ?

            //Se realiza el insert into
            prepareStatement.executeUpdate();
            resultado = "Insertado";
            InfoshowDialog(resultado);

        } catch (SQLException e) {
            InfoshowDialog("Error al insertar en el departamento: " + e.getMessage());
        } finally {
            desconectar();
        }

    }

    //Metodo para consultar la BBDD y devolver un arrayList con el resultado de la consulta
    public ArrayList<Departamento> mostrar() {
        //Se crea un arraylist
        ArrayList<Departamento> arrayDeparts = new ArrayList<Departamento>();

        try {
            conectar();
            //Variable que almacena el SELECT
            String sql = "Select * from departamentos";

            //Se prepara la consulta y se ejecuta almacenando esta consulta completa en resultSet
            Statement consulta = connection.createStatement();
            resultSet = consulta.executeQuery(sql);

            //Bucle en el que se recorre linea por linea el resultSet almacenando 
            //este en el arrayList arrayDeparts
            while (resultSet.next()) {
                //Se crea objeto Departamento 
                Departamento depart = new Departamento();

                //Se va añadiendo cada campo de la BD en el objeto depart con sus setters
                depart.setCodigo(resultSet.getInt(1));
                depart.setNombre(resultSet.getString(2));
                depart.setIdLocalizacion(resultSet.getInt(3));
                depart.setIdManager(resultSet.getInt(4));

                //Se añade el objeto al arrayList llamado arrayDeparts
                arrayDeparts.add(depart);
            }
            return arrayDeparts;

        } catch (SQLException e) {
            InfoshowDialog("Error al mostrar departamentos: " + e.getMessage());
            return null;
        } finally {
            desconectar();
        }

    }
    //Metodo para borrar Departamentos
    public Departamento Borrar(int codigo) {
        Departamento depart = new Departamento();
        try {
            //Se llama al metodo consultarCodigoBaseDatos
            depart = consultarCodigoBaseDatos(codigo);
            String resultadoJoptionpanel;
            conectar();
            
            //Si depart (resultado que obtenemos del método) no es null..
            if (depart != null) {
                //Preguntamos si se desea borrar
                int pregunta = PreguntashowDialog("¿Desea borrar el departamento con código " 
                        + codigo + "?", "SI", "NO");

                if (pregunta == 0) {

                    //Variable que almacena el DELETE 
                    String sql = "DELETE FROM Departamentos WHERE codigo =?";

                    //Preparar el Statement para realizar el delete, 
                    //indicandole que el placeholder hace referencia al codigo
                    prepareStatement = connection.prepareStatement(sql);
                    prepareStatement.setInt(1, codigo);

                    //Ejecuta la sentencia
                    prepareStatement.executeUpdate();
                    resultadoJoptionpanel = "Departamento eliminado de la BBDD.";
                    InfoshowDialog(resultadoJoptionpanel);

                } else {
                    resultadoJoptionpanel = "No se ha borrado el Departamento de la BBDD";
                    InfoshowDialog(resultadoJoptionpanel);
                }
            }

        } catch (SQLException e) {
            InfoshowDialog("Error al borrar departamento: " + e.getMessage());

        } finally {
            desconectar();

        }
        return depart;
    }
    
    //Metodo para actualizar Departamentos
    public void actualizar(int codigo, String nombre, int id_localizacion, int id_manager) {

        try {
            conectar();
            String resultado;

            //Variable que almacena el UPDATE 
            String sql = "UPDATE Departamentos SET nombre = ?, id_localizacion = ?, id_manager = ? WHERE codigo = ?";

            //Se prepara el Statement pasandole la variable que contiene el UPDATE e
            //e indicamos cada placeholder haciendo referencia a la BD que sería:
            prepareStatement = connection.prepareStatement(sql);
            
            prepareStatement.setString(1, nombre); //1º placeholder "?" -> nombre = ?
            prepareStatement.setInt(2, id_localizacion); //2º placeholder "?" -> id_localizacion = ?
            prepareStatement.setInt(3, id_manager); //3º placeholder "?" -> id_manager = ?
            prepareStatement.setInt(4, codigo); //4º placeholder "?" -> codigo = ?

            //Se realiza el update
            prepareStatement.executeUpdate();
            resultado = "Actualizado.";
            InfoshowDialog(resultado);

        } catch (SQLException e) {
            InfoshowDialog("Error al borrar departamento: " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    //Metodo para comprobar el registro en la BD indicandole el codigo
    public Departamento consultarCodigoBaseDatos(int codigo) {

        Departamento depart = new Departamento();
        String resultado;
        try {
            conectar();

            //Variable para consultar dentro de Departamento el codigo introducido
            String sql = "SELECT * FROM Departamentos WHERE codigo = ?";

            //Se prepara el Statement para realizar la consulta, pasamos la variable con la consulta
            //e indicamos único placeholder haciendo referencia al código
            prepareStatement = connection.prepareStatement(sql);
            
            prepareStatement.setInt(1, codigo); //1º placeholder "?" -> codigo = ?

            //Se ejecuta la consulta 
            resultSet = prepareStatement.executeQuery();
            //Si el el metodo retorna vacio entonce devuelve que no existe
            if (!resultSet.next()) {
                resultado = "Este registro no existe en la BBDD.";
                InfoshowDialog(resultado);
                return null;

            }
            //Se crea un objeto del resultado
            depart.setCodigo(resultSet.getInt(1));
            depart.setNombre(resultSet.getString(2));
            depart.setIdLocalizacion(resultSet.getInt(3));
            depart.setIdManager(resultSet.getInt(4));

            //return el objeto
            return depart;

        } catch (SQLException e) {
            InfoshowDialog("Error al comprobar el departamento: " + e.getMessage());
            return null;
        } finally {
            desconectar();
        }

    }

    public static int PreguntashowDialog(String msg, String option1, String option2) {
        int pregunta = JOptionPane.showOptionDialog(null, msg,
                "Ventana de confirmación", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{option1, option2}, "SI");
        return pregunta;
    }

    public void ErrorshowDialog(String msg) {
        JOptionPane.showMessageDialog(null, msg,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void InfoshowDialog(String msg) {
        JOptionPane.showMessageDialog(null, msg,
                "ÉXITO", JOptionPane.INFORMATION_MESSAGE);
    }

}
