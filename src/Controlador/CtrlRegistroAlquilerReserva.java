package Controlador;

import LOGICA.PersonaLogica;
import Logica.AlquilerReservaLogica;
import Logica.DatosReservaLogica;
import Logica.HabitacionLogica;
import Modelo.AlquilerReserva;
import Modelo.DatosReserva;
import Modelo.Habitacion;
import Modelo.Persona;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import vista.JRegistroAlquiler;
import vista.RegCliente;

public class CtrlRegistroAlquilerReserva implements KeyListener, ActionListener, PropertyChangeListener{
    JRegistroAlquiler formAlq;
    JDesktopPane DeskMain;
    Habitacion hab;
    Persona person;
    int idHab = 0;
    int idAlqRes = 0;//recuperar el id de la reserva al que se hara la actualizacion a alquiler
    HabitacionLogica habLog;
    PersonaLogica perLog;
    AlquilerReservaLogica alqreLog;
    DatosReservaLogica datResLog;
    int estadoAlq = 0;
    int idEmpleado = 0;
    
    public CtrlRegistroAlquilerReserva(JDesktopPane panel, int idHab,int idEmp) {
        formAlq = new JRegistroAlquiler();
        this.DeskMain = panel;
        this.idHab = idHab;   
        this.idEmpleado = idEmp;
        
        
        habLog = new HabitacionLogica();
        perLog = new PersonaLogica();        
        alqreLog = new AlquilerReservaLogica();
        datResLog = new DatosReservaLogica();
        
        formAlq.dtcFeAlquiler.setMinSelectableDate(new Date());
        formAlq.dtcFeSalida.setMinSelectableDate(new Date());
        formAlq.dtcFeAlquiler.setDate(new Date());
        formAlq.dtcFeSalida.setDate(new Date());
        
        formAlq.pnlAcomp.setVisible(false);
        
        //se agrega formato de hora a un spinner
        
        formAlq.spnHoraAlquiler.setModel(new SpinnerDateModel());
        formAlq.spnHoraAlquiler.setEditor(new JSpinner.DateEditor(formAlq.spnHoraAlquiler, "HH:mm"));
        
        formAlq.spnHoraSalida.setModel(new SpinnerDateModel());
        formAlq.spnHoraSalida.setEditor(new JSpinner.DateEditor(formAlq.spnHoraSalida, "HH:mm"));
        
        if(idHab != 0){
            ListarDatosHabitacion();
        }
        else{
            formAlq.btnGuardar.setActionCommand("Confirmar");
            formAlq.btnGuardar.setText("Confirmar");
            formAlq.dtcFeAlquiler.setEnabled(false);
            formAlq.spnHoraAlquiler.setEnabled(false);
            formAlq.cbxEstadoAlquiler.setEnabled(false);
        }
        
    }
    
    public void showForm(){
        DeskMain.add(formAlq);
        formAlq.txtDni.addKeyListener(this);
        formAlq.ckbMostrarAcomp.addActionListener(this);
        formAlq.btnAddClient.addActionListener(this);
        formAlq.btnGuardar.addActionListener(this);
        formAlq.btnCancelar.addActionListener(this);
        formAlq.dtcFeSalida.addPropertyChangeListener(this);
        formAlq.cbxEstadoAlquiler.addActionListener(this);
        formAlq.show();
    }
    
