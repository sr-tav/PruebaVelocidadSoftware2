package co.edu.uniquindio.libreriaspring.service;

import co.edu.uniquindio.libreriaspring.dbutils.DB;
import co.edu.uniquindio.libreriaspring.dbutils.DBUtils;
import co.edu.uniquindio.libreriaspring.domain.Libro;
import org.springframework.stereotype.Service;


/*
* Corresponde a la funcionalidad 3:
* "Un usuario puede puntuar los libros del 1 al 5
*/
@Service
public class PuntuacionService {

    private final DB db = DBUtils.getInstance();

    /**
     * Permite que un usuario puntúe un libro.
     *
     * @param usuarioId ID del usuario que realiza la puntuación
     * @param isbn ISBN del libro que se desea puntuar
     * @param valor Valor de la puntuación (1 a 5)
     * @throws Exception si ocurre algún error de validación
     */
    public void puntuarLibro(int usuarioId, String isbn, int valor) throws Exception {

        //La puntuación debe estar entre 1 y 5
        if (valor < 1 || valor > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
        }

        //Se busca el libro por su ISBN
        Libro libro = db.buscarLibroPorISBN(isbn);

        //Se valida que el libro si exista
        if (libro == null) {
            throw new IllegalArgumentException("El libro no existe.");
        }

        // Se guarda la puntuación en la base de datos
        db.puntuar(usuarioId, libro.getId(), valor);

        System.out.println("Libro puntuado correctamente.");
    }
}