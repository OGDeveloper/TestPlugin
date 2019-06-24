package my.ogdeveloper.test.db.object;

import java.util.List;

public class DatabaseResult {

    private List<DatabaseRequest> requestList;
    private int cursor;

    public DatabaseResult(List<DatabaseRequest> requestList) {
        this.requestList = requestList;
    }

    public boolean hasNext() {
        return (requestList.size()) - cursor > 0;
    }

    public DatabaseRequest getNext() {
        return requestList.get(cursor++);
    }

}
