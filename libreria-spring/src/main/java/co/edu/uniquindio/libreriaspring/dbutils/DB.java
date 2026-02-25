package co.edu.uniquindio.libreriaspring.dbutils;

import co.edu.uniquindio.libreriaspring.domain.Libro;
import co.edu.uniquindio.libreriaspring.domain.Usuario;
import java.sql.SQLException;
import java.util.List;

public interface DB {

    /**
     * Crea la base de datos SQLite y todas las tablas necesarias
     */
    void crearBaseDatos();

    void insertarDatosDePrueba() throws SQLException;
    /**
     * Busca libros por título
     * @param titulo Título del libro a buscar
     * @return Lista de libros encontrados
     * @throws SQLException si hay error en la BD
     */
    List<Libro> buscarLibroPorTitulo(String titulo) throws SQLException;

    /**
     * Busca libros por autor
     * @param autor Autor del libro a buscar
     * @return Lista de libros encontrados
     * @throws SQLException si hay error en la BD
     */
    List<Libro> buscarLibroPorAutor(String autor) throws SQLException;

    /**
     * Busca un libro por ISBN
     * @param isbn ISBN del libro a buscar
     * @return Libro encontrado o null si no existe
     * @throws SQLException si hay error en la BD
     */
    Libro buscarLibroPorISBN(String isbn) throws SQLException;

    /**
     * Añade una reseña a un libro
     * @param usuarioId ID del usuario que reseña
     * @param libroId ID del libro siendo reseñado
     * @param resena Texto de la reseña
     * @throws SQLException si hay error en la BD
     */
    void reseniar(int usuarioId, int libroId, String resena) throws SQLException;

    /**
     * Puntúa un libro
     * @param usuarioId ID del usuario que puntúa
     * @param libroId ID del libro siendo puntuado
     * @param puntuacion Puntuación (1-5)
     * @throws SQLException si hay error en la BD
     */
    void puntuar(int usuarioId, int libroId, int puntuacion) throws SQLException;

    /**
     * Busca un usuario por nombre y contraseña
     * @param nombres Nombres del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario encontrado o null si no existe
     * @throws SQLException si hay error en la BD
     */
    Usuario buscarUsuarioPorNombreYContrasena(String nombres, String contrasena) throws SQLException;
}
