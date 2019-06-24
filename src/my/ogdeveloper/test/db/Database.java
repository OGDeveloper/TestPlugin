package my.ogdeveloper.test.db;

import my.ogdeveloper.test.db.object.DatabaseCondition;
import my.ogdeveloper.test.db.object.DatabaseResult;
import my.ogdeveloper.test.db.object.DatabaseUpdate;

public interface Database {

    DatabaseResult selectFromDatabase(String table, DatabaseCondition databaseCondition);

    boolean insertIfNotExists(String table, DatabaseUpdate databaseUpdate);

    void insertIntoDatabase(String table, DatabaseUpdate databaseUpdate);

    void updateDatabase(String table, DatabaseUpdate databaseUpdate);

    void createTable(String tableName, DatabaseUpdate databaseUpdate);

}
