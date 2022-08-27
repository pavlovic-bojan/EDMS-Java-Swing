/**
 *
 * @author Bojan Pavlović
 */
import java.net.InetSocketAddress;
import java.net.Socket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;


import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.io.*;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Admin extends javax.swing.JFrame {
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    /**
     * Creates new form Admin
     */
    public Admin() {
        initComponents();
        conn=Data.javaconnect.ConnecrDb();// Preusmerenje na klasu za konekciju na bazu
        setIcon();//Unos ikonice za prozor
        Update_Doc();//Ažuriranje tabele dokumenta
        Update_Kat();//Ažuriranje tabele kategorije dokumenata
        Update_Uloge();//Ažuriranje tabele korisnici uloge
        Update_Users();//Ažuriranje tabele korisnici
        Update_KontrolaPristupa();//Ažuriranje tabele Kontrola Pristupa gde se skladiste svi podaci o loginu na sistem
        Update_ObnovaLozinke();//Ažuriranje tabele obnova lozinke
        ComboBox_KategorijaDokumenta();//Kombo box za odabir kategorije dokumenta
        ComboBox_Users();//Kombo box za odabir uloge za login
        Update_OglasnaTabla();//Ažuriranje tabele oglasna tabla
        Update_KatVozila();//Ažuriranje tabele Kategorija Vozila
        ComboBox_KategorijaVozila();//Kombo box za odabir kategorije vozila
        Update_Vozila();//Ažuriranje tabele Vozila
        ComboBox_Vozila();//Kombo box za odabir vozila koje ide na servis
        ComboBox_Zaposleni();//Kombo box koji zaposleni je predao vozilo na servis
        Update_ServisVozila();//Ažuriranje tabele Servis Vozila
        ComboBox_ZaposleniKretanjeVozila();//Kombo box koji ispisuje zaposlene u panelu servis vozila kretanje vozila
        ComboBox_VozilaKretanjaVozila();////Kombo box koji ispisuje vozila u panelu servis vozila kretanje vozila
        Update_ServisKretanjeVozila();//Azuriranje tabele za Dnevna Kretanja Vozila
    }

    //polja servis vozila dnevna kretanja
    public void poljaServisVozilaDnevnaKretanja(){
        try{
            if(rs.next()){
                    String add1=rs.getString("id");
                    txt_IDDnevnoKretanjeVozila.setText(add1);
    
                    int add2=rs.getInt("idVozila");
                    jComboBox_VoziloDnevnoKretanjeVozila.setSelectedIndex(add2);

                    String add3=rs.getString("Startna_Kilometraza");
                    txt_PocetnaKilometrazaDnevnoKretanjeVozila.setText(add3);

                    String add4=rs.getString("Krajnja_Kilometraza");
                    txt_KrajKmDnevnoKretanjeVozila.setText(add4);
                     
                    int add5=rs.getInt("idKorisnika");
                    jComboBoxZaposleniiDnevnoKretanjeVozila.setSelectedIndex(add5); 
                    
                    String add6=rs.getString("Potrosnja");
                    txt_PotrosnjaDnevnoKretanjeVozila.setText(add6);
                                   
                    java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add7 = sdf.parse(rs.getString("Datum_Voznje"));
                    DateChooser_DatumDnevnogKretanjaVozila.setDate(add7);
                    
                    String add8=rs.getString("Opis");
                    txt_opisDnevnogKretanjaVozila.setText(add8);
                    
                    String add9=rs.getString("Broj_Naloga");
                    txt_brNalogaDnevnogKretanjaVozila.setText(add9);
    
                      
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //polja servis vozila
    public void poljaServisVozila(){
        try{
            if(rs.next()){
                    String add1=rs.getString("id");
                    txt_idServisVozila.setText(add1);
    
                    int add2=rs.getInt("idVozila");
                    ComboBox_ServisVozila.setSelectedIndex(add2);

                    java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add3 = sdf.parse(rs.getString("Datum_Predaje"));
                    DateChooserDatumVozilaServis.setDate(add3);

                    String add4=rs.getString("Kilometraza");
                    txt_KilometrazaVozilaServis.setText(add4);
                     
                    String add5=rs.getString("Opis");
                    txt_OpisServisVozila.setText(add5);
    
                    int add6=rs.getInt("idKorisnik");
                    jComboBox_ZaposleniServisVozila.setSelectedIndex(add6);   
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //polja vozila
    public void poljaVozila(){
        try{
            if(rs.next()){
                    String add1=rs.getString("id");
                    txt_idVozila.setText(add1);
    
                    int add2=rs.getInt("idKat");
                    ComboBox_KategorijaVozila.setSelectedIndex(add2);

                    String add3=rs.getString("Marka");
                    txt_MarkaVozila.setText(add3);

                    String add4=rs.getString("Tip");
                    txt_TipVozila.setText(add4);
                    
                    String add5=rs.getString("Registracione_oznake");
                    txt_RegVozila.setText(add5);
                     
                    String add6=rs.getString("Opis");
                    txt_OpisVozila.setText(add6);
    
                    String add7=rs.getString("Potrosnja");
                    txt_PotrosnjsVozila.setText(add7);    
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
   //polja kategorija vozila
    public void poljaKatVozila(){
        try{
            if(rs.next()){
                    String add1=rs.getString("id");
                    TextField_idKatVozila.setText(add1);

                    String add2=rs.getString("Naziv");
                    TextField_nazivKatVozila.setText(add2);

                    String add3=rs.getString("Opis");
                    TextArea_opisKatVozila.setText(add3);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //popuna polja za vesti
    public void poljaVesti(){
        try{
            if(rs.next()){
                    String add1=rs.getString("ID");
                    TextField_idOT.setText(add1);

                    String add2=rs.getString("Naziv");
                    TextField_nazivOT.setText(add2);

                    String add3=rs.getString("Opis");
                    TextArea_opisOT.setText(add3);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
   //popuna polja za korisnik tabelu
   public void poljaKorisnik(){ 
            try{
            //popuna txt polja podacima iz tabele
                if(rs.next()){
                    String add1=rs.getString("id");
                    txt_IDUsers.setText(add1);

                    String add2=rs.getString("Korisnicko_Ime");
                    txt_UserName.setText(add2);

                    String add3=rs.getString("Lozinka");
                    txt_Pass.setText(add3);

                    String add4=rs.getString("Ime");
                    txt_Name.setText(add4);

                    String add5=rs.getString("Prezime");
                    txt_LastName.setText(add5);

                    String add6=rs.getString("Email");
                    txt_EmailUser.setText(add6);

                    String add7=rs.getString("Tel_Posao");
                    txt_JobPhon.setText(add7);

                    String add8=rs.getString("Tel_Mobilni");
                    txt_TelMob.setText(add8);

                    int add9=rs.getInt("Uloge_za_Login");
                    ComboBox_Users.setSelectedIndex(add9);

                    String add10=rs.getString("Opis");
                    txt_opisUser.setText(add10);

                    //Date add11=rs.getDate("Datum");
                    //DateChooser_regDatum.setDate(add11);
                }
        }
        catch(Exception e){
                        JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Popuna polja dokumenta
    public void dokumentaPolja(){
        try{
            if(rs.next()){
                    String add1=rs.getString("ID");
                    txt_ID.setText(add1);

                    String add2=rs.getString("Broj");
                    txt_Broj.setText(add2);

                    String add3=rs.getString("Naziv");
                    txt_Naziv.setText(add3);

                    String add4=rs.getString("Opis");
                    txt_Opis.setText(add4);

                    //Date add5=rs.getDate("Datum_Unosa");
                    //jDateChooserDatum.setDate(add5);

                    String add6=rs.getString("Dokument");
                    txt_Dokument.setText(add6);
                    
                    int add7=rs.getInt("ID_Kategorije");
                    ComboBox_KategorijaDokumenta.setSelectedIndex(add7);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    private void setIcon() {
        //Ovo je metod koji unosi ikonicu u prozor
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Grafika/icon.png")));

    }
        
    public void dostupnostInterneta(){
    // Provera da li je internet dostupan
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
    //Popuna tabele za kretanja vozila
    private void Update_ServisKretanjeVozila(){
        
        try{
        String sql="select kretanje_vozila.id,vozila.Marka,kretanje_vozila.Startna_Kilometraza,kretanje_vozila.Krajnja_Kilometraza,korisnici.Prezime,kretanje_vozila.Potrosnja,kretanje_vozila.Datum_Voznje,kretanje_vozila.Opis,kretanje_vozila.Broj_Naloga \n" +
                   "from kretanje_vozila inner join vozila on kretanje_vozila.idVozila = vozila.id \n" +
                   "inner join korisnici on kretanje_vozila.idKorisnika = korisnici.id";//Upit za popunu tabele
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        Table_DnevnoKretanjeVozila.setModel(DbUtils.resultSetToTableModel(rs));
                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Popuna tabele za servis vozila
    private void Update_ServisVozila(){
        
        try{
        String sql="select servis_vozila.id,vozila.Marka,servis_vozila.Datum_Predaje,servis_vozila.Kilometraza,servis_vozila.Opis,korisnici.Prezime \n" +
                   "from servis_vozila inner join vozila on servis_vozila.idVozila = vozila.id \n" +
                   "inner join korisnici on servis_vozila.idKorisnik = korisnici.id";//Upit za popunu tabele
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        Table_ServisVozila.setModel(DbUtils.resultSetToTableModel(rs));
                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna tabele vozila
    private void Update_Vozila(){
        
        try{
        String sql="select vozila.id,kategorije_vozila.Naziv,vozila.Marka,vozila.Tip,vozila.Registracione_oznake,vozila.Opis,vozila.Potrosnja\n" +
                   "from vozila inner join kategorije_vozila \n" +
                   "on vozila.idKat= kategorije_vozila.id";//Upit za popunu tabele
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        Table_Vozila.setModel(DbUtils.resultSetToTableModel(rs));
                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna tabele kategorija vozila
    private void Update_KatVozila(){
        
        try{
        String sql="select * from kategorije_vozila";//Upit za popunu tabele
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        Table_KatVozila.setModel(DbUtils.resultSetToTableModel(rs));
                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna tabele oglasna tabla
    private void Update_OglasnaTabla(){
        
        try{
        String sql="select * from oglasna_tabla";//Upit za popunu tabele
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        Table_OglasnaTabla.setModel(DbUtils.resultSetToTableModel(rs));
                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna tabele korisnici
     private void Update_Users(){
        
        try{
        String sql="select korisnici.id,Korisnici.Ime,korisnici.Prezime,uloge_za_login.Naziv,korisnici.Email,korisnici.Tel_Posao,korisnici.Tel_Mobilni,korisnici.Korisnicko_Ime,korisnici.Lozinka,korisnici.Opis\n" +
                   "from korisnici inner join uloge_za_login\n" +
                   "on korisnici.Uloge_za_Login= uloge_za_login.id";//Upit za popunu tabele
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        Table_Users.setModel(DbUtils.resultSetToTableModel(rs));
                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna tabele uloge za login
    private void Update_Uloge(){
        
        try{
        String sql="select * from uloge_za_login";//Upit za popunu tabele
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        Table_UlogezaLogin.setModel(DbUtils.resultSetToTableModel(rs));
                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna tabele kategorije dokumenata
    private void Update_Kat(){
        
        try{
        String sql="select * from kategorije_dokumenata";//Upit za popunu tabele
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        Table_KatDocument.setModel(DbUtils.resultSetToTableModel(rs));
                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna tabele dokumenta 
    private void Update_Doc(){
        
        try{
        String sql="select dokumenta.ID,dokumenta.Broj,dokumenta.Naziv,kategorije_dokumenata.Naziv_Kategorije,dokumenta.Datum_Unosa,dokumenta.Opis,dokumenta.Dokument\n" +
                   "from dokumenta inner join kategorije_dokumenata \n" +
                   "on dokumenta.ID_Kategorije= kategorije_dokumenata.ID";//Upit za popunu tabele
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        Table_document.setModel(DbUtils.resultSetToTableModel(rs));
                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna tabele za kontrolu pristupa
    private void Update_KontrolaPristupa() {

        try {
            String sql = "select pracenje_login.Ime_Korisnika,uloge_za_login.Naziv,pracenje_login.Datum\n" +
                     "from pracenje_login inner join uloge_za_login\n" +
                     "on pracenje_login.id_Uloge= uloge_za_login.id  order by pracenje_login.Datum desc";//Upit za popunu tabele
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            Table_KontrolaPristupa.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        //popuna tabele za obnovu lozinke 
        private void Update_ObnovaLozinke() {

        try {
            String sql = "select * from obnova_lozinke";//Upit za popunu tabele

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable_ObnovaLozinke.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
    //popuna boxa za vozila u Panelu servis kretanje vozila
    private void ComboBox_VozilaKretanjaVozila(){
    
        try{
        String sql="select * from vozila";//Upit za popunu vozila
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
   
            while(rs.next()){
            
                String Naziv=rs.getString("Marka");
                jComboBox_VoziloDnevnoKretanjeVozila.addItem(Naziv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna kombo boxa u panelu Servis Kretanje vozila za zaposlene
    private void ComboBox_ZaposleniKretanjeVozila(){
    
        try{
        String sql="select * from korisnici";//Upit za popunu zaposlenih
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
   
            while(rs.next()){
            
                String Naziv=rs.getString("Prezime");
                jComboBoxZaposleniiDnevnoKretanjeVozila.addItem(Naziv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }    
    //popuna kombo boxa u panelu Servis vozila za zaposlene
    private void ComboBox_Zaposleni(){
    
        try{
        String sql="select * from korisnici";//Upit za popunu zaposlenih
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
   
            while(rs.next()){
            
                String Naziv=rs.getString("Prezime");
                jComboBox_ZaposleniServisVozila.addItem(Naziv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }  
    //popuna boxa za vozila u Panelu servis vozila  
    private void ComboBox_Vozila(){
    
        try{
        String sql="select * from vozila";//Upit za popunu vozila
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
   
            while(rs.next()){
            
                String Naziv=rs.getString("Marka");
                ComboBox_ServisVozila.addItem(Naziv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
        //popuna kombo kategorija vozila u panelu vozila
    private void ComboBox_KategorijaVozila(){
    
        try{
        String sql="select * from kategorije_vozila";//Upit za popunu kategorije
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
   
            while(rs.next()){
            
                String Naziv=rs.getString("Naziv");
                ComboBox_KategorijaVozila.addItem(Naziv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }  
    //popuna polja u kombo kategorija dokumenata u panelu dokumenti    
    private void ComboBox_KategorijaDokumenta(){
    
        try{
        String sql="select * from kategorije_dokumenata";//Upit za popunu kategorije
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
   
            while(rs.next()){
            
                String Naziv=rs.getString("Naziv_Kategorije");
                ComboBox_KategorijaDokumenta.addItem(Naziv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //popuna polja kombo korisnik u panelu korisnik
    private void ComboBox_Users(){
    
        try{
        String sql="select * from uloge_za_login";//Upit za popunu uloga
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        
            while(rs.next()){
            
                String Naziv=rs.getString("Naziv");
                ComboBox_Users.addItem(Naziv);
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
        TabbedPane_Administracija = new javax.swing.JTabbedPane();
        TabbedPane_Doc = new javax.swing.JTabbedPane();
        Panel_AdministracijaDoc = new javax.swing.JPanel();
        Panel_upravljanjeDokumentima = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_document = new javax.swing.JTable();
        Panel_AdminDoc = new javax.swing.JPanel();
        Label_ID = new javax.swing.JLabel();
        Label_Broj = new javax.swing.JLabel();
        Label_Naziv = new javax.swing.JLabel();
        Label_Opis = new javax.swing.JLabel();
        Label_Dokument = new javax.swing.JLabel();
        Label_ID_Kategorije = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_Opis = new javax.swing.JTextArea();
        ComboBox_KategorijaDokumenta = new javax.swing.JComboBox();
        txt_ID = new javax.swing.JTextField();
        txt_Broj = new javax.swing.JTextField();
        txt_Naziv = new javax.swing.JTextField();
        txt_Dokument = new javax.swing.JTextField();
        Button_unosDokumenta = new javax.swing.JButton();
        Panel_Pretraga = new javax.swing.JPanel();
        txt_pretraga = new javax.swing.JTextField();
        Panel_FuncButton = new javax.swing.JPanel();
        Button_clear = new javax.swing.JButton();
        Button_delete = new javax.swing.JButton();
        Button_insert = new javax.swing.JButton();
        Button_update = new javax.swing.JButton();
        Button_stampaDoc = new javax.swing.JButton();
        Button_refreshDoc = new javax.swing.JButton();
        Panel_AdminKategorijaDoc = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_KatDocument = new javax.swing.JTable();
        Panel_AdminDocKat = new javax.swing.JPanel();
        Label_IDKat = new javax.swing.JLabel();
        Label_NazivKat = new javax.swing.JLabel();
        Label_OpisKat = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_OpisKat = new javax.swing.JTextArea();
        txt_IDKat = new javax.swing.JTextField();
        txt_NazivKat = new javax.swing.JTextField();
        Panel_FuncButtonKat = new javax.swing.JPanel();
        Button_clearKat = new javax.swing.JButton();
        Button_deleteKat = new javax.swing.JButton();
        Button_insertKat = new javax.swing.JButton();
        Button_updateKat = new javax.swing.JButton();
        Button_refreshKat = new javax.swing.JButton();
        Button_stampaKat = new javax.swing.JButton();
        Panel_Zaposleni = new javax.swing.JPanel();
        TabbedPane_Korisnici = new javax.swing.JTabbedPane();
        Panel_AdminKorisnici = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        Table_Users = new javax.swing.JTable();
        Panel_PretragaKorisnika = new javax.swing.JPanel();
        txt_pretragaUsers = new javax.swing.JTextField();
        Panel_AdminUsers = new javax.swing.JPanel();
        txt_UserName = new javax.swing.JTextField();
        txt_Pass = new javax.swing.JTextField();
        txt_Name = new javax.swing.JTextField();
        txt_LastName = new javax.swing.JTextField();
        txt_JobPhon = new javax.swing.JTextField();
        txt_EmailUser = new javax.swing.JTextField();
        txt_TelMob = new javax.swing.JTextField();
        ComboBox_Users = new javax.swing.JComboBox();
        jScrollPane8 = new javax.swing.JScrollPane();
        txt_opisUser = new javax.swing.JTextArea();
        jLabel_idZaposlenog = new javax.swing.JLabel();
        jLabel_korisnickoImeZaposlenog = new javax.swing.JLabel();
        jLabel_passZaposlenog = new javax.swing.JLabel();
        jLabel_imeZaposlenog = new javax.swing.JLabel();
        jLabel_prezimeZaposlenog = new javax.swing.JLabel();
        jLabel_emailZaposlenog = new javax.swing.JLabel();
        jLabel_telPosaoZaposlenog = new javax.swing.JLabel();
        jLabel_mobTelZaposlenog = new javax.swing.JLabel();
        jLabel_ulogeLogin = new javax.swing.JLabel();
        jLabel_opisZaposlenog = new javax.swing.JLabel();
        txt_IDUsers = new javax.swing.JTextField();
        Button_insertUsers = new javax.swing.JButton();
        Button_clearUsers = new javax.swing.JButton();
        Button_updateUsers = new javax.swing.JButton();
        Button_deleteUsers = new javax.swing.JButton();
        Button_refreshKorisnici = new javax.swing.JButton();
        Button_stampaKorisnici = new javax.swing.JButton();
        Panel_AdminUloge = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Table_UlogezaLogin = new javax.swing.JTable();
        Panel_AdministracijaUlogezaLogin = new javax.swing.JPanel();
        Button_clearUloge = new javax.swing.JButton();
        Button_deleteUloge = new javax.swing.JButton();
        Button_stampaUloge = new javax.swing.JButton();
        Button_updateUloge = new javax.swing.JButton();
        Button_refreshUloge = new javax.swing.JButton();
        TextField_idUloge = new javax.swing.JTextField();
        TextField_nazivUloge = new javax.swing.JTextField();
        Label_ulogaid = new javax.swing.JLabel();
        Label_nazivUloge = new javax.swing.JLabel();
        LabelulogeOpis = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TextArea_opisUloge = new javax.swing.JTextArea();
        Panel_AdminKontrolaPristupa = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        Table_KontrolaPristupa = new javax.swing.JTable();
        jButton_izvestajLogin = new javax.swing.JButton();
        jPanel_ObnovaLozinke = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable_ObnovaLozinke = new javax.swing.JTable();
        jButton_obnovaLozinke = new javax.swing.JButton();
        Panel_vestiObavestenja = new javax.swing.JPanel();
        ScrollPane_OglasnaTabla = new javax.swing.JScrollPane();
        Table_OglasnaTabla = new javax.swing.JTable();
        Panel_AdministracijaOglasneTable = new javax.swing.JPanel();
        Button_clearOT = new javax.swing.JButton();
        Button_deleteOT = new javax.swing.JButton();
        Button_insertOT = new javax.swing.JButton();
        Button_updateOT = new javax.swing.JButton();
        TextField_idOT = new javax.swing.JTextField();
        TextField_nazivOT = new javax.swing.JTextField();
        Label_OTid = new javax.swing.JLabel();
        Label_nazivOT = new javax.swing.JLabel();
        LabelOpisOT = new javax.swing.JLabel();
        ScrollPane_OpisOT = new javax.swing.JScrollPane();
        TextArea_opisOT = new javax.swing.JTextArea();
        Button_stampaOglasnaTabla = new javax.swing.JButton();
        Button_refreshOglasnaTabla = new javax.swing.JButton();
        Panel_Vozila = new javax.swing.JPanel();
        jTabbedPaneVozila = new javax.swing.JTabbedPane();
        jPanelKretanjaVozila = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        Table_DnevnoKretanjeVozila = new javax.swing.JTable();
        Panel_PretragaDnevnoKretanjeVozila = new javax.swing.JPanel();
        txt_pretragaDnevnoKretanjeVozila = new javax.swing.JTextField();
        Panel_AdminDnevnoKretanjeVozila = new javax.swing.JPanel();
        txt_PocetnaKilometrazaDnevnoKretanjeVozila = new javax.swing.JTextField();
        txt_KrajKmDnevnoKretanjeVozila = new javax.swing.JTextField();
        txt_PotrosnjaDnevnoKretanjeVozila = new javax.swing.JTextField();
        txt_brNalogaDnevnogKretanjaVozila = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        txt_opisDnevnogKretanjaVozila = new javax.swing.JTextArea();
        jLabel_idDnevnoKretanjeVozila = new javax.swing.JLabel();
        jLabel_kojeVoziloDnevnoKretanjeVozila = new javax.swing.JLabel();
        jLabel_startKmDnevnoKretanjeVozila = new javax.swing.JLabel();
        jLabel_KrajKmDnevnoKretanjeVozila = new javax.swing.JLabel();
        jLabel_ZaposleniDnevnoKretanjeVozila = new javax.swing.JLabel();
        jLabel_PotrosnjaDnevnoKretanjeVozila = new javax.swing.JLabel();
        jLabel_DatumDnevnoKretanjeVozila = new javax.swing.JLabel();
        jLabel_OpisDnevnogKretanjaVozila = new javax.swing.JLabel();
        jLabel_brNalogaDnevnogKretanjaVozila = new javax.swing.JLabel();
        txt_IDDnevnoKretanjeVozila = new javax.swing.JTextField();
        jComboBox_VoziloDnevnoKretanjeVozila = new javax.swing.JComboBox();
        jComboBoxZaposleniiDnevnoKretanjeVozila = new javax.swing.JComboBox();
        DateChooser_DatumDnevnogKretanjaVozila = new com.toedter.calendar.JDateChooser();
        Button_insertDnevnoKretanjeVozila = new javax.swing.JButton();
        Button_clearDnevnoKretanjeVozila = new javax.swing.JButton();
        Button_updateDnevnoKretanjeVozila = new javax.swing.JButton();
        Button_deleteDnevnoKretanjeVozila = new javax.swing.JButton();
        Button_refreshDnevnoKretanjeVozila = new javax.swing.JButton();
        Button_stampaDnevnoKretanjeVozila = new javax.swing.JButton();
        jPanelServisVozila = new javax.swing.JPanel();
        jScrollPaneServisVozila = new javax.swing.JScrollPane();
        Table_ServisVozila = new javax.swing.JTable();
        Panel_AdminServisVozila = new javax.swing.JPanel();
        Label_IDServisVozila = new javax.swing.JLabel();
        Label_DatumPredajeVozilaServis = new javax.swing.JLabel();
        Label_OpisServisVozila = new javax.swing.JLabel();
        Label_KilometrazaVozilaServis = new javax.swing.JLabel();
        Label_ID_VozilaServis = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        txt_OpisServisVozila = new javax.swing.JTextArea();
        ComboBox_ServisVozila = new javax.swing.JComboBox();
        txt_KilometrazaVozilaServis = new javax.swing.JTextField();
        txt_idServisVozila = new javax.swing.JTextField();
        jLabel_ZaposleniServisVozila = new javax.swing.JLabel();
        jComboBox_ZaposleniServisVozila = new javax.swing.JComboBox();
        DateChooserDatumVozilaServis = new com.toedter.calendar.JDateChooser();
        Panel_PretragaServisaVozila = new javax.swing.JPanel();
        txt_pretragaServisVozila = new javax.swing.JTextField();
        Panel_FuncButtonServisVozila = new javax.swing.JPanel();
        Button_clearServisVozila = new javax.swing.JButton();
        Button_deleteServisVozila = new javax.swing.JButton();
        Button_insertServisVozila = new javax.swing.JButton();
        Button_updateServisVozila = new javax.swing.JButton();
        Button_stampaServisVozila = new javax.swing.JButton();
        Button_refreshServisVozila = new javax.swing.JButton();
        jPanelPodaciVozila = new javax.swing.JPanel();
        jScrollPaneVozila = new javax.swing.JScrollPane();
        Table_Vozila = new javax.swing.JTable();
        Panel_AdminVozila = new javax.swing.JPanel();
        Label_IDVozila = new javax.swing.JLabel();
        Label_MarkaVozila = new javax.swing.JLabel();
        Label_OpisVozila = new javax.swing.JLabel();
        Label_TipVozila = new javax.swing.JLabel();
        Label_ID_KategorijeVozila = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        txt_OpisVozila = new javax.swing.JTextArea();
        ComboBox_KategorijaVozila = new javax.swing.JComboBox();
        txt_RegVozila = new javax.swing.JTextField();
        txt_MarkaVozila = new javax.swing.JTextField();
        txt_TipVozila = new javax.swing.JTextField();
        txt_idVozila = new javax.swing.JTextField();
        txt_PotrosnjsVozila = new javax.swing.JTextField();
        jLabel_PotrosnjaVozila = new javax.swing.JLabel();
        jLabel_RegVozila = new javax.swing.JLabel();
        Panel_PretragaVozila = new javax.swing.JPanel();
        txt_pretragaVozila = new javax.swing.JTextField();
        Panel_FuncButtonVozila = new javax.swing.JPanel();
        Button_clearVozila = new javax.swing.JButton();
        Button_deleteVozila = new javax.swing.JButton();
        Button_insertVozila = new javax.swing.JButton();
        Button_updateVozila = new javax.swing.JButton();
        Button_stampaVozila = new javax.swing.JButton();
        Button_refreshVozila = new javax.swing.JButton();
        jPanelKatVozila = new javax.swing.JPanel();
        ScrollPane_OglasnaTabla1 = new javax.swing.JScrollPane();
        Table_KatVozila = new javax.swing.JTable();
        Panel_AdministracijaKatVozila = new javax.swing.JPanel();
        Button_clearKatVozila = new javax.swing.JButton();
        Button_deleteKatVozila = new javax.swing.JButton();
        Button_insertKatVozila = new javax.swing.JButton();
        Button_updateKatVozila = new javax.swing.JButton();
        TextField_idKatVozila = new javax.swing.JTextField();
        TextField_nazivKatVozila = new javax.swing.JTextField();
        Label_idKatVozila = new javax.swing.JLabel();
        Label_nazivKatVozila = new javax.swing.JLabel();
        LabelOpisKatVozila = new javax.swing.JLabel();
        ScrollPane_OpisOT1 = new javax.swing.JScrollPane();
        TextArea_opisKatVozila = new javax.swing.JTextArea();
        Button_stampaKatVozila = new javax.swing.JButton();
        Button_refreshKatVozila = new javax.swing.JButton();
        MenuBar_adminMeni = new javax.swing.JMenuBar();
        Menu_opste = new javax.swing.JMenu();
        MenuItem_internet = new javax.swing.JMenuItem();
        MenuItem_baza = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        MenuItem_LogOut = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        MenuItem_Exit = new javax.swing.JMenuItem();
        Menu_help = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administracija Podataka");
        setMinimumSize(new java.awt.Dimension(775, 720));

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

        TabbedPane_Administracija.setToolTipText("");
        TabbedPane_Administracija.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        TabbedPane_Doc.setToolTipText("");
        TabbedPane_Doc.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        Table_document.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_document.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_documentMouseClicked(evt);
            }
        });
        Table_document.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table_documentKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Table_document);

        Panel_AdminDoc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upravljanje Dokumentima", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 153, 255))); // NOI18N

        Label_ID.setText("ID");

        Label_Broj.setText("Broj");

        Label_Naziv.setText("Naziv");

        Label_Opis.setText("Opis");

        Label_Dokument.setText("Dokument");

        Label_ID_Kategorije.setText("Kategorija");

        txt_Opis.setColumns(20);
        txt_Opis.setRows(5);
        txt_Opis.setToolTipText("Kratak opis dokumenta");
        jScrollPane3.setViewportView(txt_Opis);

        ComboBox_KategorijaDokumenta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Kategoriju Dokumenta" }));
        ComboBox_KategorijaDokumenta.setToolTipText("Prikaz postojećih kategorija dokumenata");

        txt_ID.setToolTipText("Indetifikacioni broj dokumenta");

        txt_Broj.setToolTipText("Oznaka i Broj Dokumenta");

        txt_Naziv.setToolTipText("Naziv Dokumenta");

        txt_Dokument.setToolTipText("Link do dokumenta");

        Button_unosDokumenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/browsicon.png"))); // NOI18N
        Button_unosDokumenta.setToolTipText("Unos dokumenta u bazu");
        Button_unosDokumenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_unosDokumentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_AdminDocLayout = new javax.swing.GroupLayout(Panel_AdminDoc);
        Panel_AdminDoc.setLayout(Panel_AdminDocLayout);
        Panel_AdminDocLayout.setHorizontalGroup(
            Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminDocLayout.createSequentialGroup()
                .addGroup(Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Label_ID)
                    .addComponent(Label_Broj)
                    .addComponent(Label_Naziv)
                    .addComponent(Label_ID_Kategorije)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Label_Opis)
                        .addComponent(Label_Dokument)))
                .addGap(51, 51, 51)
                .addGroup(Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Naziv)
                    .addComponent(txt_Broj)
                    .addComponent(jScrollPane3)
                    .addComponent(txt_ID, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ComboBox_KategorijaDokumenta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Panel_AdminDocLayout.createSequentialGroup()
                        .addComponent(txt_Dokument)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Button_unosDokumenta, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        Panel_AdminDocLayout.setVerticalGroup(
            Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminDocLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_ID)
                    .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_Broj)
                    .addComponent(txt_Broj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_Naziv)
                    .addComponent(txt_Naziv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox_KategorijaDokumenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_ID_Kategorije))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Dokument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_unosDokumenta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_Dokument))
                .addGap(5, 5, 5)
                .addGroup(Panel_AdminDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_AdminDocLayout.createSequentialGroup()
                        .addComponent(Label_Opis)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap())
        );

        Panel_Pretraga.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pretraga Dokumenata ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(255, 102, 102))); // NOI18N

        txt_pretraga.setToolTipText("Pretraga Dokumenata po Identifikacionom Broju, Broju Dokumenta, Nazivu Dokumenta");
        txt_pretraga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_pretragaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout Panel_PretragaLayout = new javax.swing.GroupLayout(Panel_Pretraga);
        Panel_Pretraga.setLayout(Panel_PretragaLayout);
        Panel_PretragaLayout.setHorizontalGroup(
            Panel_PretragaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_PretragaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretraga, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );
        Panel_PretragaLayout.setVerticalGroup(
            Panel_PretragaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_PretragaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretraga))
        );

        Panel_FuncButton.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Funkcionalna Dugmad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 102, 255))); // NOI18N

        Button_clear.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clear.setText("Očisti");
        Button_clear.setToolTipText("Očisti polja za unos podataka");
        Button_clear.setFocusable(false);
        Button_clear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearActionPerformed(evt);
            }
        });

        Button_delete.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_delete.setText("Obriši");
        Button_delete.setToolTipText("Obriši izabrane podatke iz baze");
        Button_delete.setFocusable(false);
        Button_delete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_delete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteActionPerformed(evt);
            }
        });

        Button_insert.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insert.setText("Unos");
        Button_insert.setToolTipText("Unos novih podataka u bazu");
        Button_insert.setFocusable(false);
        Button_insert.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insert.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertActionPerformed(evt);
            }
        });

        Button_update.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_update.setText("Ažuriranje");
        Button_update.setToolTipText("Ažuriranje izabranih podataka");
        Button_update.setFocusable(false);
        Button_update.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_update.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateActionPerformed(evt);
            }
        });

        Button_stampaDoc.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaDoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaDoc.setText("Štampa");
        Button_stampaDoc.setToolTipText("Štampa podataka iz tabele");
        Button_stampaDoc.setFocusable(false);
        Button_stampaDoc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaDoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaDocActionPerformed(evt);
            }
        });

        Button_refreshDoc.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshDoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshDoc.setText("Osveži");
        Button_refreshDoc.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshDoc.setFocusable(false);
        Button_refreshDoc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshDoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshDocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_FuncButtonLayout = new javax.swing.GroupLayout(Panel_FuncButton);
        Panel_FuncButton.setLayout(Panel_FuncButtonLayout);
        Panel_FuncButtonLayout.setHorizontalGroup(
            Panel_FuncButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_FuncButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_FuncButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_FuncButtonLayout.createSequentialGroup()
                        .addGroup(Panel_FuncButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Button_insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Button_update, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(Panel_FuncButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Button_clear, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(Button_delete, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(Button_stampaDoc, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_FuncButtonLayout.createSequentialGroup()
                        .addComponent(Button_refreshDoc, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                        .addGap(128, 128, 128))))
        );
        Panel_FuncButtonLayout.setVerticalGroup(
            Panel_FuncButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_FuncButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_FuncButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_insert, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_FuncButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_delete, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                    .addComponent(Button_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_FuncButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_stampaDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_refreshDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Panel_upravljanjeDokumentimaLayout = new javax.swing.GroupLayout(Panel_upravljanjeDokumentima);
        Panel_upravljanjeDokumentima.setLayout(Panel_upravljanjeDokumentimaLayout);
        Panel_upravljanjeDokumentimaLayout.setHorizontalGroup(
            Panel_upravljanjeDokumentimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_upravljanjeDokumentimaLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(Panel_upravljanjeDokumentimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_upravljanjeDokumentimaLayout.createSequentialGroup()
                        .addComponent(Panel_AdminDoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_upravljanjeDokumentimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Panel_FuncButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Panel_Pretraga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)))
        );
        Panel_upravljanjeDokumentimaLayout.setVerticalGroup(
            Panel_upravljanjeDokumentimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_upravljanjeDokumentimaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addGroup(Panel_upravljanjeDokumentimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Panel_upravljanjeDokumentimaLayout.createSequentialGroup()
                        .addComponent(Panel_Pretraga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Panel_FuncButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel_upravljanjeDokumentimaLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(Panel_AdminDoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout Panel_AdministracijaDocLayout = new javax.swing.GroupLayout(Panel_AdministracijaDoc);
        Panel_AdministracijaDoc.setLayout(Panel_AdministracijaDocLayout);
        Panel_AdministracijaDocLayout.setHorizontalGroup(
            Panel_AdministracijaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdministracijaDocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Panel_upravljanjeDokumentima, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Panel_AdministracijaDocLayout.setVerticalGroup(
            Panel_AdministracijaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_upravljanjeDokumentima, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        TabbedPane_Doc.addTab("Upravljanje Dokumentima", new javax.swing.ImageIcon(getClass().getResource("/Grafika/folder-documents-icon.png")), Panel_AdministracijaDoc); // NOI18N

        Table_KatDocument.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_KatDocument.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_KatDocumentMouseClicked(evt);
            }
        });
        Table_KatDocument.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table_KatDocumentKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(Table_KatDocument);

        Panel_AdminDocKat.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upravljanje Kategorijama Dokumenata", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 153, 255))); // NOI18N

        Label_IDKat.setText("ID");

        Label_NazivKat.setText("Naziv");

        Label_OpisKat.setText("Opis");

        txt_OpisKat.setColumns(20);
        txt_OpisKat.setRows(5);
        txt_OpisKat.setToolTipText("Kratak Opis Kategorije Dokumenta");
        jScrollPane4.setViewportView(txt_OpisKat);

        txt_IDKat.setToolTipText("Indetifikacioni broj Kategorije Dokumenta");

        txt_NazivKat.setToolTipText("Naziv Kategorije Dokumenta");

        javax.swing.GroupLayout Panel_AdminDocKatLayout = new javax.swing.GroupLayout(Panel_AdminDocKat);
        Panel_AdminDocKat.setLayout(Panel_AdminDocKatLayout);
        Panel_AdminDocKatLayout.setHorizontalGroup(
            Panel_AdminDocKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminDocKatLayout.createSequentialGroup()
                .addGroup(Panel_AdminDocKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Label_OpisKat)
                    .addComponent(Label_IDKat)
                    .addComponent(Label_NazivKat))
                .addGap(41, 41, 41)
                .addGroup(Panel_AdminDocKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_NazivKat)
                    .addComponent(txt_IDKat)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
                .addContainerGap())
        );
        Panel_AdminDocKatLayout.setVerticalGroup(
            Panel_AdminDocKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminDocKatLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_AdminDocKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_IDKat)
                    .addComponent(txt_IDKat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDocKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_NazivKat)
                    .addComponent(txt_NazivKat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDocKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Label_OpisKat)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        Panel_FuncButtonKat.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Funkcionalna Dugmad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 102, 255))); // NOI18N

        Button_clearKat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearKat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearKat.setText("Očisti");
        Button_clearKat.setToolTipText("Očisti polja za unos podataka");
        Button_clearKat.setFocusable(false);
        Button_clearKat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearKat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearKat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearKatActionPerformed(evt);
            }
        });

        Button_deleteKat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteKat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteKat.setText("Obriši");
        Button_deleteKat.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteKat.setFocusable(false);
        Button_deleteKat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteKat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteKat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteKatActionPerformed(evt);
            }
        });

        Button_insertKat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertKat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertKat.setText("Unos");
        Button_insertKat.setToolTipText("Unos novih podataka u bazu");
        Button_insertKat.setFocusable(false);
        Button_insertKat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertKat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertKat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertKatActionPerformed(evt);
            }
        });

        Button_updateKat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateKat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateKat.setText("Ažuriranje");
        Button_updateKat.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateKat.setFocusable(false);
        Button_updateKat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateKat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateKat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateKatActionPerformed(evt);
            }
        });

        Button_refreshKat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshKat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshKat.setText("Osveži");
        Button_refreshKat.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshKat.setFocusable(false);
        Button_refreshKat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshKat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshKat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshKatActionPerformed(evt);
            }
        });

        Button_stampaKat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaKat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaKat.setText("Štampa");
        Button_stampaKat.setToolTipText("Štampa podataka iz tabele");
        Button_stampaKat.setFocusable(false);
        Button_stampaKat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaKat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaKat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaKatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_FuncButtonKatLayout = new javax.swing.GroupLayout(Panel_FuncButtonKat);
        Panel_FuncButtonKat.setLayout(Panel_FuncButtonKatLayout);
        Panel_FuncButtonKatLayout.setHorizontalGroup(
            Panel_FuncButtonKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_FuncButtonKatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_FuncButtonKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_refreshKat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_insertKat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_updateKat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_FuncButtonKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_FuncButtonKatLayout.createSequentialGroup()
                        .addGroup(Panel_FuncButtonKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_clearKat, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_deleteKat, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(Button_stampaKat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        Panel_FuncButtonKatLayout.setVerticalGroup(
            Panel_FuncButtonKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_FuncButtonKatLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(Panel_FuncButtonKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_insertKat, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_clearKat, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_FuncButtonKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_updateKat, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                    .addComponent(Button_deleteKat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_FuncButtonKatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_stampaKat, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_refreshKat, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout Panel_AdminKategorijaDocLayout = new javax.swing.GroupLayout(Panel_AdminKategorijaDoc);
        Panel_AdminKategorijaDoc.setLayout(Panel_AdminKategorijaDocLayout);
        Panel_AdminKategorijaDocLayout.setHorizontalGroup(
            Panel_AdminKategorijaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdminKategorijaDocLayout.createSequentialGroup()
                .addComponent(Panel_AdminDocKat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_FuncButtonKat, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdminKategorijaDocLayout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        Panel_AdminKategorijaDocLayout.setVerticalGroup(
            Panel_AdminKategorijaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminKategorijaDocLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminKategorijaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Panel_FuncButtonKat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Panel_AdminDocKat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        TabbedPane_Doc.addTab("Upravljanje Kategorijama", new javax.swing.ImageIcon(getClass().getResource("/Grafika/archive-icon.png")), Panel_AdminKategorijaDoc); // NOI18N

        TabbedPane_Administracija.addTab("Dokumenta", new javax.swing.ImageIcon(getClass().getResource("/Grafika/Document-Copy-icon.png")), TabbedPane_Doc); // NOI18N

        TabbedPane_Korisnici.setBackground(new java.awt.Color(0, 255, 255));
        TabbedPane_Korisnici.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        Table_Users.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_Users.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_UsersMouseClicked(evt);
            }
        });
        Table_Users.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table_UsersKeyReleased(evt);
            }
        });
        jScrollPane7.setViewportView(Table_Users);

        Panel_PretragaKorisnika.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pretraga ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(255, 102, 102))); // NOI18N

        txt_pretragaUsers.setToolTipText("Pretraga Korisnika po Email-u, Imenu, Prezimenu");
        txt_pretragaUsers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_pretragaUsersKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout Panel_PretragaKorisnikaLayout = new javax.swing.GroupLayout(Panel_PretragaKorisnika);
        Panel_PretragaKorisnika.setLayout(Panel_PretragaKorisnikaLayout);
        Panel_PretragaKorisnikaLayout.setHorizontalGroup(
            Panel_PretragaKorisnikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_PretragaKorisnikaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretragaUsers)
                .addContainerGap())
        );
        Panel_PretragaKorisnikaLayout.setVerticalGroup(
            Panel_PretragaKorisnikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_PretragaKorisnikaLayout.createSequentialGroup()
                .addComponent(txt_pretragaUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );

        Panel_AdminUsers.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upravljanje Korisnicima", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 153, 255))); // NOI18N
        Panel_AdminUsers.setPreferredSize(new java.awt.Dimension(210, 250));

        txt_UserName.setToolTipText("Korisničko Ime Zaposlenog");

        txt_Pass.setToolTipText("Lozinka Zaposlenog");

        txt_Name.setToolTipText("Ime Zaposlenog");

        txt_LastName.setToolTipText("Prezime Zaposlenog");

        txt_JobPhon.setToolTipText("Telefon na Poslu");

        txt_EmailUser.setToolTipText("Email Zaposlenog");

        txt_TelMob.setToolTipText("Mobilni Telefon Zaposlenog");

        ComboBox_Users.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Ulogu za Login" }));
        ComboBox_Users.setToolTipText("Izaberite koju Ulogu za Ulaz na sistem želite da dodelite Zaposlenom");

        txt_opisUser.setColumns(20);
        txt_opisUser.setRows(5);
        txt_opisUser.setToolTipText("Kratak Opis Zaposlenog");
        jScrollPane8.setViewportView(txt_opisUser);

        jLabel_idZaposlenog.setText("ID");

        jLabel_korisnickoImeZaposlenog.setText("Korisničko Ime");
        jLabel_korisnickoImeZaposlenog.setToolTipText("");

        jLabel_passZaposlenog.setText("Lozinka");

        jLabel_imeZaposlenog.setText("Ime");

        jLabel_prezimeZaposlenog.setText("Prezime");

        jLabel_emailZaposlenog.setText("Email");

        jLabel_telPosaoZaposlenog.setText("Tel. na Poslu");

        jLabel_mobTelZaposlenog.setText("Mobilni Tel.");

        jLabel_ulogeLogin.setText("Login Uloge");

        jLabel_opisZaposlenog.setText("Opis");

        txt_IDUsers.setToolTipText("Korisničko Ime Zaposlenog");

        javax.swing.GroupLayout Panel_AdminUsersLayout = new javax.swing.GroupLayout(Panel_AdminUsers);
        Panel_AdminUsers.setLayout(Panel_AdminUsersLayout);
        Panel_AdminUsersLayout.setHorizontalGroup(
            Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdminUsersLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_idZaposlenog)
                    .addComponent(jLabel_korisnickoImeZaposlenog)
                    .addComponent(jLabel_passZaposlenog)
                    .addComponent(jLabel_imeZaposlenog)
                    .addComponent(jLabel_prezimeZaposlenog)
                    .addComponent(jLabel_emailZaposlenog)
                    .addComponent(jLabel_telPosaoZaposlenog)
                    .addComponent(jLabel_mobTelZaposlenog)
                    .addComponent(jLabel_ulogeLogin)
                    .addComponent(jLabel_opisZaposlenog))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_UserName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Pass, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Name, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_LastName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_EmailUser, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_JobPhon, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_TelMob, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(ComboBox_Users, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_IDUsers, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(213, 213, 213))
        );
        Panel_AdminUsersLayout.setVerticalGroup(
            Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminUsersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_idZaposlenog)
                    .addComponent(txt_IDUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_korisnickoImeZaposlenog))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_passZaposlenog)
                    .addComponent(txt_Pass, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_imeZaposlenog))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_LastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_prezimeZaposlenog))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_EmailUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_emailZaposlenog))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_JobPhon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_telPosaoZaposlenog))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TelMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_mobTelZaposlenog))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox_Users, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_ulogeLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_opisZaposlenog)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(165, 165, 165))
        );

        Button_insertUsers.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertUsers.setToolTipText("Unos novih podataka u bazu");
        Button_insertUsers.setFocusable(false);
        Button_insertUsers.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertUsers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertUsersActionPerformed(evt);
            }
        });

        Button_clearUsers.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearUsers.setToolTipText("Očisti polja za unos podataka");
        Button_clearUsers.setFocusable(false);
        Button_clearUsers.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearUsers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearUsersActionPerformed(evt);
            }
        });

        Button_updateUsers.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateUsers.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateUsers.setFocusable(false);
        Button_updateUsers.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateUsers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateUsersActionPerformed(evt);
            }
        });

        Button_deleteUsers.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteUsers.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteUsers.setFocusable(false);
        Button_deleteUsers.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteUsers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteUsersActionPerformed(evt);
            }
        });

        Button_refreshKorisnici.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshKorisnici.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshKorisnici.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshKorisnici.setFocusable(false);
        Button_refreshKorisnici.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshKorisnici.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshKorisnici.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshKorisniciActionPerformed(evt);
            }
        });

        Button_stampaKorisnici.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaKorisnici.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaKorisnici.setToolTipText("Štampa podataka iz tabele");
        Button_stampaKorisnici.setFocusable(false);
        Button_stampaKorisnici.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaKorisnici.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaKorisnici.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaKorisniciActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_AdminKorisniciLayout = new javax.swing.GroupLayout(Panel_AdminKorisnici);
        Panel_AdminKorisnici.setLayout(Panel_AdminKorisniciLayout);
        Panel_AdminKorisniciLayout.setHorizontalGroup(
            Panel_AdminKorisniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminKorisniciLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(Panel_AdminUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminKorisniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Panel_AdminKorisniciLayout.createSequentialGroup()
                        .addComponent(Panel_PretragaKorisnika, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_clearUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_insertUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_updateUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_deleteUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_refreshKorisnici, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_stampaKorisnici, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE))
                .addContainerGap())
        );
        Panel_AdminKorisniciLayout.setVerticalGroup(
            Panel_AdminKorisniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminKorisniciLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AdminKorisniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Panel_AdminUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 508, Short.MAX_VALUE)
                    .addGroup(Panel_AdminKorisniciLayout.createSequentialGroup()
                        .addGroup(Panel_AdminKorisniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Panel_PretragaKorisnika, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdminKorisniciLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(Panel_AdminKorisniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Button_clearUsers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                    .addComponent(Button_insertUsers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Button_deleteUsers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Button_refreshKorisnici, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Button_stampaKorisnici, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Button_updateUsers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)))
                .addContainerGap())
        );

        TabbedPane_Korisnici.addTab("Korisnici", new javax.swing.ImageIcon(getClass().getResource("/Grafika/korisnici.png")), Panel_AdminKorisnici); // NOI18N

        Table_UlogezaLogin.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_UlogezaLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_UlogezaLoginMouseClicked(evt);
            }
        });
        Table_UlogezaLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table_UlogezaLoginKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(Table_UlogezaLogin);

        Panel_AdministracijaUlogezaLogin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upravljanje Ulogama za Login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 102, 255))); // NOI18N

        Button_clearUloge.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearUloge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearUloge.setText("Očisti");
        Button_clearUloge.setToolTipText("Očisti polja za unos podataka");
        Button_clearUloge.setFocusable(false);
        Button_clearUloge.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearUloge.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearUloge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearUlogeActionPerformed(evt);
            }
        });

        Button_deleteUloge.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteUloge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteUloge.setText("Obriši");
        Button_deleteUloge.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteUloge.setFocusable(false);
        Button_deleteUloge.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteUloge.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteUloge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteUlogeActionPerformed(evt);
            }
        });

        Button_stampaUloge.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaUloge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaUloge.setText("Štampa");
        Button_stampaUloge.setToolTipText("Štampa podataka iz tabele");
        Button_stampaUloge.setFocusable(false);
        Button_stampaUloge.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaUloge.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaUloge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaUlogeActionPerformed(evt);
            }
        });

        Button_updateUloge.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateUloge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateUloge.setText("Ažuriranje");
        Button_updateUloge.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateUloge.setFocusable(false);
        Button_updateUloge.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateUloge.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateUloge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateUlogeActionPerformed(evt);
            }
        });

        Button_refreshUloge.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshUloge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshUloge.setText("Osveži");
        Button_refreshUloge.setToolTipText("Učitavanja svih podataka u tabeli ponovo");
        Button_refreshUloge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshUlogeActionPerformed(evt);
            }
        });

        TextField_idUloge.setToolTipText("Indetifikacioni broj Uloge");

        TextField_nazivUloge.setToolTipText("Naziv Uloge");

        Label_ulogaid.setText("Id");

        Label_nazivUloge.setText("Naziv");

        LabelulogeOpis.setText("Opis");

        TextArea_opisUloge.setColumns(20);
        TextArea_opisUloge.setRows(5);
        TextArea_opisUloge.setToolTipText("Kratki Opis Uloge ");
        jScrollPane6.setViewportView(TextArea_opisUloge);

        javax.swing.GroupLayout Panel_AdministracijaUlogezaLoginLayout = new javax.swing.GroupLayout(Panel_AdministracijaUlogezaLogin);
        Panel_AdministracijaUlogezaLogin.setLayout(Panel_AdministracijaUlogezaLoginLayout);
        Panel_AdministracijaUlogezaLoginLayout.setHorizontalGroup(
            Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdministracijaUlogezaLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Label_nazivUloge)
                    .addComponent(Label_ulogaid)
                    .addComponent(LabelulogeOpis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addComponent(TextField_idUloge, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                    .addComponent(TextField_nazivUloge))
                .addGap(10, 10, 10)
                .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_refreshUloge, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Panel_AdministracijaUlogezaLoginLayout.createSequentialGroup()
                        .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Button_stampaUloge, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Button_updateUloge, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_clearUloge, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_deleteUloge, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        Panel_AdministracijaUlogezaLoginLayout.setVerticalGroup(
            Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdministracijaUlogezaLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_AdministracijaUlogezaLoginLayout.createSequentialGroup()
                        .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_stampaUloge, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_clearUloge, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Button_updateUloge, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(Button_deleteUloge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_refreshUloge, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(Panel_AdministracijaUlogezaLoginLayout.createSequentialGroup()
                        .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Label_ulogaid)
                            .addComponent(TextField_idUloge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextField_nazivUloge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label_nazivUloge))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_AdministracijaUlogezaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelulogeOpis)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                        .addGap(8, 8, 8))))
        );

        javax.swing.GroupLayout Panel_AdminUlogeLayout = new javax.swing.GroupLayout(Panel_AdminUloge);
        Panel_AdminUloge.setLayout(Panel_AdminUlogeLayout);
        Panel_AdminUlogeLayout.setHorizontalGroup(
            Panel_AdminUlogeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdminUlogeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Panel_AdministracijaUlogezaLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Panel_AdminUlogeLayout.setVerticalGroup(
            Panel_AdminUlogeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminUlogeLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_AdministracijaUlogezaLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        TabbedPane_Korisnici.addTab("Uloge Korisnika", new javax.swing.ImageIcon(getClass().getResource("/Grafika/UserGroup.png")), Panel_AdminUloge); // NOI18N

        Table_KontrolaPristupa.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(Table_KontrolaPristupa);

        jButton_izvestajLogin.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton_izvestajLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/prava_pristupa.png"))); // NOI18N
        jButton_izvestajLogin.setText("Izveštaj o Ulazu");
        jButton_izvestajLogin.setToolTipText("Izveštaj o ulazu na sistem");
        jButton_izvestajLogin.setFocusable(false);
        jButton_izvestajLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_izvestajLogin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_izvestajLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_izvestajLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_AdminKontrolaPristupaLayout = new javax.swing.GroupLayout(Panel_AdminKontrolaPristupa);
        Panel_AdminKontrolaPristupa.setLayout(Panel_AdminKontrolaPristupaLayout);
        Panel_AdminKontrolaPristupaLayout.setHorizontalGroup(
            Panel_AdminKontrolaPristupaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdminKontrolaPristupaLayout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(Panel_AdminKontrolaPristupaLayout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(jButton_izvestajLogin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_AdminKontrolaPristupaLayout.setVerticalGroup(
            Panel_AdminKontrolaPristupaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdminKontrolaPristupaLayout.createSequentialGroup()
                .addComponent(jButton_izvestajLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        TabbedPane_Korisnici.addTab("Kontrola Pristupa", new javax.swing.ImageIcon(getClass().getResource("/Grafika/prava_pristupa.png")), Panel_AdminKontrolaPristupa); // NOI18N

        jTable_ObnovaLozinke.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(jTable_ObnovaLozinke);

        jButton_obnovaLozinke.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton_obnovaLozinke.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        jButton_obnovaLozinke.setText("Izveštaj Obnova Lozinke");
        jButton_obnovaLozinke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_obnovaLozinkeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_ObnovaLozinkeLayout = new javax.swing.GroupLayout(jPanel_ObnovaLozinke);
        jPanel_ObnovaLozinke.setLayout(jPanel_ObnovaLozinkeLayout);
        jPanel_ObnovaLozinkeLayout.setHorizontalGroup(
            jPanel_ObnovaLozinkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ObnovaLozinkeLayout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel_ObnovaLozinkeLayout.createSequentialGroup()
                .addGap(260, 260, 260)
                .addComponent(jButton_obnovaLozinke)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_ObnovaLozinkeLayout.setVerticalGroup(
            jPanel_ObnovaLozinkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ObnovaLozinkeLayout.createSequentialGroup()
                .addComponent(jButton_obnovaLozinke)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabbedPane_Korisnici.addTab("Obnova Lozinke", new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png")), jPanel_ObnovaLozinke); // NOI18N

        javax.swing.GroupLayout Panel_ZaposleniLayout = new javax.swing.GroupLayout(Panel_Zaposleni);
        Panel_Zaposleni.setLayout(Panel_ZaposleniLayout);
        Panel_ZaposleniLayout.setHorizontalGroup(
            Panel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPane_Korisnici)
        );
        Panel_ZaposleniLayout.setVerticalGroup(
            Panel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ZaposleniLayout.createSequentialGroup()
                .addComponent(TabbedPane_Korisnici)
                .addContainerGap())
        );

        TabbedPane_Administracija.addTab("Zaposleni", new javax.swing.ImageIcon(getClass().getResource("/Grafika/Admin-icon.png")), Panel_Zaposleni); // NOI18N

        Table_OglasnaTabla.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_OglasnaTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_OglasnaTablaMouseClicked(evt);
            }
        });
        Table_OglasnaTabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table_OglasnaTablaKeyReleased(evt);
            }
        });
        ScrollPane_OglasnaTabla.setViewportView(Table_OglasnaTabla);

        Panel_AdministracijaOglasneTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upravljanje Vestima i Obaveštenjima", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 102, 255))); // NOI18N

        Button_clearOT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearOT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearOT.setText("Očisti");
        Button_clearOT.setToolTipText("Očisti polja za unos podataka");
        Button_clearOT.setFocusable(false);
        Button_clearOT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearOT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearOT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearOTActionPerformed(evt);
            }
        });

        Button_deleteOT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteOT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteOT.setText("Obriši");
        Button_deleteOT.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteOT.setFocusable(false);
        Button_deleteOT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteOT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteOT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteOTActionPerformed(evt);
            }
        });

        Button_insertOT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertOT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertOT.setText("Unos");
        Button_insertOT.setToolTipText("Unos novih podataka u bazu");
        Button_insertOT.setFocusable(false);
        Button_insertOT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertOT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertOT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertOTActionPerformed(evt);
            }
        });

        Button_updateOT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateOT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateOT.setText("Ažuriranje");
        Button_updateOT.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateOT.setFocusable(false);
        Button_updateOT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateOT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateOT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateOTActionPerformed(evt);
            }
        });

        TextField_idOT.setToolTipText("Indetifikacioni broj Vesti");

        TextField_nazivOT.setToolTipText("Naziv Vesti");

        Label_OTid.setText("Id");

        Label_nazivOT.setText("Naziv");

        LabelOpisOT.setText("Opis");

        TextArea_opisOT.setColumns(20);
        TextArea_opisOT.setRows(5);
        TextArea_opisOT.setToolTipText("Kratki Opis Vesti");
        ScrollPane_OpisOT.setViewportView(TextArea_opisOT);

        Button_stampaOglasnaTabla.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaOglasnaTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaOglasnaTabla.setText("Štampa");
        Button_stampaOglasnaTabla.setToolTipText("Štampa podataka iz tabele");
        Button_stampaOglasnaTabla.setFocusable(false);
        Button_stampaOglasnaTabla.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaOglasnaTabla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaOglasnaTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaOglasnaTablaActionPerformed(evt);
            }
        });

        Button_refreshOglasnaTabla.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshOglasnaTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshOglasnaTabla.setText("Osveži");
        Button_refreshOglasnaTabla.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshOglasnaTabla.setFocusable(false);
        Button_refreshOglasnaTabla.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshOglasnaTabla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshOglasnaTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshOglasnaTablaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_AdministracijaOglasneTableLayout = new javax.swing.GroupLayout(Panel_AdministracijaOglasneTable);
        Panel_AdministracijaOglasneTable.setLayout(Panel_AdministracijaOglasneTableLayout);
        Panel_AdministracijaOglasneTableLayout.setHorizontalGroup(
            Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdministracijaOglasneTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Label_nazivOT)
                    .addComponent(Label_OTid)
                    .addComponent(LabelOpisOT))
                .addGap(9, 9, 9)
                .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane_OpisOT)
                    .addComponent(TextField_nazivOT)
                    .addComponent(TextField_idOT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_refreshOglasnaTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_insertOT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_updateOT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_clearOT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_deleteOT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_stampaOglasnaTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addContainerGap())
        );
        Panel_AdministracijaOglasneTableLayout.setVerticalGroup(
            Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdministracijaOglasneTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_AdministracijaOglasneTableLayout.createSequentialGroup()
                        .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Label_OTid)
                            .addComponent(TextField_idOT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextField_nazivOT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label_nazivOT))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_AdministracijaOglasneTableLayout.createSequentialGroup()
                                .addComponent(LabelOpisOT)
                                .addGap(35, 132, Short.MAX_VALUE))
                            .addComponent(ScrollPane_OpisOT)))
                    .addGroup(Panel_AdministracijaOglasneTableLayout.createSequentialGroup()
                        .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_insertOT, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_clearOT, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Button_updateOT, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(Button_deleteOT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Panel_AdministracijaOglasneTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Button_stampaOglasnaTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_refreshOglasnaTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        javax.swing.GroupLayout Panel_vestiObavestenjaLayout = new javax.swing.GroupLayout(Panel_vestiObavestenja);
        Panel_vestiObavestenja.setLayout(Panel_vestiObavestenjaLayout);
        Panel_vestiObavestenjaLayout.setHorizontalGroup(
            Panel_vestiObavestenjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ScrollPane_OglasnaTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
            .addComponent(Panel_AdministracijaOglasneTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Panel_vestiObavestenjaLayout.setVerticalGroup(
            Panel_vestiObavestenjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_vestiObavestenjaLayout.createSequentialGroup()
                .addComponent(ScrollPane_OglasnaTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_AdministracijaOglasneTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        TabbedPane_Administracija.addTab("Vesti i Obaveštenja", new javax.swing.ImageIcon(getClass().getResource("/Grafika/Newspapers-2-icon.png")), Panel_vestiObavestenja); // NOI18N

        Panel_Vozila.setToolTipText("");

        jTabbedPaneVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        Table_DnevnoKretanjeVozila.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_DnevnoKretanjeVozila.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_DnevnoKretanjeVozilaMouseClicked(evt);
            }
        });
        Table_DnevnoKretanjeVozila.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table_DnevnoKretanjeVozilaKeyReleased(evt);
            }
        });
        jScrollPane11.setViewportView(Table_DnevnoKretanjeVozila);

        Panel_PretragaDnevnoKretanjeVozila.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pretraga ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(255, 102, 102))); // NOI18N

        txt_pretragaDnevnoKretanjeVozila.setToolTipText("Pretraga Vozila Po Datumu");
        txt_pretragaDnevnoKretanjeVozila.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_pretragaDnevnoKretanjeVozilaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout Panel_PretragaDnevnoKretanjeVozilaLayout = new javax.swing.GroupLayout(Panel_PretragaDnevnoKretanjeVozila);
        Panel_PretragaDnevnoKretanjeVozila.setLayout(Panel_PretragaDnevnoKretanjeVozilaLayout);
        Panel_PretragaDnevnoKretanjeVozilaLayout.setHorizontalGroup(
            Panel_PretragaDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_PretragaDnevnoKretanjeVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretragaDnevnoKretanjeVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addContainerGap())
        );
        Panel_PretragaDnevnoKretanjeVozilaLayout.setVerticalGroup(
            Panel_PretragaDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_PretragaDnevnoKretanjeVozilaLayout.createSequentialGroup()
                .addComponent(txt_pretragaDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        Panel_AdminDnevnoKretanjeVozila.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dnevna Kretanja Vozila", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 153, 255))); // NOI18N
        Panel_AdminDnevnoKretanjeVozila.setPreferredSize(new java.awt.Dimension(210, 250));

        txt_PocetnaKilometrazaDnevnoKretanjeVozila.setToolTipText("Početna Kilometraža na Vozilu");

        txt_KrajKmDnevnoKretanjeVozila.setToolTipText("Kilometraža na kraju Kretanja Vozila");

        txt_PotrosnjaDnevnoKretanjeVozila.setToolTipText("Tačna potrošnja goriva u litrima");

        txt_brNalogaDnevnogKretanjaVozila.setToolTipText("Broj putnog naloga na osnovu koga vozilo sme da se kreće u saobraćaju ili interni broj ako se vozilo kreće po aerodromu");

        txt_opisDnevnogKretanjaVozila.setColumns(20);
        txt_opisDnevnogKretanjaVozila.setRows(5);
        txt_opisDnevnogKretanjaVozila.setToolTipText("Kratak Opis o Kretanju Vozila");
        jScrollPane14.setViewportView(txt_opisDnevnogKretanjaVozila);

        jLabel_idDnevnoKretanjeVozila.setText("ID");

        jLabel_kojeVoziloDnevnoKretanjeVozila.setText("Vozilo");
        jLabel_kojeVoziloDnevnoKretanjeVozila.setToolTipText("");

        jLabel_startKmDnevnoKretanjeVozila.setText("Start KM");

        jLabel_KrajKmDnevnoKretanjeVozila.setText("Kraj KM");

        jLabel_ZaposleniDnevnoKretanjeVozila.setText("Zaposleni");

        jLabel_PotrosnjaDnevnoKretanjeVozila.setText("Potrošnja/L");

        jLabel_DatumDnevnoKretanjeVozila.setText("Datum");

        jLabel_OpisDnevnogKretanjaVozila.setText("Opis");

        jLabel_brNalogaDnevnogKretanjaVozila.setText("Br Naloga");

        txt_IDDnevnoKretanjeVozila.setToolTipText("Identifikacioni broj Dnevnog Kretanja Vozila");

        jComboBox_VoziloDnevnoKretanjeVozila.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Vozilo" }));
        jComboBox_VoziloDnevnoKretanjeVozila.setToolTipText("Izaberite Kojim ste se Vozilom kretali");

        jComboBoxZaposleniiDnevnoKretanjeVozila.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ko je Vozio" }));
        jComboBoxZaposleniiDnevnoKretanjeVozila.setToolTipText("Ko je od Zaposlenih Vozio");

        DateChooser_DatumDnevnogKretanjaVozila.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout Panel_AdminDnevnoKretanjeVozilaLayout = new javax.swing.GroupLayout(Panel_AdminDnevnoKretanjeVozila);
        Panel_AdminDnevnoKretanjeVozila.setLayout(Panel_AdminDnevnoKretanjeVozilaLayout);
        Panel_AdminDnevnoKretanjeVozilaLayout.setHorizontalGroup(
            Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdminDnevnoKretanjeVozilaLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_idDnevnoKretanjeVozila)
                    .addComponent(jLabel_kojeVoziloDnevnoKretanjeVozila)
                    .addComponent(jLabel_startKmDnevnoKretanjeVozila)
                    .addComponent(jLabel_KrajKmDnevnoKretanjeVozila)
                    .addComponent(jLabel_ZaposleniDnevnoKretanjeVozila)
                    .addComponent(jLabel_PotrosnjaDnevnoKretanjeVozila)
                    .addComponent(jLabel_DatumDnevnoKretanjeVozila)
                    .addComponent(jLabel_OpisDnevnogKretanjaVozila)
                    .addComponent(jLabel_brNalogaDnevnogKretanjaVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_PocetnaKilometrazaDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_KrajKmDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_PotrosnjaDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_brNalogaDnevnogKretanjaVozila, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txt_IDDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_VoziloDnevnoKretanjeVozila, 0, 140, Short.MAX_VALUE)
                    .addComponent(jComboBoxZaposleniiDnevnoKretanjeVozila, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DateChooser_DatumDnevnogKretanjaVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(213, 213, 213))
        );
        Panel_AdminDnevnoKretanjeVozilaLayout.setVerticalGroup(
            Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_idDnevnoKretanjeVozila)
                    .addComponent(txt_IDDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_kojeVoziloDnevnoKretanjeVozila)
                    .addComponent(jComboBox_VoziloDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_startKmDnevnoKretanjeVozila)
                    .addComponent(txt_PocetnaKilometrazaDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_KrajKmDnevnoKretanjeVozila)
                    .addComponent(txt_KrajKmDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ZaposleniDnevnoKretanjeVozila)
                    .addComponent(jComboBoxZaposleniiDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_PotrosnjaDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_PotrosnjaDnevnoKretanjeVozila))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_DatumDnevnoKretanjeVozila)
                    .addComponent(DateChooser_DatumDnevnogKretanjaVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_OpisDnevnogKretanjaVozila)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminDnevnoKretanjeVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_brNalogaDnevnogKretanjaVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_brNalogaDnevnogKretanjaVozila))
                .addGap(182, 182, 182))
        );

        Button_insertDnevnoKretanjeVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertDnevnoKretanjeVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertDnevnoKretanjeVozila.setToolTipText("Unos novih podataka u bazu");
        Button_insertDnevnoKretanjeVozila.setFocusable(false);
        Button_insertDnevnoKretanjeVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertDnevnoKretanjeVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertDnevnoKretanjeVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertDnevnoKretanjeVozilaActionPerformed(evt);
            }
        });

        Button_clearDnevnoKretanjeVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearDnevnoKretanjeVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearDnevnoKretanjeVozila.setToolTipText("Očisti polja za unos podataka");
        Button_clearDnevnoKretanjeVozila.setFocusable(false);
        Button_clearDnevnoKretanjeVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearDnevnoKretanjeVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearDnevnoKretanjeVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearDnevnoKretanjeVozilaActionPerformed(evt);
            }
        });

        Button_updateDnevnoKretanjeVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateDnevnoKretanjeVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateDnevnoKretanjeVozila.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateDnevnoKretanjeVozila.setFocusable(false);
        Button_updateDnevnoKretanjeVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateDnevnoKretanjeVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateDnevnoKretanjeVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateDnevnoKretanjeVozilaActionPerformed(evt);
            }
        });

        Button_deleteDnevnoKretanjeVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteDnevnoKretanjeVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteDnevnoKretanjeVozila.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteDnevnoKretanjeVozila.setFocusable(false);
        Button_deleteDnevnoKretanjeVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteDnevnoKretanjeVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteDnevnoKretanjeVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteDnevnoKretanjeVozilaActionPerformed(evt);
            }
        });

        Button_refreshDnevnoKretanjeVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshDnevnoKretanjeVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshDnevnoKretanjeVozila.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshDnevnoKretanjeVozila.setFocusable(false);
        Button_refreshDnevnoKretanjeVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshDnevnoKretanjeVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshDnevnoKretanjeVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshDnevnoKretanjeVozilaActionPerformed(evt);
            }
        });

        Button_stampaDnevnoKretanjeVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaDnevnoKretanjeVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaDnevnoKretanjeVozila.setToolTipText("Štampa podataka iz tabele");
        Button_stampaDnevnoKretanjeVozila.setFocusable(false);
        Button_stampaDnevnoKretanjeVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaDnevnoKretanjeVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaDnevnoKretanjeVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaDnevnoKretanjeVozilaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelKretanjaVozilaLayout = new javax.swing.GroupLayout(jPanelKretanjaVozila);
        jPanelKretanjaVozila.setLayout(jPanelKretanjaVozilaLayout);
        jPanelKretanjaVozilaLayout.setHorizontalGroup(
            jPanelKretanjaVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKretanjaVozilaLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(Panel_AdminDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelKretanjaVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelKretanjaVozilaLayout.createSequentialGroup()
                        .addComponent(Panel_PretragaDnevnoKretanjeVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_clearDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_insertDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_updateDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_deleteDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_refreshDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_stampaDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelKretanjaVozilaLayout.setVerticalGroup(
            jPanelKretanjaVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKretanjaVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelKretanjaVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Panel_AdminDnevnoKretanjeVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 512, Short.MAX_VALUE)
                    .addGroup(jPanelKretanjaVozilaLayout.createSequentialGroup()
                        .addGroup(jPanelKretanjaVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Panel_PretragaDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelKretanjaVozilaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanelKretanjaVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Button_clearDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                    .addComponent(Button_insertDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Button_deleteDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Button_refreshDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Button_stampaDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Button_updateDnevnoKretanjeVozila, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPaneVozila.addTab("Dnevna Kretanja Vozila", new javax.swing.ImageIcon(getClass().getResource("/Grafika/attention-icon.png")), jPanelKretanjaVozila); // NOI18N

        Table_ServisVozila.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_ServisVozila.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_ServisVozilaMouseClicked(evt);
            }
        });
        Table_ServisVozila.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table_ServisVozilaKeyReleased(evt);
            }
        });
        jScrollPaneServisVozila.setViewportView(Table_ServisVozila);

        Panel_AdminServisVozila.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upravljanje Servisima na Vozilima", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 153, 255))); // NOI18N

        Label_IDServisVozila.setText("ID");

        Label_DatumPredajeVozilaServis.setText("Datum Predaje");

        Label_OpisServisVozila.setText("Opis");

        Label_KilometrazaVozilaServis.setText("Kilometraža");

        Label_ID_VozilaServis.setText("Vozilo");

        txt_OpisServisVozila.setColumns(20);
        txt_OpisServisVozila.setRows(5);
        txt_OpisServisVozila.setToolTipText("Kratak opis šta je rađeno u servisu na Vozilu i Naziv Servisa");
        jScrollPane13.setViewportView(txt_OpisServisVozila);

        ComboBox_ServisVozila.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Vozilo" }));
        ComboBox_ServisVozila.setToolTipText("Prikaz postojećih Vozila");

        txt_KilometrazaVozilaServis.setToolTipText("Tačna Kilometraža Vozila koje je predato u Servis");

        txt_idServisVozila.setToolTipText("Identifikacioni Broj Vozila na Servisu");

        jLabel_ZaposleniServisVozila.setText("Zaposleni");

        jComboBox_ZaposleniServisVozila.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Zaposlenog" }));
        jComboBox_ZaposleniServisVozila.setToolTipText("Zaposleni koji je predao Vozilo u Servis");

        DateChooserDatumVozilaServis.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout Panel_AdminServisVozilaLayout = new javax.swing.GroupLayout(Panel_AdminServisVozila);
        Panel_AdminServisVozila.setLayout(Panel_AdminServisVozilaLayout);
        Panel_AdminServisVozilaLayout.setHorizontalGroup(
            Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminServisVozilaLayout.createSequentialGroup()
                .addGroup(Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_AdminServisVozilaLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Label_IDServisVozila)
                            .addComponent(Label_DatumPredajeVozilaServis)
                            .addComponent(Label_ID_VozilaServis)
                            .addComponent(Label_KilometrazaVozilaServis)))
                    .addGroup(Panel_AdminServisVozilaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Label_OpisServisVozila))
                    .addGroup(Panel_AdminServisVozilaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_ZaposleniServisVozila)))
                .addGap(38, 38, 38)
                .addGroup(Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13)
                    .addComponent(ComboBox_ServisVozila, 0, 350, Short.MAX_VALUE)
                    .addComponent(txt_KilometrazaVozilaServis)
                    .addComponent(txt_idServisVozila)
                    .addComponent(jComboBox_ZaposleniServisVozila, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DateChooserDatumVozilaServis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        Panel_AdminServisVozilaLayout.setVerticalGroup(
            Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminServisVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_IDServisVozila)
                    .addComponent(txt_idServisVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox_ServisVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_ID_VozilaServis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Label_DatumPredajeVozilaServis)
                    .addComponent(DateChooserDatumVozilaServis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_KilometrazaVozilaServis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_KilometrazaVozilaServis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_OpisServisVozila))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_ZaposleniServisVozila)
                    .addComponent(jComboBox_ZaposleniServisVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_PretragaServisaVozila.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pretraga Servisa Vozila ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(255, 102, 102))); // NOI18N

        txt_pretragaServisVozila.setToolTipText("Pretraga Vozila na Servisu po Identifikacionom Broju");
        txt_pretragaServisVozila.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_pretragaServisVozilaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout Panel_PretragaServisaVozilaLayout = new javax.swing.GroupLayout(Panel_PretragaServisaVozila);
        Panel_PretragaServisaVozila.setLayout(Panel_PretragaServisaVozilaLayout);
        Panel_PretragaServisaVozilaLayout.setHorizontalGroup(
            Panel_PretragaServisaVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_PretragaServisaVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretragaServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );
        Panel_PretragaServisaVozilaLayout.setVerticalGroup(
            Panel_PretragaServisaVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_PretragaServisaVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretragaServisVozila))
        );

        Panel_FuncButtonServisVozila.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Funkcionalna Dugmad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 102, 255))); // NOI18N

        Button_clearServisVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearServisVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearServisVozila.setText("Očisti");
        Button_clearServisVozila.setToolTipText("Očisti polja za unos podataka");
        Button_clearServisVozila.setFocusable(false);
        Button_clearServisVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearServisVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearServisVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearServisVozilaActionPerformed(evt);
            }
        });

        Button_deleteServisVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteServisVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteServisVozila.setText("Obriši");
        Button_deleteServisVozila.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteServisVozila.setFocusable(false);
        Button_deleteServisVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteServisVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteServisVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteServisVozilaActionPerformed(evt);
            }
        });

        Button_insertServisVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertServisVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertServisVozila.setText("Unos");
        Button_insertServisVozila.setToolTipText("Unos novih podataka u bazu");
        Button_insertServisVozila.setFocusable(false);
        Button_insertServisVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertServisVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertServisVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertServisVozilaActionPerformed(evt);
            }
        });

        Button_updateServisVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateServisVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateServisVozila.setText("Ažuriranje");
        Button_updateServisVozila.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateServisVozila.setFocusable(false);
        Button_updateServisVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateServisVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateServisVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateServisVozilaActionPerformed(evt);
            }
        });

        Button_stampaServisVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaServisVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaServisVozila.setText("Štampa");
        Button_stampaServisVozila.setToolTipText("Štampa podataka iz tabele");
        Button_stampaServisVozila.setFocusable(false);
        Button_stampaServisVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaServisVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaServisVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaServisVozilaActionPerformed(evt);
            }
        });

        Button_refreshServisVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshServisVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshServisVozila.setText("Osveži");
        Button_refreshServisVozila.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshServisVozila.setFocusable(false);
        Button_refreshServisVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshServisVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshServisVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshServisVozilaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_FuncButtonServisVozilaLayout = new javax.swing.GroupLayout(Panel_FuncButtonServisVozila);
        Panel_FuncButtonServisVozila.setLayout(Panel_FuncButtonServisVozilaLayout);
        Panel_FuncButtonServisVozilaLayout.setHorizontalGroup(
            Panel_FuncButtonServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_FuncButtonServisVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_FuncButtonServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_FuncButtonServisVozilaLayout.createSequentialGroup()
                        .addGroup(Panel_FuncButtonServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Button_insertServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Button_updateServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(Panel_FuncButtonServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Button_clearServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(Button_deleteServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(Button_stampaServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_FuncButtonServisVozilaLayout.createSequentialGroup()
                        .addComponent(Button_refreshServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                        .addGap(128, 128, 128))))
        );
        Panel_FuncButtonServisVozilaLayout.setVerticalGroup(
            Panel_FuncButtonServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_FuncButtonServisVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_FuncButtonServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_insertServisVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_clearServisVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_FuncButtonServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_deleteServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                    .addComponent(Button_updateServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_FuncButtonServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_stampaServisVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_refreshServisVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelServisVozilaLayout = new javax.swing.GroupLayout(jPanelServisVozila);
        jPanelServisVozila.setLayout(jPanelServisVozilaLayout);
        jPanelServisVozilaLayout.setHorizontalGroup(
            jPanelServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelServisVozilaLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanelServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelServisVozilaLayout.createSequentialGroup()
                        .addComponent(Panel_AdminServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Panel_FuncButtonServisVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Panel_PretragaServisaVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPaneServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)))
        );
        jPanelServisVozilaLayout.setVerticalGroup(
            jPanelServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelServisVozilaLayout.createSequentialGroup()
                .addComponent(jScrollPaneServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addGroup(jPanelServisVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelServisVozilaLayout.createSequentialGroup()
                        .addComponent(Panel_PretragaServisaVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Panel_FuncButtonServisVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelServisVozilaLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(Panel_AdminServisVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPaneVozila.addTab("Servis Vozila", new javax.swing.ImageIcon(getClass().getResource("/Grafika/App-service-manager-icon.png")), jPanelServisVozila); // NOI18N

        Table_Vozila.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_Vozila.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_VozilaMouseClicked(evt);
            }
        });
        Table_Vozila.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table_VozilaKeyReleased(evt);
            }
        });
        jScrollPaneVozila.setViewportView(Table_Vozila);

        Panel_AdminVozila.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upravljanje Vozilima", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 153, 255))); // NOI18N

        Label_IDVozila.setText("ID");

        Label_MarkaVozila.setText("Marka");

        Label_OpisVozila.setText("Opis");

        Label_TipVozila.setText("Tip");

        Label_ID_KategorijeVozila.setText("Kategorija");

        txt_OpisVozila.setColumns(20);
        txt_OpisVozila.setRows(5);
        txt_OpisVozila.setToolTipText("Kratak opis Vozila");
        jScrollPane12.setViewportView(txt_OpisVozila);

        ComboBox_KategorijaVozila.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Kategoriju Vozila" }));
        ComboBox_KategorijaVozila.setToolTipText("Prikaz postojećih kategorija Vozila");

        txt_RegVozila.setToolTipText("Oznaka i Registracioni Broj");

        txt_MarkaVozila.setToolTipText("Marka Vozila");

        txt_TipVozila.setToolTipText("Tip Vozila");

        txt_idVozila.setToolTipText("Identifikacioni Broj Vozila");

        txt_PotrosnjsVozila.setToolTipText("Potrošnja goriva u litrima na 100 kilometara");

        jLabel_PotrosnjaVozila.setText("Potrošnja");

        jLabel_RegVozila.setText("Registracija");

        javax.swing.GroupLayout Panel_AdminVozilaLayout = new javax.swing.GroupLayout(Panel_AdminVozila);
        Panel_AdminVozila.setLayout(Panel_AdminVozilaLayout);
        Panel_AdminVozilaLayout.setHorizontalGroup(
            Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminVozilaLayout.createSequentialGroup()
                .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_AdminVozilaLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Label_IDVozila)
                            .addComponent(Label_MarkaVozila)
                            .addComponent(Label_ID_KategorijeVozila)
                            .addComponent(Label_TipVozila)))
                    .addGroup(Panel_AdminVozilaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Label_OpisVozila)
                            .addComponent(jLabel_PotrosnjaVozila)))
                    .addGroup(Panel_AdminVozilaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_RegVozila)))
                .addGap(38, 38, 38)
                .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_PotrosnjsVozila)
                    .addComponent(txt_MarkaVozila)
                    .addComponent(jScrollPane12)
                    .addComponent(txt_RegVozila, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ComboBox_KategorijaVozila, 0, 362, Short.MAX_VALUE)
                    .addComponent(txt_TipVozila)
                    .addComponent(txt_idVozila))
                .addContainerGap())
        );
        Panel_AdminVozilaLayout.setVerticalGroup(
            Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdminVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_IDVozila)
                    .addComponent(txt_idVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox_KategorijaVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_ID_KategorijeVozila))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_MarkaVozila)
                    .addComponent(txt_MarkaVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_TipVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_TipVozila))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_RegVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_RegVozila))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_OpisVozila))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_AdminVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_PotrosnjsVozila, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_PotrosnjaVozila, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        Panel_PretragaVozila.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pretraga Vozila ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(255, 102, 102))); // NOI18N

        txt_pretragaVozila.setToolTipText("Pretraga Vozila po Identifikacionom Broju, Broju Registracione Oznake");
        txt_pretragaVozila.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_pretragaVozilaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout Panel_PretragaVozilaLayout = new javax.swing.GroupLayout(Panel_PretragaVozila);
        Panel_PretragaVozila.setLayout(Panel_PretragaVozilaLayout);
        Panel_PretragaVozilaLayout.setHorizontalGroup(
            Panel_PretragaVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_PretragaVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretragaVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );
        Panel_PretragaVozilaLayout.setVerticalGroup(
            Panel_PretragaVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_PretragaVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_pretragaVozila))
        );

        Panel_FuncButtonVozila.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Funkcionalna Dugmad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 102, 255))); // NOI18N

        Button_clearVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearVozila.setText("Očisti");
        Button_clearVozila.setToolTipText("Očisti polja za unos podataka");
        Button_clearVozila.setFocusable(false);
        Button_clearVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearVozilaActionPerformed(evt);
            }
        });

        Button_deleteVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteVozila.setText("Obriši");
        Button_deleteVozila.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteVozila.setFocusable(false);
        Button_deleteVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteVozilaActionPerformed(evt);
            }
        });

        Button_insertVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertVozila.setText("Unos");
        Button_insertVozila.setToolTipText("Unos novih podataka u bazu");
        Button_insertVozila.setFocusable(false);
        Button_insertVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertVozilaActionPerformed(evt);
            }
        });

        Button_updateVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateVozila.setText("Ažuriranje");
        Button_updateVozila.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateVozila.setFocusable(false);
        Button_updateVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateVozilaActionPerformed(evt);
            }
        });

        Button_stampaVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaVozila.setText("Štampa");
        Button_stampaVozila.setToolTipText("Štampa podataka iz tabele");
        Button_stampaVozila.setFocusable(false);
        Button_stampaVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaVozilaActionPerformed(evt);
            }
        });

        Button_refreshVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshVozila.setText("Osveži");
        Button_refreshVozila.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshVozila.setFocusable(false);
        Button_refreshVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshVozilaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_FuncButtonVozilaLayout = new javax.swing.GroupLayout(Panel_FuncButtonVozila);
        Panel_FuncButtonVozila.setLayout(Panel_FuncButtonVozilaLayout);
        Panel_FuncButtonVozilaLayout.setHorizontalGroup(
            Panel_FuncButtonVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_FuncButtonVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_FuncButtonVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_FuncButtonVozilaLayout.createSequentialGroup()
                        .addGroup(Panel_FuncButtonVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Button_insertVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Button_updateVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(Panel_FuncButtonVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Button_clearVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(Button_deleteVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(Button_stampaVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_FuncButtonVozilaLayout.createSequentialGroup()
                        .addComponent(Button_refreshVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                        .addGap(128, 128, 128))))
        );
        Panel_FuncButtonVozilaLayout.setVerticalGroup(
            Panel_FuncButtonVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_FuncButtonVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_FuncButtonVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_insertVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_clearVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_FuncButtonVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_deleteVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                    .addComponent(Button_updateVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_FuncButtonVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_stampaVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_refreshVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelPodaciVozilaLayout = new javax.swing.GroupLayout(jPanelPodaciVozila);
        jPanelPodaciVozila.setLayout(jPanelPodaciVozilaLayout);
        jPanelPodaciVozilaLayout.setHorizontalGroup(
            jPanelPodaciVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPodaciVozilaLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanelPodaciVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPodaciVozilaLayout.createSequentialGroup()
                        .addComponent(Panel_AdminVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPodaciVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Panel_FuncButtonVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Panel_PretragaVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPaneVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)))
        );
        jPanelPodaciVozilaLayout.setVerticalGroup(
            jPanelPodaciVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPodaciVozilaLayout.createSequentialGroup()
                .addComponent(jScrollPaneVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addGroup(jPanelPodaciVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelPodaciVozilaLayout.createSequentialGroup()
                        .addComponent(Panel_PretragaVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Panel_FuncButtonVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelPodaciVozilaLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(Panel_AdminVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPaneVozila.addTab("Podaci o Vozilima", new javax.swing.ImageIcon(getClass().getResource("/Grafika/Household-Garage-icon.png")), jPanelPodaciVozila); // NOI18N

        Table_KatVozila.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_KatVozila.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_KatVozilaMouseClicked(evt);
            }
        });
        Table_KatVozila.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table_KatVozilaKeyReleased(evt);
            }
        });
        ScrollPane_OglasnaTabla1.setViewportView(Table_KatVozila);

        Panel_AdministracijaKatVozila.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upravljanje Kategorijama Vozila", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 102, 255))); // NOI18N

        Button_clearKatVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearKatVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearKatVozila.setText("Očisti");
        Button_clearKatVozila.setToolTipText("Očisti polja za unos podataka");
        Button_clearKatVozila.setFocusable(false);
        Button_clearKatVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearKatVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearKatVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearKatVozilaActionPerformed(evt);
            }
        });

        Button_deleteKatVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteKatVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteKatVozila.setText("Obriši");
        Button_deleteKatVozila.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteKatVozila.setFocusable(false);
        Button_deleteKatVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteKatVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteKatVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteKatVozilaActionPerformed(evt);
            }
        });

        Button_insertKatVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertKatVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertKatVozila.setText("Unos");
        Button_insertKatVozila.setToolTipText("Unos novih podataka u bazu");
        Button_insertKatVozila.setFocusable(false);
        Button_insertKatVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertKatVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertKatVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertKatVozilaActionPerformed(evt);
            }
        });

        Button_updateKatVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateKatVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateKatVozila.setText("Ažuriranje");
        Button_updateKatVozila.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateKatVozila.setFocusable(false);
        Button_updateKatVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateKatVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateKatVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateKatVozilaActionPerformed(evt);
            }
        });

        TextField_idKatVozila.setToolTipText("Indetifikacioni broj Kategorije Vozila");

        TextField_nazivKatVozila.setToolTipText("Naziv Kategorije Vozila");

        Label_idKatVozila.setText("Id");

        Label_nazivKatVozila.setText("Naziv");

        LabelOpisKatVozila.setText("Opis");

        TextArea_opisKatVozila.setColumns(20);
        TextArea_opisKatVozila.setRows(5);
        TextArea_opisKatVozila.setToolTipText("Kratki Opis Kategorije Vozila");
        ScrollPane_OpisOT1.setViewportView(TextArea_opisKatVozila);

        Button_stampaKatVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaKatVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-document-print-direct-icon.png"))); // NOI18N
        Button_stampaKatVozila.setText("Štampa");
        Button_stampaKatVozila.setToolTipText("Štampa podataka iz tabele");
        Button_stampaKatVozila.setFocusable(false);
        Button_stampaKatVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaKatVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaKatVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaKatVozilaActionPerformed(evt);
            }
        });

        Button_refreshKatVozila.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshKatVozila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshKatVozila.setText("Osveži");
        Button_refreshKatVozila.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshKatVozila.setFocusable(false);
        Button_refreshKatVozila.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshKatVozila.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshKatVozila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshKatVozilaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_AdministracijaKatVozilaLayout = new javax.swing.GroupLayout(Panel_AdministracijaKatVozila);
        Panel_AdministracijaKatVozila.setLayout(Panel_AdministracijaKatVozilaLayout);
        Panel_AdministracijaKatVozilaLayout.setHorizontalGroup(
            Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AdministracijaKatVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Label_nazivKatVozila)
                    .addComponent(Label_idKatVozila)
                    .addComponent(LabelOpisKatVozila))
                .addGap(9, 9, 9)
                .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane_OpisOT1)
                    .addComponent(TextField_nazivKatVozila)
                    .addComponent(TextField_idKatVozila))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_refreshKatVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_insertKatVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_updateKatVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_clearKatVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_deleteKatVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_stampaKatVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addContainerGap())
        );
        Panel_AdministracijaKatVozilaLayout.setVerticalGroup(
            Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AdministracijaKatVozilaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_AdministracijaKatVozilaLayout.createSequentialGroup()
                        .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Label_idKatVozila)
                            .addComponent(TextField_idKatVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextField_nazivKatVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label_nazivKatVozila))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_AdministracijaKatVozilaLayout.createSequentialGroup()
                                .addComponent(LabelOpisKatVozila)
                                .addGap(35, 132, Short.MAX_VALUE))
                            .addComponent(ScrollPane_OpisOT1)))
                    .addGroup(Panel_AdministracijaKatVozilaLayout.createSequentialGroup()
                        .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_insertKatVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_clearKatVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Button_updateKatVozila, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(Button_deleteKatVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Panel_AdministracijaKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Button_stampaKatVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_refreshKatVozila, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        javax.swing.GroupLayout jPanelKatVozilaLayout = new javax.swing.GroupLayout(jPanelKatVozila);
        jPanelKatVozila.setLayout(jPanelKatVozilaLayout);
        jPanelKatVozilaLayout.setHorizontalGroup(
            jPanelKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ScrollPane_OglasnaTabla1, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
            .addComponent(Panel_AdministracijaKatVozila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelKatVozilaLayout.setVerticalGroup(
            jPanelKatVozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKatVozilaLayout.createSequentialGroup()
                .addComponent(ScrollPane_OglasnaTabla1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_AdministracijaKatVozila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPaneVozila.addTab("Kategorije Vozila", new javax.swing.ImageIcon(getClass().getResource("/Grafika/bus-icon.png")), jPanelKatVozila); // NOI18N

        javax.swing.GroupLayout Panel_VozilaLayout = new javax.swing.GroupLayout(Panel_Vozila);
        Panel_Vozila.setLayout(Panel_VozilaLayout);
        Panel_VozilaLayout.setHorizontalGroup(
            Panel_VozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneVozila)
        );
        Panel_VozilaLayout.setVerticalGroup(
            Panel_VozilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneVozila)
        );

        TabbedPane_Administracija.addTab("Vozila", new javax.swing.ImageIcon(getClass().getResource("/Grafika/Wedding-Car-Back-icon.png")), Panel_Vozila); // NOI18N

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

        Menu_help.setText("Help");
        Menu_help.setToolTipText("Help");
        MenuBar_adminMeni.add(Menu_help);

        setJMenuBar(MenuBar_adminMeni);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ToolBar_OpsteFunkcije, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedPane_Administracija)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ToolBar_OpsteFunkcije, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TabbedPane_Administracija, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(791, 730));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_pretragaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pretragaKeyReleased
        // Pretraga Dokumenata
        //popuna txt polja podacima za pretragu prema ID
        try{
            String sql="select * from dokumenta where ID=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretraga.getText());
            rs=pst.executeQuery();
            //popuna polja
            dokumentaPolja();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        //popuna txt polja podacima za pretragu prema Broju
        try{
            String sql="select * from dokumenta where Broj=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretraga.getText());
            rs=pst.executeQuery();
            //popuna polja
            dokumentaPolja();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        //popuna txt polja podacima za pretragu prema Nazivu
        try{
            String sql="select * from dokumenta where Naziv=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretraga.getText());
            rs=pst.executeQuery();
            //popuna polja
            dokumentaPolja();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txt_pretragaKeyReleased

    private void Table_documentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_documentMouseClicked
        //Otvaranje dokumenta kada se klikne na red u tabeli
        try{
            int row=Table_document.getSelectedRow();
            String doc=(Table_document.getModel().getValueAt(row , 6).toString());
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+doc);
            }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Trenutno ne možete da otvorite dokument, restartujte računar i pokušajte ponovo");
        }
        // Kada se klikne na tebelu pokupiti podatke
        try{

            int row =Table_document.getSelectedRow();
            String Table_click=(Table_document.getModel().getValueAt(row, 0).toString());
            String sql="select * from dokumenta where ID='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            dokumentaPolja();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_documentMouseClicked

    private void Button_singoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_singoutActionPerformed
        // Sing Out i preusmerenje na formu za login
        dispose();//zatvaranje prozora
        new Login().setVisible(true);
    }//GEN-LAST:event_Button_singoutActionPerformed

    private void MenuItem_LogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_LogOutActionPerformed
        // Sing Out i preusmerenje na formu za login
        dispose();//zatvaranje prozora
        new Login().setVisible(true);
    }//GEN-LAST:event_MenuItem_LogOutActionPerformed

    private void MenuItem_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_ExitActionPerformed
        // Zatvaranje aplikacije iz menija
        System.exit(0);
    }//GEN-LAST:event_MenuItem_ExitActionPerformed

    private void Table_KatDocumentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_KatDocumentMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{

            int row =Table_KatDocument.getSelectedRow();
            String Table_click=(Table_KatDocument.getModel().getValueAt(row, 0).toString());
            String sql="select * from kategorije_dokumenata where ID='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
                if(rs.next()){
                    String add1=rs.getString("ID");
                    txt_IDKat.setText(add1);

                    String add2=rs.getString("Naziv_Kategorije");
                    txt_NazivKat.setText(add2);

                    String add3=rs.getString("Opis");
                    txt_OpisKat.setText(add3);

                }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_KatDocumentMouseClicked

    private void Button_clearKatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearKatActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            txt_IDKat.setText("");
            txt_NazivKat.setText("");
            txt_OpisKat.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearKatActionPerformed

    private void Button_deleteKatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteKatActionPerformed
        // Brisanje Podataka iz baze
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from kategorije_dokumenata where ID =?";
            try{

                pst=conn.prepareStatement(sql);
                pst.setString(1,txt_IDKat.getText());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);

                Update_Kat();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteKatActionPerformed

    private void Button_insertKatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertKatActionPerformed
        // Insert novih podataka
        try{
            String sql="Insert into kategorije_dokumenata(ID,Naziv_Kategorije,Opis) values (?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_IDKat.getText());
            pst.setString(2,txt_NazivKat.getText());
            pst.setString(3,txt_OpisKat.getText());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);

            Update_Kat();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertKatActionPerformed

    private void Button_updateKatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateKatActionPerformed
        // Update podataka
        try{
            String ID = txt_IDKat.getText();
            String Naziv_Kategorije = txt_NazivKat.getText();
            String Opis = txt_OpisKat.getText();

            String sql="update kategorije_dokumenata set ID='"+ID+"',Naziv_Kategorije='"+Naziv_Kategorije+"',Opis='"+Opis+"' where ID='"+ID+"' ";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);

            Update_Kat();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateKatActionPerformed

    private void Table_UlogezaLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_UlogezaLoginMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{

            int row =Table_UlogezaLogin.getSelectedRow();
            String Table_click=(Table_UlogezaLogin.getModel().getValueAt(row, 0).toString());
            String sql="select * from uloge_za_login where id='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            if(rs.next()){
                String add1=rs.getString("id");
                    TextField_idUloge.setText(add1);

                    String add2=rs.getString("Naziv");
                    TextField_nazivUloge.setText(add2);

                    String add3=rs.getString("Opis");
                    TextArea_opisUloge.setText(add3);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_UlogezaLoginMouseClicked

    private void Button_clearUlogeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearUlogeActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            TextField_idUloge.setText("");
            TextField_nazivUloge.setText("");
            TextArea_opisUloge.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearUlogeActionPerformed

    private void Button_deleteUlogeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteUlogeActionPerformed
        // Brisanje Podataka iz baze
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from uloge_za_login where id =?";
            try{

                pst=conn.prepareStatement(sql);
                pst.setString(1,TextField_idUloge.getText());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);

                Update_Uloge();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteUlogeActionPerformed

    private void Button_stampaUlogeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaUlogeActionPerformed
       // Kreiranje izveštaja o ulogama koje korisnik ima da bi se logovao na sistem
        try {
            String izvestaj ="Izvestaji\\UlogeLoginIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaUlogeActionPerformed

    private void Button_updateUlogeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateUlogeActionPerformed
        // Update podataka
        try{
            String ID = TextField_idUloge.getText();
            String Naziv = TextField_nazivUloge.getText();
            String Opis = TextArea_opisUloge.getText();

            String sql="update uloge_za_login set id='"+ID+"',Naziv='"+Naziv+"',Opis='"+Opis+"' where id='"+ID+"' ";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);

            Update_Uloge();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateUlogeActionPerformed

    private void Button_refreshUlogeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshUlogeActionPerformed
        Update_Uloge();
    }//GEN-LAST:event_Button_refreshUlogeActionPerformed

    private void Table_UsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_UsersMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{            
            
            int row =Table_Users.getSelectedRow();
            String Table_click=(Table_Users.getModel().getValueAt(row, 0).toString());
            String sql="select * from korisnici where id='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            poljaKorisnik();
        }
        catch(Exception e){
            //JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_UsersMouseClicked

    private void txt_pretragaUsersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pretragaUsersKeyReleased
        // Pretraga Korisnika
        //popuna txt polja podacima za pretragu prema meilu
        try{
            String sql="select * from korisnici where Email=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaUsers.getText());
            rs=pst.executeQuery();

            //popuna txt polja podacima iz tabele
            poljaKorisnik();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        //popuna txt polja podacima za pretragu prema imenu
        try{
            String sql="select * from korisnici where Ime=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaUsers.getText());
            rs=pst.executeQuery();

            //popuna txt polja podacima iz tabele
            poljaKorisnik();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        //popuna txt polja podacima za pretragu prema prezimenu
        try{
            String sql="select * from korisnici where Prezime=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaUsers.getText());
            rs=pst.executeQuery();

            //popuna txt polja podacima iz tabele
            poljaKorisnik();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txt_pretragaUsersKeyReleased

    private void Button_clearUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearUsersActionPerformed
        // Brisanje svih polja za unos podataka
        try{
                    txt_IDUsers.setText("");
                    txt_UserName.setText("");
                    txt_Pass.setText("");
                    txt_Name.setText("");
                    txt_LastName.setText("");
                    txt_EmailUser.setText("");
                    txt_JobPhon.setText("");
                    txt_TelMob.setText("");
                    ComboBox_Users.setSelectedIndex(0);
                    txt_opisUser.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearUsersActionPerformed

    private void Button_deleteUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteUsersActionPerformed
        // Brisanje Podataka iz baze
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from korisnici where id =?";
            try{

                pst=conn.prepareStatement(sql);
                pst.setString(1,txt_IDUsers.getText());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);

                Update_Users();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteUsersActionPerformed

    private void Button_insertUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertUsersActionPerformed
        // Insert novih podataka
        try{
            String sql="Insert into korisnici "
                    + "(id,Korisnicko_Ime,lozinka,Ime,Prezime,Email,Tel_Posao,"
                    + "Tel_Mobilni,Uloge_za_Login,Opis) values (?,?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_IDUsers.getText());
            pst.setString(2,txt_UserName.getText());
            pst.setString(3,txt_Pass.getText());
            pst.setString(4,txt_Name.getText());
            pst.setString(5,txt_LastName.getText());
            pst.setString(6,txt_EmailUser.getText());
            pst.setString(7,txt_JobPhon.getText());
            pst.setString(8,txt_TelMob.getText());
            pst.setInt(9,ComboBox_Users.getSelectedIndex());
            pst.setString(10,txt_opisUser.getText());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);

            Update_Users();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertUsersActionPerformed

    private void Button_updateUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateUsersActionPerformed
        // Update podataka
        try{
           String ID =  txt_IDUsers.getText();
           String KIme = txt_UserName.getText();
           String Pass = txt_Pass.getText();
           String Ime = txt_Name.getText();
           String Prez = txt_LastName.getText();
           String Meil = txt_EmailUser.getText();
           String Jobt = txt_JobPhon.getText();
           String Mobt = txt_TelMob.getText();
           int Ulog = ComboBox_Users.getSelectedIndex();
           String Opis = txt_opisUser.getText();

           String sql="update korisnici set id='"+ID+"',Korisnicko_Ime='"+KIme+"',Lozinka='"+Pass+"'"
                    + ",Ime='"+Ime+"' ,Prezime='"+Prez+"' ,Email='"+Meil+"',Tel_Posao='"+Jobt+"' ,Tel_Mobilni='"+Mobt+"'"
                    + ",Uloge_za_Login='"+Ulog+"', Opis='"+Opis+"' where id='"+ID+"' ";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);

            Update_Users();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateUsersActionPerformed

    private void Table_OglasnaTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_OglasnaTablaMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{

            int row =Table_OglasnaTabla.getSelectedRow();
            String Table_click=(Table_OglasnaTabla.getModel().getValueAt(row, 0).toString());
            String sql="select * from oglasna_tabla where ID='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            poljaVesti();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_OglasnaTablaMouseClicked

    private void Table_documentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_documentKeyReleased
          // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =Table_document.getSelectedRow();
                String Table_click=(Table_document.getModel().getValueAt(row, 0).toString());
                String sql="select * from dokumenta where ID='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                dokumentaPolja();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Table_documentKeyReleased

    private void Table_KatDocumentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_KatDocumentKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =Table_KatDocument.getSelectedRow();
                String Table_click=(Table_KatDocument.getModel().getValueAt(row, 0).toString());
                String sql="select * from kategorije_dokumenata where ID='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                if(rs.next()){
                    String add1=rs.getString("ID");
                    txt_IDKat.setText(add1);

                    String add2=rs.getString("Naziv_Kategorije");
                    txt_NazivKat.setText(add2);

                    String add3=rs.getString("Opis");
                    txt_OpisKat.setText(add3);

                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Table_KatDocumentKeyReleased

    private void Table_UsersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_UsersKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =Table_Users.getSelectedRow();
                String Table_click=(Table_Users.getModel().getValueAt(row, 0).toString());
                String sql="select * from korisnici where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                poljaKorisnik();
            }
            catch(Exception e){
                //JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Table_UsersKeyReleased

    private void Table_UlogezaLoginKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_UlogezaLoginKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =Table_UlogezaLogin.getSelectedRow();
                String Table_click=(Table_UlogezaLogin.getModel().getValueAt(row, 0).toString());
                String sql="select * from uloge_za_login where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                if(rs.next()){
                    String add1=rs.getString("id");
                    TextField_idUloge.setText(add1);

                    String add2=rs.getString("Naziv");
                    TextField_nazivUloge.setText(add2);

                    String add3=rs.getString("Opis");
                    TextArea_opisUloge.setText(add3);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Table_UlogezaLoginKeyReleased

    private void Table_OglasnaTablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_OglasnaTablaKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =Table_OglasnaTabla.getSelectedRow();
                String Table_click=(Table_OglasnaTabla.getModel().getValueAt(row, 0).toString());
                String sql="select * from oglasna_tabla where ID='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                poljaVesti();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Table_OglasnaTablaKeyReleased

    private void Button_proveraKonekcijaBazeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_proveraKonekcijaBazeActionPerformed
        conn=Data.javaconnect.KonekcijaDb();//Provera da li je aplikacija konektovana ili ne na bazu podataka pomocu dugmeta
    }//GEN-LAST:event_Button_proveraKonekcijaBazeActionPerformed

    private void MenuItem_bazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_bazaActionPerformed
        conn=Data.javaconnect.KonekcijaDb();//Provera da li je aplikacija konektovana ili ne na bazu podataka iz menija
    }//GEN-LAST:event_MenuItem_bazaActionPerformed

    private void Button_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateActionPerformed
        // Update podataka
        try{
            String ID = txt_ID.getText();
            String Broj = txt_Broj.getText();
            String Naziv = txt_Naziv.getText();
            String Opis = txt_Opis.getText();
            String Dokument = txt_Dokument.getText();
            int ID_Kategorije = ComboBox_KategorijaDokumenta.getSelectedIndex();

            String sql="update dokumenta set ID='"+ID+"',Broj='"+Broj+"',Naziv='"+Naziv+"',Opis='"+Opis+"',Dokument='"+Dokument+"',ID_Kategorije='"+ID_Kategorije+"' where ID='"+ID+"' ";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);

            Update_Doc();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateActionPerformed

    private void Button_insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertActionPerformed
        // Insert novih podataka
        try{
            String sql="Insert into dokumenta(ID,Broj,Naziv,Opis,Dokument,ID_Kategorije ) values (?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_ID.getText());
            pst.setString(2,txt_Broj.getText());
            pst.setString(3,txt_Naziv.getText());
            pst.setString(4,txt_Opis.getText());
            pst.setString(5,txt_Dokument.getText());
            pst.setInt(6,ComboBox_KategorijaDokumenta.getSelectedIndex());
            
            
            
            pst.execute();

            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);

            Update_Doc();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertActionPerformed

    private void Button_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteActionPerformed
        // Brisanje Podataka iz baze
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from dokumenta where ID =?";
            try{

                pst=conn.prepareStatement(sql);
                pst.setString(1,txt_ID.getText());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);

                Update_Doc();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteActionPerformed

    private void Button_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            txt_ID.setText("");
            txt_Broj.setText("");
            txt_Naziv.setText("");
            txt_Opis.setText("");
            txt_Dokument.setText("");
            ComboBox_KategorijaDokumenta.setSelectedIndex(0);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearActionPerformed

    private void Button_stampaDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaDocActionPerformed
        // Kreiranje izveštaja o dokumentima
        try {
            String izvestaj ="Izvestaji\\DokumentaIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }    }//GEN-LAST:event_Button_stampaDocActionPerformed

    private void Button_refreshDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshDocActionPerformed
        Update_Doc();// Ponovno ucitavanje tabele dokument
    }//GEN-LAST:event_Button_refreshDocActionPerformed

    private void Button_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_ExitActionPerformed
        // Zatvaranje aplikacije pomocu dugmeta
        System.exit(0);
    }//GEN-LAST:event_Button_ExitActionPerformed

    private void Button_internetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_internetActionPerformed
        // Provera da li je internet dostupan iz dugmeta
        dostupnostInterneta();
    }//GEN-LAST:event_Button_internetActionPerformed

    private void MenuItem_internetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_internetActionPerformed
        // Provera da li je internet dostupan iz menija
        dostupnostInterneta();
    }//GEN-LAST:event_MenuItem_internetActionPerformed

    private void Button_unosDokumentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_unosDokumentaActionPerformed
         
        
        try{
           // odabir dokumenta
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f=chooser.getSelectedFile();
            String filename =f.getAbsolutePath();
            txt_Dokument.setText(filename);         
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }  
    }//GEN-LAST:event_Button_unosDokumentaActionPerformed

    private void Button_stampaKorisniciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaKorisniciActionPerformed
        // Kreiranje izveštaja o Korisnicima
        try {
            String izvestaj ="Izvestaji\\KorisnikIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaKorisniciActionPerformed

    private void Button_refreshKorisniciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshKorisniciActionPerformed
        // Ponovno ucitavanje podataka u tabelu
        Update_Users();
    }//GEN-LAST:event_Button_refreshKorisniciActionPerformed

    private void Button_refreshKatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshKatActionPerformed
        // Ponovno ucitavanje podataka u tabe
        Update_Kat();
    }//GEN-LAST:event_Button_refreshKatActionPerformed

    private void Button_stampaKatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaKatActionPerformed
                // Kreiranje izveštaja o kategorijama dokumenata
        try {
            String izvestaj ="Izvestaji\\KategorijeDokumenataIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaKatActionPerformed

    private void Button_refreshOglasnaTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshOglasnaTablaActionPerformed
        // Ponovno ucitavanje podataka u tabelu
        Update_OglasnaTabla();
    }//GEN-LAST:event_Button_refreshOglasnaTablaActionPerformed

    private void Button_stampaOglasnaTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaOglasnaTablaActionPerformed
        // Kreiranje izveštaja o Vestima iz kompanije
        try {
            String izvestaj ="Izvestaji\\VestiIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaOglasnaTablaActionPerformed

    private void Button_updateOTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateOTActionPerformed
        // Update podataka
        try{
            String ID =  TextField_idOT.getText();
            String Naziv = TextField_nazivOT.getText();
            String Opis = TextArea_opisOT.getText();

            String sql="update oglasna_tabla set ID='"+ID+"',Naziv='"+Naziv+"',Opis='"+Opis+"' where ID='"+ID+"' ";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);

            Update_OglasnaTabla();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateOTActionPerformed

    private void Button_insertOTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertOTActionPerformed
        // Insert novih podataka
        try{
            String sql="Insert into oglasna_tabla(ID,Naziv,Opis) values (?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,TextField_idOT.getText());
            pst.setString(2,TextField_nazivOT.getText());
            pst.setString(3,TextArea_opisOT.getText());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);

            Update_OglasnaTabla();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertOTActionPerformed

    private void Button_deleteOTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteOTActionPerformed
        // Brisanje Podataka iz baze
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from oglasna_tabla where ID =?";
            try{

                pst=conn.prepareStatement(sql);
                pst.setString(1,TextField_idOT.getText());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);

                Update_OglasnaTabla();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteOTActionPerformed

    private void Button_clearOTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearOTActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            TextField_idOT.setText("");
            TextField_nazivOT.setText("");
            TextArea_opisOT.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearOTActionPerformed

    private void Table_KatVozilaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_KatVozilaMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{

            int row =Table_KatVozila.getSelectedRow();
            String Table_click=(Table_KatVozila.getModel().getValueAt(row, 0).toString());
            String sql="select * from kategorije_vozila where id='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            poljaKatVozila();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_KatVozilaMouseClicked

    private void Table_KatVozilaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_KatVozilaKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =Table_KatVozila.getSelectedRow();
                String Table_click=(Table_KatVozila.getModel().getValueAt(row, 0).toString());
                String sql="select * from kategorije_vozila where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                poljaKatVozila();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Table_KatVozilaKeyReleased

    private void Button_clearKatVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearKatVozilaActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            TextField_idKatVozila.setText("");
            TextField_nazivKatVozila.setText("");
            TextArea_opisKatVozila.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearKatVozilaActionPerformed

    private void Button_deleteKatVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteKatVozilaActionPerformed
        // Brisanje Podataka iz baze
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from kategorije_vozila where id =?";
            try{

                pst=conn.prepareStatement(sql);
                pst.setString(1,TextField_idKatVozila.getText());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);

                Update_KatVozila();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteKatVozilaActionPerformed

    private void Button_insertKatVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertKatVozilaActionPerformed
        // Insert novih podataka
        try{
            String sql="Insert into kategorije_vozila(id,Naziv,Opis) values (?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,TextField_idKatVozila.getText());
            pst.setString(2,TextField_nazivKatVozila.getText());
            pst.setString(3,TextArea_opisKatVozila.getText());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);

            Update_KatVozila();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertKatVozilaActionPerformed

    private void Button_updateKatVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateKatVozilaActionPerformed
        // Update podataka
        try{
            String ID =  TextField_idKatVozila.getText();
            String Naziv = TextField_nazivKatVozila.getText();
            String Opis = TextArea_opisKatVozila.getText();

            String sql="update kategorije_vozila set id='"+ID+"',Naziv='"+Naziv+"',Opis='"+Opis+"' where id='"+ID+"' ";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);

            Update_KatVozila();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateKatVozilaActionPerformed

    private void Button_stampaKatVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaKatVozilaActionPerformed
        // Kreiranje izveštaja o Kategorijama Vozila
        try {
            String izvestaj ="Izvestaji\\KategorijeVozilaIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaKatVozilaActionPerformed

    private void Button_refreshKatVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshKatVozilaActionPerformed
        // Ponovno ucitavanje podataka u tabelu
        Update_KatVozila();
    }//GEN-LAST:event_Button_refreshKatVozilaActionPerformed

    private void Table_VozilaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_VozilaMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{

            int row =Table_Vozila.getSelectedRow();
            String Table_click=(Table_Vozila.getModel().getValueAt(row, 0).toString());
            String sql="select * from vozila where id='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            poljaVozila();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_VozilaMouseClicked

    private void Table_VozilaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_VozilaKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =Table_Vozila.getSelectedRow();
                String Table_click=(Table_Vozila.getModel().getValueAt(row, 0).toString());
                String sql="select * from vozila where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                poljaVozila();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }//GEN-LAST:event_Table_VozilaKeyReleased

    private void txt_pretragaVozilaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pretragaVozilaKeyReleased
        //popuna txt polja podacima za pretragu prema identifikacionom broju
        try{
            String sql="select * from vozila where id=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaVozila.getText());
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            poljaVozila();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        //popuna txt polja podacima za pretragu prema registraciji vozila
        try{
            String sql="select * from vozila where Registracione_oznake=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaVozila.getText());
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            poljaVozila();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txt_pretragaVozilaKeyReleased

    private void Button_clearVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearVozilaActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            txt_idVozila.setText("");
            ComboBox_KategorijaVozila.setSelectedIndex(0);
            txt_MarkaVozila.setText("");
            txt_TipVozila.setText("");
            txt_RegVozila.setText("");
            txt_OpisVozila.setText("");
            txt_PotrosnjsVozila.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearVozilaActionPerformed

    private void Button_deleteVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteVozilaActionPerformed
   	// Brisanje Podataka iz baze
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from vozila where id =?";
            try{

                pst=conn.prepareStatement(sql);
                pst.setString(1,txt_idVozila.getText());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);

                Update_Vozila();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteVozilaActionPerformed

    private void Button_insertVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertVozilaActionPerformed
        // Insert novih podataka
        try{
            String sql="Insert into vozila(id,idKat,Marka,Tip,Registracione_oznake,Opis,Potrosnja) values (?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_idVozila.getText());
            pst.setInt(2,ComboBox_KategorijaVozila.getSelectedIndex());
            pst.setString(3,txt_MarkaVozila.getText());
            pst.setString(4,txt_TipVozila.getText());
            pst.setString(5,txt_RegVozila.getText());
            pst.setString(6,txt_OpisVozila.getText());
            pst.setString(7,txt_PotrosnjsVozila.getText());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);

            Update_Vozila();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertVozilaActionPerformed

    private void Button_updateVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateVozilaActionPerformed
        // Update podataka
        try{
            String ID =  txt_idVozila.getText();
            int Kat_Vozila = ComboBox_KategorijaVozila.getSelectedIndex();
            String Marka = txt_MarkaVozila.getText();
            String Tip = txt_TipVozila.getText();
            String RegOznake = txt_RegVozila.getText();
            String Opis = txt_OpisVozila.getText();
            String Potrosnja = txt_PotrosnjsVozila.getText();

            String sql="update vozila set id='"+ID+"',idKat='"+Kat_Vozila+"',Marka='"+Marka+"',Tip='"+Tip+"',Registracione_oznake='"+RegOznake+"',Opis='"+Opis+"',Potrosnja='"+Potrosnja+"' where id='"+ID+"' ";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);

            Update_Vozila();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateVozilaActionPerformed

    private void Button_stampaVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaVozilaActionPerformed
        // Kreiranje izveštaja o Vozilima
        try {
            String izvestaj ="Izvestaji\\VozilaIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaVozilaActionPerformed

    private void Button_refreshVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshVozilaActionPerformed
        //Ponovno ucitavanje u tabelu vozila
        Update_Vozila();
    }//GEN-LAST:event_Button_refreshVozilaActionPerformed

    private void Table_ServisVozilaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_ServisVozilaMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{

            int row =Table_ServisVozila.getSelectedRow();
            String Table_click=(Table_ServisVozila.getModel().getValueAt(row, 0).toString());
            String sql="select * from servis_vozila where id='"+Table_click+"' ";//Upit za popunu textBox na klik u tabeli
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            poljaServisVozila();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_ServisVozilaMouseClicked

    private void Table_ServisVozilaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_ServisVozilaKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =Table_ServisVozila.getSelectedRow();
                String Table_click=(Table_ServisVozila.getModel().getValueAt(row, 0).toString());
                String sql="select * from servis_vozila where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                poljaServisVozila();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Table_ServisVozilaKeyReleased

    private void txt_pretragaServisVozilaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pretragaServisVozilaKeyReleased
         //popuna txt polja podacima za pretragu prema identifikacionom broju
        try{
            String sql="select * from servis_vozila where id=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaServisVozila.getText());
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            poljaServisVozila();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txt_pretragaServisVozilaKeyReleased

    private void Button_clearServisVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearServisVozilaActionPerformed
        // Brisanje svih polja za unos podataka
        try{
            txt_idServisVozila.setText("");
            ComboBox_ServisVozila.setSelectedIndex(0);
            ((JTextField) DateChooserDatumVozilaServis.getDateEditor().getUiComponent()).setText("");
            txt_KilometrazaVozilaServis.setText("");
            txt_OpisServisVozila.setText("");
            jComboBox_ZaposleniServisVozila.setSelectedIndex(0);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearServisVozilaActionPerformed

    private void Button_deleteServisVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteServisVozilaActionPerformed
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from servis_vozila where id =?";
            try{

                pst=conn.prepareStatement(sql);
                pst.setString(1,txt_idServisVozila.getText());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);

                Update_ServisVozila();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteServisVozilaActionPerformed

    private void Button_insertServisVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertServisVozilaActionPerformed
        // Insert novih podataka
        try{
            String sql="Insert into servis_vozila(id,idVozila,Datum_Predaje,Kilometraza,Opis,idKorisnik) values (?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_idServisVozila.getText());
            pst.setInt(2,ComboBox_ServisVozila.getSelectedIndex());
            pst.setString(3, ((JTextField) DateChooserDatumVozilaServis.getDateEditor().getUiComponent()).getText());
            pst.setString(4,txt_KilometrazaVozilaServis.getText());
            pst.setString(5,txt_OpisServisVozila.getText());
            pst.setInt(6,jComboBox_ZaposleniServisVozila.getSelectedIndex());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!","Sačuvaj Podatke",JOptionPane.OK_OPTION);

            Update_ServisVozila();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertServisVozilaActionPerformed

    private void Button_updateServisVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateServisVozilaActionPerformed
        // Update podataka
        try{
            String ID =  txt_idServisVozila.getText();
            int Vozilo = ComboBox_ServisVozila.getSelectedIndex();
            String DatumPredaje = ((JTextField) DateChooserDatumVozilaServis.getDateEditor().getUiComponent()).getText();
            String Kilometraza = txt_KilometrazaVozilaServis.getText();
            String OpisServisaVozila = txt_OpisServisVozila.getText();
            int Zaposleni = jComboBox_ZaposleniServisVozila.getSelectedIndex();

            String sql="update servis_vozila set id='"+ID+"',idVozila='"+Vozilo+"',Datum_Predaje='"+DatumPredaje+"',Kilometraza='"+Kilometraza+"',Opis='"+OpisServisaVozila+"',idKorisnik='"+Zaposleni+"' where id='"+ID+"' ";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!","Ažuriraj Podatke",JOptionPane.OK_OPTION);

            Update_ServisVozila();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateServisVozilaActionPerformed

    private void Button_stampaServisVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaServisVozilaActionPerformed
        // Kreiranje izveštaja o Servisu Vozila
        try {
            String izvestaj ="Izvestaji\\ServisVozilaIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaServisVozilaActionPerformed

    private void Button_refreshServisVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshServisVozilaActionPerformed
        // Ponovna popuna tabele podacima
        Update_ServisVozila();
    }//GEN-LAST:event_Button_refreshServisVozilaActionPerformed

    private void Table_DnevnoKretanjeVozilaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_DnevnoKretanjeVozilaMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{

            int row = Table_DnevnoKretanjeVozila.getSelectedRow();
            String Table_click = (Table_DnevnoKretanjeVozila.getModel().getValueAt(row, 0).toString());
            String sql = "select * from kretanje_vozila where id='" + Table_click + "' ";//Upit za popunu textBox na klik u tabeli
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            //popuna txt polja podacima iz tabele
            poljaServisVozilaDnevnaKretanja();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_DnevnoKretanjeVozilaMouseClicked

    private void Table_DnevnoKretanjeVozilaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_DnevnoKretanjeVozilaKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =Table_DnevnoKretanjeVozila.getSelectedRow();
                String Table_click=(Table_DnevnoKretanjeVozila.getModel().getValueAt(row, 0).toString());
                String sql="select * from kretanje_vozila where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                poljaServisVozilaDnevnaKretanja();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Table_DnevnoKretanjeVozilaKeyReleased

    private void txt_pretragaDnevnoKretanjeVozilaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pretragaDnevnoKretanjeVozilaKeyReleased
        //popuna txt polja podacima za pretragu prema Datumu Voznje
        try{
            String sql="select * from kretanje_vozila where Datum_Voznje=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaDnevnoKretanjeVozila.getText());
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            poljaServisVozilaDnevnaKretanja();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txt_pretragaDnevnoKretanjeVozilaKeyReleased

    private void Button_insertDnevnoKretanjeVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertDnevnoKretanjeVozilaActionPerformed
      // Unos Novih podataka u tabelu kretanje_vozila
      try {
            String sql = "Insert into kretanje_vozila (id,idVozila,Startna_Kilometraza,Krajnja_Kilometraza,idKorisnika,Potrosnja,Datum_Voznje,Opis,Broj_Naloga) values (?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_IDDnevnoKretanjeVozila.getText());
            pst.setInt(2, jComboBox_VoziloDnevnoKretanjeVozila.getSelectedIndex());
            pst.setString(3, txt_PocetnaKilometrazaDnevnoKretanjeVozila.getText());
            pst.setString(4, txt_KrajKmDnevnoKretanjeVozila.getText());
            pst.setInt(5, jComboBoxZaposleniiDnevnoKretanjeVozila.getSelectedIndex());
            pst.setString(6, txt_PotrosnjaDnevnoKretanjeVozila.getText());
            pst.setString(7, ((JTextField) DateChooser_DatumDnevnogKretanjaVozila.getDateEditor().getUiComponent()).getText());
            pst.setString(8, txt_opisDnevnogKretanjaVozila.getText());
            pst.setString(9, txt_brNalogaDnevnogKretanjaVozila.getText());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!", "Sačuvaj Podatke", JOptionPane.OK_OPTION);

            Update_ServisKretanjeVozila();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertDnevnoKretanjeVozilaActionPerformed

    private void Button_clearDnevnoKretanjeVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearDnevnoKretanjeVozilaActionPerformed
        //Ciscenje polja za unos u panelu Dnevno Kretanje Vozila
        try{
            txt_IDDnevnoKretanjeVozila.setText("");
            jComboBox_VoziloDnevnoKretanjeVozila.setSelectedIndex(0);
            txt_PocetnaKilometrazaDnevnoKretanjeVozila.setText("");
            txt_KrajKmDnevnoKretanjeVozila.setText("");
            jComboBoxZaposleniiDnevnoKretanjeVozila.setSelectedIndex(0);
            txt_PotrosnjaDnevnoKretanjeVozila.setText("");
            ((JTextField) DateChooser_DatumDnevnogKretanjaVozila.getDateEditor().getUiComponent()).setText("");
            txt_opisDnevnogKretanjaVozila.setText("");
            txt_brNalogaDnevnogKretanjaVozila.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_Button_clearDnevnoKretanjeVozilaActionPerformed

    private void Button_updateDnevnoKretanjeVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateDnevnoKretanjeVozilaActionPerformed
        // Azuriranje podataka u tabeli kretanje_vozila
        try {
            String ID = txt_IDDnevnoKretanjeVozila.getText();
            int Vozilo = jComboBox_VoziloDnevnoKretanjeVozila.getSelectedIndex();
            String KmPocetna = txt_PocetnaKilometrazaDnevnoKretanjeVozila.getText();
            String KmKrajnja = txt_KrajKmDnevnoKretanjeVozila.getText();
            int Zaposleni = jComboBoxZaposleniiDnevnoKretanjeVozila.getSelectedIndex();
            String Potrosnja = txt_PotrosnjaDnevnoKretanjeVozila.getText();
            String Datum = ((JTextField) DateChooser_DatumDnevnogKretanjaVozila.getDateEditor().getUiComponent()).getText();
            String Opis = txt_opisDnevnogKretanjaVozila.getText();
            String Nalog = txt_brNalogaDnevnogKretanjaVozila.getText();
            
            String sql = "update kretanje_vozila set id='" + ID + "',idVozila='" + Vozilo + "',Startna_Kilometraza='" + KmPocetna + "',Krajnja_Kilometraza='" + KmKrajnja + "' ,idKorisnika='" + Zaposleni + "' ,Potrosnja='" + Potrosnja + "',Datum_Voznje='" + Datum + "', Opis='" + Opis + "', Broj_Naloga='" + Nalog + "' where id='" + ID + "' ";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!", "Ažuriraj Podatke", JOptionPane.OK_OPTION);

            Update_ServisKretanjeVozila();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_updateDnevnoKretanjeVozilaActionPerformed

    private void Button_deleteDnevnoKretanjeVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteDnevnoKretanjeVozilaActionPerformed
        // Brisanje podataka iz tabele kretanje_vozila
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from kretanje_vozila where id =?";
            try{

                pst=conn.prepareStatement(sql);
                pst.setString(1,txt_IDDnevnoKretanjeVozila.getText());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);

                Update_ServisKretanjeVozila();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteDnevnoKretanjeVozilaActionPerformed

    private void Button_refreshDnevnoKretanjeVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshDnevnoKretanjeVozilaActionPerformed
        // Ponovno ucitavanje podataka u tabelu Dnevno Kretanje Vozila
        Update_ServisKretanjeVozila();
    }//GEN-LAST:event_Button_refreshDnevnoKretanjeVozilaActionPerformed

    private void Button_stampaDnevnoKretanjeVozilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaDnevnoKretanjeVozilaActionPerformed
        // Kreiranje izveštaja o Dnevnom Kretanju Vozila
        try {
            String izvestaj ="Izvestaji\\DnevnaKretanjaVozilaIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaDnevnoKretanjeVozilaActionPerformed

    private void jButton_izvestajLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_izvestajLoginActionPerformed
        // Kreiranje izveštaja o ulazu na sitem
        try {
            String izvestaj ="Izvestaji\\LoginIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton_izvestajLoginActionPerformed

    private void jButton_obnovaLozinkeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_obnovaLozinkeActionPerformed
        // Kreiranje izveštaja o zahtevima za obnovu lozinke
        try {
            String izvestaj ="Izvestaji\\ObnovaLozinkeIzvestaj.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(izvestaj);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton_obnovaLozinkeActionPerformed

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
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_Exit;
    private javax.swing.JButton Button_clear;
    private javax.swing.JButton Button_clearDnevnoKretanjeVozila;
    private javax.swing.JButton Button_clearKat;
    private javax.swing.JButton Button_clearKatVozila;
    private javax.swing.JButton Button_clearOT;
    private javax.swing.JButton Button_clearServisVozila;
    private javax.swing.JButton Button_clearUloge;
    private javax.swing.JButton Button_clearUsers;
    private javax.swing.JButton Button_clearVozila;
    private javax.swing.JButton Button_delete;
    private javax.swing.JButton Button_deleteDnevnoKretanjeVozila;
    private javax.swing.JButton Button_deleteKat;
    private javax.swing.JButton Button_deleteKatVozila;
    private javax.swing.JButton Button_deleteOT;
    private javax.swing.JButton Button_deleteServisVozila;
    private javax.swing.JButton Button_deleteUloge;
    private javax.swing.JButton Button_deleteUsers;
    private javax.swing.JButton Button_deleteVozila;
    private javax.swing.JButton Button_insert;
    private javax.swing.JButton Button_insertDnevnoKretanjeVozila;
    private javax.swing.JButton Button_insertKat;
    private javax.swing.JButton Button_insertKatVozila;
    private javax.swing.JButton Button_insertOT;
    private javax.swing.JButton Button_insertServisVozila;
    private javax.swing.JButton Button_insertUsers;
    private javax.swing.JButton Button_insertVozila;
    private javax.swing.JButton Button_internet;
    private javax.swing.JButton Button_proveraKonekcijaBaze;
    private javax.swing.JButton Button_refreshDnevnoKretanjeVozila;
    private javax.swing.JButton Button_refreshDoc;
    private javax.swing.JButton Button_refreshKat;
    private javax.swing.JButton Button_refreshKatVozila;
    private javax.swing.JButton Button_refreshKorisnici;
    private javax.swing.JButton Button_refreshOglasnaTabla;
    private javax.swing.JButton Button_refreshServisVozila;
    private javax.swing.JButton Button_refreshUloge;
    private javax.swing.JButton Button_refreshVozila;
    private javax.swing.JButton Button_singout;
    private javax.swing.JButton Button_stampaDnevnoKretanjeVozila;
    private javax.swing.JButton Button_stampaDoc;
    private javax.swing.JButton Button_stampaKat;
    private javax.swing.JButton Button_stampaKatVozila;
    private javax.swing.JButton Button_stampaKorisnici;
    private javax.swing.JButton Button_stampaOglasnaTabla;
    private javax.swing.JButton Button_stampaServisVozila;
    private javax.swing.JButton Button_stampaUloge;
    private javax.swing.JButton Button_stampaVozila;
    private javax.swing.JButton Button_unosDokumenta;
    private javax.swing.JButton Button_update;
    private javax.swing.JButton Button_updateDnevnoKretanjeVozila;
    private javax.swing.JButton Button_updateKat;
    private javax.swing.JButton Button_updateKatVozila;
    private javax.swing.JButton Button_updateOT;
    private javax.swing.JButton Button_updateServisVozila;
    private javax.swing.JButton Button_updateUloge;
    private javax.swing.JButton Button_updateUsers;
    private javax.swing.JButton Button_updateVozila;
    private javax.swing.JComboBox ComboBox_KategorijaDokumenta;
    private javax.swing.JComboBox ComboBox_KategorijaVozila;
    private javax.swing.JComboBox ComboBox_ServisVozila;
    private javax.swing.JComboBox ComboBox_Users;
    private com.toedter.calendar.JDateChooser DateChooserDatumVozilaServis;
    private com.toedter.calendar.JDateChooser DateChooser_DatumDnevnogKretanjaVozila;
    private javax.swing.JLabel LabelOpisKatVozila;
    private javax.swing.JLabel LabelOpisOT;
    private javax.swing.JLabel Label_Broj;
    private javax.swing.JLabel Label_DatumPredajeVozilaServis;
    private javax.swing.JLabel Label_Dokument;
    private javax.swing.JLabel Label_ID;
    private javax.swing.JLabel Label_IDKat;
    private javax.swing.JLabel Label_IDServisVozila;
    private javax.swing.JLabel Label_IDVozila;
    private javax.swing.JLabel Label_ID_Kategorije;
    private javax.swing.JLabel Label_ID_KategorijeVozila;
    private javax.swing.JLabel Label_ID_VozilaServis;
    private javax.swing.JLabel Label_KilometrazaVozilaServis;
    private javax.swing.JLabel Label_MarkaVozila;
    private javax.swing.JLabel Label_Naziv;
    private javax.swing.JLabel Label_NazivKat;
    private javax.swing.JLabel Label_OTid;
    private javax.swing.JLabel Label_Opis;
    private javax.swing.JLabel Label_OpisKat;
    private javax.swing.JLabel Label_OpisServisVozila;
    private javax.swing.JLabel Label_OpisVozila;
    private javax.swing.JLabel Label_TipVozila;
    private javax.swing.JLabel Label_idKatVozila;
    private javax.swing.JLabel Label_nazivKatVozila;
    private javax.swing.JLabel Label_nazivOT;
    private javax.swing.JLabel Label_nazivUloge;
    private javax.swing.JLabel Label_ulogaid;
    private javax.swing.JLabel LabelulogeOpis;
    private javax.swing.JMenuBar MenuBar_adminMeni;
    private javax.swing.JMenuItem MenuItem_Exit;
    private javax.swing.JMenuItem MenuItem_LogOut;
    private javax.swing.JMenuItem MenuItem_baza;
    private javax.swing.JMenuItem MenuItem_internet;
    private javax.swing.JMenu Menu_help;
    private javax.swing.JMenu Menu_opste;
    private javax.swing.JPanel Panel_AdminDnevnoKretanjeVozila;
    private javax.swing.JPanel Panel_AdminDoc;
    private javax.swing.JPanel Panel_AdminDocKat;
    private javax.swing.JPanel Panel_AdminKategorijaDoc;
    private javax.swing.JPanel Panel_AdminKontrolaPristupa;
    private javax.swing.JPanel Panel_AdminKorisnici;
    private javax.swing.JPanel Panel_AdminServisVozila;
    private javax.swing.JPanel Panel_AdminUloge;
    private javax.swing.JPanel Panel_AdminUsers;
    private javax.swing.JPanel Panel_AdminVozila;
    private javax.swing.JPanel Panel_AdministracijaDoc;
    private javax.swing.JPanel Panel_AdministracijaKatVozila;
    private javax.swing.JPanel Panel_AdministracijaOglasneTable;
    private javax.swing.JPanel Panel_AdministracijaUlogezaLogin;
    private javax.swing.JPanel Panel_FuncButton;
    private javax.swing.JPanel Panel_FuncButtonKat;
    private javax.swing.JPanel Panel_FuncButtonServisVozila;
    private javax.swing.JPanel Panel_FuncButtonVozila;
    private javax.swing.JPanel Panel_Pretraga;
    private javax.swing.JPanel Panel_PretragaDnevnoKretanjeVozila;
    private javax.swing.JPanel Panel_PretragaKorisnika;
    private javax.swing.JPanel Panel_PretragaServisaVozila;
    private javax.swing.JPanel Panel_PretragaVozila;
    private javax.swing.JPanel Panel_Vozila;
    private javax.swing.JPanel Panel_Zaposleni;
    private javax.swing.JPanel Panel_upravljanjeDokumentima;
    private javax.swing.JPanel Panel_vestiObavestenja;
    private javax.swing.JScrollPane ScrollPane_OglasnaTabla;
    private javax.swing.JScrollPane ScrollPane_OglasnaTabla1;
    private javax.swing.JScrollPane ScrollPane_OpisOT;
    private javax.swing.JScrollPane ScrollPane_OpisOT1;
    private javax.swing.JTabbedPane TabbedPane_Administracija;
    private javax.swing.JTabbedPane TabbedPane_Doc;
    private javax.swing.JTabbedPane TabbedPane_Korisnici;
    private javax.swing.JTable Table_DnevnoKretanjeVozila;
    private javax.swing.JTable Table_KatDocument;
    private javax.swing.JTable Table_KatVozila;
    private javax.swing.JTable Table_KontrolaPristupa;
    private javax.swing.JTable Table_OglasnaTabla;
    private javax.swing.JTable Table_ServisVozila;
    private javax.swing.JTable Table_UlogezaLogin;
    private javax.swing.JTable Table_Users;
    private javax.swing.JTable Table_Vozila;
    private javax.swing.JTable Table_document;
    private javax.swing.JTextArea TextArea_opisKatVozila;
    private javax.swing.JTextArea TextArea_opisOT;
    private javax.swing.JTextArea TextArea_opisUloge;
    private javax.swing.JTextField TextField_idKatVozila;
    private javax.swing.JTextField TextField_idOT;
    private javax.swing.JTextField TextField_idUloge;
    private javax.swing.JTextField TextField_nazivKatVozila;
    private javax.swing.JTextField TextField_nazivOT;
    private javax.swing.JTextField TextField_nazivUloge;
    private javax.swing.JToolBar ToolBar_OpsteFunkcije;
    private javax.swing.JButton jButton_izvestajLogin;
    private javax.swing.JButton jButton_obnovaLozinke;
    private javax.swing.JComboBox jComboBoxZaposleniiDnevnoKretanjeVozila;
    private javax.swing.JComboBox jComboBox_VoziloDnevnoKretanjeVozila;
    private javax.swing.JComboBox jComboBox_ZaposleniServisVozila;
    private javax.swing.JLabel jLabelPozdravnaPoruka;
    public static javax.swing.JLabel jLabelUser;
    private javax.swing.JLabel jLabel_DatumDnevnoKretanjeVozila;
    private javax.swing.JLabel jLabel_KrajKmDnevnoKretanjeVozila;
    private javax.swing.JLabel jLabel_OpisDnevnogKretanjaVozila;
    private javax.swing.JLabel jLabel_PotrosnjaDnevnoKretanjeVozila;
    private javax.swing.JLabel jLabel_PotrosnjaVozila;
    private javax.swing.JLabel jLabel_RegVozila;
    private javax.swing.JLabel jLabel_ZaposleniDnevnoKretanjeVozila;
    private javax.swing.JLabel jLabel_ZaposleniServisVozila;
    private javax.swing.JLabel jLabel_brNalogaDnevnogKretanjaVozila;
    private javax.swing.JLabel jLabel_emailZaposlenog;
    private javax.swing.JLabel jLabel_idDnevnoKretanjeVozila;
    private javax.swing.JLabel jLabel_idZaposlenog;
    private javax.swing.JLabel jLabel_imeZaposlenog;
    private javax.swing.JLabel jLabel_kojeVoziloDnevnoKretanjeVozila;
    private javax.swing.JLabel jLabel_korisnickoImeZaposlenog;
    private javax.swing.JLabel jLabel_mobTelZaposlenog;
    private javax.swing.JLabel jLabel_opisZaposlenog;
    private javax.swing.JLabel jLabel_passZaposlenog;
    private javax.swing.JLabel jLabel_prezimeZaposlenog;
    private javax.swing.JLabel jLabel_startKmDnevnoKretanjeVozila;
    private javax.swing.JLabel jLabel_telPosaoZaposlenog;
    private javax.swing.JLabel jLabel_ulogeLogin;
    private javax.swing.JPanel jPanelKatVozila;
    private javax.swing.JPanel jPanelKretanjaVozila;
    private javax.swing.JPanel jPanelPodaciVozila;
    private javax.swing.JPanel jPanelServisVozila;
    private javax.swing.JPanel jPanel_ObnovaLozinke;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollPaneServisVozila;
    private javax.swing.JScrollPane jScrollPaneVozila;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPaneVozila;
    private javax.swing.JTable jTable_ObnovaLozinke;
    private javax.swing.JTextField txt_Broj;
    private javax.swing.JTextField txt_Dokument;
    private javax.swing.JTextField txt_EmailUser;
    private javax.swing.JTextField txt_ID;
    private javax.swing.JTextField txt_IDDnevnoKretanjeVozila;
    private javax.swing.JTextField txt_IDKat;
    private javax.swing.JTextField txt_IDUsers;
    private javax.swing.JTextField txt_JobPhon;
    private javax.swing.JTextField txt_KilometrazaVozilaServis;
    private javax.swing.JTextField txt_KrajKmDnevnoKretanjeVozila;
    private javax.swing.JTextField txt_LastName;
    private javax.swing.JTextField txt_MarkaVozila;
    private javax.swing.JTextField txt_Name;
    private javax.swing.JTextField txt_Naziv;
    private javax.swing.JTextField txt_NazivKat;
    private javax.swing.JTextArea txt_Opis;
    private javax.swing.JTextArea txt_OpisKat;
    private javax.swing.JTextArea txt_OpisServisVozila;
    private javax.swing.JTextArea txt_OpisVozila;
    private javax.swing.JTextField txt_Pass;
    private javax.swing.JTextField txt_PocetnaKilometrazaDnevnoKretanjeVozila;
    private javax.swing.JTextField txt_PotrosnjaDnevnoKretanjeVozila;
    private javax.swing.JTextField txt_PotrosnjsVozila;
    private javax.swing.JTextField txt_RegVozila;
    private javax.swing.JTextField txt_TelMob;
    private javax.swing.JTextField txt_TipVozila;
    private javax.swing.JTextField txt_UserName;
    private javax.swing.JTextField txt_brNalogaDnevnogKretanjaVozila;
    private javax.swing.JTextField txt_idServisVozila;
    private javax.swing.JTextField txt_idVozila;
    private javax.swing.JTextArea txt_opisDnevnogKretanjaVozila;
    private javax.swing.JTextArea txt_opisUser;
    private javax.swing.JTextField txt_pretraga;
    private javax.swing.JTextField txt_pretragaDnevnoKretanjeVozila;
    private javax.swing.JTextField txt_pretragaServisVozila;
    private javax.swing.JTextField txt_pretragaUsers;
    private javax.swing.JTextField txt_pretragaVozila;
    // End of variables declaration//GEN-END:variables
//variable za dokument
    //private Dokument format=null;
//globalan naziv za polje za odabir dokumenta
    String filename=null;
//druge globalne promenjive koje su mi potrebne da bi se dokument sacuvao
    int s=0;
    byte[] Dokumenti=null;
    
}