package com.cipher.engine;

public enum Facing {
        // Options for the Facing(s)
        NA         (-1),  // # -1 - Not Available / Error, special indicator, mostly replaced by 'null'
        NORTH       (0),  // # 0 - Iterating clockwise starting from North with 0 as the first index
        NORTHEAST   (1),  // # 1
        EAST        (2),  // # 2
        SOUTHEAST   (3),  // # 3
        SOUTH       (4),  // # 4
        SOUTHWEST   (5),  // # 5
        WEST        (6),  // # 6
        NORTHWEST   (7),  // # 7
        BELOW       (8),  // # 9 - Below and above are special cases of cube faces as they do not have compass names
        ABOVE       (9);  // # 8 

        private final int level;
        public static final Facing[] facings = new Facing[] { NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST, BELOW, ABOVE };
        
        // Allow a numeric level to be associated with the enum Facing
        Facing(int level){
            this.level = level;
        }
        public int getLevel(){
            return this.level;
        }
        
        // Stored static list of available indices
        public Facing getByIndx(int index){
            return facings[index];
        }
        
}
