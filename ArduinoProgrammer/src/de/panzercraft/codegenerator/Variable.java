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
public class Variable {
    
    private final Codegenerator codegenerator;
    private final String type;
    private final String name;
    private Object value;
    
    public Variable(Codegenerator codegenerator, String type, String name, Object value) {
        this.codegenerator = codegenerator;
        this.type = type;
        this.name = name;
        this.value = value;
        codegenerator.getVariables().add(this);
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    private Function getFunction() {
        Function function = new Function(null, toString());
        return function;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s = %s;", type, name, value);
    }
    
}
