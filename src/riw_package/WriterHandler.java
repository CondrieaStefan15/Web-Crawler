package riw_package;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class WriterHandler {

    //functie care scrie un string intr-un path, in interiorul unui director radacina
    public static void writeContent(String pageContent, String path, String rootDirectory){

        path = path.replaceAll("[:*|?<>\"\\\\]", "");
        if(!path.endsWith("/")){
            path += "/";
        }
        if(!rootDirectory.equals("/")){
            rootDirectory += "/";
        }

        File file = new File(rootDirectory + path + "file.txt");
        file.getParentFile().mkdirs();

        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(pageContent);
            printWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println(path + " Eroare!!!!!!!!!!!!!1111");
            e.printStackTrace();
        }
    }








}
