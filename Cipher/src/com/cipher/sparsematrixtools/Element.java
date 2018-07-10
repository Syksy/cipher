package com.cipher.sparsematrixtools;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Syksy
 */
public class Element<T> {
    private T t;    
    public Element(T t){
        this.t = t;
    }
    public T get(){
        return this.t;
    }
    public void set(T t){
        this.t = t;
    }
}
