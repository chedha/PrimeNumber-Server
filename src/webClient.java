import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class webClient {
	
	private static void constructGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		MyJFrame frame = new MyJFrame();
		frame.setVisible(true);
		
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				constructGUI();
			}
		});

	}

}


class MyJFrame extends JFrame {
	public JLabel inputNumber;
	public JLabel answer;
	public JTextField number;
	
	
	

	public MyJFrame() {
		super();
		init();
	}

	private void init() {
		number = new JTextField();		
		JButton btn1 = new JButton("Calculate");
		btn1.addActionListener(new MyButtonListener(this));		
		inputNumber = new JLabel("Enter a Number");
		answer = new JLabel("Server Answer: ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2, 2));		
		this.add(inputNumber);		
		this.add(number);
		this.add(btn1);
		this.add(answer);		
		this.pack();
		this.setVisible(true);

		

	}
}

class MyButtonListener implements ActionListener {
	MyJFrame fr;
	public MyButtonListener(MyJFrame frame)
	{
		fr = frame;
	}

	public void actionPerformed(ActionEvent e) 
	{
		JButton btn = (JButton) e.getSource();		
		Scanner inFS = null;		
		String userInput = fr.number.getText();
		inFS = new Scanner(userInput);
				
		try {
			String userNum = inFS.nextLine();		
			Socket connection = new Socket("127.0.0.1", 1236);
			InputStream input = connection.getInputStream();
			OutputStream output = connection.getOutputStream();
			
			output.write(userNum.length());
			output.write(userNum.getBytes());
			
			int n = input.read();
			byte [] data = new byte[n];
			input.read(data);
			
			String serverResponse = new String(data, StandardCharsets.UTF_8);
			System.out.println("Server said: " + serverResponse);
			
			fr.answer.setText(serverResponse);
			
			if(!connection.isClosed())
				connection.close();
			inFS.close();
			
		} catch (IOException exception) {
			
			exception.printStackTrace();
		}
		
	}
}