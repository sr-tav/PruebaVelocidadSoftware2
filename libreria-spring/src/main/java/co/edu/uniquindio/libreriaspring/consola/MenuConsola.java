package co.edu.uniquindio.libreriaspring.consola;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import co.edu.uniquindio.libreriaspring.service.LibroService;
import co.edu.uniquindio.libreriaspring.domain.Libro;

import co.edu.uniquindio.libreriaspring.service.PuntuacionService;


import java.util.Scanner;

@Component
public class MenuConsola{
    Scanner sc = new Scanner(System.in);
    private final ConfigurableApplicationContext context;
    private final PuntuacionService puntuacionService;
    private final LibroService libroService;

    String mensaje = "//////// MENU LIBRERIA //////////\n" +
            "1. consultar por autor o titulo \n" +
            "2. consultar por cualquiera \n" +
            "3. calificar libro \n" +
            "4. hacer reseña de un libro \n" +
            "0. salir \n";

    public MenuConsola(ConfigurableApplicationContext context, PuntuacionService puntuacionService, LibroService libroService) {
        this.context = context;
        this.puntuacionService = puntuacionService;
        this.libroService = libroService;
    }

    public void mostrarMenu(){
        String opcion = "1";
        while(!opcion.equals("0")){
            System.out.println(mensaje);
            opcion = sc.nextLine();
            switch (opcion){
                case "1":
                    System.out.println("Escoja una opcion: \n" +
                            "1. consultar por autor \n" +
                            "2. consultar por titulo \n");
                    String opcion2 = sc.nextLine();
                    if(opcion2.equals("1")){
                        System.out.println("Ingrese el autor a buscar: ");
                        String autor = sc.nextLine();
                        
                        libroService.coincidenciaAutor(autor).forEach(System.out::println);
                        
                    }else if(opcion2.equals("2")){
                        System.out.println("Ingrese el titulo a buscar: ");

                        libroService.coincidenciaTitulo(sc.nextLine()).forEach(System.out::println);

                    }
                    break;
                case "2":
                    buscarLibrosPorCriterios();
                    break;
                case "3":
                    try {
                        System.out.print("Ingrese ISBN del libro: ");
                        String isbn = sc.nextLine();

                        System.out.print("Ingrese puntuación (1-5): ");
                        int valor = Integer.parseInt(sc.nextLine());

                        // Usuario temporal y se llama
                        int usuarioId = 1;

                        //Se llama al método que tiene toda la lógica
                        puntuacionService.puntuarLibro(usuarioId, isbn, valor);

                        System.out.println("Libro puntuado correctamente ✅");

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
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
