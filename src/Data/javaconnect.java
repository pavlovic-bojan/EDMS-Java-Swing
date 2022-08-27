package Data;

import java.sql.*;
import javax.swing.*;
public class javaconnect {
    
    Connection conn = null;
   public static Connection ConnecrDb(){
        try{
        
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection ("jdbc:sqlite:EDMS.sqlite");
            
            //Connection conn = DriverManager.getConnection ("jdbc:sqlite:C:\\Users\\Boki\\Desktop\\EDMS\\EDMS.sqlite");
            //JOptionPane.showMessageDialog(null, "Konekcija sa Bazom je Uspešno Uspostavljena!","Provera Konekcije sa Bazom Podataka",JOptionPane.OK_OPTION);
            return conn;
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Konekcija sa Bazom nije Uspostavljena!","Provera Konekcije sa Bazom Podataka",JOptionPane.OK_OPTION);
                return null;
        }
   }
   public static Connection KonekcijaDb(){
        try{
        
            Class.forName("org.sqlite.JDBC");
            Connection provera = DriverManager.getConnection ("jdbc:sqlite:C:\\Users\\Boki\\Desktop\\EDMS\\EDMS.sqlite");
            JOptionPane.showMessageDialog(null, "Konekcija sa Bazom je Uspešno Uspostavljena!","Provera Konekcije sa Bazom Podataka",JOptionPane.OK_OPTION);
            return provera;
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Konekcija sa Bazom nije Uspostavljena!","Provera Konekcije sa Bazom Podataka",JOptionPane.OK_OPTION);
                return null;
        }
   }    
}
