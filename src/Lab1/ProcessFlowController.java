package Lab1;


// Working in the separate form GUI thread
class ProcessFlowController extends Thread {
    private boolean mIsEnd = false;
    private int mProcessesExecuted;
    private int mProcessesDestroyed;
    private int mProcessId;

    private ProcessImitator mProcessImitator;

    // TODO replace with an array of CPUs
    private CpuImitator mCpu1;
    private CpuImitator mCpu2;

    // TODO create a queue for added processes

    @Override
    public void run() {
        // play "Awolnation – Run" starting from 2:10
        // https://soundcloud.com/awolnation/01-run#t=2:10

        while (!mIsEnd) {
            assignProcesses();
        }
        calculateStats();
    }

    // synchronized because of a threat creating processes with same number
    synchronized void createNewProcess() {
        mProcessImitator = new ProcessImitator(mProcessId++);
        notify();
    }

    /**
     * check if there are unassigned processes, if so, assign them.
     */
    private synchronized void assignProcesses() {
        if (mProcessImitator != null) {
            if (mCpu1.isAvailable()) {
                mProcessImitator.setTimeToLive(5000);
                mCpu1.assignTask(mProcessImitator);
                mProcessesExecuted++;
                mProcessImitator = null;

            } else if (mCpu2.isAvailable()) {
                mProcessImitator.setTimeToLive(7000);
                mCpu2.assignTask(mProcessImitator);
                mProcessesExecuted++;
                mProcessImitator = null;

            } else {
                Logger.logMessage("Process № " + mProcessImitator.getProcessId() + " was destroyed (all CPUs are busy)");
                mProcessesDestroyed++;
                mProcessImitator = null;
            }

        } else {
//            Logger.logMessage("So far all processes distributed");
            // wait until new processes will be created
            try {
                // using wait() instead of Thread.sleep() in order to distribute processes with minimum delay
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Logger.logMessage("InterruptedException in " + this.toString());
            }
        }
    }

    synchronized public void stopSimulation() {
        mIsEnd = true;

        try {
            // wait everything to stop
            mCpu1.stopCpu();
            mCpu2.stopCpu();
            if (mCpu1 != null) mCpu1.join();
            if (mCpu2 != null) mCpu2.join();
        } catch (InterruptedException e) { /* ignore */ }

        // notify that there will be no new processes
        notify();
    }

    public void startSimulation() {
        // reset counters for each simulation
        mProcessesExecuted = 0;
        mProcessesDestroyed = 0;
        mProcessId = 1;

        // threads can't be reused, so reinitialize them
        mCpu1 = new CpuImitator("CPU1");
        mCpu2 = new CpuImitator("CPU2");

        mCpu1.startCpu();
        mCpu2.startCpu();

        this.start();
    }

    public boolean isEnd() {
        return mIsEnd;
    }

    private void calculateStats() {
        Double destroyedPercentage = 100.0 * mProcessesDestroyed / (mProcessesDestroyed + mProcessesExecuted);
        if (destroyedPercentage.isNaN()) {
            destroyedPercentage = 0D;
        }
        Logger.logMessage("Destroyed " + destroyedPercentage + "% of processes");
    }

}
