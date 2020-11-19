package sample;

// visitor pattern
public class UserTotal implements SysEntryVisitor {

   private int totalUser = 0;

    @Override
    public void visitUser(User user) {
        setter(getter() + 1);
    }

    @Override
    public void visitGroup(Group group) {
    }

    @Override
    public int getter() {
        return totalUser;
    }

    @Override
    public void setter(int number) {
        this.totalUser = number;
    }

}
