/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Controles;

//import Modelo.Empleado;
import Modelo.Empleados_Vista;
//import Modelo.Persona;
//import Modelo.TipoDocumento;
//import Modelo.TipoEmpleado;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ChristianM
 */
public class ModeloTablaEmpleados extends AbstractTableModel {
    
    List<Empleados_Vista> lisEmpV = null;
//    List<Persona> listPers = null;
//    List<TipoDocumento> listTD = null;
//    List<TipoEmpleado> listTE= null;
    String columnas[] = { "idPersona", "Nombres", "Apellido Paterno", "Apellido Materno", "Dirección", "Correo", "Documeno", "N° documento", "Tipo Empleado", "Sueldo"};

    public ModeloTablaEmpleados (List<Empleados_Vista> lista) {
        this.lisEmpV = lista;
    }
    
    @Override
    public int getRowCount() {
        return lisEmpV.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String valor = "";
        if(columnIndex == 0){
            valor = String.valueOf(lisEmpV.get(rowIndex).getIdPersona());
        }
        if(columnIndex == 1){
            valor = String.valueOf(lisEmpV.get(rowIndex).getNombres());
        }
        if(columnIndex == 2){
            valor = String.valueOf(lisEmpV.get(rowIndex).getApellidoPaterno());
        }
        if(columnIndex == 3){
            valor = String.valueOf(lisEmpV.get(rowIndex).getApellidoMaterno());
        }
        if(columnIndex == 4){
            valor = String.valueOf(lisEmpV.get(rowIndex).getDireccion());
        }
        if(columnIndex == 5){
            valor = String.valueOf(lisEmpV.get(rowIndex).getCorreo());
        }
        if(columnIndex == 6){
            valor = String.valueOf(lisEmpV.get(rowIndex).getDocumento());
        }
        if(columnIndex == 7){
            valor = String.valueOf(lisEmpV.get(rowIndex).getNdocumento());
        }
        if(columnIndex == 8){
            valor = String.valueOf(lisEmpV.get(rowIndex).getCategoria());
        }
        if(columnIndex == 9){
            valor = String.valueOf(lisEmpV.get(rowIndex).getSueldo());
        }
        return valor;
    }

    @Override
    public String getColumnName(int i){
        return columnas[i];
    }
}
