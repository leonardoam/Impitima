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
    private PreparedStatement selectAllPessoaTitNomeStatement;
    private PreparedStatement selectAllCandidatoPorCargoStatement;
    private PreparedStatement selectAllCandidatoStatement;
    
    private PreparedStatement selectCandidatoStatement;
    private PreparedStatement selectPartidoStatement;
    private PreparedStatement selectViceStatement;
    
    private PreparedStatement selectAllVotoCandidato;
    private PreparedStatement selectAllVotoPartido;
    
    public DataSelection(DBConnector conn) throws SQLException{
        this.conn = conn.getConnection();
        
        selectAllZonaStatement = this.conn.prepareStatement("SELECT * FROM zona WHERE 1=1 ORDER BY nroZona, estadoZona");
        selectAllSecaoStatement = this.conn.prepareStatement("SELECT * FROM secao WHERE 1=1 ORDER BY nroZona, estadoZona, nroSecao");
        selectAllUrnaStatement = this.conn.prepareStatement("SELECT * FROM urna WHERE 1=1 ORDER BY nroZona, estadoZona, nroSecao, nroUrna");
        selectAllPartidoStatement = this.conn.prepareStatement("SELECT * FROM partido WHERE 1=1 ORDER BY nroPartido");
        selectAllPessoaStatement = this.conn.prepareStatement("SELECT nroTitEleitor, UPPER(nomePessoa), UPPER(endPessoa), TO_CHAR(dataNasc, 'DD/MM/YYYY'), UPPER(escolaridade), UPPER(tipoPessoa), nroZona, estadoZona, nroSecao FROM pessoa WHERE 1=1 ORDER BY nomePessoa");
        selectAllPessoaTitNomeStatement = this.conn.prepareStatement("SELECT nrotiteleitor, nomepessoa FROM PESSOA ORDER BY nomepessoa");
        selectAllFiliaStatement = this.conn.prepareStatement("SELECT pessoa.nrotiteleitor, UPPER(pessoa.nomepessoa), partido.nropartido, partido.siglapartido, UPPER(partido.nomepartido)"
                                                            + " FROM FILIA, PESSOA, PARTIDO "
                                                            + " WHERE filia.nrotiteleitor = pessoa.nrotiteleitor"
                                                            + "  AND filia.nropartido = partido.nropartido"
                                                            + "  ORDER BY partido.nroPartido ASC");
        selectAllFuncionarioStatement = this.conn.prepareStatement("SELECT pessoa.nrotiteleitor, UPPER(pessoa.nomepessoa), UPPER(pessoa.endpessoa), TO_CHAR(dataNasc, 'DD/MM/YYYY'), UPPER(pessoa.escolaridade), UPPER(pessoa.tipopessoa), pessoa.nrozona, pessoa.estadozona, pessoa.nrosecao, UPPER(funcionario.cargofunc)"
                                                            + " FROM PESSOA"
                                                            + " LEFT JOIN FUNCIONARIO"
                                                            + " ON PESSOA.NROTITELEITOR = FUNCIONARIO.NROTITELEITOR"
                                                            + " ORDER BY pessoa.nomepessoa");
        selectAllCandidatoStatement = this.conn.prepareStatement("SELECT nrotiteleitor, UPPER(nomefantasia), nrocandidato, UPPER(cargocandidato), nrovotos" +
                                                                 "  FROM candidato" +
                                                                 "  ORDER BY cargocandidato, nomefantasia");
        selectAllCandidatoPorCargoStatement = this.conn.prepareStatement("SELECT candidato.nrotiteleitor, UPPER(pessoa.nomepessoa), UPPER(candidato.nomefantasia), UPPER(candidato.cargocandidato), candidato.nrocandidato" +
                                                                        "  FROM PESSOA, CANDIDATO" +
                                                                        "  WHERE PESSOA.NROTITELEITOR = CANDIDATO.NROTITELEITOR" +
                                                                        "    AND UPPER(CANDIDATO.CARGOCANDIDATO) LIKE UPPER(?)");
        selectCandidatoStatement = this.conn.prepareStatement("SELECT UPPER(candidato.nomefantasia), UPPER(candidato.cargocandidato), candidato.nrotiteleitor, partido.siglapartido" +
                                                                "    FROM CANDIDATO" +
                                                                "    LEFT JOIN FILIA" +
                                                                "    ON FILIA.NROTITELEITOR = CANDIDATO.NROTITELEITOR" +
                                                                "    JOIN PARTIDO" +
                                                                "    ON FILIA.NROPARTIDO = PARTIDO.NROPARTIDO" +
                                                                "    AND CANDIDATO.NROCANDIDATO = ?" +
                                                                "    AND UPPER(CANDIDATO.CARGOCANDIDATO) LIKE UPPER(?)");
        selectPartidoStatement = this.conn.prepareStatement("SELECT nropartido, UPPER(nomepartido), siglapartido FROM PARTIDO WHERE nropartido = ?");
        selectViceStatement = this.conn.prepareStatement("SELECT UPPER(candidato.nomefantasia), UPPER(candidato.cargocandidato), candidato.nrotiteleitor, PARTIDO.SIGLAPARTIDO" +
                                                                "    FROM CANDIDATO" +
                                                                "    JOIN EHVICEDE" +
                                                                "    ON EHVICEDE.NROTITELEITORVICE = CANDIDATO.NROTITELEITOR" +
                                                                "    AND EHVICEDE.NROTITELEITORPRINCIPAL = ?" +
                                                                "    LEFT JOIN FILIA" +
                                                                "    ON FILIA.NROTITELEITOR = CANDIDATO.NROTITELEITOR" +
                                                                "    JOIN PARTIDO" +
                                                                "    ON PARTIDO.NROPARTIDO = FILIA.NROPARTIDO");
        
        /*TODO*/
        /*selectAllVotoCandidato = this.conn.prepareStatement("");
        selectAllVotoPartido = this.conn.prepareStatement("");*/
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
    public ResultSet selectAllTitNomePessoa() throws SQLException{
        return selectAllPessoaTitNomeStatement.executeQuery();
    }
    
    public ResultSet selectAllFilia() throws SQLException{
        return selectAllFiliaStatement.executeQuery();
    }
    
    public ResultSet selectAllFuncionario() throws SQLException{
        return selectAllFuncionarioStatement.executeQuery();
    }
    
    public ResultSet selectAllCandidato() throws SQLException{
        return selectAllCandidatoStatement.executeQuery();
    }
    
    public ResultSet selectAllCandidatoPorCargo(String cargo) throws SQLException{
        selectAllCandidatoPorCargoStatement.setString(1, cargo);
        return selectAllCandidatoPorCargoStatement.executeQuery();
    }
    
    public ResultSet selectCandidato(String numTitEleitor, String cargo) throws SQLException{
        selectCandidatoStatement.setString(1, numTitEleitor);
        selectCandidatoStatement.setString(2, cargo);
        return selectCandidatoStatement.executeQuery();
    }
    public ResultSet selectPartido(String nroPartido) throws SQLException{
        selectPartidoStatement.setInt(1, Integer.parseInt(nroPartido));
        return selectPartidoStatement.executeQuery();
    }
    public ResultSet selectVice(String nroTitEleitor) throws SQLException{
        selectViceStatement.setString(1, nroTitEleitor);
        return selectViceStatement.executeQuery();
    }
}