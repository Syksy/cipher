// Primal commands are such that they should always be only called by the main Cipher-engine thread, as otherwise the content could be manipulated.
// This includes e.g. moving objects or characters, creating or deleting such, etc.

package com.cipher.engine;

public interface PrimalCommand {
    
    @Override
    public String toString();
}
