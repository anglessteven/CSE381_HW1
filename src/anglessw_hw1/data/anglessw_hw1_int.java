/**
 * Author: Steven Angles
 * Class: CSE381
 * Professor: Dr. Rao
 * 
 * Description:
 * This class represents an int type and encapsulates certain operations to make processing easier.
 */
package anglessw_hw1.data;

public class anglessw_hw1_int extends anglessw_hw1_base {
	private int data;
	
	public anglessw_hw1_int(Object obj) {
		super(obj);
		this.data = (int) obj;
	}
	
	public String toString() {
		return ""+data;
	}
	
	public boolean equals(Object obj) {
		int temp = Integer.parseInt((String) obj);
		return (this.data == temp);
	}
	
	public Object getData() {
		return this.data;
	}
	
	public String getType() {
		return "int";
	}
}
