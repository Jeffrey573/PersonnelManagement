package PMsystemDesign;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class archivesM extends JFrame implements ActionListener{
	JLabel label = null;
	JLabel label_same = null;
	JLabel label_name=null;
	JLabel label_sex=null;
	JLabel label_birthday=null;
	//JLabel label_birthday_1=null;
	JLabel label_department=null;
	JLabel label_job=null;
	JLabel label_education=null;
	//JLabel label_education_1= null;
	JLabel label_specialty=null;
	JLabel label_address=null;
	JLabel label_tel=null;
	JLabel label_email=null;
	JLabel label_status=null;
	//JLabel label_status_1=null;
	JLabel label_remark=null;
	
	JTextField fillinField = null;
	JTextField name=null;
	JTextField sex=null;
	JTextField birthday=null;
	JTextField department=null;
	JTextField job=null;
	JTextField education=null;
	JTextField specialty=null;
	JTextField address=null;
	JTextField tel=null;
	JTextField email=null;
	JTextField status=null;
	JTextField remark=null;
	
	JButton button_alter=null;
	JButton button_query = null;
	JButton button_queryall = null;
	JButton button_delete = null;
	JButton button_deleteall = null;
	JButton back = null;
	
	JPanel jP1, jP2,jP3,jP4,jP5,jP6= null;
	JPanel jPTop, jPBottom = null;
	DefaultTableModel employeeTableModel = null;
	JTable employeeJTable = null;
	JScrollPane employeeJScrollPane = null;
	Vector employeeVector = null;
	Vector titleVector = null;

	JComboBox selectBox = null;
	
	private static DbProcess dbProcess;
	String SQFieldStr = "Ա����";
	
	public archivesM(){
		this.setTitle("��������");
		this.setBounds(200, 30, 850,650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new GridLayout(2, 1));
		this.setVisible(true);
		
		label = new JLabel("Ա����Ϣ��");
		label_same = new JLabel(" =");
		label_name=new JLabel("*������");
		label_sex=new JLabel("*�Ա�");
		label_birthday=new JLabel("*���գ�");
		//label_birthday_1=new JLabel("��ʽ����-��-��");
		label_department=new JLabel("*���ţ�");
		label_job=new JLabel("*ְ��");
		label_education=new JLabel("*�����̶ȣ�");
		//label_education_1= new JLabel("ע��Сѧ, ����, ����, ְ��, ��, ��ר, ˶ʿ, ��ʿ, ��ʿ��");
		label_specialty=new JLabel("רҵ���ܣ�");
		label_address=new JLabel("��ͥסַ��");
		label_tel=new JLabel("*��ϵ�绰��");
		label_email=new JLabel("�����ʼ���");
		label_status=new JLabel("*��ǰ״̬��");
		//label_status_1=new JLabel("ע����T��ΪԱ������F��Ϊ��Ա��������*��Ϊ������");
		label_remark=new JLabel("��ע��");
		
		fillinField = new JTextField(16);
		name=new JTextField(20);
		sex=new JTextField(2);
		birthday=new JTextField(10);
		department=new JTextField(20);
		job=new JTextField(10);
		education=new JTextField(8);
		specialty=new JTextField(35);
		address=new JTextField(40);
		tel=new JTextField(15);
		email=new JTextField(20);
		status=new JTextField(2);
		remark=new JTextField(30);
		
		button_query = new JButton("��ѯ");
		button_queryall = new JButton("��ѯ���м�¼");
		button_alter = new JButton("�޸���Ϣ");
		button_delete = new JButton("ɾ����ǰ��¼");
		button_deleteall = new JButton("ɾ�����м�¼");
		back = new JButton("����������");
		
		button_query.addActionListener(this);
		button_queryall.addActionListener(this);
		button_alter.addActionListener(this);
		button_delete.addActionListener(this);
		button_deleteall.addActionListener(this);
		back.addActionListener(this);
		fillinField.addKeyListener(new java.awt.event.KeyAdapter(){
			public void keyTyped(java.awt.event.KeyEvent e){
				if(e.getKeyChar()=='\n'){
					button_query.doClick();
				}
			}
		});
		
		selectBox = new JComboBox();
		selectBox.setModel(new DefaultComboBoxModel(new String[] {"Ա����","����", "�Ա�", "����", "����", "ְ��", "�����̶�", "רҵ����", "��ͥסַ", "��ϵ�绰", "�����ʼ�", "��ǰ״̬", "��ע"}));
		selectBox.addItemListener(new ItemListener() {//�������¼�����  
            public void itemStateChanged(ItemEvent event) {  
                switch (event.getStateChange()) {  
                case ItemEvent.SELECTED:  
                	SQFieldStr = (String) event.getItem();  
                    System.out.println("ѡ�У�" + SQFieldStr);  
                    break;  
                case ItemEvent.DESELECTED:  
                    System.out.println("ȡ��ѡ�У�" + event.getItem());  
                    break;  
                }  
            }  
        });
		
		employeeVector = new Vector();
		titleVector = new Vector();
		titleVector.add("Ա����");
		titleVector.add("����");
		titleVector.add("�Ա�");
		titleVector.add("����");
		titleVector.add("����");
		titleVector.add("ְ��");
		titleVector.add("�����̶�");
		titleVector.add("רҵ����");
		titleVector.add("��ͥסַ");
		titleVector.add("��ϵ�绰");
		titleVector.add("�����ʼ�");
		titleVector.add("��ǰ״̬");
		titleVector.add("��ע");
		employeeJTable = new JTable(employeeVector, titleVector);
		employeeJTable.setPreferredScrollableViewportSize(new Dimension(800,230));
	    employeeJScrollPane = new JScrollPane(employeeJTable);
		//�ֱ�����ˮƽ�ʹ�ֱ�������Զ�����
		employeeJScrollPane.setHorizontalScrollBarPolicy(                
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		employeeJScrollPane.setVerticalScrollBarPolicy(                
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		employeeJTable.addMouseListener(new MouseAdapter()
		{ 
			public void mouseClicked(MouseEvent e) 
			{ 
				int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // �����λ��
				System.out.println("mouseClicked(). row = " + row);
				Vector v = new Vector();
				v = (Vector)employeeVector.get(row);
				name.setText((String) v.get(1));
				sex.setText((String) v.get(2));
				birthday.setText((String) v.get(3));
				department.setText((String) v.get(4));
				job.setText((String) v.get(5));
				education.setText((String) v.get(6));
				specialty.setText((String) v.get(7));
				address.setText((String) v.get(8));
				tel.setText((String) v.get(9));
				email.setText((String) v.get(10));
				status.setText((String) v.get(11));
				remark.setText((String) v.get(12));
			}
		});
		jP1 = new JPanel();
		jP2 = new JPanel();
		jP3 = new JPanel();
		jP4 = new JPanel();
		jP5 = new JPanel();
		jP6 = new JPanel();
		jPTop = new JPanel();
		jPBottom = new JPanel();
		
		jP1.add(label,BorderLayout.SOUTH);
		jP2.add(employeeJScrollPane);
		
		jP3.add(selectBox);
		jP3.add(label_same);
		jP3.add(fillinField);
		jP3.add(button_query);
		jP3.add(button_queryall);
		jP3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP3.setPreferredSize(new Dimension(10,10));
		
		jP4.add(label_name);
		jP4.add(name);
		jP4.add(label_sex);
		jP4.add(sex);
		jP4.add(label_birthday);
		jP4.add(birthday);
		jP4.add(label_department);
		jP4.add(department);
		jP4.add(label_job);
		jP4.add(job);
		jP4.add(label_education);
		jP4.add(education);
		jP4.add(label_specialty);
		jP4.add(specialty);
		jP4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP4.setPreferredSize(new Dimension(20,20));
		
		jP5.add(label_address);
		jP5.add(address);
		jP5.add(label_tel);
		jP5.add(tel);
		jP5.add(label_email);
		jP5.add(email);
		jP5.add(label_status);
		jP5.add(status);
		jP5.add(label_remark);
		jP5.add(remark);
		jP5.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP5.setPreferredSize(new Dimension(20,20));
		
		jP6.add(button_alter);
		jP6.add(button_delete);
		jP6.add(button_deleteall);
		jP6.add(back);
		jP6.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP6.setPreferredSize(new Dimension(20,20));
		
		jPTop.add(jP1);
		jPTop.add(jP2);
		
		jPBottom.setLayout(new GridLayout(4, 1));
		jPBottom.add(jP3);
		jPBottom.add(jP4);
		jPBottom.add(jP5);
		jPBottom.add(jP6);
		
		this.add("North", jPTop);
		this.add("South", jPBottom);
		
		dbProcess = new DbProcess();
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					archivesM archivesM= new archivesM();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("����������")){
			PM_design window = new PM_design();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("��ѯ")
				&&!fillinField.getText().isEmpty()){
			System.out.println("actionPerformed(). ��ѯ");
			String sQueryField = fillinField.getText();
			System.out.println(sQueryField);
			queryProcess(sQueryField);
			fillinField.setText("");
		}else if(e.getActionCommand().equals("��ѯ���м�¼")) {
			System.out.println("actionPerformed(). ��ѯ���м�¼");
			queryAllProcess();
		}else if(e.getActionCommand().equals("�޸���Ϣ")
				&& !education.getText().isEmpty()
				&& !birthday.getText().isEmpty()
				&& !name.getText().isEmpty()
				&& !sex.getText().isEmpty()
				&& !department.getText().isEmpty()
				&& !job.getText().isEmpty()
				&& !status.getText().isEmpty()
				&& !tel.getText().isEmpty()){
			System.out.println("actionPerformed(). ��Ϣ�޸�");
			updateProcess();
		}else if(e.getActionCommand().equals("ɾ����ǰ��¼")){
			System.out.println("actionPerformed(). ɾ����ǰ��¼");
			deleteCurrentRecordProcess();
		}else if(e.getActionCommand().equals("ɾ�����м�¼")){
			tip tip=new tip();
		}
	}
	public void queryProcess(String sQueryField)
	{
		try{
			// ������ѯ����
			String sql = "select *  from person where ";
			String  queryFieldStr= jCBSelectQueryFieldTransfer(SQFieldStr);
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "';";
			System.out.println("queryProcess(). sql = " + sql);
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
			employeeVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("PID"));
				v.add(rs.getString("Pname"));
				v.add(rs.getString("Psex"));
				v.add(rs.getString("Pbirthday"));
				v.add(rs.getString("Pdepartment"));
				v.add(rs.getString("Pjob"));
				v.add(rs.getString("Pedu_level"));
				v.add(rs.getString("Pspecialty"));
				v.add(rs.getString("Paddress"));
				v.add(rs.getString("Ptel"));
				v.add(rs.getString("Pemail"));
				v.add(rs.getString("Pstatus"));
				v.add(rs.getString("Premark"));
				employeeVector.add(v);
			}
			
			employeeJTable.updateUI();

			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void queryAllProcess()
	{
		try{
			// ������ѯ����
			String sql = "select * from person;";
			System.out.println("queryAllProcess(). sql = " + sql);
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);

			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
			employeeVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("PID"));
				v.add(rs.getString("Pname"));
				v.add(rs.getString("Psex"));
				v.add(rs.getString("Pbirthday"));
				v.add(rs.getString("Pdepartment"));
				v.add(rs.getString("Pjob"));
				v.add(rs.getString("Pedu_level"));
				v.add(rs.getString("Pspecialty"));
				v.add(rs.getString("Paddress"));
				v.add(rs.getString("Ptel"));
				v.add(rs.getString("Pemail"));
				v.add(rs.getString("Pstatus"));
				v.add(rs.getString("Premark"));
				employeeVector.add(v);
			}
			
			employeeJTable.updateUI();

			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateProcess()
	{
		int count=employeeJTable.getSelectedRow();//��ȡ��ѡ�е��кţ���¼��  
		String PID= employeeJTable.getValueAt(count, 0).toString();
		System.out.println(PID);
		String Pname=name.getText().trim();
		String Psex=sex.getText().trim();
		String Pbirthday=birthday.getText().trim();
		String Pdepartment=department.getText().trim();
		String Pjob=job.getText().trim();
		String Pedu_level=education.getText().trim();
		String Pspecialty=specialty.getText().trim();
		if(specialty.getText().trim()==null){
			Pspecialty=null;
		}
		String Paddress=address.getText().trim();
		if(address.getText().trim()==null){
			Paddress=null;
		}
		String Ptel=tel.getText().trim();
		String Pemail=email.getText().trim();
		if(email.getText().trim()==null){
			Pemail=null;
		}
		String Pstatus=status.getText().trim();
		String Premark=remark.getText().trim();
		if(remark.getText().trim()==null){
			Premark=null;
		}
		
		// ������������
		String sql = "update person set Pname = '";
		sql = sql + Pname + "',Psex='";
		sql = sql + Psex + "',Pbirthday='";
		sql = sql + Pbirthday + "',Pdepartment='";
		sql = sql + Pdepartment + "',Pjob='";
		sql = sql + Pjob + "',Pedu_level='";
		sql = sql + Pedu_level+ "',Pspecialty='";
		sql = sql + Pspecialty + "',Paddress='";
		sql = sql + Paddress + "',Ptel='";
		sql = sql + Ptel + "',Pemail='";
		sql = sql + Pemail + "',Pstatus='";
		sql = sql + Pstatus+ "',Premark='";
		sql = sql + Premark + "' ";
		sql = sql + " where PID = '" + PID + "';";
		System.out.println("updateProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("updateProcess(). update database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}

	public void deleteCurrentRecordProcess()
	{
		int count=employeeJTable.getSelectedRow();//��ȡ��ѡ�е��кţ���¼��  
		String PID= employeeJTable.getValueAt(count, 0).toString();
		System.out.println(PID);
		String sql = "delete from person where PID = '" + PID+ "';";
		System.out.println("deleteCurrentRecordProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("deleteCurrentRecordProcess(). delete database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}
	
	public void deleteAllRecordsProcess()
	{
		// ����ɾ������
		String sql = "delete from person ;";
		System.out.println("deleteAllRecordsProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("deleteAllRecordsProcess(). delete database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}
	
	public String jCBSelectQueryFieldTransfer(String InputStr)
	{
		String outputStr = "";
		System.out.println("jCBSelectQueryFieldTransfer(). InputStr = " + InputStr);
		
		if(InputStr.equals("Ա����")){
			outputStr = "PID";
		}else if(InputStr.equals("����")){
			outputStr = "Pname";
		}else if(InputStr.equals("�Ա�")){
			outputStr = "Psex";
		}else if(InputStr.equals("����")){
			outputStr = "Pbirthday";
		}else if(InputStr.equals("����")){
			outputStr = "Pdepartment";
		}else if(InputStr.equals("ְ��")){
			outputStr = "Pjob";
		}else if(InputStr.equals("�����̶�")){
			outputStr = "Pedu_level";
		}else if(InputStr.equals("רҵ����")){
			outputStr = "Pspecialty";
		}
		else if(InputStr.equals("��ͥסַ")){
			outputStr = "Paddress";
		}
		else if(InputStr.equals("��ϵ�绰")){
			outputStr = "Ptel";
		}
		else if(InputStr.equals("�����ʼ�")){
			outputStr = "Pemail";
		}
		else if(InputStr.equals("��ǰ״̬")){
			outputStr = "Pstatus";
		}
		else if(InputStr.equals("��ע")){
			outputStr = "Premark";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
}
