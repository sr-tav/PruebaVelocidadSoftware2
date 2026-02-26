package co.edu.uniquindio.libreriaspring.consola;
import co.edu.uniquindio.libreriaspring.domain.Usuario;
import co.edu.uniquindio.libreriaspring.service.ReseñaService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import co.edu.uniquindio.libreriaspring.service.LibroService;
import co.edu.uniquindio.libreriaspring.domain.Libro;

import co.edu.uniquindio.libreriaspring.service.PuntuacionService;


import java.sql.SQLOutput;
import java.util.Scanner;

@Component
public class MenuConsola{
    Scanner sc = new Scanner(System.in);
    private final ConfigurableApplicationContext context;
    private final PuntuacionService puntuacionService;
    private final LibroService libroService;
    private final ReseñaService reseaService;
    private Usuario activo;

    String mensaje = "//////// MENU LIBRERIA //////////\n" +
            "1. consultar por autor o titulo \n" +
            "2. consultar por cualquiera \n" +
            "3. calificar libro \n" +
            "4. hacer reseña de un libro \n" +
            "0. salir \n";

    public MenuConsola(ConfigurableApplicationContext context, PuntuacionService puntuacionService,
                       LibroService libroService, ReseñaService reseaService) {
        this.context = context;
        this.puntuacionService = puntuacionService;
        this.libroService = libroService;
        this.reseaService = reseaService;
    }

    public void mostrarMenu(Usuario activo){
        this.activo = activo;
        String opcion = "1";
        while(!opcion.equals("0")){
            System.out.println(mensaje);
            opcion = sc.nextLine();
            switch (opcion){
                case "1":
                    buscarPorLibroAutor();
                    break;
                case "2":
                    buscarLibrosPorCriterios();
                    break;
                case "3":
                    realizarPuntuacion();
                    break;
                case "4":
                    realizarReseña();
                    break;
                case "0":
                    System.out.println("Saliendo del programa...");
                    context.close();
                    break;
            }
        }
    }
    //funcionalidad 1
    public void buscarPorLibroAutor(){
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
    //funcionalidad 3
    public void realizarPuntuacion(){
        try {
            System.out.print("Ingrese ISBN del libro: ");
            String isbn = sc.nextLine();

            System.out.print("Ingrese puntuación (1-5): ");
            int valor = Integer.parseInt(sc.nextLine());

            // Usuario temporal y se llama
            int usuarioId = activo.getId();

            //Se llama al método que tiene toda la lógica
            puntuacionService.puntuarLibro(usuarioId, isbn, valor);

            System.out.println("Libro puntuado correctamente ✅");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //funcionalidad 4
    public void realizarReseña(){
        System.out.println("INGRESE EL LIBRO");

        System.out.print("Titulo (opcional): ");
        String titulo = sc.nextLine();

        System.out.print("Autor (opcional): ");
        String autor = sc.nextLine();

        System.out.print("ISBN (opcional): ");
        String isbn = sc.nextLine();

        var resultados = libroService.buscarPorCriterios(titulo, autor, isbn);
        Libro libro = resultados.get(0);

        System.out.println("Usted ha seleccionado el libro: "+ libro.getTitulo() + "\nautor: "+ libro.getAutor() + "\n");
        System.out.println(" Escriba su reseña: ");
        String reseña = sc.nextLine();

        reseaService.reseñarLibro(activo.getId(), libro.getId(), reseña);
    }
}
