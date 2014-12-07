/**
 * Enum for room costs.
 * @author Linye Ouyang
 *
 */
public enum RoomCost {
Luxury(200), Economical(80);
private int cost;
private RoomCost(int cost){
	this.cost=cost;
}
public int getCost(){
	return cost;
}
}
