package my.ogdeveloper.test.user;

import my.ogdeveloper.test.db.SQLiteDatabase;
import my.ogdeveloper.test.db.object.DatabaseCondition;
import my.ogdeveloper.test.db.object.DatabaseResult;
import my.ogdeveloper.test.db.object.DatabaseUpdate;

public class SQLiteStorage implements Storage {

    private SQLiteDatabase database;

    public SQLiteStorage(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void save(String table, DatabaseUpdate update) {
        database.updateDatabase(table, update);
    }

    @Override
    public DatabaseResult load(String table, DatabaseCondition condition) {
        return database.selectFromDatabase(table, condition);
    }

    @Override
    public Object getObject(String table, DatabaseCondition condition, String row, Object defaultValue) {
        DatabaseResult result = database.selectFromDatabase(table, condition);

        if (result.hasNext())
            return result.getNext().getObject(row, defaultValue);

        return null;
    }

}
