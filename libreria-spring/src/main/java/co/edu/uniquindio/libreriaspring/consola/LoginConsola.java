package co.edu.uniquindio.libreriaspring.consola;

import co.edu.uniquindio.libreriaspring.dbutils.DBUtils;
import co.edu.uniquindio.libreriaspring.domain.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class LoginConsola implements CommandLineRunner {
    private final MenuConsola menuConsola;

    public LoginConsola(MenuConsola menuConsola) {
        this.menuConsola = menuConsola;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("nombre de usuario");
        String name = sc.nextLine();
        System.out.println("contraseña");
        String pass = sc.nextLine();
        Usuario usuario = DBUtils.getInstance().buscarUsuarioPorNombreYContrasena(name, pass);

        if (usuario != null) {
            menuConsola.mostrarMenu();
        }else{
            System.out.println("credenciales incorrectas");
        }
    }
}
