package app.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

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
                while(currentLine != "\0");
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
    
        String line;
        
        for(int i = 0, len = lines.size(); i < len; i++)
        {
            line = lines.get(i);
            if(i == 1)
            {
                
            }
        }

    }

    private static String find(ArrayList<String> lines, String start, String defaultValue)
    {
        String returnValue;
        for(String line : lines)
        {
            if(line.startsWith(start))
            {
                returnValue =  line.substring(start.length());

                //Split x y w h in /\:\s(\w+)/g)
                //returnValue.split(regex)
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
        for(String line : lines)
        {
            if(line.startsWith(start))
                return line.substring(start.length());
        }
        return defaultValue;
    }

    private float find(ArrayList<String> lines, String start, float defaultValue)
    {
        for(String line : lines)
        {
            if(line.startsWith(start))
                return line.substring(start.length());
        }
        return defaultValue;
    }
}