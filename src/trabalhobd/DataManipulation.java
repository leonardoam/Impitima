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
        
        insertPessoaStatement = this.conn.prepareStatement("INSERT INTO pessoa (nroTitEleitor, nomePessoa, endPessoa, dataNasc, escolaridade, tipoPessoa, nroZona, estadoZona, nroSecao) VALUES (?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'),, ?, ?, ?, ?, ?)");
        updatePessoaStatement = this.conn.prepareStatement("UPDATE pessoa SET nomePessoa = ?, endPessoa = ?, dataNasc = ?, escolaridade = ?, tipoPessoa = ?, nroZona = ?, estadoZona = ?, nroSecao = ? WHERE nroTitEleitor = ?");
        deletePessoaStatement = this.conn.prepareStatement("DELETE FROM pessoa WHERE nroTitEleitor = ?");
    }
    
    public int insereZona(int nroZona, String estadoZona, String endZona) throws SQLException{
        insertZonaStatement.setInt(1, nroZona);
        insertZonaStatement.setString(2, estadoZona);
        if (endZona.isEmpty())
            insertZonaStatement.setNull(3, Types.VARCHAR);
        else
            insertZonaStatement.setString(3, endZona);
        conn.commit();
        return insertZonaStatement.executeUpdate();
    }
    
    public int updateZona(int nroZona, String estadoZona, String endZona) throws SQLException{
        updateZonaStatement.setInt(2, nroZona);
        updateZonaStatement.setString(3, estadoZona);
        if (endZona.isEmpty())
            updateZonaStatement.setNull(1, Types.VARCHAR);
        else
            updateZonaStatement.setString(1, endZona);
        conn.commit();
        return updateZonaStatement.executeUpdate();
    }
    
    public int deleteZona(int nroZona, String estadoZona) throws SQLException{
        deleteZonaStatement.setInt(1, nroZona);
        deleteZonaStatement.setString(2, estadoZona);
        conn.commit();
        return deleteZonaStatement.executeUpdate();
    }
    
    public int insereSecao(int nroZona, String estadoZona, int nroSecao, String localSecao) throws SQLException{
        insertSecaoStatement.setInt(1, nroZona);
        insertSecaoStatement.setString(2, estadoZona);
        insertSecaoStatement.setInt(3, nroSecao);
        if (localSecao.isEmpty())
            insertSecaoStatement.setNull(4, Types.VARCHAR);
        else
            insertSecaoStatement.setString(4, localSecao);
        conn.commit();
        return insertSecaoStatement.executeUpdate();
    }
    
    public int updateSecao(int nroZona, String estadoZona, int nroSecao, String localSecao) throws SQLException{
        updateSecaoStatement.setInt(2, nroZona);
        updateSecaoStatement.setString(3, estadoZona);
        updateSecaoStatement.setInt(4, nroSecao);
        if (localSecao.isEmpty())
            updateSecaoStatement.setNull(1, Types.VARCHAR);
        else
            updateSecaoStatement.setString(1, localSecao);
        conn.commit();
        return updateSecaoStatement.executeUpdate();
    }
    
    public int deleteSecao(int nroZona, String estadoZona, int nroSecao) throws SQLException{
        deleteSecaoStatement.setInt(1, nroZona);
        deleteSecaoStatement.setString(2, estadoZona);
        deleteSecaoStatement.setInt(3, nroSecao);
        conn.commit();
        return deleteSecaoStatement.executeUpdate();
    }
    
    public int insereUrna(int nroZona, String estadoZona, int nroSecao, String modelo, String tipoUrna) throws SQLException{      
        insertUrnaStatement.setInt(1, nroZona);
        insertUrnaStatement.setString(2, estadoZona);
        insertUrnaStatement.setInt(3, nroSecao);
        insertUrnaStatement.setInt(4, 10/*"sequence_"+nroZona+"_"+nroSecao+".NEXTVAL"*/);
        if (modelo.isEmpty())
            insertUrnaStatement.setNull(5, Types.VARCHAR);
        else
            insertUrnaStatement.setString(5, modelo);
        if (tipoUrna.isEmpty())
            insertUrnaStatement.setNull(6, Types.VARCHAR);
        else
            insertUrnaStatement.setString(6, tipoUrna);
        conn.commit();
        return insertUrnaStatement.executeUpdate();
    }
    
    public int updateUrna(int nroZona, String estadoZona, int nroSecao, int nroUrna, String modelo, String tipoUrna) throws SQLException{
        updateUrnaStatement.setInt(3, nroZona);
        updateUrnaStatement.setString(4, estadoZona);
        updateUrnaStatement.setInt(5, nroSecao);
        updateUrnaStatement.setInt(6, nroUrna);
        if (modelo.isEmpty())
            updateUrnaStatement.setNull(1, Types.VARCHAR);
        else
            updateUrnaStatement.setString(1, modelo);
        if (tipoUrna.isEmpty())
            updateUrnaStatement.setNull(2, Types.VARCHAR);
        else
            updateUrnaStatement.setString(2, tipoUrna);
        conn.commit();
        return updateUrnaStatement.executeUpdate();
    }
    
    public int deleteUrna(int nroZona, String estadoZona, int nroSecao, int nroUrna) throws SQLException{
        deleteUrnaStatement.setInt(1, nroZona);
        deleteUrnaStatement.setString(2, estadoZona);
        deleteUrnaStatement.setInt(3, nroSecao);
        deleteUrnaStatement.setInt(4, nroUrna);
        conn.commit();
        return deleteUrnaStatement.executeUpdate();
    }
    
    public int inserePartido(int nroPartido, String nomePartido, String siglaPartido) throws SQLException{
        insertPartidoStatement.setInt(1, nroPartido);
        if (nomePartido.isEmpty())
            insertPartidoStatement.setNull(2, Types.VARCHAR);
        else
            insertPartidoStatement.setString(2, nomePartido);
        if (siglaPartido.isEmpty())
            insertPartidoStatement.setNull(3, Types.VARCHAR);
        else
            insertPartidoStatement.setString(3, siglaPartido);
        conn.commit();
        return insertPartidoStatement.executeUpdate();
    }
    
    public int updatePartido(int nroPartido, String nomePartido, String siglaPartido) throws SQLException{
        updatePartidoStatement.setInt(3, nroPartido);
        if (nomePartido.isEmpty())
            updatePartidoStatement.setNull(1, Types.VARCHAR);
        else
            updatePartidoStatement.setString(1, nomePartido);
        if (siglaPartido.isEmpty())
            updatePartidoStatement.setNull(2, Types.VARCHAR);
        else
            updatePartidoStatement.setString(2, siglaPartido);
        conn.commit();
        return updatePartidoStatement.executeUpdate();
    }
    
    public int deletePartido(int nroPartido) throws SQLException{
        deletePartidoStatement.setInt(1, nroPartido);
        conn.commit();
        return deletePartidoStatement.executeUpdate();
    }
    
    public int inserePessoa(int nroTitEleitor, String nomePessoa, String endPessoa, String dataNasc, String escolaridade, String tipoPessoa, int nroZona, String estadoZona, int nroSecao) throws SQLException{
        insertPessoaStatement.setInt(1, nroTitEleitor);
        if (nomePessoa.isEmpty())
            insertPessoaStatement.setNull(2, Types.VARCHAR);
        else
            insertPessoaStatement.setString(2, nomePessoa);
        if (endPessoa.isEmpty())
            insertPessoaStatement.setNull(3, Types.VARCHAR);
        else
            insertPessoaStatement.setString(3, endPessoa);
        
        conn.commit();
        return insertPessoaStatement.executeUpdate();
    }
    
    public int updatePessoa(int nroPessoa, String nomePessoa, String siglaPessoa) throws SQLException{
        updatePessoaStatement.setInt(3, nroPessoa);
        if (nomePessoa.isEmpty())
            updatePessoaStatement.setNull(1, Types.VARCHAR);
        else
            updatePessoaStatement.setString(1, nomePessoa);
        if (siglaPessoa.isEmpty())
            updatePessoaStatement.setNull(2, Types.VARCHAR);
        else
            updatePessoaStatement.setString(2, siglaPessoa);
        conn.commit();
        return updatePessoaStatement.executeUpdate();
    }
    
    public int deletePessoa(int nroPessoa) throws SQLException{
        deletePessoaStatement.setInt(1, nroPessoa);
        conn.commit();
        return deletePessoaStatement.executeUpdate();
    }
}
