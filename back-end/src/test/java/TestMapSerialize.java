
import datatypes.ourUser;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static server.Server.deserializeHashMap;
import static server.Server.serializeHashMap;

public class TestMapSerialize {

    @Test
    public void testSerializeAndDeserializeMap() {

        ourUser user1 = new ourUser("user1", "password");
        ourUser user2 = new ourUser("user2", "password");
        HashMap<String, ourUser> users = new HashMap<>();
        users.put("user1", user1);
        users.put("user2", user2);
        serializeHashMap(users,"data/test_users.ser");
        Map<String, ourUser> newUsers = deserializeHashMap("data/test_users.ser");
        assertEquals(newUsers.size(), users.size());
        assertEquals(users.get("user1").getTopGenre(0), newUsers.get("user1").getTopGenre(0));
    }
}
