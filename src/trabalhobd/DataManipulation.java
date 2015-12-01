/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhobd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 *
 * @author leonardo
 */
public class DataManipulation {
    private Connection conn;
    
    private PreparedStatement insertZonaStatement;
    private PreparedStatement updateZonaStatement;
    private PreparedStatement deleteZonaStatement;
    
    private PreparedStatement insertSecaoStatement;
    private PreparedStatement updateSecaoStatement;
    private PreparedStatement deleteSecaoStatement;
    
    private PreparedStatement insertUrnaStatement;
    private PreparedStatement updateUrnaStatement;
    private PreparedStatement deleteUrnaStatement;
    
    private PreparedStatement insertPartidoStatement;
    private PreparedStatement updatePartidoStatement;
    private PreparedStatement deletePartidoStatement;
    
    private PreparedStatement insertPessoaStatement;
    private PreparedStatement updatePessoaStatement;
    private PreparedStatement deletePessoaStatement;
    
    private PreparedStatement insertFiliaStatement;
    private PreparedStatement deleteFiliaStatement;
    
    private PreparedStatement insertFuncionarioStatement;
    private PreparedStatement updateFuncionarioStatement;
    private PreparedStatement deleteFuncionarioStatement;
    
    public DataManipulation(DBConnector conn) throws SQLException{
        this.conn = conn.getConnection();
        
        insertZonaStatement = this.conn.prepareStatement("INSERT INTO zona (nroZona, estadoZona, endZona, qtdEleitoresZ) VALUES (?, ?, ?, 0)");
        updateZonaStatement = this.conn.prepareStatement("UPDATE zona SET endZona = ? WHERE nroZona = ? AND estadoZona = ?");
        deleteZonaStatement = this.conn.prepareStatement("DELETE FROM zona WHERE nroZona = ? AND estadoZona = ?");
        
        insertSecaoStatement = this.conn.prepareStatement("INSERT INTO secao (nroZona, estadoZona, nroSecao, localSecao, qtdEleitoresS) VALUES (?, ?, ?, ?, 0)");
        updateSecaoStatement = this.conn.prepareStatement("UPDATE secao SET localSecao = ? WHERE nroZona = ? AND estadoZona = ? AND nroSecao = ?");
        deleteSecaoStatement = this.conn.prepareStatement("DELETE FROM secao WHERE nroZona = ? AND estadoZona = ? AND nroSecao = ?");
        
        insertUrnaStatement = this.conn.prepareStatement("INSERT INTO urna (nroZona, estadoZona, nroSecao, nroUrna, modelo, tipoUrna) VALUES (?, ?, ?, seq_urna.NEXTVAL, ?, ?)");
        updateUrnaStatement = this.conn.prepareStatement("UPDATE urna SET modelo = ?, tipoUrna = ? WHERE nroZona = ? AND estadoZona = ? AND nroSecao = ? AND nroUrna = ?");
        deleteUrnaStatement = this.conn.prepareStatement("DELETE FROM urna WHERE nroZona = ? AND estadoZona = ? AND nroSecao = ? AND nroUrna = ?");
        
        insertPartidoStatement = this.conn.prepareStatement("INSERT INTO partido (nroPartido, nomePartido, siglaPartido, nroVotosP) VALUES (?, ?, ?, 0)");
        updatePartidoStatement = this.conn.prepareStatement("UPDATE partido SET nomePartido = ?, siglaPartido = ? WHERE nroPartido = ?");
        deletePartidoStatement = this.conn.prepareStatement("DELETE FROM partido WHERE nroPartido = ?");
        
        insertPessoaStatement = this.conn.prepareStatement("INSERT INTO pessoa (nroTitEleitor, nomePessoa, endPessoa, dataNasc, escolaridade, tipoPessoa, nroZona, estadoZona, nroSecao) VALUES (?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?)");
        updatePessoaStatement = this.conn.prepareStatement("UPDATE pessoa SET nomePessoa = ?, endPessoa = ?, dataNasc = TO_DATE(?, 'DD/MM/YYYY'), escolaridade = ?, tipoPessoa = ?, nroZona = ?, estadoZona = ?, nroSecao = ? WHERE nroTitEleitor = ?");
        deletePessoaStatement = this.conn.prepareStatement("DELETE FROM pessoa WHERE nroTitEleitor = ?");
        
        insertFiliaStatement = this.conn.prepareStatement("INSERT INTO filia (nroTitEleitor, nroPartido) VALUES (?, ?)");
        deleteFiliaStatement = this.conn.prepareStatement("DELETE FROM filia WHERE nroTitEleitor = ?");
        
        insertFuncionarioStatement = this.conn.prepareStatement("INSERT INTO funcionario (nroTitEleitor, cargoFunc, nroZona, estadoZona, nroSecao) VALUES (?, ?, ?, ?, ?)");
        updateFuncionarioStatement = this.conn.prepareStatement("UPDATE funcionario SET cargofunc = ? WHERE nroTitEleitor = ?");
        deleteFuncionarioStatement = this.conn.prepareStatement("DELETE FROM funcionario WHERE nroTitEleitor = ?");
    }
    
