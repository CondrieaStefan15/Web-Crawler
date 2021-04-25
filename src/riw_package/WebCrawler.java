package riw_package;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class WebCrawler {
    private final String robotName = "RIWEB_CRAWLER";
    private Map<String, Domain> dnsCache;   //IP->Domain details
    private Set<String> linksVisited;
    private ArrayDeque<String> queueURLS;   //URL Frontier
    private String outPath;

    //Optional
    public final static int LIMIT_QUEUE = 300;

    public WebCrawler(String[] SEED_LIST){
        this.dnsCache = new HashMap<>();
        this.linksVisited = new HashSet<>();

        this.queueURLS = new ArrayDeque<>();
        for(int i=0;i<SEED_LIST.length;++i){
            queueURLS.addLast(SEED_LIST[i]);
        }

        outPath = "D://RIWEB_CRAWLER_DATASET/";
    }

    public void setOutPath(String outPath){
        this.outPath = outPath;
    }



    public void crawling(){

        long start = System.currentTimeMillis();
        int i = 1;
        int minute = 60;    //60 secunde
        while (!queueURLS.isEmpty() && i++ <= LIMIT_QUEUE){

            String urlString = queueURLS.removeFirst();

            System.out.print(i-1 + "\t" + urlString + "\t qSize = " + queueURLS.size() + "; ");

            MyURI myURI = new MyURI(urlString);

            Domain domain = dnsCache.get(myURI.getHost());

            //=========================================================== create domain
            if(domain == null){ //daca domeniul inca nu a mai fost explorat, explorez acum
                try {
                    domain = new Domain();

                    String ip = InetAddress.getByName(myURI.getHost()).getHostAddress();    //preluare IP; ar trebuie sa fie modulul DNS
                    domain.setIPAdress(ip);

                    String robotsPath = myURI.getProtocol() + "://" + myURI.getHost() + ":" + myURI.getPort() + "/robots.txt";

                    Connection connection =
                            Jsoup
                                    .connect(robotsPath)
                                    .userAgent(robotName)
                                    .ignoreHttpErrors(true);

                    connection.execute();

                    Connection.Response response = connection.response();

                    RobotsInfo robotsInfo = new RobotsInfo();

                    if(response.statusCode() == 200){
                        String robotContent = response.body();
                        WriterHandler.writeContent(robotContent, myURI.getHost()  + "/robots/", outPath);
                        robotsInfo = RobotsHandler.getRobotsInfo(robotContent, robotName);
                        domain.setRobotsInfo(robotsInfo);
                    }else{
                        robotsInfo.setAllowAll(true);
                        robotsInfo.setDisallowAll(false);
                    }
                    dnsCache.put(myURI.getHost(),domain);


                } catch (UnknownHostException e) {
                    e.printStackTrace();

                    RobotsInfo robotsInfo = new RobotsInfo();
                    robotsInfo.setAllowAll(true);
                    robotsInfo.setDisallowAll(false);

                    domain.setRobotsInfo(robotsInfo);
                    dnsCache.put(myURI.getHost(), domain);
                    linksVisited.add(urlString);

                    //continue;

                } catch (IOException e) {
                    RobotsInfo robotsInfo = new RobotsInfo();
                    robotsInfo.setAllowAll(true);
                    robotsInfo.setDisallowAll(false);

                    domain.setRobotsInfo(robotsInfo);
                    dnsCache.put(myURI.getHost(), domain);
                    linksVisited.add(urlString);

                    e.printStackTrace();

                    //continue;

                }finally {

                    //continue;
                }
            }

            //==================================================================================================


            if(LinkHandler.havePermission(myURI.getPath(), domain)){

                Connection connection =
                        Jsoup
                        .connect(urlString)
                        .userAgent(robotName)
                        .ignoreHttpErrors(true);

                try {
                    connection.execute();

                    Connection.Response response = connection.response();

                    System.out.println("Status: "+ response.statusMessage());
                    if(response.statusCode() == 200 && response.statusMessage().equals("OK")){

                        Document pageDocument = connection.get();

                        WriterHandler.writeContent(pageDocument.outerHtml(),  myURI.getHost() + "/" + myURI.getPath(), outPath);

                        Set<String> linksFromPage = LinkHandler.getUtilLinksFromPage(pageDocument);

                        for(String link : linksFromPage){
                            //link-urile sa nu fie deja vizitate si sa nu fie deja in coada de vizitare
                            if(!linksVisited.contains(link) && !queueURLS.contains(link)){
                                queueURLS.addLast(link);
                            }
                        }

                    }else {
                        //nu pot descarca pagina, adaug in lista celor vizitate si trec la urmatoarea
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    linksVisited.add(urlString);
                    System.out.println();
                    continue;

                }finally {

                }
            }else{
                //do nothing
                System.out.println();
            }

            linksVisited.add(urlString);


            long stop = System.currentTimeMillis();
            double timeElapsed = (double)(stop - start)/1000; //in seconds
              //60 seconds
            if(timeElapsed > minute){
                System.out.printf("\n\n=====>   %3.2f secunde ( = %2.2f minute ); %d link-uri vizitate! \n\n", timeElapsed, timeElapsed/60, linksVisited.size());
                minute += 60;
            }

        }


        //System.out.println("Link-uri vizitate: " + linksVisited.size());
        System.out.println("Dns cache size: " + dnsCache.size());
        System.out.println("Link-uri de vizitat in continuare : " + queueURLS.size());

        //aceasta secventa de cod scrie intr-un fiseri domeniile explorate si ip-urile lor
//
//        try {
//            PrintWriter printWriter = new PrintWriter("domains.txt");
//            for(Map.Entry<String, Domain> entry : dnsCache.entrySet()){
//                //entry.getValue().display();
//                String host = entry.getKey();
//                String ip = entry.getValue().getIPAdress();
//
//
//                printWriter.println(host + ": " + ip);
//            }
//            printWriter.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }



    }
}
