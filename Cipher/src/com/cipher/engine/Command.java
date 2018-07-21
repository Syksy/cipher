/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cipher.engine;

/**
 *
 * @author Syksy
 */

// Interface for Cipher commands; e.g. actions in the game world that attempt to alter the database/game state/world
// All functions are just placeholders and will be implemented as their own classes
public interface Command {
    
    @Override
    public String toString();
}
