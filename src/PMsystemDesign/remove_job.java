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

public class remove_job extends JFrame implements ActionListener {
	// �������
	JLabel label_table = null;
	JLabel equal = null;
	JLabel label_removeJ = null;
	
	JTextField queryField = null;
	JTextField removeJ = null;
	
	JButton query = null;
	JButton queryAll = null;
	JButton update = null;
	JButton back = null;
	
	JComboBox<String> jCBSelectQueryField = null;
	
	JPanel jP1, jP2,jP3,jP4 = null;
	JPanel jPTop, jPBottom = null;
	DefaultTableModel removeJTableModel = null;
	JTable removeJJTable = null;
	JScrollPane removeJJScrollPane = null;
	Vector removeJVector = null;
	Vector titleVector = null;
	
	private static DbProcess dbProcess;
	String SelectQueryFieldStr = "Ա����";

	public remove_job() {
		label_table = new JLabel("Ա��ְ���");
		equal = new JLabel(" = ");
		label_removeJ=new JLabel(" ְ�� ");
		
		queryField = new JTextField(16);
		removeJ = new JTextField(16);
		
		query = new JButton("��ѯ");
		queryAll = new JButton("��ѯ���м�¼");
		update = new JButton("����");
		back=new JButton("����������");
		
		query.addActionListener(this);
		queryAll.addActionListener(this);
		update.addActionListener(this);
		back.addActionListener(this);
		
		jCBSelectQueryField = new JComboBox<String>();
		jCBSelectQueryField.addItem("Ա����");  
		jCBSelectQueryField.addItem("ְ��");  
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
	
		removeJVector = new Vector();
		titleVector = new Vector();
		
		titleVector.add("Ա����");
		titleVector.add("ְ��");
		removeJJTable = new JTable(removeJVector, titleVector);
		removeJJTable.setPreferredScrollableViewportSize(new Dimension(450,160));
		removeJJScrollPane = new JScrollPane(removeJJTable);
		//�ֱ�����ˮƽ�ʹ�ֱ�������Զ�����
		removeJJScrollPane.setHorizontalScrollBarPolicy(                
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		removeJJScrollPane.setVerticalScrollBarPolicy(                
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//Ϊ�����Ӽ����� 
		removeJJTable.addMouseListener(new MouseAdapter()
		{ 
			public void mouseClicked(MouseEvent e) 
			{ 
				int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // �����λ��
				System.out.println("mouseClicked(). row = " + row);
				Vector v = new Vector();
				v = (Vector) removeJVector.get(row);
				removeJ.setText((String) v.get(1));
			}
		});


		jP1 = new JPanel();
		jP2 = new JPanel();
		jP3 = new JPanel();
		jP4 = new JPanel();
		jPTop = new JPanel();
		jPBottom = new JPanel();
		
		jP1.add(label_table,BorderLayout.SOUTH);
		jP2.add(removeJJScrollPane);
		
		jP3.add(jCBSelectQueryField);
		jP3.add(equal);
		jP3.add(queryField);
		jP3.add(query);
		jP3.add(queryAll);
		jP3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP3.setPreferredSize(new Dimension(20,20));
		
		jP4.add(label_removeJ);
		jP4.add(removeJ);
		jP4.add(update);
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
		this.setTitle("Ա��ְ�����ϵͳ");
		this.setSize(500, 500);
		this.setLocation(150, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("��ѯ")  
			&& !queryField.getText().isEmpty()){
			System.out.println("actionPerformed(). ��ѯ");
			String sQueryField = queryField.getText().trim();
			queryProcess(sQueryField);
			queryField.setText("");
		}else if(e.getActionCommand().equals("��ѯ���м�¼")) {
			System.out.println("actionPerformed(). ��ѯ���м�¼");
			queryAllProcess();
		}else if(e.getActionCommand().equals("����")
				&& !removeJ.getText().isEmpty()){
			System.out.println("actionPerformed(). ����");
			updateProcess();
			//�����¼��������ϸ��
			remove_details detailsR = new  remove_details();
			detailsR.queryAllProcess();
			int count=removeJJTable.getSelectedRow();  
			detailsR.PID_J=removeJJTable.getValueAt(count, 0).toString();
			detailsR.removeJobProcess("1");
		}else if(e.getActionCommand().equals("����������")){
			PM_design window = new PM_design();
			this.setVisible(false);
		}
	}
	
	public static void main(String[] args) {
        // TODO Auto-generated method stub
		remove_job remove_job = new  remove_job();
    }
	
	public void queryProcess(String sQueryField)
	{
		try{
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
		    String sql = "select * from person where ";
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "';";
			
			System.out.println("queryProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
	
			// ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
			removeJVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("PID"));
				v.add(rs.getString("Pjob"));
				removeJVector.add(v);
			}
			
			removeJJTable.updateUI();

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
			removeJVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("PID"));
				v.add(rs.getString("Pjob"));
				removeJVector.add(v);
			}
			
			removeJJTable.updateUI();

			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateProcess()
	{
		int count=removeJJTable.getSelectedRow();  
		String PID=removeJJTable.getValueAt(count, 0).toString();
		System.out.println(PID);
		String Pjob =removeJ.getText().trim();
		// ������������
		String sql = "update person set Pjob = '";
		sql = sql + Pjob + "'";
		sql = sql + " WHERE PID = '" + PID + "';";
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

	public String jCBSelectQueryFieldTransfer(String InputStr)
	{
		String outputStr = "";
		System.out.println("jCBSelectQueryFieldTransfer(). InputStr = " + InputStr);
		
		if(InputStr.equals("Ա����")){
			outputStr = "PID";
		}else{
			outputStr = "Pjob";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
}


