/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.panzercraft.codegenerator;

/**
 *
 * @author Paul
 */
public class Function {
    
    protected final Codegenerator codegenerator;
    private final String function;
    private boolean disabled = false;
    private Variable variable = null;
    
    public Function(Codegenerator codegenerator, String function) {
        this.codegenerator = codegenerator;
        this.function = function;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
    public void enable() {
        if(variable != null) {
            codegenerator.getVariablesTemp().add(variable);
        }
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }
    
    @Override
    public String toString() {
        if(variable != null) {
            return variable.toString();
        } else {
            return ((disabled) ? "//" : "") + function + ((function.endsWith(";")) ? "" : ";");
        }
    }
    
}
