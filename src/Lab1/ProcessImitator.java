package Lab1;

class ProcessImitator {
    private int mTimeToLive = 6000;
    private boolean mIsEnd = false;
    private int mProcessId;

    public ProcessImitator(int processNumber) {
        this.mProcessId = processNumber;
    }

    public ProcessImitator(int processNumber, int timeToLive) {
        this.mProcessId = processNumber;
        this.mTimeToLive = timeToLive;
    }

    /**
     * Imitates hard work during mTimeToLive sec
     * (Creates several Threads)
     */
    public void run() {

        Thread[] threads = new Thread[15];
        for (Thread t : threads) {
            t = new Thread(new calculatorThread());
            t.setDaemon(true); // just in case this motherfuckers would want to live by themselves
            t.start();
        }
        try {
            Thread.sleep(mTimeToLive);
            mIsEnd = true;

            // beware to end all threads before method returns
            for (Thread t : threads) {
                if (t != null) {
                    t.join();
                }

            }
        } catch (InterruptedException e) {
            Logger.logMessage("InterruptedException in " + this.toString());
            e.printStackTrace();
        }
    }

    public void setTimeToLive(int t) {
        mTimeToLive = t;
    }

    public int getProcessId() {
        return mProcessId;
    }

    private class calculatorThread implements Runnable {

        @Override
        public void run() {
            // just to keep thread busy
            outer:
            while (true) {
                for (double i = 1, d = 1; i < Integer.MAX_VALUE; i++) {
                    d = (i * 1.2);
                    if (mIsEnd) break outer;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Logger.logMessage("InterruptedException in " + this.toString());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
