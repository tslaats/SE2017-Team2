package datamodel;

public class Position {
	
	private int x;
	private int y;
	
	public Position(final int x, final int y) {
		this.setX(x);
		this.setY(y);
	}
	
	@Override
	public String toString() { 
	    return "("+this.x()+","+this.y()+")";
	} 
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (!(other instanceof Position))return false;
	    if (this.x() == ((Position) other).x() && this.y() == ((Position) other).y()) {
	    	return true;
	    }
	    return false;
	    
	}
	
	public int x() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int y() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