    public void insereZona(String nroZona, String estadoZona, String endZona) throws SQLException{
        insertZonaStatement.setInt(1, Integer.parseInt(nroZona));
        insertZonaStatement.setString(2, estadoZona);
        if (endZona.isEmpty())
            insertZonaStatement.setNull(3, Types.VARCHAR);
        else
            insertZonaStatement.setString(3, endZona);
        insertZonaStatement.executeUpdate();
        conn.commit();
    }
    
    public void updateZona(String nroZona, String estadoZona, String endZona) throws SQLException{
        updateZonaStatement.setInt(2, Integer.parseInt(nroZona));
        updateZonaStatement.setString(3, (String)estadoZona);
        if (endZona == null)
            updateZonaStatement.setNull(1, Types.VARCHAR);
        else
            updateZonaStatement.setString(1, (String)endZona);
        conn.commit();updateZonaStatement.executeUpdate();
        conn.commit();
    }
    
    public void deleteZona(String nroZona, String estadoZona) throws SQLException{
        deleteZonaStatement.setInt(1, Integer.parseInt(nroZona));
        deleteZonaStatement.setString(2, estadoZona);
        deleteZonaStatement.executeUpdate();
        conn.commit();
    }
    
    public void insereSecao(String nroZona, String estadoZona, String nroSecao, String localSecao) throws SQLException{
        insertSecaoStatement.setInt(1, Integer.parseInt(nroZona));
        insertSecaoStatement.setString(2, estadoZona);
        insertSecaoStatement.setInt(3, Integer.parseInt(nroSecao));
        if (localSecao.isEmpty())
            insertSecaoStatement.setNull(4, Types.VARCHAR);
        else
            insertSecaoStatement.setString(4, localSecao);
        insertSecaoStatement.executeUpdate();
        conn.commit();
    }
    
    public void updateSecao(String nroZona, String estadoZona, String nroSecao, String localSecao) throws SQLException{
        updateSecaoStatement.setInt(2, Integer.parseInt(nroZona));
        updateSecaoStatement.setString(3, estadoZona);
        updateSecaoStatement.setInt(4, Integer.parseInt(nroSecao));
        if (localSecao == null)
            updateSecaoStatement.setNull(1, Types.VARCHAR);
        else
            updateSecaoStatement.setString(1, localSecao);
        updateSecaoStatement.executeUpdate();
        conn.commit();
    }
    
    public void deleteSecao(String nroZona, String estadoZona, String nroSecao) throws SQLException{
        deleteSecaoStatement.setInt(1, Integer.parseInt(nroZona));
        deleteSecaoStatement.setString(2, estadoZona);
        deleteSecaoStatement.setInt(3, Integer.parseInt(nroSecao));
        deleteSecaoStatement.executeUpdate();
        conn.commit();
    }
    
