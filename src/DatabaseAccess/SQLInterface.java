package DatabaseAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Used to abstract the interface with the database, so that only a SQL
 * statement needs to be provided as input, and a ResultSet is provided as
 * output.
 * 
 * @author Ralph
 * 
 */
class SQLInterface {

	// For Local
	//private static final String URL = "jdbc:mysql://localhost/mysql";
	private static final String URL = "jdbc:mysql://fling.seas.upenn.edu/rben";
	
	private static final String USER = "rben";
	private static final String PASS = "cis330";
	
	//private static final String USER = "root";
	//private static final String PASS = "";

	private Connection conn = null;
	private Statement stmt = null;

	private static SQLInterface inst = null;

	private SQLInterface(String URL, String usr, String pwd) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, usr, pwd);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	static SQLInterface getSQLInterface() {
		if (inst == null) {
			inst = new SQLInterface(URL, USER, PASS);
		}
		return inst;
	}

	/**
	 * Executes the SQL command. Exits the program upon any SQLException.
	 * 
	 * @param sql
	 *            the SQL command to execute
	 * @return
	 */
	synchronized ResultSet getResultSafe(String sql) {
		ResultSet out = null;
		try {
			if (stmt.execute(sql)) {
				out = stmt.getResultSet();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return out;
	}

	/**
	 * Used if handling of SQLExceptions is to be defined outside the scope of
	 * the method.
	 * 
	 * @param sql
	 *            the SQL statement to be executed
	 * @return
	 * @throws SQLException
	 */
	synchronized ResultSet getResultUnsafe(String sql) throws SQLException {
		ResultSet out = null;
		if (stmt.execute(sql)) {
			out = stmt.getResultSet();
		}
		return out;
	}

	void close() {
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	protected void finalize() {
		close();
	}

}
