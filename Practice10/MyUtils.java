import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyUtils {
	private Connection connection;
	private Statement statement;
	private String schemaName;

    public Connection createConnection() throws SQLException {
    	DriverManager.registerDriver(new org.h2.Driver());
		connection = DriverManager.getConnection("jdbc:h2:mem:test", "", "");
		return connection;
    }
    public void closeConnection() throws SQLException {
    	connection.close();
    }
    public Statement createStatement() throws SQLException {
    	statement = connection.createStatement();
		return statement;
    }
    public void closeStatement() throws SQLException {
    	statement.close();
    }
    public void createSchema(String schemaName) throws SQLException {
    	this.schemaName = schemaName;
        statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + this.schemaName + ";");
    }
    public void dropSchema() throws SQLException {
    	statement.executeUpdate("DROP SCHEMA IF EXISTS " + this.schemaName + ";");
    }
    public void useSchema() throws SQLException {
    	statement.executeUpdate("SET SCHEMA " + this.schemaName + ";");
    }
    public void createTableRoles() throws SQLException {
    	statement.executeUpdate("CREATE TABLE IF NOT EXISTS Roles (id INT primary key auto_increment , roleName TEXT)");
    }
    public void createTableDirections() throws SQLException {
    	statement.executeUpdate("CREATE TABLE IF NOT EXISTS Directions (id INT primary key auto_increment , directionName TEXT)");
    }
    public void createTableProjects() throws SQLException {
    	statement.executeUpdate("CREATE TABLE IF NOT EXISTS Projects (id INT primary key auto_increment , projectName TEXT ,directionId INT, FOREIGN KEY(directionId) REFERENCES Directions(id))");
    }
    public void createTableEmployee() throws SQLException {
    	statement.executeUpdate("CREATE TABLE IF NOT EXISTS Employee (id INT primary key auto_increment , projectName TEXT ,roleId INT, FOREIGN KEY(roleId) REFERENCES Roles(id),projectId INT, FOREIGN KEY(projectId) REFERENCES Projects(id))");
    }
    public void dropTable(String tableName) throws SQLException {
    	statement.executeUpdate("DROP TABLE "+tableName);
    }
    public void insertTableRoles(String roleName) throws SQLException {
    	statement.executeUpdate("INSERT INTO Roles (roleName) VALUES('"+roleName+"');");
    }
    public void insertTableDirections(String directionName) throws SQLException {
    	statement.executeUpdate("INSERT INTO Directions (directionName) VALUES('"+directionName+"');");
    }
    public void insertTableProjects(String projectName, String directionName) throws SQLException {
    	statement.executeUpdate("INSERT INTO Projects (projectName,directionID) VALUES ('"+projectName+"',"+getDirectionId(directionName)+");");
    }
    public void insertTableEmployee(String firstName, String roleName, String projectName) throws SQLException {
    	statement.executeUpdate("INSERT INTO Employee (firstName,roleId,projectId) VALUES('"+firstName+"','"+getRoleId(roleName)+"','"+getProjectId(projectName)+");");
    }
    public int getRoleId(String roleName) throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT id FROM Roles WHERE roleName = \'"+roleName+"\'");
    	resultSet.next();
    	return resultSet.getInt(1);
    }
    public int getDirectionId(String directionName) throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT id FROM Directions WHERE directionName = \'"+directionName+"\'");
    	resultSet.next();
    	return resultSet.getInt(1);
    }
    public int getProjectId(String projectName) throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT id FROM Projects WHERE projectName = \'"+projectName+"\'");
    	resultSet.next();
    	return resultSet.getInt(1);
    }
    public int getEmployeeId(String firstName) throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT id FROM Employee WHERE firstName = \'"+firstName+"\'");
    	resultSet.next();
    	return resultSet.getInt(1);
    }
    public List<String> getAllRoles() throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT roleName FROM Roles");
    	List<String> roles = new ArrayList<>();
    	while (resultSet.next())
    		roles.add(resultSet.getString("roleName"));
		return roles;
    }
    public List<String> getAllDirestion() throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT directionName FROM Directions");
    	List<String> roles = new ArrayList<>();
    	while (resultSet.next())
    		roles.add(resultSet.getString("directionName"));
		return roles;
    }
    public List<String> getAllProjects() throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT projectName FROM Projects");
    	List<String> roles = new ArrayList<>();
    	while (resultSet.next())
    		roles.add(resultSet.getString("projectName"));
		return roles;
    }
    public List<String> getAllEmployee() throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT firstName FROM Employee");
    	List<String> roles = new ArrayList<>();
    	while (resultSet.next())
    		roles.add(resultSet.getString("firstName"));
		return roles;
    }
    public List<String> getAllDevelopers() throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT firstName FROM Employee WHERE roleId = 1");
    	List<String> roles = new ArrayList<>();
    	while (resultSet.next())
    		roles.add(resultSet.getString("firstName"));
		return roles;
    }
    public List<String> getAllJavaProjects() throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT projectName FROM Projects WHERE directionId = 1");
    	List<String> roles = new ArrayList<>();
    	while (resultSet.next())
    		roles.add(resultSet.getString("projectName"));
		return roles;
    }
    public List<String> getAllJavaDevelopers() throws SQLException {
    	ResultSet resultSet = statement.executeQuery("SELECT firstName FROM Employee WHERE roleId = 1 AND (projectId = 1 OR projectId = 2)");
    	List<String> roles = new ArrayList<>();
    	while (resultSet.next())
    		roles.add(resultSet.getString("firstName"));
		return roles;
    }

}