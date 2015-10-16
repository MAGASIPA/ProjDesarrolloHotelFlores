
package Logica;

import DAO.AlquilerReservaDAO;
import Modelo.AlquilerReserva;

public class AlquilerReservaLogica {
    public String registroReservaAlquiler(AlquilerReserva Alq){
        AlquilerReservaDAO alqDAO = new AlquilerReservaDAO();
        return alqDAO.registroReservaAlquiler(Alq);
    }
    public String confirmarReservaAlquiler(AlquilerReserva Alq){
        AlquilerReservaDAO alqDAO = new AlquilerReservaDAO();
        return alqDAO.confirmarReservaAlquiler(Alq);
    }
   
}
