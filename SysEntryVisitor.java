package sample;

// visitor pattern
public interface SysEntryVisitor {

    public void visitUser(User user);

    public void visitGroup(Group group);

    public int getter();

    public void setter(int number);

}
