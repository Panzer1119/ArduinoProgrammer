/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.panzercraft.main;

import jaddon.controller.JAddOnStandard;
import jaddon.controller.JFrameManager;
import jaddon.controller.StandardMethods;
import jaddon.controller.StaticStandard;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Paul & Roman
 */
public class ArduinoProgrammer implements ActionListener, StandardMethods, WindowListener {
    
    public static final String PROGRAMNAME = "Arduino Programmer";
    public static final String VERSION = "0.1";
    
    private final JFrameManager frame = new JFrameManager(PROGRAMNAME, VERSION);
    private final JAddOnStandard standard = new JAddOnStandard(PROGRAMNAME, VERSION, true, true, false, true, true);
    
    private boolean init = false;
    
    public ArduinoProgrammer() {
        init();
        reloadConfig();
        reloadLang();
        frame.setDefaultCloseOperation(JFrameManager.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(600, 400));
        frame.addWindowListener(this);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    @Override
    public boolean init() {
        if(init) {
            return false;
        }
        StaticStandard.getLogger().setLoggingOnlyIfIDE(false);
        StaticStandard.getLogger().setPrintTimestamp(true);
        StaticStandard.getLogger().setPrintExtraInformation(true);
        StaticStandard.getLogger().enableOutputStream();
        StaticStandard.getLogger().enableErrorOutputStream();
        int id = frame.addWork(String.format(StaticStandard.getLang().getLang("work_initialation_phase", "Initiation phase %d..."), 1), false);
        StaticStandard.getLogger().log("Initiation phase 1 started");
        StaticStandard.getUpdater().isIDE();
        standard.setDoUpdate(true);
        StaticStandard.getUpdater().setFileTemp(StaticStandard.getConfig().getTempFolder());
        StaticStandard.getUpdater().loadURLsFromInternResource("/de/panzercraft/urls/updaterURLs.txt");
        StaticStandard.getConfig().setDefaultConfig(new String[] {"lang"}, new String[] {"EN"});
        StaticStandard.getLang().setClassReference(ArduinoProgrammer.class);
        StaticStandard.getLang().setFile("/de/panzercraft/lang");
        StaticStandard.getConfig().reloadConfig();
        StaticStandard.getConfig().setDoUpdate(true);
        StaticStandard.getConfig().update();
        StaticStandard.getLang().setDoUpdate(true);
        StaticStandard.getLang().update();
        StaticStandard.getLogger().setIsIDE(StaticStandard.isIsIDE());
        StaticStandard.getLogger().setDoUpdate(true);
        StaticStandard.getLogger().update();
        reloadConfig();
        init = true;
        frame.delWork(id);
        StaticStandard.getLogger().log("Initiation phase 1 completed successfully");
        return true;
    }

    @Override
    public boolean reloadConfig() {
        StaticStandard.getConfig().reloadConfig();
        StaticStandard.getLang().setLang(StaticStandard.getConfig().getProperty("lang", "EN"));
        return true;
    }
    
    @Override
    public boolean reloadLang() {
        StaticStandard.getConfig().reloadConfig();
        StaticStandard.getLang().setLang(StaticStandard.getConfig().getProperty("lang", "EN"));
        StaticStandard.getLang().reloadLang();
        StaticStandard.getLogger().update();
        /*
        send.setText(StaticStandard.getLang().getLang("send", "Send"));
        M1.setText(StaticStandard.getLang().getLang("chat", "Chat"));
        M2.setText(StaticStandard.getLang().getLang("file", "File"));
        M3.setText(StaticStandard.getLang().getLang("extras", "Extras"));

        M1I1.setText(StaticStandard.getLang().getLang("add_new_chat", "Add New Chat"));
        M1I2.setText(StaticStandard.getLang().getLang("close_chat", "Close Chat"));
        M1I3.setText(StaticStandard.getLang().getLang("available_chat", "Available Chat"));
        M1I4.setText(StaticStandard.getLang().getLang("join_chat", "Join Chat"));
        M1I5.setText(StaticStandard.getLang().getLang("connect_to_server", "Connect to Server"));
        M1I6.setText(StaticStandard.getLang().getLang("join_server", "Join Server"));

        M2I1.setText(StaticStandard.getLang().getLang("exit", "Exit"));
        M2I2.setText(StaticStandard.getLang().getLang("restart", "Restart"));
        M2I3.setText(StaticStandard.getLang().getLang("send_file", "Send File"));

        M3I1.setText(StaticStandard.getLang().getLang("settings", "Settings"));
        M3I2.setText(StaticStandard.getLang().getLang("changelog", "Changelog"));
        M3I3.setText(StaticStandard.getLang().getLang("about", "About"));
        M3I4.setText(StaticStandard.getLang().getLang("chat_server", "Chat Server"));
        */
        return true;
    }

    @Override
    public void exit() {
        try {
            
        } catch (Exception ex) {
        }
        StaticStandard.exit();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == null) {
            
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(e.getSource() == frame) {
            exit();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
