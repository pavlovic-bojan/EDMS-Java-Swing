import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
/**
 *
 * @author Bojan
 */
public class Login extends javax.swing.JFrame {

    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    /**
     * Creates new form QEDMS_Login
     */
    public Login() {

        initComponents();
        conn=Data.javaconnect.ConnecrDb();// Preusmerenje na klasu za konekciju na bazu
        ComboBox_ulogakorisnika();
    }
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Label_username = new javax.swing.JLabel();
        Label_password = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        txt_userName = new javax.swing.JTextField();
        Button_login = new javax.swing.JButton();
        Button_forget_password = new javax.swing.JButton();
        ComboBox_ulogakorisnika = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        Label_logo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EDMS Login");
        setName("loginFrame"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ulaz na Sistem EDMS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(0, 0, 153))); // NOI18N

        Label_username.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Label_username.setText("Korisničko Ime");

        Label_password.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Label_password.setText("Lozinka");

        txt_password.setToolTipText("Unesite Vašu lozinku");

        txt_userName.setToolTipText("Unesite Vaše Korisničko Ime");

        Button_login.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_login.setText("Ulaz na Sistem");
        Button_login.setToolTipText("Ulaz na Sistem ako su svi podaci tačni koji su iznad upisani");
        Button_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_loginActionPerformed(evt);
            }
        });
        Button_login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Button_loginKeyPressed(evt);
            }
        });

        Button_forget_password.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Button_forget_password.setText("Zaboravljena Lozinka");
        Button_forget_password.setToolTipText("Biće te preusmereni u deo za obnovu lozinke");
        Button_forget_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_forget_passwordActionPerformed(evt);
            }
        });
        Button_forget_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Button_forget_passwordKeyPressed(evt);
            }
        });

        ComboBox_ulogakorisnika.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Izaberite Prava Pristupa" }));
        ComboBox_ulogakorisnika.setToolTipText("Izaberite koja su Vam prava pristupa dodeljena");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Prava Pristupa");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Label_username)
                    .addComponent(Label_password, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_forget_password)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ComboBox_ulogakorisnika, 0, 190, Short.MAX_VALUE)
                        .addComponent(txt_userName)
                        .addComponent(txt_password)
                        .addComponent(Button_login, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_username)
                    .addComponent(txt_userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_password)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox_ulogakorisnika, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(12, 12, 12)
                .addComponent(Button_login)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Button_forget_password)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        Label_username.getAccessibleContext().setAccessibleName("Label_UserName");
        Label_password.getAccessibleContext().setAccessibleName("Label_Password");
        txt_password.getAccessibleContext().setAccessibleName("Texbox_Pass");
        txt_userName.getAccessibleContext().setAccessibleName("TexBox_UserName");
        Button_login.getAccessibleContext().setAccessibleName("Button_Login");
        Button_forget_password.getAccessibleContext().setAccessibleName("Button_Forget_Password");

        Label_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafika/Users-icon.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label_logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(Label_logo)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(639, 286));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void login(){
    //sql upit za login
    String sql ="select * from korisnici where Korisnicko_Ime=? and lozinka=? and id=?";

        try{

            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_userName.getText());
            pst.setString(2,txt_password.getText());
            pst.setInt(3,ComboBox_ulogakorisnika.getSelectedIndex());
            int u=ComboBox_ulogakorisnika.getSelectedIndex();
            rs=pst.executeQuery();

            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Korisničko Ime, Lozinka i Prava Pristupa se podudaraju Vi ste se Uspešno Ulogovali na Sistem EDMS!");
                rs.close();
                pst.close();
                //Otvaranje prozora shodno pravima pristupa
                switch(u){
                    case 1:
                        dispose();//zatvaranje prozora
                        new Admin().setVisible(true);
                        Admin.jLabelUser.setText(this.txt_userName.getText());
                        break;
                    case 2:
                        dispose();//zatvaranje prozora
                        new Servis().setVisible(true); 
                        Servis.jLabelUser.setText(this.txt_userName.getText());
                        break;
                    case 3:
                        dispose();//zatvaranje prozora
                        new ProbniLet().setVisible(true); 
                        ProbniLet.jLabelUser.setText(this.txt_userName.getText());
                        break;
                }                
            }
            else{
                JOptionPane.showMessageDialog(null, "Korisničko Ime, Lozinka ili Prava Pristupa se ne podudaraju da bi ste se Uspešno Ulogovali na Sistem EDMS morate uneti tačne podatke!");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try{
                rs.close();
                pst.close();
            }catch(Exception e){
            }
        }
        try{
            //upit za ubacivanje podataka ko se logovo i ko pokusava sa kojim podacima da se loguje
            String sql1="Insert into pracenje_login(Ime_Korisnika,id_Uloge) values (?,?)";
            pst=conn.prepareStatement(sql1);
            pst.setString(1,txt_userName.getText());
            pst.setInt(2,ComboBox_ulogakorisnika.getSelectedIndex());
            pst.execute();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Vaši podaci o ulazu na sistem nisu uspešno sačuvani!","Unos Podatkaka",JOptionPane.OK_OPTION);
        }
    
    }
    
    public void zaboravljenaLozinka(){
        JOptionPane.showMessageDialog(null, "Bićete preusmereni na sistem obnove Lozinke!");
        //otvaranje prozora Password
        dispose();//zatvaranje prozora
        new Password().setVisible(true);
    }
    
    private void Button_forget_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_forget_passwordActionPerformed
        //otvaranje prozora Password
        zaboravljenaLozinka();
    }//GEN-LAST:event_Button_forget_passwordActionPerformed

    private void ComboBox_ulogakorisnika(){
    
        try{
        String sql="select * from uloge_za_login";//Upit za popunu uloga za login
        
        pst=conn.prepareStatement(sql);     
        rs=pst.executeQuery();
            while(rs.next()){
            
                String Naziv=rs.getString("Naziv");
                ComboBox_ulogakorisnika.addItem(Naziv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    /**
     * 
     * @event dogadjaj na klik dugmeta za login
     *        
     */
// dogadjaj na klik dugmeta za login
    private void Button_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_loginActionPerformed
        //login kada se pritisne na dugme za login
        login();
    }//GEN-LAST:event_Button_loginActionPerformed

    private void Button_loginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Button_loginKeyPressed
        // login kada se pretisne enter
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            login();            
        }
    }//GEN-LAST:event_Button_loginKeyPressed

    private void Button_forget_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Button_forget_passwordKeyPressed
        // preusmerenje na zaboravljena lozinka kada se pritisne enter
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        //otvaranje prozora Password
        zaboravljenaLozinka();            
        }
    }//GEN-LAST:event_Button_forget_passwordKeyPressed
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

            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {

            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {

            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {

            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }

        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_forget_password;
    private javax.swing.JButton Button_login;
    private javax.swing.JComboBox ComboBox_ulogakorisnika;
    private javax.swing.JLabel Label_logo;
    private javax.swing.JLabel Label_password;
    private javax.swing.JLabel Label_username;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_userName;
    // End of variables declaration//GEN-END:variables
}