package co.edu.uniquindio.libreriaspring.consola;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import co.edu.uniquindio.libreriaspring.services.LibroService;
import co.edu.uniquindio.libreriaspring.domain.Libro;

import java.util.Scanner;
@Component
public class MenuConsola{
    Scanner sc = new Scanner(System.in);
    private final ConfigurableApplicationContext context;
    private final LibroService libroService;
    String mensaje = "//////// MENU LIBRERIA //////////\n" +
            "1. consultar por autor y titulo \n" +
            "2. consultar por cualquiera \n" +
            "3. calificar libro \n" +
            "4. hacer reseña de un libro \n" +
            "0. salir \n";

    public MenuConsola(ConfigurableApplicationContext context, LibroService libroService) {
        this.context = context;
        this.libroService = libroService;
    }

    public void mostrarMenu(){
        String opcion = "1";
        while(!opcion.equals("0")){
            System.out.println(mensaje);
            opcion = sc.nextLine();
            switch (opcion){
                case "1":
                    System.out.println("aqui va la logica para la prime funcionalidad");
                    break;
                case "2":
                    buscarLibrosPorCriterios();
                    break;
                case "3":
                    System.out.println("aqui va la logica para la tercer funcionalidad");
                    break;
                case "4":
                    System.out.println("aqui va la logica para la cuarta funcionalidad");
                    break;
                case "0":
                    System.out.println("Saliendo del programa...");
                    context.close();
                    break;
            }
        }
    }

    //funcionalidad 2
    public void buscarLibrosPorCriterios() {

        System.out.println("BUSQUEDA DE LIBROS");

        System.out.print("Titulo (opcional): ");
        String titulo = sc.nextLine();

        System.out.print("Autor (opcional): ");
        String autor = sc.nextLine();

        System.out.print("ISBN (opcional): ");
        String isbn = sc.nextLine();

        var resultados = libroService.buscarPorCriterios(titulo, autor, isbn);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros.");
        } else {
            System.out.println("Resultados:");
            for (Libro libro : resultados) {
                System.out.println(libro);
            }
        }
    }
}
