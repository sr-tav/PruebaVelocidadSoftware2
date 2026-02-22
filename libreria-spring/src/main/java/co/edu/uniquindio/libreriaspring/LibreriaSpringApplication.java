package co.edu.uniquindio.libreriaspring;

import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.edu.uniquindio.libreriaspring.dbutils.DBUtils;
import co.edu.uniquindio.libreriaspring.domain.Libro;
import co.edu.uniquindio.libreriaspring.domain.Usuario;
import co.edu.uniquindio.libreriaspring.dbutils.DB;

@SpringBootApplication
public class LibreriaSpringApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(LibreriaSpringApplication.class, args);

        DB db= DBUtils.getInstance();
        db.crearBaseDatos();
        db.insertarDatosDePrueba();



        List<Libro> resultado = db.buscarLibroPorTitulo("Programming");
        System.out.print(resultado);

        List<Libro> resultado2 = db.buscarLibroPorAutor("Kernighan");
        System.out.print(resultado2);
        
        List<Libro> resultado3 = db.buscarLibroPorAutor("Four");
        System.out.print(resultado3);

        Libro resultado4 = db.buscarLibroPorISBN("978-0-134-49418-4");
        System.out.print(resultado4);

        Usuario usuario = db.buscarUsuarioPorNombreYContrasena("Juan","password123" );

        db.reseniar(usuario.getId(), resultado4.getId(), "que es esta basura XDDD");

        db.puntuar(usuario.getId(), resultado4.getId(), 3);


    }

}
