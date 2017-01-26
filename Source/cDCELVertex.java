//class definition for doubly connected edge list vertex
import java.util.List;
import java.util.ArrayList;

public class cDCELVertex {
	List<cDCELHalfEdge> incidentEdges = new ArrayList<>();
    cPointi coord;
    // String override function
	public String toString(){
		return coord.x + "," + coord.y; 
	}
    public void addIncidentEdge(cDCELHalfEdge edge){
		incidentEdges.add(edge);
	}
    //method to find incident half edge on a vertex
    public cDCELHalfEdge findIncidentHalfEdge(cDCELVertex vertex2){
		for(cDCELHalfEdge head: incidentEdges){
			cDCELHalfEdge temp = head;
			do{
				if(temp.getOrigin()==vertex2){
					return head;
				}
				temp = temp.getNext();
			}while(temp.getOrigin()!=this);
		}
		
		return null;
	}
	public cPointi getCoord() {
		return coord;
	}
	public void setCoord(cPointi coord) {
		this.coord = coord;
	}
	
	
}
