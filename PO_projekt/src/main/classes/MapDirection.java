package classes;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST,
    NORTHWEST,
    NORTHEAST,
    SOUTHWEST,
    SOUTHEAST;


    public String toString(){
        switch(this) {
            case NORTH: return "Północ";
            case SOUTH: return "Południe";
            case WEST: return "Zachód";
            case EAST: return "Wschód";
            case NORTHWEST: return "Północny-zachód";
            case NORTHEAST: return "Północny-wschód";
            case SOUTHWEST: return "Południowy-Zachód";
            case SOUTHEAST: return "Połódniowy-Wschód";
            default:
                return null;
        }
    }

    public MapDirection next(){
        switch(this) {
            case NORTH: return NORTHEAST;
            case SOUTH: return SOUTHWEST;
            case WEST: return NORTHWEST;
            case EAST: return SOUTHEAST;
            case NORTHWEST: return NORTH;
            case NORTHEAST: return EAST;
            case SOUTHWEST: return WEST;
            case SOUTHEAST: return SOUTH;
            default:
                return null;
        }
    }

    public MapDirection previous(){
        switch(this) {
            case NORTH: return NORTHWEST;
            case SOUTH: return SOUTHEAST;
            case WEST: return SOUTHWEST;
            case EAST: return NORTHEAST;
            case NORTHWEST: return WEST;
            case NORTHEAST: return NORTH;
            case SOUTHWEST: return SOUTH;
            case SOUTHEAST: return EAST;
            default:
                return null;
        }
    }

    public Vector2d toUnitVector(){
        switch(this) {
            case NORTH: return new Vector2d(0,1);
            case SOUTH: return new Vector2d(0,-1);
            case WEST:  return new Vector2d(-1,0);
            case EAST: return new Vector2d(1,0);
            case NORTHWEST: return new Vector2d(-1,1);
            case NORTHEAST: return new Vector2d(1,1);
            case SOUTHWEST: return new Vector2d(-1,-1);
            case SOUTHEAST: return new Vector2d(1,-1);
            default:
                return null;
        }
    }
}

