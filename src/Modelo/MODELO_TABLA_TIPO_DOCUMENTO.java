
package Modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MODELO_TABLA_TIPO_DOCUMENTO extends AbstractTableModel{
    
    String titulos[] = {"Código", "Descripción"};
private List<Tipodocumento> tipodocumento;

    public MODELO_TABLA_TIPO_DOCUMENTO(List<Tipodocumento> tipodocumento) {
        this.tipodocumento = tipodocumento;
    }


    public List<Tipodocumento> getTipodocumento() {
        return tipodocumento;
    }

    @Override
    public int getRowCount() {
        return tipodocumento.size();
    }

    @Override
    public int getColumnCount() {
        return titulos.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tipodocumento obj;
        Object valor = "";
        if (this.tipodocumento != null) {
            obj = this.tipodocumento.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    valor = obj.getIdTipoDoc();
                    break;
                case 1:
                    valor = obj.getNombreDoc();
                    break;
            }
        }
        return valor;
    }

    public Tipodocumento getTipodocumento(int Pos) {
        Tipodocumento obj = null;

        if (this.tipodocumento != null) {
            obj = this.tipodocumento.get(Pos);
        }
        return obj;
    }

    @Override
    public String getColumnName(int columa) {
        return titulos[columa];
    }





}
