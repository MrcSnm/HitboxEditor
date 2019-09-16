package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.event.IIOReadProgressListener;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JProgressBar;
//http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/AddImageIOReadProgressListenertoImageReader.htm
public class ImageProgress
{
	
    private static Thread t;
    public static File currentScheduledFile;
    private static List<File> scheduledFiles = new ArrayList<File>();
    public long filesSize = 0;
    public long currentReadSize = 0;
    public long totalReadSize = 0;
    public long completedSize = 0;
    private boolean scheduledToStop = false;

    public BufferedImage imageRead;
    FileInputStream fileInputStream;
    ImageInputStream imageInputStream;
    ImageReader reader;
    JProgressBar progressBar;
    
    public static float currentProgress;


    public static long getBytesReadFromFile(float percentageDone)
    {
        if(currentScheduledFile != null)
        {
            return (long)(currentScheduledFile.length() * (percentageDone / 100));
        } 
        return -1;
    }
    
    
    Callable<?> onProgressHandler = null;
    Callable<?> onCompleteHandler = null;
    Callable<?> onProcessCompleteHandler = null;

    public boolean setImageToRead(File img)
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream(img);
            currentScheduledFile = img;
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(fileInputStream);
            reader.setInput(imageInputStream, false);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return false;
    }

    public boolean setImagesToRead(File[] imgs)
    {
      for(int i = 0, len = imgs.length; i < len; i++)
      {
          scheduledFiles.add(imgs[i]);  
          if(imgs[i].length() != -1)
          {
            filesSize+= imgs[i].length();
          }
          else
          {
            forceStop();
            throw new Error("Could not read " + imgs[i].getName());
          }
      }
      return setImageToRead(imgs[0]);
    }

    private void readNextFile()
    {
      if(scheduledFiles.size() >= 1)
      {
        setImageToRead(scheduledFiles.get(0));
      }
      else
        t = null;
    }
    
    public void setOnProgressHandler(Callable<?> call)
    {
    	onProgressHandler = call;
    }
    
    public void setOnCompleteHandler(Callable<?> call)
    {
      onCompleteHandler = call;	
    }

    public void setOnProcessCompleteHandler(Callable<?> call)
    {
      onProcessCompleteHandler = call;
    }

    private void finishExecution()
    {
      
      try
      {
        onCompleteHandler.call();
        scheduledFiles.remove(0);

      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }

    public void forceStop()
    {
      scheduledToStop = true;
    }
    private void stopProcess()
    {
      filesSize = 0;
      scheduledFiles.clear();
      currentProgress = 0;
      currentReadSize = 0;
      currentScheduledFile = null;
      t = null;
      imageRead = null;
    }
    
    ImageProgress(JProgressBar ref)
    {
        reader = (ImageReader)ImageIO.getImageReadersByFormatName("PNG").next();
        progressBar = ref;
        reader.addIIOReadProgressListener(new IIOReadProgressListener() 
        {
            public void imageComplete(ImageReader source) 
            {
                completedSize+= currentScheduledFile.length();
            }
      
            public void imageProgress(ImageReader source, float percentageDone) 
            {
              totalReadSize = completedSize + getBytesReadFromFile(percentageDone);
              currentProgress = (float)totalReadSize / filesSize;
              ref.setValue((int)(currentProgress * 100));
              if(onProgressHandler != null)
              {
                try 
                {
                  onProgressHandler.call();
                }
                catch (Exception e) 
                {
                  e.printStackTrace();
                }
              }
            }
      
            public void imageStarted(ImageReader source, int imageIndex) 
            {
            }
      
            public void readAborted(ImageReader source) 
            {
              System.out.println("read aborted " + source);
            }
      
            public void sequenceComplete(ImageReader source) 
            {
              System.out.println("sequence complete " + source);
            }
      
            public void sequenceStarted(ImageReader source, int minIndex) 
            {
              System.out.println("sequence started " + source + ": " + minIndex);
            }
      
            public void thumbnailComplete(ImageReader source) 
            {
              System.out.println("thumbnail complete " + source);
            }
      
            public void thumbnailProgress(ImageReader source, float percentageDone) 
            {
              System.out.println("thumbnail started " + source + ": " + percentageDone + "%");
            }
      
            public void thumbnailStarted(ImageReader source, int imageIndex, int thumbnailIndex) 
            {
              System.out.println("thumbnail progress " + source + ", " + thumbnailIndex + " of "
                  + imageIndex);
            }
          });


        }

    public void readImages() throws IOException
    {
    	
    	t = new Thread(new Runnable()
    	{
          @Override
          public void run()
          {
            while(t != null)
            {
              if(scheduledToStop)
              {
                break;
              }
              try 
              {
               imageRead = reader.read(0);
               finishExecution();
               readNextFile();
              }
              catch (IOException e) 
              {
                 e.printStackTrace();
              }
            }
            if(onProcessCompleteHandler != null)
            {
              try
              {
                onProcessCompleteHandler.call();
              }
              catch(Exception e)
              {
                e.printStackTrace();
              }
            }
            if(scheduledToStop)
              stopProcess();
          }
      });
    	t.start();
    }
}