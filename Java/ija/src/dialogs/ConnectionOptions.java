/*
 * ConnectionOptions.java
 *
 * Created on 27. duben 2009, 15:08
 */
package dialogs;

import javax.swing.JOptionPane;

/**
 *
 * @author  ivo
 */
public class ConnectionOptions extends javax.swing.JDialog {

     java.awt.Frame ff;

     /** Creates new form ConnectionOptions */
     public ConnectionOptions(java.awt.Frame parent, boolean modal) {
	  super(parent, modal);
	  initComponents();
	  ff = parent;

	  jLabel1.setText("Connection settings");
	  jRadioButton1.setText("Connect to server");
	  jRadioButton2.setText("Work offline");
	  jRadioButton3.setText("send data to server");
	  jRadioButton4.setText("recieve data from server");
	  jButton2.setText("Register");
	  jLabel3.setText("Server:");
	  jButton1.setText("Login");
	  jTextField2.setText("localhost");
	  jTextField1.setText("11111");
	  jLabel2.setText("Port:   ");
	  jLabel4.setText("Firstly:");
	  jButton3.setText("Close");
	  jLabel1.setText("Connection settings:");
	  jLabel6.setText("Login:");
	  jLabel5.setText("Password:");



	  pack();
     }

     /** This method is called from within the constructor to
      * initialize the form.
      * WARNING: Do NOT modify this code. The content of this method is
      * always regenerated by the Form Editor.
      */
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
     private void initComponents() {
          java.awt.GridBagConstraints gridBagConstraints;

          jScrollPane1 = new javax.swing.JScrollPane();
          jTextArea1 = new javax.swing.JTextArea();
          buttonGroup1 = new javax.swing.ButtonGroup();
          buttonGroup2 = new javax.swing.ButtonGroup();
          jLabel1 = new javax.swing.JLabel();
          jRadioButton1 = new javax.swing.JRadioButton();
          jRadioButton2 = new javax.swing.JRadioButton();
          jRadioButton3 = new javax.swing.JRadioButton();
          jRadioButton4 = new javax.swing.JRadioButton();
          jButton1 = new javax.swing.JButton();
          jButton2 = new javax.swing.JButton();
          jPanel1 = new javax.swing.JPanel();
          jLabel3 = new javax.swing.JLabel();
          jTextField2 = new javax.swing.JTextField();
          jPanel2 = new javax.swing.JPanel();
          jLabel2 = new javax.swing.JLabel();
          jTextField1 = new javax.swing.JTextField();
          jButton3 = new javax.swing.JButton();
          jLabel4 = new javax.swing.JLabel();
          jTextField3 = new javax.swing.JTextField();
          jPasswordField1 = new javax.swing.JPasswordField();
          jLabel5 = new javax.swing.JLabel();
          jLabel6 = new javax.swing.JLabel();

          jScrollPane1.setName("jScrollPane1"); // NOI18N

          jTextArea1.setColumns(20);
          jTextArea1.setRows(5);
          jTextArea1.setName("jTextArea1"); // NOI18N
          jScrollPane1.setViewportView(jTextArea1);

          setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
          setName("Form"); // NOI18N
          setResizable(false);
          getContentPane().setLayout(new java.awt.GridBagLayout());




          jLabel1.setName("jLabel1"); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 0;
          gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
          getContentPane().add(jLabel1, gridBagConstraints);

          buttonGroup1.add(jRadioButton1);
          jRadioButton1.setSelected(true);

          jRadioButton1.setName("jRadioButton1"); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 3;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          getContentPane().add(jRadioButton1, gridBagConstraints);

          buttonGroup1.add(jRadioButton2);

          jRadioButton2.setName("jRadioButton2"); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 3;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          getContentPane().add(jRadioButton2, gridBagConstraints);

          buttonGroup2.add(jRadioButton3);
          jRadioButton3.setSelected(true);

          jRadioButton3.setName("jRadioButton3"); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 7;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
          getContentPane().add(jRadioButton3, gridBagConstraints);

          buttonGroup2.add(jRadioButton4);

          jRadioButton4.setName("jRadioButton4"); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 7;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
          getContentPane().add(jRadioButton4, gridBagConstraints);


          jButton1.setName("jButton1"); // NOI18N
          jButton1.addActionListener(new java.awt.event.ActionListener() {
               public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 8;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
          getContentPane().add(jButton1, gridBagConstraints);


          jButton2.setName("jButton2"); // NOI18N
          jButton2.addActionListener(new java.awt.event.ActionListener() {
               public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 8;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
          getContentPane().add(jButton2, gridBagConstraints);

          jPanel1.setName("jPanel1"); // NOI18N
          jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));


          jLabel3.setName("jLabel3"); // NOI18N
          jPanel1.add(jLabel3);


          jTextField2.setName("jTextField2"); // NOI18N
          jTextField2.addActionListener(new java.awt.event.ActionListener() {
               public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jTextField2ActionPerformed(evt);
               }
          });
          jPanel1.add(jTextField2);

          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 4;
          gridBagConstraints.gridwidth = 3;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
          getContentPane().add(jPanel1, gridBagConstraints);

          jPanel2.setName("jPanel2"); // NOI18N
          jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));


          jLabel2.setName("jLabel2"); // NOI18N
          jPanel2.add(jLabel2);


          jTextField1.setName("jTextField1"); // NOI18N
          jPanel2.add(jTextField1);

          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 5;
          gridBagConstraints.gridwidth = 3;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
          getContentPane().add(jPanel2, gridBagConstraints);


