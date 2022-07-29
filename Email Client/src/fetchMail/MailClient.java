package fetchMail;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.mail.*;

public class MailClient extends javax.swing.JFrame 
{
	
    public MailClient() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() 
    {

        lserver = new javax.swing.JLabel();
        tserver = new javax.swing.JTextField();
        luname = new javax.swing.JLabel();
        tuname = new javax.swing.JTextField();
        lpass = new javax.swing.JLabel();
        tpass = new javax.swing.JTextField();
        fetchBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        message = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lserver.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lserver.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lserver.setText("SMPT Server");

        luname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        luname.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        luname.setText("User Name");

        lpass.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lpass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lpass.setText("Password");


        fetchBtn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        fetchBtn.setText("Fetch");
        fetchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fetchBtnActionPerformed(evt);
            }
        });

        message.setColumns(20);
        message.setRows(5);
        message.setEditable(false);
        jScrollPane1.setViewportView(message);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lserver, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tserver, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(luname, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tuname, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lpass, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tpass, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(fetchBtn)))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lserver, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tserver, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(luname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tuname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lpass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tpass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fetchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void fetchBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
        fetchmail();
    }
    
    private void fetchmail()
    {
    	String popServer,popUser,popPassword;
    	popServer=tserver.getText();
    	popUser=tuname.getText();
    	popPassword=tpass.getText();
    	
    	System.out.println(popServer);
    	try {
    		receive(popServer,popUser,popPassword);
    	}
    	catch(Exception ex)
    	{
    		System.out.println(ex);
    	}
    }
    
    public void receive(String popServer, String popUser, String popPassword)
    {
    	Store store=null;
    	Folder folder=null;
    	try {
    		Properties props=new Properties();
    		props.put("mail.pop3.host","pop.gmail.com");
    		props.put("mail.pop3.ssl.enable", "true");
    		props.put("mail.pop3.user", popUser);
    		props.put("mail.pop3.socketFactory", 995);
    		props.put("mail.pop3.socketFactory.class","javax.net.ssl.SSLSocketFactory");
    		props.put("mail.pop3.port", 995);
    		Session session=Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(popUser, popPassword);

                }
            });  
    		store=(Store)session.getStore("pop3");
    		store.connect(popServer,995,popUser,popPassword);
    		
    		folder=store.getDefaultFolder();
    		if(folder==null) throw new Exception("No default folder");
    		
    		folder=folder.getFolder("INBOX");
    		if(folder==null) throw new Exception("No POP3 INBOX");
    		
    		folder.open(Folder.READ_ONLY);
    		Message msgs[]=folder.getMessages();
    		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
    		
    		message.setText("");
    		int msgNum;
    		for(msgNum=0;msgNum<msgs.length;msgNum++)
    		{
    			System.out.println(msgNum+": "+msgs[msgNum].getFrom()[0]+"\t"+msgs[msgNum].getSubject());
    			System.out.println("Do you want to read message? [YES to read/QUIT to end]");
    			String line=reader.readLine();
    			String y="yes";
    			int j=line.compareTo(y);
    			if(j==0)
    			{
    				msgs[msgNum].writeTo(System.out);
    				Object content=msgs[msgNum].getContent();
    				
    				if(content instanceof Multipart)
    				{
    					StringBuffer messageContent=new StringBuffer();
    					StringBuffer msg=new StringBuffer();
    					Multipart multipart=(Multipart)content;
    					for (int i = 0; i < multipart.getCount(); i++) 
			            {
			                Part part = (Part) multipart.getBodyPart(i);
			                if (part.isMimeType("text/plain")) 
			                {
			                    msg = messageContent.append(part.getContent().toString());		                    
			                    String msg1 = new String();
			                    String from = new String();
			                    String subj = new String();
			                    int k ;
			                    k = msgNum;
			                    
			                    msg1 = msg.toString();
			                    subj = msgs[msgNum].getSubject();
			                    from = msgs[msgNum].getFrom()[0].toString();
			                    
			                    message.append("Message No:"+(k+1)+"\n");
			                    message.append("Message From:"+from+"\n");
			                    message.append("Message Subject:"+subj+"\n");
			                    message.append("\n"+msg1+"\n"); 
			                }
			            }
    				}
    				else
    				{
    					System.out.println("Closing Previous Message. Going For Next.");
    				}
    			}
    		}
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    	}
    	finally
    	{
    		try {
    			if (folder!=null) folder.close(false);
				if (store!=null) store.close();
    		}
    		catch (Exception ex2) {ex2.printStackTrace();}
    	}
    }

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
            java.util.logging.Logger.getLogger(MailClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MailClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MailClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MailClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MailClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton fetchBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lpass;
    private javax.swing.JLabel lserver;
    private javax.swing.JLabel luname;
    private javax.swing.JTextArea message;
    private javax.swing.JTextField tpass;
    private javax.swing.JTextField tserver;
    private javax.swing.JTextField tuname;
    // End of variables declaration                   
}