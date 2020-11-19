package sample;

// visitor pattern
public class GroupTotal implements SysEntryVisitor {

    private int totalGroup = 0;

    @Override
    public void visitUser(User user) {
    }

    @Override
    public void visitGroup(Group group) {
        setter(getter() + 1);
    }

    @Override
    public int getter() {
        return totalGroup;
    }

    @Override
    public void setter(int number) {
        this.totalGroup = number;
    }

}
