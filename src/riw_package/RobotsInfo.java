package riw_package;

import java.util.ArrayList;
import java.util.List;

//obiect ce contine listele cu pemisiunile din robots.txt
public class RobotsInfo {
    private List<String> permissions;
    private List<String> exclusions;
    private boolean allowAll;
    private boolean disallowAll;

    public RobotsInfo() {
        permissions = new ArrayList<>();
        exclusions = new ArrayList<>();

        allowAll = true;
        disallowAll = false;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<String> getExclusions() {
        return exclusions;
    }

    public boolean isAllowAll() {
        return allowAll;
    }

    public void setAllowAll(boolean allowAll) {
        this.allowAll = allowAll;
    }

    public boolean isDisallowAll() {
        return disallowAll;
    }

    public void setDisallowAll(boolean disallowAll) {
        this.disallowAll = disallowAll;
    }
}
