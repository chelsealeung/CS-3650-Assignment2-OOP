package sample;

public class LatestUpdate implements SysEntryVisitor {

    private String currentLatestUser = "";
    private long currentLatestTime = 0;

    @Override
    public void visitUser(User user) {
        if (user.getLastUpdateTime() > currentLatestTime) {
            currentLatestTime = user.getLastUpdateTime();
            currentLatestUser = user.getID();
        }
    }

    @Override
    public void visitGroup(Group group) {
    }

    @Override
    public int getter() {
        return 0;
    }

    @Override
    public void setter(int number) {
    }

    public String getCurrentLatestUser() {
        return currentLatestUser;
    }

}
