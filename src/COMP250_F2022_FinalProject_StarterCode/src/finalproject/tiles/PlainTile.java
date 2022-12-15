package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class PlainTile extends Tile {
  public PlainTile() {
    super(3, 1, 0);
    this.type = TileType.Plain;
  }
}
