package com.cipher.engine;

// Generic parameter wrapper for most parameter types that have to be passed around in the engine
public class CipherParameter {
    private String parName;
    private Class<?> parType;
    private Object val;

    // Format the parameter
    public CipherParameter(String name, Class<?> type){
        this.parName = name;
        this.parType = type;
    }
    
    // Set value
    public <T> T set(T obj, Class<T> type){
        if(parType.equals(type)){
            T toReturn = type.cast(val);
            val = obj;
            return toReturn;
        }
        return null;
    }
    
    // Get value
    public <T> T get(Class<T> type){
        if(parType.equals(type)){
            return type.cast(val);
        }
        return null;
    }    
    
}
