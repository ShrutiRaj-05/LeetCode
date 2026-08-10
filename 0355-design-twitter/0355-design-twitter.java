import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Twitter {

    // Helper class to represent a single Tweet as a node in a Linked List
    private static class Tweet {
        int id;
        int timestamp;
        Tweet next;

        Tweet(int id, int timestamp) {
            this.id = id;
            this.timestamp = timestamp;
            this.next = null;
        }
    }

    private static int globalTimestamp = 0;

    // Maps userId to set of users they follow
    private final Map<Integer, Set<Integer>> userToFollowees;

    // Maps userId to head of their Tweet linked list (most recent first)
    private final Map<Integer, Tweet> userToTweets;

    public Twitter() {
        userToFollowees = new HashMap<>();
        userToTweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        Tweet newTweet = new Tweet(tweetId, globalTimestamp++);
        
        // Prepend new tweet so the list stays ordered from newest to oldest
        newTweet.next = userToTweets.get(userId);
        userToTweets.put(userId, newTweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> feed = new ArrayList<>();

        // Get set of followees (including the user themselves)
        Set<Integer> followees = userToFollowees.getOrDefault(userId, new HashSet<>());
        
        // Max-Heap ordered by timestamp (newest tweet first)
        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(b.timestamp, a.timestamp)
        );

        // Add the head tweet of the user themselves
        if (userToTweets.containsKey(userId)) {
            maxHeap.offer(userToTweets.get(userId));
        }

        // Add the head tweet of each followee
        for (int followeeId : followees) {
            if (userToTweets.containsKey(followeeId)) {
                maxHeap.offer(userToTweets.get(followeeId));
            }
        }

        // Extract at most 10 most recent tweets across all followed feeds
        while (!maxHeap.isEmpty() && feed.size() < 10) {
            Tweet curr = maxHeap.poll();
            feed.add(curr.id);

            // Move pointer to the next older tweet in the same user's feed
            if (curr.next != null) {
                maxHeap.offer(curr.next);
            }
        }

        return feed;
    }

    public void follow(int followerId, int followeeId) {
        // A user cannot follow themselves
        if (followerId == followeeId) {
            return;
        }
        userToFollowees.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (userToFollowees.containsKey(followerId)) {
            userToFollowees.get(followerId).remove(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */