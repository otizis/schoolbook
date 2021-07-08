package com.dzsb.util.schoolbook;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextWork
{
    public static void main(String[] args) throws IOException
    {
        File dir = new File("D:\\Download\\poetrys");
        File[] files = dir.listFiles();

        Thumbnails.fromFiles(Arrays.asList(files)).width(256).outputFormat("png").asFiles(Rename.PREFIX_DOT_THUMBNAIL);


    }
}
