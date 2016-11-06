/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.panzercraft.codegenerator;

import java.util.ArrayList;

/**
 *
 * @author Paul
 */
public class ForLoop extends Function {
    
    private final ArrayList<Function> functions = new ArrayList<>();
    private String counter_name;
    private int start_value = -1;
    private String operator = "";
    private boolean countVariable = false; //Zählt bis zu einer variable nicht einer festegelegten zahl
    private int count_max = -1;
    private String count_max_name = "";
    private Function endFunction = null;
    
    public ForLoop(Codegenerator codegenerator, String counter_name) {
        super(codegenerator, "FORLOOP");
        this.counter_name = counter_name;
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
    
    public void addFunction(Function function) {
        functions.add(function);
    }

    public String getCounterName() {
        return counter_name;
    }

    public void setCounterName(String counter_name) {
        this.counter_name = counter_name;
    }

    public boolean isCountVariable() {
        return countVariable;
    }

    public void setCountVariable(boolean countVariable) {
        this.countVariable = countVariable;
    }

    public int getCountMax() {
        return count_max;
    }

    public void setCountMax(int count_max) {
        this.count_max = count_max;
    }

    public String getCountMaxName() {
        return count_max_name;
    }

    public void setCountMaxName(String count_max_name) {
        this.count_max_name = count_max_name;
    }

    public int getStartValue() {
        return start_value;
    }

    public void setStartValue(int start_value) {
        this.start_value = start_value;
    }

    public Function getEndFunction() {
        return endFunction;
    }

    public void setEndFunction(Function endFunction) {
        this.endFunction = endFunction;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public String check() {
        try {
            if(countVariable) {
                if(codegenerator.existsVariableTemp(count_max_name)) {
                    return String.format("Variable \"%s\" does not exist in the for loop", count_max_name);
                } else {
                    if(operator.isEmpty() || operator == null) {
                        return "No operator in the for loop";
                    } else {
                        if(endFunction == null) {
                            return "No end function in the for loop";
                        } else {
                            return null;
                        }
                    }
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            return "For loop failed the check";
        }
    }
    
    @Override
    public String toString() {
        String errors = check();
        if(errors != null) {
            codegenerator.addErrors(errors);
        }
        String toString = String.format("for(int %s = %d; %s %s %s; %s) {%n", counter_name, start_value, counter_name, operator, (countVariable ? count_max_name : "" + count_max), ((endFunction.toString().endsWith(";")) ? endFunction.toString().substring(0, endFunction.toString().length() - 1) : endFunction));
        for(Function function : functions) {
            if(function != null) {
                toString += "   " + function + "\n";
            }
        }
        toString += "}";
        return toString;
    }
    
}
