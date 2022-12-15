package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class DesertTile extends Tile {
  public DesertTile() {
    super(2, 6, 3);
    this.type = TileType.Desert;
  }
}
