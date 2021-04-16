package at.Xirado.terminal.Utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLoader
{
    /**
     * Copies a resource from the resources-folder to the working-directoy
     * @param filepath The path of the file
     * @return True if the file was copied successfully
     */
    public static boolean copyFileFromResources(String filepath)
    {
        try(InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream(filepath))
        {
            if(inputStream != null)
            {
                File file = new File(filepath);
                if(file.exists()){
                    return false;
                }
                try(OutputStream outputStream = new FileOutputStream(file))
                {
                    IOUtils.copy(inputStream, outputStream);
                }catch (IOException ex)
                {
                    ex.printStackTrace();
                }
                return true;
            }
            return false;
        }catch (IOException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    public static Path getPath()
    {
        String pathstring = FileLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(pathstring, StandardCharsets.UTF_8);
        return Path.of(new File(decodedPath).getParentFile().getPath()+"\\");
    }
}
