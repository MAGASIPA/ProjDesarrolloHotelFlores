package Modelo.Controles;

import Modelo.Habitacion;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaHabitaciones extends AbstractTableModel{
    
    List<Habitacion> listHab = null;
    String columns[] = {"ID","Tipo Habitacion","Numero","Piso","Precio","Baño","Medidas","Estado"};

    public ModeloTablaHabitaciones(List<Habitacion> list) {
        this.listHab = list;
    }          
    
    @Override
    public int getRowCount() {
        return listHab.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object valor="";
        
        if(columnIndex == 0){
            valor = listHab.get(rowIndex).getIdHabitacion();
        }
        if(columnIndex == 1){
            valor = listHab.get(rowIndex).getTipHab();
        }
        if(columnIndex == 2){
            valor = listHab.get(rowIndex).getNumero();
        }
        if(columnIndex == 3){
            valor = listHab.get(rowIndex).getPiso();
        }
        if(columnIndex == 4){
            valor = listHab.get(rowIndex).getPrecio();
        }
        if(columnIndex == 5){
            valor = listHab.get(rowIndex).getBaño();
        }
        if(columnIndex == 6){
            valor = listHab.get(rowIndex).getMedidas();
        }
        if(columnIndex == 7){
            switch(listHab.get(rowIndex).getEstado()){
                case 1:
                    valor = "Libre";
                    break;
                case 2:
                    valor = "Ocupado";
                    break;
                case 3:
                    valor = "Mantenimiento";
                    break;
                case 4:
                    valor = "Limpieza";
                    break;
                case 5:
                    valor = "Deshabilitado";
                    break;                    
            }
            
        } 
        return valor;
    }
    @Override
    public String getColumnName(int i){
        return columns[i];
    }
}
