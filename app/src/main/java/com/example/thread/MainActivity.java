package com.example.thread;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    private AtomicInteger atomicInteger = new AtomicInteger(1);

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // counter Thread with Synchrozied keyword

        CounterThread counterThread = new CounterThread();
        counterThread.start();

        // thread first

//        ThreadFirst threadFirst = new ThreadFirst();
//        threadFirst.start();

        // second thread

//        ThreadSecond threadSecond= new ThreadSecond();
//        threadSecond.start();

        // third

//        ThreadThird threadThird = new ThreadThird();
//        threadThird.start();


    }

    //CountThread


    class CounterThread extends Thread {
        @Override
        public void run() {
            CounterValue counter = new CounterValue();
            Log.d(TAG, "threads main counter : " +Thread.currentThread().getId());

            Thread[] threads = new Thread[10];
            for (int i = 0; i < 10; i++) {
                threads[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "threads: " +Thread.currentThread().getId());
                        counter.incrementCount();
                    }
                });
                threads[i].start();
            }
            for (int i = 0; i < 10; i++) {
                try {
                    Log.d(TAG, " counter join: " +Thread.currentThread().getId());

                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "run: "+ counter.getIncrementValue() +"curent thrad "+Thread.currentThread().getId());
        }
    }

    int a = 0;

    class ThreadFirst extends Thread {
        @Override
        public void run() {
            // task
            int count = 0;
            Log.d(TAG, "first start time: " + System.currentTimeMillis());
            for (int i = 0; i < 3; i++) {
                a++;
                if (atomicInteger.get() == 1) {
                    Log.d(TAG, "ThreadFirst run: " + count++ + " a " + a);
                    atomicInteger.set(1);
                }
            }
            Log.d(TAG, "first end  time: " + System.currentTimeMillis());
        }
    }


    class ThreadSecond extends Thread {
        @Override
        public void run() {
            // task
            int count = 0;
            a++;
            Log.d(TAG, "second start time: " + System.currentTimeMillis());
            for (int i = 0; i < 3; i++) {
                if (atomicInteger.get() == 1) {
                    Log.d(TAG, "ThreadSecond run: " + count++ + " a " + a);
                    atomicInteger.set(4);
                }
            }
            Log.d(TAG, "second end time: " + System.currentTimeMillis());

        }
    }

    class ThreadThird extends Thread {
        @Override
        public void run() {
            // task
            int count = 0;
            Log.d(TAG, "third start time: " + System.currentTimeMillis());

            for (int i = 0; i < 6; i++) {
                if (atomicInteger.get() == 4) {
                    Log.d(TAG, "ThreadThird run: " + count++);
                    atomicInteger.set(6);
                }
            }
            Log.d(TAG, "thread end time: " + System.currentTimeMillis());
        }
    }

    class CounterValue {

        int count = 0;

        public synchronized int incrementCount() {
            return count++;
        }

        public synchronized int getIncrementValue() {
            return count;
        }
    }
}