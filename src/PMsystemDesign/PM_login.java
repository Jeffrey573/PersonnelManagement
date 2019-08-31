package PMsystemDesign;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
public class PM_login extends JFrame implements ActionListener {
	JLabel label_identify=null;
	JLabel id=null;
	JLabel password=null;
	
	JTextField user_ID=null;
	JPasswordField passwordField=null;
	
	JButton JBt_1=null;
	JButton JBt_2=null;
	
	JComboBox identify=null;
	
	private static DbProcess dbProcess;
	String identifyFieldStr="����Ա";
	
	public PM_login(){
		id=new JLabel("Ա����:");
		password=new JLabel(" ����:");
		
		user_ID=new JTextField(10);
		passwordField = new JPasswordField();
		
		JBt_1=new JButton("��¼");
		JBt_2=new JButton("�˳�");
		
		JBt_1.addActionListener(this);
		JBt_2.addActionListener(this);
		passwordField.addKeyListener(new java.awt.event.KeyAdapter(){
			public void keyTyped(java.awt.event.KeyEvent e){
				if(e.getKeyChar()=='\n'){
					JBt_1.doClick();
				}
			}
		});
		
		this.setTitle("��ҵ���¹���ϵͳ��¼");
		this.setBounds(100, 100, 300,250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		
		id.setBounds(69, 66, 43, 15);
		this.getContentPane().add(id);
		password.setBounds(78, 100, 77, 15);
		this.getContentPane().add(password);
		user_ID.setBounds(117, 63, 84, 21);
		this.getContentPane().add(user_ID);
		passwordField.setBounds(117, 97, 84, 21);
		this.getContentPane().add(passwordField);
		JBt_1.setBounds(35, 155, 84, 23);
		this.getContentPane().add(JBt_1);
		JBt_2.setBounds(138, 155, 84, 23);
		this.getContentPane().add(JBt_2);
		
		identify = new JComboBox();
		identify.setModel(new DefaultComboBoxModel(new String[] {"����Ա", "Ա��"}));
		identify.setBounds(117, 34, 77, 23);
		this.getContentPane().add(identify);
		identify.addItemListener(new ItemListener() {//�������¼�����  
            public void itemStateChanged(ItemEvent event) {  
                switch (event.getStateChange()) {  
                case ItemEvent.SELECTED:  
                	identifyFieldStr = (String) event.getItem();  
                    System.out.println("ѡ�У�" + identifyFieldStr);  
                    break;  
                case ItemEvent.DESELECTED:  
                    System.out.println("ȡ��ѡ�У�" + event.getItem());  
                    break;  
                }  
            }  
        });
		
		label_identify = new JLabel("��ݣ�");
		label_identify.setBounds(78, 38, 54, 15);
		this.getContentPane().add(label_identify);
		
		dbProcess = new DbProcess();
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("�˳�")){
			System.out.println("�����˳�!");
			System.exit(0);
		}else{
			try{
				String userStr=user_ID.getText().trim().replace(" ", "");
				String passStr=new String(passwordField.getPassword()).trim().replace(" ", "");
				if(!checkLogin(userStr,passStr)){
					JOptionPane.showMessageDialog(null, "�û����������޷���¼��","��¼ʧ��",JOptionPane.ERROR_MESSAGE);
					return;
				}else{
					if(checkIdentify(identifyFieldStr)&&identifyFieldStr.equals("����Ա")) {
						PM_design window = new PM_design();
						this.setVisible(false);
					}else if(identifyFieldStr.equals("����Ա")&&user_ID.getText().trim().equals("000")&&passwordField.getText().trim().equals("000")){
						PM_design window = new PM_design();
						this.setVisible(false);
					}else if(checkIdentify(identifyFieldStr)&&identifyFieldStr.equals("Ա��")){
						foremployee foremployee = new foremployee();
						foremployee.PID_R=user_ID.getText().trim();
						this.setVisible(false);
					}else {
						JOptionPane.showMessageDialog(null, "����Ȩ�ޣ����������ٵ�¼��","��¼ʧ��",JOptionPane.ERROR_MESSAGE);
						return;
					}
			}
		}catch(Exception e1){
			e1.printStackTrace();
			}	
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PM_login log=new PM_login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public boolean checkLogin(String userStr,String passStr) throws SQLException{
		String sql="select * from person;";
		dbProcess.connect();
		ResultSet rs = dbProcess.executeQuery(sql);
		System.out.println(rs);
		int flag=0;
		while(rs.next()){
			if(userStr.equals(rs.getString("PID"))&&passStr.equals(rs.getString("Ppasswd"))){
				flag=1;
				break;
			}
		}
		if(flag==1)
			  return true;
		return false;
	}
	public boolean checkIdentify(String idenStr) throws SQLException{
		String PID=user_ID.getText().trim().replace(" ", "");
		String sql="select * from person where PID = '" + PID+ "';";
		dbProcess.connect();
		ResultSet rs = dbProcess.executeQuery(sql);
		int flag=0;
		while(rs.next()){
			if(PID.equals(rs.getString("PID"))&&idenStr.equals(rs.getString("Pauthority"))){
				System.out.println(idenStr);
				flag=1;
				break;
			}
		}
		if(flag==1)
			  return true;
		return false;
	}
}
