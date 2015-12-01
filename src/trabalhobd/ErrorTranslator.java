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
    public static String translate(SQLException ex){
        String erro = "";
        while(true){
            switch(ex.getErrorCode()){
                case 0:
                    erro += ex.getMessage();
                    break;
                case 1:
                    erro += "Valor já existente.\n";
                    break;
                case 4091:
                    erro += "Valor não pode ser removido, pois outros valores dependem dele.\n";
                    break;
                default:
                    erro += "Erro desconhecido - Código: "+ex.getErrorCode()+".\n"+ex.getMessage()+"\n";
            }
            ex = ex.getNextException();
            if (ex == null)
                break;
        }
        return erro;
    }
}
