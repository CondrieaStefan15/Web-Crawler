package riw_package;

import java.util.List;

public class Domain {
    private String IPAdress;
    private RobotsInfo robotsInfo;

    private boolean allowAll;
    private boolean disallowAll;



    public Domain() {

        robotsInfo = new RobotsInfo();
    }

    public String getIPAdress() {
        return IPAdress;
    }

    public void setIPAdress(String IPAdress) {
        this.IPAdress = IPAdress;
    }


    public RobotsInfo getRobotsInfo() {
        return robotsInfo;
    }

    public void setRobotsInfo(RobotsInfo robotsInfo) {
        this.robotsInfo = robotsInfo;

        //System.out.println("Permissions: " + robotsInfo.getPermissions().toString() + ", size = " + robotsInfo.getPermissions().size());
        //System.out.println("Exclusions:" + robotsInfo.getExclusions().toString() + ", size = " + robotsInfo.getExclusions().size());
        //ar trebui sa seteze allowAll sau disallowall

        List<String> exclusions = robotsInfo.getExclusions();

        if(exclusions.size() == 1){
            if(exclusions.get(0).equals("")){
                allowAll = true;
                disallowAll = false;
            }else if(exclusions.get(0).equals("/")){
                allowAll = false;
                disallowAll = true;
            }

        }
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


    public void display(){
        if(IPAdress != null)
            System.out.println("IP: " + IPAdress);
        else
            System.out.println("IP: ");

        System.out.println("allowAll: " + allowAll);
        System.out.println("disallowAll: " + disallowAll);

        for(String allow : robotsInfo.getPermissions()){
            System.out.println("\tAllow: " + allow);
        }

        for(String disallow : robotsInfo.getExclusions()){
            System.out.println("\tDisallow: " + disallow);
        }
    }


}
