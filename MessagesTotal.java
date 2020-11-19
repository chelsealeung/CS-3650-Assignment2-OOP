package sample;

// visitor pattern
public class MessagesTotal implements SysEntryVisitor {

    private int totalMessage = 0;

    @Override
    public void visitUser(User user) {
        setter(getter() + user.getTweet().size());
    }

    @Override
    public void visitGroup(Group group) {
    }

    @Override
    public int getter() {
        return totalMessage;
    }

    @Override
    public void setter(int number) {
        this.totalMessage = number;
    }

}
