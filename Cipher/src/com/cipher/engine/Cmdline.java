/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cipher.engine;

// Input/output console
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// Other stuff
import java.util.Arrays;

import com.cipher.db.*;
/**
 *
 * @author Syksy
 */
class Cmdline {
    // Static class for processing all sorts of character-based input that can interact with the Cipher engine
    public static class Input{        
        // Level of verbosity in processing the input; 0 = silent, 1 = standard user, 2 = debug, 3 = ultra-debug
        public int verbosity = 2;
        
        // Extract String[] command parameters following the command itself
        public String[] pars(String str){
            String[] pars = str.split(" ");
            String[] newpars = {};
            if(pars!=null){
                newpars = new String[pars.length-1];
                for(int i=0; i<newpars.length; i++) newpars[i] = pars[i+1];
            }            
            return newpars;
        }
        // Extract the number of parameters following the command itself
        public int npars(String str){
            String[] pars = str.split(" ");
            int npars = -1; // The initial command is not counted into the parameters
            if(pars!=null) for(String par : pars) npars++;            
            return npars;
        }
        // Get the i:th command parameter
        public String getParAt(String str, int i){
            return this.pars(str)[i];
        }
        
        // Use RegEx etc to remove potential SQL injection and other potentially malicious input
        public String sanitize(String str){
            // List of naughty words, such as DROP table in SQL, that should never get through
            String[] naughty = {"drop", "from"};
            
            return str;
        }
        
        // A command-line based interface for processing user input until user desires to quit
        public void CipherCmdline(CipherDB db){
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));            
                String input = "";
                String[] pars = {};
                if(verbosity>=1) System.out.println("Starting Cipher command line...\n"
                    + "Enter commands following by a line change with a spacebar as the delimiter for parameters.\n"
                    + "Type 'help' for a list of commands or 'q'/quit'/'e'/exit' for stopping this process.\n");
                //input = System.console().readLine().toLowerCase().trim();
                // Indicate Cipher console with a '> '-prefix
                System.out.print("> ");
                // Sanitize, lower case and trim the input string to avoid potential issues
                input = this.sanitize(reader.readLine().toLowerCase().trim());
                // While-loop the command line(s) until user wants to quit
                while(!Arrays.asList("q","quit","e","exit").contains(input)){
                    if(verbosity>=1) System.out.println("\n-- '" + input + "' called --\n");
                    String command = input.split(" ")[0];
                    // If user provides a non-empty line, try to parse and process the desired command
                    if(command!=null){
                        if(verbosity>=2) System.out.println("Command: " + command + ", number of parameters (including command): " + this.npars(input));
                        if(verbosity>=3){
                            for(int i = 0; i < this.npars(input); i++){
                                System.out.println("Parameter index " + i + ", content: " + this.getParAt(input, i));
                            }
                        }
                        switch(command){
                            // User wishes to print the help file
                            case "help": case "h": case "commands":
                                System.out.println(this.HelpFile());
                                break;
                            // User wishes to create a tile, edge, etc
                            case "create": case "c":
                                if(this.npars(input)>=2){
                                    String par1 = this.getParAt(input, 0);
                                    switch(par1){
                                        case "tile":
                                            db.addTile(
                                                new Tile(
                                                        Integer.parseInt(this.getParAt(input, 1)), // x
                                                        Integer.parseInt(this.getParAt(input, 2)), // y
                                                        Integer.parseInt(this.getParAt(input, 3)), // z
                                                        this.getParAt(input, 4).charAt(0) // character symbol
                                                    )
                                                );
                                            break;
                                        case "edge":
                                            db.addEdge(
                                                new Edge(
                                                        Integer.parseInt(this.getParAt(input, 1)), // x1
                                                        Integer.parseInt(this.getParAt(input, 2)), // y1
                                                        Integer.parseInt(this.getParAt(input, 3)), // x2
                                                        Integer.parseInt(this.getParAt(input, 4)), // y2
                                                        Integer.parseInt(this.getParAt(input, 5)) // z
                                                    )
                                                );
                                            break;

                                        default:

                                            break;
                                    }
                                }else{
                                    if(verbosity>=1) System.out.println("Illegal number of parameters provided for 'create': " + this.npars(input));
                                }
                                break;
                            // User wishes to print information from the base such as all the tiles or a particular tile at given coordinates
                            case "print": case "p":
                                
                                break;
                            // Set level of verbosity to an integer
                            case "verbosity": case "v":
                                this.verbosity = Integer.parseInt(this.getParAt(input, 0));
                                if(verbosity>=1) System.out.println("Command line verbosity level changed to: " + this.verbosity);
                                break;
                            // Informing the user that an unidentified command was provided    
                            default:
                                if(verbosity>=1) System.out.println("Unknown Cipher command: " + command + "\n");
                                break;
                        }             
                    }else{
                        if(verbosity>=1) System.out.println("Unable to process empty input.\n");
                    }
                    // Indicate Cipher console with a '> '-prefix
                    System.out.print("> ");
                    // Sanitize, lower case and trim the input string to avoid potential issues
                    input = this.sanitize(reader.readLine().toLowerCase().trim());
                }
                if(verbosity>=1) System.out.println("Stopping Cipher command line...\n");
            }catch(Exception e){
                if(verbosity>=1) System.out.println("Exception when trying to run Cipher console: " + e + "\n");
            }
        }
        
        // Print all available commands into the command line
        public String HelpFile(){
            String help =
                    "\n\n" + 
                    "\n== Cipher command line help == " +
                    "\n help|h|commands: this help file" + // Line 2 
                    "\n create [tile/edge]: " + // etc
                    "\n print: TODO" + // etc
                    "\n verbosity [0/1/2]: Set level of verbosity (0 = silent, 1 = standard, 2 = debug)" + // etc
                    "\n\n" + 
                    "";
            return help;
        }
    }
}
