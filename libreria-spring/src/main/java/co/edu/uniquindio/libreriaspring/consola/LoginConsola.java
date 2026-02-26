package co.edu.uniquindio.libreriaspring.consola;

import co.edu.uniquindio.libreriaspring.domain.Usuario;
import co.edu.uniquindio.libreriaspring.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class LoginConsola implements CommandLineRunner {
    private final MenuConsola menuConsola;
    private final UsuarioService usuarioService;

    public LoginConsola(MenuConsola menuConsola, UsuarioService usuarioService) {
        this.menuConsola = menuConsola;
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("nombre de usuario");
        String name = sc.nextLine();
        System.out.println("contraseña");
        String pass = sc.nextLine();

        Usuario activo = usuarioService.buscarUsuario(name, pass);

        if (activo != null) {
            menuConsola.mostrarMenu(activo);
        }else{
            System.out.println("credenciales incorrectas");
        }
    }
}
