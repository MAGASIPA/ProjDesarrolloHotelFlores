
package Logica;

import DAO.DatosReservaDAO;
import Modelo.DatosReserva;
import java.util.Date;

public class DatosReservaLogica {
    public DatosReserva ListarDatosReservaCliente(String numDoc, Date fechaReg){
        DatosReservaDAO datResDAO = new DatosReservaDAO();
        return datResDAO.ListarDatosReservaCliente(numDoc, fechaReg);
    }
}
