/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhobd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author leonardo
 */
public abstract class TableManipulator {    
    public static void setNewModel(JTable table, int[] editable){
        DefaultTableModel model = new myTableModel(editable);
        Vector<String> columnName = new Vector<String>();
        Vector<Integer> columnWidth = new Vector<Integer>();
        for(int i=0; i<table.getModel().getColumnCount(); i++){
            columnName.add(table.getModel().getColumnName(i));
            columnWidth.add(table.getColumnModel().getColumn(i).getMinWidth());
        }
        model.setColumnIdentifiers(columnName);
        table.setModel(model);
        table.setAutoCreateRowSorter(true);
        for(int i=0; i<table.getModel().getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setMinWidth(columnWidth.get(i));
    }
    
    public static void centralizeColumns(JTable table, int[] columns){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i=0; i<columns.length; i++)
            table.getColumnModel().getColumn(columns[i]).setCellRenderer( centerRenderer );
    }
    
    /*public static void adjustColumnWidth(JTable table, int[] width){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for(int i=0; i<width.length; i++)
            table.getColumn(model.getColumnName(i)).setMinWidth(width[i]);
    }*/
    
    public static void fillTable(JTable table, ResultSet rs, int[] buttoncolumns, String[] buttonlabels, Action[] buttonactions){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        try {
            for(int i=0; rs.next(); i++){
                Vector<Object> row = new Vector<Object>();
                for(int j=1; j<=rs.getMetaData().getColumnCount(); j++)
                    row.add(rs.getString(j));
                for(int j=0; j<buttonlabels.length; j++)
                    row.add(buttonlabels[j]);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problemas ao criar tabela.\n"+ErrorTranslator.translate(ex));
        }
        ButtonColumn button;
        for(int i=0; i<buttoncolumns.length; i++)
            button = new ButtonColumn(table, buttonactions[i], buttoncolumns[i]);
    }
    
    public static void fillTable(JTable table, ResultSet rs, int[] rscolumns, int[] buttoncolumns, String[] buttonlabels, Action[] buttonactions){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        try {
            for(int i=0; rs.next(); i++){
                Vector<Object> row = new Vector<Object>();
                for(int j=0; j<rscolumns.length; j++)
                    row.add(rs.getString(rscolumns[j]));
                for(int j=0; j<buttonlabels.length; j++)
                    row.add(buttonlabels[j]);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problemas ao criar tabela.\n"+ErrorTranslator.translate(ex));
        }
        ButtonColumn button;
        for(int i=0; i<buttoncolumns.length; i++)
            button = new ButtonColumn(table, buttonactions[i], buttoncolumns[i]);
    }
    
    public static void fillTableFuncionario(JTable table, ResultSet rs, int[] buttoncolumns, String[] buttonlabels, Action[] buttonactions){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        try {
            for(int i=0; rs.next(); i++){
                Vector<Object> row = new Vector<Object>();
                for(int j=1; j<=rs.getMetaData().getColumnCount(); j++){
                    if (j == 10 && rs.getString(j) == null)
                            row.add("Nenhum");
                    else
                        row.add(rs.getString(j));
                }
                for(int j=0; j<buttonlabels.length; j++)
                    row.add(buttonlabels[j]);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problemas ao criar tabela.\n"+ErrorTranslator.translate(ex));
        }
        ButtonColumn button;
        for(int i=0; i<buttoncolumns.length; i++)
            button = new ButtonColumn(table, buttonactions[i], buttoncolumns[i]);
    }
}
