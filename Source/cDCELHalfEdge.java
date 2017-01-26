//Class definition for doubly connected edge list half edge.
public class cDCELHalfEdge {
	cDCELHalfEdge next,prev;
    cDCELVertex origin;
	cDCELHalfEdge twin;
    boolean isEssential=true;
	boolean isDiagonal;
    //string override function
	public String toString(){
		return origin.toString() +" -> " + twin.origin.toString();
	}
	public cDCELVertex getOrigin() {
		return origin;
	}
	public void setOrigin(cDCELVertex origin) {
		this.origin = origin;
	}
    public boolean isEssential() {
		return isEssential;
	}
	public void setEssential(boolean isEssential) {
		this.isEssential = isEssential;
	}
	public cDCELHalfEdge getTwin() {
		return twin;
	}
    public boolean isDiagonal() {
		return isDiagonal;
	}
	public void setDiagonal(boolean isDiagonal) {
		this.isDiagonal = isDiagonal;
	}
	public void setTwin(cDCELHalfEdge twin) {
		this.twin = twin;
	}
	public cDCELHalfEdge getNext() {
		return next;
	}
	public void setNext(cDCELHalfEdge next) {
		this.next = next;
	}
	public cDCELHalfEdge getPrev() {
		return prev;
	}
	public void setPrev(cDCELHalfEdge prev) {
		this.prev = prev;
	}
	
}
