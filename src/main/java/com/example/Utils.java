package com.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

public class Utils {


    /**
     * Find files on resources from main / test
     */
    public static String findFilesResources(String fileName) {

        String path = System.getProperty("user.dir") + File.separator;

        ArrayList<String> list = new ArrayList<String>();
        list.add(path);
        list.add(path + "src" + File.separator + "main" + File.separator + "resources"+ File.separator);
        list.add(path + "src" + File.separator + "test" + File.separator + "resources"+ File.separator);

        String find = null;

        for ( String paths: list ){
            if (new File(paths + fileName).exists())
                find = paths + fileName;
        }

        if (find == null)
            throw new IllegalStateException("could not find the file: " + "\"" + fileName + "\"" + "\n in the folders: " + list);

        return find;
    }

    /***
     * get properties
     * @param fileName
     * @return
     */
    public static Properties getProperties(String fileName) {

        String propertiesFile = Utils.findFilesResources(fileName);
        Properties properties = new Properties();
        try(InputStream f = Files.newInputStream(Paths.get(propertiesFile))) {
            properties.load(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

}
