package my.ogdeveloper.test.db.object;

import javax.annotation.Nullable;
import java.util.List;

public class DatabaseRequest {

    private List<DatabaseDate> databaseDates;

    public DatabaseRequest(List<DatabaseDate> databaseDates) {
        this.databaseDates = databaseDates;
    }

    public Object getObject(String key, @Nullable Object defaultObject) {
        for (DatabaseDate date: databaseDates) {
            if (date.getKey().equals(key))
                return date.getDate();
        }

        if (defaultObject == null)
            return null;
        else
            return defaultObject;
    }

    public String getString(String key, @Nullable String defaultValue) {
        return (String) getObject(key, defaultValue);
    }

    public int getInt(String key, @Nullable int defaultValue) {
        return (int) getObject(key, defaultValue);
    }

    public boolean getBoolean(String key, @Nullable boolean defaultValue) {
        return (boolean) getObject(key, defaultValue);
    }

}
