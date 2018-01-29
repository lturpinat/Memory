package utils;

import model.Theme;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static model.Constants.*;

/**
 * Utility class to find card images
 *
 * @author lturpinat
 */
public final class ResourceFinder {

    private ResourceFinder(){}

    /**
     * Return a list of themes <br>
     * @param resourceDirectoryPath directory where the themes are stored
     * @return the themes recognized in {@code resourceDirectoryPath}
     * @throws NullPointerException if reading directories or images fail
     */
    public static List<Theme> getAvailableThemes(URI resourceDirectoryPath){
        File file = new File(resourceDirectoryPath);

        if(file.isFile())
            return null;

        List<Theme> themes = new ArrayList<>();

        //Get all directories from the resourcePath directory
        File[] directories = file.listFiles(File::isDirectory);

        //Get all resources folders
        for(File potentialTheme : directories){
            //Check for the files' extension (@see checkImageFileExtension())
            File[] files = potentialTheme.listFiles((filenameFilter, source) ->
                    checkImageFileExtension(new File(filenameFilter, source).getName()));

            if (files.length < MINIMAL_DECK_SIZE)//We need a theme that matches the minimal configuration of the game
                continue;

            //Only cards' images
            List<URI> images = new ArrayList<>();

            URI backgroundImage = findImageByName(files, IMAGE_BACKGROUND_FILENAME);
            URI hiddenCardImage = findImageByName(files, HIDDEN_CARD_FILENAME);

            Arrays.stream(files).forEach(x -> {
                //Prevent from inserting background and hidden card images (named "-1" and "0")
                if(!(x.getName().startsWith(IMAGE_BACKGROUND_FILENAME) || x.getName().startsWith(HIDDEN_CARD_FILENAME)))
                    images.add(x.toURI());
            });

            themes.add(new Theme(potentialTheme.getName(), images, hiddenCardImage, backgroundImage));
        }

        return themes;
    }

    /**
     * Return the image matching the specified name
     * @param imageFiles list of files where to look for the image
     * @param fileName file name without the extension
     * @return the URI of the found file or null if not found
     */
    private static URI findImageByName(File[] imageFiles, String fileName){
        //Find fileName followed by anything (eg. XY.png)
        String filePattern = "(" + fileName + ").*";

        //Return null if not found
        File file = Arrays.stream(imageFiles)
                .filter(image -> Pattern.matches(filePattern, image.getName()))
                .findFirst().orElse(null);

        return file == null ? null : file.toURI();
    }

    /**
     * Return whether or not the file is a jpg or png file <br/>
     * This check is made with the file extension only.
     * @param fileName full name of the file
     * @return true if it is an image file
     */
    private static boolean checkImageFileExtension(String fileName){
        return Pattern.matches(IMAGE_REGEX_STRING, fileName);
    }
}
