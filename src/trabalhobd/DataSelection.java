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
    private PreparedStatement selectAllFiliaStatement;
    private PreparedStatement selectAllFuncionarioStatement;
    
    public DataSelection(DBConnector conn) throws SQLException{
        this.conn = conn.getConnection();
        
        selectAllZonaStatement = this.conn.prepareStatement("SELECT * FROM zona WHERE 1=1 ORDER BY nroZona, estadoZona");
        selectAllSecaoStatement = this.conn.prepareStatement("SELECT * FROM secao WHERE 1=1 ORDER BY nroZona, estadoZona, nroSecao");
        selectAllUrnaStatement = this.conn.prepareStatement("SELECT * FROM urna WHERE 1=1 ORDER BY nroZona, estadoZona, nroSecao, nroUrna");
        selectAllPartidoStatement = this.conn.prepareStatement("SELECT * FROM partido WHERE 1=1 ORDER BY nroPartido");
        selectAllPessoaStatement = this.conn.prepareStatement("SELECT nroTitEleitor, nomePessoa, endPessoa, TO_CHAR(dataNasc, 'DD/MM/YYYY'), escolaridade, tipoPessoa, nroZona, estadoZona, nroSecao FROM pessoa WHERE 1=1 ORDER BY nomePessoa");
        selectAllFiliaStatement = this.conn.prepareStatement("SELECT pessoa.nrotiteleitor, pessoa.nomepessoa, partido.nropartido, partido.siglapartido, partido.nomepartido"
                                                            + " FROM FILIA, PESSOA, PARTIDO "
                                                            + " WHERE filia.nrotiteleitor = pessoa.nrotiteleitor"
                                                            + "  AND filia.nropartido = partido.nropartido"
                                                            + "  ORDER BY partido.nroPartido ASC");
        
        selectAllFuncionarioStatement = this.conn.prepareStatement("SELECT pessoa.nrotiteleitor, pessoa.nomepessoa, pessoa.endpessoa, TO_CHAR(dataNasc, 'DD/MM/YYYY'), pessoa.escolaridade, pessoa.tipopessoa, pessoa.nrozona, pessoa.estadozona, pessoa.nrosecao, funcionario.cargofunc"
                                                            + " FROM PESSOA"
                                                            + " LEFT JOIN FUNCIONARIO"
                                                            + " ON PESSOA.NROTITELEITOR = FUNCIONARIO.NROTITELEITOR"
                                                            + " ORDER BY pessoa.nomepessoa");
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
    
    public ResultSet selectAllFilia() throws SQLException{
        return selectAllFiliaStatement.executeQuery();
    }
    
    public ResultSet selectAllFuncionario() throws SQLException{
        return selectAllFuncionarioStatement.executeQuery();
    }
}
