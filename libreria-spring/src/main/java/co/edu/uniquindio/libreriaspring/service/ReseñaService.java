package co.edu.uniquindio.libreriaspring.service;

import co.edu.uniquindio.libreriaspring.dbutils.DB;
import co.edu.uniquindio.libreriaspring.dbutils.DBUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
@Service
public class ReseñaService {
    private final DB db = DBUtils.getInstance();

    public void reseñarLibro(int user_id, int libro_id, String reseña){
        try{
            db.reseniar(user_id, libro_id, reseña);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Reseña creada con exito");
    }
}
