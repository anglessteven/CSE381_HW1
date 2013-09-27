/**
 * Author: Steven Angles
 * Class: CSE381
 * Professor: Dr. Rao
 * 
 * Description:
 * This class represents a char type and encapsulates certain operations to make processing easier.
 */
package anglessw_hw1.data;

public class anglessw_hw1_char extends anglessw_hw1_base {
	private char data;
	
	public anglessw_hw1_char(Object obj) {
		super(obj);
		this.data = (char) obj;
	}
	
	public String toString() {
		return ("\'"+data+"\'");
	}
	
	public boolean equals(Object obj) {
		String temp = (String) obj;
		return (this.data == temp.charAt(0));
	}
	
	public Object getData() {
		return data;
	}
	
	public String getType() {
		return "char";
	}
}
