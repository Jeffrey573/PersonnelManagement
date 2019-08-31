package PMsystemDesign;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

public class remove_details extends JFrame implements ActionListener {
	// �������
	JLabel label_details = null;
	JLabel equal = null;
	
	JTextField jTFQueryField = null;
	
	JButton query = null;
	JButton queryAll = null;
	JButton deleteCurrentRecord = null;
	JButton deleteAllRecords = null;
	JButton back = null;
	
	//JComboBox jCBSelectQueryField = null;
	JComboBox<String> jCBSelectQueryField = null;//��ѯ�ֶ�
	JPanel jP1, jP2,jP3,jP4 = null;
	JPanel jPTop, jPBottom = null;
	DefaultTableModel removeTableModel = null;
	JTable removeJTable = null;
	JScrollPane removeJScrollPane = null;
	Vector removeVector = null;
	Vector titleVector = null;
	
	private static DbProcess dbProcess;
	String SelectQueryFieldStr = "��¼���";
	String PID_E;
	String PID_J;
	String PID_L;
	// ���캯��
	public remove_details() {
		// �������	
		label_details = new JLabel("���µ�����ϸ��");
		equal = new JLabel(" = ");
		
		jTFQueryField = new JTextField(10);
		
		query = new JButton("��ѯ");
		queryAll = new JButton("��ѯ���м�¼");
		deleteCurrentRecord = new JButton("ɾ����ǰ��¼");
		deleteAllRecords = new JButton("ɾ�����м�¼");
		back = new JButton("����������");
		// ���ü���
		query.addActionListener(this);
		queryAll.addActionListener(this);
		deleteCurrentRecord.addActionListener(this);
		deleteAllRecords.addActionListener(this);
		back.addActionListener(this);
		
		jCBSelectQueryField = new JComboBox<String>();//��ѯ�ֶ�
		jCBSelectQueryField.addItem("��¼���");  
		jCBSelectQueryField.addItem("Ա����");  
		jCBSelectQueryField.addItem("�������");
		jCBSelectQueryField.addItem("����");
		jCBSelectQueryField.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {  
                switch (event.getStateChange()) {  
                case ItemEvent.SELECTED:  
                	SelectQueryFieldStr = (String) event.getItem();  
                    System.out.println("ѡ�У�" + SelectQueryFieldStr);  
                    break;  
                case ItemEvent.DESELECTED:  
                    System.out.println("ȡ��ѡ�У�" + event.getItem());  
                    break;  
                }  
            }  
        });
	
		removeVector = new Vector();
		titleVector = new Vector();
		
		// �����ͷ
		titleVector.add("��¼���");
		titleVector.add("Ա����");
		titleVector.add("�������");
		titleVector.add("����");
		removeJTable = new JTable(removeVector, titleVector);
		removeJTable.setPreferredScrollableViewportSize(new Dimension(450,160));
		removeJScrollPane = new JScrollPane(removeJTable);
		//�ֱ�����ˮƽ�ʹ�ֱ�������Զ�����
		removeJScrollPane.setHorizontalScrollBarPolicy(                
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		removeJScrollPane.setVerticalScrollBarPolicy(                
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		jP1 = new JPanel();
		jP2 = new JPanel();
		jP3 = new JPanel();
		jP4 = new JPanel();
		jPTop = new JPanel();
		jPBottom = new JPanel();
		
		jP1.add(label_details,BorderLayout.SOUTH);
		jP2.add(removeJScrollPane);
		
		jP3.add(jCBSelectQueryField);
		jP3.add(equal);
		jP3.add(jTFQueryField);
		jP3.add(query);
		jP3.add(queryAll);
		jP3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP3.setPreferredSize(new Dimension(20,20));
		
		jP4.add(deleteCurrentRecord);
		jP4.add(deleteAllRecords);
		jP4.add(back);
		jP4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP4.setPreferredSize(new Dimension(20,20));
		
		jPTop.add(jP1);
		jPTop.add(jP2);
		
		jPBottom.setLayout(new GridLayout(4, 1));
		jPBottom.add(jP3);
		jPBottom.add(jP4);
		
		this.add("North", jPTop);
		this.add("South", jPBottom);
	
		this.setLayout(new GridLayout(2, 1));
		this.setTitle("���µ�����ϸ");
		this.setSize(500, 500);
		this.setLocation(750, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("��ѯ")  
			&& !jTFQueryField.getText().isEmpty()){
			System.out.println("actionPerformed(). ��ѯ");
			String sQueryField = jTFQueryField.getText().trim();
			queryProcess(sQueryField);
			jTFQueryField.setText("");
		}else if(e.getActionCommand().equals("��ѯ���м�¼")) {
			System.out.println("actionPerformed(). ��ѯ���м�¼");
			queryAllProcess();
		}else if(e.getActionCommand().equals("ɾ����ǰ��¼")){
			System.out.println("actionPerformed(). ɾ����ǰ��¼");
			deleteCurrentRecordProcess();
		}else if(e.getActionCommand().equals("ɾ�����м�¼")){
			tip_1 tip_1 = new tip_1();
		}else if(e.getActionCommand().equals("����������")){
			PM_design window = new PM_design();
			this.setVisible(false);
		}
	}
	
	public static void main(String[] args) {
        // TODO Auto-generated method stub
		remove_details detailsR = new  remove_details();
    }
	
	public void queryProcess(String sQueryField)
	{
		try{
			// ������ѯ����
			String sql = "select * from personnel where ";
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "';";
			
			System.out.println("queryProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
	
			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
			removeVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("RID"));
				v.add(rs.getString("PID"));
				v.add(rs.getString("Rcode"));
				v.add(rs.getString("Rdescription"));
				removeVector.add(v);
			}
			
			removeJTable.updateUI();

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
			String sql = "select * from personnel;";
			System.out.println("queryAllProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);

			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
			removeVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("RID"));
				v.add(rs.getString("PID"));
				v.add(rs.getString("Rcode"));
				v.add(rs.getString("Rdescription"));
				removeVector.add(v);
			}
			
			removeJTable.updateUI();

			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
	}

	public void deleteCurrentRecordProcess()
	{
		int count=removeJTable.getSelectedRow();  
		String RID=removeJTable.getValueAt(count, 0).toString();
		System.out.println(RID);
		String sql = "delete from personnel where RID = '" + RID + "';";
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
		String sql = "delete from personnel;";
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
		
		if(InputStr.equals("��¼���")){
			outputStr = "RID";
		}else if(InputStr.equals("Ա����")){
			outputStr = "PID";
		}else if(InputStr.equals("�������")){
			outputStr = "Rcode";
		}else if(InputStr.equals("����")){
			outputStr = "Rdescription";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
	
	public void archivesEntryProcess(String str1) {
		int count=removeJTable.getRowCount();
		System.out.println(count);
		String RID=String.valueOf(++count);
		String Rcode=str1;
		String Rdescription=null;
		try{
			// ������ѯ����
			String sql = "select description from personnel_change where Rcode='"+Rcode+"';";
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			while(rs.next()){
				Rdescription=rs.getString("description");
			}
			
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
	    System.out.println(PID_E);
		String sql1 = "insert into personnel values('";
		sql1= sql1 + RID + "','";
		sql1 = sql1 + PID_E +  "','";
		sql1 = sql1 + Rcode + "','";
		sql1 = sql1 + Rdescription + "');";
		try{
			if (dbProcess.executeUpdate(sql1) < 1) {
				System.out.println("insertProcess(). insert database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		removeVector.clear();
		Vector v = new Vector();
		v.add(RID);
		v.add(PID_E);
		v.add(Rcode);
		v.add(Rdescription);
		removeVector.add(v);
		removeJTable.updateUI();
		queryAllProcess();
	}
	
	public void removeJobProcess(String str2) {
		int count=removeJTable.getRowCount();
		System.out.println(count);
		String RID=String.valueOf(++count);
		String Rcode=str2;
		String Rdescription=null;
		try{
			// ������ѯ����
			String sql = "select description from personnel_change where Rcode='"+Rcode+"';";
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			while(rs.next()){
				Rdescription=rs.getString("description");
			}
			
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
	    System.out.println(PID_J);
		String sql1 = "insert into personnel values('";
		sql1= sql1 + RID + "','";
		sql1 = sql1 + PID_J +  "','";
		sql1 = sql1 + Rcode + "','";
		sql1 = sql1 + Rdescription + "');";
		try{
			if (dbProcess.executeUpdate(sql1) < 1) {
				System.out.println("insertProcess(). insert database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		removeVector.clear();
		Vector v = new Vector();
		v.add(RID);
		v.add(PID_J);
		v.add(Rcode);
		v.add(Rdescription);
		removeVector.add(v);
		removeJTable.updateUI();
		queryAllProcess();
	}
	
	public void removeEmployeeProcess(String str3) {
		int count=removeJTable.getRowCount();
		System.out.println(count);
		String RID=String.valueOf(++count);
		String Rcode=str3;
		String Rdescription=null;
		try{
			// ������ѯ����
			String sql = "select description from personnel_change where Rcode='"+Rcode+"';";
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			while(rs.next()){
				Rdescription=rs.getString("description");
			}
			
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
	    System.out.println(PID_L);
		String sql1 = "insert into personnel values('";
		sql1= sql1 + RID + "','";
		sql1 = sql1 + PID_L +  "','";
		sql1 = sql1 + Rcode + "','";
		sql1 = sql1 + Rdescription + "');";
		try{
			if (dbProcess.executeUpdate(sql1) < 1) {
				System.out.println("insertProcess(). insert database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
		removeVector.clear();
		Vector v = new Vector();
		v.add(RID);
		v.add(PID_L);
		v.add(Rcode);
		v.add(Rdescription);
		removeVector.add(v);
		removeJTable.updateUI();
		queryAllProcess();
	}
}
