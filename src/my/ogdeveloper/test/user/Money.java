package my.ogdeveloper.test.user;

import my.ogdeveloper.test.db.object.DatabaseCondition;
import my.ogdeveloper.test.db.object.DatabaseUpdate;

import java.util.UUID;

public class Money {

    private Storage storage;
    private UUID uuid;

    public Money(Storage storage, UUID uuid) {
        this.storage = storage;
        this.uuid = uuid;
    }

    public int getMoney() {
        DatabaseCondition condition = new DatabaseCondition();
        condition.addCondition("UUID", uuid.toString());

        return (int) storage.getObject("user", condition, "Money", 0);
    }

    public void setMoney(int money) {
        DatabaseUpdate update = new DatabaseUpdate();
        update.addUpdate("Money", money);
        update.addCondition("UUID", uuid.toString());

        storage.save("user", update);
    }

}
