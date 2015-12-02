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
                case 12899:
                    erro += "Valor de campo inserido é muito grande.\n";
                    break;
                case 2291:
                    erro += "Valor não atende aos requisitos de registro.\n";
                    break;
                case 1403:
                    erro += "Nenhum dado encontrado.\n";
                    break;
                case 6512:
                    erro += "Nenhum dado encontrado.\n";
                    break;
                case 1422:
                    erro += "Erro interno.\n";
                    break;
                case 1722:
                    erro += "Entrada inválida.\n";
                    break;
                case 1841:
                    erro += "Data inválida.\n";
                    break;
                case 1843:
                    erro += "Data inválida.\n";
                    break;
                case 1847:
                    erro += "Data inválida.\n";
                    break;
                default:
                    erro += "Erro desconhecido - Código: "+ex.getErrorCode()+".\n"+ex.getMessage()+"\n";
            }
            erro += "\n"+ex.getMessage();
            ex = ex.getNextException();
            if (ex == null)
                break;
        }
        return erro+"\n";
    }
}
