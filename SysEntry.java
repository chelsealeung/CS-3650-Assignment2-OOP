package sample;

// composite pattern
public interface SysEntry {

    public String getID();

    public void setID(String name);

    public String toString();

    public void accept(SysEntryVisitor visitor);

    public Long getCreationTime();

    public void setCreationTime();

}
