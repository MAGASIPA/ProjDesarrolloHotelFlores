package Modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MODELO_TABLA_TIPO_EMPLEADO extends AbstractTableModel {

    private String titulos[] = {"ID", "CATEGORIA", "DESCRIPCION"};
    private List<Tipoempleado> tipoempleado;

    public MODELO_TABLA_TIPO_EMPLEADO(List<Tipoempleado> tipoempleado) {
        this.tipoempleado = tipoempleado;
        this.fireTableDataChanged();
    }
    
    public List<Tipoempleado> getTipoempleado() {
        return tipoempleado;
    }
    @Override
    public int getRowCount() {
        return tipoempleado.size();
    }

    @Override
    public int getColumnCount() {
        return titulos.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tipoempleado obj;
        Object valor = "";
        if (this.tipoempleado != null) {
            obj = this.tipoempleado.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    valor = obj.getIdTipoEmpleado();
                    break;
                case 1:
                    valor = obj.getCategoria();
                    break;
                case 2:
                    valor = obj.getDescripcion();
                    break;
            }
        }
        return valor;
    }

    public Tipoempleado getTipoempleado(int Pos) {
        Tipoempleado obj = null;

        if (this.tipoempleado != null) {
            obj = this.tipoempleado.get(Pos);
        }
        return obj;
    }

    @Override
    public String getColumnName(int columa) {
        return titulos[columa];
    }

}
