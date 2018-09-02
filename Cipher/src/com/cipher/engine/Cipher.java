package com.cipher.engine;

// Cipher DB handling
import com.cipher.db.*;
import com.cipher.engine.Cmdline.Input;

public class Cipher {
    // db is an abstract connection to the Cipher database; various implementations may exist for different purposes
    // e.g. SQL database, JSON-file, serialized binary save game file etc.
    private static CipherDB db;
    
    public static void main(String[] args){
        db = new CipherDBPostgreSQL(); // When the database class is created, a connection attempt to MySQL is done together with the appropriate tables (unless they already exist)

        // Run Cipher command line
        Input input = new Input();
        input.CipherCmdline(db);
        
        System.out.println("\nEnd of main, disconnecting... \n");
        db.disconnect();
    }    
}
