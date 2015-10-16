
package Controlador;

import Logica.LoginLogica;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vista.JLogin;
import vista.Main;

public class CtrlLogin implements ActionListener{
    
    JLogin formLogin;
    LoginLogica loginLog;
    int idEmp = 0;
    public CtrlLogin() {
        formLogin = new JLogin();        
        loginLog = new LoginLogica();
    }
    
    public void showForm(){
        formLogin.btnIngresar.addActionListener(this);
        formLogin.setLocationRelativeTo(null);
//        Toolkit screen = Toolkit.getDefaultToolkit();
//        Dimension tamaño = screen.getScreenSize();
//        int alto = tamaño.height;
//        int ancho = ta
        
        formLogin.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("INGRESAR")){
            
            int rspta = loginLog.loginUsuario(formLogin.txtUser.getText(), String.valueOf(formLogin.txtPass.getPassword()));
            
            if(rspta == -1){
                JOptionPane.showMessageDialog(null, "Usuario Incorrecto","Login",JOptionPane.ERROR_MESSAGE);
            }else{
                if(rspta == 0){
                    JOptionPane.showMessageDialog(null, "Password Incorrecta","Login",JOptionPane.ERROR_MESSAGE);
                }else {
                    idEmp = rspta;
                    System.out.println(idEmp);
                    Main menu = new Main(); 
                    CtrlMain ctrl = new CtrlMain(menu,idEmp);
                    formLogin.dispose();
                    ctrl.show();
                }
            }
        }
    }
    
    
}
