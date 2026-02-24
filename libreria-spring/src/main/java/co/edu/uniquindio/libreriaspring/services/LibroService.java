package co.edu.uniquindio.libreriaspring.services;

import co.edu.uniquindio.libreriaspring.domain.Libro;
import co.edu.uniquindio.libreriaspring.dbutils.DBUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroService {

    private final DBUtils db = DBUtils.getInstance();

    //funcionalidad 2
    public List<Libro> buscarPorCriterios(String titulo, String autor, String isbn) {

        List<Libro> resultados = new ArrayList<>();

        try {
            if (titulo != null && !titulo.isEmpty()) {
                resultados.addAll(db.buscarLibroPorTitulo(titulo));
            }

            if (autor != null && !autor.isEmpty()) {
                resultados.addAll(db.buscarLibroPorAutor(autor));
            }

            if (isbn != null && !isbn.isEmpty()) {
                Libro libro = db.buscarLibroPorISBN(isbn);
                if (libro != null) {
                    resultados.add(libro);
                }
            }

        } catch (Exception e) {
            System.out.println("Error en la búsqueda: " + e.getMessage());
        }

        return resultados;
    }
}
