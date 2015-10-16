
package Modelo.Controles;

import Modelo.Tipodocumento;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ModeloComboTipoDoc extends AbstractListModel implements ComboBoxModel{
    
    List<Tipodocumento> lista = new ArrayList<>();
    Tipodocumento seleccionado = null;

    public ModeloComboTipoDoc(List<Tipodocumento> list) {
        lista = list;
    }

    @Override
    public int getSize() {
        return lista.size();
    }

    @Override
    public Object getElementAt(int i) {
        if (lista.size() > 0) {
            return lista.get(i).getNombreDoc();
        } else {
            return null;
        }
    }

    @Override
    public void setSelectedItem(Object o) {
        if (lista.size() > 0 && o != null) {
            for (Tipodocumento z : lista) {
                if (z.toString().equals(o.toString())) {
                    seleccionado = z;
                    break;
                }
            }
        }
    }

    @Override
    public Object getSelectedItem() {
        return seleccionado;
    }

}
