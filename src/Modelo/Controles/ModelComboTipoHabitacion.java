package Modelo.Controles;

import Modelo.Tipohabitacion;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ModelComboTipoHabitacion extends AbstractListModel implements ComboBoxModel{

    private List<Tipohabitacion> rows;
    Tipohabitacion tipHab;

    public ModelComboTipoHabitacion(List<Tipohabitacion> row) {
        this.rows = row;
    }
       
    @Override
    public int getSize() {
        return rows.size();
    }

    @Override
    public Object getElementAt(int index) {
        return rows.get(index).getCategoria();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if(anItem!=null &&rows.size()>0 ){
            for(Tipohabitacion m:rows){
                if(m.toString().equals(anItem.toString())){
                 tipHab=m;
                 break;
                }
            }        
        }
    }

    @Override
    public Object getSelectedItem() {
        return tipHab;
    }
    
}
