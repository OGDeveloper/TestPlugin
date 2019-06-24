package my.ogdeveloper.test.user;

import my.ogdeveloper.test.db.object.DatabaseCondition;
import my.ogdeveloper.test.db.object.DatabaseResult;
import my.ogdeveloper.test.db.object.DatabaseUpdate;

public interface Storage {

    void save(String table, DatabaseUpdate update);

    DatabaseResult load(String table, DatabaseCondition condition);

    Object getObject(String table, DatabaseCondition condition, String row, Object defaultValue);

}
