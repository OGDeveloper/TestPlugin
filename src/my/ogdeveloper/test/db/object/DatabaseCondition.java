package my.ogdeveloper.test.db.object;

import java.util.ArrayList;
import java.util.List;

public class DatabaseCondition {

    protected List<DatabaseDate> databaseDateList;

    public DatabaseCondition() {
        databaseDateList = new ArrayList<>();
    }

    public void addCondition(String key, Object obj) {
        databaseDateList.add(new DatabaseDate(key, obj));
    }

    public List<DatabaseDate> getDatabaseDateList() {
        return databaseDateList;
    }

}
