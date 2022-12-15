package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class MountainTile extends Tile {
  public MountainTile() {
    super(100, 100, 100);
    this.type = TileType.Moutain;
  }
}
