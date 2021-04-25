package riw_package;



public class MainClass {

    public static void main(String[] args){

        long startMain = System.currentTimeMillis();

        String[] SEED_LIST = {"http://ro.wikipedia.org/"};
        WebCrawler webCrawler = new WebCrawler(SEED_LIST);
        webCrawler.setOutPath("D:/RIWEB_CRAWLER_DATASET/");

        webCrawler.crawling();


        long stopMain = System.currentTimeMillis();

        System.out.printf("Time: %2.2f minute!", (double)(stopMain - startMain)/1000/60);

    }

    public static void cea(String asdf){
        System.out.println(asdf);
    }
}
