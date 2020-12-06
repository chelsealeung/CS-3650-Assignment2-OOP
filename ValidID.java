package sample;

import java.util.ArrayList;

public class ValidID implements SysEntryVisitor {

    private ArrayList<String> totalIDList = new ArrayList();
    private boolean valid = true;

    @Override
    public void visitUser(User user) {
        if (totalIDList.contains(user.getID())) {
            valid = false;
        }
        if (user.getID().contains(" ")) {
            valid = false;
        }
        totalIDList.add(user.getID());
    }

    @Override
    public void visitGroup(Group group) {
        if (totalIDList.contains(group.getID())) {
            valid = false;
        }
        if (group.getID().contains(" ")) {
            valid = false;
        }
        totalIDList.add(group.getID());
    }

    @Override
    public int getter() {
        return 0;
    }

    @Override
    public void setter(int number) {
    }

    public boolean getValid() {
        return valid;
    }

}
