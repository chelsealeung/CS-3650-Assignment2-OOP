package sample;

import java.util.ArrayList;
import java.util.List;

// composite pattern
public class Group implements SysEntry {

    private String groupID;
    private List<SysEntry> sysEntries = new ArrayList<>();

    @Override
    public String getID() {
        return this.groupID;
    }

    @Override
    public void setID(String name) {
        this.groupID = name;
    }

    @Override
    public String toString() {  // hashcode to value
        return this.groupID;
    }   // hashcode to value

    // Observer
    @Override
    public void accept(SysEntryVisitor visitor) {
        visitor.visitGroup(this);
        for (SysEntry entry : getSysEntries()) {
            entry.accept(visitor);
        }
    }

    public List<SysEntry> getSysEntries() {
        return sysEntries;
    }

    public void setSysEntries(SysEntry entry) {
        this.sysEntries.add(entry);
    }

    // check if already contain this user
    public boolean containsUserID(String name) {
        for (SysEntry entry : getSysEntries()) {
            if (entry instanceof User) {
                if (entry.getID().equals(name)) {
                    return true;
                }
            }
            else if (entry instanceof Group) {
                if (((Group)entry).containsUserID(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    // check if already contain this group
    public boolean containsGroupID(String name) {
        for (SysEntry entry : getSysEntries()) {
            if (entry instanceof Group) {
                if (entry.getID().equals(name)) {
                    return true;
                }
                else {
                    if(((Group)entry).containsGroupID(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // return assigned user
    public User returnGroupItem(String name) {
        for (SysEntry entry : getSysEntries()) {
            if (entry instanceof User) {
                if (entry.getID().equals(name)) {
                    return (User)entry;
                }
            }
            if (entry instanceof Group) {
                if (((Group)entry).containsUserID(name)) {
                    return ((Group)entry).returnGroupItem(name);
                }
            }
        }
        return null;
    }

}
