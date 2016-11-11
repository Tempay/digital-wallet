package src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;

/**
 * Created by Tempaycai on 11/6/16.
 */
public class Users {

    // exisIds stores all ids that have made at least one transaction.
    private final HashMap<Integer, PaymoID> existIds;

    public Users() {
        this.existIds = new HashMap<>();
    }

    // add a new user id to the existIds
    public void add(Integer id) {
        existIds.put(id, new PaymoID(id));
    }

    // check if id exists
    public boolean hasId(Integer id) {
        return existIds.containsKey(id);
    }

    // return the value of this id. Here the value is a PaymoID object
    public PaymoID getId(Integer id) {
        return existIds.get(id);
    }

    /**
     * addTransaction is called when a new line of transaction is read
     * addTransaction add both of users in the transaction to other's first
     * generation friend and add each other's first generation friends as
     * it's second generation friends.
     * @param id1 user 1 in the transaction
     * @param id2 user 2 int the transaction
     */
    public void addTransaction(Integer id1, Integer id2) {
        // check if id exits, if not, create it
        if(!hasId(id1)) add(id1);
        if(!hasId(id2)) add(id2);

        PaymoID user1 = existIds.get(id1);
        PaymoID user2 = existIds.get(id2);

        // add each other into it's friends set
        user1.addFriend(id2);
        user2.addFriend(id1);
    }

    public boolean areFriends(Integer id1, Integer id2) {
        // if the existIds do not have any of these ids, return false
        if(!hasId(id1) || !hasId(id2)) return false;

        PaymoID user1 = existIds.get(id1);
        PaymoID user2 = existIds.get(id2);

        // if users are friends return true;
        if(user1.hasFriend(id2)) return true;

        return false;	
    }

    public boolean areSecondGenerationFriends(Integer id1, Integer id2) {
        // if the existIds do not have any of these ids, return false
        if(!hasId(id1) || !hasId(id2)) return false;

        if(areFriends(id1,id2)) return true;

        PaymoID user1 = existIds.get(id1);
        PaymoID user2 = existIds.get(id2);

        // if they are second generation friends, i.e., they have common friend(s), return true;
        Iterator<Integer> friendsOfUser1 = user1.iteratorForFriends();

        while(friendsOfUser1.hasNext()) {
            Integer next = friendsOfUser1.next();
            if(user2.hasFriend(next)) return true;
        }

        return false;

    }

    public boolean areFourthGenerationFriends(Integer id1, Integer id2) {
        // if the existIds do not have any of these ids, return false
        if(!hasId(id1) || !hasId(id2)) return false;

        // if users are friends, return true
        if(areFriends(id1, id2)) return true;

        // if users are second generation friends, return true;
        if(areSecondGenerationFriends(id1, id2)) return true;

        PaymoID user1 = existIds.get(id1);
        PaymoID user2 = existIds.get(id2);

        // if users' second generation friends sets has common ids, they are 4th generation friends, return true;
        HashSet<Integer> fourthGenerationFriendsOfUser1 = secondGenerationFriends(user1);
        HashSet<Integer> fourthGenerationFriendsOfUser2 = secondGenerationFriends(user2);

        for(Integer friend : fourthGenerationFriendsOfUser1) {
            if(fourthGenerationFriendsOfUser2.contains(friend)) return true;
        }
        return false;
    }

    private HashSet<Integer> secondGenerationFriends(PaymoID user) {
        // a set stores all second generation friends
        HashSet<Integer> secondGenerationFriends = new HashSet<>();

        Iterator<Integer> friends = user.iteratorForFriends();

        while(friends.hasNext()) {
            Integer next = friends.next();

            // add this friend's id into the second generation friends set
            secondGenerationFriends.add(next);

            // iterate all friends in friend's friend set, i.e., 2nd generation friends and add into the set
            Iterator<Integer> friendsOfFriend = existIds.get(next).iteratorForFriends();

            while(friendsOfFriend.hasNext()) {
                Integer friendsNext = friendsOfFriend.next();
                secondGenerationFriends.add(friendsNext);
            }
        }
        return secondGenerationFriends;
    }

}
