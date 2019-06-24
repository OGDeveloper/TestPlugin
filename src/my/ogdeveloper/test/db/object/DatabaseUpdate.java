package my.ogdeveloper.test.db.object;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUpdate extends DatabaseCondition {

    private List<DatabaseDate> updateDateList;

    public DatabaseUpdate() {
        super();

        updateDateList = new ArrayList<>();
    }

    public <T> void addUpdate(String key, T obj) {
        updateDateList.add(new DatabaseDate(key, obj));
    }

    public List<DatabaseDate> getUpdateDateList() {
        return updateDateList;
    }

}
