/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import DAO.Empleado_DAO;
import Modelo.Empleado;

/**
 *
 * @author ChristianM
 */
public class Empleado_Logica {
    public int regEmpleado(Empleado e){
        Empleado_DAO em = new Empleado_DAO();
        return em.registroEmpleado(e);
    }
}
