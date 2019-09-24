package app.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.Callable;

import app.Box;
import app.ImageComponent;
import app.ImportedView;

public class Loader {
    private Loader() {

    }

    public static ArrayList<String> getFileLines(String path) {
        BufferedReader f;
        ArrayList<String> lines = new ArrayList<String>();
        String currentLine;
        try 
        {
            f = new BufferedReader(new FileReader(path));
            try
            {
                do
                {
                    currentLine = f.readLine();
                    lines.add(currentLine);
                }
                while(currentLine != null);
                lines.remove(lines.size() - 1);
                f.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        } 
        catch (FileNotFoundException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lines;
    }

    public static void loadProject(String path, ImportedView importedView)
    {
        ArrayList<String> lines = getFileLines(path);
        importedView.images.clear();
    
        parse(lines, importedView);
    }

    private static void parse(ArrayList<String> lines, ImportedView imported)
    {
        String[] returned;

        int x, y, w, h;
        Box currentBox = new Box(0,0,0,0);
        List<Box> scheduledHitboxes = new ArrayList<Box>();
        List<Box> scheduledHurtboxes = new ArrayList<Box>();
        AtomicReference<Float> pivotX = new AtomicReference<Float>();
        AtomicReference<Float> pivotY = new AtomicReference<Float>();
        
        Callable<Box> boxCreationFunction = new Callable<Box>()
        {
            public Box call()
            {
                return new Box(0,0,0,0);
            }
        };
        for(String line : lines)
        {
            //CHECK FOR FILENAME/Object
            //returned = (line.split("/\"(\w+.png)\"/");

            //GETTING Numbers \"\\w+\"\\s\\:\\s
            returned = line.split("\"\\w+\"\\s\\:\\s");

            if(line.contains(".png"))
            {
                scheduledHitboxes = new ArrayList<Box>();
                scheduledHurtboxes = new ArrayList<Box>();
                imported.scheduleCreation(scheduledHitboxes, scheduledHurtboxes, pivotX, pivotY, line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
                System.out.println(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
            else if(line.contains("\"hitboxes\""))
            {
                final List<Box> currentScheduleHB = scheduledHitboxes;
                boxCreationFunction = new Callable<Box>() 
                {
                    public Box call()
                    {
                        Box b = new Box(0,0,0,0);
                        currentScheduleHB.add(b);
                        return b;
                    }
                };
            }
            else if(line.contains("\"hurtboxes\""))
            {
                final List<Box> currentScheduleHB = scheduledHitboxes;
                boxCreationFunction = new Callable<Box>() 
                {
                    public Box call()
                    {
                        Box b = new Box(0,0,0,0);
                        currentScheduleHB.add(b);
                        return b;
                    }
                };
            }
            if(returned.length == 5)
            {
                try
                {
                    currentBox = boxCreationFunction.call();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                x = Integer.parseInt(returned[1].split("\\D")[0]);
                y = Integer.parseInt(returned[2].split("\\D")[0]);
                w = Integer.parseInt(returned[3].split("\\D")[0]);
                h = Integer.parseInt(returned[4].split("\\D")[0]);
                currentBox.RECT_X = x;
                currentBox.RECT_X_2 = x + w;
                currentBox.RECT_Y = y;
                currentBox.RECT_Y_2 = y + h;
            }
            else if(returned.length == 4)
            {
                pivotX.set(Float.parseFloat(returned[2].split("\\D")[0]));
                pivotY.set(Float.parseFloat(returned[3].split("\\D")[0]));
            }
        }
        imported.executeScheduled();
    }

    private static String find(ArrayList<String> lines, String start, String defaultValue)
    {
        String returnValue;
        for(String line : lines)
        {
            if(line.startsWith(start))
            {
                returnValue =  line.substring(start.length());
                return returnValue;
            }
        }
        return defaultValue;
    }

    private boolean find(ArrayList<String> lines, String start, boolean defaultValue)
    {
        String str = find(lines, start, Boolean.toString(defaultValue));
        if(str != null)
            return Boolean.parseBoolean(str);
        return defaultValue;
    }

    private int find(ArrayList<String> lines, String start, int defaultValue)
    {
        String str = find(lines, start, Integer.toString(defaultValue));
        if(str != null)
            return Integer.parseInt(str);
        return defaultValue;
    }

    private float find(ArrayList<String> lines, String start, float defaultValue)
    {
        String str = find(lines, start, Float.toString(defaultValue));
        if(str != null)
            return Float.parseFloat(str);
        return defaultValue;
    }
}