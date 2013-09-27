/**
 * Author: Steven Angles
 * Class: CSE381
 * Professor: Dr. Rao
 * 
 * Description:
 * This class is a base class for the object data representation.
 */
package anglessw_hw1.data;

public class anglessw_hw1_base {
	private Object data = null;
	
	public anglessw_hw1_base(Object obj) {
		data = obj;
	}
	
	public String toString() {
		return ""+data;
	}
	
	public Object getData() {
		return data;
	}
	
	public String getType() {
		return "object";
	}
}
