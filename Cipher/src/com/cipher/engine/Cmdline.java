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
                        // Do not go into switch-case loop if the user wanted a comment starting with symbol '#' or '%'
                        if(command.charAt(0) != '#' & command.charAt(0) != '%') { 
                            switch(command){
                                // User wishes to print the help file
                                case "help": case "h": case "commands":
                                    try{
                                        System.out.println(this.HelpFile());
                                    }catch(Exception e){
                                        System.out.println("Error printing the help string: " + e);
                                    }
                                    break;
                                // User wishes to create a tile, edge, etc
                                case "create": case "c":
                                    if(this.npars(input)>=2){
                                        String par1 = this.getParAt(input, 0);
                                        switch(par1){
                                            case "tile":
                                                try{
                                                    System.out.println("Create tile call returned with: " + db.addTile(
                                                        new Tile(
                                                                Integer.parseInt(this.getParAt(input, 1)), // x
                                                                Integer.parseInt(this.getParAt(input, 2)), // y
                                                                Integer.parseInt(this.getParAt(input, 3)), // z
                                                                this.getParAt(input, 4).charAt(0) // Tile character symbol
                                                            )
                                                        ));
                                                }catch(Exception e){
                                                    System.out.println("'create tile' error: " + e);
                                                }
                                                break;
                                            case "edge":
                                                try{
                                                    if(verbosity>=1) System.out.println("Creating tile...\n");
                                                    System.out.println("Create edge call returned with: " + db.addEdge(
                                                        new Edge(
                                                                Integer.parseInt(this.getParAt(input, 1)), // x1
                                                                Integer.parseInt(this.getParAt(input, 2)), // x2
                                                                Integer.parseInt(this.getParAt(input, 3)), // y1
                                                                Integer.parseInt(this.getParAt(input, 4)), // y2
                                                                Integer.parseInt(this.getParAt(input, 5)), // z1
                                                                Integer.parseInt(this.getParAt(input, 6)), // z2
                                                                this.getParAt(input, 6).charAt(0) // Edge character symbol
                                                            )
                                                        ));
                                                }catch(Exception e){
                                                    System.out.println("'create edge' error: " + e);
                                                }
                                                break;
                                            default:
                                                if(verbosity>=1) System.out.println("Invalid first parameter for 'create': " + this.getParAt(input, 0) + "\n");
                                                break;
                                        }
                                    }else{
                                        if(verbosity>=1) System.out.println("Illegal number of parameters provided for 'create': " + this.npars(input) + "\n");
                                    }
                                    break;
                                // Deleting stuff
                                case "delete": case "d":
                                    if(this.npars(input)>=2){
                                        String par1 = this.getParAt(input, 0);
                                        switch(par1){
                                            // Delete a tile from DB
                                            case "tile": case "t":
                                                try{
                                                    if(verbosity>=1) System.out.println("Deleting tile...\n");
                                                    System.out.println("Delete tile call returned with: " + db.deteleTile(
                                                            Integer.parseInt(this.getParAt(input, 1)), 
                                                            Integer.parseInt(this.getParAt(input, 2)), 
                                                            Integer.parseInt(this.getParAt(input, 3))
                                                        )                                                    
                                                    );
                                                }catch(Exception e){
                                                    System.out.println("'delete tile' error: " + e);
                                                }
                                                break;
                                            // Delete an edge from DB
                                            case "edge": case "e":
                                                try{
                                                    if(verbosity>=1) System.out.println("Deleting edge...\n");
                                                    System.out.println("Delete edge call returned with: " + db.deteleEdge(
                                                            Integer.parseInt(this.getParAt(input, 1)), // x1
                                                            Integer.parseInt(this.getParAt(input, 2)), // x2
                                                            Integer.parseInt(this.getParAt(input, 3)), // y1 
                                                            Integer.parseInt(this.getParAt(input, 4)), // y2
                                                            Integer.parseInt(this.getParAt(input, 5)), // z1
                                                            Integer.parseInt(this.getParAt(input, 6))  // z2
                                                        )                                                    
                                                    );
                                                }catch(Exception e){
                                                    System.out.println("'delete edge' error: " + e);
                                                }
                                                break;
                                            default:
                                                if(verbosity>=1) System.out.println("Invalid first parameter for 'delete': " + this.getParAt(input, 0) + "\n");
                                                break;
                                        }
                                    }
                                    break;
                                // User wishes to print information from the base such as all the tiles or a particular tile at given coordinates
                                case "print": case "p":
                                    if(this.npars(input)>=1){
                                        String par1 = this.getParAt(input, 0);
                                        switch(par1){
                                            // Loop through the z-axis and print symbol tile/edge representation for the map {x,y} at each z-value
                                            case "map": case "m":
                                                try{
                                                    System.out.println(db.getMap());
                                                }catch(Exception e){
                                                    System.out.println("'print map' error: " + e);
                                                }
                                                break;
                                            // Print a {x,y} symbol tile/edge map slice at specific z-value given as a parameter
                                            case "slice": case "s":
                                                try{
                                                    // TODO
                                                }catch(Exception e){
                                                    System.out.println("'print slice' error: " + e);
                                                }
                                                break;
                                            // Print all tiles in the database
                                            case "tiles": case "t":
                                                try{
                                                    System.out.println(db.getTiles());
                                                }catch(Exception e){
                                                    System.out.println("'print tiles' error: " + e);
                                                }
                                                break;
                                            // Print all edges in the database
                                            case "edges": case "e":
                                                try{
                                                    System.out.println(db.getEdges());
                                                }catch(Exception e){
                                                    System.out.println("'print edges' error: " + e);
                                                }
                                                break;
                                            default:
                                                if(verbosity>=1) System.out.println("Invalid first parameter for 'print': " + this.getParAt(input, 0) + "\n");
                                                break;
                                        }
                                    }else{
                                        if(verbosity>=1) System.out.println("Illegal number of parameters provided for 'print': " + this.npars(input) + "\n");
                                    }

                                    break;
                                // Set level of verbosity to an integer
                                case "verbosity": case "v":
                                    try{
                                        this.verbosity = Integer.parseInt(this.getParAt(input, 0));
                                        if(verbosity>=1) System.out.println("Command line verbosity level changed to: " + this.verbosity);
                                    }catch(Exception e){
                                        System.out.println("Error changing verbosity: " + e);
                                    }
                                    break;
                                // Informing the user that an unidentified command was provided    
                                default:
                                    if(verbosity>=1) System.out.println("Unknown Cipher command: " + command + "\n");
                                    break;
                            }             
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
                    "\n" + 
                    "\n == Cipher command line help == " +
                    "\n {opt1/opt2} indicates options opt1 and opt2 as feasible choices." +
                    "\n List of parameter types are shown in square brackets following the parameter list itself." + 
                    "\n If you want to an ignored comment, start the line with character '#' or '%'" + 
                    "\n ..." + 
                    "\n {help/h/commands}: this help file" + // Line 2 
                    "\n create {tile/edge}: Create a tile or an edge depending on the first parameter (further parameters required as indicated below)" + // etc
                    "\n create tile x y z symbol [INT INT INT CHAR(1)]" +
                    "\n create edge x1 x2 y1 y2 z1 z2 symbol [INT INT INT INT INT INT CHAR(1)]" +
                    "\n delete {tile/edge}: Delete a tile or an edge depending on the first parameter (further parameters required as indicated below)" + // etc
                    "\n delete tile x y z symbol [INT INT INT CHAR(1)]" +
                    "\n delete edge x1 x2 y1 y2 z1 z2 [INT INT INT INT INT INT]" +
                    "\n print {map/tiles/edges}" + // etc
                    "\n verbosity {0/1/2/...} [INT]: Set level of verbosity (0 = silent, 1 = standard, 2 = debug)" + // etc
                    "\n";
            return help;
        }
    }
}
