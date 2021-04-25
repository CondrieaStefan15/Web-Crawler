package riw_package;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinkHandler {

    //functie care verifica daca o pagina de la link-ul path are permisiuni de a fi explorata pe domeniu
    public static boolean havePermission(String path, Domain domain){

        if(domain.isAllowAll()) {
            return true;
        }else if(domain.isDisallowAll()) {
            return false;
        }else{
            if(containsPath(path, domain.getRobotsInfo().getExclusions())) {
                return false;
            }
        }
        return true;
    }


    private static boolean containsPath(String path, List<String> paths){

        for(String pFromPaths : paths){
            if(pFromPaths.equals(path) || (path.startsWith(pFromPaths) && pFromPaths.endsWith("/"))){
                return true;
            }
        }
        return false;
    }



    //returneaza link-urile absolute din pagina
    //fara duplicate
    public static Set<String> getUtilLinksFromPage(Document pageDocument){


        Elements links = pageDocument.select("a");

        Set<String> linkList = new HashSet<>();

        for(Element link : links){

            String linkString = link.absUrl("href");
            if(!linkString.equals("")){
                if(linkString.contains("#")){
                    linkString = linkString.split("#")[0];
                }

                if(!linkList.contains(linkString)){
                    linkList.add(linkString);
                }
            }
        }

        return linkList;
    }
}
