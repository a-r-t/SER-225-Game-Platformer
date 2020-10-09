package Level;

public class TileTypeHolder {
	private TileType tileType;
	
	TileTypeHolder(TileType type){
		tileType = type;
	}
	
	public TileType getTileType(){
		return tileType;
	}
	
	public void setTileType(TileType inputTile){
		tileType = inputTile;
	}
}
