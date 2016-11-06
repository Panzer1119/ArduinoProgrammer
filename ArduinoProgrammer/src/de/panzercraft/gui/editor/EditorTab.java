/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.panzercraft.gui.editor;

import jaddon.controller.StaticStandard;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Paul
 */
public class EditorTab {
    
    private Editor editor;
    private String name;
    private final JEditorPane ep = new JEditorPane();
    private final JScrollPane sp = new JScrollPane(ep, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private boolean isAdded = false;
    
    public EditorTab(Editor editor, String name) {
        this.editor = editor;
        this.name = name;
    }
    
    public boolean add() {
        if(isAdded) {
            return true;
        }
        try {
            editor.tabbedpane.add(name, sp);
            isAdded = true;
            return true;
        } catch (Exception ex) {
            isAdded = false;
            return false;
        }
    }
    
    public boolean delete() {
        if(!isAdded) {
            return true;
        }
        try {
            editor.tabbedpane.remove(editor.tabbedpane.indexOfComponent(sp));
            isAdded = false;
            return true;
        } catch (Exception ex) {
            isAdded = true;
            return false;
        }
    }
    
    public Editor moveToEditor(Editor editor) {
        try {
            delete();
            editor.addTab(this);
        } catch (Exception ex) {
            StaticStandard.logErr("Error while moving editortab: " + ex, ex);
        }
        return editor;
    }
    
    public String getText() {
        return ep.getText();
    }
    
    public String getSelectedText() {
        return ep.getSelectedText();
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JEditorPane getEditorPane() {
        return ep;
    }

    public JScrollPane getScrollPane() {
        return sp;
    }

    public boolean isAdded() {
        return isAdded;
    }
    
}
