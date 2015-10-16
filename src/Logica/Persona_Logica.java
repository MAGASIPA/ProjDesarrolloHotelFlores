/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import DAO.Persona_DAO;
import Modelo.Personaa;

/**
 *
 * @author ChristianM
 */
public class Persona_Logica {
    public int registrarPersona(Personaa p){
        Persona_DAO pd = new Persona_DAO();
        return pd.registrarPersona(p);
    }
}
