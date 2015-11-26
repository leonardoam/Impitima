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
    private PreparedStatement selectZonaStatement;
    
    public DataSelection(DBConnector conn) throws SQLException{
        this.conn = conn.getConnection();
        
        selectZonaStatement = this.conn.prepareStatement("SELECT * FROM zona");
    }
    
    public ResultSet selectAllZonas() throws SQLException{
        return selectZonaStatement.executeQuery();
    }
}
