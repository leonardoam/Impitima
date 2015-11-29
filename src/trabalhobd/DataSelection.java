/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhobd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author leonardo
 */
public class DataSelection {
    private Connection conn;
    private PreparedStatement selectAllZonaStatement;
    private PreparedStatement selectAllSecaoStatement;
    private PreparedStatement selectAllUrnaStatement;
    private PreparedStatement selectAllPartidoStatement;
    private PreparedStatement selectAllPessoaStatement;
    
    public DataSelection(DBConnector conn) throws SQLException{
        this.conn = conn.getConnection();
        
        selectAllZonaStatement = this.conn.prepareStatement("SELECT * FROM zona WHERE 1=1 ORDER BY nroZona, estadoZona");
        selectAllSecaoStatement = this.conn.prepareStatement("SELECT * FROM secao WHERE 1=1 ORDER BY nroZona, estadoZona, nroSecao");
        selectAllUrnaStatement = this.conn.prepareStatement("SELECT * FROM urna WHERE 1=1 ORDER BY nroZona, estadoZona, nroSecao, nroUrna");
        selectAllPartidoStatement = this.conn.prepareStatement("SELECT * FROM partido WHERE 1=1 ORDER BY nroPartido");
        selectAllPessoaStatement = this.conn.prepareStatement("SELECT nroTitEleitor, nomePessoa, endPessoa, TO_CHAR(dataNasc, 'DD/MM/YYYY'), escolaridade, tipoPessoa, nroZona, estadoZona, nroSecao FROM pessoa WHERE 1=1 ORDER BY nomePessoa");
    }
    
    public ResultSet selectAllZonas() throws SQLException{
        return selectAllZonaStatement.executeQuery();
    }
    
    public ResultSet selectAllSecao() throws SQLException{
        return selectAllSecaoStatement.executeQuery();
    }
    
    public ResultSet selectAllUrna() throws SQLException{
        return selectAllUrnaStatement.executeQuery();
    }
    
    public ResultSet selectAllPartido() throws SQLException{
        return selectAllPartidoStatement.executeQuery();
    }
    
    public ResultSet selectAllPessoa() throws SQLException{
        return selectAllPessoaStatement.executeQuery();
    }
}
