/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhobd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author leonardo
 */
public class DBConnector {
    private Connection connection;
    
    public DBConnector(String user, String password, boolean remote) throws SQLException, ClassNotFoundException{
        Class.forName("oracle.jdbc.driver.OracleDriver");

        if (remote)
            connection = DriverManager.getConnection("jdbc:oracle:thin:@grad.icmc.usp.br:15215:orcl", user, password);
        else
            connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.183.15:1521:orcl", user, password);
    }
    
    public void disconnect() throws SQLException{
        connection.close();
    }
    
    public void commit() throws SQLException{
        connection.commit();
    }
    
    public void rollback() throws SQLException{
        connection.rollback();
    }
    
    public boolean test() throws SQLException{
        return connection.isValid(10);
    }
    
    public Connection getConnection(){
        return connection;
    }
}
