package my.ogdeveloper.test.user;

import java.util.UUID;

public class SimpleUser implements User {

    private UUID uuid;
    private Storage storage;

    public SimpleUser(UUID uuid, Storage storage) {
        this.uuid = uuid;
        this.storage = storage;
    }

    @Override
    public Money getMoney() {
        return new Money(storage, uuid);
    }

}
