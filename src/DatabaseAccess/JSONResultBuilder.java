package DatabaseAccess;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;


class JSONResultBuilder{
	/**Converts a result set object to JSON to be returned to the caller.
	 * @param rs the result set to be converted
	 * @return JSON formatted result set, or null if an error occurs
	 */
	static LinkedList<String> generateJSONFormat(ResultSet rs){
		try{
			ResultSetMetaData rsmd = rs.getMetaData();
			String[] colNames = new String[rsmd.getColumnCount()];
			for(int i = 0; i < colNames.length; i++){
				colNames[i] = rsmd.getColumnName(i + 1);		// Add 1 because SQL is 1 indexed
			}

			LinkedList<String> out = new LinkedList<String>();
			while(rs.next()){
				JSONFormatBuilder json = new JSONFormatBuilder();
				for(int i = 0; i < rsmd.getColumnCount(); i++){
					addJSONField(i, rs, rsmd, json, colNames);
				}
				out.add(json.toString());
			}

			return out;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}

	}


	/**Figures out the type of the column and correctly adds the columns value to the
	 * json string (using the correct type). A helper method to generateJSONFormat().
	 * @param col the column number to add
	 * @param rs the result set
	 * @param rsmd the result set's metadata
	 * @param json the JSONBuilder object to add the data to
	 * @throws SQLException 
	 */
	private static void addJSONField(int col, ResultSet rs, ResultSetMetaData rsmd, 
			JSONFormatBuilder json, String[] colNames) throws SQLException{

		String colTypeName = rsmd.getColumnTypeName(col + 1).toLowerCase();

		if(colTypeName.contains("varchar")){
			String val = rs.getString(col + 1);		// SQL is 1 indexed
			json.appendStringField(colNames[col], val);
		}
		else if(colTypeName.contains("int")){
			int val = rs.getInt(col + 1);
			json.appendIntegerField(colNames[col], val);
		}
		else if(colTypeName.contains("double")){
			float val = rs.getFloat(col + 1);
			json.appendFloatField(colNames[col], val);
		}
	}

	/**A holder object for JSON strings returned by queries. Has methods to help add fields to the
	 * object.
	 * @author Ralph
	 *
	 */
	private static class JSONFormatBuilder {

		private StringBuffer buf;

		private boolean first;


		JSONFormatBuilder(){
			buf = new StringBuffer();
			buf.append("{");
			first = true;
		}

		void appendStringField(String name, String val){
			if(!first){
				buf.append(", ");
			}
			buf.append("\"" + name + "\": " + "\"" + val + "\"");
			first = false;
		}

		void appendIntegerField(String name, int val){
			if(!first){
				buf.append(", ");
			}
			buf.append("\"" + name + "\": " + val);
			first = false;
		}

		void appendFloatField(String name, double val){
			if(!first){
				buf.append(", ");
			}
			buf.append("\"" + name + "\": " + val);
			first = false;
		}

		public String toString(){
			return buf.toString() + "}";
		}

	}
}
