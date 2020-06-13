/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        Instant timeStampMin = Instant.MAX;
        Instant timeStampMax = Instant.MIN;
        for (Tweet t : tweets) {
            if (t.getTimestamp().compareTo(timeStampMax) > 0)
                timeStampMax = t.getTimestamp();
            if (t.getTimestamp().compareTo(timeStampMin) < 0)
                timeStampMin = t.getTimestamp();
        }
        Timespan ts = new Timespan(timeStampMin, timeStampMax);
        return ts;
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        // this set contains all character that is valid for a username
        Set<Character> validSet = new HashSet<>();
        validSet.add('_');
        validSet.add('-');
        for (int i = 0; i <= 9; i++) {
            validSet.add((char) i);
        }
        String letters = "abcdefghijklmnopqrstuvwxyz" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (char letter : letters.toCharArray()) {
            validSet.add(letter);
        }

        Set<String> set = new HashSet<>();
        for (Tweet t : tweets) {
            String text = t.getText();
            int index = 0;
            index = text.indexOf("@", 0);
            while (index != -1) {
                if (index > 0) {
                    char prev = text.charAt(index - 1);
                    if (validSet.contains(prev))
                        break;
                }
                char after = text.charAt(index + 1);
                if (!validSet.contains(after)) {
                    String username = "";
                    for (int i = index + 2; validSet.contains(text.charAt(i)); i++)
                        username += String.valueOf(text.charAt(i));
                    set.add(username);
                }
                index = text.indexOf("@", index + 1);
            }
        }
        return set;
    }

}
