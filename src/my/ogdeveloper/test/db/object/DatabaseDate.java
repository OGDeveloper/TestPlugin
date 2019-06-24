package my.ogdeveloper.test.db.object;

public class DatabaseDate {

    private String key;
    private Object date;

    public DatabaseDate(String key, Object date) {
        this.key = key;
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public Object getDate() {
        return date;
    }

    public String getString() {
        return (String) date;
    }

    public int getInt() {
        return (int) date;
    }

    public boolean getBoolean() {
        return (boolean) date;
    }

}
