package com.ilives.baseprj.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileUtils {

    public static final List<String> IMAGE_EXTENSIONS = new ArrayList<>();
    public static final List<String> VIDEO_EXTENSIONS = new ArrayList<>();

    static {
        IMAGE_EXTENSIONS.add("jpg");
        IMAGE_EXTENSIONS.add("png");
        IMAGE_EXTENSIONS.add("jpeg");
        IMAGE_EXTENSIONS.add("tiff");

        VIDEO_EXTENSIONS.add("mp4");
    }

    public static List<String> getFileListInFolder(String folderPath, List<String> extensions) {
        File folder = new File(folderPath);
        List<String> results = new ArrayList<>();
        if (folder.exists()) {
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null)
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        String fileName = listOfFiles[i].getName();
                        int extensionIndex = fileName.lastIndexOf(".");
                        if (extensionIndex != -1) {
                            String extension = fileName.substring(extensionIndex + 1);
                            if (extensions.contains(extension)) {
                                results.add(fileName);
                            }
                        }
                    }
                }
        }
        return results;
    }

    public static boolean isImages(String fileName) {
        return IMAGE_EXTENSIONS.contains(getFileExtension(fileName).toLowerCase());
    }

    public static boolean isVideo(String fileName) {
        return VIDEO_EXTENSIONS.contains(getFileExtension(fileName));
    }

    private static String getFileExtension(String fileName) {
        int extensionIndex = fileName.lastIndexOf(".");
        return extensionIndex > -1 ? fileName.substring(extensionIndex + 1) : "";
    }

    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                if (children != null) {
                    for (int i = 0; i < children.length; i++) {
                        deleteFile(children[i]);
                    }
                }
                file.delete();
            } else {
                file.delete();
            }
        }
    }
}
