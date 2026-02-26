package co.edu.uniquindio.libreriaspring.service;

import co.edu.uniquindio.libreriaspring.dbutils.DBUtils;
import co.edu.uniquindio.libreriaspring.domain.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final DBUtils db = DBUtils.getInstance();

    public Usuario buscarUsuario(String nombre, String pass){
        Usuario usuario = null;
        try{
            usuario = db.buscarUsuarioPorNombreYContrasena(nombre,pass);
        }catch(Exception e){
            System.out.println("Error al buscar usuario");
        }
        return usuario;
    }
}
