package co.edu.uniquindio.libreriaspring.dbutils;

import co.edu.uniquindio.libreriaspring.domain.Libro;
import co.edu.uniquindio.libreriaspring.domain.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils implements DB {

    private static final String DATABASE_URL = "jdbc:sqlite:libreria.db";
    private static DBUtils instancia;

    /**
     * Constructor privado para implementar el patrón Singleton
     */
    private DBUtils() {
    }

    /**
     * Obtiene la instancia única de DBUtils
     * @return Instancia de DBUtils
     */
    public static  DBUtils getInstance() {
        if (instancia == null) {
            instancia = new DBUtils();
        }
        return instancia;
    }

    /**
     * Crea la base de datos SQLite y todas las tablas necesarias
     */
    @Override
    public void crearBaseDatos() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {

            // Tabla de usuarios
            String crearTablaUsuarios = "CREATE TABLE IF NOT EXISTS usuario (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombres VARCHAR(100) NOT NULL, " +
                    "apellidos VARCHAR(100) NOT NULL, " +
                    "cedula VARCHAR(20) UNIQUE NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "contrasena VARCHAR(255) NOT NULL" +
                    ")";

            // Tabla de libros
            String crearTablaLibros = "CREATE TABLE IF NOT EXISTS libro (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "isbn VARCHAR(20) UNIQUE NOT NULL, " +
                    "titulo VARCHAR(200) NOT NULL, " +
                    "autor VARCHAR(150) NOT NULL, " +
                    "año_publicacion INTEGER" +
                    ")";

            // Tabla de reseñas (sin campo de puntuacion)
            String crearTablaResenas = "CREATE TABLE IF NOT EXISTS resena (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "usuario_id INTEGER NOT NULL, " +
                    "libro_id INTEGER NOT NULL, " +
                    "resena TEXT, " +
                    "fecha DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (libro_id) REFERENCES libro(id) ON DELETE CASCADE" +
                    ")";

            // Tabla de puntuación
            String crearTablaPuntuacion = "CREATE TABLE IF NOT EXISTS puntuacion (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "usuario_id INTEGER NOT NULL, " +
                    "libro_id INTEGER NOT NULL, " +
                    "puntuacion INTEGER NOT NULL, " +
                    "fecha DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (libro_id) REFERENCES libro(id) ON DELETE CASCADE" +
                    ")";

            // Ejecutar creación de tablas
            statement.execute(crearTablaUsuarios);
            statement.execute(crearTablaLibros);
            statement.execute(crearTablaResenas);
            statement.execute(crearTablaPuntuacion);

            System.out.println("Base de datos y tablas creadas correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al crear la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Busca libros por título
     * @param titulo Título del libro a buscar
     * @return Lista de libros encontrados
     * @throws SQLException si hay error en la BD
     */
    @Override
    public List<Libro> buscarLibroPorTitulo(String titulo) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libro WHERE titulo LIKE ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + titulo + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                libros.add(new Libro(
                        resultSet.getInt("id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getInt("año_publicacion")
                ));
            }
        }

        return libros;
    }

    /**
     * Busca libros por autor
     * @param autor Autor del libro a buscar
     * @return Lista de libros encontrados
     * @throws SQLException si hay error en la BD
     */
    @Override
    public List<Libro> buscarLibroPorAutor(String autor) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libro WHERE autor LIKE ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + autor + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                libros.add(new Libro(
                        resultSet.getInt("id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getInt("año_publicacion")
                ));
            }
        }

        return libros;
    }

    /**
     * Busca un libro por ISBN
     * @param isbn ISBN del libro a buscar
     * @return Libro encontrado o null si no existe
     * @throws SQLException si hay error en la BD
     */
    @Override
    public Libro buscarLibroPorISBN(String isbn) throws SQLException {
        String sql = "SELECT * FROM libro WHERE isbn = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Libro(
                        resultSet.getInt("id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getInt("año_publicacion")
                );
            }
        }

        return null;
    }

    /**
     * Añade una reseña a un libro
     * @param usuarioId ID del usuario que reseña
     * @param libroId ID del libro siendo reseñado
     * @param resena Texto de la reseña
     * @throws SQLException si hay error en la BD
     */
    @Override
    public void reseniar(int usuarioId, int libroId, String resena) throws SQLException {
        String sql = "INSERT INTO resena (usuario_id, libro_id, resena) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, usuarioId);
            statement.setInt(2, libroId);
            statement.setString(3, resena);
            statement.executeUpdate();

            System.out.println("Reseña agregada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al agregar reseña: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Puntúa un libro
     * @param usuarioId ID del usuario que puntúa
     * @param libroId ID del libro siendo puntuado
     * @param puntuacion Puntuación (sin verificaciones)
     * @throws SQLException si hay error en la BD
     */
    @Override
    public void puntuar(int usuarioId, int libroId, int puntuacion) throws SQLException {
        String sql = "INSERT INTO puntuacion (usuario_id, libro_id, puntuacion) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, usuarioId);
            statement.setInt(2, libroId);
            statement.setInt(3, puntuacion);
            statement.executeUpdate();

            System.out.println("Puntuación registrada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al registrar puntuación: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca un usuario por nombre y contraseña
     * @param nombres Nombres del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario encontrado o null si no existe
     * @throws SQLException si hay error en la BD
     */
    @Override
    public Usuario buscarUsuarioPorNombreYContrasena(String nombres, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE nombres = ? AND contrasena = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nombres);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Usuario(
                        resultSet.getInt("id"),
                        resultSet.getString("nombres"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("cedula"),
                        resultSet.getString("email"),
                        resultSet.getString("contrasena")
                );
            }
        }

        return null;
    }

    public List<Libro> obtenerLibroCoincidentesPorTitulo(String palabra) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libro WHERE titulo LIKE ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + palabra + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                libros.add(new Libro(
                        resultSet.getInt("id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getInt("año_publicacion")
                ));
            }
        }

        return libros;
    }


    public List<Libro> obtenerLibroCoincidentesPorAutor(String palabra) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libro WHERE autor LIKE ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + palabra + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                libros.add(new Libro(
                        resultSet.getInt("id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getInt("año_publicacion")
                ));
            }
        }

        return libros;
    }

    /**
     * Obtiene una conexión a la base de datos SQLite
     * @return Connection a la base de datos
     * @throws SQLException si hay error al conectar
     */
    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    @Override
    public void insertarDatosDePrueba() {
        try (Connection conn = obtenerConexion()) {
            // Insertar usuario de prueba
            String insertUsuario = "INSERT INTO usuario (nombres, apellidos, cedula, email, contrasena) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertUsuario)) {
                ps.setString(1, "Juan");
                ps.setString(2, "Pérez");
                ps.setString(3, "12345678");
                ps.setString(4, "juan@example.com");
                ps.setString(5, "password123");
                ps.executeUpdate();

                ps.setString(1, "admin");
                ps.setString(2, "00");
                ps.setString(3, "00000000");
                ps.setString(4, "admin@example.com");
                ps.setString(5, "admin");
                ps.executeUpdate();
            }

            // Insertar libros de prueba
            String insertLibro = "INSERT INTO libro (isbn, titulo, autor, año_publicacion) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertLibro)) {
                // Libro 1
                ps.setString(1, "978-0-13-110362-7");
                ps.setString(2, "The C Programming Language");
                ps.setString(3, "Brian W. Kernighan");
                ps.setInt(4, 1988);
                ps.executeUpdate();

                // Libro 2
                ps.setString(1, "978-0-201-63361-0");
                ps.setString(2, "Design Patterns");
                ps.setString(3, "Gang of Four");
                ps.setInt(4, 1994);
                ps.executeUpdate();

                // Libro 3
                ps.setString(1, "978-0-134-49418-4");
                ps.setString(2, "Effective Java");
                ps.setString(3, "Joshua Bloch");
                ps.setInt(4, 2018);
                ps.executeUpdate();

                // Libro 4
                ps.setString(1, "978-1-491-95012-4");
                ps.setString(2, "Learning SQL");
                ps.setString(3, "Alan Beaulieu");
                ps.setInt(4, 2020);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
