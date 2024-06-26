package bank.management.system;
import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame {

    String pinnumber;
    
    MiniStatement(String pinnumber){
        
        this.pinnumber = pinnumber;
        setTitle("Mini Statement");
        setLayout(null);
        
        JLabel mini = new JLabel();
        mini.setBounds(20,80,400,200);
        add(mini);
        
        JLabel bank = new JLabel("State Bank Of India");
        bank.setBounds(120,20,200,20);
        add(bank);
        
        JLabel card = new JLabel();
        card.setBounds(20,80,300,20);
        add(card);
        
        JLabel text = new JLabel("Thank You !");
        text.setFont(new Font("Raleway",Font.BOLD,16));
        text.setBounds(120,450,200,20);
        add(text);
        
        JLabel balance = new JLabel();
        balance.setBounds(20,400,300,40);
        add(balance);
        
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from login where pin = '"+pinnumber+"'");
            
            while(rs.next()) {
                card.setText("Card Number : " + rs.getString("cardnumber").substring(0, 4) + "XXXXXXXX" + rs.getString("cardnumber").substring(12));
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        
        try {
            Conn conn = new Conn();
            int bal = 0;
            ResultSet rs = conn.s.executeQuery("select * from bank where pin = '"+pinnumber+"'");
            while(rs.next()) {
                mini.setText(mini.getText() + "<html>" + rs.getString("date") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("type") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("amount")+"<br><br><html>");
                
                if(rs.getString("type").equals("Deposit")) {
                    bal += Integer.parseInt(rs.getString("amount"));
                } else {
                    bal -= Integer.parseInt(rs.getString("amount"));
                }
            }
            balance.setText("Your Current Account Balance is Rs "+ bal);
        } catch(Exception e){
            System.out.println(e);
        }
        
        mini.setBounds(20 ,140, 400, 200);
        
        setSize(400,600);
        setLocation(20,20);
        getContentPane().setBackground(Color.WHITE);
    }
   
    public static void main(String args[]) {
        
        new MiniStatement("").setVisible(true);
    }
}