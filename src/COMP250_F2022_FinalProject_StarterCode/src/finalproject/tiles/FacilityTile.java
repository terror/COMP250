package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class FacilityTile extends Tile {
  public FacilityTile() {
    super(1, 2, 0);
    this.type = TileType.Facility;
  }
}
