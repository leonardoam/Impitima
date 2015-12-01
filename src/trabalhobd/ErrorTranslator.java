/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhobd;

import java.sql.SQLException;

/**
 *
 * @author leonardo
 */
public class ErrorTranslator {
    public static String translate(String entity, SQLException ex){
        /*switch(code){
            case 1:
                return "Tentativa de inserção de uma "+entity+" já existente.";
            default:
                return "Erro desconhecido - Código: "+code+".";
        }*/
        return ex.getMessage()+"\n"+ex.getLocalizedMessage();
    }
}
