package PMsystemDesign;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class PM_design extends JFrame implements ActionListener{
	Label label=null;
	
	JMenuBar menuBar = null;
	
	JMenu employee = null;
	JMenuItem entry = null;
	JMenuItem archivesM = null;
	
	JMenu remove= null;
	JMenuItem remove_details = null;
	JMenuItem remove_job = null;//��λ����
	JMenuItem remove_employee = null;//Ա������
	
	JMenu safety = null;
	JMenuItem safety_Pass = null;
	JMenuItem safety_Right = null;
	
	JMenu systemS = null;
	JMenuItem systemS_L = null;
	JMenuItem systemS_Q = null;
	
	JMenu help = null;
	JMenuItem help_A = null;
	
	private static DbProcess dbProcess;
	
	public PM_design(){
		label = new Label("��ҵ���¹���ϵͳ");
		label.setBackground(SystemColor.activeCaption);
		label.setFont(new Font("Harrington", Font.BOLD | Font.ITALIC, 50));
		label.setForeground(Color.BLUE);
		this.setTitle("���ã�����Ա");
		this.getContentPane().setForeground(Color.LIGHT_GRAY);
		this.getContentPane().add(label, BorderLayout.CENTER);
		this.setBounds(100, 100, 450, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		employee = new JMenu("Ա������");
		menuBar.add(employee);
		entry = new JMenuItem("��Ϣ¼��");
		employee.add(entry);
		archivesM = new JMenuItem("��������");
		employee.add(archivesM);
		
		remove= new JMenu("��������");
		menuBar.add(remove);
		remove_details = new JMenuItem("������ϸ");
		remove.add(remove_details);
		remove_job = new JMenuItem("��λ����");
		remove.add(remove_job);
		remove_employee = new JMenuItem("Ա������");
		remove.add(remove_employee);
	
		safety = new JMenu("��ȫ����");
		menuBar.add(safety);
		safety_Pass = new JMenuItem("������֤");
		safety.add(safety_Pass);
		safety_Right = new JMenuItem("Ȩ������");
		safety.add(safety_Right);
		
		systemS = new JMenu("ϵͳ����");
		menuBar.add(systemS);
		systemS_L = new JMenuItem("���ص�¼����");
		systemS.add(systemS_L);
		systemS_Q = new JMenuItem("�˳�ϵͳ");
		systemS.add(systemS_Q);
		
		help = new JMenu("����");
		menuBar.add(help);
		help_A = new JMenuItem("����");
		help.add(help_A);
		
		entry.addActionListener(this);
		archivesM.addActionListener(this);
		remove_details.addActionListener(this);
		remove_job.addActionListener(this);
		remove_employee.addActionListener(this);
		safety_Pass.addActionListener(this);
		safety_Right.addActionListener(this);
		systemS_L.addActionListener(this);
		systemS_Q.addActionListener(this);
		help_A.addActionListener(this);
		
		dbProcess = new DbProcess();	
	}
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PM_design window = new PM_design();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("��Ϣ¼��")){
			archives_entry en= new archives_entry();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("��������")) {
			archivesM archivesM= new archivesM();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("���ص�¼����")){
			System.out.println("�˳���¼��");
			PM_login log=new PM_login();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("�˳�ϵͳ")){
			System.out.println("�˳�ϵͳ��");
			System.exit(0);
		}else if(e.getActionCommand().equals("������ϸ")){
			remove_details detailsR = new  remove_details();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("��λ����")){
			remove_job remove_job = new  remove_job();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("Ա������")){
			remove_employee remove_employee = new  remove_employee();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("������֤")){
			passwordM passwordM = new  passwordM();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("Ȩ������")){
			authorityM authorityM = new  authorityM();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("����")){
			
		}
	}
}
