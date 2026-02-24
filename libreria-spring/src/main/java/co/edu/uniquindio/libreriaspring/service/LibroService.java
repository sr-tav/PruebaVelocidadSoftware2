package co.edu.uniquindio.libreriaspring.service;

import co.edu.uniquindio.libreriaspring.domain.Libro;
import co.edu.uniquindio.libreriaspring.dbutils.DBUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroService {

    private final DBUtils db = DBUtils.getInstance();

    //funcionalidad 1
    //Buscar libros por coincidencia en el título
    public List<Libro> coincidenciaTitulo(String palabra){
        List<Libro> libros = new ArrayList<>();
        try {
            libros = db.obtenerLibroCoincidentesPorTitulo(palabra);
        } catch (Exception e) {
            System.out.println("Error al buscar libros por título: " + e.getMessage());
        }
        return libros;
    }

    //funcionalidad 1
    //Buscar libros por coincidencia en el autor
    public List<Libro> coincidenciaAutor(String palabra){
        List<Libro> libros = new ArrayList<>();
        try {
            libros = db.obtenerLibroCoincidentesPorAutor(palabra);
        } catch (Exception e) {
            System.out.println("Error al buscar libros por autor: " + e.getMessage());
        }
        return libros;
    }

}
