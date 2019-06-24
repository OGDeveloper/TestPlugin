package my.ogdeveloper.test.db;

import my.ogdeveloper.test.db.object.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SQLiteDatabase implements Database {

    private Properties properties;
    private Connection cn;

    public SQLiteDatabase(Properties properties) {
        this.properties = properties;
    }

    private void connect() {
        try {
            if (cn != null && !cn.isClosed())
                return;

            Class.forName("org.sqlite.JDBC");
            cn = DriverManager.getConnection("jdbc:sqlite:" + properties.getProperty("fileName") + ".db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized DatabaseResult selectFromDatabase(String table, DatabaseCondition databaseCondition) {
        connect();

        StringBuilder sql = new StringBuilder("SELECT");

        sql.append(" ").append("*").append(" ").append("FROM").append(" ").append("`").append(table).append("`");

        if (databaseCondition != null && databaseCondition.getDatabaseDateList().size() > 0) {
            sql.append(" ").append("WHERE");

            for (DatabaseDate databaseDate: databaseCondition.getDatabaseDateList()) {
                if (databaseDate.getDate() instanceof String)
                    sql.append(" ").append("`").append(databaseDate.getKey()).append("`").append("=").append("'").append(databaseDate.getDate()).append("'");
                else
                    sql.append(" ").append("`").append(databaseDate.getKey()).append("`").append("=").append(databaseDate.getDate());
            }
        }

        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql.toString());

            List<DatabaseRequest> requests = new ArrayList<>();

            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                List<DatabaseDate> dates = new ArrayList<>();

                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    Object obj = resultSet.getObject(columnName);

                    dates.add(new DatabaseDate(columnName, obj));
                }

                requests.add(new DatabaseRequest(dates));
            }

            return new DatabaseResult(requests);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized boolean insertIfNotExists(String table, DatabaseUpdate databaseUpdate) {
        DatabaseCondition condition = new DatabaseCondition();

        if (databaseUpdate.getDatabaseDateList().size() > 0) {
            for (DatabaseDate databaseDate: databaseUpdate.getDatabaseDateList())
                condition.addCondition(databaseDate.getKey(), databaseDate.getDate());
        }

        DatabaseResult result = selectFromDatabase(table, condition);

        if (!result.hasNext()) {
            insertIntoDatabase(table, databaseUpdate);
            return true;
        }

        return false;
    }

    @Override
    public synchronized void insertIntoDatabase(String table, DatabaseUpdate databaseUpdate) {
        connect();

        StringBuilder sql = new StringBuilder("INSERT");
        StringBuilder sqlValues = new StringBuilder("INSERT");

        sql.append(" ").append("INTO").append(" ").append("`").append(table).append("`");


        List<DatabaseDate> updateDateList = databaseUpdate.getUpdateDateList();

        if (databaseUpdate.getUpdateDateList().size() > 0) {
            sql.append(" ").append(")");
            sqlValues.append(" ").append("(");

            for (int i = 0; i < updateDateList.size(); i++) {
                DatabaseDate date = updateDateList.get(i);

                if (i == updateDateList.size() - 1) {
                    sql.append("`").append(date.getKey()).append("`");

                    if (date.getDate() instanceof String)
                        sqlValues.append("'").append(date.getDate()).append("'");
                    else
                        sqlValues.append(date.getDate());
                    break;
                }

                sql.append("`").append(date.getKey()).append("`").append(" ").append(table).append(",");

                if (date.getDate() instanceof String)
                    sqlValues.append("'").append(date.getDate()).append("'").append(" ").append(table).append(",");
                else
                    sqlValues.append(date.getDate()).append(" ").append(table).append(",");
            }

            sql.append(")").append(" ");
            sqlValues.append(")");
            sql.append(" ").append("VALUES").append(sqlValues);;

            executeUpdate(sql.toString());
            close();
        }
    }

    @Override
    public void updateDatabase(String table, DatabaseUpdate databaseUpdate) {
        connect();

        StringBuilder sql = new StringBuilder("UPDATE");

        sql.append(" ").append("`").append(table).append("`");

        if (databaseUpdate.getUpdateDateList().size() > 0) {
            sql.append(" ").append("SET").append(" ");

            List<DatabaseDate> updateDateList = databaseUpdate.getUpdateDateList();

            for (int i = 0; i < updateDateList.size(); i++) {
                DatabaseDate date = updateDateList.get(i);

                sql.append("`").append(date.getKey()).append("`").append("=");

                if (date.getDate() instanceof String)
                    sql.append("'").append(date.getDate()).append("'");
                else
                    sql.append(date.getDate());

                if (i != updateDateList.size() - 1) {
                    sql.append(", ");
                    break;
                }
            }
        }

        if (databaseUpdate.getDatabaseDateList().size() > 0) {
            sql.append(" ").append("WHERE");

            for (DatabaseDate databaseDate: databaseUpdate.getDatabaseDateList()) {
                if (databaseDate.getDate() instanceof String)
                    sql.append(" `").append(databaseDate.getKey()).append("`").append("=").append("'").append(databaseDate.getDate()).append("'");
                else
                    sql.append(" `").append(databaseDate.getKey()).append("`").append("=").append(databaseDate.getDate());
            }
        }

        System.out.println(sql);

        executeUpdate(sql.toString());
        close();
    }


    @Override
    public synchronized void createTable(String tableName, DatabaseUpdate databaseUpdate) {
        connect();

        StringBuilder sql = new StringBuilder("CREATE");

        sql.append(" ").append("TABLE").append(" ").append("IF").append(" ").append("NOT").append(" ").append("EXISTS").append(" ").append("`").append(tableName).append("`");

        if (!databaseUpdate.getUpdateDateList().isEmpty()) {
            sql.append(" ").append("(");

            for (int i = 0; i < databaseUpdate.getUpdateDateList().size(); i++) {
                DatabaseDate date = databaseUpdate.getUpdateDateList().get(i);

                if (i == databaseUpdate.getUpdateDateList().size() - 1) {
                    sql.append("`").append(date.getKey()).append("`").append(" ").append(date.getDate());
                    break;
                }

                sql.append("`").append(date.getKey()).append("`").append(" ").append(date.getDate()).append(",").append(" ");
            }

            sql.append(")");
        }

        executeUpdate(sql.toString());
        close();
    }

    private void executeUpdate(String sql) {
        try {
            Statement statement = cn.createStatement();

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
