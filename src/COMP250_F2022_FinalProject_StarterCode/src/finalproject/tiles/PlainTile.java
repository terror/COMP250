package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class PlainTile extends Tile {
  // TODO level 0: finish constructor
  public PlainTile() {
    super(3, 1, 0);
    this.type = TileType.Plain;
  }
}
