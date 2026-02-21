package co.edu.uniquindio.libreriaspring.consola;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class MenuConsola{
    Scanner sc = new Scanner(System.in);
    private final ConfigurableApplicationContext context;
    String mensaje = "//////// MENU LIBRERIA //////////\n" +
            "1. consultar por autor y titulo \n" +
            "2. consultar por cualquiera \n" +
            "3. calificar libro \n" +
            "4. hacer reseña de un libro \n" +
            "0. salir \n";

    public MenuConsola(ConfigurableApplicationContext context) {
        this.context = context;
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
                    System.out.println("aqui va la logica para la segunda funcionalidad");
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
}
