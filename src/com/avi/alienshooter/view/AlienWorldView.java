/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter.view;

import com.avi.alienshooter.AlienShooter;
import com.avi.alienshooter.AlienShooterConfig;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Avi
 */
public class AlienWorldView extends JFrame {

    private AlienShooterComponent body = null;
//window event resize listener

    public AlienWorldView() {
        //set the title
        this.setTitle("Alien Shooter");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        MyWindowListener mwl = new MyWindowListener();
        this.addWindowListener(mwl);
        try {
            body = new AlienShooterComponent();
        } catch (Exception ex) {
        }
        this.addKeyListener(body);
        this.getContentPane().add(body);

        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar);

        JMenu file = new JMenu("File");
        menubar.add(file);

        JMenuItem pause = new JMenuItem("Pause");
        file.add(pause);
        pause.addActionListener(new PauseAction());

        JMenuItem play = new JMenuItem("Play");
        file.add(play);
        play.addActionListener(new PlayAction());

        JMenuItem restart = new JMenuItem("Restart");
        file.add(restart);
        restart.addActionListener(new RestartAction());




        JMenuItem cheats = new JMenuItem("Cheats");
        file.add(cheats);
        //cheats.addActionListener(new RestartAction());

        JMenuItem exit = new JMenuItem("Exit");
        file.add(exit);
        exit.addActionListener(new ExitAction());

        JMenu edit = new JMenu("Edit");
        menubar.add(edit);

        JMenuItem options = new JMenuItem("Options");
        edit.add(options);
        options.addActionListener(new OptionAction());


    }
    

    public AlienShooterComponent getBody() {
        return body;
    }

    class PauseAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //freezes everything
            body.stopTimer();
        }
    }

    class PlayAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                body.startTimer();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    class RestartAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AlienShooterComponent comp = body;
            body.setScore(0);
            body.getAlienList().clear();
            body.setFailCount(0);
        }
    }

    public void handleExit() {
        //setVisible(false); 
        //dispose(); 
        try {
            body.overwriteHighScore();
            System.out.println("new high score successfully saved");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }

    class ExitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            handleExit();
        }
    }

    class OptionAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //JDialog.setDefaultLookAndFeelDecorated(true);
            OptionPanel op = new OptionPanel();
            AlienShooter af = AlienShooter.getInstance();
            AlienShooterConfig config = af.getConfig();
            op.getAlienSpeed().setValue(config.getWorldAlienSpped());
            op.getGunSpeed().setValue(config.getWorldGunSpeed());
            op.getBulletSpeed().setValue(config.getWorldBulletSpeed());
            /*
             JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 500, 250);
             slider.setMajorTickSpacing(50);
             slider.setPaintTicks(true);
            
             panel.add(slider);
             */

            int response = JOptionPane.showConfirmDialog(AlienWorldView.this, op, "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION) {
                System.out.println("No button clicked");
            } else if (response == JOptionPane.YES_OPTION) {
                System.out.println("Yes button clicked");
                int alienSpeed = op.getAlienSpeed().getValue();
                int gunSpeed = op.getGunSpeed().getValue();
                int bulletSpeed = op.getBulletSpeed().getValue();


                config.setAlienSpeed(1000 - alienSpeed);
                config.setGunSpeed(1000 - gunSpeed);
                config.setBulletSpeed(1000 - bulletSpeed);



            } else if (response == JOptionPane.CLOSED_OPTION) {
                System.out.println("JOptionPane closed");
            }
        }
    }

    class MyWindowListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            AlienWorldView.this.handleExit();
        }
    }
}
