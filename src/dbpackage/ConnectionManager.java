package dbpackage;

import java.io.File;
import java.sql.*;

public class ConnectionManager {
    private String encoding;
    private String driver;
    private String username;
    private String password;
    private Connection conn;

    public String getEncoding() {
        return encoding;
    }
    public String getDriver() {
        return driver;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    ConnectionManager(String encoding, String drive, String username, String password) {
        this.encoding = encoding;
        this.driver = drive;
        this.username = username;
        this.password = password;
    }

    ConnectionManager (String xmlConfigFileName) {
        ConnectionManager cm = new XMLParser().getConfigFromXML(xmlConfigFileName);
        this.encoding = cm.getEncoding();
        this.driver = cm.getDriver();
        this.username = cm.getUsername();
        this.password = cm.getPassword();
    }

    private void createNewConnection () {
        try {
            Class.forName(driver);
            try {
                if (conn == null || conn.isClosed()) {
                    File file = new File("src/LIBRARY.FDB");
                    conn = DriverManager.getConnection("jdbc:firebirdsql:localhost:"
                            + file.getAbsolutePath() + encoding, username, password);
                }
            } catch (SQLException ex) {
                System.out.println("Попытка подключения к базе данных не удалась");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Драйвер не найден");
        }
    }

    Connection getConnection () {
        createNewConnection();
        return conn;
    }

    void doSQL (Connection conn, String strSQL, boolean print){
        try (Statement stmt = conn.createStatement())
        {
            if (print) {
                ResultSet rs = stmt.executeQuery(strSQL);
                printSQLResult(rs);
                rs.close();
            }
            else stmt.executeUpdate(strSQL);
        }
        catch(SQLException exc)
        {
            exc.printStackTrace();
        }
    }

    private void printSQLResult (ResultSet rs) {
        System.out.println();
        try {
            for (int n = 1; n < rs.getMetaData().getColumnCount() + 1; n++) {
                System.out.printf("%-20s", rs.getMetaData().getColumnName(n));
            }

            System.out.println();

            while (rs.next()) {
                for (int n = 1; n < rs.getMetaData().getColumnCount() + 1; n++) {
                    Object obj = rs.getObject(n);
                    if (obj != null) System.out.printf("%-20s", obj);
                }
                System.out.println();
            }
        }
        catch (SQLException exc) {
            exc.printStackTrace();
        }
        System.out.println();
    }
}
