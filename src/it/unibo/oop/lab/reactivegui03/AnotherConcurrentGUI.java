package it.unibo.oop.lab.reactivegui03;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;




public class AnotherConcurrentGUI extends JFrame{
    final private double PERC_WIDTH = 0.3;
    final private double PERC_HEIGHT = 0.3;
    final JLabel testo = new JLabel ("0");
    public AnotherConcurrentGUI() {
        super();
        final Dimension size = getToolkit().getScreenSize();
        this.setSize((int)(size.width * PERC_WIDTH), (int) (size.height * PERC_HEIGHT ));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        final JPanel panel = new JPanel();
        this.getContentPane().add(panel);
        final JButton up = new JButton ("up");
        final JButton down = new JButton ("down");
        final JButton stop = new JButton ("stop");
        
        panel.add(testo);
        panel.add(up);
        panel.add(down);
        panel.add(stop);
        
        final Agent agent = new Agent();
        new Thread(agent).start();
        new Thread(()-> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            agent.stop = true;
            up.setEnabled(false);
            down.setEnabled(false);
        }).start();;
        
        stop.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                agent.stop=true;
                up.setEnabled(false);
                down.setEnabled(false);
                
            }
        });
        this.setVisible(true);
        
        up.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                agent.up = 1; 
                agent.run();
                
            }
        });
        
        down.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                agent.up = -1; 
                agent.run();
                
            }
        });
        
    }
     
    final class Agent implements Runnable {

        private boolean stop=false;
        private int counter = 0; 
        private int up = 1;
        @Override
        public void run() {
            
            while(!this.stop) {
                
                try {
                    SwingUtilities.invokeAndWait(() -> AnotherConcurrentGUI.this.testo.setText(Integer.toString(counter)));
                     if(up == 1) {
                    counter++;
                     }
                     else {
                    counter--;
                     }
                     Thread.sleep(100);
                } catch (InvocationTargetException e1  ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
            }
        }
        
    }
    
 }
