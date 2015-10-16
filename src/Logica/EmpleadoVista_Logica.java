/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import DAO.EmpleadosVista_DAO;
import Modelo.Empleados_Vista;
import java.util.List;

/**
 *
 * @author ChristianM
 */
public class EmpleadoVista_Logica {
    public List<Empleados_Vista> listarEmpleados() {
        EmpleadosVista_DAO evd = new EmpleadosVista_DAO();
        return evd.listarEmpleados();
    }
    
    public int actEmpleado(Empleados_Vista ev){
        EmpleadosVista_DAO evd = new EmpleadosVista_DAO();
        return evd.actualizaEmpleado(ev);
    }
}