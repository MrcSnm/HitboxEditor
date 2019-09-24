package app.file;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import app.Box;
import app.CrossPlatformFunctions;
import app.ImageComponent;
import app.ImportedView;

public class Saver
{
    public static String projectName;
    private Saver()
    {

    }
    public static void saveProject(ImportedView view)
    {
        ImageComponent buffer;
        String saveString = "{\n\t" + addProp("projectName", projectName) + ",\n";
        boolean hasNext = false;

        for(Map.Entry<String, ImageComponent> entry : view.images.entrySet())
        {
            hasNext = true;
            saveString+="\t";
            buffer = entry.getValue();
            String path = CrossPlatformFunctions.convertDirToOS(buffer.absolutePath).replaceAll("\\\\", "/");
            saveString+= "\"" + buffer.imgName + "\" : " + "\n\t{\n";
            saveString+= "\t\t" + "\"absolutePath\" : \"" + path + "\",\n";
            saveString+= "\t\t" + "\"pivot\" : {\"x\" : " + (float)buffer.anchorX + ", \"y\" : " + (float)buffer.anchorY + "},\n";
            saveString+= "\t\t" + addBox("hitboxes", buffer.hitboxes) + ",\n";
            saveString+= "\t\t" + addBox("hurtboxes", buffer.hurtboxes);
            saveString+="\n\t},\n";
        }
        saveString = saveString.substring(0, saveString.length() - 2);

        saveString+= "\n}";
        save(saveString);
    }

    private static String addBox(String propName, List<Box> value)
    {
        String boxes ="\"" + propName + "\" : \n\t\t[";
        boolean hasComma = false;
        for(Box b : value)
        {
            hasComma = true;
            boxes+= "\n\t\t\t{";
            boxes+= addProp("x", b.RECT_X) + ", " + addProp("y", b.RECT_Y) + ", " + addProp("width", b.RECT_X_2 - b.RECT_X) + ", " + addProp("height", b.RECT_Y_2 - b.RECT_Y) + "},";
        }
        if(hasComma)
            boxes = boxes.substring(0, boxes.length() - 1);
        boxes+= "\n\t\t]";
        

        return boxes;
    }

    private static String addProp(String propName, String value)
    {
        return "\"" + propName + "\" : " + "\"" + value + "\"";
    }

    private static <T> String addProp(String propName, T value)
    {
        return "\"" + propName + "\" : " + value;
    }

    private static void save(String content)
    {
        String path = CrossPlatformFunctions.crossPlatformSave("Save project", "*.json");
        try
        {
            PrintWriter file;
            file = new PrintWriter(path);
            file.write(content);
            file.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}