package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// visitor pattern
public class PositivePercentage implements SysEntryVisitor{

    private double totalMessage = 0;
    private double totalPositive = 0;
    private double countPercentage = 0;
    private List<String> dict = new ArrayList<String>(Arrays.asList("awesome", "great", "nice", "thank", "good", "appreciate", "happy"));

    @Override
    public void visitUser(User user) {
        String[] arr = new String[dict.size()];
        arr = dict.toArray(arr);
        for (String tweet : user.getTweet()) {
            totalMessage += 1;
            if (stringContainsItemFromList(tweet, arr)) {
                totalPositive +=1;
                break;
            }
        }
    }

    private static boolean stringContainsItemFromList(String input, String[] items) {
        return Arrays.stream(items).anyMatch(input::contains);
    }

    @Override
    public void visitGroup(Group group) {
    }

    @Override
    public int getter() {
        countPercentage = (totalPositive/totalMessage)*100.0;
        setter((int)Math.rint(countPercentage));
        return (int)Math.rint(countPercentage);
    }

    @Override
    public void setter(int number) {
        this.countPercentage = number;
    }

}
