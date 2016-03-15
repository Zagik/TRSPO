package Lab1;

/**
 * Every second checks if there is a process to run, if so, runs it.
 * Working in its own thread
 */
class CpuImitator extends Thread {
    private boolean mIsEnd = false;
    private boolean isAvailable = true;
    private String mName;
    private ProcessImitator mProcessImitator;
    private int mMessageTimer = 0;

    CpuImitator(String n) {
        mName = n;
    }

    public void assignTask(ProcessImitator pi) {
        mProcessImitator = pi;
    }

    @Override
    public void run() {
        while (!mIsEnd) {

            if (mProcessImitator != null) {
                isAvailable = false;

                Logger.logMessage("Process № " + mProcessImitator.getProcessId() + " Started working in " + mName);
                mProcessImitator.run();
                Logger.logMessage("Process № " + mProcessImitator.getProcessId() + " Finished working in " + mName);

                mProcessImitator = null;
                isAvailable = true;
            } else {

                try {
                    // log message every 2 sec (100 * 20 ms)
                    if (mMessageTimer == 20) {
                        Logger.logMessage(mName + ": waiting for process");
                        mMessageTimer = 0;
                    }
                    Thread.sleep(100);
                    mMessageTimer++;
                } catch (InterruptedException e) {
                    Logger.logMessage("InterruptedException in " + this.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // can't use method stop(), because its already used by superclass
    public void stopCpu() {
        mIsEnd = true;
    }

    // for naming consistency only
    public void startCpu() {
        this.start();
    }
}
