package app.base;


public abstract class ConcurrentUpdate extends Thread
{
    public boolean isLooping = false;
    private boolean waitingForPriority = false;
    public double deltaTime = 0;
    public double updateDelay = 0;

    public ConcurrentUpdate(float updatesPerSecond)
    {
        updateDelay = 1000 / updatesPerSecond;
    }

    public ConcurrentUpdate()
    {
        this(60);
    }

    @Override
    public void run()
    {
        while(isLooping)
        {
            if(!waitingForPriority)
            {
                long startTime = System.nanoTime();
                update(deltaTime / 1000);
                long endTime = System.nanoTime() - startTime;
                double miliDt = (double) endTime / 1000000;
                if (miliDt < updateDelay)
                {
                    deltaTime = updateDelay - miliDt;
                    try {Thread.sleep((long) (updateDelay - miliDt));}
                    catch (InterruptedException e) {e.printStackTrace();}
                }
            }
            else
            {
                try {Thread.sleep((long) (300));}
                catch (InterruptedException e) {e.printStackTrace();}
            }
        }
    }

    public abstract void update(double deltaTime);

    public void setUpdating(boolean isUpdating)
    {
        waitingForPriority = !isUpdating;
    }

    @Override
    public void start()
    {
        super.start();
        isLooping = true;
    }
}