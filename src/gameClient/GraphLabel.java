package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphLabel extends JFrame implements ActionListener {
    JTextField tf;
    JTextField tf2;
    JLabel l;
    JButton b;
   // JFrame f;
    public GraphLabel() {
   super();
   JFrame fr=new JFrame("game");
        JPanel p = new JPanel();
        JPanel p2 = new JPanel();
        JLabel l = new JLabel("Enter id:");
        JLabel l2 = new JLabel("Enter level of game:");
       tf = new JTextField();
        tf2 = new JTextField();
        JButton b = new JButton("Start");


        p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));
        p.setLayout(new FlowLayout());

        p2.add(l);
        p2.add(tf);
        p2.add(l2);
        p2.add(tf2);
         p.add(b);

        fr.add(p2, BorderLayout.PAGE_START);
        fr.add(p, BorderLayout.PAGE_END);

        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       fr.pack();
        b.addActionListener(this);

        fr.setSize(400,300);
        setLocationRelativeTo(null);
        fr.setVisible(true);
         fr.getDefaultCloseOperation();
   //
//        JPanel k=new JPanel();
//        l = new JLabel("Enter id:");
//        l.setBounds(75, 40, 300, 100);
//        k.add(l);
//        k.setLayout(new BoxLayout(k,BoxLayout.PAGE_AXIS));
//        JLabel l2=new JLabel("Enter level of game:");
//        l2.setBounds(10, 20, 300, 100);
//        k.add(l2);
//        tf = new JTextField();
//        tf.setBounds(130, 10, 30, 5);
//        k.add(tf);
//        tf2 = new JTextField();
//        tf2.setBounds(130, 5, 150, 20);
//        k.add(tf2);
//        b = new JButton("Start");
//        b.setBounds(50, 100, 250, 20);
//        k.add(b);
//        b.addActionListener(this);






        //this.add(k);

     // setLocationRelativeTo(null);
      //  setSize(400, 300);

      // setVisible(true);
       // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
   public int getLevel(){
       String s=tf2.getText();
       Integer in=Integer.parseInt(s);
       return in;
   }
    public int getId() {
     String s=tf.getText();
     Integer in=Integer.parseInt(s);
        return in;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Thread client = new Thread(new Ex2());
        client.start();

    }
}