/**
 *
 * @author Bojan Pavlović
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.sql.*;
import javax.swing.*;
import java.net.*;
import java.text.SimpleDateFormat;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Servis extends javax.swing.JFrame {
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;

    public Servis() {
        initComponents();
        conn=Data.javaconnect.ConnecrDb();// Preusmerenje na klasu za konekciju na bazu
        setIcon();//Unos ikonice za prozor
        Update_Vazduhoplova();//Popuna tabele podacima
        Update_RadniNalog();//Popuna tabele podacima
        Update_IzvrseniRadovi();//Popuna tabele podacima
        Update_OdlozeniRadovi();//Popuna tabele podacima
        ComboBox_RegOznakeVazduhoplova();//izbor reg oznake vazduhoplova
        ComboBox_BrojRadnogNaloga();//izbor radnog naloga
        ComboBox_RukovodilazTehSluzbeZaposleni();//Rukovodilac tehnicke sluzbe Zaposleni
    }
    //Ovo je metod koji unosi ikonicu u prozor
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Grafika/icon.png")));
    }
    // Provera da li je internet dostupan
    public void dostupnostInterneta(){
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress("www.google.com",8080);
        try{
            sock.connect(addr,3000); 
            JOptionPane.showMessageDialog(null, "Vi ste konektovani na internet!","Provera Internet Konekcije",JOptionPane.OK_OPTION);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Vi niste konektovani na internet!","Provera Internet Konekcije",JOptionPane.OK_OPTION);
        } finally {
            try{sock.close();}
            catch(Exception e) {}        
        }
    }
    public void dostupnostInternetaAutor(){
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress("www.google.com",8080);
        try{
            sock.connect(addr,3000); 
            JOptionPane.showMessageDialog(null, "Vi napuštate EDMS sistem i odlazite do web sajta Autora!","Autorov web sajt",JOptionPane.OK_OPTION);
            String url = "http://bojanpavlovic.in.rs";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Vi niste konektovani na internet!","Provera Internet Konekcije",JOptionPane.OK_OPTION);
        } finally {
            try{sock.close();}
            catch(Exception e) {}        
        }
    }
    //Ovo je metod koji ucitava podatke u tabelu o vazduhoplovima
    private void Update_Vazduhoplova(){
        try{
            String sql="select * from vazduhoplov order by id desc";//Upit za popunu tabele
            pst=conn.prepareStatement(sql);     
            rs=pst.executeQuery();
            jTableVazduhoplov.setModel(DbUtils.resultSetToTableModel(rs));     
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Ovo je metod koji ucitava podatke u tabelu o izvrseni radovi
    private void Update_IzvrseniRadovi(){
        try{
            String sql="select izvrseni_radovi.id,radni_nalog.Broj_Naloga,izvrseni_radovi.Primljen_na_Radove,izvrseni_radovi.Preuzet,izvrseni_radovi.Napomena,izvrseni_radovi.Mesto,izvrseni_radovi.Resenje_DCV from izvrseni_radovi inner join radni_nalog on izvrseni_radovi.idRadniNalog = radni_nalog.id order by izvrseni_radovi.id desc";//Upit za popunu tabele
            pst=conn.prepareStatement(sql);     
            rs=pst.executeQuery();
            jTableIzvrseniRadovi.setModel(DbUtils.resultSetToTableModel(rs));     
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Ovo je metod koji ucitava podatke u tabelu
    private void Update_RadniNalog(){
        try{
            String sql="select radni_nalog.id,radni_nalog.Broj_Naloga,radni_nalog.Datum_Otvaranja,vazduhoplov.Registarska_Oznaka,radni_nalog.Naziv_Komponente,radni_nalog.Broj_Dela,radni_nalog.Lansirao,radni_nalog.Datum_Pocetka,radni_nalog.Datum_Zavrsetka,radni_nalog.Overio_Zavrsetak,radni_nalog.Predmet_Radova,radni_nalog.Napomena from radni_nalog inner join vazduhoplov on radni_nalog.idVazduhoplova = vazduhoplov.id order by radni_nalog.id desc";//Upit za popunu tabele
            pst=conn.prepareStatement(sql);     
            rs=pst.executeQuery();
            jTableRadniNalog.setModel(DbUtils.resultSetToTableModel(rs));        
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Ovo je metod koji ucitava podatke u tabelu
    private void Update_OdlozeniRadovi(){
        try{
            String sql="select odlozeni_radovi.id,vazduhoplov.Registarska_Oznaka,odlozeni_radovi.Izvor,odlozeni_radovi.R_BR,odlozeni_radovi.Opis,odlozeni_radovi.Datum_Opis,korisnici.Ime,odlozeni_radovi.Uradjeno,odlozeni_radovi.Datum_Uradjeno,korisnici.Ime from odlozeni_radovi inner join vazduhoplov on odlozeni_radovi.idVazduhoplova = vazduhoplov.id inner join korisnici on odlozeni_radovi.idZaposlenogOpis = korisnici.id order by odlozeni_radovi.id desc";//Upit za popunu tabele
            pst=conn.prepareStatement(sql);     
            rs=pst.executeQuery();
            jTableOdlozeniRadovi.setModel(DbUtils.resultSetToTableModel(rs));        
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //metod za popuni podataka kada se izabere kombobox radni naloga
    public void popunaPoljaRadniNalogIzvrseniRadovi(){
        //Popuniti Polja kada se izabere nesto iz combobox
        String tmp =(String)jComboBoxBrojRNIzvrseniRadovi.getSelectedItem();
        String sql="select * from radni_nalog where Broj_Naloga=?";
            try{
                pst = conn.prepareStatement(sql);
                pst.setString(1, tmp);
                rs = pst.executeQuery();
                if(rs.next()){
                        String add1=rs.getString("Predmet_Radova");
                        jTextAreaPredmetRNIzvrseniRadovi.setText(add1);
                        java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date add2 = sdf.parse(rs.getString("Datum_Pocetka"));
                        jDateChooserPocetakIzvrseniRadovi.setDate(add2);
                        java.text.SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date add3 = sdf1.parse(rs.getString("Datum_Zavrsetka"));
                        jDateChooserZavrsetakIzvrseniRadovi.setDate(add3);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }
    //metod za popuni podataka kada se izabere kombobox regOznake
    public void popunaPoljaOVazduhoplovuIzvrseniRadovi(){
        //Popuniti Polja kada se izabere nesto iz combobox
        String tmp =(String)jComboBoxOVazduhoplovuIzvrseniRadovi.getSelectedItem();
        String sql="select * from vazduhoplov where Registarska_Oznaka=?";
            try{
                pst = conn.prepareStatement(sql);
                pst.setString(1, tmp);
                rs = pst.executeQuery();
                if(rs.next()){
                        String add1=rs.getString("Proizvodjac_Tip");
                        jTextField_tipVazduhoplovaIzvrseniRadovi.setText(add1);
                        String add2=rs.getString("Seriski_Broj");
                        jTextField_serijskiBrojVazduhoplovaIzvrseniRadovi.setText(add2);
                        String add3=rs.getString("Vlasnik");
                        jTextField_vlasnikVazduhoplovaIzvrseniRadovi.setText(add3);
                        String add4=rs.getString("Nalet");
                        jTextField_naletVazduhoplovaIzvrseniRadovi.setText(add4);
                        String add5=rs.getString("Casova_Leta");
                        jTextField_casovaLetaVazduhoplovaIzvrseniRadovi.setText(add5);
                        String add6=rs.getString("Ciklusa");
                        jTextField_ciklusaVazduhoplovaIzvrseniRadovi.setText(add6);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }
    //metod za popuni podataka kada se izabere kombobox regOznake
    public void popunaPoljaOVazduhoplovu(){
        //Popuniti Polja kada se izabere nesto iz combobox
        String tmp =(String)jComboBoxOVazduhoplovu.getSelectedItem();
        String sql="select * from vazduhoplov where Registarska_Oznaka=?";     
            try{
                pst = conn.prepareStatement(sql);
                pst.setString(1, tmp);
                rs = pst.executeQuery();
                if(rs.next()){
                        String add1=rs.getString("Proizvodjac_Tip");
                        jTextField_tipVazduhoplovaRN.setText(add1);
                        String add2=rs.getString("Seriski_Broj");
                        jTextField_serijskiBrojVazduhoplovaRN.setText(add2);
                        String add3=rs.getString("Vlasnik");
                        jTextField_vlasnikVazduhoplovaRN.setText(add3);
                        String add4=rs.getString("Nalet");
                        jTextField_naletVazduhoplovaRN.setText(add4);
                        String add5=rs.getString("Casova_Leta");
                        jTextField_casovaLetaVazduhoplovaRN.setText(add5);
                        String add6=rs.getString("Ciklusa");
                        jTextField_ciklusaVazduhoplovaRN.setText(add6);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }
    //popuna boxa za vazduhoplov 
    private void ComboBox_RegOznakeVazduhoplova(){
        try{
        String sql="select * from vazduhoplov";//Upit za popunu vazduhoplova
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
            while(rs.next()){
                String Naziv=rs.getString("Registarska_Oznaka");
                jComboBoxOVazduhoplovu.addItem(Naziv);
                jComboBoxOVazduhoplovuIzvrseniRadovi.addItem(Naziv);
                jComboBoxRGOznakaOdlozeniRadovi.addItem(Naziv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna boxa za Radni Nalog 
    private void ComboBox_BrojRadnogNaloga(){
        try{
        String sql="select * from radni_nalog";//Upit za popunu radnog naloga
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
            while(rs.next()){
                String Naziv=rs.getString("Broj_Naloga");
                jComboBoxBrojRNIzvrseniRadovi.addItem(Naziv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna boxa za zaposlene 
    private void ComboBox_RukovodilazTehSluzbeZaposleni(){
        try{
        String sql="select * from korisnici";//Upit za popunu radnog naloga
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
            while(rs.next()){
                String Ime=rs.getString("Ime");
                String Prezime=rs.getString("Prezime");
                jComboBoxRukovodilacTehSluzbeIzvrseniRadovi.addItem(Ime+" "+Prezime);
                jComboZaposleniOpisOdlozeniRadovi.addItem(Ime+" "+Prezime);
                jComboZaposleniUradjenoOdlozeniRadovi.addItem(Ime+" "+Prezime);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Popuna polja Vazduhoplova
    public void PoljaVazduhoplova(){
        try{
            if(rs.next()){
                    String add1=rs.getString("id");
                    jTextField_idVazduhoplova.setText(add1);
                    String add2=rs.getString("Proizvodjac_Tip");
                    jTextField_tipVazduhoplova.setText(add2);
                    String add3=rs.getString("Registarska_Oznaka");
                    jTextField_rgVazduhoplova.setText(add3);
                    String add4=rs.getString("Seriski_Broj");
                    jTextField_serijskiBrojVazduhoplova.setText(add4);
                    String add5=rs.getString("Vlasnik");
                    jTextField_vlasnikVazduhoplova.setText(add5);
                    String add6=rs.getString("Nalet");
                    jTextField_naletVazduhoplova.setText(add6);
                    String add7=rs.getString("Casova_Leta");
                    jTextField_casovaLetaVazduhoplova.setText(add7);
                    String add8=rs.getString("Ciklusa");
                    jTextField_ciklusaVazduhoplova.setText(add8);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }
     //popuna polja radni nalog
     public void popunaPolljaRadniNalog(){
        try{
            if(rs.next()){
                    String add1=rs.getString("id");
                    jTextFieldIDRN.setText(add1);
                    String add2=rs.getString("Broj_Naloga");
                    jTextFieldBRNalogaRN.setText(add2);
                    java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add3 = sdf.parse(rs.getString("Datum_Otvaranja"));
                    jDateChooserOtvaranjeRN.setDate(add3);
                    int add4=rs.getInt("idVazduhoplova");
                    jComboBoxOVazduhoplovu.setSelectedIndex(add4);
                    String add5=rs.getString("Naziv_Komponente");
                    jTextFieldNazivRN.setText(add5);
                    String add6=rs.getString("Predmet_Radova");
                    jTextAreaPredmetRN.setText(add6);
                    String add7=rs.getString("Broj_Dela");
                    jTextFieldBRDelaRN.setText(add7);
                    String add8=rs.getString("Lansirao");
                    jTextFieldLansiraoRN.setText(add8);
                    String add9=rs.getString("Overio_Zavrsetak");
                    jTextFieldOverioRN.setText(add9);
                    java.text.SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add10 = sdf1.parse(rs.getString("Datum_Pocetka"));
                    jDateChooserPocetakRN.setDate(add10);
                    java.text.SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add11 = sdf2.parse(rs.getString("Datum_Zavrsetka"));
                    jDateChooserZavrsetakRN.setDate(add11);
                    String add12=rs.getString("Napomena");
                    jTextAreaNapomeneRN.setText(add12);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
     }    
     //popuna polja izvrseni radovi
     public void popunaPolljaIzvrseniRadovi(){
        try{
            if(rs.next()){
                    String add1=rs.getString("id");
                    jTextFieldIDIzvrseniRadovi.setText(add1);
                    String add2=rs.getString("Napomena");
                    jTextAreaNapomenaIzvrseniRadovi.setText(add2);
                    java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add3 = sdf.parse(rs.getString("Primljen_na_Radove"));
                    jDateChooserPrimljenIzvrseniRadovi.setDate(add3);
                    java.text.SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add4 = sdf1.parse(rs.getString("Preuzet"));
                    jDateChooserPreuzetIzvrseniRadovi.setDate(add4);
                    java.text.SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add5 = sdf2.parse(rs.getString("Datum_Predaje"));
                    jDateChooserDatumPredajeIzvrseniRadovi.setDate(add5);
                    int add6=rs.getInt("idRadniNalog");
                    jComboBoxBrojRNIzvrseniRadovi.setSelectedIndex(add6);
                    String add7=rs.getString("Resenje_DCV");
                    jTextFieldDCVIzvrseniRadovi.setText(add7);
                    String add8=rs.getString("Mesto");
                    jTextFieldjMestoIzvrseniRadovi.setText(add8);
                    String add9=rs.getString("Broj_Priloga");
                    jTextFieldBRPrilogaIzvrseniRadovi.setText(add9);
                    int add10=rs.getInt("idZaposleni");
                    jComboBoxRukovodilacTehSluzbeIzvrseniRadovi.setSelectedIndex(add10);
                    int add11=rs.getInt("idVazduhoplova");
                    jComboBoxOVazduhoplovuIzvrseniRadovi.setSelectedIndex(add11);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
     }
     //popuna polja izvrseni radovi
     public void popunaPolljaOdlozeniRadovi(){
        try{
            if(rs.next()){
                    String add1=rs.getString("id");
                    jTextFieldIDOdlozeniRadovi.setText(add1);
                    int add2=rs.getInt("idVazduhoplova");
                    jComboBoxRGOznakaOdlozeniRadovi.setSelectedIndex(add2);
                    String add3=rs.getString("Izvor");
                    jTextFieldIzvorOdlozeniRadovi.setText(add3);
                    String add4=rs.getString("R_BR");
                    jTextFieldRedniBrojOdlozeniRadovi.setText(add4);
                    String add5=rs.getString("Opis");
                    jTextAreaOpisOdlozeniRadovi.setText(add5);
                    java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add6 = sdf.parse(rs.getString("Datum_Opis"));
                    jDateChooserOpisOdlozeniRadovi.setDate(add6);
                    int add7=rs.getInt("idZaposlenogOpis");
                    jComboZaposleniOpisOdlozeniRadovi.setSelectedIndex(add7);
                    String add8=rs.getString("Uradjeno");
                    jTextAreaUradjenoOdlozeniRadovi.setText(add8);
                    java.text.SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add9 = sdf1.parse(rs.getString("Datum_Uradjeno"));
                    jDateChooserUradjenoOdlozeniRadovi.setDate(add9);
                    int add10=rs.getInt("idZaposlenogUradjeno");
                    jComboZaposleniUradjenoOdlozeniRadovi.setSelectedIndex(add10);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPaneServisHeliVision = new javax.swing.JTabbedPane();
        jPanelVazduhoplov = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableVazduhoplov = new javax.swing.JTable();
        jPanel_poljaVazduhoplov = new javax.swing.JPanel();
        jLabel_tipVazduhoplova = new javax.swing.JLabel();
        jLabel_rgVazduhoplova = new javax.swing.JLabel();
        jLabel_serijskiBrojVazduhoplova = new javax.swing.JLabel();
        jLabel_vlasnikVazduhoplova = new javax.swing.JLabel();
        jLabel_naletVazduhoplova = new javax.swing.JLabel();
        jLabel_casoviLetaVazduhoplova = new javax.swing.JLabel();
        jLabel_ciklusaVazduhoplova = new javax.swing.JLabel();
        jLabel_idVazduhoplova = new javax.swing.JLabel();
        jTextField_rgVazduhoplova = new javax.swing.JTextField();
        jTextField_vlasnikVazduhoplova = new javax.swing.JTextField();
        jTextField_naletVazduhoplova = new javax.swing.JTextField();
        jTextField_casovaLetaVazduhoplova = new javax.swing.JTextField();
        jTextField_ciklusaVazduhoplova = new javax.swing.JTextField();
        jTextField_idVazduhoplova = new javax.swing.JTextField();
        jTextField_serijskiBrojVazduhoplova = new javax.swing.JTextField();
        jTextField_tipVazduhoplova = new javax.swing.JTextField();
        jPanel_funkcionalnaDugmad = new javax.swing.JPanel();
        Button_deleteVazduhoplova = new javax.swing.JButton();
        Button_clearVazduhoplova = new javax.swing.JButton();
        txt_pretragaVazduhoplova = new javax.swing.JTextField();
        Button_insertVazduhoplova = new javax.swing.JButton();
        Button_updateVazduhoplova = new javax.swing.JButton();
        Button_refreshVazduhoplova = new javax.swing.JButton();
        Button_stampaVazduhoplova = new javax.swing.JButton();
        jPanelRadniNalog = new javax.swing.JPanel();
        jPanel_funkcionalnaDugmad1 = new javax.swing.JPanel();
        Button_deleteRadniNalog = new javax.swing.JButton();
        Button_clearRadniNalog = new javax.swing.JButton();
        txt_pretragaRadniNalog = new javax.swing.JTextField();
        Button_insertRadniNalog = new javax.swing.JButton();
        Button_updateRadniNalog = new javax.swing.JButton();
        Button_refreshRadniNalog = new javax.swing.JButton();
        Button_stampaRadniNalog = new javax.swing.JButton();
        jTabbedPaneRadniNalog = new javax.swing.JTabbedPane();
        jScrollPaneRadniNalog = new javax.swing.JScrollPane();
        jPanelOVazduhoplovu = new javax.swing.JPanel();
        jPanel_poljaVazduhoplov1 = new javax.swing.JPanel();
        jLabel_tipVazduhoplovaRN = new javax.swing.JLabel();
        jLabel_rgVazduhoplovaRN = new javax.swing.JLabel();
        jLabel_serijskiBrojVazduhoplovaRN = new javax.swing.JLabel();
        jLabel_vlasnikVazduhoplovaRN = new javax.swing.JLabel();
        jLabel_naletVazduhoplovaRN = new javax.swing.JLabel();
        jLabel_casoviLetaVazduhoplovaRN = new javax.swing.JLabel();
        jLabel_ciklusaVazduhoplovaRN = new javax.swing.JLabel();
        jTextField_vlasnikVazduhoplovaRN = new javax.swing.JTextField();
        jTextField_naletVazduhoplovaRN = new javax.swing.JTextField();
        jTextField_casovaLetaVazduhoplovaRN = new javax.swing.JTextField();
        jTextField_ciklusaVazduhoplovaRN = new javax.swing.JTextField();
        jTextField_serijskiBrojVazduhoplovaRN = new javax.swing.JTextField();
        jTextField_tipVazduhoplovaRN = new javax.swing.JTextField();
        jComboBoxOVazduhoplovu = new javax.swing.JComboBox();
        jScrollPanePratecaDokumentacijaRN = new javax.swing.JScrollPane();
        jPanelDokumentaRN = new javax.swing.JPanel();
        jLabelNORN = new javax.swing.JLabel();
        jLabelNazivRN = new javax.swing.JLabel();
        jLabelOznakaRN = new javax.swing.JLabel();
        jLabelNapomenaRN = new javax.swing.JLabel();
        jTextFieldNORN1 = new javax.swing.JTextField();
        jTextFieldNazivDokumentaRN1 = new javax.swing.JTextField();
        jTextFieldOznakaRN1 = new javax.swing.JTextField();
        jTextFieldNapomenaRN1 = new javax.swing.JTextField();
        jTextFieldNORN2 = new javax.swing.JTextField();
        jTextFieldNazivDokumentaRN2 = new javax.swing.JTextField();
        jTextFieldOznakaRN2 = new javax.swing.JTextField();
        jTextFieldNapomenaRN2 = new javax.swing.JTextField();
        jTextFieldNORN3 = new javax.swing.JTextField();
        jTextFieldNazivDokumentaRN3 = new javax.swing.JTextField();
        jTextFieldOznakaRN3 = new javax.swing.JTextField();
        jTextFieldNapomenaRN3 = new javax.swing.JTextField();
        jTextFieldNORN4 = new javax.swing.JTextField();
        jTextFieldNazivDokumentaRN4 = new javax.swing.JTextField();
        jTextFieldOznakaRN4 = new javax.swing.JTextField();
        jTextFieldNapomenaRN4 = new javax.swing.JTextField();
        jTextFieldNORN5 = new javax.swing.JTextField();
        jTextFieldNazivDokumentaRN5 = new javax.swing.JTextField();
        jTextFieldOznakaRN5 = new javax.swing.JTextField();
        jTextFieldNapomenaRN5 = new javax.swing.JTextField();
        jTextFieldNORN6 = new javax.swing.JTextField();
        jTextFieldNazivDokumentaRN6 = new javax.swing.JTextField();
        jTextFieldOznakaRN6 = new javax.swing.JTextField();
        jTextFieldNapomenaRN6 = new javax.swing.JTextField();
        jScrollPaneNalogRadni = new javax.swing.JScrollPane();
        jPanelPoljaZaRadniNalog = new javax.swing.JPanel();
        jLabelIDRN = new javax.swing.JLabel();
        jLabelBRNalogRN = new javax.swing.JLabel();
        jLabelOtvaranjeRN = new javax.swing.JLabel();
        jLabelPredmerRN = new javax.swing.JLabel();
        jLabelNzivRN = new javax.swing.JLabel();
        jLabelBRDelaRN = new javax.swing.JLabel();
        jLabelLansiraoRN = new javax.swing.JLabel();
        jLabelPocetakRN = new javax.swing.JLabel();
        jLabelZavrsetakRN = new javax.swing.JLabel();
        jLabelOveriRN = new javax.swing.JLabel();
        jTextFieldIDRN = new javax.swing.JTextField();
        jTextFieldBRNalogaRN = new javax.swing.JTextField();
        jDateChooserOtvaranjeRN = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaPredmetRN = new javax.swing.JTextArea();
        jTextFieldNazivRN = new javax.swing.JTextField();
        jTextFieldBRDelaRN = new javax.swing.JTextField();
        jTextFieldLansiraoRN = new javax.swing.JTextField();
        jDateChooserPocetakRN = new com.toedter.calendar.JDateChooser();
        jDateChooserZavrsetakRN = new com.toedter.calendar.JDateChooser();
        jTextFieldOverioRN = new javax.swing.JTextField();
        jLabelNapomeneRN = new javax.swing.JLabel();
        jScrollPaneNapomenaRN = new javax.swing.JScrollPane();
        jTextAreaNapomeneRN = new javax.swing.JTextArea();
        jScrollPaneTableRadniNalog = new javax.swing.JScrollPane();
        jTableRadniNalog = new javax.swing.JTable();
        jPanelIzvrseniRadovi = new javax.swing.JPanel();
        jPanel_funkcionalnaDugmadIzvrseniRadovi = new javax.swing.JPanel();
        Button_deleteIzvrseniRadovi = new javax.swing.JButton();
        Button_clearIzvrseniRadovi = new javax.swing.JButton();
        txt_pretragaIzvrseniRadovi = new javax.swing.JTextField();
        Button_insertIzvrseniRadovi = new javax.swing.JButton();
        Button_updateIzvrseniRadovi = new javax.swing.JButton();
        Button_refreshIzvrseniRadovi = new javax.swing.JButton();
        Button_stampaIzvrseniRadovi = new javax.swing.JButton();
        jTabbedPaneIzvrseniRadovi = new javax.swing.JTabbedPane();
        jScrollPaneVazduhoplovIzvrseniRadovi = new javax.swing.JScrollPane();
        jPanelOVazduhoplovuIzvrseniRadovi = new javax.swing.JPanel();
        jPanel_poljaVazduhoplovIzvrseniRadovi = new javax.swing.JPanel();
        jLabel_tipVazduhoplovaIzvrseniRadovi = new javax.swing.JLabel();
        jLabel_rgVazduhoplovaIzvrseniRadovi = new javax.swing.JLabel();
        jLabel_serijskiBrojVazduhoplovaIzvrseniRadovi = new javax.swing.JLabel();
        jLabel_vlasnikVazduhoplovaIzvrseniRadovi = new javax.swing.JLabel();
        jLabel_naletVazduhoplovaIzvrseniRadovi = new javax.swing.JLabel();
        jLabel_casoviLetaVazduhoplovaIzvrseniRadovi = new javax.swing.JLabel();
        jLabel_ciklusaVazduhoplovaIzvrseniRadovi = new javax.swing.JLabel();
        jTextField_vlasnikVazduhoplovaIzvrseniRadovi = new javax.swing.JTextField();
        jTextField_naletVazduhoplovaIzvrseniRadovi = new javax.swing.JTextField();
        jTextField_casovaLetaVazduhoplovaIzvrseniRadovi = new javax.swing.JTextField();
        jTextField_ciklusaVazduhoplovaIzvrseniRadovi = new javax.swing.JTextField();
        jTextField_serijskiBrojVazduhoplovaIzvrseniRadovi = new javax.swing.JTextField();
        jTextField_tipVazduhoplovaIzvrseniRadovi = new javax.swing.JTextField();
        jComboBoxOVazduhoplovuIzvrseniRadovi = new javax.swing.JComboBox();
        jScrollPaneNalogRadniIzvrseniRadovi = new javax.swing.JScrollPane();
        jPanelPoljaZaRadniNalogIzvrseniRadovi = new javax.swing.JPanel();
        jLabelBrojRNIzvrseniRadovi = new javax.swing.JLabel();
        jLabelPredmerIzvrseniRadovi = new javax.swing.JLabel();
        jScrollPaneIzvrseniRadovi = new javax.swing.JScrollPane();
        jTextAreaPredmetRNIzvrseniRadovi = new javax.swing.JTextArea();
        jComboBoxBrojRNIzvrseniRadovi = new javax.swing.JComboBox();
        jDateChooserPocetakIzvrseniRadovi = new com.toedter.calendar.JDateChooser();
        jDateChooserZavrsetakIzvrseniRadovi = new com.toedter.calendar.JDateChooser();
        jLabelPocetakIzvrseniRadovi = new javax.swing.JLabel();
        jLabelZavrsetakIzvrseniRadovi = new javax.swing.JLabel();
        jScrollPaneIzvrseniRadoviPanel = new javax.swing.JScrollPane();
        jPanelPanelIzvrseniRadovi = new javax.swing.JPanel();
        jLabelPrimljenIzvrseniRadovi = new javax.swing.JLabel();
        jDateChooserPreuzetIzvrseniRadovi = new com.toedter.calendar.JDateChooser();
        jDateChooserPrimljenIzvrseniRadovi = new com.toedter.calendar.JDateChooser();
        jLabelPreuzeIzvrseniRadovi = new javax.swing.JLabel();
        jScrollPaneIzvrseniRadovi1 = new javax.swing.JScrollPane();
        jTextAreaNapomenaIzvrseniRadovi = new javax.swing.JTextArea();
        jLabelNapomenaIzvrseniRadovi = new javax.swing.JLabel();
        jLabelIDIzvrseniRadovi = new javax.swing.JLabel();
        jTextFieldIDIzvrseniRadovi = new javax.swing.JTextField();
        jComboBoxRukovodilacTehSluzbeIzvrseniRadovi = new javax.swing.JComboBox();
        jTextFieldDCVIzvrseniRadovi = new javax.swing.JTextField();
        jTextFieldjMestoIzvrseniRadovi = new javax.swing.JTextField();
        jTextFieldBRPrilogaIzvrseniRadovi = new javax.swing.JTextField();
        jDateChooserDatumPredajeIzvrseniRadovi = new com.toedter.calendar.JDateChooser();
        jLabelDatumPredajeIzvrseniRadovi = new javax.swing.JLabel();
        jLabelRukovodilacTehSluzbeIzvrseniRadovi = new javax.swing.JLabel();
        jLabelDCVIzvrseniRadovi = new javax.swing.JLabel();
        jLabelMestoIzvrseniRadovi = new javax.swing.JLabel();
        jLabelBRPrilogaIzvrseniRadovi = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableIzvrseniRadovi = new javax.swing.JTable();
        jPanelOdlozeniRadovi = new javax.swing.JPanel();
        jScrollPaneOdlozeniRadoviTable = new javax.swing.JScrollPane();
        jTableOdlozeniRadovi = new javax.swing.JTable();
        jPanelOdlozeniRadoviPolja = new javax.swing.JPanel();
        jLabelRGBroj = new javax.swing.JLabel();
        jTextFieldIzvorOdlozeniRadovi = new javax.swing.JTextField();
        jLabelIzvorOdlozeniRadovi = new javax.swing.JLabel();
        jComboBoxRGOznakaOdlozeniRadovi = new javax.swing.JComboBox();
        jLabelOpisOdlozeniRadovi = new javax.swing.JLabel();
        jScrollPaneOpisOdlozeniRadovi = new javax.swing.JScrollPane();
        jTextAreaOpisOdlozeniRadovi = new javax.swing.JTextArea();
        jLabelZaposleniOdlozeniRadovi = new javax.swing.JLabel();
        jComboZaposleniOpisOdlozeniRadovi = new javax.swing.JComboBox();
        jLabelUradjenoOdlozeniRadovi = new javax.swing.JLabel();
        jScrollPaneUradjenoOdlozeniRadovi = new javax.swing.JScrollPane();
        jTextAreaUradjenoOdlozeniRadovi = new javax.swing.JTextArea();
        jLabelZaposleniUradjenoOdlozeniRadovi = new javax.swing.JLabel();
        jComboZaposleniUradjenoOdlozeniRadovi = new javax.swing.JComboBox();
        jLabelRedniBrojOdlozeniRadovi = new javax.swing.JLabel();
        jTextFieldRedniBrojOdlozeniRadovi = new javax.swing.JTextField();
        jDateChooserOpisOdlozeniRadovi = new com.toedter.calendar.JDateChooser();
        jDateChooserUradjenoOdlozeniRadovi = new com.toedter.calendar.JDateChooser();
        jLabelDatumUradjenoOdlozeniRadovi = new javax.swing.JLabel();
        jLabelDatumOpisOdlozeniRadovi = new javax.swing.JLabel();
        jLabelIdOdlozeniRadovi = new javax.swing.JLabel();
        jTextFieldIDOdlozeniRadovi = new javax.swing.JTextField();
        txt_pretragaOdlozeniRadovi = new javax.swing.JTextField();
        Button_insertOdlozeniRadovi = new javax.swing.JButton();
        Button_clearOdlozeniRadovi = new javax.swing.JButton();
        Button_updateOdlozeniRadovi = new javax.swing.JButton();
        Button_refreshOdlozeniRadovi = new javax.swing.JButton();
        Button_stampaOdlozeniRadovi = new javax.swing.JButton();
        Button_deleteOdlozeniRadovi = new javax.swing.JButton();
        ToolBar_OpsteFunkcije = new javax.swing.JToolBar();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabelPozdravnaPoruka = new javax.swing.JLabel();
        jLabelUser = new javax.swing.JLabel();
        Button_singout = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        Button_internet = new javax.swing.JButton();
        Button_proveraKonekcijaBaze = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        Button_Exit = new javax.swing.JButton();
        MenuBar_adminMeni = new javax.swing.JMenuBar();
        Menu_opste = new javax.swing.JMenu();
        MenuItem_internet = new javax.swing.JMenuItem();
        MenuItem_baza = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        MenuItem_LogOut = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        MenuItem_Exit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Panel za Administraciju Servisa");
        setPreferredSize(new java.awt.Dimension(744, 701));
        setResizable(false);

        jTabbedPaneServisHeliVision.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jTableVazduhoplov.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableVazduhoplov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableVazduhoplovMouseClicked(evt);
            }
        });
        jTableVazduhoplov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableVazduhoplovKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTableVazduhoplov);

        jPanel_poljaVazduhoplov.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Podaci o Vazduhoplovu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 204, 0))); // NOI18N

        jLabel_tipVazduhoplova.setText("Proizvođač Tip");

        jLabel_rgVazduhoplova.setText("Registarska Oznaka");

        jLabel_serijskiBrojVazduhoplova.setText("Seriski Broj");

        jLabel_vlasnikVazduhoplova.setText("Vlasnik");

        jLabel_naletVazduhoplova.setText("Nalet");

        jLabel_casoviLetaVazduhoplova.setText("Časova Leta");

        jLabel_ciklusaVazduhoplova.setText("Ciklusa");

        jLabel_idVazduhoplova.setText("ID");

        jTextField_rgVazduhoplova.setToolTipText("Unesite Registarske Oznake Vazduhoplova");

        jTextField_vlasnikVazduhoplova.setToolTipText("Unesite Vlasnika Vazduhoplova");

        jTextField_naletVazduhoplova.setToolTipText("Unesite Nalet Vazduhoplova");

        jTextField_casovaLetaVazduhoplova.setToolTipText("Unesite Časove Leta Vazduhoplova");

        jTextField_ciklusaVazduhoplova.setToolTipText("Unesite Ciklus Vazduhoplova");

        jTextField_idVazduhoplova.setToolTipText("Unesite ID Vazduhoplova");

        jTextField_serijskiBrojVazduhoplova.setToolTipText("Unesite Serijski Broj Vazduhoplova");

        jTextField_tipVazduhoplova.setToolTipText("Unesite Proizvođača i Tip Vazduhoplova");

        javax.swing.GroupLayout jPanel_poljaVazduhoplovLayout = new javax.swing.GroupLayout(jPanel_poljaVazduhoplov);
        jPanel_poljaVazduhoplov.setLayout(jPanel_poljaVazduhoplovLayout);
        jPanel_poljaVazduhoplovLayout.setHorizontalGroup(
            jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_poljaVazduhoplovLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel_poljaVazduhoplovLayout.createSequentialGroup()
                            .addComponent(jLabel_idVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField_idVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel_poljaVazduhoplovLayout.createSequentialGroup()
                            .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel_rgVazduhoplova)
                                .addComponent(jLabel_vlasnikVazduhoplova)
                                .addComponent(jLabel_naletVazduhoplova)
                                .addComponent(jLabel_casoviLetaVazduhoplova)
                                .addComponent(jLabel_tipVazduhoplova))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField_tipVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_casovaLetaVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_naletVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_vlasnikVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_rgVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_ciklusaVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_serijskiBrojVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel_ciklusaVazduhoplova)
                    .addComponent(jLabel_serijskiBrojVazduhoplova)))
        );
        jPanel_poljaVazduhoplovLayout.setVerticalGroup(
            jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_poljaVazduhoplovLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_idVazduhoplova)
                    .addComponent(jTextField_idVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_tipVazduhoplova)
                    .addComponent(jTextField_tipVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_rgVazduhoplova)
                    .addComponent(jTextField_rgVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_serijskiBrojVazduhoplova)
                    .addComponent(jTextField_serijskiBrojVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_vlasnikVazduhoplova)
                    .addComponent(jTextField_vlasnikVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_naletVazduhoplova)
                    .addComponent(jTextField_naletVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_casoviLetaVazduhoplova)
                    .addComponent(jTextField_casovaLetaVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ciklusaVazduhoplova)
                    .addComponent(jTextField_ciklusaVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel_funkcionalnaDugmad.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pretraga", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 204, 0))); // NOI18N

        Button_deleteVazduhoplova.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteVazduhoplova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteVazduhoplova.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteVazduhoplova.setFocusable(false);
        Button_deleteVazduhoplova.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteVazduhoplova.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteVazduhoplova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteVazduhoplovaActionPerformed(evt);
            }
        });

        Button_clearVazduhoplova.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearVazduhoplova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearVazduhoplova.setToolTipText("Očisti polja za unos podataka");
        Button_clearVazduhoplova.setFocusable(false);
        Button_clearVazduhoplova.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearVazduhoplova.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearVazduhoplova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearVazduhoplovaActionPerformed(evt);
            }
        });

        txt_pretragaVazduhoplova.setToolTipText("Pretraga Vazduhoplova Po Registracionoj Oznaci");
        txt_pretragaVazduhoplova.setMinimumSize(new java.awt.Dimension(6, 40));
        txt_pretragaVazduhoplova.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_pretragaVazduhoplovaKeyReleased(evt);
            }
        });

        Button_insertVazduhoplova.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertVazduhoplova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertVazduhoplova.setToolTipText("Unos novih podataka u bazu");
        Button_insertVazduhoplova.setFocusable(false);
        Button_insertVazduhoplova.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertVazduhoplova.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertVazduhoplova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertVazduhoplovaActionPerformed(evt);
            }
        });

        Button_updateVazduhoplova.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateVazduhoplova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateVazduhoplova.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateVazduhoplova.setFocusable(false);
        Button_updateVazduhoplova.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateVazduhoplova.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateVazduhoplova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateVazduhoplovaActionPerformed(evt);
            }
        });

        Button_refreshVazduhoplova.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshVazduhoplova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshVazduhoplova.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshVazduhoplova.setFocusable(false);
        Button_refreshVazduhoplova.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshVazduhoplova.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshVazduhoplova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshVazduhoplovaActionPerformed(evt);
            }
        });

        Button_stampaVazduhoplova.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaVazduhoplova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaVazduhoplova.setToolTipText("Štampa podataka iz tabele");
        Button_stampaVazduhoplova.setFocusable(false);
        Button_stampaVazduhoplova.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaVazduhoplova.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaVazduhoplova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaVazduhoplovaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_funkcionalnaDugmadLayout = new javax.swing.GroupLayout(jPanel_funkcionalnaDugmad);
        jPanel_funkcionalnaDugmad.setLayout(jPanel_funkcionalnaDugmadLayout);
        jPanel_funkcionalnaDugmadLayout.setHorizontalGroup(
            jPanel_funkcionalnaDugmadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_funkcionalnaDugmadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_funkcionalnaDugmadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_funkcionalnaDugmadLayout.createSequentialGroup()
                        .addGroup(jPanel_funkcionalnaDugmadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Button_insertVazduhoplova, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_clearVazduhoplova, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_funkcionalnaDugmadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_funkcionalnaDugmadLayout.createSequentialGroup()
                                .addComponent(Button_stampaVazduhoplova)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_refreshVazduhoplova))
                            .addGroup(jPanel_funkcionalnaDugmadLayout.createSequentialGroup()
                                .addComponent(Button_updateVazduhoplova)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_deleteVazduhoplova))))
                    .addComponent(txt_pretragaVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel_funkcionalnaDugmadLayout.setVerticalGroup(
            jPanel_funkcionalnaDugmadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_funkcionalnaDugmadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretragaVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_funkcionalnaDugmadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_clearVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_stampaVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_refreshVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_funkcionalnaDugmadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Button_insertVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_updateVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_deleteVazduhoplova, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelVazduhoplovLayout = new javax.swing.GroupLayout(jPanelVazduhoplov);
        jPanelVazduhoplov.setLayout(jPanelVazduhoplovLayout);
        jPanelVazduhoplovLayout.setHorizontalGroup(
            jPanelVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVazduhoplovLayout.createSequentialGroup()
                .addGroup(jPanelVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_funkcionalnaDugmad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_poljaVazduhoplov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE))
        );
        jPanelVazduhoplovLayout.setVerticalGroup(
            jPanelVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVazduhoplovLayout.createSequentialGroup()
                .addComponent(jPanel_funkcionalnaDugmad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel_poljaVazduhoplov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );

        jTabbedPaneServisHeliVision.addTab("Vazduhoplov", new javax.swing.ImageIcon(getClass().getResource("/Grafika/icon.png")), jPanelVazduhoplov); // NOI18N

        jPanel_funkcionalnaDugmad1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pretraga", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 204, 0))); // NOI18N

        Button_deleteRadniNalog.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteRadniNalog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteRadniNalog.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteRadniNalog.setFocusable(false);
        Button_deleteRadniNalog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteRadniNalog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteRadniNalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteRadniNalogActionPerformed(evt);
            }
        });

        Button_clearRadniNalog.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearRadniNalog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearRadniNalog.setToolTipText("Očisti polja za unos podataka");
        Button_clearRadniNalog.setFocusable(false);
        Button_clearRadniNalog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearRadniNalog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearRadniNalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearRadniNalogActionPerformed(evt);
            }
        });

        txt_pretragaRadniNalog.setToolTipText("Pretraga Radnog Naloga po Broju");
        txt_pretragaRadniNalog.setMinimumSize(new java.awt.Dimension(6, 40));
        txt_pretragaRadniNalog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_pretragaRadniNalogKeyReleased(evt);
            }
        });

        Button_insertRadniNalog.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertRadniNalog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertRadniNalog.setToolTipText("Unos novih podataka u bazu");
        Button_insertRadniNalog.setFocusable(false);
        Button_insertRadniNalog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertRadniNalog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertRadniNalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertRadniNalogActionPerformed(evt);
            }
        });

        Button_updateRadniNalog.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateRadniNalog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateRadniNalog.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateRadniNalog.setFocusable(false);
        Button_updateRadniNalog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateRadniNalog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateRadniNalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateRadniNalogActionPerformed(evt);
            }
        });

        Button_refreshRadniNalog.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshRadniNalog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshRadniNalog.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshRadniNalog.setFocusable(false);
        Button_refreshRadniNalog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshRadniNalog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshRadniNalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshRadniNalogActionPerformed(evt);
            }
        });

        Button_stampaRadniNalog.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaRadniNalog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Adobe-PDF-Document-icon.png"))); // NOI18N
        Button_stampaRadniNalog.setToolTipText("Generisanje Dokumenta u PDF formatu koji možete štampati");
        Button_stampaRadniNalog.setFocusable(false);
        Button_stampaRadniNalog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaRadniNalog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaRadniNalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaRadniNalogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_funkcionalnaDugmad1Layout = new javax.swing.GroupLayout(jPanel_funkcionalnaDugmad1);
        jPanel_funkcionalnaDugmad1.setLayout(jPanel_funkcionalnaDugmad1Layout);
        jPanel_funkcionalnaDugmad1Layout.setHorizontalGroup(
            jPanel_funkcionalnaDugmad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_funkcionalnaDugmad1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_funkcionalnaDugmad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_funkcionalnaDugmad1Layout.createSequentialGroup()
                        .addGroup(jPanel_funkcionalnaDugmad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Button_insertRadniNalog, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_clearRadniNalog, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_funkcionalnaDugmad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_funkcionalnaDugmad1Layout.createSequentialGroup()
                                .addComponent(Button_stampaRadniNalog)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_refreshRadniNalog))
                            .addGroup(jPanel_funkcionalnaDugmad1Layout.createSequentialGroup()
                                .addComponent(Button_updateRadniNalog)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_deleteRadniNalog))))
                    .addComponent(txt_pretragaRadniNalog, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_funkcionalnaDugmad1Layout.setVerticalGroup(
            jPanel_funkcionalnaDugmad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_funkcionalnaDugmad1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretragaRadniNalog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_funkcionalnaDugmad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_clearRadniNalog, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_stampaRadniNalog, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_refreshRadniNalog, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_funkcionalnaDugmad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Button_insertRadniNalog, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_updateRadniNalog, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_deleteRadniNalog, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPaneRadniNalog.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jPanel_poljaVazduhoplov1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ovo Nemožete Menjati", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel_tipVazduhoplovaRN.setText("Proizvođač Tip");

        jLabel_rgVazduhoplovaRN.setText("Reg. Oznaka");

        jLabel_serijskiBrojVazduhoplovaRN.setText("Seriski Broj");

        jLabel_vlasnikVazduhoplovaRN.setText("Vlasnik");

        jLabel_naletVazduhoplovaRN.setText("Nalet");

        jLabel_casoviLetaVazduhoplovaRN.setText("Časova Leta");

        jLabel_ciklusaVazduhoplovaRN.setText("Ciklusa");

        jTextField_vlasnikVazduhoplovaRN.setToolTipText("Vlasnik Izabranog Vazduhoplova");

        jTextField_naletVazduhoplovaRN.setToolTipText(" Nalet Izabranog Vazduhoplova");

        jTextField_casovaLetaVazduhoplovaRN.setToolTipText("Časovi Leta  Izabranog Vazduhoplova");

        jTextField_ciklusaVazduhoplovaRN.setToolTipText(" Ciklus Izabranog Vazduhoplova");

        jTextField_serijskiBrojVazduhoplovaRN.setToolTipText("Serijski Broj  Izabranog Vazduhoplova");

        jTextField_tipVazduhoplovaRN.setToolTipText("Proizvođača i Tip Izabranog Vazduhoplova");

        jComboBoxOVazduhoplovu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Vazduhoplov" }));
        jComboBoxOVazduhoplovu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxOVazduhoplovuPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel_poljaVazduhoplov1Layout = new javax.swing.GroupLayout(jPanel_poljaVazduhoplov1);
        jPanel_poljaVazduhoplov1.setLayout(jPanel_poljaVazduhoplov1Layout);
        jPanel_poljaVazduhoplov1Layout.setHorizontalGroup(
            jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_poljaVazduhoplov1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_ciklusaVazduhoplovaRN)
                    .addComponent(jLabel_serijskiBrojVazduhoplovaRN)
                    .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_poljaVazduhoplov1Layout.createSequentialGroup()
                            .addComponent(jLabel_rgVazduhoplovaRN)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                            .addComponent(jComboBoxOVazduhoplovu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_poljaVazduhoplov1Layout.createSequentialGroup()
                            .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel_vlasnikVazduhoplovaRN)
                                .addComponent(jLabel_naletVazduhoplovaRN)
                                .addComponent(jLabel_casoviLetaVazduhoplovaRN)
                                .addComponent(jLabel_tipVazduhoplovaRN))
                            .addGap(36, 36, 36)
                            .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField_tipVazduhoplovaRN, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                .addComponent(jTextField_casovaLetaVazduhoplovaRN)
                                .addComponent(jTextField_naletVazduhoplovaRN)
                                .addComponent(jTextField_vlasnikVazduhoplovaRN)
                                .addComponent(jTextField_ciklusaVazduhoplovaRN)
                                .addComponent(jTextField_serijskiBrojVazduhoplovaRN))))))
        );
        jPanel_poljaVazduhoplov1Layout.setVerticalGroup(
            jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_poljaVazduhoplov1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_rgVazduhoplovaRN)
                    .addComponent(jComboBoxOVazduhoplovu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_tipVazduhoplovaRN)
                    .addComponent(jTextField_tipVazduhoplovaRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_serijskiBrojVazduhoplovaRN)
                    .addComponent(jTextField_serijskiBrojVazduhoplovaRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_vlasnikVazduhoplovaRN)
                    .addComponent(jTextField_vlasnikVazduhoplovaRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_naletVazduhoplovaRN)
                    .addComponent(jTextField_naletVazduhoplovaRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_casoviLetaVazduhoplovaRN)
                    .addComponent(jTextField_casovaLetaVazduhoplovaRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplov1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ciklusaVazduhoplovaRN)
                    .addComponent(jTextField_ciklusaVazduhoplovaRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelOVazduhoplovuLayout = new javax.swing.GroupLayout(jPanelOVazduhoplovu);
        jPanelOVazduhoplovu.setLayout(jPanelOVazduhoplovuLayout);
        jPanelOVazduhoplovuLayout.setHorizontalGroup(
            jPanelOVazduhoplovuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOVazduhoplovuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_poljaVazduhoplov1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanelOVazduhoplovuLayout.setVerticalGroup(
            jPanelOVazduhoplovuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOVazduhoplovuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel_poljaVazduhoplov1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPaneRadniNalog.setViewportView(jPanelOVazduhoplovu);

        jTabbedPaneRadniNalog.addTab("Vazduhoplov", new javax.swing.ImageIcon(getClass().getResource("/Grafika/icon.png")), jScrollPaneRadniNalog); // NOI18N

        jLabelNORN.setText("NO");

        jLabelNazivRN.setText("Naziv Dokumenta");

        jLabelOznakaRN.setText("Oznaka");

        jLabelNapomenaRN.setText("Napomena");

        jTextFieldNORN1.setToolTipText("Unesite NO");

        jTextFieldNazivDokumentaRN1.setToolTipText("Unesite Naziv Dokumenta");

        jTextFieldOznakaRN1.setToolTipText("Unesite Oznaku");

        jTextFieldNapomenaRN1.setToolTipText("Unesite Napomenu");

        jTextFieldNORN2.setToolTipText("Unesite NO");

        jTextFieldNazivDokumentaRN2.setToolTipText("Unesite Naziv Dokumenta");

        jTextFieldOznakaRN2.setToolTipText("Unesite Oznaku");

        jTextFieldNapomenaRN2.setToolTipText("Unesite Napomenu");

        jTextFieldNORN3.setToolTipText("Unesite NO");

        jTextFieldNazivDokumentaRN3.setToolTipText("Unesite Naziv Dokumenta");

        jTextFieldOznakaRN3.setToolTipText("Unesite Oznaku");

        jTextFieldNapomenaRN3.setToolTipText("Unesite Napomenu");

        jTextFieldNORN4.setToolTipText("Unesite NO");

        jTextFieldNazivDokumentaRN4.setToolTipText("Unesite Naziv Dokumenta");

        jTextFieldOznakaRN4.setToolTipText("Unesite Oznaku");

        jTextFieldNapomenaRN4.setToolTipText("Unesite Napomenu");

        jTextFieldNORN5.setToolTipText("Unesite NO");

        jTextFieldNazivDokumentaRN5.setToolTipText("Unesite Naziv Dokumenta");

        jTextFieldOznakaRN5.setToolTipText("Unesite Oznaku");

        jTextFieldNapomenaRN5.setToolTipText("Unesite Napomenu");

        jTextFieldNORN6.setToolTipText("Unesite NO");

        jTextFieldNazivDokumentaRN6.setToolTipText("Unesite Naziv Dokumenta");

        jTextFieldOznakaRN6.setToolTipText("Unesite Oznaku");

        jTextFieldNapomenaRN6.setToolTipText("Unesite Napomenu");

        javax.swing.GroupLayout jPanelDokumentaRNLayout = new javax.swing.GroupLayout(jPanelDokumentaRN);
        jPanelDokumentaRN.setLayout(jPanelDokumentaRNLayout);
        jPanelDokumentaRNLayout.setHorizontalGroup(
            jPanelDokumentaRNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDokumentaRNLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabelNORN)
                .addGap(32, 32, 32)
                .addComponent(jLabelNazivRN)
                .addGap(31, 31, 31)
                .addComponent(jLabelOznakaRN)
                .addGap(29, 29, 29)
                .addComponent(jLabelNapomenaRN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDokumentaRNLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanelDokumentaRNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDokumentaRNLayout.createSequentialGroup()
                        .addComponent(jTextFieldNORN1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNazivDokumentaRN1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldOznakaRN1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNapomenaRN1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDokumentaRNLayout.createSequentialGroup()
                        .addComponent(jTextFieldNORN2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNazivDokumentaRN2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldOznakaRN2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNapomenaRN2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDokumentaRNLayout.createSequentialGroup()
                        .addComponent(jTextFieldNORN3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNazivDokumentaRN3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldOznakaRN3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNapomenaRN3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDokumentaRNLayout.createSequentialGroup()
                        .addComponent(jTextFieldNORN4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNazivDokumentaRN4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldOznakaRN4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNapomenaRN4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDokumentaRNLayout.createSequentialGroup()
                        .addComponent(jTextFieldNORN5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNazivDokumentaRN5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldOznakaRN5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNapomenaRN5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDokumentaRNLayout.createSequentialGroup()
                        .addComponent(jTextFieldNORN6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNazivDokumentaRN6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldOznakaRN6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNapomenaRN6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelDokumentaRNLayout.setVerticalGroup(
            jPanelDokumentaRNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDokumentaRNLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDokumentaRNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNORN)
                    .addComponent(jLabelNazivRN)
                    .addComponent(jLabelOznakaRN)
                    .addComponent(jLabelNapomenaRN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDokumentaRNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNORN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNazivDokumentaRN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOznakaRN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNapomenaRN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDokumentaRNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNORN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNazivDokumentaRN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOznakaRN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNapomenaRN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDokumentaRNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNORN3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNazivDokumentaRN3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOznakaRN3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNapomenaRN3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDokumentaRNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNORN4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNazivDokumentaRN4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOznakaRN4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNapomenaRN4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDokumentaRNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNORN5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNazivDokumentaRN5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOznakaRN5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNapomenaRN5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDokumentaRNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNORN6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNazivDokumentaRN6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOznakaRN6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNapomenaRN6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(226, Short.MAX_VALUE))
        );

        jScrollPanePratecaDokumentacijaRN.setViewportView(jPanelDokumentaRN);

        jTabbedPaneRadniNalog.addTab("Prateća Dokumentacija za Radni Nalog", new javax.swing.ImageIcon(getClass().getResource("/Grafika/Document-Copy-icon.png")), jScrollPanePratecaDokumentacijaRN); // NOI18N

        jLabelIDRN.setText("id");

        jLabelBRNalogRN.setText("Broj Naloga");

        jLabelOtvaranjeRN.setText("Datum Otvaranja");

        jLabelPredmerRN.setText("Predmet Radova");

        jLabelNzivRN.setText("Naziv Komponente");

        jLabelBRDelaRN.setText("Broj Dela");

        jLabelLansiraoRN.setText("Lansirao");

        jLabelPocetakRN.setText("Datum Početka");

        jLabelZavrsetakRN.setText("Datum Završetka");

        jLabelOveriRN.setText("Overio Završetak");

        jTextFieldIDRN.setToolTipText("Unesite Identifikacioni Broj Radnog Naloga");

        jTextFieldBRNalogaRN.setToolTipText("Unesite Broj Naloga");

        jDateChooserOtvaranjeRN.setToolTipText("Unesite Datum Otvaranja Radnog Naloga");
        jDateChooserOtvaranjeRN.setDateFormatString("yyyy-MM-dd");

        jTextAreaPredmetRN.setColumns(20);
        jTextAreaPredmetRN.setRows(5);
        jTextAreaPredmetRN.setToolTipText("Unesite Predmet Radova");
        jScrollPane1.setViewportView(jTextAreaPredmetRN);

        jTextFieldNazivRN.setToolTipText("Unesite Naziv Komponente");

        jTextFieldBRDelaRN.setToolTipText("Unesite Broj Dela");

        jTextFieldLansiraoRN.setToolTipText("Unesite Lansirao");

        jDateChooserPocetakRN.setToolTipText("Unesite Datum Početka");
        jDateChooserPocetakRN.setDateFormatString("yyyy-MM-dd");

        jDateChooserZavrsetakRN.setToolTipText("Unesite Datum Završetka");
        jDateChooserZavrsetakRN.setDateFormatString("yyyy-MM-dd");

        jTextFieldOverioRN.setToolTipText("Unesite ko je overio završetak");

        jLabelNapomeneRN.setText("Napomene");

        jTextAreaNapomeneRN.setColumns(20);
        jTextAreaNapomeneRN.setRows(5);
        jTextAreaNapomeneRN.setToolTipText("Unesite Napomenu");
        jScrollPaneNapomenaRN.setViewportView(jTextAreaNapomeneRN);

        javax.swing.GroupLayout jPanelPoljaZaRadniNalogLayout = new javax.swing.GroupLayout(jPanelPoljaZaRadniNalog);
        jPanelPoljaZaRadniNalog.setLayout(jPanelPoljaZaRadniNalogLayout);
        jPanelPoljaZaRadniNalogLayout.setHorizontalGroup(
            jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelBRNalogRN)
                    .addComponent(jLabelIDRN)
                    .addGroup(jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                        .addComponent(jLabelOtvaranjeRN)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldBRNalogaRN, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooserOtvaranjeRN, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldIDRN, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                        .addComponent(jLabelPredmerRN)
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTextFieldBRDelaRN, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                            .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelNzivRN)
                                .addComponent(jLabelBRDelaRN)
                                .addComponent(jLabelLansiraoRN))
                            .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jTextFieldLansiraoRN, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextFieldNazivRN, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                            .addComponent(jLabelZavrsetakRN)
                            .addGap(18, 18, 18)
                            .addComponent(jDateChooserZavrsetakRN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                            .addComponent(jLabelPocetakRN)
                            .addGap(27, 27, 27)
                            .addComponent(jDateChooserPocetakRN, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                            .addComponent(jLabelNapomeneRN)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPaneNapomenaRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                            .addComponent(jLabelOveriRN)
                            .addGap(18, 18, 18)
                            .addComponent(jTextFieldOverioRN, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanelPoljaZaRadniNalogLayout.setVerticalGroup(
            jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelIDRN)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelPoljaZaRadniNalogLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextFieldIDRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldBRNalogaRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelBRNalogRN))
                        .addGap(5, 5, 5)))
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelOtvaranjeRN)
                    .addComponent(jDateChooserOtvaranjeRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPredmerRN)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNzivRN)
                    .addComponent(jTextFieldNazivRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBRDelaRN)
                    .addComponent(jTextFieldBRDelaRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLansiraoRN)
                    .addComponent(jTextFieldLansiraoRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPocetakRN)
                    .addComponent(jDateChooserPocetakRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelZavrsetakRN)
                    .addComponent(jDateChooserZavrsetakRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOveriRN)
                    .addComponent(jTextFieldOverioRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPoljaZaRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNapomeneRN)
                    .addComponent(jScrollPaneNapomenaRN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPaneNalogRadni.setViewportView(jPanelPoljaZaRadniNalog);

        jTabbedPaneRadniNalog.addTab("Radni Nalog", new javax.swing.ImageIcon(getClass().getResource("/Grafika/file-complete-icon.png")), jScrollPaneNalogRadni); // NOI18N

        jTableRadniNalog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableRadniNalog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRadniNalogMouseClicked(evt);
            }
        });
        jTableRadniNalog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableRadniNalogKeyReleased(evt);
            }
        });
        jScrollPaneTableRadniNalog.setViewportView(jTableRadniNalog);

        javax.swing.GroupLayout jPanelRadniNalogLayout = new javax.swing.GroupLayout(jPanelRadniNalog);
        jPanelRadniNalog.setLayout(jPanelRadniNalogLayout);
        jPanelRadniNalogLayout.setHorizontalGroup(
            jPanelRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRadniNalogLayout.createSequentialGroup()
                .addGroup(jPanelRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_funkcionalnaDugmad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelRadniNalogLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPaneRadniNalog, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTableRadniNalog, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE))
        );
        jPanelRadniNalogLayout.setVerticalGroup(
            jPanelRadniNalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneTableRadniNalog, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
            .addGroup(jPanelRadniNalogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_funkcionalnaDugmad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneRadniNalog, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jTabbedPaneServisHeliVision.addTab("Radni Nalog", new javax.swing.ImageIcon(getClass().getResource("/Grafika/Document-Copy-icon.png")), jPanelRadniNalog); // NOI18N

        jPanel_funkcionalnaDugmadIzvrseniRadovi.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pretraga", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 204, 0))); // NOI18N

        Button_deleteIzvrseniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteIzvrseniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteIzvrseniRadovi.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteIzvrseniRadovi.setFocusable(false);
        Button_deleteIzvrseniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteIzvrseniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteIzvrseniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteIzvrseniRadoviActionPerformed(evt);
            }
        });

        Button_clearIzvrseniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearIzvrseniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearIzvrseniRadovi.setToolTipText("Očisti polja za unos podataka");
        Button_clearIzvrseniRadovi.setFocusable(false);
        Button_clearIzvrseniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearIzvrseniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearIzvrseniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearIzvrseniRadoviActionPerformed(evt);
            }
        });

        txt_pretragaIzvrseniRadovi.setToolTipText("Pretraga Izvršenih Radova po Broju  Rešenja DCV");
        txt_pretragaIzvrseniRadovi.setMinimumSize(new java.awt.Dimension(6, 40));
        txt_pretragaIzvrseniRadovi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_pretragaIzvrseniRadoviKeyReleased(evt);
            }
        });

        Button_insertIzvrseniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertIzvrseniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertIzvrseniRadovi.setToolTipText("Unos novih podataka u bazu");
        Button_insertIzvrseniRadovi.setFocusable(false);
        Button_insertIzvrseniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertIzvrseniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertIzvrseniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertIzvrseniRadoviActionPerformed(evt);
            }
        });

        Button_updateIzvrseniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateIzvrseniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateIzvrseniRadovi.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateIzvrseniRadovi.setFocusable(false);
        Button_updateIzvrseniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateIzvrseniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateIzvrseniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateIzvrseniRadoviActionPerformed(evt);
            }
        });

        Button_refreshIzvrseniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshIzvrseniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshIzvrseniRadovi.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshIzvrseniRadovi.setFocusable(false);
        Button_refreshIzvrseniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshIzvrseniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshIzvrseniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshIzvrseniRadoviActionPerformed(evt);
            }
        });

        Button_stampaIzvrseniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaIzvrseniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Adobe-PDF-Document-icon.png"))); // NOI18N
        Button_stampaIzvrseniRadovi.setToolTipText("Generisanje Dokumenta u PDF formatu koji možete štampati");
        Button_stampaIzvrseniRadovi.setFocusable(false);
        Button_stampaIzvrseniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaIzvrseniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaIzvrseniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaIzvrseniRadoviActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_funkcionalnaDugmadIzvrseniRadoviLayout = new javax.swing.GroupLayout(jPanel_funkcionalnaDugmadIzvrseniRadovi);
        jPanel_funkcionalnaDugmadIzvrseniRadovi.setLayout(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout);
        jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.setHorizontalGroup(
            jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createSequentialGroup()
                        .addGroup(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Button_insertIzvrseniRadovi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_clearIzvrseniRadovi, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createSequentialGroup()
                                .addComponent(Button_stampaIzvrseniRadovi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_refreshIzvrseniRadovi))
                            .addGroup(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createSequentialGroup()
                                .addComponent(Button_updateIzvrseniRadovi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_deleteIzvrseniRadovi))))
                    .addComponent(txt_pretragaIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.setVerticalGroup(
            jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretragaIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_clearIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_stampaIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_refreshIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_funkcionalnaDugmadIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Button_insertIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_updateIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_deleteIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPaneIzvrseniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jPanel_poljaVazduhoplovIzvrseniRadovi.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ovo Nemožete Menjati", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel_tipVazduhoplovaIzvrseniRadovi.setText("Proizvođač Tip");

        jLabel_rgVazduhoplovaIzvrseniRadovi.setText("Reg. Oznaka");

        jLabel_serijskiBrojVazduhoplovaIzvrseniRadovi.setText("Seriski Broj");

        jLabel_vlasnikVazduhoplovaIzvrseniRadovi.setText("Vlasnik");

        jLabel_naletVazduhoplovaIzvrseniRadovi.setText("Nalet");

        jLabel_casoviLetaVazduhoplovaIzvrseniRadovi.setText("Časova Leta");

        jLabel_ciklusaVazduhoplovaIzvrseniRadovi.setText("Ciklusa");

        jTextField_vlasnikVazduhoplovaIzvrseniRadovi.setToolTipText("Vlasnik Izabranog Vazduhoplova");

        jTextField_naletVazduhoplovaIzvrseniRadovi.setToolTipText(" Nalet Izabranog Vazduhoplova");

        jTextField_casovaLetaVazduhoplovaIzvrseniRadovi.setToolTipText("Časovi Leta  Izabranog Vazduhoplova");

        jTextField_ciklusaVazduhoplovaIzvrseniRadovi.setToolTipText(" Ciklus Izabranog Vazduhoplova");

        jTextField_serijskiBrojVazduhoplovaIzvrseniRadovi.setToolTipText("Serijski Broj  Izabranog Vazduhoplova");

        jTextField_tipVazduhoplovaIzvrseniRadovi.setToolTipText("Proizvođača i Tip Izabranog Vazduhoplova");

        jComboBoxOVazduhoplovuIzvrseniRadovi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Vazduhoplov" }));
        jComboBoxOVazduhoplovuIzvrseniRadovi.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxOVazduhoplovuIzvrseniRadoviPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel_poljaVazduhoplovIzvrseniRadoviLayout = new javax.swing.GroupLayout(jPanel_poljaVazduhoplovIzvrseniRadovi);
        jPanel_poljaVazduhoplovIzvrseniRadovi.setLayout(jPanel_poljaVazduhoplovIzvrseniRadoviLayout);
        jPanel_poljaVazduhoplovIzvrseniRadoviLayout.setHorizontalGroup(
            jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_ciklusaVazduhoplovaIzvrseniRadovi)
                    .addComponent(jLabel_serijskiBrojVazduhoplovaIzvrseniRadovi)
                    .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createSequentialGroup()
                            .addComponent(jLabel_rgVazduhoplovaIzvrseniRadovi)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                            .addComponent(jComboBoxOVazduhoplovuIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createSequentialGroup()
                            .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel_vlasnikVazduhoplovaIzvrseniRadovi)
                                .addComponent(jLabel_naletVazduhoplovaIzvrseniRadovi)
                                .addComponent(jLabel_casoviLetaVazduhoplovaIzvrseniRadovi)
                                .addComponent(jLabel_tipVazduhoplovaIzvrseniRadovi))
                            .addGap(36, 36, 36)
                            .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField_tipVazduhoplovaIzvrseniRadovi, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                .addComponent(jTextField_casovaLetaVazduhoplovaIzvrseniRadovi)
                                .addComponent(jTextField_naletVazduhoplovaIzvrseniRadovi)
                                .addComponent(jTextField_vlasnikVazduhoplovaIzvrseniRadovi)
                                .addComponent(jTextField_ciklusaVazduhoplovaIzvrseniRadovi)
                                .addComponent(jTextField_serijskiBrojVazduhoplovaIzvrseniRadovi))))))
        );
        jPanel_poljaVazduhoplovIzvrseniRadoviLayout.setVerticalGroup(
            jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_rgVazduhoplovaIzvrseniRadovi)
                    .addComponent(jComboBoxOVazduhoplovuIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_tipVazduhoplovaIzvrseniRadovi)
                    .addComponent(jTextField_tipVazduhoplovaIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_serijskiBrojVazduhoplovaIzvrseniRadovi)
                    .addComponent(jTextField_serijskiBrojVazduhoplovaIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_vlasnikVazduhoplovaIzvrseniRadovi)
                    .addComponent(jTextField_vlasnikVazduhoplovaIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_naletVazduhoplovaIzvrseniRadovi)
                    .addComponent(jTextField_naletVazduhoplovaIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_casoviLetaVazduhoplovaIzvrseniRadovi)
                    .addComponent(jTextField_casovaLetaVazduhoplovaIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_poljaVazduhoplovIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ciklusaVazduhoplovaIzvrseniRadovi)
                    .addComponent(jTextField_ciklusaVazduhoplovaIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelOVazduhoplovuIzvrseniRadoviLayout = new javax.swing.GroupLayout(jPanelOVazduhoplovuIzvrseniRadovi);
        jPanelOVazduhoplovuIzvrseniRadovi.setLayout(jPanelOVazduhoplovuIzvrseniRadoviLayout);
        jPanelOVazduhoplovuIzvrseniRadoviLayout.setHorizontalGroup(
            jPanelOVazduhoplovuIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOVazduhoplovuIzvrseniRadoviLayout.createSequentialGroup()
                .addComponent(jPanel_poljaVazduhoplovIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 385, Short.MAX_VALUE))
        );
        jPanelOVazduhoplovuIzvrseniRadoviLayout.setVerticalGroup(
            jPanelOVazduhoplovuIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOVazduhoplovuIzvrseniRadoviLayout.createSequentialGroup()
                .addComponent(jPanel_poljaVazduhoplovIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 140, Short.MAX_VALUE))
        );

        jScrollPaneVazduhoplovIzvrseniRadovi.setViewportView(jPanelOVazduhoplovuIzvrseniRadovi);

        jTabbedPaneIzvrseniRadovi.addTab("Vazduhoplov", new javax.swing.ImageIcon(getClass().getResource("/Grafika/icon.png")), jScrollPaneVazduhoplovIzvrseniRadovi); // NOI18N

        jPanelPoljaZaRadniNalogIzvrseniRadovi.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ovo Nemožete Menjati", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabelBrojRNIzvrseniRadovi.setText("Radni Nalog");

        jLabelPredmerIzvrseniRadovi.setText("Predmet Radova");

        jTextAreaPredmetRNIzvrseniRadovi.setColumns(20);
        jTextAreaPredmetRNIzvrseniRadovi.setRows(5);
        jTextAreaPredmetRNIzvrseniRadovi.setToolTipText("Predmet Radova Izabranog Radnog Naloga");
        jScrollPaneIzvrseniRadovi.setViewportView(jTextAreaPredmetRNIzvrseniRadovi);

        jComboBoxBrojRNIzvrseniRadovi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Radni Nalog" }));
        jComboBoxBrojRNIzvrseniRadovi.setToolTipText("Izaberite Radni Nalog");
        jComboBoxBrojRNIzvrseniRadovi.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxBrojRNIzvrseniRadoviPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jDateChooserPocetakIzvrseniRadovi.setToolTipText("Početak");
        jDateChooserPocetakIzvrseniRadovi.setDateFormatString("yyyy-MM-dd");

        jDateChooserZavrsetakIzvrseniRadovi.setToolTipText("Završetak");
        jDateChooserZavrsetakIzvrseniRadovi.setDateFormatString("yyyy-MM-dd");

        jLabelPocetakIzvrseniRadovi.setText("Početak");

        jLabelZavrsetakIzvrseniRadovi.setText("Završetak");

        javax.swing.GroupLayout jPanelPoljaZaRadniNalogIzvrseniRadoviLayout = new javax.swing.GroupLayout(jPanelPoljaZaRadniNalogIzvrseniRadovi);
        jPanelPoljaZaRadniNalogIzvrseniRadovi.setLayout(jPanelPoljaZaRadniNalogIzvrseniRadoviLayout);
        jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.setHorizontalGroup(
            jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPredmerIzvrseniRadovi)
                    .addComponent(jLabelBrojRNIzvrseniRadovi)
                    .addComponent(jLabelPocetakIzvrseniRadovi)
                    .addComponent(jLabelZavrsetakIzvrseniRadovi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jDateChooserPocetakIzvrseniRadovi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneIzvrseniRadovi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jComboBoxBrojRNIzvrseniRadovi, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooserZavrsetakIzvrseniRadovi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.setVerticalGroup(
            jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createSequentialGroup()
                        .addGroup(jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelBrojRNIzvrseniRadovi)
                            .addComponent(jComboBoxBrojRNIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPredmerIzvrseniRadovi)
                            .addComponent(jScrollPaneIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooserPocetakIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelPocetakIzvrseniRadovi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPoljaZaRadniNalogIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooserZavrsetakIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelZavrsetakIzvrseniRadovi))
                .addContainerGap(173, Short.MAX_VALUE))
        );

        jScrollPaneNalogRadniIzvrseniRadovi.setViewportView(jPanelPoljaZaRadniNalogIzvrseniRadovi);

        jTabbedPaneIzvrseniRadovi.addTab("Radni Nalog", new javax.swing.ImageIcon(getClass().getResource("/Grafika/file-complete-icon.png")), jScrollPaneNalogRadniIzvrseniRadovi); // NOI18N

        jLabelPrimljenIzvrseniRadovi.setText("Primljen na Radove");

        jDateChooserPreuzetIzvrseniRadovi.setToolTipText("Preuzet");
        jDateChooserPreuzetIzvrseniRadovi.setDateFormatString("yyyy-MM-dd");

        jDateChooserPrimljenIzvrseniRadovi.setToolTipText("Primljen na Radove");
        jDateChooserPrimljenIzvrseniRadovi.setDateFormatString("yyyy-MM-dd");

        jLabelPreuzeIzvrseniRadovi.setText("Preuzet");

        jTextAreaNapomenaIzvrseniRadovi.setColumns(20);
        jTextAreaNapomenaIzvrseniRadovi.setRows(5);
        jTextAreaNapomenaIzvrseniRadovi.setToolTipText("Unesite Napomenu");
        jScrollPaneIzvrseniRadovi1.setViewportView(jTextAreaNapomenaIzvrseniRadovi);

        jLabelNapomenaIzvrseniRadovi.setText("Napomena");

        jLabelIDIzvrseniRadovi.setText("ID");

        jTextFieldIDIzvrseniRadovi.setToolTipText("Unesite Identifikacioni Broj");

        jComboBoxRukovodilacTehSluzbeIzvrseniRadovi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberit Rukovodioca Tehničke Službe" }));
        jComboBoxRukovodilacTehSluzbeIzvrseniRadovi.setToolTipText("Izaberite Zaposlenog koji je u ulozi Rukovodioca Tehničke Službe");

        jTextFieldDCVIzvrseniRadovi.setToolTipText("Unesite Rešenje DCV");

        jTextFieldjMestoIzvrseniRadovi.setToolTipText("Unesite Mesto");

        jTextFieldBRPrilogaIzvrseniRadovi.setToolTipText("Unesite Broj Priloga kao ceo broj");

        jDateChooserDatumPredajeIzvrseniRadovi.setToolTipText("Unesite Datum Predaje");
        jDateChooserDatumPredajeIzvrseniRadovi.setDateFormatString("yyyy-MM-dd");

        jLabelDatumPredajeIzvrseniRadovi.setText("Datum Predaje");

        jLabelRukovodilacTehSluzbeIzvrseniRadovi.setText("Rukovodilac Teh.Sl.");

        jLabelDCVIzvrseniRadovi.setText("Rešenje DCV");

        jLabelMestoIzvrseniRadovi.setText("Mesto");

        jLabelBRPrilogaIzvrseniRadovi.setText("Br.Priloga");

        javax.swing.GroupLayout jPanelPanelIzvrseniRadoviLayout = new javax.swing.GroupLayout(jPanelPanelIzvrseniRadovi);
        jPanelPanelIzvrseniRadovi.setLayout(jPanelPanelIzvrseniRadoviLayout);
        jPanelPanelIzvrseniRadoviLayout.setHorizontalGroup(
            jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPanelIzvrseniRadoviLayout.createSequentialGroup()
                .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNapomenaIzvrseniRadovi)
                    .addComponent(jLabelPrimljenIzvrseniRadovi)
                    .addComponent(jLabelPreuzeIzvrseniRadovi)
                    .addComponent(jLabelIDIzvrseniRadovi)
                    .addComponent(jLabelDatumPredajeIzvrseniRadovi)
                    .addComponent(jLabelRukovodilacTehSluzbeIzvrseniRadovi)
                    .addComponent(jLabelDCVIzvrseniRadovi)
                    .addComponent(jLabelMestoIzvrseniRadovi)
                    .addComponent(jLabelBRPrilogaIzvrseniRadovi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooserDatumPredajeIzvrseniRadovi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldBRPrilogaIzvrseniRadovi)
                    .addComponent(jComboBoxRukovodilacTehSluzbeIzvrseniRadovi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooserPrimljenIzvrseniRadovi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneIzvrseniRadovi1)
                    .addComponent(jTextFieldIDIzvrseniRadovi)
                    .addComponent(jDateChooserPreuzetIzvrseniRadovi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldjMestoIzvrseniRadovi)
                    .addComponent(jTextFieldDCVIzvrseniRadovi))
                .addGap(0, 404, Short.MAX_VALUE))
        );
        jPanelPanelIzvrseniRadoviLayout.setVerticalGroup(
            jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPanelIzvrseniRadoviLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelPanelIzvrseniRadoviLayout.createSequentialGroup()
                        .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelIDIzvrseniRadovi)
                            .addComponent(jTextFieldIDIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelPanelIzvrseniRadoviLayout.createSequentialGroup()
                                .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelNapomenaIzvrseniRadovi)
                                    .addComponent(jScrollPaneIzvrseniRadovi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooserPrimljenIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelPrimljenIzvrseniRadovi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooserPreuzetIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPreuzeIzvrseniRadovi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooserDatumPredajeIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelDatumPredajeIzvrseniRadovi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxRukovodilacTehSluzbeIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelRukovodilacTehSluzbeIzvrseniRadovi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDCVIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDCVIzvrseniRadovi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldjMestoIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMestoIzvrseniRadovi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldBRPrilogaIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBRPrilogaIzvrseniRadovi))
                .addContainerGap(92, Short.MAX_VALUE))
        );

        jScrollPaneIzvrseniRadoviPanel.setViewportView(jPanelPanelIzvrseniRadovi);

        jTabbedPaneIzvrseniRadovi.addTab("Izvršeni Radovi", new javax.swing.ImageIcon(getClass().getResource("/Grafika/Document-Copy-icon.png")), jScrollPaneIzvrseniRadoviPanel); // NOI18N

        jTableIzvrseniRadovi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableIzvrseniRadovi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableIzvrseniRadoviMouseClicked(evt);
            }
        });
        jTableIzvrseniRadovi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableIzvrseniRadoviKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableIzvrseniRadovi);

        javax.swing.GroupLayout jPanelIzvrseniRadoviLayout = new javax.swing.GroupLayout(jPanelIzvrseniRadovi);
        jPanelIzvrseniRadovi.setLayout(jPanelIzvrseniRadoviLayout);
        jPanelIzvrseniRadoviLayout.setHorizontalGroup(
            jPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIzvrseniRadoviLayout.createSequentialGroup()
                .addGroup(jPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_funkcionalnaDugmadIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPaneIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelIzvrseniRadoviLayout.setVerticalGroup(
            jPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIzvrseniRadoviLayout.createSequentialGroup()
                .addGroup(jPanelIzvrseniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanelIzvrseniRadoviLayout.createSequentialGroup()
                        .addComponent(jPanel_funkcionalnaDugmadIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPaneIzvrseniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPaneServisHeliVision.addTab("Izvršeni Radovi", new javax.swing.ImageIcon(getClass().getResource("/Grafika/document-word-icon.png")), jPanelIzvrseniRadovi); // NOI18N

        jTableOdlozeniRadovi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableOdlozeniRadovi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableOdlozeniRadoviMouseClicked(evt);
            }
        });
        jTableOdlozeniRadovi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableOdlozeniRadoviKeyReleased(evt);
            }
        });
        jScrollPaneOdlozeniRadoviTable.setViewportView(jTableOdlozeniRadovi);

        jPanelOdlozeniRadoviPolja.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Odloženi Radovi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        jLabelRGBroj.setText("Registarska Oznaka");

        jTextFieldIzvorOdlozeniRadovi.setToolTipText("Unesite Izvor");

        jLabelIzvorOdlozeniRadovi.setText("Izvor");

        jComboBoxRGOznakaOdlozeniRadovi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite RG.Oznaku" }));
        jComboBoxRGOznakaOdlozeniRadovi.setToolTipText("Izaberite Registarsku Oznaku Vazduhoplova");

        jLabelOpisOdlozeniRadovi.setText("Opis");

        jTextAreaOpisOdlozeniRadovi.setColumns(20);
        jTextAreaOpisOdlozeniRadovi.setRows(5);
        jTextAreaOpisOdlozeniRadovi.setToolTipText("Unesite Opis");
        jScrollPaneOpisOdlozeniRadovi.setViewportView(jTextAreaOpisOdlozeniRadovi);

        jLabelZaposleniOdlozeniRadovi.setText("Zaposleni");

        jComboZaposleniOpisOdlozeniRadovi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Zaposlenog" }));
        jComboZaposleniOpisOdlozeniRadovi.setToolTipText("Izaberite Zaposlenog koi je dao Opis");

        jLabelUradjenoOdlozeniRadovi.setText("Urađeno");

        jScrollPaneUradjenoOdlozeniRadovi.setToolTipText("Unesite Urađeno");

        jTextAreaUradjenoOdlozeniRadovi.setColumns(20);
        jTextAreaUradjenoOdlozeniRadovi.setRows(5);
        jTextAreaUradjenoOdlozeniRadovi.setToolTipText("Unesite Urađeno");
        jScrollPaneUradjenoOdlozeniRadovi.setViewportView(jTextAreaUradjenoOdlozeniRadovi);

        jLabelZaposleniUradjenoOdlozeniRadovi.setText("Zaposleni");

        jComboZaposleniUradjenoOdlozeniRadovi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Zaposlenog" }));
        jComboZaposleniUradjenoOdlozeniRadovi.setToolTipText("Izaberite Zaposlenog ko je Radio");

        jLabelRedniBrojOdlozeniRadovi.setText("R.BR.");

        jTextFieldRedniBrojOdlozeniRadovi.setToolTipText("Unesite Redni Broj Odloženih Radova");

        jDateChooserOpisOdlozeniRadovi.setDateFormatString("yyyy-MM-dd");

        jDateChooserUradjenoOdlozeniRadovi.setDateFormatString("yyyy-MM-dd");

        jLabelDatumUradjenoOdlozeniRadovi.setText("Datum");

        jLabelDatumOpisOdlozeniRadovi.setText("Datum");

        jLabelIdOdlozeniRadovi.setText("id");

        jTextFieldIDOdlozeniRadovi.setToolTipText("Unesite Identifikacioni Broj ");

        javax.swing.GroupLayout jPanelOdlozeniRadoviPoljaLayout = new javax.swing.GroupLayout(jPanelOdlozeniRadoviPolja);
        jPanelOdlozeniRadoviPolja.setLayout(jPanelOdlozeniRadoviPoljaLayout);
        jPanelOdlozeniRadoviPoljaLayout.setHorizontalGroup(
            jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRGBroj)
                    .addComponent(jLabelOpisOdlozeniRadovi)
                    .addComponent(jLabelZaposleniOdlozeniRadovi)
                    .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                        .addComponent(jLabelRedniBrojOdlozeniRadovi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldRedniBrojOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDatumOpisOdlozeniRadovi))
                    .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                        .addComponent(jLabelIdOdlozeniRadovi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIDOdlozeniRadovi, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jDateChooserOpisOdlozeniRadovi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxRGOznakaOdlozeniRadovi, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPaneOpisOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboZaposleniOpisOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                        .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                                .addComponent(jLabelIzvorOdlozeniRadovi)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabelDatumUradjenoOdlozeniRadovi)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldIzvorOdlozeniRadovi)
                            .addComponent(jDateChooserUradjenoOdlozeniRadovi, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                        .addGap(53, 53, 53))
                    .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                        .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                                .addComponent(jLabelUradjenoOdlozeniRadovi)
                                .addGap(8, 8, 8)
                                .addComponent(jScrollPaneUradjenoOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                                .addComponent(jLabelZaposleniUradjenoOdlozeniRadovi)
                                .addGap(7, 7, 7)
                                .addComponent(jComboZaposleniUradjenoOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelOdlozeniRadoviPoljaLayout.setVerticalGroup(
            jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRGBroj)
                    .addComponent(jComboBoxRGOznakaOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldIzvorOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelIzvorOdlozeniRadovi))
                .addGap(11, 11, 11)
                .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelRedniBrojOdlozeniRadovi)
                        .addComponent(jTextFieldRedniBrojOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelDatumOpisOdlozeniRadovi))
                    .addComponent(jDateChooserOpisOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooserUradjenoOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDatumUradjenoOdlozeniRadovi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                        .addComponent(jLabelOpisOdlozeniRadovi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelIdOdlozeniRadovi)
                            .addComponent(jTextFieldIDOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                        .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                                .addComponent(jScrollPaneUradjenoOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboZaposleniUradjenoOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelOdlozeniRadoviPoljaLayout.createSequentialGroup()
                                .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPaneOpisOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelUradjenoOdlozeniRadovi))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelOdlozeniRadoviPoljaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboZaposleniOpisOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelZaposleniUradjenoOdlozeniRadovi)
                                    .addComponent(jLabelZaposleniOdlozeniRadovi))))
                        .addContainerGap(35, Short.MAX_VALUE))))
        );

        txt_pretragaOdlozeniRadovi.setToolTipText("Pretraga Odlozenih Radova Po Registracionoj Oznaci");
        txt_pretragaOdlozeniRadovi.setMinimumSize(new java.awt.Dimension(6, 40));
        txt_pretragaOdlozeniRadovi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_pretragaOdlozeniRadoviKeyReleased(evt);
            }
        });

        Button_insertOdlozeniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertOdlozeniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertOdlozeniRadovi.setToolTipText("Unos novih podataka u bazu");
        Button_insertOdlozeniRadovi.setFocusable(false);
        Button_insertOdlozeniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertOdlozeniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertOdlozeniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertOdlozeniRadoviActionPerformed(evt);
            }
        });

        Button_clearOdlozeniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearOdlozeniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearOdlozeniRadovi.setToolTipText("Očisti polja za unos podataka");
        Button_clearOdlozeniRadovi.setFocusable(false);
        Button_clearOdlozeniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearOdlozeniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearOdlozeniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearOdlozeniRadoviActionPerformed(evt);
            }
        });

        Button_updateOdlozeniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateOdlozeniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateOdlozeniRadovi.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateOdlozeniRadovi.setFocusable(false);
        Button_updateOdlozeniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateOdlozeniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateOdlozeniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateOdlozeniRadoviActionPerformed(evt);
            }
        });

        Button_refreshOdlozeniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshOdlozeniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshOdlozeniRadovi.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshOdlozeniRadovi.setFocusable(false);
        Button_refreshOdlozeniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshOdlozeniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshOdlozeniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshOdlozeniRadoviActionPerformed(evt);
            }
        });

        Button_stampaOdlozeniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaOdlozeniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaOdlozeniRadovi.setToolTipText("Štampa podataka iz tabele");
        Button_stampaOdlozeniRadovi.setFocusable(false);
        Button_stampaOdlozeniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaOdlozeniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaOdlozeniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaOdlozeniRadoviActionPerformed(evt);
            }
        });

        Button_deleteOdlozeniRadovi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteOdlozeniRadovi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteOdlozeniRadovi.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteOdlozeniRadovi.setFocusable(false);
        Button_deleteOdlozeniRadovi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteOdlozeniRadovi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteOdlozeniRadovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteOdlozeniRadoviActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelOdlozeniRadoviLayout = new javax.swing.GroupLayout(jPanelOdlozeniRadovi);
        jPanelOdlozeniRadovi.setLayout(jPanelOdlozeniRadoviLayout);
        jPanelOdlozeniRadoviLayout.setHorizontalGroup(
            jPanelOdlozeniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOdlozeniRadoviLayout.createSequentialGroup()
                .addComponent(jPanelOdlozeniRadoviPolja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelOdlozeniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelOdlozeniRadoviLayout.createSequentialGroup()
                        .addGroup(jPanelOdlozeniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Button_insertOdlozeniRadovi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_clearOdlozeniRadovi, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelOdlozeniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOdlozeniRadoviLayout.createSequentialGroup()
                                .addComponent(Button_stampaOdlozeniRadovi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_refreshOdlozeniRadovi))
                            .addGroup(jPanelOdlozeniRadoviLayout.createSequentialGroup()
                                .addComponent(Button_updateOdlozeniRadovi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_deleteOdlozeniRadovi))))
                    .addComponent(txt_pretragaOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 175, Short.MAX_VALUE))
            .addComponent(jScrollPaneOdlozeniRadoviTable)
        );
        jPanelOdlozeniRadoviLayout.setVerticalGroup(
            jPanelOdlozeniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOdlozeniRadoviLayout.createSequentialGroup()
                .addComponent(jScrollPaneOdlozeniRadoviTable, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelOdlozeniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOdlozeniRadoviLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_pretragaOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelOdlozeniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_clearOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_stampaOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_refreshOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelOdlozeniRadoviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Button_insertOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_updateOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_deleteOdlozeniRadovi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(79, 79, 79))
                    .addGroup(jPanelOdlozeniRadoviLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelOdlozeniRadoviPolja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPaneServisHeliVision.addTab("Odloženi Radovi", new javax.swing.ImageIcon(getClass().getResource("/Grafika/file-complete-icon.png")), jPanelOdlozeniRadovi); // NOI18N

        ToolBar_OpsteFunkcije.setFloatable(false);
        ToolBar_OpsteFunkcije.setRollover(true);
        ToolBar_OpsteFunkcije.setToolTipText("");
        ToolBar_OpsteFunkcije.add(jSeparator3);

        jLabelPozdravnaPoruka.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabelPozdravnaPoruka.setForeground(new java.awt.Color(51, 153, 0));
        jLabelPozdravnaPoruka.setText("Dobrodošli ");
        ToolBar_OpsteFunkcije.add(jLabelPozdravnaPoruka);

        jLabelUser.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabelUser.setForeground(new java.awt.Color(255, 0, 51));
        jLabelUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelUser.setText("User");
        ToolBar_OpsteFunkcije.add(jLabelUser);

        Button_singout.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_singout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/door-out-icon.png"))); // NOI18N
        Button_singout.setText("Log Out");
        Button_singout.setToolTipText("Log Out sa sistema EDMS");
        Button_singout.setFocusable(false);
        Button_singout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_singout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_singout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_singoutActionPerformed(evt);
            }
        });
        ToolBar_OpsteFunkcije.add(Button_singout);
        ToolBar_OpsteFunkcije.add(jSeparator1);

        Button_internet.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_internet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/network-icon.png"))); // NOI18N
        Button_internet.setText("Internet");
        Button_internet.setToolTipText("Provera da li je aplikacija konektovana ili ne na Internet");
        Button_internet.setFocusable(false);
        Button_internet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_internet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_internet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_internetActionPerformed(evt);
            }
        });
        ToolBar_OpsteFunkcije.add(Button_internet);

        Button_proveraKonekcijaBaze.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_proveraKonekcijaBaze.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Misc-Delete-Database-icon.png"))); // NOI18N
        Button_proveraKonekcijaBaze.setText("Baza");
        Button_proveraKonekcijaBaze.setToolTipText("Provera da li je aplikacija konektovana ili ne na bazu podataka");
        Button_proveraKonekcijaBaze.setFocusable(false);
        Button_proveraKonekcijaBaze.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_proveraKonekcijaBaze.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_proveraKonekcijaBaze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_proveraKonekcijaBazeActionPerformed(evt);
            }
        });
        ToolBar_OpsteFunkcije.add(Button_proveraKonekcijaBaze);
        ToolBar_OpsteFunkcije.add(jSeparator6);

        Button_Exit.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Button-Close-icon.png"))); // NOI18N
        Button_Exit.setText("Exit");
        Button_Exit.setToolTipText("Izlaz iz sistema EDMS");
        Button_Exit.setFocusable(false);
        Button_Exit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_Exit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_ExitActionPerformed(evt);
            }
        });
        ToolBar_OpsteFunkcije.add(Button_Exit);

        Menu_opste.setText("File");
        Menu_opste.setToolTipText("Opšti Meni");

        MenuItem_internet.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_internet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/network-icon.png"))); // NOI18N
        MenuItem_internet.setText("Internet");
        MenuItem_internet.setToolTipText("Provera da li je aplikacija konektovana ili ne na Internet");
        MenuItem_internet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_internetActionPerformed(evt);
            }
        });
        Menu_opste.add(MenuItem_internet);

        MenuItem_baza.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_baza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Misc-Delete-Database-icon.png"))); // NOI18N
        MenuItem_baza.setText("Baza");
        MenuItem_baza.setToolTipText("Provera da li je aplikacija konektovana ili ne na bazu podataka");
        MenuItem_baza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_bazaActionPerformed(evt);
            }
        });
        Menu_opste.add(MenuItem_baza);

        jMenuItem1.setText("Web Sajt Autora");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        Menu_opste.add(jMenuItem1);
        Menu_opste.add(jSeparator2);

        MenuItem_LogOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_LogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/door-out-icon.png"))); // NOI18N
        MenuItem_LogOut.setText("Log Out");
        MenuItem_LogOut.setToolTipText("Log Out sa sistema EDMS");
        MenuItem_LogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_LogOutActionPerformed(evt);
            }
        });
        Menu_opste.add(MenuItem_LogOut);
        Menu_opste.add(jSeparator4);

        MenuItem_Exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Button-Close-icon.png"))); // NOI18N
        MenuItem_Exit.setText("Exit");
        MenuItem_Exit.setToolTipText("Izlaz iz sistema EDMS");
        MenuItem_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_ExitActionPerformed(evt);
            }
        });
        Menu_opste.add(MenuItem_Exit);

        MenuBar_adminMeni.add(Menu_opste);

        setJMenuBar(MenuBar_adminMeni);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneServisHeliVision)
            .addComponent(ToolBar_OpsteFunkcije, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ToolBar_OpsteFunkcije, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneServisHeliVision))
        );

        setSize(new java.awt.Dimension(898, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Button_deleteVazduhoplovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteVazduhoplovaActionPerformed
        // Brisanje podataka iz tabele kretanje_vozila
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from vazduhoplov where id =?";
            try{
                pst=conn.prepareStatement(sql);
                pst.setString(1,jTextField_idVazduhoplova.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);
                //Ponovno ucitavanje podataka u tabelu
                Update_Vazduhoplova();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteVazduhoplovaActionPerformed

    private void Button_refreshVazduhoplovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshVazduhoplovaActionPerformed
        // Ponovno ucitavanje podataka u tabelu
        Update_Vazduhoplova();
    }//GEN-LAST:event_Button_refreshVazduhoplovaActionPerformed

    private void Button_insertVazduhoplovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertVazduhoplovaActionPerformed
        // Insert novih podataka
        try{
            String sql="Insert into vazduhoplov(id,Proizvodjac_Tip,Registarska_Oznaka,Seriski_Broj,Vlasnik,Nalet,Casova_Leta,Ciklusa) values (?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,jTextField_idVazduhoplova.getText());
            pst.setString(2,jTextField_tipVazduhoplova.getText());
            pst.setString(3,jTextField_rgVazduhoplova.getText());
            pst.setString(4,jTextField_serijskiBrojVazduhoplova.getText());
            pst.setString(5,jTextField_vlasnikVazduhoplova.getText());
            pst.setString(6,jTextField_naletVazduhoplova.getText());
            pst.setString(7,jTextField_casovaLetaVazduhoplova.getText());
            pst.setString(8,jTextField_ciklusaVazduhoplova.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);
            // Ponovno ucitavanje podataka u tabelu
            Update_Vazduhoplova();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertVazduhoplovaActionPerformed

    private void Button_clearVazduhoplovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearVazduhoplovaActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            jTextField_idVazduhoplova.setText("");
            jTextField_tipVazduhoplova.setText("");
            jTextField_rgVazduhoplova.setText("");
            jTextField_serijskiBrojVazduhoplova.setText("");
            jTextField_vlasnikVazduhoplova.setText("");
            jTextField_naletVazduhoplova.setText("");
            jTextField_casovaLetaVazduhoplova.setText("");
            jTextField_ciklusaVazduhoplova.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearVazduhoplovaActionPerformed

    private void Button_stampaVazduhoplovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaVazduhoplovaActionPerformed
        // Kreiranje izveštaja o Vazduhoplovima
        try {
            String izvestaj ="Izvestaji\\VazduhoploviIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaVazduhoplovaActionPerformed

    private void Button_updateVazduhoplovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateVazduhoplovaActionPerformed
        // Update podataka
        try{
            String ID =  jTextField_idVazduhoplova.getText();
            String Tip = jTextField_tipVazduhoplova.getText();
            String RegOznake = jTextField_rgVazduhoplova.getText();
            String SeriskiBroj = jTextField_serijskiBrojVazduhoplova.getText();
            String Vlasnik = jTextField_vlasnikVazduhoplova.getText();
            String Nalet = jTextField_naletVazduhoplova.getText();
            String Let = jTextField_casovaLetaVazduhoplova.getText();
            String Ciklusa = jTextField_ciklusaVazduhoplova.getText();
            String sql="update vazduhoplov set id='"+ID+"',Proizvodjac_Tip='"+Tip+"',Registarska_Oznaka='"+RegOznake+"',Seriski_Broj='"+SeriskiBroj+"',Vlasnik='"+Vlasnik+"',Nalet='"+Nalet+"',Casova_Leta='"+Let+"',Ciklusa='"+Ciklusa+"' where id='"+ID+"' ";
            pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);
                //Ponovno ucitavanje podataka u tabelu
                Update_Vazduhoplova();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateVazduhoplovaActionPerformed

    private void txt_pretragaVazduhoplovaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pretragaVazduhoplovaKeyReleased
        //popuna txt polja podacima za pretragu 
        try{
            String sql="select * from vazduhoplov where Registarska_Oznaka=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaVazduhoplova.getText());
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            PoljaVazduhoplova();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txt_pretragaVazduhoplovaKeyReleased

    private void MenuItem_internetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_internetActionPerformed
        // Provera da li je internet dostupan iz menija
        dostupnostInterneta();
    }//GEN-LAST:event_MenuItem_internetActionPerformed

    private void MenuItem_bazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_bazaActionPerformed
        conn=Data.javaconnect.KonekcijaDb();//Provera da li je aplikacija konektovana ili ne na bazu podataka iz menija
    }//GEN-LAST:event_MenuItem_bazaActionPerformed

    private void MenuItem_LogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_LogOutActionPerformed
        // Sing Out i preusmerenje na formu za login
        dispose();//zatvaranje prozora
        new Login().setVisible(true);
    }//GEN-LAST:event_MenuItem_LogOutActionPerformed

    private void MenuItem_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_ExitActionPerformed
        // Zatvaranje aplikacije iz menija
        System.exit(0);
    }//GEN-LAST:event_MenuItem_ExitActionPerformed

    private void Button_singoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_singoutActionPerformed
        // Sing Out i preusmerenje na formu za login
        dispose();//zatvaranje prozora
        new Login().setVisible(true);
    }//GEN-LAST:event_Button_singoutActionPerformed

    private void Button_internetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_internetActionPerformed
        // Provera da li je internet dostupan iz dugmeta
        dostupnostInterneta();
    }//GEN-LAST:event_Button_internetActionPerformed

    private void Button_proveraKonekcijaBazeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_proveraKonekcijaBazeActionPerformed
        conn=Data.javaconnect.KonekcijaDb();//Provera da li je aplikacija konektovana ili ne na bazu podataka pomocu dugmeta
    }//GEN-LAST:event_Button_proveraKonekcijaBazeActionPerformed

    private void Button_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_ExitActionPerformed
        // Zatvaranje aplikacije pomocu dugmeta
        System.exit(0);
    }//GEN-LAST:event_Button_ExitActionPerformed

    private void jTableVazduhoplovMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableVazduhoplovMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{
            int row =jTableVazduhoplov.getSelectedRow();
            String Table_click=(jTableVazduhoplov.getModel().getValueAt(row, 0).toString());
            String sql="select * from vazduhoplov where id='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            PoljaVazduhoplova();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jTableVazduhoplovMouseClicked

    private void jTableVazduhoplovKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableVazduhoplovKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =jTableVazduhoplov.getSelectedRow();
                String Table_click=(jTableVazduhoplov.getModel().getValueAt(row, 0).toString());
                String sql="select * from vazduhoplov where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                PoljaVazduhoplova();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jTableVazduhoplovKeyReleased

    private void Button_deleteRadniNalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteRadniNalogActionPerformed
        // Brisanje podataka iz tabele 
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from radni_nalog where id =?";
            try{
                pst=conn.prepareStatement(sql);
                pst.setString(1,jTextFieldIDRN.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);
                //Ponovno ucitavanje podataka u tabelu
                Update_RadniNalog();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteRadniNalogActionPerformed

    private void Button_clearRadniNalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearRadniNalogActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            jTextAreaNapomeneRN.setText("");
            jComboBoxOVazduhoplovu.setSelectedIndex(0);
            jTextField_tipVazduhoplovaRN.setText("");
            jTextField_serijskiBrojVazduhoplovaRN.setText("");
            jTextField_vlasnikVazduhoplovaRN.setText("");
            jTextField_naletVazduhoplovaRN.setText("");
            jTextField_casovaLetaVazduhoplovaRN.setText("");
            jTextField_ciklusaVazduhoplovaRN.setText("");
            jTextFieldIDRN.setText("");
            jTextFieldBRNalogaRN.setText("");
            jTextAreaPredmetRN.setText("");
            jTextFieldNazivRN.setText("");
            jTextFieldBRDelaRN.setText("");
            jTextFieldLansiraoRN.setText("");
            jTextFieldOverioRN.setText("");
            ((JTextField) jDateChooserOtvaranjeRN.getDateEditor().getUiComponent()).setText("");
            ((JTextField) jDateChooserPocetakRN.getDateEditor().getUiComponent()).setText("");
            ((JTextField) jDateChooserZavrsetakRN.getDateEditor().getUiComponent()).setText("");
            jTextFieldNORN6.setText("");
            jTextFieldNORN1.setText("");
            jTextFieldNORN2.setText("");
            jTextFieldNORN3.setText("");
            jTextFieldNORN4.setText("");
            jTextFieldNORN5.setText("");
            jTextFieldNazivDokumentaRN6.setText("");
            jTextFieldNazivDokumentaRN1.setText("");
            jTextFieldNazivDokumentaRN2.setText("");
            jTextFieldNazivDokumentaRN3.setText("");
            jTextFieldNazivDokumentaRN4.setText("");
            jTextFieldNazivDokumentaRN5.setText("");
            jTextFieldOznakaRN6.setText("");
            jTextFieldOznakaRN1.setText("");
            jTextFieldOznakaRN2.setText("");
            jTextFieldOznakaRN3.setText("");
            jTextFieldOznakaRN4.setText("");
            jTextFieldOznakaRN5.setText("");
            jTextFieldNapomenaRN6.setText("");
            jTextFieldNapomenaRN1.setText("");
            jTextFieldNapomenaRN2.setText("");
            jTextFieldNapomenaRN3.setText("");
            jTextFieldNapomenaRN4.setText("");
            jTextFieldNapomenaRN5.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearRadniNalogActionPerformed

    private void txt_pretragaRadniNalogKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pretragaRadniNalogKeyReleased
       //popuna txt polja podacima za pretragu 
        try{
            String sql="select * from radni_nalog where Broj_Naloga=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaRadniNalog.getText());
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            popunaPolljaRadniNalog();//popuna polj radni nalog
            popunaPoljaOVazduhoplovu();//popuna polja o vazduhoplovu
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txt_pretragaRadniNalogKeyReleased

    private void Button_insertRadniNalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertRadniNalogActionPerformed
       // Insert novih podataka
        try{
            String sql="Insert into radni_nalog(id,Broj_Naloga,Datum_Otvaranja,idVazduhoplova,Naziv_Komponente,Broj_Dela,Lansirao,Datum_Pocetka,Datum_Zavrsetka,Overio_Zavrsetak,Predmet_Radova,Napomena) values (?,?,?,?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,jTextFieldIDRN.getText());
            pst.setString(2,jTextFieldBRNalogaRN.getText());
            pst.setString(3,((JTextField) jDateChooserOtvaranjeRN.getDateEditor().getUiComponent()).getText());
            pst.setInt(4, jComboBoxOVazduhoplovu.getSelectedIndex());
            pst.setString(5,jTextFieldNazivRN.getText());
            pst.setString(6,jTextFieldBRDelaRN.getText());
            pst.setString(7,jTextFieldLansiraoRN.getText());
            pst.setString(8,((JTextField) jDateChooserPocetakRN.getDateEditor().getUiComponent()).getText());
            pst.setString(9,((JTextField) jDateChooserZavrsetakRN.getDateEditor().getUiComponent()).getText());
            pst.setString(10,jTextFieldOverioRN.getText());
            pst.setString(11,jTextAreaPredmetRN.getText());
            pst.setString(12,jTextAreaNapomeneRN.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);
            // Ponovno ucitavanje podataka u tabelu
            Update_RadniNalog();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }

    }//GEN-LAST:event_Button_insertRadniNalogActionPerformed

    private void Button_updateRadniNalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateRadniNalogActionPerformed
        // Update podataka
        try{
            String ID = jTextFieldIDRN.getText();
            String Broj_Naloga = jTextFieldBRNalogaRN.getText();
            String Datum_Otvaranja = ((JTextField) jDateChooserOtvaranjeRN.getDateEditor().getUiComponent()).getText();
            int idVazduhoplova = jComboBoxOVazduhoplovu.getSelectedIndex();
            String Naziv_Komponente = jTextFieldNazivRN.getText();
            String Broj_Dela = jTextFieldBRDelaRN.getText();
            String Lansirao = jTextFieldLansiraoRN.getText();
            String Datum_Pocetka = ((JTextField) jDateChooserPocetakRN.getDateEditor().getUiComponent()).getText();
            String Datum_Zavrsetka = ((JTextField) jDateChooserZavrsetakRN.getDateEditor().getUiComponent()).getText();
            String Overio_Zavrsetak = jTextFieldOverioRN.getText();
            String Predmet_Radova = jTextAreaPredmetRN.getText();
            String Napomena = jTextAreaNapomeneRN.getText();
            String sql="update radni_nalog set id='"+ID+"',Broj_Naloga='"+Broj_Naloga+"',Datum_Otvaranja='"+Datum_Otvaranja+"',idVazduhoplova='"+idVazduhoplova+"',Naziv_Komponente='"+Naziv_Komponente+"',Broj_Dela='"+Broj_Dela+"',Lansirao='"+Lansirao+"',Datum_Pocetka='"+Datum_Pocetka+"',Datum_Zavrsetka='"+Datum_Zavrsetka+"',Overio_Zavrsetak='"+Overio_Zavrsetak+"',Predmet_Radova='"+Predmet_Radova+"',Napomena='"+Napomena+"' where id='"+ID+"' ";
            pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);
            //Ponovno ucitavanje podataka u tabelu
            Update_RadniNalog();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateRadniNalogActionPerformed

    private void Button_refreshRadniNalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshRadniNalogActionPerformed
        // Ponovno Ucitavanje podataka u tabelu
        Update_RadniNalog();
    }//GEN-LAST:event_Button_refreshRadniNalogActionPerformed

    private void Button_stampaRadniNalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaRadniNalogActionPerformed
        // Kreiranje Dokumenta
        String DatumOtvaranja = ((JTextField) jDateChooserOtvaranjeRN.getDateEditor().getUiComponent()).getText();
        String DatumPocetka = ((JTextField) jDateChooserPocetakRN.getDateEditor().getUiComponent()).getText();
        String DatumZavrsetka = ((JTextField) jDateChooserZavrsetakRN.getDateEditor().getUiComponent()).getText();
        try {
            //inicijalizacija
            Document document =new Document();
            //kreiranje samog dokumenta
            PdfWriter.getInstance(document, new FileOutputStream("RadniNalog.pdf"));      
            //otvaranje
            document.open();
            //Kreiranje zaglavlja dokumenta
            PdfPTable zaglavlje = new PdfPTable(1);
            zaglavlje.addCell("HELI VISION doo" + "                                                                 TEHNIČKA SLUŽBA\n" +
                "                                                               ZA ODRŽAVANJE VAZDUHOPLOVA");
            document.add(zaglavlje);
            //Prazan red
            document.add(new Paragraph(" "));
            //Kreiranje podataka o radnom nalogu
            PdfPTable radniNalog = new PdfPTable(3);
            radniNalog.addCell("RADNI NALOG\n" +
                "Work order");
            radniNalog.addCell("BROJ NALOG:\n" +
                "No: " + jTextFieldBRNalogaRN.getText());
            radniNalog.addCell("DATUM OTVARANJA:\n" +
                "Date of issue: " + DatumOtvaranja);
            document.add(radniNalog);
            //kreiranje reda sa tekstom i tabele podaci o vazduhoplovu
            document.add(new Paragraph("PODACI O VAZDUHOPLOVU / KOMPONENTI: Aircraft's / Component's data:"));
            //prvi red tabele
            PdfPTable PodaciVazduhoplov = new PdfPTable(1);
            PodaciVazduhoplov.addCell("Proizvođač i tip vazduhoplova/komponente:\n" +
                "Manufacturer & model of Aircraft / Component: " + jTextField_tipVazduhoplovaRN.getText());
            document.add(PodaciVazduhoplov);
            //drugi red tabele
            PdfPTable PodaciVazduhoplov1 = new PdfPTable(2);
            PodaciVazduhoplov1.addCell("Registarska oznaka: \n" +
                "Registration number: " + jComboBoxOVazduhoplovu.getSelectedItem().toString());
            PodaciVazduhoplov1.addCell("Seriski broj:\n" +
                "Serial number: " + jTextField_serijskiBrojVazduhoplovaRN.getText());
            PodaciVazduhoplov1.addCell("Naziv komponente:\n" +
                "Component' s name: " + jTextFieldNazivRN.getText());
            PodaciVazduhoplov1.addCell("Broj dela:\n" +
                "Part number: " + jTextFieldBRDelaRN.getText());
            document.add(PodaciVazduhoplov1);
            //treci red tabele
            PdfPTable PodaciVazduhoplov2 = new PdfPTable(3);
            PodaciVazduhoplov2.addCell("Nalet:\n" +
                "Flight: " + jTextField_naletVazduhoplovaRN.getText());
            PodaciVazduhoplov2.addCell("Časova leta:\n" +
                "FH " + jTextField_casovaLetaVazduhoplovaRN.getText());
            PodaciVazduhoplov2.addCell("Ciklusa:\n" +
                "FC " + jTextField_ciklusaVazduhoplovaRN.getText());
            document.add(PodaciVazduhoplov2);
            //Prazan red
            document.add(new Paragraph(" "));
            //tabele podaci o vazduhoplovu sa datumom pocetka i zavrsetka ko je overio
            PdfPTable PodaciVazduhoplov3 = new PdfPTable(4);
            PodaciVazduhoplov3.addCell("Lansirao:\n" +
                "Launched by: " + jTextFieldLansiraoRN.getText());
            PodaciVazduhoplov3.addCell("Datum Početka:\n" +
                "Date starter: " + DatumPocetka);
            PodaciVazduhoplov3.addCell("Datum Završetka:\n" +
                "Date stopped: " + DatumZavrsetka);
            PodaciVazduhoplov3.addCell("Overio Završetak:\n" +
                "Verified by: " + jTextFieldOverioRN.getText());
            document.add(PodaciVazduhoplov3);
            //Prazan red
            document.add(new Paragraph(" "));
            //kreiranje reda sa tekstom i tabele podaci o vazduhoplovu dodatna dokumentacija
            document.add(new Paragraph("SADRŽAJ PRATEĆE DOKUMENTACIJE RADNOG NALOGA\n" +
                "Content of additional documentation"));
            PdfPTable PodaciVazduhoplov4 = new PdfPTable(4);
            PodaciVazduhoplov4.addCell("No");
            PodaciVazduhoplov4.addCell("Naziv Dokumenta\n" +
                "Document name ");
            PodaciVazduhoplov4.addCell("Oznaka\n" +
                "Mark");
            PodaciVazduhoplov4.addCell("Napomena\n" +
                "Remark");
            PodaciVazduhoplov4.addCell(jTextFieldNORN1.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNazivDokumentaRN1.getText());
            PodaciVazduhoplov4.addCell(jTextFieldOznakaRN1.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNapomenaRN1.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNORN2.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNazivDokumentaRN2.getText());
            PodaciVazduhoplov4.addCell(jTextFieldOznakaRN2.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNapomenaRN2.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNORN3.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNazivDokumentaRN3.getText());
            PodaciVazduhoplov4.addCell(jTextFieldOznakaRN3.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNapomenaRN3.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNORN4.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNazivDokumentaRN4.getText());
            PodaciVazduhoplov4.addCell(jTextFieldOznakaRN4.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNapomenaRN4.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNORN5.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNazivDokumentaRN5.getText());
            PodaciVazduhoplov4.addCell(jTextFieldOznakaRN5.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNapomenaRN5.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNORN6.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNazivDokumentaRN6.getText());
            PodaciVazduhoplov4.addCell(jTextFieldOznakaRN6.getText());
            PodaciVazduhoplov4.addCell(jTextFieldNapomenaRN6.getText());
            document.add(PodaciVazduhoplov4);
            //Prazan red
            document.add(new Paragraph(" "));
            //Napomene poslednji red
            document.add(new Paragraph("Napomene:\n" +
                "Remarks: " + jTextAreaNapomeneRN.getText()));
            //zatvaranje 
            document.close();
            JOptionPane.showMessageDialog(null, "Uspešno ste kreirali Radni Nalog");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        // Otvaranje Dokumenta
        try {
            String doc = "RadniNalog.pdf";
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+doc);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaRadniNalogActionPerformed

    private void jComboBoxOVazduhoplovuPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxOVazduhoplovuPopupMenuWillBecomeInvisible
        popunaPoljaOVazduhoplovu();//popuna polja o vazduhoplovu
    }//GEN-LAST:event_jComboBoxOVazduhoplovuPopupMenuWillBecomeInvisible

    private void jTableRadniNalogKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableRadniNalogKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =jTableRadniNalog.getSelectedRow();
                String Table_click=(jTableRadniNalog.getModel().getValueAt(row, 0).toString());
                String sql="select * from radni_nalog where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                popunaPolljaRadniNalog();//popuna polj radni nalog
                popunaPoljaOVazduhoplovu();//popuna polja o vazduhoplovu
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jTableRadniNalogKeyReleased

    private void jTableRadniNalogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRadniNalogMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{
            int row =jTableRadniNalog.getSelectedRow();
            String Table_click=(jTableRadniNalog.getModel().getValueAt(row, 0).toString());
            String sql="select * from radni_nalog where id='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            popunaPolljaRadniNalog();//popuna polj radni nalog
            popunaPoljaOVazduhoplovu();//popuna polja o vazduhoplovu
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jTableRadniNalogMouseClicked

    private void Button_deleteIzvrseniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteIzvrseniRadoviActionPerformed
        // Brisanje podataka iz tabele 
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from izvrseni_radovi where id =?";
            try{
                pst=conn.prepareStatement(sql);
                pst.setString(1,jTextFieldIDIzvrseniRadovi.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);
                //Ponovno ucitavanje podataka u tabelu
                Update_IzvrseniRadovi();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteIzvrseniRadoviActionPerformed

    private void Button_clearIzvrseniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearIzvrseniRadoviActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            jComboBoxOVazduhoplovuIzvrseniRadovi.setSelectedIndex(0);
            jTextAreaNapomenaIzvrseniRadovi.setText("");
            jComboBoxRukovodilacTehSluzbeIzvrseniRadovi.setSelectedIndex(0);
            jTextFieldIDIzvrseniRadovi.setText("");
            jTextFieldBRPrilogaIzvrseniRadovi.setText("");
            jTextFieldjMestoIzvrseniRadovi.setText("");
            jTextFieldDCVIzvrseniRadovi.setText("");
            jComboBoxBrojRNIzvrseniRadovi.setSelectedIndex(0);
            jTextAreaPredmetRNIzvrseniRadovi.setText("");
            jTextField_tipVazduhoplovaIzvrseniRadovi.setText("");
            jTextField_serijskiBrojVazduhoplovaIzvrseniRadovi.setText("");
            jTextField_vlasnikVazduhoplovaIzvrseniRadovi.setText("");
            jTextField_naletVazduhoplovaIzvrseniRadovi.setText("");
            jTextField_casovaLetaVazduhoplovaIzvrseniRadovi.setText("");
            jTextField_ciklusaVazduhoplovaIzvrseniRadovi.setText("");
            ((JTextField) jDateChooserPocetakIzvrseniRadovi.getDateEditor().getUiComponent()).setText("");
            ((JTextField) jDateChooserZavrsetakIzvrseniRadovi.getDateEditor().getUiComponent()).setText("");
            ((JTextField) jDateChooserPrimljenIzvrseniRadovi.getDateEditor().getUiComponent()).setText("");
            ((JTextField) jDateChooserPreuzetIzvrseniRadovi.getDateEditor().getUiComponent()).setText("");
            ((JTextField) jDateChooserDatumPredajeIzvrseniRadovi.getDateEditor().getUiComponent()).setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearIzvrseniRadoviActionPerformed

    private void txt_pretragaIzvrseniRadoviKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pretragaIzvrseniRadoviKeyReleased
       //popuna txt polja podacima za pretragu 
        try{
            String sql="select * from izvrseni_radovi where Resenje_DCV=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaIzvrseniRadovi.getText());
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            popunaPolljaIzvrseniRadovi();//popuna poza Izvrseni Radovi
            popunaPoljaRadniNalogIzvrseniRadovi();// popuna polja o radnom nalogau
            popunaPoljaOVazduhoplovuIzvrseniRadovi();//popuna polja o vazduhoplovu
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txt_pretragaIzvrseniRadoviKeyReleased

    private void Button_insertIzvrseniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertIzvrseniRadoviActionPerformed
        // Insert novih podataka
        try{
            String sql="Insert into izvrseni_radovi(id,idRadniNalog,Primljen_na_Radove,Preuzet,Napomena,Mesto,idZaposleni,Resenje_DCV,Broj_Priloga,Datum_Predaje,idVazduhoplova) values (?,?,?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,jTextFieldIDIzvrseniRadovi.getText());
            pst.setInt(2, jComboBoxBrojRNIzvrseniRadovi.getSelectedIndex());
            pst.setString(3,((JTextField) jDateChooserPrimljenIzvrseniRadovi.getDateEditor().getUiComponent()).getText());
            pst.setString(4,((JTextField) jDateChooserPreuzetIzvrseniRadovi.getDateEditor().getUiComponent()).getText());
            pst.setString(5,jTextAreaNapomenaIzvrseniRadovi.getText());
            pst.setString(6,jTextFieldjMestoIzvrseniRadovi.getText());
            pst.setInt(7, jComboBoxRukovodilacTehSluzbeIzvrseniRadovi.getSelectedIndex());
            pst.setString(8,jTextFieldDCVIzvrseniRadovi.getText());
            pst.setString(9,jTextFieldBRPrilogaIzvrseniRadovi.getText());
            pst.setString(10,((JTextField) jDateChooserDatumPredajeIzvrseniRadovi.getDateEditor().getUiComponent()).getText());
            pst.setInt(11, jComboBoxOVazduhoplovuIzvrseniRadovi.getSelectedIndex());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);
            // Ponovno ucitavanje podataka u tabelu
            Update_IzvrseniRadovi();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertIzvrseniRadoviActionPerformed

    private void Button_updateIzvrseniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateIzvrseniRadoviActionPerformed
        // Update podataka
        try{
            String id = jTextFieldIDIzvrseniRadovi.getText();
            int idRadniNalog = jComboBoxBrojRNIzvrseniRadovi.getSelectedIndex();
            String Primljen_na_Radove = ((JTextField) jDateChooserPrimljenIzvrseniRadovi.getDateEditor().getUiComponent()).getText();
            String Preuzet = ((JTextField) jDateChooserPreuzetIzvrseniRadovi.getDateEditor().getUiComponent()).getText();
            String Napomena = jTextAreaNapomenaIzvrseniRadovi.getText();
            String Mesto = jTextFieldjMestoIzvrseniRadovi.getText();
            int idZaposleni = jComboBoxRukovodilacTehSluzbeIzvrseniRadovi.getSelectedIndex();
            String Resenje_DCV = jTextFieldDCVIzvrseniRadovi.getText();
            String Broj_Priloga = jTextFieldBRPrilogaIzvrseniRadovi.getText();
            String Datum_Predaje = ((JTextField) jDateChooserDatumPredajeIzvrseniRadovi.getDateEditor().getUiComponent()).getText();
            int idVazduhoplova = jComboBoxOVazduhoplovuIzvrseniRadovi.getSelectedIndex();
            String sql="update izvrseni_radovi set id='"+id+"',idRadniNalog='"+idRadniNalog+"',Primljen_na_Radove='"+Primljen_na_Radove+"',Preuzet='"+Preuzet+"',Napomena='"+Napomena+"',Mesto='"+Mesto+"',idZaposleni='"+idZaposleni+"',Resenje_DCV='"+Resenje_DCV+"',Broj_Priloga='"+Broj_Priloga+"',Datum_Predaje='"+Datum_Predaje+"', idVazduhoplova='"+idVazduhoplova+"' where id='"+id+"' ";
            pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);
            //Ponovno ucitavanje podataka u tabelu
            Update_IzvrseniRadovi();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateIzvrseniRadoviActionPerformed

    private void Button_refreshIzvrseniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshIzvrseniRadoviActionPerformed
        //Ponovno ucitavanje podataka u tabelu
        Update_IzvrseniRadovi();
    }//GEN-LAST:event_Button_refreshIzvrseniRadoviActionPerformed

    private void Button_stampaIzvrseniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaIzvrseniRadoviActionPerformed
        // Kreiranje Dokumenta
        String PrimljenNaRadove = ((JTextField) jDateChooserPrimljenIzvrseniRadovi.getDateEditor().getUiComponent()).getText();
        String DatumPocetka = ((JTextField) jDateChooserPocetakIzvrseniRadovi.getDateEditor().getUiComponent()).getText();
        String DatumZavrsetka = ((JTextField) jDateChooserZavrsetakIzvrseniRadovi.getDateEditor().getUiComponent()).getText();
        String Preuzet = ((JTextField) jDateChooserPreuzetIzvrseniRadovi.getDateEditor().getUiComponent()).getText();
        String DatumPredaje = ((JTextField) jDateChooserDatumPredajeIzvrseniRadovi.getDateEditor().getUiComponent()).getText();
        
        try {
            //inicijalizacija
            Document document =new Document();
            //kreiranje samog dokumenta
            PdfWriter.getInstance(document, new FileOutputStream("IzvestajIzvrseniRadovi.pdf"));      
            //otvaranje
            document.open();
            //Kreiranje zaglavlja dokumenta
            PdfPTable zaglavlje = new PdfPTable(1);
            zaglavlje.addCell("HELI VISION doo" + "                                                                 TEHNIČKA SLUŽBA\n" +
                "                                                               ZA ODRŽAVANJE VAZDUHOPLOVA");
            document.add(zaglavlje);
            //Prazan red
            document.add(new Paragraph(" "));
            //Naslov Dokumenta
            document.add(new Paragraph("                                               IZVEŠTAJ O IZVRŠENIM RADOVIMA\n" +
                "                                                          PO RADNOM NALOGU BROJ",FontFactory.getFont(FontFactory.TIMES_BOLD,13,Font.BOLD)));
            document.add(new Paragraph("                                                                              " + jComboBoxBrojRNIzvrseniRadovi.getSelectedItem().toString(),FontFactory.getFont(FontFactory.TIMES_BOLD,13,Font.BOLD)));
            //Prazan red
            document.add(new Paragraph(" "));
            //Kreiranje podataka o Izvrsenim Radovima prvi red
            PdfPTable izvrseniRadovi = new PdfPTable(1);
            izvrseniRadovi.addCell("Vlasnik vazduhoplova:\n" +
                "Aircraft Owner " + jTextField_vlasnikVazduhoplovaIzvrseniRadovi.getText());
            document.add(izvrseniRadovi);
            //drugi red tabele
            PdfPTable izvrseniRadovi1 = new PdfPTable(1);
            izvrseniRadovi1.addCell("Proizvođač i tip vazduhoplova:\n" +
                "Manufacturer & model of Aircraft: " + jTextField_tipVazduhoplovaIzvrseniRadovi.getText());
            document.add(izvrseniRadovi1);
            //treci red tabele
            PdfPTable izvrseniRadovi2 = new PdfPTable(2);
            izvrseniRadovi2.addCell("Registarska oznaka: \n" +
                "Registration number " + jComboBoxOVazduhoplovuIzvrseniRadovi.getSelectedItem().toString());
            izvrseniRadovi2.addCell("Seriski broj:\n" +
                "Serial number: " + jTextField_serijskiBrojVazduhoplovaIzvrseniRadovi.getText());
            document.add(izvrseniRadovi2);
            //cetvrti red tabele
            PdfPTable izvrseniRadovi3 = new PdfPTable(3);
            izvrseniRadovi3.addCell("Nalet:\n" +
                "Onflight " + jTextField_naletVazduhoplovaIzvrseniRadovi.getText());
            izvrseniRadovi3.addCell("Časova leta:\n" +
                "FH " + jTextField_casovaLetaVazduhoplovaIzvrseniRadovi.getText());
            izvrseniRadovi3.addCell("Ciklusa:\n" +
                "FC " + jTextField_ciklusaVazduhoplovaIzvrseniRadovi.getText());
            document.add(izvrseniRadovi3);
            //peti red tabele
            PdfPTable izvrseniRadovi4 = new PdfPTable(1);
            izvrseniRadovi4.addCell("PREDMET RADOVA: \n" +
                "Subject of work " + jTextAreaPredmetRNIzvrseniRadovi.getText());
            document.add(izvrseniRadovi4);
            //sesti red tabele
            PdfPTable izvrseniRadovi5 = new PdfPTable(4);
            izvrseniRadovi5.addCell("Primljen na radove:\n" +
                "Reccive on work " );
            izvrseniRadovi5.addCell("Početak:\n" +
                "Sstarted " );
            izvrseniRadovi5.addCell("Završetak:\n" +
                "Stopped " );
            izvrseniRadovi5.addCell("Preuzet:\n" +
                "Take over " );
            document.add(izvrseniRadovi5);
           //sedmi red tabele 
            PdfPTable izvrseniRadovi6 = new PdfPTable(4);
            izvrseniRadovi6.addCell(PrimljenNaRadove);
            izvrseniRadovi6.addCell(DatumPocetka);
            izvrseniRadovi6.addCell(DatumZavrsetka);
            izvrseniRadovi6.addCell(Preuzet);
            document.add(izvrseniRadovi6);
            //Prazan red
            document.add(new Paragraph(" "));
            //napomena
            document.add(new Paragraph("NAPOMENE:\n" +
                "Remarks " + jTextAreaNapomenaIzvrseniRadovi.getText()));
            //Prazan red
            document.add(new Paragraph(" "));
            document.add(new Paragraph("RUKOVODILAC TEHNIČKE SLUŽBE                                         VLASNIK VAZDUHOPLOVA"));
            document.add(new Paragraph(jComboBoxRukovodilacTehSluzbeIzvrseniRadovi.getSelectedItem().toString()+"                                                                              "+jTextField_vlasnikVazduhoplovaIzvrseniRadovi.getText()));
            document.add(new Paragraph("___________________________                                             ____________________"));
            //Prazan red
            document.add(new Paragraph(" "));
            //red tabele u podnozju
            PdfPTable izvrseniRadovi7 = new PdfPTable(4);
            izvrseniRadovi7.addCell("Odobreno Rešenjem DCV broj: " + jTextFieldDCVIzvrseniRadovi.getText());
            izvrseniRadovi7.addCell("Ovaj izveštaj sadrži i " + jTextFieldBRPrilogaIzvrseniRadovi.getText()+" priloga ");
            izvrseniRadovi7.addCell("Datum Predaje: " + DatumPredaje);
            izvrseniRadovi7.addCell("Mesto: " + jTextFieldjMestoIzvrseniRadovi.getText());
            document.add(izvrseniRadovi7);
            //zatvaranje 
            document.close();
            JOptionPane.showMessageDialog(null, "Uspešno ste kreirali Izveštaj o Izvršenim Radovima");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        // Otvaranje Dokumenta
        try {
            String doc = "IzvestajIzvrseniRadovi.pdf";
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+doc);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaIzvrseniRadoviActionPerformed

    private void jComboBoxOVazduhoplovuIzvrseniRadoviPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxOVazduhoplovuIzvrseniRadoviPopupMenuWillBecomeInvisible
        popunaPoljaOVazduhoplovuIzvrseniRadovi();//popuna polja o vazduhoplovu
    }//GEN-LAST:event_jComboBoxOVazduhoplovuIzvrseniRadoviPopupMenuWillBecomeInvisible

    private void jComboBoxBrojRNIzvrseniRadoviPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxBrojRNIzvrseniRadoviPopupMenuWillBecomeInvisible
        popunaPoljaRadniNalogIzvrseniRadovi();// popuna polja o radnom nalogau
    }//GEN-LAST:event_jComboBoxBrojRNIzvrseniRadoviPopupMenuWillBecomeInvisible

    private void jTableIzvrseniRadoviKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableIzvrseniRadoviKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =jTableIzvrseniRadovi.getSelectedRow();
                String Table_click=(jTableIzvrseniRadovi.getModel().getValueAt(row, 0).toString());
                String sql="select * from izvrseni_radovi where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                popunaPolljaIzvrseniRadovi();//popuna poza Izvrseni Radovi
                popunaPoljaRadniNalogIzvrseniRadovi();// popuna polja o radnom nalogau
                popunaPoljaOVazduhoplovuIzvrseniRadovi();//popuna polja o vazduhoplovu
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jTableIzvrseniRadoviKeyReleased

    private void jTableIzvrseniRadoviMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableIzvrseniRadoviMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{
            int row =jTableIzvrseniRadovi.getSelectedRow();
            String Table_click=(jTableIzvrseniRadovi.getModel().getValueAt(row, 0).toString());
            String sql="select * from izvrseni_radovi where id='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            popunaPolljaIzvrseniRadovi();//popuna poza Izvrseni Radovi
            popunaPoljaRadniNalogIzvrseniRadovi();// popuna polja o radnom nalogau
            popunaPoljaOVazduhoplovuIzvrseniRadovi();//popuna polja o vazduhoplovu
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jTableIzvrseniRadoviMouseClicked

    private void Button_deleteOdlozeniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteOdlozeniRadoviActionPerformed
        // Brisanje podataka iz tabele
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from odlozeni_radovi where id =?";
            try{
                pst=conn.prepareStatement(sql);
                pst.setString(1,jTextFieldIDOdlozeniRadovi.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);
                //Ponovno ucitavanje podataka u tabelu
                Update_OdlozeniRadovi();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteOdlozeniRadoviActionPerformed

    private void Button_stampaOdlozeniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaOdlozeniRadoviActionPerformed
        // Kreiranje izveštaja o Vazduhoplovima
        try {
            String izvestaj ="Izvestaji\\OdlozeniRadoviIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaOdlozeniRadoviActionPerformed

    private void Button_refreshOdlozeniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshOdlozeniRadoviActionPerformed
        Update_OdlozeniRadovi();// Ponovna popuna podacima u tabeli odlozeni radovi
    }//GEN-LAST:event_Button_refreshOdlozeniRadoviActionPerformed

    private void Button_updateOdlozeniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateOdlozeniRadoviActionPerformed
        // Update podataka
        try{
            String id = jTextFieldIDOdlozeniRadovi.getText();
            int idVazduhoplova = jComboBoxRGOznakaOdlozeniRadovi.getSelectedIndex();
            String Izvor = jTextFieldIzvorOdlozeniRadovi.getText();
            String R_BR = jTextFieldRedniBrojOdlozeniRadovi.getText();
            String Opis = jTextAreaOpisOdlozeniRadovi.getText();
            String Datum_Opis = ((JTextField) jDateChooserOpisOdlozeniRadovi.getDateEditor().getUiComponent()).getText();
            int idZaposlenogOpis = jComboZaposleniOpisOdlozeniRadovi.getSelectedIndex();
            String Uradjeno = jTextAreaUradjenoOdlozeniRadovi.getText();
            String Datum_Uradjeno = ((JTextField) jDateChooserUradjenoOdlozeniRadovi.getDateEditor().getUiComponent()).getText();
            int idZaposlenogUradjeno = jComboZaposleniUradjenoOdlozeniRadovi.getSelectedIndex();
            String sql="update odlozeni_radovi set id='"+id+"',Izvor='"+Izvor+"',R_BR='"+R_BR+"',Opis='"+Opis+"',Datum_Opis='"+Datum_Opis+"',idZaposlenogOpis='"+idZaposlenogOpis+"',Uradjeno='"+Uradjeno+"',Datum_Uradjeno='"+Datum_Uradjeno+"',idZaposlenogUradjeno='"+idZaposlenogUradjeno+"', idVazduhoplova='"+idVazduhoplova+"' where id='"+id+"' ";
            pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);
            //Ponovno ucitavanje podataka u tabelu
            Update_OdlozeniRadovi();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateOdlozeniRadoviActionPerformed

    private void Button_clearOdlozeniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearOdlozeniRadoviActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            jComboBoxRGOznakaOdlozeniRadovi.setSelectedIndex(0);
            jComboZaposleniOpisOdlozeniRadovi.setSelectedIndex(0);
            jComboZaposleniUradjenoOdlozeniRadovi.setSelectedIndex(0);
            jTextFieldRedniBrojOdlozeniRadovi.setText("");
            jTextFieldIzvorOdlozeniRadovi.setText("");
            jTextFieldIDOdlozeniRadovi.setText("");
            jTextAreaOpisOdlozeniRadovi.setText("");
            jTextAreaUradjenoOdlozeniRadovi.setText("");
            ((JTextField) jDateChooserOpisOdlozeniRadovi.getDateEditor().getUiComponent()).setText("");
            ((JTextField) jDateChooserUradjenoOdlozeniRadovi.getDateEditor().getUiComponent()).setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearOdlozeniRadoviActionPerformed

    private void Button_insertOdlozeniRadoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertOdlozeniRadoviActionPerformed
        // Insert novih podataka
        try{
            String sql="Insert into odlozeni_radovi(id,idVazduhoplova,Izvor,R_BR,Opis,Datum_Opis,idZaposlenogOpis,Uradjeno,Datum_Uradjeno,idZaposlenogUradjeno) values (?,?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,jTextFieldIDOdlozeniRadovi.getText());
            pst.setInt(2, jComboBoxRGOznakaOdlozeniRadovi.getSelectedIndex());
            pst.setString(3,jTextFieldIzvorOdlozeniRadovi.getText());
            pst.setString(4,jTextFieldRedniBrojOdlozeniRadovi.getText());
            pst.setString(5,jTextAreaOpisOdlozeniRadovi.getText());
            pst.setString(6,((JTextField) jDateChooserOpisOdlozeniRadovi.getDateEditor().getUiComponent()).getText());
            pst.setInt(7, jComboZaposleniOpisOdlozeniRadovi.getSelectedIndex());
            pst.setString(8,jTextAreaUradjenoOdlozeniRadovi.getText());
            pst.setString(9,((JTextField) jDateChooserUradjenoOdlozeniRadovi.getDateEditor().getUiComponent()).getText());
            pst.setInt(10, jComboZaposleniUradjenoOdlozeniRadovi.getSelectedIndex());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);
            // Ponovno ucitavanje podataka u tabelu
            Update_OdlozeniRadovi();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertOdlozeniRadoviActionPerformed

    private void txt_pretragaOdlozeniRadoviKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pretragaOdlozeniRadoviKeyReleased
        //popuna txt polja podacima za pretragu
        try{
            String sql="select * from odlozeni_radovi where Izvor=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaOdlozeniRadovi.getText());
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            popunaPolljaOdlozeniRadovi();//popuna polja Odlozeni Radovi
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txt_pretragaOdlozeniRadoviKeyReleased

    private void jTableOdlozeniRadoviKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableOdlozeniRadoviKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =jTableOdlozeniRadovi.getSelectedRow();
                String Table_click=(jTableOdlozeniRadovi.getModel().getValueAt(row, 0).toString());
                String sql="select * from odlozeni_radovi where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                popunaPolljaOdlozeniRadovi();//popuna polja Odlozeni Radovi
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jTableOdlozeniRadoviKeyReleased

    private void jTableOdlozeniRadoviMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableOdlozeniRadoviMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{
            int row =jTableOdlozeniRadovi.getSelectedRow();
            String Table_click=(jTableOdlozeniRadovi.getModel().getValueAt(row, 0).toString());
            String sql="select * from odlozeni_radovi where id='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            popunaPolljaOdlozeniRadovi();//popuna polja Odlozeni Radovi
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jTableOdlozeniRadoviMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        dostupnostInternetaAutor();//Odlazak na sajt autora
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Servis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Servis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Servis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Servis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servis().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_Exit;
    private javax.swing.JButton Button_clearIzvrseniRadovi;
    private javax.swing.JButton Button_clearOdlozeniRadovi;
    private javax.swing.JButton Button_clearRadniNalog;
    private javax.swing.JButton Button_clearVazduhoplova;
    private javax.swing.JButton Button_deleteIzvrseniRadovi;
    private javax.swing.JButton Button_deleteOdlozeniRadovi;
    private javax.swing.JButton Button_deleteRadniNalog;
    private javax.swing.JButton Button_deleteVazduhoplova;
    private javax.swing.JButton Button_insertIzvrseniRadovi;
    private javax.swing.JButton Button_insertOdlozeniRadovi;
    private javax.swing.JButton Button_insertRadniNalog;
    private javax.swing.JButton Button_insertVazduhoplova;
    private javax.swing.JButton Button_internet;
    private javax.swing.JButton Button_proveraKonekcijaBaze;
    private javax.swing.JButton Button_refreshIzvrseniRadovi;
    private javax.swing.JButton Button_refreshOdlozeniRadovi;
    private javax.swing.JButton Button_refreshRadniNalog;
    private javax.swing.JButton Button_refreshVazduhoplova;
    private javax.swing.JButton Button_singout;
    private javax.swing.JButton Button_stampaIzvrseniRadovi;
    private javax.swing.JButton Button_stampaOdlozeniRadovi;
    private javax.swing.JButton Button_stampaRadniNalog;
    private javax.swing.JButton Button_stampaVazduhoplova;
    private javax.swing.JButton Button_updateIzvrseniRadovi;
    private javax.swing.JButton Button_updateOdlozeniRadovi;
    private javax.swing.JButton Button_updateRadniNalog;
    private javax.swing.JButton Button_updateVazduhoplova;
    private javax.swing.JMenuBar MenuBar_adminMeni;
    private javax.swing.JMenuItem MenuItem_Exit;
    private javax.swing.JMenuItem MenuItem_LogOut;
    private javax.swing.JMenuItem MenuItem_baza;
    private javax.swing.JMenuItem MenuItem_internet;
    private javax.swing.JMenu Menu_opste;
    private javax.swing.JToolBar ToolBar_OpsteFunkcije;
    private javax.swing.JComboBox jComboBoxBrojRNIzvrseniRadovi;
    private javax.swing.JComboBox jComboBoxOVazduhoplovu;
    private javax.swing.JComboBox jComboBoxOVazduhoplovuIzvrseniRadovi;
    private javax.swing.JComboBox jComboBoxRGOznakaOdlozeniRadovi;
    private javax.swing.JComboBox jComboBoxRukovodilacTehSluzbeIzvrseniRadovi;
    private javax.swing.JComboBox jComboZaposleniOpisOdlozeniRadovi;
    private javax.swing.JComboBox jComboZaposleniUradjenoOdlozeniRadovi;
    private com.toedter.calendar.JDateChooser jDateChooserDatumPredajeIzvrseniRadovi;
    private com.toedter.calendar.JDateChooser jDateChooserOpisOdlozeniRadovi;
    private com.toedter.calendar.JDateChooser jDateChooserOtvaranjeRN;
    private com.toedter.calendar.JDateChooser jDateChooserPocetakIzvrseniRadovi;
    private com.toedter.calendar.JDateChooser jDateChooserPocetakRN;
    private com.toedter.calendar.JDateChooser jDateChooserPreuzetIzvrseniRadovi;
    private com.toedter.calendar.JDateChooser jDateChooserPrimljenIzvrseniRadovi;
    private com.toedter.calendar.JDateChooser jDateChooserUradjenoOdlozeniRadovi;
    private com.toedter.calendar.JDateChooser jDateChooserZavrsetakIzvrseniRadovi;
    private com.toedter.calendar.JDateChooser jDateChooserZavrsetakRN;
    private javax.swing.JLabel jLabelBRDelaRN;
    private javax.swing.JLabel jLabelBRNalogRN;
    private javax.swing.JLabel jLabelBRPrilogaIzvrseniRadovi;
    private javax.swing.JLabel jLabelBrojRNIzvrseniRadovi;
    private javax.swing.JLabel jLabelDCVIzvrseniRadovi;
    private javax.swing.JLabel jLabelDatumOpisOdlozeniRadovi;
    private javax.swing.JLabel jLabelDatumPredajeIzvrseniRadovi;
    private javax.swing.JLabel jLabelDatumUradjenoOdlozeniRadovi;
    private javax.swing.JLabel jLabelIDIzvrseniRadovi;
    private javax.swing.JLabel jLabelIDRN;
    private javax.swing.JLabel jLabelIdOdlozeniRadovi;
    private javax.swing.JLabel jLabelIzvorOdlozeniRadovi;
    private javax.swing.JLabel jLabelLansiraoRN;
    private javax.swing.JLabel jLabelMestoIzvrseniRadovi;
    private javax.swing.JLabel jLabelNORN;
    private javax.swing.JLabel jLabelNapomenaIzvrseniRadovi;
    private javax.swing.JLabel jLabelNapomenaRN;
    private javax.swing.JLabel jLabelNapomeneRN;
    private javax.swing.JLabel jLabelNazivRN;
    private javax.swing.JLabel jLabelNzivRN;
    private javax.swing.JLabel jLabelOpisOdlozeniRadovi;
    private javax.swing.JLabel jLabelOtvaranjeRN;
    private javax.swing.JLabel jLabelOveriRN;
    private javax.swing.JLabel jLabelOznakaRN;
    private javax.swing.JLabel jLabelPocetakIzvrseniRadovi;
    private javax.swing.JLabel jLabelPocetakRN;
    private javax.swing.JLabel jLabelPozdravnaPoruka;
    private javax.swing.JLabel jLabelPredmerIzvrseniRadovi;
    private javax.swing.JLabel jLabelPredmerRN;
    private javax.swing.JLabel jLabelPreuzeIzvrseniRadovi;
    private javax.swing.JLabel jLabelPrimljenIzvrseniRadovi;
    private javax.swing.JLabel jLabelRGBroj;
    private javax.swing.JLabel jLabelRedniBrojOdlozeniRadovi;
    private javax.swing.JLabel jLabelRukovodilacTehSluzbeIzvrseniRadovi;
    private javax.swing.JLabel jLabelUradjenoOdlozeniRadovi;
    public static javax.swing.JLabel jLabelUser;
    private javax.swing.JLabel jLabelZaposleniOdlozeniRadovi;
    private javax.swing.JLabel jLabelZaposleniUradjenoOdlozeniRadovi;
    private javax.swing.JLabel jLabelZavrsetakIzvrseniRadovi;
    private javax.swing.JLabel jLabelZavrsetakRN;
    private javax.swing.JLabel jLabel_casoviLetaVazduhoplova;
    private javax.swing.JLabel jLabel_casoviLetaVazduhoplovaIzvrseniRadovi;
    private javax.swing.JLabel jLabel_casoviLetaVazduhoplovaRN;
    private javax.swing.JLabel jLabel_ciklusaVazduhoplova;
    private javax.swing.JLabel jLabel_ciklusaVazduhoplovaIzvrseniRadovi;
    private javax.swing.JLabel jLabel_ciklusaVazduhoplovaRN;
    private javax.swing.JLabel jLabel_idVazduhoplova;
    private javax.swing.JLabel jLabel_naletVazduhoplova;
    private javax.swing.JLabel jLabel_naletVazduhoplovaIzvrseniRadovi;
    private javax.swing.JLabel jLabel_naletVazduhoplovaRN;
    private javax.swing.JLabel jLabel_rgVazduhoplova;
    private javax.swing.JLabel jLabel_rgVazduhoplovaIzvrseniRadovi;
    private javax.swing.JLabel jLabel_rgVazduhoplovaRN;
    private javax.swing.JLabel jLabel_serijskiBrojVazduhoplova;
    private javax.swing.JLabel jLabel_serijskiBrojVazduhoplovaIzvrseniRadovi;
    private javax.swing.JLabel jLabel_serijskiBrojVazduhoplovaRN;
    private javax.swing.JLabel jLabel_tipVazduhoplova;
    private javax.swing.JLabel jLabel_tipVazduhoplovaIzvrseniRadovi;
    private javax.swing.JLabel jLabel_tipVazduhoplovaRN;
    private javax.swing.JLabel jLabel_vlasnikVazduhoplova;
    private javax.swing.JLabel jLabel_vlasnikVazduhoplovaIzvrseniRadovi;
    private javax.swing.JLabel jLabel_vlasnikVazduhoplovaRN;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanelDokumentaRN;
    private javax.swing.JPanel jPanelIzvrseniRadovi;
    private javax.swing.JPanel jPanelOVazduhoplovu;
    private javax.swing.JPanel jPanelOVazduhoplovuIzvrseniRadovi;
    private javax.swing.JPanel jPanelOdlozeniRadovi;
    private javax.swing.JPanel jPanelOdlozeniRadoviPolja;
    private javax.swing.JPanel jPanelPanelIzvrseniRadovi;
    private javax.swing.JPanel jPanelPoljaZaRadniNalog;
    private javax.swing.JPanel jPanelPoljaZaRadniNalogIzvrseniRadovi;
    private javax.swing.JPanel jPanelRadniNalog;
    private javax.swing.JPanel jPanelVazduhoplov;
    private javax.swing.JPanel jPanel_funkcionalnaDugmad;
    private javax.swing.JPanel jPanel_funkcionalnaDugmad1;
    private javax.swing.JPanel jPanel_funkcionalnaDugmadIzvrseniRadovi;
    private javax.swing.JPanel jPanel_poljaVazduhoplov;
    private javax.swing.JPanel jPanel_poljaVazduhoplov1;
    private javax.swing.JPanel jPanel_poljaVazduhoplovIzvrseniRadovi;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPaneIzvrseniRadovi;
    private javax.swing.JScrollPane jScrollPaneIzvrseniRadovi1;
    private javax.swing.JScrollPane jScrollPaneIzvrseniRadoviPanel;
    private javax.swing.JScrollPane jScrollPaneNalogRadni;
    private javax.swing.JScrollPane jScrollPaneNalogRadniIzvrseniRadovi;
    private javax.swing.JScrollPane jScrollPaneNapomenaRN;
    private javax.swing.JScrollPane jScrollPaneOdlozeniRadoviTable;
    private javax.swing.JScrollPane jScrollPaneOpisOdlozeniRadovi;
    private javax.swing.JScrollPane jScrollPanePratecaDokumentacijaRN;
    private javax.swing.JScrollPane jScrollPaneRadniNalog;
    private javax.swing.JScrollPane jScrollPaneTableRadniNalog;
    private javax.swing.JScrollPane jScrollPaneUradjenoOdlozeniRadovi;
    private javax.swing.JScrollPane jScrollPaneVazduhoplovIzvrseniRadovi;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPaneIzvrseniRadovi;
    private javax.swing.JTabbedPane jTabbedPaneRadniNalog;
    private javax.swing.JTabbedPane jTabbedPaneServisHeliVision;
    private javax.swing.JTable jTableIzvrseniRadovi;
    private javax.swing.JTable jTableOdlozeniRadovi;
    private javax.swing.JTable jTableRadniNalog;
    private javax.swing.JTable jTableVazduhoplov;
    private javax.swing.JTextArea jTextAreaNapomenaIzvrseniRadovi;
    private javax.swing.JTextArea jTextAreaNapomeneRN;
    private javax.swing.JTextArea jTextAreaOpisOdlozeniRadovi;
    private javax.swing.JTextArea jTextAreaPredmetRN;
    private javax.swing.JTextArea jTextAreaPredmetRNIzvrseniRadovi;
    private javax.swing.JTextArea jTextAreaUradjenoOdlozeniRadovi;
    private javax.swing.JTextField jTextFieldBRDelaRN;
    private javax.swing.JTextField jTextFieldBRNalogaRN;
    private javax.swing.JTextField jTextFieldBRPrilogaIzvrseniRadovi;
    private javax.swing.JTextField jTextFieldDCVIzvrseniRadovi;
    private javax.swing.JTextField jTextFieldIDIzvrseniRadovi;
    private javax.swing.JTextField jTextFieldIDOdlozeniRadovi;
    private javax.swing.JTextField jTextFieldIDRN;
    private javax.swing.JTextField jTextFieldIzvorOdlozeniRadovi;
    private javax.swing.JTextField jTextFieldLansiraoRN;
    private javax.swing.JTextField jTextFieldNORN1;
    private javax.swing.JTextField jTextFieldNORN2;
    private javax.swing.JTextField jTextFieldNORN3;
    private javax.swing.JTextField jTextFieldNORN4;
    private javax.swing.JTextField jTextFieldNORN5;
    private javax.swing.JTextField jTextFieldNORN6;
    private javax.swing.JTextField jTextFieldNapomenaRN1;
    private javax.swing.JTextField jTextFieldNapomenaRN2;
    private javax.swing.JTextField jTextFieldNapomenaRN3;
    private javax.swing.JTextField jTextFieldNapomenaRN4;
    private javax.swing.JTextField jTextFieldNapomenaRN5;
    private javax.swing.JTextField jTextFieldNapomenaRN6;
    private javax.swing.JTextField jTextFieldNazivDokumentaRN1;
    private javax.swing.JTextField jTextFieldNazivDokumentaRN2;
    private javax.swing.JTextField jTextFieldNazivDokumentaRN3;
    private javax.swing.JTextField jTextFieldNazivDokumentaRN4;
    private javax.swing.JTextField jTextFieldNazivDokumentaRN5;
    private javax.swing.JTextField jTextFieldNazivDokumentaRN6;
    private javax.swing.JTextField jTextFieldNazivRN;
    private javax.swing.JTextField jTextFieldOverioRN;
    private javax.swing.JTextField jTextFieldOznakaRN1;
    private javax.swing.JTextField jTextFieldOznakaRN2;
    private javax.swing.JTextField jTextFieldOznakaRN3;
    private javax.swing.JTextField jTextFieldOznakaRN4;
    private javax.swing.JTextField jTextFieldOznakaRN5;
    private javax.swing.JTextField jTextFieldOznakaRN6;
    private javax.swing.JTextField jTextFieldRedniBrojOdlozeniRadovi;
    private javax.swing.JTextField jTextField_casovaLetaVazduhoplova;
    private javax.swing.JTextField jTextField_casovaLetaVazduhoplovaIzvrseniRadovi;
    private javax.swing.JTextField jTextField_casovaLetaVazduhoplovaRN;
    private javax.swing.JTextField jTextField_ciklusaVazduhoplova;
    private javax.swing.JTextField jTextField_ciklusaVazduhoplovaIzvrseniRadovi;
    private javax.swing.JTextField jTextField_ciklusaVazduhoplovaRN;
    private javax.swing.JTextField jTextField_idVazduhoplova;
    private javax.swing.JTextField jTextField_naletVazduhoplova;
    private javax.swing.JTextField jTextField_naletVazduhoplovaIzvrseniRadovi;
    private javax.swing.JTextField jTextField_naletVazduhoplovaRN;
    private javax.swing.JTextField jTextField_rgVazduhoplova;
    private javax.swing.JTextField jTextField_serijskiBrojVazduhoplova;
    private javax.swing.JTextField jTextField_serijskiBrojVazduhoplovaIzvrseniRadovi;
    private javax.swing.JTextField jTextField_serijskiBrojVazduhoplovaRN;
    private javax.swing.JTextField jTextField_tipVazduhoplova;
    private javax.swing.JTextField jTextField_tipVazduhoplovaIzvrseniRadovi;
    private javax.swing.JTextField jTextField_tipVazduhoplovaRN;
    private javax.swing.JTextField jTextField_vlasnikVazduhoplova;
    private javax.swing.JTextField jTextField_vlasnikVazduhoplovaIzvrseniRadovi;
    private javax.swing.JTextField jTextField_vlasnikVazduhoplovaRN;
    private javax.swing.JTextField jTextFieldjMestoIzvrseniRadovi;
    private javax.swing.JTextField txt_pretragaIzvrseniRadovi;
    private javax.swing.JTextField txt_pretragaOdlozeniRadovi;
    private javax.swing.JTextField txt_pretragaRadniNalog;
    private javax.swing.JTextField txt_pretragaVazduhoplova;
    // End of variables declaration//GEN-END:variables
}
