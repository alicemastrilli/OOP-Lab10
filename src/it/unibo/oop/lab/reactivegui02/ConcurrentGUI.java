package it.unibo.oop.lab.reactivegui02;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class ConcurrentGUI extends JFrame{

    
    /**
     * 
     */
    private static final double WIDTH_PERC = 0.3;
    private static final double HEIGHT_PERC = 0.3;
    private static final long serialVersionUID = 1L;
    private final JLabel testo = new JLabel("0");
    private final JButton up = new JButton("up");
    private final JButton down = new JButton("down");
    private final JButton stop = new JButton("stop");
    public ConcurrentGUI() {
        super();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth()*WIDTH_PERC),(int) (screenSize.getHeight()*HEIGHT_PERC));
        
        final JPanel panel = new JPanel();
         this.getContentPane().add(panel);
        panel.add(testo);
        panel.add(up);
        panel.add(down);
        panel.add(stop);
        final Agent agent = new Agent();
        new Thread(agent).start();
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                agent.stopCounting();
            }
        });
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                agent.su = 1;
            }
        });
        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                agent.su = 0;
            }
        });
         this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         this.setVisible(true);
    }
    final class Agent implements Runnable {
        private int counter;
        private boolean stop;
        private int su = 1;

    @Override
    public void run() {
        while (!this.stop) {
            try {
                if (su == 1) {
                    SwingUtilities.invokeAndWait(() -> testo.setText(Integer.toString(Agent.this.counter)));
                    this.counter++;
                    Thread.sleep(100);
                }
                else {
                    SwingUtilities.invokeAndWait(() -> testo.setText(Integer.toString(Agent.this.counter)));
                    this.counter--;
                    Thread.sleep(100);
                }
            } catch (InvocationTargetException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // TODO Auto-generated method stub
    }

    public void stopCounting() {
        this.stop = true;
        ConcurrentGUI.this.down.setEnabled(false);
        ConcurrentGUI.this.up.setEnabled(false);
        ConcurrentGUI.this.stop.setEnabled(false);
    }
    }
    }
