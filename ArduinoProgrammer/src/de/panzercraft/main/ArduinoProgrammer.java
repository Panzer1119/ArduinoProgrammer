/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.panzercraft.main;

import de.panzercraft.codegenerator.Codegenerator;
import de.panzercraft.codegenerator.ForLoop;
import de.panzercraft.codegenerator.Function;
import de.panzercraft.codegenerator.Variable;
import de.panzercraft.gui.editor.Editor;
import jaddon.controller.JAddOnStandard;
import jaddon.controller.JFrameManager;
import jaddon.controller.StandardMethods;
import jaddon.controller.StaticStandard;
import jaddon.utils.JUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Paul & Roman
 */
public class ArduinoProgrammer implements ActionListener, StandardMethods, WindowListener {
    
    public static final String PROGRAMNAME = "Arduino Programmer";
    public static final String VERSION = "0.1";
    
    private final JFrameManager frame = new JFrameManager(PROGRAMNAME, VERSION);
    private final JAddOnStandard standard = new JAddOnStandard(PROGRAMNAME, VERSION, true, true, false, true, true);
    private final JMenuBar MB = new JMenuBar();
    private final JMenu M1 = new JMenu("File");
    private final JMenuItem M1I1 = new JMenuItem("Exit");
    private final JMenuItem M1I2 = new JMenuItem("Restart");
    
    public static final ArrayList<Editor> editors = new ArrayList<>();
    
    private File workspace_dir = null;
    
    private boolean init = false;
    
    public ArduinoProgrammer() {
        init();
        reloadConfig();
        reloadLang();
        initAfter();
    }
    
    private void initAfter() {
        frame.setDefaultCloseOperation(JFrameManager.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(800, 600));
        frame.addWindowListener(this);
        M1I1.addActionListener(this);
        M1I2.addActionListener(this);
        M1.add(M1I2);
        M1.add(M1I1);
        MB.add(M1);
        frame.setJMenuBar(MB);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        test();
    }
    
    private void test() {
        Editor editor = openEditor(frame, "TestEditor");
        editor.setVisible(true);
        editor.addTab("TestTab");
        Codegenerator codegenerator = new Codegenerator();
        Function function = new Function(codegenerator, "");
        Variable variable = new Variable(codegenerator, "int", "z", 0);
        function.setVariable(variable);
        codegenerator.addFunction(function);
        Function trolltest = new Function(codegenerator, "//test;");
        codegenerator.addFunction(trolltest);
        ForLoop loop = new ForLoop(codegenerator, "i");
        loop.setCountVariable(true);
        loop.setCountMaxName("z");
        loop.setOperator("<");
        loop.setEndFunction(new Function(codegenerator, "i++;"));
        loop.addFunction(trolltest);
        loop.addFunction(trolltest);
        loop.addFunction(trolltest);
        codegenerator.addFunction(loop);
        StaticStandard.log("CODE: \n" + codegenerator.generateCode());
    }
    
    private boolean isWorkspace(File file) {
        //TODO das muss ausgeproggt werden, wenn der workspace mal mit .project files gemacht ist
        return true;
    }
    
    public Editor openEditor(Component c, String title) {
        Editor editor = new Editor(c, title);
        reloadLangEditors();
        return editor;
    }
    
    private void createWorkspace() {
        final File workspace_dir_old = workspace_dir;
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(workspace_dir);
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        boolean run = true;
        while(run) {
            int result = fc.showDialog(frame, StaticStandard.getLang().getLang("workspace_dir_selection", "Set as workspace"));
            if(result == JFileChooser.APPROVE_OPTION) {
                File file = ((fc.getSelectedFile() != null) ? fc.getSelectedFile() : fc.getCurrentDirectory());
                boolean good = false;
                if(isWorkspace(file)) {
                    good = true;
                } else {
                    if(file.listFiles().length > 0) {
                        int input = JOptionPane.showConfirmDialog(frame, StaticStandard.getLang().getLang("workspace_dir_selection_not_empty", "Directory is not empty, do you want to use it as your workspace?"), StaticStandard.getLang().getLang("workspace_dir_selection_not_empty_title", "Warning"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(input == JOptionPane.YES_OPTION) {
                            good = true;
                        } else {
                            good = false;
                        }
                    } else {
                        good = true;
                    }
                }
                if(good) {
                    workspace_dir = file;
                    StaticStandard.getConfig().setProperty("workspace_dir", workspace_dir.getAbsolutePath());
                    StaticStandard.getConfig().saveConfig();
                    StaticStandard.log(String.format("Set \"%s\" as workspace", workspace_dir.getAbsolutePath()));
                    run = false;
                }
            } else {
                run = false;
                exit();
            }
        }
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
        StaticStandard.getConfig().setDefaultConfig(new String[] {"lang", "workspace_dir"}, new String[] {"EN", "null"});
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
        try {
            String workspace_dir_string = StaticStandard.getConfig().getProperty("workspace_dir", "null");
            if(workspace_dir_string == null || workspace_dir_string.equalsIgnoreCase("null") || workspace_dir_string.isEmpty()) {
                createWorkspace();
            } else {
                workspace_dir = new File(workspace_dir_string);
            }
        } catch (Exception ex) {
        }
        return true;
    }
    
    private void reloadLangEditors() {
        try {
            StaticStandard.getConfig().reloadConfig();
            StaticStandard.getLang().setLang(StaticStandard.getConfig().getProperty("lang", "EN"));
            StaticStandard.getLang().reloadLang();
            StaticStandard.getLogger().update();
            for(Editor editor : editors) {
                try {
                    editor.M1.setText(StaticStandard.getLang().getLang("file", "File"));
                    editor.M2.setText(StaticStandard.getLang().getLang("edit", "Edit"));
                    editor.M1I1.setText(StaticStandard.getLang().getLang("exit", "Exit"));
                } catch (Exception ex) {
                }
            }
        } catch (Exception ex) {
        }
    }
    
    @Override
    public boolean reloadLang() {
        StaticStandard.getConfig().reloadConfig();
        StaticStandard.getLang().setLang(StaticStandard.getConfig().getProperty("lang", "EN"));
        StaticStandard.getLang().reloadLang();
        StaticStandard.getLogger().update();
        reloadLangEditors();
        //send.setText(StaticStandard.getLang().getLang("send", "Send"));
        //M1.setText(StaticStandard.getLang().getLang("chat", "Chat"));
        
        M1.setText(StaticStandard.getLang().getLang("file", "File"));
        /*
        M3.setText(StaticStandard.getLang().getLang("extras", "Extras"));

        M1I1.setText(StaticStandard.getLang().getLang("add_new_chat", "Add New Chat"));
        M1I2.setText(StaticStandard.getLang().getLang("close_chat", "Close Chat"));
        M1I3.setText(StaticStandard.getLang().getLang("available_chat", "Available Chat"));
        M1I4.setText(StaticStandard.getLang().getLang("join_chat", "Join Chat"));
        M1I5.setText(StaticStandard.getLang().getLang("connect_to_server", "Connect to Server"));
        M1I6.setText(StaticStandard.getLang().getLang("join_server", "Join Server"));
        */
        
        M1I1.setText(StaticStandard.getLang().getLang("exit", "Exit"));
        M1I2.setText(StaticStandard.getLang().getLang("restart", "Restart"));
        /*
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
            StaticStandard.getConfig().saveConfig();
        } catch (Exception ex) {
        }
        StaticStandard.exit();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == M1I1) {
            exit();
        } else if(e.getSource() == M1I2) {
            JUtils.restart();
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
