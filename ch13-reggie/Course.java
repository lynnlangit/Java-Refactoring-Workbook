import java.sql.*;

public class Course {
	private String name;
	private int credits;
	static String url = "jdbc:mysql://localhost:8889/Reggie";		// MySql
	static String username = "root";
	static String password = "root";


	static {
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();	// MySQL
		} catch (Exception ignored) {
		}
	}

	public static Course create(String name, int credits) throws Exception {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM course WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course VALUES ('" + name + "', '" + credits
					+ "');");
			return new Course(name, credits);
		} finally {
			try {
				conn.close();
			} catch (Exception ignored) {
			}
		}
	}

	public static Course find(String name) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM course WHERE name = '" + name
					+ "';");
			if (!result.next())
				return null;

			int credits = result.getInt("Credits");
			return new Course(name, credits);
		} catch (Exception ex) {
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ignored) {
			}
		}
	}

	public void update() throws Exception {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);
			Statement statement = conn.createStatement();

			statement.executeUpdate("DELETE FROM course WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course VALUES('" + name + "','" + credits + "');");
		} finally {
			try {
				conn.close();
			} catch (Exception ignored) {
			}
		}
	}

	Course(String name, int credits) {
		this.name = name;
		this.credits = credits;
	}

	public int getCredits() {
		return credits;
	}

	public String getName() {
		return name;
	}
}
