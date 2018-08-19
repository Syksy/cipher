// ProcessCommands are such that they can be queued by any process, e.g. a client connected to the main thread.
// They have to be validated and processed by the main engine thread, before they can be procesed as PrimalCommands that will make real changes to the game.

package com.cipher.engine;

public interface ProcessCommand {
       
    @Override
    public String toString();
}
