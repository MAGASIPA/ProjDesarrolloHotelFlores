
package Logica;

import DAO.LoginDAO;

public class LoginLogica {
    public int loginUsuario(String user, String pass){
        LoginDAO logDAO = new LoginDAO();
        return logDAO.loginUsuario(user, pass);
    }
}
