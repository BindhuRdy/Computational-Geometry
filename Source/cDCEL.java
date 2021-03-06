import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
/* Doubly connected edge list construction for given polygon vertices*/

public class cDCEL{
	List<cDCELVertex> vertices;
    HashMap<cVertex,cDCELVertex> map = new HashMap<>();
	List<cDCELHalfEdge> halfedges;
	public cDCEL(cPolygoni pol) {
		int n = pol.listcopy.n;
		cDCELHalfEdge prevEdge=null;
        vertices = new ArrayList<>();
		halfedges = new ArrayList<>();
        cVertex temp = pol.listcopy.head;
		cDCELHalfEdge prevTwinEdge=null;
		for(int i=0;i<n;i++){
			cDCELVertex vert = new cDCELVertex();
            //setting 
            cDCELHalfEdge edge = new cDCELHalfEdge();
            cDCELHalfEdge twinEdge = new cDCELHalfEdge();
			edge.setDiagonal(false);
			edge.setOrigin(vert);
			edge.setTwin(twinEdge);
			edge.setPrev(prevEdge);
            //setting twin edge

			twinEdge.setDiagonal(false);
			twinEdge.setNext(prevTwinEdge);
			twinEdge.setTwin(edge);
			
			if(prevEdge!=null){
				prevEdge.setNext(edge);
			}
			
			if(prevTwinEdge!=null){
				prevTwinEdge.setOrigin(vert);
				prevTwinEdge.setPrev(twinEdge);
			}
			vert.setCoord(temp.v);
			vert.addIncidentEdge(edge);
			vertices.add(vert);
			
			halfedges.add(edge);
			halfedges.add(twinEdge);
			
			prevEdge = edge;
			prevTwinEdge = twinEdge;
			
			map.put(temp, vert);
			
			temp = temp.next;
		}
		
		if(halfedges.size()>0){
			prevTwinEdge.setOrigin(vertices.get(0));
			prevTwinEdge.setPrev(halfedges.get(1));
			prevEdge.setNext(halfedges.get(0));
			halfedges.get(1).setNext(prevTwinEdge);
			halfedges.get(0).setPrev(prevEdge);
		}
	}
	
	public void addDiagonal(cVertex vert1,cVertex vert2){
		cDCELVertex v1 = map.get(vert1);
		cDCELVertex v2 = map.get(vert2);
		System.out.println("vertices we got are "+ v1+", "+v2);
		
		cDCELHalfEdge edge = new cDCELHalfEdge();
		cDCELHalfEdge twinEdge = new cDCELHalfEdge();
		cDCELHalfEdge v1_incidentEdge = v1.findIncidentHalfEdge(v2);
		cDCELHalfEdge v2_incidentEdge = v2.findIncidentHalfEdge(v1);
		edge.setDiagonal(true);
		edge.setTwin(twinEdge);
		edge.setOrigin(v1);
		v1.addIncidentEdge(edge);
		v2.addIncidentEdge(twinEdge);
		twinEdge.setDiagonal(true);
		twinEdge.setTwin(edge);
		twinEdge.setOrigin(v2);
		
		edge.setNext(v2_incidentEdge);
		twinEdge.setNext(v1_incidentEdge);
		
		edge.setPrev(v1_incidentEdge.getPrev());
		twinEdge.setPrev(v2_incidentEdge.getPrev());
		
		v2_incidentEdge.getPrev().setNext(twinEdge);
		v1_incidentEdge.getPrev().setNext(edge);
		
		v1_incidentEdge.setPrev(twinEdge);
		v2_incidentEdge.setPrev(edge);
		halfedges.add(edge);
		halfedges.add(twinEdge);
		System.out.println("added diagonal between "+edge+ " essentiality"+edge.isEssential);
		
	}
	//method for printing
	public void print(){
		cDCELHalfEdge temp = halfedges.get(0);
				
		for(int i=vertices.size()*2;i<halfedges.size();i+=2){
			System.out.println(temp.getOrigin().getCoord().x+", "+temp.getOrigin().getCoord().y);
			System.out.println(temp);			
			temp = temp.next;
		}
	}
	// method to remove diagonal 
	public void removeDiagonal(int index){
		cDCELHalfEdge edge = halfedges.get(index);
		
		System.out.println("remove diagonal "+ edge);
		cDCELHalfEdge twinEdge = halfedges.get(index+1);
		edge.getPrev().setNext(twinEdge.getNext());
		twinEdge.getNext().setPrev(edge.getPrev());
		
		twinEdge.getPrev().setNext(edge.getNext());
		edge.getNext().setPrev(twinEdge.getPrev());
		
		edge.setEssential(false);
		twinEdge.setEssential(false);
	}
	// Method to check if the given tri point set results in reflex edges.  
	public boolean isReflex(cDCELVertex v0, cDCELVertex v1, cDCELVertex v2) {
	    cPointi p0 = v0.coord;
	    cPointi p1 = v1.coord;
	    cPointi p2 = v2.coord;
	    System.out.println("checking reflex between "+v0+" "+v1+" "+v2);
	    if(p1.LeftOn( p1, p2, p0))
	    	return false;
	    
	    return true;
	  }
      // Method to implement Hertel Nehlhorn Algorithm removing non essential diagonals
	public void hertelMehlhorn(){
		for(int i=vertices.size()*2;i<halfedges.size();i+=2){
			cDCELHalfEdge edge = halfedges.get(i);
			cDCELHalfEdge twinEdge = halfedges.get(i+1);
			
			cDCELVertex cur = edge.getOrigin();
			cDCELVertex next = edge.getPrev().getOrigin();
			cDCELVertex prev = edge.getTwin().getNext().getNext().getOrigin();
			
			System.out.println("working on " + edge+ " next "+edge.getNext()+" prev"+ edge.getPrev());
			System.out.println("Twin working on " + twinEdge+ " next "+twinEdge.getNext()+" prev"+ twinEdge.getPrev());
			System.out.println("Edges previous " +prev);
			System.out.println("Edges next "+next);
			
			if(isReflex(next,cur,prev)){
				System.out.println("Is reflex one");
				continue;
			}
			
			cur = edge.getTwin().getOrigin();
			next = edge.getTwin().getPrev().getOrigin();
			prev = edge.getNext().getNext().getOrigin();
			if(isReflex(next,cur,prev)){
				System.out.println("Is reflex two");
				continue;
			}
			removeDiagonal(i);
			
		}
	}
    //method to print the diagonals showing polygon decomposition
	public void DrawDiagonals(Graphics g, Color inColor)
	  {
	    System.out.println("Drawing diagonals");
	    for(int i=vertices.size()*2;i<halfedges.size();i+=2){
			cDCELHalfEdge edge = halfedges.get(i);
			cDCELVertex vert1 = edge.getOrigin();
			cDCELVertex vert2 = edge.getTwin().getOrigin();
			Color c = Color.BLACK;
			g.setColor(c);
            //printing only essential edges
            if(edge.isEssential()){
             	g.drawLine(vert1.coord.x, vert1.coord.y, vert2.coord.x, vert2.coord.y);
            }
		}
	
	  } 
}