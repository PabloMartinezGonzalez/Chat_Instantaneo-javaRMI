/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import javax.swing.table.*;
/**
 *
 * @author alumnogreibd
 */
public class ModeloTablaAmigos extends AbstractTableModel{
    private ArrayList<String> amigos;
    
    public ModeloTablaAmigos(){
        this.amigos=new ArrayList<>();
    }
    
    public int getColumnCount (){
        return 1;
    }

    public int getRowCount(){
        return amigos.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "Amigos";break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
        }
        return clase;
    }

    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }

    @Override
    public Object getValueAt(int row, int col){
        Object resultado=null;
        switch (col){
            case 0: resultado= amigos.get(row);break;

        }
        return resultado;
    }

    public void setFilas(ArrayList<String> amigos){
        this.amigos=amigos;
        fireTableDataChanged();
    }

    public String obtenerAmigos(int i){
        return this.amigos.get(i);
    }
    
    public void nuevaFila(String a){
        this.amigos.add(a);
        fireTableRowsInserted(this.amigos.size()-1, this.amigos.size()-1);
    }
    
    public void borrarFila(int i){
        this.amigos.remove(i);
        fireTableRowsDeleted(i,i);
    }
    
    public ArrayList<String> devolver(){
        return this.amigos;
    }
}
