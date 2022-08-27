import java.awt.Toolkit;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.*;
import javax.swing.*;
import java.util.Date;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Bojan Pavlović
 */
public class ProbniLet extends javax.swing.JFrame {
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    /**
     * Creates new form ProbniLet
     */
    public ProbniLet() {
       initComponents();
       conn=Data.javaconnect.ConnecrDb();// Preusmerenje na klasu za konekciju na bazu
       setIcon();//Unos ikonice za prozor
       ComboBox_RegOznakeVazduhoplova();//Popuna kombo box sa registraciom vazduhoplova
       ComboBox_RadniNalog();//popuna kombo box sa brojem radnog naloga
       Update_ProbniLet();//Ucitavanje podataka u tabelu
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
    //metod za popuni podataka kada se izabere kombobox brojRadnogNaloga
    public void popunaPoljaRadniNalog(){
        //Popuniti Polja kada se izabere nesto iz combobox
        String tmp =(String)jComboBoxBRNaloga.getSelectedItem();
        String sql="select * from radni_nalog where Broj_Naloga=?";     
            try{
                pst = conn.prepareStatement(sql);
                pst.setString(1, tmp);
                rs = pst.executeQuery();
                if(rs.next()){
                    
                        String add1=rs.getString("Predmet_Radova");
                        jTextAreaPredmetRadovaPLeta.setText(add1);

                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }
    //metod za popuni podataka kada se izabere kombobox regOznake
    public void popunaPoljaOVazduhoplovu(){
        //Popuniti Polja kada se izabere nesto iz combobox
        String tmp =(String)jComboBoxRGOznake.getSelectedItem();
        String sql="select * from vazduhoplov where Registarska_Oznaka=?";     
            try{
                pst = conn.prepareStatement(sql);
                pst.setString(1, tmp);
                rs = pst.executeQuery();
                if(rs.next()){
                        String add1=rs.getString("Proizvodjac_Tip");
                        jTextFieldProizvodjacVazduhoplov.setText(add1);

                        String add2=rs.getString("Seriski_Broj");
                        jTextFieldSerijskiBrojVazduhoplovaPLet.setText(add2);

                        String add3=rs.getString("Nalet");
                        jTextFieldNaletVazduhoplovaPLet.setText(add3);

                        String add4=rs.getString("Casova_Leta");
                        jTextFieldCasovaLetaPLet.setText(add4);

                        String add5=rs.getString("Ciklusa");
                        jTextFieldCiklusaVazduhoplovaPLeta.setText(add5);

                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }
    //polja probni let
    public void poljaProbniLet(){
        try{
            if(rs.next()){
                    String add1=rs.getString("id");
                    jTextField_idProbnogLeta.setText(add1);

                    int add2=rs.getInt("idVazduhoplova");
                    jComboBoxRGOznake.setSelectedIndex(add2);

                    int add3=rs.getInt("idRadniNalog");
                    jComboBoxBRNaloga.setSelectedIndex(add3);
                    
                    String add4=rs.getString("Pretpoletni_Pregled_Izvrsio");
                    jTextField_pppIzvrsio.setText(add4);
                    
                    String add5=rs.getString("Aerodrom");
                    jTextFieldAerodromPLeta.setText(add5);
                    
                    java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date add6 = sdf.parse(rs.getString("Datum"));
                    jDateChooser_DatumProbnogLeta.setDate(add6);
                    
                    String add7=rs.getString("Vreme_Pocetka");
                    jTextFieldPocetakPLeta.setText(add7);
                    
                    String add8=rs.getString("Vreme_Zavrsetka");
                    jTextFieldZavrsetakPLeta.setText(add8);
                    
                    String add9=rs.getString("Pritisak_Vazduha");
                    jTextFieldPritisakVazduhaPLeta.setText(add9);
                    
                    String add10=rs.getString("Temperatura_Vazduha");
                    jTextFieldTemperaturaVazduhaPLeta.setText(add10);
                    
                    String add11=rs.getString("Visina_Leta");
                    jTextFieldVisinaLetaPLeta.setText(add11);
                    
                    String add12=rs.getString("Startovanje");
                    jTextFieldStartovanjePLeta.setText(add12);
                    
                    String add13=rs.getString("Zagrevanje");
                    jTextFieldZagrevanjePLeta.setText(add13);
                    
                    String add14=rs.getString("Grejanje_Karburatora");
                    jTextFieldGKarburatoraPLeta.setText(add14);
                    
                    String add15=rs.getString("Komanda_Gasa");
                    jTextFielKomandaGasaPLeta.setText(add15);
                    
                    String add16=rs.getString("Komanda_Smese");
                    jTextFieldKomandaSemsePLeta.setText(add16);
                    
                    String add17=rs.getString("Rad_Slavina_Goriva");
                    jTextFieldKRSGPLeta.setText(add17);
                    
                    String add18=rs.getString("Parking_Kocnica");
                    jTextFieldParkingKocnicaPLeta.setText(add18);
                    
                    String add19=rs.getString("Punjenje_Generatora");
                    jTextFieldPunjenjeGPLeta.setText(add19);
                    
                    String add20=rs.getString("Temp_Uqa");
                    jTextFieldUQAPLeta.setText(add20);
                    
                    String add21=rs.getString("Prit_Uqa");
                    jTextFieldPritUQAPLeta.setText(add21);
                    
                    String add22=rs.getString("Prit_Goriva");
                    jTextFieldPritGorivaPLeta.setText(add22);
                    
                    String add23=rs.getString("Protok_Goriva");
                    jTextFieldProtokGorivaPLeta.setText(add23);
                    
                    String add24=rs.getString("TGC");
                    jTextFieldTGCPLeta.setText(add24);
                    
                    String add25=rs.getString("Levi");
                    jTextFieldPMLeviPLeta.setText(add25);
                    
                    String add26=rs.getString("Desni");
                    jTextFieldPMDesniPLeta.setText(add26);
                    
                    String add27=rs.getString("Razlika");
                    jTextFieldPMROPLeta.setText(add27);
                    
                    String add28=rs.getString("Obrtaj_Promene_Koraka_Elise_Mali_Korak");
                    jTextFieldMaliKorakPLeta.setText(add28);
                    
                    String add29=rs.getString("Obrtaj_Promene_Koraka_Elise_Veliki_Korak");
                    jTextFieldVelikiKorakPLeta.setText(add29);
                    
                    String add30=rs.getString("Puna_Snaga_br_Obrta");
                    jTextFieldBRObrtaPLeta.setText(add30);
                    
                    String add31=rs.getString("Puna_Snaga_Mano_Vakum");
                    jTextFieldManoVakumPLeta.setText(add31);
                    
                    String add32=rs.getString("Puna_Snaga_Protok_Goriva");
                    jTextFieldProtokGorivaPleta.setText(add32);
                    
                    String add33=rs.getString("Puna_Snaga_Provera_Smese");
                    jTextFieldPSPleta.setText(add33);
                    
                    String add34=rs.getString("Puna_Snaga_Zaluzine_Motora");
                    jTextFieldZaluzineMotoraPLeta.setText(add34);
                    
                    String add35=rs.getString("Puna_Snaga_Hladnjak");
                    jTextFieldHladnjakaPLeta.setText(add35);
                    
                    String add36=rs.getString("Voznja_po_Zemlji");
                    jTextFieldVoznjaPoZemljPLeta.setText(add36);
                    
                    String add37=rs.getString("Kocnice");
                    jTextFieldKocnicePLeta.setText(add37);
                    
                    String add38=rs.getString("Upravljivost_u_Poletanju");
                    jTextFieldUpravljivostUPoletanjuPLeta.setText(add38);
                    
                    String add39=rs.getString("Stajni_Trap");
                    jTextFielStajniTrapPLeta.setText(add39);
                    
                    String add40=rs.getString("Rad_Trimera");
                    jTextFieldRadTrimeraPLeta.setText(add40);
                    
                    String add41=rs.getString("Rad_Komandi_Leta_u_Letu");
                    jTextFieldRKLULPLeta.setText(add41);
                    
                    String add42=rs.getString("Rad_Zakrilaca");
                    jTextFieldRadZakrilcaPLeta.setText(add42);
                    
                    String add43=rs.getString("Indikacija_Zakrilaca");
                    jTextFieldIndikacijaZakrilcaPLeta.setText(add43);
                    
                    String add44=rs.getString("Svetla_Upozorenja");
                    jTextFieldSvetlaUpozorenjaPLeta.setText(add44);
                    
                    String add45=rs.getString("Radio_Stanica");
                    jTextFieldRadioStanicaPLeta.setText(add45);
                    
                    String add46=rs.getString("Vor");
                    jTextFieldVORPLeta.setText(add46);
                    
                    String add47=rs.getString("Adf");
                    jTextFieldADFPLeta.setText(add47);
                    
                    String add48=rs.getString("Interfon");
                    jTextFieldInterfonPLeta.setText(add48);
                    
                    String add49=rs.getString("Pokazivanje_Istrumenata");
                    jTextFieldPokazivanjeIstrumenataPLeta.setText(add49);
                    
                    String add50=rs.getString("Horizontalni_Let");
                    jTextFieldHorizontalniPLeta.setText(add50);
                    
                    String add51=rs.getString("Prevuceni_Let_Brzina_Sloma_Uzgona");
                    jTextFieldPrevuceniLetOPLeta.setText(add51);
                    
                    String add52=rs.getString("Levi_Desni_Ostri_Zaokret");
                    jTextFieldOstriZaokretPLeta.setText(add52);
                    
                    String add53=rs.getString("Prilaz_i_Sletanje");
                    jTextField_sletanjePleta.setText(add53);
                    
                    String add54=rs.getString("Opsta_Zapazanja_Pilota");
                    jTextAreaZapazanjaPLeta.setText(add54);
                    
                    String add55=rs.getString("Odobreno_Resenjem_DCV_broj");
                    jTextFieldOdobrenoPLeta.setText(add55);
                    
                    String add56=rs.getString("Pilot");
                    jTextFieldPilotPLeta.setText(add56);
                    
                    String add57=rs.getString("Komanda_Elise");
                    jTextFieldKomandaKorakaElisePLeta.setText(add57);
                    
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //popuna boxa za vozila u Panelu servis kretanje vozila
    private void ComboBox_RadniNalog(){
    
        try{
        String sql="select * from radni_nalog";//Upit za popunu radnog naloga
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
   
            while(rs.next()){
            
                String Naziv=rs.getString("Broj_Naloga");
                jComboBoxBRNaloga.addItem(Naziv);
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
                jComboBoxRGOznake.addItem(Naziv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Popuna tabele Probni Let
    private void Update_ProbniLet(){
        
        try{
        String sql="select izvestaj_sa_probnog_leta.id,vazduhoplov.Registarska_Oznaka,izvestaj_sa_probnog_leta.Aerodrom,izvestaj_sa_probnog_leta.Datum,izvestaj_sa_probnog_leta.Pretpoletni_Pregled_Izvrsio, izvestaj_sa_probnog_leta.Opsta_Zapazanja_Pilota,izvestaj_sa_probnog_leta.Odobreno_Resenjem_DCV_broj,izvestaj_sa_probnog_leta.Pilot \n" +
                   "from izvestaj_sa_probnog_leta inner join vazduhoplov on izvestaj_sa_probnog_leta.idVazduhoplova = vazduhoplov.id order by izvestaj_sa_probnog_leta.id desc";//Upit za popunu tabele
            
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
        jTableProbniLet.setModel(DbUtils.resultSetToTableModel(rs));
                
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

        jTabbedPaneIzvestajProbniLet = new javax.swing.JTabbedPane();
        jPanelOsnovaPLeta = new javax.swing.JPanel();
        jScrollPaneTable = new javax.swing.JScrollPane();
        jTableProbniLet = new javax.swing.JTable();
        jTabbedPaneIZVESTAJ_SA_PROBNOG_LETA = new javax.swing.JTabbedPane();
        jScrollPanePodaciVazduhoplova = new javax.swing.JScrollPane();
        jPanelPodaciVazduhoplov = new javax.swing.JPanel();
        jLabelAerodromPLeta = new javax.swing.JLabel();
        jTextFieldAerodromPLeta = new javax.swing.JTextField();
        jLabelDatumPLeta = new javax.swing.JLabel();
        jLabelPocetakPLeta = new javax.swing.JLabel();
        jTextFieldPocetakPLeta = new javax.swing.JTextField();
        jLabelZavrsetakPLeta = new javax.swing.JLabel();
        jTextFieldZavrsetakPLeta = new javax.swing.JTextField();
        jLabelPritisakVazduhaPLeta = new javax.swing.JLabel();
        jTextFieldPritisakVazduhaPLeta = new javax.swing.JTextField();
        jLabelTemperaturaVazduhaPLeta = new javax.swing.JLabel();
        jTextFieldTemperaturaVazduhaPLeta = new javax.swing.JTextField();
        jLabelVisinaLetaPLeta = new javax.swing.JLabel();
        jTextFieldVisinaLetaPLeta = new javax.swing.JTextField();
        jLabelPPPregledIzvrsioPLeta = new javax.swing.JLabel();
        jDateChooser_DatumProbnogLeta = new com.toedter.calendar.JDateChooser();
        jLabel_idProbnogLeta = new javax.swing.JLabel();
        jTextField_idProbnogLeta = new javax.swing.JTextField();
        jTextField_pppIzvrsio = new javax.swing.JTextField();
        jPanelNeMenjati = new javax.swing.JPanel();
        jLabelProizvodjacTip = new javax.swing.JLabel();
        jLabelSerijskiBroj = new javax.swing.JLabel();
        jLabelBrNaloga = new javax.swing.JLabel();
        jTextFieldNaletVazduhoplovaPLet = new javax.swing.JTextField();
        jComboBoxBRNaloga = new javax.swing.JComboBox();
        jComboBoxRGOznake = new javax.swing.JComboBox();
        jTextFieldCiklusaVazduhoplovaPLeta = new javax.swing.JTextField();
        jTextFieldSerijskiBrojVazduhoplovaPLet = new javax.swing.JTextField();
        jTextFieldCasovaLetaPLet = new javax.swing.JTextField();
        jLabelNaletVazduhoplovaPLet = new javax.swing.JLabel();
        jTextFieldProizvodjacVazduhoplov = new javax.swing.JTextField();
        jLabelCiklusaVazduhoplovaPLet = new javax.swing.JLabel();
        jLabelCasovaLetaPLet = new javax.swing.JLabel();
        jLabelRGOznake = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaPredmetRadovaPLeta = new javax.swing.JTextArea();
        jLabelPredmetRadovaPLeta = new javax.swing.JLabel();
        jScrollPaneMotor = new javax.swing.JScrollPane();
        jPanelMotor = new javax.swing.JPanel();
        jLabelStartovanjePLeta = new javax.swing.JLabel();
        jLabelZagrevanjePLeta = new javax.swing.JLabel();
        jLabelGKarburatoraPLeta = new javax.swing.JLabel();
        jTextFieldStartovanjePLeta = new javax.swing.JTextField();
        jTextFieldGKarburatoraPLeta = new javax.swing.JTextField();
        jLabelKomandaGasaPLeta = new javax.swing.JLabel();
        jTextFielKomandaGasaPLeta = new javax.swing.JTextField();
        jLabelKomandaSmesePLeta = new javax.swing.JLabel();
        jTextFieldKomandaSemsePLeta = new javax.swing.JTextField();
        jLabelKRSGPLeta = new javax.swing.JLabel();
        jTextFieldKRSGPLeta = new javax.swing.JTextField();
        jLabelParkingKocnicaPLeta = new javax.swing.JLabel();
        jLabelPMPLeta = new javax.swing.JLabel();
        jLabelPMLeviPLeta = new javax.swing.JLabel();
        jTextFieldPMLeviPLeta = new javax.swing.JTextField();
        jLabelPMDesniPLeta = new javax.swing.JLabel();
        jTextFieldPMDesniPLeta = new javax.swing.JTextField();
        jLabelaPMROPLeta = new javax.swing.JLabel();
        jTextFieldPMROPLeta = new javax.swing.JTextField();
        jLabelPKEPLeta = new javax.swing.JLabel();
        jLabelMaliKPLeta = new javax.swing.JLabel();
        jTextFieldMaliKorakPLeta = new javax.swing.JTextField();
        jLabelVelikiKorakPLeta = new javax.swing.JLabel();
        jTextFieldVelikiKorakPLeta = new javax.swing.JTextField();
        jLabelPunaSnagaPLeta = new javax.swing.JLabel();
        jTextFieldZagrevanjePLeta = new javax.swing.JTextField();
        jTextFieldParkingKocnicaPLeta = new javax.swing.JTextField();
        jLabelPunjenjeGPLeta = new javax.swing.JLabel();
        jTextFieldPunjenjeGPLeta = new javax.swing.JTextField();
        jLabelUQUPLeta = new javax.swing.JLabel();
        jTextFieldUQAPLeta = new javax.swing.JTextField();
        jLabelPritUQAPLeta = new javax.swing.JLabel();
        jTextFieldPritUQAPLeta = new javax.swing.JTextField();
        jLabelPritGorivaPLeta = new javax.swing.JLabel();
        jTextFieldPritGorivaPLeta = new javax.swing.JTextField();
        jLabelProtokGorivaPLeta = new javax.swing.JLabel();
        jTextFieldProtokGorivaPLeta = new javax.swing.JTextField();
        jLabelTgcPLeta = new javax.swing.JLabel();
        jTextFieldTGCPLeta = new javax.swing.JTextField();
        jLabelBRObrtaPLeta = new javax.swing.JLabel();
        jTextFieldBRObrtaPLeta = new javax.swing.JTextField();
        jLabelManaoVakumPLeta = new javax.swing.JLabel();
        jTextFieldManoVakumPLeta = new javax.swing.JTextField();
        jLabelProtikGorivaPLeta = new javax.swing.JLabel();
        jTextFieldProtokGorivaPleta = new javax.swing.JTextField();
        jLabelProveraSmesePLetaS = new javax.swing.JLabel();
        jTextFieldPSPleta = new javax.swing.JTextField();
        jLabelZaluzineMotoraPLeta = new javax.swing.JLabel();
        jTextFieldZaluzineMotoraPLeta = new javax.swing.JTextField();
        jLabelHladnjakaPLeta = new javax.swing.JLabel();
        jTextFieldHladnjakaPLeta = new javax.swing.JTextField();
        jLabelKomandaKorakaElisePLeta = new javax.swing.JLabel();
        jTextFieldKomandaKorakaElisePLeta = new javax.swing.JTextField();
        jScrollPaneProverauLetu = new javax.swing.JScrollPane();
        jPanelProveraULetu = new javax.swing.JPanel();
        jLabelVoznjaPoZemljiPLeta = new javax.swing.JLabel();
        jLabelKocnicePLeta = new javax.swing.JLabel();
        jLabelGUPUPLeta = new javax.swing.JLabel();
        jTextFieldVoznjaPoZemljPLeta = new javax.swing.JTextField();
        jTextFieldUpravljivostUPoletanjuPLeta = new javax.swing.JTextField();
        jLabelStajniTrapPLeta = new javax.swing.JLabel();
        jTextFielStajniTrapPLeta = new javax.swing.JTextField();
        jLabelRadTrimeraPLeta = new javax.swing.JLabel();
        jTextFieldRadTrimeraPLeta = new javax.swing.JTextField();
        jLabelRKLULPLeta = new javax.swing.JLabel();
        jTextFieldRKLULPLeta = new javax.swing.JTextField();
        jLabelRadZakrilcaPLeta = new javax.swing.JLabel();
        jLabePokazivanjeIstrumenataPLeta = new javax.swing.JLabel();
        jLabelHorizontalniPLeta = new javax.swing.JLabel();
        jTextFieldPokazivanjeIstrumenataPLeta = new javax.swing.JTextField();
        jLabelPrevuceniPLeta = new javax.swing.JLabel();
        jTextFieldHorizontalniPLeta = new javax.swing.JTextField();
        jTextFieldPrevuceniLetOPLeta = new javax.swing.JTextField();
        jLabelOstriZaokretPLeta = new javax.swing.JLabel();
        jTextFieldOstriZaokretPLeta = new javax.swing.JTextField();
        jLabelZapazanjaPLeta = new javax.swing.JLabel();
        jTextFieldKocnicePLeta = new javax.swing.JTextField();
        jTextFieldRadZakrilcaPLeta = new javax.swing.JTextField();
        jLabelIndikacijaZakrilca = new javax.swing.JLabel();
        jTextFieldIndikacijaZakrilcaPLeta = new javax.swing.JTextField();
        jLabelSvetlaUpozorenjaPLeta = new javax.swing.JLabel();
        jTextFieldSvetlaUpozorenjaPLeta = new javax.swing.JTextField();
        jLabelRadioStanicaPLeta = new javax.swing.JLabel();
        jTextFieldRadioStanicaPLeta = new javax.swing.JTextField();
        jLabelVORPLeta = new javax.swing.JLabel();
        jTextFieldVORPLeta = new javax.swing.JTextField();
        jLabelADFPLeta = new javax.swing.JLabel();
        jTextFieldADFPLeta = new javax.swing.JTextField();
        jLabelInterfonPLeta = new javax.swing.JLabel();
        jTextFieldInterfonPLeta = new javax.swing.JTextField();
        jLabelOdobrenoPLeta = new javax.swing.JLabel();
        jTextFieldOdobrenoPLeta = new javax.swing.JTextField();
        jLabelPilotPLeta = new javax.swing.JLabel();
        jTextFieldPilotPLeta = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaZapazanjaPLeta = new javax.swing.JTextArea();
        jLabel_sletanje = new javax.swing.JLabel();
        jTextField_sletanjePleta = new javax.swing.JTextField();
        txt_pretragaProbniLet = new javax.swing.JTextField();
        Button_insertProbniLet = new javax.swing.JButton();
        Button_clearProbniLet = new javax.swing.JButton();
        Button_updateProbniLet = new javax.swing.JButton();
        Button_refreshProbniLet = new javax.swing.JButton();
        Button_stampaProbniLet = new javax.swing.JButton();
        Button_deleteProbniLet = new javax.swing.JButton();
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
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        MenuItem_LogOut = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        MenuItem_Exit = new javax.swing.JMenuItem();
        Menu_help = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Panel za Administraciju Probnog Leta");
        setResizable(false);

        jTabbedPaneIzvestajProbniLet.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jTableProbniLet.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableProbniLet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProbniLetMouseClicked(evt);
            }
        });
        jTableProbniLet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableProbniLetKeyReleased(evt);
            }
        });
        jScrollPaneTable.setViewportView(jTableProbniLet);

        jPanelPodaciVazduhoplov.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Podaci o Vazduhoplovu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        jLabelAerodromPLeta.setText("Aerodrom");

        jTextFieldAerodromPLeta.setToolTipText("Unesite Aerodrom na kome se izvodi Probno Letenje");

        jLabelDatumPLeta.setText("Datum");

        jLabelPocetakPLeta.setText("Početak");

        jTextFieldPocetakPLeta.setToolTipText("Unesite početak Probnog Letenja");

        jLabelZavrsetakPLeta.setText("Završetak");

        jTextFieldZavrsetakPLeta.setToolTipText("Unesite Završetak Probnog Letenja");

        jLabelPritisakVazduhaPLeta.setText("Pritisak Vazduha");

        jTextFieldPritisakVazduhaPLeta.setToolTipText("Unesite Pritisak Vazduha");

        jLabelTemperaturaVazduhaPLeta.setText("Temperatura Vazduha");

        jTextFieldTemperaturaVazduhaPLeta.setToolTipText("Unesite Temperaturu Vazduha");

        jLabelVisinaLetaPLeta.setText("Visina Leta");

        jTextFieldVisinaLetaPLeta.setToolTipText("Unesite Visinu Leta");

        jLabelPPPregledIzvrsioPLeta.setText("Predpoletni Pregled Izvršio");

        jDateChooser_DatumProbnogLeta.setDateFormatString("yyyy-MM-dd");

        jLabel_idProbnogLeta.setText("id");

        jTextField_idProbnogLeta.setToolTipText("Identifikacioni Broj Probnog Leta");

        jPanelNeMenjati.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ovo Nemožete Menjati", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabelProizvodjacTip.setText("Proizvođač");

        jLabelSerijskiBroj.setText("Serijski Broj");

        jLabelBrNaloga.setText("Radni Nalog");

        jTextFieldNaletVazduhoplovaPLet.setToolTipText("Unesite Broj sati Naleta");

        jComboBoxBRNaloga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Radni Nalog" }));
        jComboBoxBRNaloga.setToolTipText("Izaberite Radni Nalog");
        jComboBoxBRNaloga.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxBRNalogaPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jComboBoxRGOznake.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Registracionu Oznaku" }));
        jComboBoxRGOznake.setToolTipText("Izaberite Registracionu Oznaku Vazduhoplova");
        jComboBoxRGOznake.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxRGOznakePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jTextFieldCiklusaVazduhoplovaPLeta.setToolTipText("Unesite Broj Ciklusa");

        jTextFieldSerijskiBrojVazduhoplovaPLet.setToolTipText("Unesite Seriski Broj Vazduhoplova");

        jTextFieldCasovaLetaPLet.setToolTipText("Unesite Broj Časova Leta");

        jLabelNaletVazduhoplovaPLet.setText("Nalet");

        jTextFieldProizvodjacVazduhoplov.setToolTipText("Unesite Proizvođača i Tip Vazduhoplova");

        jLabelCiklusaVazduhoplovaPLet.setText("Ciklusa");

        jLabelCasovaLetaPLet.setText("Časova Leta");

        jLabelRGOznake.setText("RG. Oznake");

        jTextAreaPredmetRadovaPLeta.setColumns(20);
        jTextAreaPredmetRadovaPLeta.setRows(5);
        jTextAreaPredmetRadovaPLeta.setToolTipText("Unesite Predmet Radova");
        jScrollPane1.setViewportView(jTextAreaPredmetRadovaPLeta);

        jLabelPredmetRadovaPLeta.setText("Predmet Radova");

        javax.swing.GroupLayout jPanelNeMenjatiLayout = new javax.swing.GroupLayout(jPanelNeMenjati);
        jPanelNeMenjati.setLayout(jPanelNeMenjatiLayout);
        jPanelNeMenjatiLayout.setHorizontalGroup(
            jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNeMenjatiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelNeMenjatiLayout.createSequentialGroup()
                        .addComponent(jLabelProizvodjacTip)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldProizvodjacVazduhoplov, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelNeMenjatiLayout.createSequentialGroup()
                        .addComponent(jLabelRGOznake)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxRGOznake, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelNeMenjatiLayout.createSequentialGroup()
                        .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCiklusaVazduhoplovaPLet)
                            .addComponent(jLabelBrNaloga)
                            .addComponent(jLabelPredmetRadovaPLeta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jComboBoxBRNaloga, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCiklusaVazduhoplovaPLeta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelNeMenjatiLayout.createSequentialGroup()
                        .addComponent(jLabelSerijskiBroj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldSerijskiBrojVazduhoplovaPLet, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelNeMenjatiLayout.createSequentialGroup()
                        .addComponent(jLabelNaletVazduhoplovaPLet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldNaletVazduhoplovaPLet, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelNeMenjatiLayout.createSequentialGroup()
                        .addComponent(jLabelCasovaLetaPLet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldCasovaLetaPLet, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelNeMenjatiLayout.setVerticalGroup(
            jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNeMenjatiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelProizvodjacTip)
                    .addComponent(jTextFieldProizvodjacVazduhoplov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRGOznake)
                    .addComponent(jComboBoxRGOznake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSerijskiBrojVazduhoplovaPLet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSerijskiBroj))
                .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNaletVazduhoplovaPLet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNaletVazduhoplovaPLet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCasovaLetaPLet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCasovaLetaPLet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCiklusaVazduhoplovaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCiklusaVazduhoplovaPLet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxBRNaloga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBrNaloga))
                .addGap(9, 9, 9)
                .addGroup(jPanelNeMenjatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPredmetRadovaPLeta))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelPodaciVazduhoplovLayout = new javax.swing.GroupLayout(jPanelPodaciVazduhoplov);
        jPanelPodaciVazduhoplov.setLayout(jPanelPodaciVazduhoplovLayout);
        jPanelPodaciVazduhoplovLayout.setHorizontalGroup(
            jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPodaciVazduhoplovLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelNeMenjati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelPodaciVazduhoplovLayout.createSequentialGroup()
                        .addComponent(jLabelAerodromPLeta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldAerodromPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelPodaciVazduhoplovLayout.createSequentialGroup()
                        .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelZavrsetakPLeta)
                            .addComponent(jLabelPritisakVazduhaPLeta)
                            .addComponent(jLabelTemperaturaVazduhaPLeta)
                            .addComponent(jLabelVisinaLetaPLeta)
                            .addComponent(jLabelPPPregledIzvrsioPLeta)
                            .addComponent(jLabelPocetakPLeta)
                            .addComponent(jLabelDatumPLeta)
                            .addComponent(jLabel_idProbnogLeta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_idProbnogLeta)
                            .addComponent(jTextFieldPocetakPLeta, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(jTextFieldVisinaLetaPLeta, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(jTextFieldTemperaturaVazduhaPLeta)
                            .addComponent(jTextFieldZavrsetakPLeta)
                            .addComponent(jTextFieldPritisakVazduhaPLeta)
                            .addComponent(jDateChooser_DatumProbnogLeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField_pppIzvrsio))))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanelPodaciVazduhoplovLayout.setVerticalGroup(
            jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPodaciVazduhoplovLayout.createSequentialGroup()
                .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPodaciVazduhoplovLayout.createSequentialGroup()
                        .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelAerodromPLeta)
                            .addComponent(jTextFieldAerodromPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPodaciVazduhoplovLayout.createSequentialGroup()
                                .addComponent(jLabelDatumPLeta)
                                .addGap(9, 9, 9)
                                .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldPocetakPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelPocetakPLeta)))
                            .addComponent(jDateChooser_DatumProbnogLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelZavrsetakPLeta)
                            .addComponent(jTextFieldZavrsetakPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPritisakVazduhaPLeta)
                            .addComponent(jTextFieldPritisakVazduhaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTemperaturaVazduhaPLeta)
                            .addComponent(jTextFieldTemperaturaVazduhaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPodaciVazduhoplovLayout.createSequentialGroup()
                                .addComponent(jLabelVisinaLetaPLeta)
                                .addGap(12, 12, 12)
                                .addComponent(jLabelPPPregledIzvrsioPLeta))
                            .addGroup(jPanelPodaciVazduhoplovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel_idProbnogLeta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelPodaciVazduhoplovLayout.createSequentialGroup()
                                    .addComponent(jTextFieldVisinaLetaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField_pppIzvrsio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField_idProbnogLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jPanelNeMenjati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jScrollPanePodaciVazduhoplova.setViewportView(jPanelPodaciVazduhoplov);

        jTabbedPaneIZVESTAJ_SA_PROBNOG_LETA.addTab("Podaci o Vazduhoplovu", new javax.swing.ImageIcon(getClass().getResource("/Grafika/file-complete-icon.png")), jScrollPanePodaciVazduhoplova); // NOI18N

        jPanelMotor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Motor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        jLabelStartovanjePLeta.setText("Startovanje");

        jLabelZagrevanjePLeta.setText("Zagrevanje");

        jLabelGKarburatoraPLeta.setText("Grejač Karburatora");

        jTextFieldStartovanjePLeta.setToolTipText("Unesite Startovanje");

        jTextFieldGKarburatoraPLeta.setToolTipText("Unesite Grejač Karburatora");

        jLabelKomandaGasaPLeta.setText("Komanda Gasa");

        jTextFielKomandaGasaPLeta.setToolTipText("Unesite Komandu Gasa");

        jLabelKomandaSmesePLeta.setText("Komanda Smeše");

        jTextFieldKomandaSemsePLeta.setToolTipText("Unesite Komandu Smeše");

        jLabelKRSGPLeta.setText("Komanda Rada Slavina Goriva");

        jTextFieldKRSGPLeta.setToolTipText("Unesite Komanda Rada Slavina Goriva");

        jLabelParkingKocnicaPLeta.setText("Parking Kočnica");

        jLabelPMPLeta.setText("Proba Magneta");

        jLabelPMLeviPLeta.setText("Levi");

        jTextFieldPMLeviPLeta.setToolTipText("Unesite probu magneta Levu");

        jLabelPMDesniPLeta.setText("Desni");

        jTextFieldPMDesniPLeta.setToolTipText("Unesite probu magneta Desnu");

        jLabelaPMROPLeta.setText("Razlika Obrtaja");

        jTextFieldPMROPLeta.setToolTipText("Unesite Razliku Obrtaja");

        jLabelPKEPLeta.setText("Promena Koraka Elise");

        jLabelMaliKPLeta.setText("Mali Korak");

        jTextFieldMaliKorakPLeta.setToolTipText("Unesite promenu koraka elise Mali Korak");

        jLabelVelikiKorakPLeta.setText("Veliki Korak");

        jTextFieldVelikiKorakPLeta.setToolTipText("Unesite promenu koraka elise Veliki Korak");

        jLabelPunaSnagaPLeta.setText("Puna Snaga");

        jTextFieldZagrevanjePLeta.setToolTipText("Unesite Zagrevanje");

        jTextFieldParkingKocnicaPLeta.setToolTipText("Unesite Parking Kočnice");

        jLabelPunjenjeGPLeta.setText("Punjenje Generatora");

        jTextFieldPunjenjeGPLeta.setToolTipText("Unesite Punjenje Generatora");

        jLabelUQUPLeta.setText("Temp.UQA");

        jTextFieldUQAPLeta.setToolTipText("Unesite Temp.UQA");

        jLabelPritUQAPLeta.setText("Prit.UQA");

        jTextFieldPritUQAPLeta.setToolTipText("Unesite Prit.UQA");

        jLabelPritGorivaPLeta.setText("Prit.Goriva");

        jTextFieldPritGorivaPLeta.setToolTipText("Unesite Prit Goriva");

        jLabelProtokGorivaPLeta.setText("Protok Goriva");

        jTextFieldProtokGorivaPLeta.setToolTipText("Unesite Protok Goriva");

        jLabelTgcPLeta.setText("TGC");

        jTextFieldTGCPLeta.setToolTipText("Unesite TGC");

        jLabelBRObrtaPLeta.setText("BR.Obrta");

        jTextFieldBRObrtaPLeta.setToolTipText("Unesite BR.Obrta");

        jLabelManaoVakumPLeta.setText("Mano Vakum");

        jTextFieldManoVakumPLeta.setToolTipText("Unesite Mano Vakum");

        jLabelProtikGorivaPLeta.setText("Protok Goriva");

        jTextFieldProtokGorivaPleta.setToolTipText("Unesite Protok Griva");

        jLabelProveraSmesePLetaS.setText("Provera Smeše");

        jTextFieldPSPleta.setToolTipText("Unesite Proveru Smeše");

        jLabelZaluzineMotoraPLeta.setText("Žaluzine Motora");

        jTextFieldZaluzineMotoraPLeta.setToolTipText("Unesite Žaluzine Motora");

        jLabelHladnjakaPLeta.setText("Hladnjaka");

        jTextFieldHladnjakaPLeta.setToolTipText("Unesite Hladnjaka");

        jLabelKomandaKorakaElisePLeta.setText("Komanda Koraka Elise");

        jTextFieldKomandaKorakaElisePLeta.setToolTipText("Unesite Komanda Koraka Elise");

        javax.swing.GroupLayout jPanelMotorLayout = new javax.swing.GroupLayout(jPanelMotor);
        jPanelMotor.setLayout(jPanelMotorLayout);
        jPanelMotorLayout.setHorizontalGroup(
            jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMotorLayout.createSequentialGroup()
                .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelStartovanjePLeta)
                    .addComponent(jLabelZagrevanjePLeta)
                    .addComponent(jLabelGKarburatoraPLeta)
                    .addComponent(jLabelKomandaGasaPLeta)
                    .addComponent(jLabelKomandaSmesePLeta)
                    .addComponent(jLabelKRSGPLeta)
                    .addComponent(jLabelParkingKocnicaPLeta)
                    .addComponent(jLabelPunjenjeGPLeta)
                    .addComponent(jLabelUQUPLeta)
                    .addComponent(jLabelPritUQAPLeta)
                    .addComponent(jLabelPritGorivaPLeta)
                    .addComponent(jLabelProtokGorivaPLeta)
                    .addComponent(jLabelTgcPLeta)
                    .addComponent(jLabelKomandaKorakaElisePLeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldParkingKocnicaPLeta)
                    .addComponent(jTextFieldStartovanjePLeta)
                    .addComponent(jTextFieldKRSGPLeta)
                    .addComponent(jTextFieldKomandaSemsePLeta)
                    .addComponent(jTextFielKomandaGasaPLeta)
                    .addComponent(jTextFieldGKarburatoraPLeta)
                    .addComponent(jTextFieldZagrevanjePLeta)
                    .addComponent(jTextFieldPunjenjeGPLeta)
                    .addComponent(jTextFieldUQAPLeta)
                    .addComponent(jTextFieldPritUQAPLeta)
                    .addComponent(jTextFieldPritGorivaPLeta)
                    .addComponent(jTextFieldProtokGorivaPLeta)
                    .addComponent(jTextFieldTGCPLeta, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jTextFieldKomandaKorakaElisePLeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPKEPLeta)
                    .addComponent(jLabelPunaSnagaPLeta)
                    .addGroup(jPanelMotorLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelVelikiKorakPLeta)
                            .addComponent(jLabelMaliKPLeta)
                            .addComponent(jLabelBRObrtaPLeta)
                            .addComponent(jLabelManaoVakumPLeta)
                            .addComponent(jLabelProtikGorivaPLeta)
                            .addComponent(jLabelProveraSmesePLetaS)
                            .addComponent(jLabelZaluzineMotoraPLeta)
                            .addComponent(jLabelHladnjakaPLeta)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelPMPLeta)
                        .addGroup(jPanelMotorLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelaPMROPLeta)
                                .addComponent(jLabelPMDesniPLeta)
                                .addComponent(jLabelPMLeviPLeta)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPMROPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextFieldMaliKorakPLeta)
                        .addComponent(jTextFieldVelikiKorakPLeta)
                        .addComponent(jTextFieldBRObrtaPLeta)
                        .addComponent(jTextFieldManoVakumPLeta)
                        .addComponent(jTextFieldProtokGorivaPleta)
                        .addComponent(jTextFieldPSPleta)
                        .addComponent(jTextFieldZaluzineMotoraPLeta)
                        .addComponent(jTextFieldHladnjakaPLeta, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                    .addComponent(jTextFieldPMLeviPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPMDesniPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        jPanelMotorLayout.setVerticalGroup(
            jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMotorLayout.createSequentialGroup()
                .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMotorLayout.createSequentialGroup()
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelStartovanjePLeta)
                            .addComponent(jTextFieldStartovanjePLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPMPLeta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelZagrevanjePLeta)
                            .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldZagrevanjePLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelPMDesniPLeta)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelGKarburatoraPLeta)
                            .addComponent(jTextFieldGKarburatoraPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPMLeviPLeta)))
                    .addGroup(jPanelMotorLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jTextFieldPMDesniPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPMLeviPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKomandaGasaPLeta)
                    .addComponent(jTextFielKomandaGasaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelaPMROPLeta)
                    .addComponent(jTextFieldPMROPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldKomandaSemsePLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelKomandaSmesePLeta)
                    .addComponent(jLabelPKEPLeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMaliKPLeta)
                    .addComponent(jTextFieldMaliKorakPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelKomandaKorakaElisePLeta)
                    .addComponent(jTextFieldKomandaKorakaElisePLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMotorLayout.createSequentialGroup()
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMotorLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabelPunaSnagaPLeta))
                            .addComponent(jTextFieldVelikiKorakPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelVelikiKorakPLeta))
                        .addGap(9, 9, 9)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelBRObrtaPLeta)
                            .addComponent(jTextFieldBRObrtaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelManaoVakumPLeta)
                            .addComponent(jTextFieldManoVakumPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelProtikGorivaPLeta)
                            .addComponent(jTextFieldProtokGorivaPleta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelProveraSmesePLetaS)
                            .addComponent(jTextFieldPSPleta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelZaluzineMotoraPLeta)
                            .addComponent(jTextFieldZaluzineMotoraPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelHladnjakaPLeta)
                            .addComponent(jTextFieldHladnjakaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelMotorLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelKRSGPLeta)
                            .addComponent(jTextFieldKRSGPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMotorLayout.createSequentialGroup()
                                .addComponent(jLabelParkingKocnicaPLeta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelPunjenjeGPLeta))
                            .addGroup(jPanelMotorLayout.createSequentialGroup()
                                .addComponent(jTextFieldParkingKocnicaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldPunjenjeGPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelUQUPLeta)
                            .addComponent(jTextFieldUQAPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPritUQAPLeta)
                            .addComponent(jTextFieldPritUQAPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPritGorivaPLeta)
                            .addComponent(jTextFieldPritGorivaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelProtokGorivaPLeta)
                            .addComponent(jTextFieldProtokGorivaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTgcPLeta)
                            .addComponent(jTextFieldTGCPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jScrollPaneMotor.setViewportView(jPanelMotor);

        jTabbedPaneIZVESTAJ_SA_PROBNOG_LETA.addTab("Motor", new javax.swing.ImageIcon(getClass().getResource("/Grafika/Very-Basic-Services-icon.png")), jScrollPaneMotor); // NOI18N

        jPanelProveraULetu.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Provera u Letu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        jLabelVoznjaPoZemljiPLeta.setText("Vožnja po Zemlji");

        jLabelKocnicePLeta.setText("Kočnice");

        jLabelGUPUPLeta.setText("Upravljivost u Poletanju");

        jTextFieldVoznjaPoZemljPLeta.setToolTipText("Unesite vožnju po zemlji");

        jTextFieldUpravljivostUPoletanjuPLeta.setToolTipText("Unesite upravljivost u poletanju");

        jLabelStajniTrapPLeta.setText("Stajni Trap");

        jTextFielStajniTrapPLeta.setToolTipText("Unesite Stajni Trap");

        jLabelRadTrimeraPLeta.setText("Rad Trimera");

        jTextFieldRadTrimeraPLeta.setToolTipText("Unesite Rad Trimera");

        jLabelRKLULPLeta.setText("Rad Komandi Leta u Letu");

        jTextFieldRKLULPLeta.setToolTipText("Unesite Rad Komandi u Letu");

        jLabelRadZakrilcaPLeta.setText("Rad Zakrilaca");

        jLabePokazivanjeIstrumenataPLeta.setText("Pokazivanje Istrumenata");

        jLabelHorizontalniPLeta.setText("Horizontalni Let");

        jTextFieldPokazivanjeIstrumenataPLeta.setToolTipText("Unesite Pokazivanje Istrumenata");

        jLabelPrevuceniPLeta.setText("Prevučeni Let, Brzina Sloma Uzgona");

        jTextFieldHorizontalniPLeta.setToolTipText("Unesite Horizontalni Let");

        jTextFieldPrevuceniLetOPLeta.setToolTipText("Unesite Prevučeni Let, Brzinu Sloma Uzgona");

        jLabelOstriZaokretPLeta.setText("Levi i Desni Oštri Zaokret");

        jTextFieldOstriZaokretPLeta.setToolTipText("Unesite Levi Desni Oštri Zaokret");

        jLabelZapazanjaPLeta.setText("Opšta Zapažanja Pilota");

        jTextFieldKocnicePLeta.setToolTipText("Unesite ocenu kočnica");

        jTextFieldRadZakrilcaPLeta.setToolTipText("Unesite Rad Zakrilca");

        jLabelIndikacijaZakrilca.setText("Indikacija Zakrilaca");

        jTextFieldIndikacijaZakrilcaPLeta.setToolTipText("Unesite Indikacija Zakrilca");

        jLabelSvetlaUpozorenjaPLeta.setText("Svetla Upozorenja");

        jTextFieldSvetlaUpozorenjaPLeta.setToolTipText("Unesite Svetla Upozorenja");

        jLabelRadioStanicaPLeta.setText("Radio Stanica");

        jTextFieldRadioStanicaPLeta.setToolTipText("Unesite Radio Stanicu");

        jLabelVORPLeta.setText("VOR");

        jTextFieldVORPLeta.setToolTipText("Unesite VOR");

        jLabelADFPLeta.setText("ADF");

        jTextFieldADFPLeta.setToolTipText("Unesite ADF");

        jLabelInterfonPLeta.setText("Interfon");

        jTextFieldInterfonPLeta.setToolTipText("Unesite Interfon");

        jLabelOdobrenoPLeta.setText("Odobreno Rešenjem DCV br.");

        jTextFieldOdobrenoPLeta.setToolTipText("Unesite Broj Rešenje Odobrenja DCV");

        jLabelPilotPLeta.setText("Pilot");

        jTextFieldPilotPLeta.setToolTipText("Unesite Pilota");

        jTextAreaZapazanjaPLeta.setColumns(20);
        jTextAreaZapazanjaPLeta.setRows(5);
        jTextAreaZapazanjaPLeta.setToolTipText("Unesite Zapažanja Pilota");
        jScrollPane2.setViewportView(jTextAreaZapazanjaPLeta);

        jLabel_sletanje.setText("Prilaz i Sletanje");

        javax.swing.GroupLayout jPanelProveraULetuLayout = new javax.swing.GroupLayout(jPanelProveraULetu);
        jPanelProveraULetu.setLayout(jPanelProveraULetuLayout);
        jPanelProveraULetuLayout.setHorizontalGroup(
            jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVoznjaPoZemljiPLeta)
                    .addComponent(jLabelKocnicePLeta)
                    .addComponent(jLabelGUPUPLeta)
                    .addComponent(jLabelStajniTrapPLeta)
                    .addComponent(jLabelRadTrimeraPLeta)
                    .addComponent(jLabelRKLULPLeta)
                    .addComponent(jLabelRadZakrilcaPLeta)
                    .addComponent(jLabelIndikacijaZakrilca)
                    .addComponent(jLabelRadioStanicaPLeta)
                    .addComponent(jLabelInterfonPLeta)
                    .addComponent(jLabelSvetlaUpozorenjaPLeta)
                    .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabelADFPLeta)
                        .addComponent(jLabelVORPLeta)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldRadZakrilcaPLeta)
                    .addComponent(jTextFieldVoznjaPoZemljPLeta)
                    .addComponent(jTextFieldRKLULPLeta)
                    .addComponent(jTextFieldRadTrimeraPLeta)
                    .addComponent(jTextFielStajniTrapPLeta)
                    .addComponent(jTextFieldUpravljivostUPoletanjuPLeta)
                    .addComponent(jTextFieldKocnicePLeta)
                    .addComponent(jTextFieldIndikacijaZakrilcaPLeta)
                    .addComponent(jTextFieldSvetlaUpozorenjaPLeta)
                    .addComponent(jTextFieldRadioStanicaPLeta)
                    .addComponent(jTextFieldVORPLeta)
                    .addComponent(jTextFieldADFPLeta)
                    .addComponent(jTextFieldInterfonPLeta, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelHorizontalniPLeta)
                    .addComponent(jLabelPrevuceniPLeta)
                    .addComponent(jLabelZapazanjaPLeta)
                    .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                            .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelOdobrenoPLeta)
                                .addComponent(jLabelPilotPLeta))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldOdobrenoPLeta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldPilotPLeta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                            .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                                    .addComponent(jLabelOstriZaokretPLeta)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldOstriZaokretPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                                    .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabePokazivanjeIstrumenataPLeta)
                                        .addComponent(jLabel_sletanje))
                                    .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextFieldHorizontalniPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jTextFieldPokazivanjeIstrumenataPLeta, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                                                    .addComponent(jTextFieldPrevuceniLetOPLeta))))
                                        .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                                            .addGap(4, 4, 4)
                                            .addComponent(jTextField_sletanjePleta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGap(1, 1, 1))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanelProveraULetuLayout.setVerticalGroup(
            jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldVoznjaPoZemljPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelVoznjaPoZemljiPLeta))
                    .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabePokazivanjeIstrumenataPLeta)
                        .addComponent(jTextFieldPokazivanjeIstrumenataPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelKocnicePLeta)
                    .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldKocnicePLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelHorizontalniPLeta)
                        .addComponent(jTextFieldHorizontalniPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelGUPUPLeta)
                    .addComponent(jTextFieldUpravljivostUPoletanjuPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPrevuceniPLeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStajniTrapPLeta)
                    .addComponent(jTextFielStajniTrapPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrevuceniLetOPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldRadTrimeraPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelRadTrimeraPLeta)
                    .addComponent(jLabelOstriZaokretPLeta)
                    .addComponent(jTextFieldOstriZaokretPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRKLULPLeta)
                    .addComponent(jTextFieldRKLULPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_sletanje)
                    .addComponent(jTextField_sletanjePleta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                        .addComponent(jLabelRadZakrilcaPLeta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelIndikacijaZakrilca))
                    .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                        .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldRadZakrilcaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelZapazanjaPLeta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelRadioStanicaPLeta)
                                    .addGroup(jPanelProveraULetuLayout.createSequentialGroup()
                                        .addComponent(jTextFieldIndikacijaZakrilcaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextFieldSvetlaUpozorenjaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelSvetlaUpozorenjaPLeta))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldRadioStanicaPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldVORPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelVORPLeta)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldADFPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelOdobrenoPLeta)
                        .addComponent(jTextFieldOdobrenoPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelADFPLeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProveraULetuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInterfonPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelInterfonPLeta)
                    .addComponent(jLabelPilotPLeta)
                    .addComponent(jTextFieldPilotPLeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPaneProverauLetu.setViewportView(jPanelProveraULetu);

        jTabbedPaneIZVESTAJ_SA_PROBNOG_LETA.addTab("Provera u Letu", new javax.swing.ImageIcon(getClass().getResource("/Grafika/icon.png")), jScrollPaneProverauLetu); // NOI18N

        txt_pretragaProbniLet.setToolTipText("Pretraga Po Broju DCV");
        txt_pretragaProbniLet.setMinimumSize(new java.awt.Dimension(6, 40));
        txt_pretragaProbniLet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_pretragaProbniLetKeyReleased(evt);
            }
        });

        Button_insertProbniLet.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_insertProbniLet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Emblems-vcs-added-icon.png"))); // NOI18N
        Button_insertProbniLet.setToolTipText("Unos novih podataka u bazu");
        Button_insertProbniLet.setFocusable(false);
        Button_insertProbniLet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_insertProbniLet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_insertProbniLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_insertProbniLetActionPerformed(evt);
            }
        });

        Button_clearProbniLet.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_clearProbniLet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Actions-edit-clear-icon.png"))); // NOI18N
        Button_clearProbniLet.setToolTipText("Očisti polja za unos podataka");
        Button_clearProbniLet.setFocusable(false);
        Button_clearProbniLet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_clearProbniLet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_clearProbniLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_clearProbniLetActionPerformed(evt);
            }
        });

        Button_updateProbniLet.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_updateProbniLet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Text-Edit-icon.png"))); // NOI18N
        Button_updateProbniLet.setToolTipText("Ažuriranje izabranih podataka");
        Button_updateProbniLet.setFocusable(false);
        Button_updateProbniLet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_updateProbniLet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_updateProbniLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_updateProbniLetActionPerformed(evt);
            }
        });

        Button_refreshProbniLet.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_refreshProbniLet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Reload-icon.png"))); // NOI18N
        Button_refreshProbniLet.setToolTipText("Učitavanje podataka u tabelu ponovo");
        Button_refreshProbniLet.setFocusable(false);
        Button_refreshProbniLet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_refreshProbniLet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_refreshProbniLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_refreshProbniLetActionPerformed(evt);
            }
        });

        Button_stampaProbniLet.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_stampaProbniLet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Adobe-PDF-Document-icon.png"))); // NOI18N
        Button_stampaProbniLet.setToolTipText("Generisanje Dokumenta u PDF formatu koji možete štampati");
        Button_stampaProbniLet.setFocusable(false);
        Button_stampaProbniLet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_stampaProbniLet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_stampaProbniLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_stampaProbniLetActionPerformed(evt);
            }
        });

        Button_deleteProbniLet.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_deleteProbniLet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/delete-icon.png"))); // NOI18N
        Button_deleteProbniLet.setToolTipText("Obriši izabrane podatke iz baze");
        Button_deleteProbniLet.setFocusable(false);
        Button_deleteProbniLet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_deleteProbniLet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_deleteProbniLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteProbniLetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelOsnovaPLetaLayout = new javax.swing.GroupLayout(jPanelOsnovaPLeta);
        jPanelOsnovaPLeta.setLayout(jPanelOsnovaPLetaLayout);
        jPanelOsnovaPLetaLayout.setHorizontalGroup(
            jPanelOsnovaPLetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOsnovaPLetaLayout.createSequentialGroup()
                .addComponent(jTabbedPaneIZVESTAJ_SA_PROBNOG_LETA, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOsnovaPLetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOsnovaPLetaLayout.createSequentialGroup()
                        .addGroup(jPanelOsnovaPLetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Button_insertProbniLet, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Button_clearProbniLet, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelOsnovaPLetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOsnovaPLetaLayout.createSequentialGroup()
                                .addComponent(Button_stampaProbniLet)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_refreshProbniLet))
                            .addGroup(jPanelOsnovaPLetaLayout.createSequentialGroup()
                                .addComponent(Button_updateProbniLet)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_deleteProbniLet))))
                    .addComponent(txt_pretragaProbniLet, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98))
            .addComponent(jScrollPaneTable)
        );
        jPanelOsnovaPLetaLayout.setVerticalGroup(
            jPanelOsnovaPLetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOsnovaPLetaLayout.createSequentialGroup()
                .addComponent(jScrollPaneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelOsnovaPLetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOsnovaPLetaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPaneIZVESTAJ_SA_PROBNOG_LETA, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
                    .addGroup(jPanelOsnovaPLetaLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(txt_pretragaProbniLet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelOsnovaPLetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_clearProbniLet, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_stampaProbniLet, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_refreshProbniLet, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelOsnovaPLetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Button_insertProbniLet, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_updateProbniLet, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_deleteProbniLet, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jTabbedPaneIzvestajProbniLet.addTab("Izveštaj sa probnog leta", new javax.swing.ImageIcon(getClass().getResource("/Grafika/icon.png")), jPanelOsnovaPLeta); // NOI18N

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
            .addComponent(jTabbedPaneIzvestajProbniLet)
            .addComponent(ToolBar_OpsteFunkcije, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ToolBar_OpsteFunkcije, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneIzvestajProbniLet)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(898, 739));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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

    private void txt_pretragaProbniLetKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pretragaProbniLetKeyReleased
        //popuna txt polja podacima za pretragu prema brodu DCV
        try{
            String sql="select * from izvestaj_sa_probnog_leta where Odobreno_Resenjem_DCV_broj=?";//Upit za pretragu
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_pretragaProbniLet.getText());
            rs=pst.executeQuery();
            //popuna txt polja podacima iz tabele
            poljaProbniLet();
            popunaPoljaOVazduhoplovu();//popuna polja o vazduhoplovu
            popunaPoljaRadniNalog();//popuna polja o radnom nalogu
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txt_pretragaProbniLetKeyReleased

    private void Button_insertProbniLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_insertProbniLetActionPerformed
        // Unos Novih podataka u tabelu probnilet
      try {
            String sql = "Insert into izvestaj_sa_probnog_leta (id, idVazduhoplova, idRadniNalog, Aerodrom, Datum, Vreme_Pocetka, Vreme_Zavrsetka, Pritisak_Vazduha, Temperatura_Vazduha, Visina_Leta, Pretpoletni_Pregled_Izvrsio, Startovanje, Zagrevanje, Grejanje_Karburatora, Komanda_Gasa, Komanda_Smese, Komanda_Elise, Rad_Slavina_Goriva, Parking_Kocnica, Punjenje_Generatora, Temp_Uqa, Prit_Uqa, Prit_Goriva, Protok_Goriva, TGC, Levi, Desni, Razlika, Obrtaj_Promene_Koraka_Elise_Mali_Korak, Obrtaj_Promene_Koraka_Elise_Veliki_Korak, Puna_Snaga_br_Obrta, Puna_Snaga_Mano_Vakum, Puna_Snaga_Protok_Goriva, Puna_Snaga_Provera_Smese, Puna_Snaga_Zaluzine_Motora, Puna_Snaga_Hladnjak, Voznja_po_Zemlji, Kocnice, Upravljivost_u_Poletanju, Stajni_Trap, Rad_Trimera, Rad_Komandi_Leta_u_Letu, Rad_Zakrilaca, Indikacija_Zakrilaca, Svetla_Upozorenja, Radio_Stanica, Vor, Adf, Interfon, Pokazivanje_Istrumenata, Horizontalni_Let, Prevuceni_Let_Brzina_Sloma_Uzgona, Levi_Desni_Ostri_Zaokret, Prilaz_i_Sletanje, Opsta_Zapazanja_Pilota, Odobreno_Resenjem_DCV_broj, Pilot) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1, jTextField_idProbnogLeta.getText());
            pst.setInt(2, jComboBoxRGOznake.getSelectedIndex());
            pst.setInt(3, jComboBoxBRNaloga.getSelectedIndex());
            pst.setString(4, jTextFieldAerodromPLeta.getText());
            pst.setString(5, ((JTextField) jDateChooser_DatumProbnogLeta.getDateEditor().getUiComponent()).getText());
            pst.setString(6, jTextFieldPocetakPLeta.getText());
            pst.setString(7, jTextFieldZavrsetakPLeta.getText());
            pst.setString(8, jTextFieldPritisakVazduhaPLeta.getText());
            pst.setString(9, jTextFieldTemperaturaVazduhaPLeta.getText());
            pst.setString(10, jTextFieldVisinaLetaPLeta.getText());
            pst.setString(11, jTextField_pppIzvrsio.getText());
            pst.setString(12, jTextFieldStartovanjePLeta.getText());
            pst.setString(13, jTextFieldZagrevanjePLeta.getText());
            pst.setString(14, jTextFieldGKarburatoraPLeta.getText());
            pst.setString(15, jTextFielKomandaGasaPLeta.getText());
            pst.setString(16, jTextFieldKomandaSemsePLeta.getText());
            pst.setString(17, jTextFieldKomandaKorakaElisePLeta.getText());
            pst.setString(18, jTextFieldKRSGPLeta.getText());
            pst.setString(19, jTextFieldParkingKocnicaPLeta.getText());
            pst.setString(20, jTextFieldPunjenjeGPLeta.getText());
            pst.setString(21, jTextFieldUQAPLeta.getText());
            pst.setString(22, jTextFieldPritUQAPLeta.getText());
            pst.setString(23, jTextFieldPritGorivaPLeta.getText());
            pst.setString(24, jTextFieldProtokGorivaPLeta.getText());
            pst.setString(25, jTextFieldTGCPLeta.getText());
            pst.setString(26, jTextFieldPMDesniPLeta.getText());
            pst.setString(27, jTextFieldPMLeviPLeta.getText());
            pst.setString(28, jTextFieldPMROPLeta.getText());
            pst.setString(29, jTextFieldMaliKorakPLeta.getText());
            pst.setString(30, jTextFieldVelikiKorakPLeta.getText());
            pst.setString(31, jTextFieldBRObrtaPLeta.getText());
            pst.setString(32, jTextFieldManoVakumPLeta.getText());
            pst.setString(33, jTextFieldProtokGorivaPleta.getText());
            pst.setString(34, jTextFieldPSPleta.getText());
            pst.setString(35, jTextFieldZaluzineMotoraPLeta.getText());
            pst.setString(36, jTextFieldHladnjakaPLeta.getText());
            pst.setString(37, jTextFieldVoznjaPoZemljPLeta.getText());
            pst.setString(38, jTextFieldKocnicePLeta.getText());
            pst.setString(39, jTextFieldUpravljivostUPoletanjuPLeta.getText());
            pst.setString(40, jTextFielStajniTrapPLeta.getText());
            pst.setString(41, jTextFieldRadTrimeraPLeta.getText());
            pst.setString(42, jTextFieldRKLULPLeta.getText());
            pst.setString(43, jTextFieldRadZakrilcaPLeta.getText());
            pst.setString(44, jTextFieldIndikacijaZakrilcaPLeta.getText());
            pst.setString(45, jTextFieldSvetlaUpozorenjaPLeta.getText());
            pst.setString(46, jTextFieldRadioStanicaPLeta.getText());
            pst.setString(47, jTextFieldVORPLeta.getText());
            pst.setString(48, jTextFieldADFPLeta.getText());
            pst.setString(49, jTextFieldInterfonPLeta.getText());
            pst.setString(50, jTextFieldPokazivanjeIstrumenataPLeta.getText());
            pst.setString(51, jTextFieldHorizontalniPLeta.getText());
            pst.setString(52, jTextFieldPrevuceniLetOPLeta.getText());
            pst.setString(53, jTextFieldOstriZaokretPLeta.getText());
            pst.setString(54, jTextField_sletanjePleta.getText());
            pst.setString(55, jTextAreaZapazanjaPLeta.getText());
            pst.setString(56, jTextFieldOdobrenoPLeta.getText());
            pst.setString(57, jTextFieldPilotPLeta.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Vaši podaci su uspešno sačuvani!", "Sačuvaj Podatke", JOptionPane.OK_OPTION);
            Update_ProbniLet();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_insertProbniLetActionPerformed

    private void Button_clearProbniLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_clearProbniLetActionPerformed
        //Brisanje svih polja za unos u polju panelu probni let
        try{
                    jTextField_idProbnogLeta.setText("");
                    jComboBoxRGOznake.setSelectedIndex(0);
                    jComboBoxBRNaloga.setSelectedIndex(0);
                    jTextField_pppIzvrsio.setText("");
                    jTextFieldAerodromPLeta.setText("");                    
                    jTextFieldProizvodjacVazduhoplov.setText("");
                    jTextFieldSerijskiBrojVazduhoplovaPLet.setText("");
                    jTextFieldNaletVazduhoplovaPLet.setText("");
                    jTextFieldCasovaLetaPLet.setText("");
                    jTextFieldCiklusaVazduhoplovaPLeta.setText("");
                    jTextAreaPredmetRadovaPLeta.setText("");
                    ((JTextField) jDateChooser_DatumProbnogLeta.getDateEditor().getUiComponent()).setText("");
                    jTextFieldPocetakPLeta.setText("");
                    jTextFieldZavrsetakPLeta.setText("");
                    jTextFieldPritisakVazduhaPLeta.setText("");
                    jTextFieldTemperaturaVazduhaPLeta.setText("");
                    jTextFieldVisinaLetaPLeta.setText("");
                    jTextFieldStartovanjePLeta.setText("");
                    jTextFieldZagrevanjePLeta.setText("");
                    jTextFieldGKarburatoraPLeta.setText("");
                    jTextFielKomandaGasaPLeta.setText("");
                    jTextFieldKomandaSemsePLeta.setText("");
                    jTextFieldKomandaKorakaElisePLeta.setText("");
                    jTextFieldKRSGPLeta.setText("");
                    jTextFieldParkingKocnicaPLeta.setText("");
                    jTextFieldPunjenjeGPLeta.setText("");
                    jTextFieldUQAPLeta.setText("");
                    jTextFieldPritUQAPLeta.setText("");
                    jTextFieldPritGorivaPLeta.setText("");
                    jTextFieldProtokGorivaPLeta.setText("");
                    jTextFieldTGCPLeta.setText("");
                    jTextFieldPMLeviPLeta.setText("");
                    jTextFieldPMDesniPLeta.setText("");
                    jTextFieldPMROPLeta.setText("");
                    jTextFieldMaliKorakPLeta.setText("");
                    jTextFieldVelikiKorakPLeta.setText("");
                    jTextFieldBRObrtaPLeta.setText("");
                    jTextFieldManoVakumPLeta.setText("");
                    jTextFieldProtokGorivaPleta.setText("");
                    jTextFieldPSPleta.setText("");
                    jTextFieldZaluzineMotoraPLeta.setText("");
                    jTextFieldHladnjakaPLeta.setText("");
                    jTextFieldVoznjaPoZemljPLeta.setText("");
                    jTextFieldKocnicePLeta.setText("");
                    jTextFieldUpravljivostUPoletanjuPLeta.setText("");
                    jTextFielStajniTrapPLeta.setText("");
                    jTextFieldRadTrimeraPLeta.setText("");
                    jTextFieldRKLULPLeta.setText("");
                    jTextFieldRadZakrilcaPLeta.setText("");
                    jTextFieldIndikacijaZakrilcaPLeta.setText("");
                    jTextFieldSvetlaUpozorenjaPLeta.setText("");
                    jTextFieldRadioStanicaPLeta.setText("");
                    jTextFieldVORPLeta.setText("");
                    jTextFieldADFPLeta.setText("");
                    jTextFieldInterfonPLeta.setText("");
                    jTextFieldPokazivanjeIstrumenataPLeta.setText("");
                    jTextFieldHorizontalniPLeta.setText("");
                    jTextFieldPrevuceniLetOPLeta.setText("");
                    jTextFieldOstriZaokretPLeta.setText("");
                    jTextField_sletanjePleta.setText("");
                    jTextAreaZapazanjaPLeta.setText("");
                    jTextFieldOdobrenoPLeta.setText("");
                    jTextFieldPilotPLeta.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_clearProbniLetActionPerformed

    private void Button_updateProbniLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_updateProbniLetActionPerformed
        // Azuriranje podataka u tabeli probni let
        try {
            String ID = jTextField_idProbnogLeta.getText();
            int RegOznaga = jComboBoxRGOznake.getSelectedIndex();
            int BRNaloga = jComboBoxBRNaloga.getSelectedIndex();
            String Aerodrom = jTextFieldAerodromPLeta.getText();
            String Datum = ((JTextField) jDateChooser_DatumProbnogLeta.getDateEditor().getUiComponent()).getText();
            String Pocetak = jTextFieldPocetakPLeta.getText();
            String Zavrsetak = jTextFieldZavrsetakPLeta.getText();
            String PritisakVazduha = jTextFieldPritisakVazduhaPLeta.getText();
            String TemperaturaVazduha = jTextFieldTemperaturaVazduhaPLeta.getText();
            String VisinaLeta = jTextFieldVisinaLetaPLeta.getText();
            String PPPregled = jTextField_pppIzvrsio.getText();
            String Startovanje = jTextFieldStartovanjePLeta.getText();
            String Zagrevanje = jTextFieldZagrevanjePLeta.getText();
            String GrejanjeKarburatora = jTextFieldGKarburatoraPLeta.getText();
            String KomandaGasa = jTextFielKomandaGasaPLeta.getText();
            String KomandaSmese = jTextFieldKomandaSemsePLeta.getText();
            String KomandaElise = jTextFieldKomandaKorakaElisePLeta.getText();
            String RadSlavinaGoriva = jTextFieldKRSGPLeta.getText();
            String ParkingKocnica = jTextFieldParkingKocnicaPLeta.getText();
            String PunjenjeGeneratora = jTextFieldPunjenjeGPLeta.getText();
            String TempUqa = jTextFieldUQAPLeta.getText();
            String PritUqa = jTextFieldPritUQAPLeta.getText();
            String PritGoriva = jTextFieldPritGorivaPLeta.getText();
            String ProtokGoriva = jTextFieldProtokGorivaPLeta.getText();
            String Tgc = jTextFieldTGCPLeta.getText();
            String Desni = jTextFieldPMDesniPLeta.getText();
            String Levi = jTextFieldPMLeviPLeta.getText();
            String Razlika = jTextFieldPMROPLeta.getText();
            String MaliKorak = jTextFieldMaliKorakPLeta.getText();
            String VelikiKorak = jTextFieldVelikiKorakPLeta.getText();
            String BRObrta = jTextFieldBRObrtaPLeta.getText();
            String ManoVakum = jTextFieldManoVakumPLeta.getText();
            String ProtokGoriva2 = jTextFieldProtokGorivaPleta.getText();
            String ProveraSmese = jTextFieldPSPleta.getText();
            String Zaluzine = jTextFieldZaluzineMotoraPLeta.getText();
            String Hladnjak = jTextFieldHladnjakaPLeta.getText();
            String VoznjaPoZemlji = jTextFieldVoznjaPoZemljPLeta.getText();
            String Kocnice = jTextFieldKocnicePLeta.getText();
            String UpravljivostPoletanje = jTextFieldUpravljivostUPoletanjuPLeta.getText();
            String StajniTrap =  jTextFielStajniTrapPLeta.getText();
            String RadTrimera = jTextFieldRadTrimeraPLeta.getText();
            String KomandeULetu = jTextFieldRKLULPLeta.getText();
            String RadZakrilaca = jTextFieldRadZakrilcaPLeta.getText();
            String IndikacijaZakrilaca = jTextFieldIndikacijaZakrilcaPLeta.getText();
            String SvetlaUpozorenja = jTextFieldSvetlaUpozorenjaPLeta.getText();
            String RadioStanica = jTextFieldRadioStanicaPLeta.getText();
            String Vor = jTextFieldVORPLeta.getText();
            String Adf = jTextFieldADFPLeta.getText();
            String Interfon = jTextFieldInterfonPLeta.getText();
            String PokazivanjeInstrumenata = jTextFieldPokazivanjeIstrumenataPLeta.getText();
            String HorizontalniLet = jTextFieldHorizontalniPLeta.getText();
            String PrevuceniLet = jTextFieldPrevuceniLetOPLeta.getText();
            String OstriZaokreti = jTextFieldOstriZaokretPLeta.getText();
            String Sletanje = jTextField_sletanjePleta.getText();
            String ZapazanjePilota = jTextAreaZapazanjaPLeta.getText();
            String OdobrenjeDVC = jTextFieldOdobrenoPLeta.getText();
            String Pilot = jTextFieldPilotPLeta.getText();
            
            String sql = "update izvestaj_sa_probnog_leta set id='" + ID + "',idVazduhoplova='" + RegOznaga + "',idRadniNalog='" + BRNaloga + "',Aerodrom='" + Aerodrom + "' ,Datum='" + Datum + "' ,Vreme_Pocetka='" + Pocetak + "',Vreme_Zavrsetka='" + Zavrsetak + "', Pritisak_Vazduha='" + PritisakVazduha + "', Temperatura_Vazduha='" + TemperaturaVazduha + "', Visina_Leta='" + VisinaLeta + "', Pretpoletni_Pregled_Izvrsio='" + PPPregled + "', Startovanje='" + Startovanje + "', Zagrevanje='" + Zagrevanje + "', Grejanje_Karburatora='" + GrejanjeKarburatora + "', Komanda_Gasa='" + KomandaGasa + "', Komanda_Smese='" + KomandaSmese + "', Komanda_Elise='" + KomandaElise + "', Rad_Slavina_Goriva='" + RadSlavinaGoriva + "', Parking_Kocnica='" + ParkingKocnica + "', Punjenje_Generatora='" + PunjenjeGeneratora + "', Temp_Uqa='" + TempUqa + "', Prit_Uqa='" + PritUqa + "', Prit_Goriva='" + PritGoriva + "', Protok_Goriva='" + ProtokGoriva + "', TGC='" + Tgc + "', Levi='" + Levi + "', Desni='" + Desni + "', Razlika='" + Razlika + "', Obrtaj_Promene_Koraka_Elise_Mali_Korak='" + MaliKorak + "', Obrtaj_Promene_Koraka_Elise_Veliki_Korak='" + VelikiKorak + "', Puna_Snaga_br_Obrta='" + BRObrta + "', Puna_Snaga_Mano_Vakum='" + ManoVakum + "', Puna_Snaga_Protok_Goriva='" + ProtokGoriva2 + "', Puna_Snaga_Provera_Smese='" + ProveraSmese + "', Puna_Snaga_Zaluzine_Motora='" + Zaluzine + "', Puna_Snaga_Hladnjak='" + Hladnjak + "', Voznja_po_Zemlji='" + VoznjaPoZemlji + "', Kocnice='" + Kocnice + "', Upravljivost_u_Poletanju='" + UpravljivostPoletanje + "', Stajni_Trap='" + StajniTrap + "', Rad_Trimera='" + RadTrimera + "', Rad_Komandi_Leta_u_Letu='" + KomandeULetu + "', Rad_Zakrilaca='" + RadZakrilaca + "', Indikacija_Zakrilaca='" + IndikacijaZakrilaca + "', Svetla_Upozorenja='" + SvetlaUpozorenja + "', Radio_Stanica='" + RadioStanica + "', Vor='" + Vor + "', Adf='" + Adf + "', Interfon='" + Interfon + "', Pokazivanje_Istrumenata='" + PokazivanjeInstrumenata + "', Horizontalni_Let='" + HorizontalniLet + "', Prevuceni_Let_Brzina_Sloma_Uzgona='" + PrevuceniLet + "', Levi_Desni_Ostri_Zaokret='" + OstriZaokreti + "', Prilaz_i_Sletanje='" + Sletanje + "', Opsta_Zapazanja_Pilota='" + ZapazanjePilota + "', Odobreno_Resenjem_DCV_broj='" + OdobrenjeDVC + "', Pilot='" + Pilot + "' where id='" + ID + "' ";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Uspešno ste ažurirali podatke u bazi podataka!", "Ažuriraj Podatke", JOptionPane.OK_OPTION);

            Update_ProbniLet();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }//GEN-LAST:event_Button_updateProbniLetActionPerformed

    private void Button_refreshProbniLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_refreshProbniLetActionPerformed
        // Ponovno ucitavanje podataka u tabelu probni let
        Update_ProbniLet();
    }//GEN-LAST:event_Button_refreshProbniLetActionPerformed

    private void Button_stampaProbniLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_stampaProbniLetActionPerformed
        // Kreiranje Dokumenta
        String Datum = ((JTextField) jDateChooser_DatumProbnogLeta.getDateEditor().getUiComponent()).getText();
        try {
            //inicijalizacija
            Document document =new Document();
            //kreiranje samog dokumenta
            PdfWriter.getInstance(document, new FileOutputStream("IzveštajSaProbnogLeta.pdf"));      
            //otvaranje
            document.open();
            //Kreiranje zaglavlja dokumenta
            PdfPTable zaglavlje = new PdfPTable(1);
            zaglavlje.addCell("HELI VISION doo" + "                                                                 TEHNIČKA SLUŽBA\n" +
                "                                                               ZA ODRŽAVANJE VAZDUHOPLOVA");
            document.add(zaglavlje);
            //Prazan red
            document.add(new Paragraph(" "));
            //Kreiranje Naslova
            document.add(new Paragraph("                                                 IZVEŠTAJ SA PROBNOG LETA\n" +
                "                                                                Test flight report",FontFactory.getFont(FontFactory.TIMES_BOLD,14,Font.BOLD)));
            //Prazan red
            document.add(new Paragraph(" "));
            //Osnovno o vazduhoplovu
            document.add(new Paragraph("PODACI O VAZDUHOPLOVU : Aircraft's data:",FontFactory.getFont(FontFactory.TIMES_BOLD,11,Font.BOLD)));
            //Prazan red
            document.add(new Paragraph(" "));
            //Prvi red Tabele
            PdfPTable tabelaPrviRed = new PdfPTable(1);
            tabelaPrviRed.addCell("Proizvođač i tip vazduhoplova:\n" + 
                "Manufacturer & model of Aircraft:    " + jTextFieldProizvodjacVazduhoplov.getText());
            document.add(tabelaPrviRed);
            //Drugi red u tabeli
            PdfPTable tabelaDrugiRed = new PdfPTable(2);
            tabelaDrugiRed.addCell("Registarska oznaka: \n" +
                "Registration number    " + jComboBoxRGOznake.getSelectedItem().toString());
            tabelaDrugiRed.addCell("Seriski broj:\n" +
                "Serial number:    " + jTextFieldSerijskiBrojVazduhoplovaPLet.getText());
            document.add(tabelaDrugiRed);
            //Red nahnadno ubacen
            PdfPTable tabelaNahnadno = new PdfPTable(2);
            tabelaNahnadno.addCell("Nalet:\n" +
                "Onflight    " + jTextFieldNaletVazduhoplovaPLet.getText());
            tabelaNahnadno.addCell("Časova leta:\n" +
                "FH    " + jTextFieldCasovaLetaPLet.getText());
            tabelaNahnadno.addCell("Ciklusa:\n" +
                "FC    " + jTextFieldCiklusaVazduhoplovaPLeta.getText());
            document.add(tabelaNahnadno);
            //Treci red u tabeli
            PdfPTable tabelaTreciRed = new PdfPTable(2);
            tabelaTreciRed.addCell("PREDMET RADOVA:\n" +
                "Subject of work    " + jTextAreaPredmetRadovaPLeta.getText());
            tabelaTreciRed.addCell("RADNI NALOG    " + jComboBoxBRNaloga.getSelectedItem().toString());
            document.add(tabelaTreciRed);
            //Cetvrti i Peti red u tabeli
            PdfPTable tabelaCetvrtiPetiRed = new PdfPTable(4);
            tabelaCetvrtiPetiRed.addCell("Aerodrom:\n" +
                "Airport    " + jTextFieldAerodromPLeta.getText());
            tabelaCetvrtiPetiRed.addCell("Datum:\n" +
                "Date    " + Datum);
            tabelaCetvrtiPetiRed.addCell("Početak:\n" +
                "Started    " + jTextFieldPocetakPLeta.getText());
            tabelaCetvrtiPetiRed.addCell("Završetak:\n" +
                "Stopped    " + jTextFieldZavrsetakPLeta.getText());
            tabelaCetvrtiPetiRed.addCell("Pritisak vazduha\n" +
                "Air pressure    " + jTextFieldPritisakVazduhaPLeta.getText());
            tabelaCetvrtiPetiRed.addCell("Temperatura vazduha\n" +
                "Air temperature    " + jTextFieldTemperaturaVazduhaPLeta.getText());
            tabelaCetvrtiPetiRed.addCell("Visina leta\n" +
                "Flight altitude    " + jTextFieldVisinaLetaPLeta.getText());
            tabelaCetvrtiPetiRed.addCell("Pretpoletni pregled izvršio\n" +
                "Preflight check    " + jTextField_pppIzvrsio.getText());
            document.add(tabelaCetvrtiPetiRed);
            //Prazan red
            document.add(new Paragraph(" "));
            //Podaci koji se unose iz baze o motoru
            document.add(new Paragraph("1.     MOTOR",FontFactory.getFont(FontFactory.TIMES_BOLD,11,Font.BOLD)));
            document.add(new Paragraph("STARTOVANJE: " + jTextFieldStartovanjePLeta.getText() + ", ZAGREVANJE: " + jTextFieldZagrevanjePLeta.getText() + ", GREJAČ KARBURATORA: " + jTextFieldGKarburatoraPLeta.getText() + ", KOMANDA GASA: " + jTextFielKomandaGasaPLeta.getText() + ", KOMANDA SMEŠE: " + jTextFieldKomandaSemsePLeta.getText() + ", KOMANDA KORAKA ELISE: " + jTextFieldKomandaKorakaElisePLeta.getText() + ", RAD SLAVINA GORIVA: " + jTextFieldKRSGPLeta.getText() + ", PARKING KOČNICA: " + jTextFieldParkingKocnicaPLeta.getText() + ", PUNJENJE GENERATORA: " + jTextFieldPunjenjeGPLeta.getText() + ", TEMP. UQA: " + jTextFieldUQAPLeta.getText() + ", PRIT. UQA: " + jTextFieldPritUQAPLeta.getText() + ", PRIT. GORIVA: " + jTextFieldPritGorivaPLeta.getText() + ", PROTOK GORIVA: " + jTextFieldProtokGorivaPLeta.getText() + ", TGC: " + jTextFieldTGCPLeta.getText() + ", PROBA MAGNETA:   LEVI: " + jTextFieldPMLeviPLeta.getText() + ", DESNI: " + jTextFieldPMDesniPLeta.getText() + ", RAZLIKA OBRTAJA: " + jTextFieldPMROPLeta.getText()+ ", PROMENA KORAKA ELISE:   MALI KORAK: " + jTextFieldMaliKorakPLeta.getText() + " o/min " + ", VELIKI KORAK: " + jTextFieldVelikiKorakPLeta.getText() + " o/min "  + ", PUNA SNAGA:   BR. OBRTA: " + jTextFieldBRObrtaPLeta.getText() + ", MANO VAKUM: " + jTextFieldManoVakumPLeta.getText() + ", PROTOK GORIVA: " + jTextFieldProtokGorivaPleta.getText() + ", PROVERA SMEŠE: " + jTextFieldPSPleta.getText() + ", ŽALUZINE MOTORA: " + jTextFieldZaluzineMotoraPLeta.getText() + ", HLADNJAKA " + jTextFieldHladnjakaPLeta.getText() + "."));
            //Podaci koji se unose iz baze o proverama u letu
            document.add(new Paragraph("2.     PROVERA U LETU",FontFactory.getFont(FontFactory.TIMES_BOLD,11,Font.BOLD)));     
            document.add(new Paragraph("VOŽNJA PO ZEMLJI: " + jTextFieldVoznjaPoZemljPLeta.getText() + ", KOČNICE: " + jTextFieldKocnicePLeta.getText() + ", UPRAVLJIVOST U POLETANJU: " + jTextFieldUpravljivostUPoletanjuPLeta.getText() + ", STAJNI TRAP: " + jTextFielStajniTrapPLeta.getText() + ", RAD TRIMERA: " + jTextFieldRadTrimeraPLeta.getText() + ", RAD KOMANDI LETA U LETU: " + jTextFieldRKLULPLeta.getText() + ", RAD ZAKRILACA: " + jTextFieldRadZakrilcaPLeta.getText() + ", INDIKACIJA ZAKRILACA: " + jTextFieldIndikacijaZakrilcaPLeta.getText() + ", SVETLA UPOZORENJA: " + jTextFieldSvetlaUpozorenjaPLeta.getText() + ", RADIO STANICA: " + jTextFieldRadioStanicaPLeta.getText() + ", VOR: " + jTextFieldVORPLeta.getText() + ", ADF: " + jTextFieldADFPLeta.getText() + ", INTERFON: " + jTextFieldInterfonPLeta.getText() + ", POKAZIVANJE INSTRUMENATA: " + jTextFieldPokazivanjeIstrumenataPLeta.getText() + ", HORIZONTALNI LET: " + jTextFieldHorizontalniPLeta.getText() + ", PREVUČENI LET, BRZINA SLOMA UZGONA: " + jTextFieldPrevuceniLetOPLeta.getText() + ", LEVI I DESNI OŠTRI ZAOKRET: " + jTextFieldOstriZaokretPLeta.getText() + ", PRILAZ I SLETANJE: " + jTextField_sletanjePleta.getText() + ", OPŠTA ZAPAŽANJA PILOTA: " + jTextAreaZapazanjaPLeta.getText() + "."));
            //Prazan red
            document.add(new Paragraph(" "));
            //Kreiranje podnozje dokumenta
            PdfPTable podnozje = new PdfPTable(2);
            podnozje.addCell("Odobreno Rešenjem DCV broj:    " + jTextFieldOdobrenoPLeta.getText());
            podnozje.addCell("Pilot / Pilot\n" +
                "________________ "+ jTextFieldPilotPLeta.getText());
            document.add(podnozje);
            //zatvaranje 
            document.close();
            JOptionPane.showMessageDialog(null, "Uspešno ste kreirali Izveštaj sa Probnog Leta");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        // Otvaranje Dokumenta
        try {
            String doc = "IzveštajSaProbnogLeta.pdf";
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+doc);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Button_stampaProbniLetActionPerformed

    private void Button_deleteProbniLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteProbniLetActionPerformed
        // Brisanje Podataka iz baze
        int brisanje =JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete izabrani podatak","Obriši Izabrano",JOptionPane.YES_NO_OPTION);
        if(brisanje==0){
            String sql="Delete from izvestaj_sa_probnog_leta where id =?";
            try{

                pst=conn.prepareStatement(sql);
                pst.setString(1,jTextField_idProbnogLeta.getText());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Uspešno ste obrisali izabrane podatke iz baze podataka!","Obriši Izabrano",JOptionPane.OK_OPTION);

                Update_ProbniLet();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Button_deleteProbniLetActionPerformed

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

    private void jTableProbniLetKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableProbniLetKeyReleased
        // Popuna textboxa kada se krece korisnik kroz tabelu pomocu dugmica up down
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            try{
                int row =jTableProbniLet.getSelectedRow();
                String Table_click=(jTableProbniLet.getModel().getValueAt(row, 0).toString());
                String sql="select * from izvestaj_sa_probnog_leta where id='"+Table_click+"' ";
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                //popuna txt polja podacima iz tabele
                poljaProbniLet();
                popunaPoljaOVazduhoplovu();//popuna polja o vazduhoplovu
                popunaPoljaRadniNalog();//popuna polja o radnom nalogu
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jTableProbniLetKeyReleased

    private void jTableProbniLetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProbniLetMouseClicked
        // Kada se klikne na tebelu pokupiti podatke
        try{

            int row = jTableProbniLet.getSelectedRow();
            String Table_click = (jTableProbniLet.getModel().getValueAt(row, 0).toString());
            String sql = "select * from izvestaj_sa_probnog_leta where id='" + Table_click + "' ";//Upit za popunu textBox na klik u tabeli
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            //popuna txt polja podacima iz tabele
            poljaProbniLet();
            popunaPoljaOVazduhoplovu();//popuna polja o vazduhoplovu
            popunaPoljaRadniNalog();//popuna polja o radnom nalogu
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jTableProbniLetMouseClicked

    private void jComboBoxRGOznakePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxRGOznakePopupMenuWillBecomeInvisible
        popunaPoljaOVazduhoplovu();//popuna polja o vazduhoplovu
    }//GEN-LAST:event_jComboBoxRGOznakePopupMenuWillBecomeInvisible

    private void jComboBoxBRNalogaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxBRNalogaPopupMenuWillBecomeInvisible
        popunaPoljaRadniNalog();//popuna polja o radnom nalogu
    }//GEN-LAST:event_jComboBoxBRNalogaPopupMenuWillBecomeInvisible

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
            java.util.logging.Logger.getLogger(ProbniLet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProbniLet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProbniLet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProbniLet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProbniLet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_Exit;
    private javax.swing.JButton Button_clearProbniLet;
    private javax.swing.JButton Button_deleteProbniLet;
    private javax.swing.JButton Button_insertProbniLet;
    private javax.swing.JButton Button_internet;
    private javax.swing.JButton Button_proveraKonekcijaBaze;
    private javax.swing.JButton Button_refreshProbniLet;
    private javax.swing.JButton Button_singout;
    private javax.swing.JButton Button_stampaProbniLet;
    private javax.swing.JButton Button_updateProbniLet;
    private javax.swing.JMenuBar MenuBar_adminMeni;
    private javax.swing.JMenuItem MenuItem_Exit;
    private javax.swing.JMenuItem MenuItem_LogOut;
    private javax.swing.JMenuItem MenuItem_baza;
    private javax.swing.JMenuItem MenuItem_internet;
    private javax.swing.JMenu Menu_help;
    private javax.swing.JMenu Menu_opste;
    private javax.swing.JToolBar ToolBar_OpsteFunkcije;
    private javax.swing.JComboBox jComboBoxBRNaloga;
    private javax.swing.JComboBox jComboBoxRGOznake;
    private com.toedter.calendar.JDateChooser jDateChooser_DatumProbnogLeta;
    private javax.swing.JLabel jLabePokazivanjeIstrumenataPLeta;
    private javax.swing.JLabel jLabelADFPLeta;
    private javax.swing.JLabel jLabelAerodromPLeta;
    private javax.swing.JLabel jLabelBRObrtaPLeta;
    private javax.swing.JLabel jLabelBrNaloga;
    private javax.swing.JLabel jLabelCasovaLetaPLet;
    private javax.swing.JLabel jLabelCiklusaVazduhoplovaPLet;
    private javax.swing.JLabel jLabelDatumPLeta;
    private javax.swing.JLabel jLabelGKarburatoraPLeta;
    private javax.swing.JLabel jLabelGUPUPLeta;
    private javax.swing.JLabel jLabelHladnjakaPLeta;
    private javax.swing.JLabel jLabelHorizontalniPLeta;
    private javax.swing.JLabel jLabelIndikacijaZakrilca;
    private javax.swing.JLabel jLabelInterfonPLeta;
    private javax.swing.JLabel jLabelKRSGPLeta;
    private javax.swing.JLabel jLabelKocnicePLeta;
    private javax.swing.JLabel jLabelKomandaGasaPLeta;
    private javax.swing.JLabel jLabelKomandaKorakaElisePLeta;
    private javax.swing.JLabel jLabelKomandaSmesePLeta;
    private javax.swing.JLabel jLabelMaliKPLeta;
    private javax.swing.JLabel jLabelManaoVakumPLeta;
    private javax.swing.JLabel jLabelNaletVazduhoplovaPLet;
    private javax.swing.JLabel jLabelOdobrenoPLeta;
    private javax.swing.JLabel jLabelOstriZaokretPLeta;
    private javax.swing.JLabel jLabelPKEPLeta;
    private javax.swing.JLabel jLabelPMDesniPLeta;
    private javax.swing.JLabel jLabelPMLeviPLeta;
    private javax.swing.JLabel jLabelPMPLeta;
    private javax.swing.JLabel jLabelPPPregledIzvrsioPLeta;
    private javax.swing.JLabel jLabelParkingKocnicaPLeta;
    private javax.swing.JLabel jLabelPilotPLeta;
    private javax.swing.JLabel jLabelPocetakPLeta;
    private javax.swing.JLabel jLabelPozdravnaPoruka;
    private javax.swing.JLabel jLabelPredmetRadovaPLeta;
    private javax.swing.JLabel jLabelPrevuceniPLeta;
    private javax.swing.JLabel jLabelPritGorivaPLeta;
    private javax.swing.JLabel jLabelPritUQAPLeta;
    private javax.swing.JLabel jLabelPritisakVazduhaPLeta;
    private javax.swing.JLabel jLabelProizvodjacTip;
    private javax.swing.JLabel jLabelProtikGorivaPLeta;
    private javax.swing.JLabel jLabelProtokGorivaPLeta;
    private javax.swing.JLabel jLabelProveraSmesePLetaS;
    private javax.swing.JLabel jLabelPunaSnagaPLeta;
    private javax.swing.JLabel jLabelPunjenjeGPLeta;
    private javax.swing.JLabel jLabelRGOznake;
    private javax.swing.JLabel jLabelRKLULPLeta;
    private javax.swing.JLabel jLabelRadTrimeraPLeta;
    private javax.swing.JLabel jLabelRadZakrilcaPLeta;
    private javax.swing.JLabel jLabelRadioStanicaPLeta;
    private javax.swing.JLabel jLabelSerijskiBroj;
    private javax.swing.JLabel jLabelStajniTrapPLeta;
    private javax.swing.JLabel jLabelStartovanjePLeta;
    private javax.swing.JLabel jLabelSvetlaUpozorenjaPLeta;
    private javax.swing.JLabel jLabelTemperaturaVazduhaPLeta;
    private javax.swing.JLabel jLabelTgcPLeta;
    private javax.swing.JLabel jLabelUQUPLeta;
    public static javax.swing.JLabel jLabelUser;
    private javax.swing.JLabel jLabelVORPLeta;
    private javax.swing.JLabel jLabelVelikiKorakPLeta;
    private javax.swing.JLabel jLabelVisinaLetaPLeta;
    private javax.swing.JLabel jLabelVoznjaPoZemljiPLeta;
    private javax.swing.JLabel jLabelZagrevanjePLeta;
    private javax.swing.JLabel jLabelZaluzineMotoraPLeta;
    private javax.swing.JLabel jLabelZapazanjaPLeta;
    private javax.swing.JLabel jLabelZavrsetakPLeta;
    private javax.swing.JLabel jLabel_idProbnogLeta;
    private javax.swing.JLabel jLabel_sletanje;
    private javax.swing.JLabel jLabelaPMROPLeta;
    private javax.swing.JPanel jPanelMotor;
    private javax.swing.JPanel jPanelNeMenjati;
    private javax.swing.JPanel jPanelOsnovaPLeta;
    private javax.swing.JPanel jPanelPodaciVazduhoplov;
    private javax.swing.JPanel jPanelProveraULetu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneMotor;
    private javax.swing.JScrollPane jScrollPanePodaciVazduhoplova;
    private javax.swing.JScrollPane jScrollPaneProverauLetu;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPaneIZVESTAJ_SA_PROBNOG_LETA;
    private javax.swing.JTabbedPane jTabbedPaneIzvestajProbniLet;
    private javax.swing.JTable jTableProbniLet;
    private javax.swing.JTextArea jTextAreaPredmetRadovaPLeta;
    private javax.swing.JTextArea jTextAreaZapazanjaPLeta;
    private javax.swing.JTextField jTextFielKomandaGasaPLeta;
    private javax.swing.JTextField jTextFielStajniTrapPLeta;
    private javax.swing.JTextField jTextFieldADFPLeta;
    private javax.swing.JTextField jTextFieldAerodromPLeta;
    private javax.swing.JTextField jTextFieldBRObrtaPLeta;
    private javax.swing.JTextField jTextFieldCasovaLetaPLet;
    private javax.swing.JTextField jTextFieldCiklusaVazduhoplovaPLeta;
    private javax.swing.JTextField jTextFieldGKarburatoraPLeta;
    private javax.swing.JTextField jTextFieldHladnjakaPLeta;
    private javax.swing.JTextField jTextFieldHorizontalniPLeta;
    private javax.swing.JTextField jTextFieldIndikacijaZakrilcaPLeta;
    private javax.swing.JTextField jTextFieldInterfonPLeta;
    private javax.swing.JTextField jTextFieldKRSGPLeta;
    private javax.swing.JTextField jTextFieldKocnicePLeta;
    private javax.swing.JTextField jTextFieldKomandaKorakaElisePLeta;
    private javax.swing.JTextField jTextFieldKomandaSemsePLeta;
    private javax.swing.JTextField jTextFieldMaliKorakPLeta;
    private javax.swing.JTextField jTextFieldManoVakumPLeta;
    private javax.swing.JTextField jTextFieldNaletVazduhoplovaPLet;
    private javax.swing.JTextField jTextFieldOdobrenoPLeta;
    private javax.swing.JTextField jTextFieldOstriZaokretPLeta;
    private javax.swing.JTextField jTextFieldPMDesniPLeta;
    private javax.swing.JTextField jTextFieldPMLeviPLeta;
    private javax.swing.JTextField jTextFieldPMROPLeta;
    private javax.swing.JTextField jTextFieldPSPleta;
    private javax.swing.JTextField jTextFieldParkingKocnicaPLeta;
    private javax.swing.JTextField jTextFieldPilotPLeta;
    private javax.swing.JTextField jTextFieldPocetakPLeta;
    private javax.swing.JTextField jTextFieldPokazivanjeIstrumenataPLeta;
    private javax.swing.JTextField jTextFieldPrevuceniLetOPLeta;
    private javax.swing.JTextField jTextFieldPritGorivaPLeta;
    private javax.swing.JTextField jTextFieldPritUQAPLeta;
    private javax.swing.JTextField jTextFieldPritisakVazduhaPLeta;
    private javax.swing.JTextField jTextFieldProizvodjacVazduhoplov;
    private javax.swing.JTextField jTextFieldProtokGorivaPLeta;
    private javax.swing.JTextField jTextFieldProtokGorivaPleta;
    private javax.swing.JTextField jTextFieldPunjenjeGPLeta;
    private javax.swing.JTextField jTextFieldRKLULPLeta;
    private javax.swing.JTextField jTextFieldRadTrimeraPLeta;
    private javax.swing.JTextField jTextFieldRadZakrilcaPLeta;
    private javax.swing.JTextField jTextFieldRadioStanicaPLeta;
    private javax.swing.JTextField jTextFieldSerijskiBrojVazduhoplovaPLet;
    private javax.swing.JTextField jTextFieldStartovanjePLeta;
    private javax.swing.JTextField jTextFieldSvetlaUpozorenjaPLeta;
    private javax.swing.JTextField jTextFieldTGCPLeta;
    private javax.swing.JTextField jTextFieldTemperaturaVazduhaPLeta;
    private javax.swing.JTextField jTextFieldUQAPLeta;
    private javax.swing.JTextField jTextFieldUpravljivostUPoletanjuPLeta;
    private javax.swing.JTextField jTextFieldVORPLeta;
    private javax.swing.JTextField jTextFieldVelikiKorakPLeta;
    private javax.swing.JTextField jTextFieldVisinaLetaPLeta;
    private javax.swing.JTextField jTextFieldVoznjaPoZemljPLeta;
    private javax.swing.JTextField jTextFieldZagrevanjePLeta;
    private javax.swing.JTextField jTextFieldZaluzineMotoraPLeta;
    private javax.swing.JTextField jTextFieldZavrsetakPLeta;
    private javax.swing.JTextField jTextField_idProbnogLeta;
    private javax.swing.JTextField jTextField_pppIzvrsio;
    private javax.swing.JTextField jTextField_sletanjePleta;
    private javax.swing.JTextField txt_pretragaProbniLet;
    // End of variables declaration//GEN-END:variables
}