          jButton3.setName("jButton3"); // NOI18N
          jButton3.addActionListener(new java.awt.event.ActionListener() {
               public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton3ActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 9;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
          getContentPane().add(jButton3, gridBagConstraints);


          jLabel4.setName("jLabel4"); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 6;
          gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
          getContentPane().add(jLabel4, gridBagConstraints);

          jTextField3.setName("jTextField3"); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
          getContentPane().add(jTextField3, gridBagConstraints);

          jPasswordField1.setName("jPasswordField1"); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 2;
          gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
          getContentPane().add(jPasswordField1, gridBagConstraints);


          jLabel5.setName("jLabel5"); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 2;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          getContentPane().add(jLabel5, gridBagConstraints);


          jLabel6.setName("jLabel6"); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          getContentPane().add(jLabel6, gridBagConstraints);

          pack();
     }// </editor-fold>//GEN-END:initComponents
     
     
     private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
     // TODO add your handling code here:
     }//GEN-LAST:event_jTextField2ActionPerformed

     private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
	  // TODO add your handling code here: //LOGIN
	  
	  if ( this.jRadioButton1.isSelected() )
	  {
  	  if ( this.jTextField2.getText().equals("") )
  	  {
  	     JOptionPane.showMessageDialog(ff, "Empty host address", "Error", JOptionPane.ERROR_MESSAGE); 
  	     return;
  	  }	  
  	  
  	  try {
  	       this.port = Integer.parseInt(this.jTextField1.getText());
  
  	  } catch (NumberFormatException ex) {
  	       //niaky dialog box o chybe portu
  	       JOptionPane.showMessageDialog(ff, "Port hase to be a number!", "Error", JOptionPane.ERROR_MESSAGE);
  	       return;
  	  }
  	}
	  
	  
	  
	  
	  if (this.jRadioButton1.isSelected()) {
	       this.onlineLogginning = true;
	  } else {
	       this.onlineLogginning = false;
	  }
	  if (this.jRadioButton3.isSelected()) {
	       this.sendToServerFirst = true;
	  } else {
	       this.sendToServerFirst = false;
	  }
	  this.action = "LOGIN";
	  this.setVisible(false);
     }//GEN-LAST:event_jButton1ActionPerformed

     private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
	  // TODO add your handling code here: REGISTER
	  
	  if ( this.jRadioButton1.isSelected() )
	  {
  	  if ( this.jTextField2.getText().equals("") )
  	  {
  	     JOptionPane.showMessageDialog(ff, "Empty host address", "Error", JOptionPane.ERROR_MESSAGE); 
  	     return;
  	  }	  
  	  
  	  try {
  	       this.port = Integer.parseInt(this.jTextField1.getText());
  
  	  } catch (NumberFormatException ex) {
  	       //niaky dialog box o chybe portu
  	       JOptionPane.showMessageDialog(ff, "Port hase to be a number!", "Error", JOptionPane.ERROR_MESSAGE);
  	       return;
  	  }
  	}
	  if (this.jRadioButton1.isSelected()) {
	       this.onlineLogginning = true;
	  } else {
	       this.onlineLogginning = false;
	  }
	  if (this.jRadioButton3.isSelected()) {
	       this.sendToServerFirst = true;
	  } else {
	       this.sendToServerFirst = false;
	  }
	  this.action = "REGISTER";
	  this.setVisible(false);
     }//GEN-LAST:event_jButton2ActionPerformed

     private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
	  // TODO add your handling code here: CLOSE
	  this.setVisible(false);
	  this.setDefaultCloseOperation(this.HIDE_ON_CLOSE);
	  //this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
     }//GEN-LAST:event_jButton3ActionPerformed

     /**
      * @param args the command line arguments
      */
     public static void main(String args[]) {
	  java.awt.EventQueue.invokeLater(new Runnable() {

	       public void run() {
		    ConnectionOptions dialog = new ConnectionOptions(new javax.swing.JFrame(), true);
		    dialog.addWindowListener(new java.awt.event.WindowAdapter() {

			 public void windowClosing(java.awt.event.WindowEvent e) {
			      System.exit(0);
			 }
		    });
		    dialog.setVisible(true);
	       }
	  });
     }
     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.ButtonGroup buttonGroup1;
     private javax.swing.ButtonGroup buttonGroup2;
     private javax.swing.JButton jButton1;
     private javax.swing.JButton jButton2;
     private javax.swing.JButton jButton3;
     private javax.swing.JLabel jLabel1;
     private javax.swing.JLabel jLabel2;
     private javax.swing.JLabel jLabel3;
     private javax.swing.JLabel jLabel4;
     private javax.swing.JLabel jLabel5;
     private javax.swing.JLabel jLabel6;
     private javax.swing.JPanel jPanel1;
     private javax.swing.JPanel jPanel2;
     private javax.swing.JPasswordField jPasswordField1;
     private javax.swing.JRadioButton jRadioButton1;
     private javax.swing.JRadioButton jRadioButton2;
     private javax.swing.JRadioButton jRadioButton3;
     private javax.swing.JRadioButton jRadioButton4;
     private javax.swing.JScrollPane jScrollPane1;
     private javax.swing.JTextArea jTextArea1;
     private javax.swing.JTextField jTextField1;
     private javax.swing.JTextField jTextField2;
     private javax.swing.JTextField jTextField3;
     // End of variables declaration//GEN-END:variables
     private String action ="";
     private int port;
     private boolean onlineLogginning;
     private boolean sendToServerFirst;

      public String getAction()
      {
	       return this.action;      
      }
      
     public String getLogin() {
	  return this.jTextField3.getText();
     }

     public String getPassword() {
	  return this.jPasswordField1.getText();
     }

     public String getServerName() {
	  return this.jTextField2.getText();
     }

     public int getServerPort() {
	  return this.port;
     }

     public boolean isOnlineLoggining() {
	  return this.onlineLogginning;
     }

     public boolean sendDataToServerFirst() {
	  return sendToServerFirst;
     }
}
