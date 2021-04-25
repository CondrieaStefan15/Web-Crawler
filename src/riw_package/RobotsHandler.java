package riw_package;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RobotsHandler {



    //functie care returneaza un RobotsInfo, obiecte ce contine listele cu path-urile permise si interzise preluate din robots.txt
    public static RobotsInfo getRobotsInfo(String robotsContent, String robotName){

        RobotsInfo robotsInfo = new RobotsInfo();
        List<String> permissions = robotsInfo.getPermissions();
        List<String> exclusions = robotsInfo.getExclusions();

        String[] userAgentsInfo = robotsContent.split("User-agent:");

        for(String userAgentInfo : userAgentsInfo){

            String[] linesPermission = userAgentInfo.split("\n");

            if(linesPermission[0].trim().equals(robotName)){
                for(int i = 1; i<linesPermission.length;++i){
                    if(linesPermission[i].startsWith("Disallow:")){
                        exclusions.add(linesPermission[i].substring(9).trim());
                    }else if(linesPermission[i].startsWith("Allow:")){
                        permissions.add(linesPermission[i].substring(6).trim());
                    }
                }
            }
        }

        if(permissions.size() == 0 && exclusions.size() == 0){  //inseamna ca nu exista user agent si verific User-agent: *
            for(String usersGeneralInfo: userAgentsInfo){
                String[] linesPermission = usersGeneralInfo.split("\n");

                if(linesPermission[0].trim().equals("*")){
                    for(int i = 1; i<linesPermission.length;++i){
                        if(linesPermission[i].startsWith("Disallow:")){
                            exclusions.add(linesPermission[i].substring(9).trim());
                        }else if(linesPermission[i].startsWith("Allow:")){
                            permissions.add(linesPermission[i].substring(6).trim());
                        }
                    }
                }
            }
        }

        return robotsInfo;
    }




    public static void main(String[] args){



    }
}
