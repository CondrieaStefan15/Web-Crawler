package riw_package;

import java.net.URI;
import java.net.URISyntaxException;

public class MyURI {

    private String protocol;
    private String path;
    private String host;
    private int port;



    public String getProtocol(){
        return protocol;
    }
    public String getPath() {
        return path;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }


    public MyURI(String url) {

        try {
            URI uri = new URI(url);

            String protocol = uri.getScheme();
            String path = uri.getRawPath();
            String host = uri.getHost();
            int port = uri.getPort();

            if(path == null || path.length() == 0){
                path = "/";
            }
            if(port == -1){
                if(protocol.equals("http"))
                    port = 80;
                else if(protocol.equals("https")){
                    port = 443;
                }
            }

            this.host = host;
            this.path = path;
            this.protocol = protocol;
            this.port = port;

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
