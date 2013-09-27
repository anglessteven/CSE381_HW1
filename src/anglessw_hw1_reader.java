/**
 * anglessw_hw1_reader.java
 * Author: Steven Angles
 * Class: CSE381
 * Professor: Dr. Rao
 * 
 * Description:
 * This class manages all file I/O for hw1.
 */
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import anglessw_hw1.data.*;

public class anglessw_hw1_reader {
	private ArrayList<String> schemaList = null;
	private anglessw_hw1_base[][] binaryData = null;
	private int recordSize = -1;

	private static final String CHAR = "char";
	private static final String INT = "int";
	private static final String DOUBLE = "double";

	private static final int CHAR_SIZE = 2;
	private static final int INT_SIZE = 4;
	private static final int DOUBLE_SIZE = 8;

	/**
	 * Reads the schema from a provided schema file and
	 * stores it in an ArrayList.
	 * @param fileName
	 * 				The name of the schema file.
	 */
	public void openAndReadSchema(final String fileName) throws IOException {
		schemaList = new ArrayList<String>();
		String curLine = null;
		FileInputStream fis = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		while ((curLine = br.readLine()) != null) {
			schemaList.add(curLine);
		}

		calculateSize();
		
		fis.close();
		br.close();
	}

	/**
	 * Reads the data from a provided binary file and stores
	 * it in an array for later use.
	 * @param fileName
	 * 				The name of the binary file
	 */
	public void openAndReadBinary(final String fileName) throws IOException {
		File file = new File(fileName);
		int numRecords = (int) (file.length() / recordSize);
		binaryData = new anglessw_hw1_base[numRecords][schemaList.size()];
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		
		for (int i=0; i<numRecords; i++) {
			for (int j=0; j<schemaList.size(); j++) {
				switch (schemaList.get(j)) {
				case CHAR:
					binaryData[i][j] = new anglessw_hw1_char(dis.readChar());
					break;
				case INT:
					binaryData[i][j] = new anglessw_hw1_int(dis.readInt());
					break;
				case DOUBLE:
					binaryData[i][j] = new anglessw_hw1_double(dis.readDouble());
					break;
				}
			}
		}
		dis.close();
	}

	/**
	 * Gets the binary data from a search query on a binary file.
	 * @param query
	 * 				The string to query.
	 * @return
	 * 				A byte array containing the search data in binary format.
	 */
	public byte[] getBinarySearch(final String query) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			for (int i=0; i<binaryData.length; i++) {
				if (binaryData[i][0].equals(query)) {
					for (anglessw_hw1_base obj : binaryData[i]) {
						switch (obj.getType()) {
						case CHAR:
							dos.writeChar((char) obj.getData());
							break;
						case INT:
							dos.writeInt((int) obj.getData());
							break;
						case DOUBLE:
							dos.writeDouble((double) obj.getData());
							break;	
						}
					}
				}
			}
			dos.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return baos.toByteArray();
	}

	/**
	 * Calculates the size of a single record from the
	 * schema file.
	 */
	private void calculateSize() {
		recordSize = 0;

		for (String s : schemaList) {
			switch (s) {
			case CHAR:
				recordSize += CHAR_SIZE;
				break;
			case INT:
				recordSize += INT_SIZE;
				break;
			case DOUBLE:
				recordSize += DOUBLE_SIZE;
				break;
			}
		}
	}

	/**
	 * Gets a string representation of the contents
	 * of the schema file.
	 * @return
	 * 			The string representation of the contents
	 * 			of the schema file.
	 */
	public String getSchemaContents() {
		String out = "";
		for (String s : schemaList) {
			out += '\t'+s+'\n';
		}

		return out;
	}

	/**
	 * Gets a string representation of the contents of
	 * the binary file.
	 * @return
	 * 			The string representation of the contents
	 * 			of the binary file.
	 */
	public String getBinaryContents() {
		String out = "";
		for (int i=0; i<binaryData.length; i++) {
			out += formulateRecord(i);
			out += "\n";
		}

		return out;
	}

	/**
	 * Creates a string representation of a single record.
	 * @param index
	 * 				The index of the record in the binaryData array 
	 * @return
	 * 				A string representing a single record from the
	 * 				provided index.
	 */
	private String formulateRecord(final int index) {
		String record = "";
		Object[] arrS = binaryData[index];

		for (int i=0; i<arrS.length; i++) {
			record += arrS[i];
			if (i != arrS.length-1) {
				record += ", ";
			}
		}

		return record;
	}

	/**
	 * Returns all records that match a search query.
	 * @param query
	 * 				The search query.
	 * @return
	 * 			A string representing all the records
	 * 			that matched the search.
	 */
	public String getSearchResults(final String query) {
		String results = "";

		for (int i=0; i<binaryData.length; i++) {
			if (binaryData[i][0].equals(query)) {
				results += formulateRecord(i);
				results += "\n";
			}
		}

		return results;
	}

	/**
	 * Get the type of the record that is searchable for this
	 * schema.
	 * @return
	 * 			The searchable type.
	 */
	public String getSearchableType() {
		return schemaList.get(0);
	}
}
