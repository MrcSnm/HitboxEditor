package app.file;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import app.Box;
import app.Editor;
import app.CrossPlatformFunctions;
import app.ImageComponent;
import app.ImportedView;
import app.MainWindow;

public class Saver
{
    public static String projectName;
    public static boolean isSaved = true;

    private Saver()
    {

    }

    public static void setSaved()
    {
        MainWindow.setEditorTitle(false);
        isSaved = true;
    }
    public static void setUnsaved()
    {
        MainWindow.setEditorTitle(true);
        isSaved = false;
    }

    public static void saveExit(ImportedView view, Editor editor)
    {
        if(isSaved)
            System.exit(0);
        int opt = JOptionPane.showOptionDialog
        (
            null, "File is not saved, do you want to save?", "Save file", JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, null, null
        );
        switch(opt)
        {
            case JOptionPane.YES_OPTION:
                saveProject(view, editor);
                System.exit(0);
            case JOptionPane.NO_OPTION:
                System.exit(0);
            case JOptionPane.CANCEL_OPTION:
                break;
            default:
                break;
        }
    }

    public static void saveProject(ImportedView view, Editor editor)
    {
        if(isSaved)
            return;
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
            saveString+= "\t\t" + "\"pivot\" : {\"x\" : " + (float)buffer.anchor.x + ", \"y\" : " + (float)buffer.anchor.y + "},\n";
            saveString+= "\t\t" + addBox("hitboxes", buffer.hitboxes, editor) + ",\n";
            saveString+= "\t\t" + addBox("hurtboxes", buffer.hurtboxes, editor);
            saveString+="\n\t},\n";
        }
        saveString = saveString.substring(0, saveString.length() - 2);

        saveString+= "\n}";
        save(saveString);
        isSaved = true;
    }

    private static String addBox(String propName, List<Box> value, Editor editor)
    {
        String boxes ="\"" + propName + "\" : \n\t\t[";
        boolean hasComma = false;
        for(Box b : value)
        {
            hasComma = true;
            boxes+= "\n\t\t\t{";
            if(!b.canSave())
            {
                b.toggleNormallize(editor);
                boxes+= addProp("x", b.RECT_X) + ", " + addProp("y", b.RECT_Y) + ", " + addProp("width", b.RECT_X_2 - b.RECT_X) + ", " + addProp("height", b.RECT_Y_2 - b.RECT_Y) + "},";
                b.toggleNormallize(editor);
            }
            else
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
        String path;
        if(MainWindow.PROJECT_PATH != null)
            path = MainWindow.PROJECT_PATH;
        else
            path = CrossPlatformFunctions.crossPlatformSave("Save project", "*.json");
        try
        {
            PrintWriter file;
            if(path == null || path.equals(""))
                return;
            if(!path.contains(".json"))
                path+= ".json";
            file = new PrintWriter(path);
            file.write(content);
            file.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        MainWindow.setEditorTitle(false);
    }
}