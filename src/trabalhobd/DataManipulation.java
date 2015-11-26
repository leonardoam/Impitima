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

/**
 *
 * @author leonardo
 */
public class DataManipulation {
    private Connection conn;
    private PreparedStatement insereZonaStatement;
    
    public DataManipulation(DBConnector conn) throws SQLException{
        this.conn = conn.getConnection();
        
        insereZonaStatement = this.conn.prepareStatement("INSERT INTO zona (nroZona, estadoZona, endZona) VALUES (?, ?, ?)");
    }
    
    public boolean insereZona(int nroZona, String estadoZona, String endZona) throws SQLException{
        insereZonaStatement.setInt(1, nroZona);
        insereZonaStatement.setString(2, estadoZona);
        insereZonaStatement.setString(3, endZona);
        
        int res = insereZonaStatement.executeUpdate();
        if (res == 1)
            return true;
        return false;
    }
}