    private void ListarDatosHabitacion(){
        hab = new Habitacion();
        hab = habLog.ListarHabitacion_xID(idHab);
        
        formAlq.lblTipoHab.setText(hab.getTipHab().getCategoria());
        formAlq.lblDescrip.setText(hab.getTipHab().getComentario());
        formAlq.lblNum.setText(hab.getNumero());
        formAlq.lblPrecio.setText(hab.getPrecio()+"");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER){
            String DatosCliente = "";
            String nom,apeP,apeM;
            
            person = perLog.buscarPersonaClienteXDoc(formAlq.txtDni.getText());
            if(person.getIdPersona()!= 0){
                if(idHab != 0){//si idHab es diferente de 0 entonces sera un registro de alquiler, y se buscara a la persona
                    nom = person.getNombres();
                    apeP = person.getApellidoPaterno();
                    apeM = person.getApellidoMaterno();
                    DatosCliente = nom + " " + apeP + " " + apeM;
                } else {
                   //si es 0 se hara una consulta para traer los datos de la reserva y actualizarla a alquiler
                    DatosReserva datRes;
                    datRes = datResLog.ListarDatosReservaCliente(formAlq.txtDni.getText(), formAlq.dtcFeAlquiler.getDate());
                    if(datRes.getIdReservaAlq()!= 0){
                        //pregunto si datRes tiene datos guardados, si los tiene
                        //listo los nombres en la caja de texto cliente
                        nom = person.getNombres();
                        apeP = person.getApellidoPaterno();
                        apeM = person.getApellidoMaterno();
                        DatosCliente = nom + " " + apeP + " " + apeM;   

                        formAlq.lblTipoHab.setText(datRes.getCategoria());
                        formAlq.lblNum.setText(datRes.getNumeroHab());
                        formAlq.lblPrecio.setText(datRes.getPrecio()+"");
                        formAlq.lblDescrip.setText(datRes.getDescripcion());
                        formAlq.txaAcomp.setText(datRes.getAcompañantes());
                        idAlqRes = datRes.getIdReservaAlq();
                        idHab = datRes.getIdHabitacion();
                    }else{
                        JOptionPane.showMessageDialog(null, "Su Reserva a Caducado o no Existe","Registro Alquiler",JOptionPane.ERROR_MESSAGE);
                    }                   
                    
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "El cliente no esta registrado","Registro Alquiler",JOptionPane.ERROR_MESSAGE);
            }   
            
            formAlq.txtDatosClient.setText(DatosCliente);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //permite que al marcar el ckb se active un textarea para registrar acompañantes, solo como referencia
        if(e.getSource() == formAlq.ckbMostrarAcomp){
            if(formAlq.ckbMostrarAcomp.isSelected()){                
                formAlq.pnlAcomp.setPreferredSize(new Dimension(180,50));
                formAlq.pnlAcomp.setVisible(true);                
                formAlq.setSize(new Dimension(792, 635));
            }else {
                formAlq.pnlAcomp.setPreferredSize(new Dimension(180,0));
                formAlq.pnlAcomp.setVisible(false);                
                formAlq.setSize(new Dimension(792, 447));
            }
        }
        
        if(e.getSource() == formAlq.cbxEstadoAlquiler){
            if(formAlq.cbxEstadoAlquiler.getSelectedIndex()== 1){
                //formAlq.dtcFeSalida.setEnabled(true);
                //formAlq.spnHoraSalida.setEnabled(true);
                formAlq.txtmontoAlq.setText("0.00");
                formAlq.txtmontoAlq.setEnabled(false);
            }
            else{
                formAlq.dtcFeSalida.setEnabled(true);
                formAlq.spnHoraSalida.setEnabled(true);
                formAlq.txtmontoAlq.setEnabled(true);
            }
        }
                
        if(e.getActionCommand().equals("addCliente")){
            RegCliente formRC = new RegCliente();
            PersonaControlador per = new PersonaControlador(formRC, DeskMain);
            per.runner();
        }
        
        if(e.getActionCommand().equals("Guardar")){
            try {
                AlquilerReserva alqRes = new AlquilerReserva();
                
                //agrego las fechas de los datachooser
                alqRes.setFechaRegistro(formAlq.dtcFeAlquiler.getDate());                
                alqRes.setFechaSalida(formAlq.dtcFeSalida.getDate());
                
                //declaro un formato de tiempo
                SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
                
                //agrego un formato de los valores optenidos de spinner y lo concateno para formar un tiempo de formato HH:mm:ss
                String tiempoIni = time.format(formAlq.spnHoraAlquiler.getValue()).concat(":00");
                String tiempoFin = time.format(formAlq.spnHoraSalida.getValue()).concat(":00");
                
                //luego declaro un formato de tipo datetime osea fecha y hora
                time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                //atributos variables de tipo Date para hacer un parse osea un formateo de tipo yyyy-MM-dd HH:mm:ss con la funcion .parse
                //luego el .getTime() me optiene los datos del tiempo en este caso la hora.
                Date date1 = new Date(time.parse("2000-01-01 "+ tiempoIni).getTime());
                Date date2 = new Date(time.parse("2000-01-01 "+ tiempoFin).getTime());
                
                //teniendo las horas en formato Date ahora hago una nueva conversion usando las clases Time y optengo la hora con .getTime()
                //para asi agregarlas al modelo.
                alqRes.setHoraRegistro(new Time(date1.getTime()));
                alqRes.setHoraSalida(new Time(date2.getTime()));
                
                alqRes.setAcompañantes(formAlq.txaAcomp.getText());
                alqRes.setMonto(Double.parseDouble(formAlq.txtmontoAlq.getText()));
                alqRes.setEstado(formAlq.cbxEstadoAlquiler.getSelectedIndex()+1);
                alqRes.setIdCliente(person.getIdPersona());
                alqRes.setIdEmpleado(idEmpleado);//idempleado
                alqRes.setIdHabitacion(idHab);
                
                String msj = alqreLog.registroReservaAlquiler(alqRes);
                
                JOptionPane.showMessageDialog(null, msj,"Registro Alquiler - Reserva",JOptionPane.INFORMATION_MESSAGE);
            } catch (ParseException ex) {
                Logger.getLogger(CtrlRegistroAlquilerReserva.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        if(e.getActionCommand().equals("Confirmar")){
            try {
                AlquilerReserva alqRes = new AlquilerReserva();
                
                alqRes.setIdAlquiler(idAlqRes);
                //agrego las fechas de los datachooser
                alqRes.setFechaRegistro(formAlq.dtcFeAlquiler.getDate());                
                alqRes.setFechaSalida(formAlq.dtcFeSalida.getDate());
                
                //declaro un formato de tiempo
                SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
                //agrego un formato de los valores optenidos de spinner y lo concateno para formar un tiempo de formato HH:mm:ss
                String tiempoIni = time.format(formAlq.spnHoraAlquiler.getValue()).concat(":00");
                String tiempoFin = time.format(formAlq.spnHoraSalida.getValue()).concat(":00");
                //luego declaro un formato de tipo datetime osea fecha y hora
                time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                //atributos variables de tipo Date para hacer un parse osea un formateo de tipo yyyy-MM-dd HH:mm:ss con la funcion .parse
                //luego el .getTime() me optiene los datos del tiempo en este caso la hora.
                Date date1 = new Date(time.parse("2000-01-01 "+ tiempoIni).getTime());
                Date date2 = new Date(time.parse("2000-01-01 "+ tiempoFin).getTime());
                
                //teniendo las horas en formato Date ahora hago una nueva conversion usando las clases Time y optengo la hora con .getTime()
                //para asi agregarlas al modelo.
                alqRes.setHoraRegistro(new Time(date1.getTime()));
                alqRes.setHoraSalida(new Time(date2.getTime()));
                
                alqRes.setAcompañantes(formAlq.txaAcomp.getText());
                alqRes.setMonto(Double.parseDouble(formAlq.txtmontoAlq.getText()));
                alqRes.setIdHabitacion(idHab);
                
                String msj = alqreLog.confirmarReservaAlquiler(alqRes);
                
                JOptionPane.showMessageDialog(null, msj,"Registro Alquiler - Reserva",JOptionPane.INFORMATION_MESSAGE);
            } catch (ParseException ex) {
                Logger.getLogger(CtrlRegistroAlquilerReserva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(e.getActionCommand().equals("Cancelar")){
            formAlq.dispose();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        //System.out.println(evt.getPropertyName());
                
        if(evt.getPropertyName().equals("date")){
            try {
                Date fechaFin = (Date)evt.getNewValue();
                
                SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                
                String fIni = fecha.format(formAlq.dtcFeAlquiler.getDate()).concat(" ").concat(hora.format(formAlq.spnHoraAlquiler.getValue()).concat(":00"));
                String fFin = fecha.format(new Date(fechaFin.getTime())).concat(" ").concat(hora.format(formAlq.spnHoraSalida.getValue()).concat(":00"));
                
                SimpleDateFormat diferencia = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                long diferenciaDias = (diferencia.parse(fFin).getTime() - diferencia.parse(fIni).getTime());
                
                //long diferenciaDias = (fechaFin.getTime() - formAlq.dtcFeAlquiler.getDate().getTime());
                long minutos = diferenciaDias / (1000 * 60);
                int minutosAlq = (int)minutos;
                
                double precioMin = (Double.parseDouble(formAlq.lblPrecio.getText())/24)/60;
                                
                double montoAlq = (minutosAlq * precioMin);
                           
                
                formAlq.txtmontoAlq.setText(roundDecimals(montoAlq, 2)+"");
                
            } catch (ParseException ex) {
                Logger.getLogger(CtrlRegistroAlquilerReserva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private double roundDecimals(double val,int totDec){
        return Math.round(val*Math.pow(10,totDec))/Math.pow(10,totDec);
    }
    
}
