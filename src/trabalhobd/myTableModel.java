/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhobd;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author leonardo
 */
public class myTableModel extends DefaultTableModel{
    private int[] iseditable;
    public myTableModel(int[] iseditable){
        super();
        this.iseditable = iseditable;
    }
    public boolean isCellEditable(int row, int column) {
        if (iseditable[column] == 1){
            return true;
        }else{
            return false;
        }
    }
}