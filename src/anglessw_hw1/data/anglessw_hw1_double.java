/**
 * Author: Steven Angles
 * Class: CSE381
 * Professor: Dr. Rao
 * 
 * Description:
 * This class represents a double type and encapsulates certain operations to make processing easier.
 */
package anglessw_hw1.data;

public class anglessw_hw1_double extends anglessw_hw1_base {
	private double data;
	
	public anglessw_hw1_double(Object obj) {
		super(obj);
		this.data = (double) obj;
	}
	
	public String toString() {
		return ""+data;
	}
	
	public boolean equals(Object obj) {
		double temp = Double.parseDouble((String) obj);
		return (this.data == temp);
	}
	
	public Object getData() {
		return this.data;
	}
	
	public String getType() {
		return "double";
	}
}
