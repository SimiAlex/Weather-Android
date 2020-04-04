package com.example.todayweather;

class MyRunnable implements Runnable
{
    // fields
    private MainActivity mainActivity;

    // constructor
    public MyRunnable(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    // methods
    @Override
    public void run()
    {
        mainActivity.getDataInNewThread();
        mainActivity.updateUI();
    }
}
