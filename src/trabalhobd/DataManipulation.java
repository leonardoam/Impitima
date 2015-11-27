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
    
    public DataManipulation(DBConnector conn) throws SQLException{
        this.conn = conn.getConnection();
        
        insertZonaStatement = this.conn.prepareStatement("INSERT INTO zona (nroZona, estadoZona, endZona) VALUES (?, ?, ?)");
        updateZonaStatement = this.conn.prepareStatement("UPDATE zona SET endZona = ? WHERE nroZona = ? AND estadoZona = ?");
        deleteZonaStatement = this.conn.prepareStatement("DELETE FROM zona WHERE nroZona = ? AND estadoZona = ?");
        
        insertSecaoStatement = this.conn.prepareStatement("INSERT INTO secao (nroZona, estadoZona, nroSecao, localSecao) VALUES (?, ?, ?)");
        updateSecaoStatement = this.conn.prepareStatement("UPDATE secao SET localSecao = ? WHERE nroZona = ? AND estadoZona = ? AND nroSecao = ?");
        deleteSecaoStatement = this.conn.prepareStatement("DELETE FROM secao WHERE nroZona = ? AND estadoZona = ? AND nroSecao = ?");
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
    
    public int updateZona(int nroZona, String estadoZona, int nroSecao, String localSecao) throws SQLException{
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
    
    public int deleteZona(int nroZona, String estadoZona, int nroSecao) throws SQLException{
        deleteSecaoStatement.setInt(1, nroZona);
        deleteSecaoStatement.setString(2, estadoZona);
        deleteSecaoStatement.setInt(3, nroSecao);
        conn.commit();
        return deleteSecaoStatement.executeUpdate();
    }
}