    public void insereUrna(String nroZona, String estadoZona, String nroSecao, String tipoUrna, String modelo) throws SQLException{      
        insertUrnaStatement.setInt(1, Integer.parseInt(nroZona));
        insertUrnaStatement.setString(2, estadoZona);
        insertUrnaStatement.setInt(3, Integer.parseInt(nroSecao));
        if (modelo == null)
            insertUrnaStatement.setNull(4, Types.VARCHAR);
        else
            insertUrnaStatement.setString(4, modelo);
        if (tipoUrna.isEmpty())
            insertUrnaStatement.setNull(5, Types.VARCHAR);
        else
            insertUrnaStatement.setString(5, tipoUrna);
        insertUrnaStatement.executeUpdate();
        conn.commit();
    }
    
    public void updateUrna(String nroZona, String estadoZona, String nroSecao, String nroUrna, String modelo, String tipoUrna) throws SQLException{
        updateUrnaStatement.setInt(3, Integer.parseInt(nroZona));
        updateUrnaStatement.setString(4, estadoZona);
        updateUrnaStatement.setInt(5, Integer.parseInt(nroSecao));
        updateUrnaStatement.setInt(6, Integer.parseInt(nroUrna));
        if (modelo.isEmpty())
            updateUrnaStatement.setNull(1, Types.VARCHAR);
        else
            updateUrnaStatement.setString(1, modelo);
        if (tipoUrna.isEmpty())
            updateUrnaStatement.setNull(2, Types.VARCHAR);
        else
            updateUrnaStatement.setString(2, tipoUrna);
        updateUrnaStatement.executeUpdate();
        conn.commit();
    }
    
    public void deleteUrna(String nroZona, String estadoZona, String nroSecao, String nroUrna) throws SQLException{
        deleteUrnaStatement.setInt(1, Integer.parseInt(nroZona));
        deleteUrnaStatement.setString(2, estadoZona);
        deleteUrnaStatement.setInt(3, Integer.parseInt(nroSecao));
        deleteUrnaStatement.setInt(4, Integer.parseInt(nroUrna));
        deleteUrnaStatement.executeUpdate();
        conn.commit();
    }
    
    public void inserePartido(String nroPartido, String nomePartido, String siglaPartido) throws SQLException{
        insertPartidoStatement.setInt(1, Integer.parseInt(nroPartido));
        if (nomePartido.isEmpty())
            insertPartidoStatement.setNull(2, Types.VARCHAR);
        else
            insertPartidoStatement.setString(2, nomePartido);
        if (siglaPartido.isEmpty())
            insertPartidoStatement.setNull(3, Types.VARCHAR);
        else
            insertPartidoStatement.setString(3, siglaPartido);
        insertPartidoStatement.executeUpdate();
        conn.commit();
    }
    
    public void updatePartido(String nroPartido, String nomePartido, String siglaPartido) throws SQLException{
        updatePartidoStatement.setInt(3, Integer.parseInt(nroPartido));
        if (nomePartido == null)
            updatePartidoStatement.setNull(1, Types.VARCHAR);
        else
            updatePartidoStatement.setString(1, nomePartido);
        if (siglaPartido == null)
            updatePartidoStatement.setNull(2, Types.VARCHAR);
        else
            updatePartidoStatement.setString(2, siglaPartido);
        updatePartidoStatement.executeUpdate();
        conn.commit();
    }
    
    public void deletePartido(String nroPartido) throws SQLException{
        deletePartidoStatement.setInt(1, Integer.parseInt(nroPartido));
        deletePartidoStatement.executeUpdate();
        conn.commit();
    }
    
    public void inserePessoa(String nroTitEleitor, String nomePessoa, String endPessoa, String dataNasc, String escolaridade, String tipoPessoa, String nroZona, String estadoZona, String nroSecao) throws SQLException{
        insertPessoaStatement.setString(1, nroTitEleitor);
        if (nomePessoa.isEmpty())
            insertPessoaStatement.setNull(2, Types.VARCHAR);
        else
            insertPessoaStatement.setString(2, nomePessoa);
        if (endPessoa.isEmpty())
            insertPessoaStatement.setNull(3, Types.VARCHAR);
        else
            insertPessoaStatement.setString(3, endPessoa);
        if (dataNasc.isEmpty())
            insertPessoaStatement.setNull(4, Types.VARCHAR);
        else
            insertPessoaStatement.setString(4, dataNasc);
        if (escolaridade == null)
            insertPessoaStatement.setNull(5, Types.VARCHAR);
        else
            insertPessoaStatement.setString(5, escolaridade);
        if (tipoPessoa == null)
            insertPessoaStatement.setNull(6, Types.VARCHAR);
        else
            insertPessoaStatement.setString(6, tipoPessoa);
        insertPessoaStatement.setInt(7, Integer.parseInt(nroZona));
        insertPessoaStatement.setString(8, estadoZona);
        insertPessoaStatement.setInt(9, Integer.parseInt(nroSecao));
        insertPessoaStatement.executeUpdate();
        conn.commit();
    }
    
