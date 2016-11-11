package src;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Tempaycai on 11/6/16.
 */
public class PaymoID {
    private final Integer myId;
    private final HashSet<Integer> friends = new HashSet<>();

    public PaymoID(Integer myId) {
        this.myId = myId;
    }

    public void addFriend(Integer id) {
        friends.add(id);
    }

    public Integer getMyId() {
        return myId;
    }

    public Iterator<Integer> iteratorForFriends() {
        return friends.iterator();
    }

    public boolean hasFriend(Integer id) {
        return friends.contains(id);
    }
}
