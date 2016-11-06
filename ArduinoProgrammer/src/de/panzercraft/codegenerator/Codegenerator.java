/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.panzercraft.codegenerator;

import jaddon.controller.StaticStandard;
import java.util.ArrayList;

/**
 *
 * @author Paul
 */
public class Codegenerator {
    
    private final ArrayList<Function> functions = new ArrayList<>();
    private final ArrayList<Variable> variables = new ArrayList<>();
    private final ArrayList<Variable> variables_temp = new ArrayList<>();
    private final ArrayList<String> errors = new ArrayList<>();
    private String code = "";
    
    public Codegenerator() {
        
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }
    
    public void setFunctions(ArrayList<Function> functions) {
        this.functions.clear();
        for(Function function : functions) {
            this.functions.add(function);
        }
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }
    
    public void setVariables(ArrayList<Variable> variables) {
        this.variables.clear();
        for(Variable variable : variables) {
            this.variables.add(variable);
        }
    }
    
    public Object getValue(String name) {
        for(Variable variable : variables) {
            if(variable.getName().equals(name)) {
                return variable.getValue();
            }
        }
        return null;
    }
    
    public void addFunction(Function function) {
        functions.add(function);
    }
    
    public void reset() {
        functions.clear();
        variables.clear();
        variables_temp.clear();
        code = "";
    }
    
    public boolean existsVariable(String name) {
        for(Variable variable : variables) {
            if(variable.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean existsVariableTemp(String name) {
        for(Variable variable : variables_temp) {
            if(variable.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
    
    public void addErrors(String errors) {
        String[] split = errors.split("\n");
        for(String g : split) {
            addError(g);
        }
    }
    
    public void addError(String error) {
        errors.add(error);
    }

    public ArrayList<Variable> getVariablesTemp() {
        return variables_temp;
    }

    public String getCode() {
        return code;
    }
    
    public String generateCode() {
        errors.clear();
        variables_temp.clear();
        code = "";
        for(Function function : functions) {
            try {
                function.enable();
                code += function + "\n";
                Thread.sleep(10);
                StaticStandard.log(variables_temp.size());
            } catch (Exception ex) {
            }
        }
        StaticStandard.log("Error log:");
        for(String error : errors) {
            StaticStandard.logErr(error);
        }
        return code;
    }
    
}
