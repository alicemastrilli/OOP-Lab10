package it.unibo.oop.lab.reactivegui03;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


public class AnotherConcurrentGUI {
    private static final long serialVersionUID = 1L;
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final JLabel display = new JLabel("0");
    private final JButton stop = new JButton("stop");
    private final JButton down = new JButton("down");
    private final JButton up = new JButton("up");
    private int su=1;
    private boolean run = true;
    
    private JFrame frame = new JFrame();
    public AnotherConcurrentGUI() {
        super();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final JPanel panel = new JPanel();
        panel.add(display);
        panel.add(up);
        panel.add(down);
        panel.add(stop);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        final Agent agent = new Agent();
        new Thread(agent).start();
        new Thread(() -> {
            try {
                Thread.sleep(10000);
                
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
                run=false;
                up.setEnabled(false);
                down.setEnabled(false);
                stop.setEnabled(false);
        }).start();
        up.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                AnotherConcurrentGUI.this.su = 1;
                
            }
        });
        down.addActionListener(new ActionListener() {
           
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                AnotherConcurrentGUI.this.su = 0;
            }
        });
        stop.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                AnotherConcurrentGUI.this.run=false;
                up.setEnabled(false);
                down.setEnabled(false);
                stop.setEnabled(false);
                
            }
        });
        
        // TODO Auto-generated constructor stub
    }
    
    private class Agent implements  Runnable {
        int counter = 0;

        public void run() {
            
            while(AnotherConcurrentGUI.this.run) {
           
            AnotherConcurrentGUI.this.display.setText(Integer.toString(counter));
           
                if(su == 1) {
                    counter++;
                }
                else {
                    counter--;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    } 
}
