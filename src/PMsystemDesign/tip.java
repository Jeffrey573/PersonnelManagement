package PMsystemDesign;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;

public class tip extends JFrame implements ActionListener{
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tip tip = new tip();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public tip() {
		this.setTitle("ע�⣡");
		this.getContentPane().setForeground(Color.RED);
		this.setBounds(500, 250, 280, 240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("�Ƿ�ɾ�����м�¼���˹��̲�����");
		lblNewLabel.setFont(new Font("΢���ź�", Font.BOLD, 12));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(43, 74, 191, 15);
		this.getContentPane().add(lblNewLabel);
		
		JButton button = new JButton("ȷ��");
		button.setBounds(37, 144, 93, 23);
		this.getContentPane().add(button);
		
		JButton button_1 = new JButton("����");
		button_1.setBounds(141, 144, 93, 23);
		this.getContentPane().add(button_1);
		
		button.addActionListener(this);
		button_1.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("ȷ��")){
			System.out.println("actionPerformed(). ɾ�����м�¼");
			new archivesM().deleteAllRecordsProcess();
		}else {
			this.setVisible(false);
		}
	}
}