    public void updatePessoa(String nroTitEleitor, String nomePessoa, String endPessoa, String dataNasc, String escolaridade, String tipoPessoa, String nroZona, String estadoZona, String nroSecao) throws SQLException{
        updatePessoaStatement.setString(9, nroTitEleitor);
        if (nomePessoa == null)
            updatePessoaStatement.setNull(1, Types.VARCHAR);
        else
            updatePessoaStatement.setString(1, nomePessoa);
        if (endPessoa == null)
            updatePessoaStatement.setNull(2, Types.VARCHAR);
        else
            updatePessoaStatement.setString(2, endPessoa);
        if (dataNasc == null)
            updatePessoaStatement.setNull(3, Types.VARCHAR);
        else
            updatePessoaStatement.setString(3, dataNasc);
        if (escolaridade == null)
            updatePessoaStatement.setNull(4, Types.VARCHAR);
        else
            updatePessoaStatement.setString(4, escolaridade);
        if (tipoPessoa == null)
            updatePessoaStatement.setNull(5, Types.VARCHAR);
        else
            updatePessoaStatement.setString(5, tipoPessoa);
        updatePessoaStatement.setInt(6, Integer.parseInt(nroZona));
        updatePessoaStatement.setString(7, estadoZona);
        updatePessoaStatement.setInt(8, Integer.parseInt(nroSecao));
        updatePessoaStatement.executeUpdate();
        conn.commit();
    }
    
    public void deletePessoa(String nroTitEleitor) throws SQLException{
        deletePessoaStatement.setString(1, nroTitEleitor);
        deletePessoaStatement.executeUpdate();
        conn.commit();
    }
    
    public void insereFilia(String nroTitEleitor, String nroPartido) throws SQLException{
        System.out.println(nroTitEleitor+" "+nroPartido);
        insertFiliaStatement.setString(1, nroTitEleitor);
        insertFiliaStatement.setInt(2, Integer.parseInt(nroPartido));
        insertFiliaStatement.execute();
        conn.commit();
    }
    
    public void deleteFilia(String nroTitEleitor) throws SQLException{
        deleteFiliaStatement.setString(1, nroTitEleitor);
        deleteFiliaStatement.execute();
        conn.commit();
    }
    
    public void insereFuncionario(String nroTitEleitor, String cargoFunc, String nroZona, String estadoZona, String nroSecao) throws SQLException{
        insertFuncionarioStatement.setString(1, nroTitEleitor);
        insertFuncionarioStatement.setString(2, cargoFunc);
        insertFuncionarioStatement.setInt(3, Integer.parseInt(nroZona));
        insertFuncionarioStatement.setString(4, estadoZona);
        insertFuncionarioStatement.setInt(5, Integer.parseInt(nroSecao));
        insertFuncionarioStatement.execute();
        conn.commit();
    }
    
    public void updateFuncionario(String nroTitEleitor, String cargoFunc) throws SQLException{
        updateFuncionarioStatement.setString(2, nroTitEleitor);
        updateFuncionarioStatement.setString(1, cargoFunc);
        int count = updateFuncionarioStatement.executeUpdate();
        if (count == 0)
            throw new SQLException("Não é um funcionário.\n", null, 0);
        conn.commit();
    }
    
    public void deleteFuncionario(String nroTitEleitor) throws SQLException{
        deleteFuncionarioStatement.setString(1, nroTitEleitor);
        int count = deleteFuncionarioStatement.executeUpdate();
        if (count == 0)
            throw new SQLException("Não é um funcionário.\n", null, 0);
        conn.commit();
    }
}
