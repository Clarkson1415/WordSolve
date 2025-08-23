package com.example.wordsolve;

import java.sql.*;

public class DatabaseConnection
{
    public DatabaseConnection(){};

    String dbUrl = "jdbc:sqlite:" + getClass().getResource("/com/example/wordsolve/dictionary.db").getPath();

    public void testConnection() throws SQLException
    {
        this.CheckWordExists("c");
        this.CheckWordExists("hello");

        this.GetWordDefinition("c");

        // gets collumn names of the database
//        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM entries LIMIT 1");
//        ResultSet rs = stmt.executeQuery();
//        int columnCount = rs.getMetaData().getColumnCount();
//        for (int i = 1; i <= columnCount; i++) {
//            System.out.println("Column " + i + ": " + rs.getMetaData().getColumnName(i));
//        }
    }

    public String GetWordDefinition(String wordToFind) throws SQLException {
        var conn = this.openConnection();

        PreparedStatement query = conn.prepareStatement("SELECT word, wordtype, definition FROM entries WHERE word =" +
                " ? LIMIT 1");

        query.setString(1, wordToFind);
        ResultSet result = query.executeQuery();

        var definition = "";
        var type = "";

        if (result.next())
        {
            type = result.getString("wordtype");
            definition = result.getString("definition");
        } else {
            System.out.println("Word not found");
        }

        System.out.printf("Word: %s%nType: %s%nDefinition: %s%n",
                wordToFind,
                type,
                definition);

        return definition;
    }

    private Connection openConnection()
    {
        Connection conn = null;

        try
        {
            conn = DriverManager.getConnection(dbUrl);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return conn;
    }

    public boolean CheckWordExists(String wordToLookFor) throws SQLException {
        var conn = this.openConnection();
        PreparedStatement doesWordExistQuery = conn.prepareStatement("SELECT 1 FROM entries WHERE word = ? LIMIT 1");
        doesWordExistQuery.setString(1, wordToLookFor);
        ResultSet doesWordExistResult = doesWordExistQuery.executeQuery();
        boolean exists = doesWordExistResult.next();
        System.out.printf("does word %s exist? = %b \n", wordToLookFor, exists);
        conn.close();
        return exists;
    }
}