package com.waytojava.jobschedulardemo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Manish Kumar on 11/16/2018
 */
public class MainActivity extends AppCompatActivity {

    private Button btnScheduleJob;
    private Button btnCancelJob;
    public static final String TAG = "JobSchedularDemo";
    private static final int JOB_ID = 11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnScheduleJob = findViewById(R.id.btn_schedule_job);
        btnCancelJob = findViewById(R.id.btn_cancel_job);

        btnScheduleJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });

        btnCancelJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                jobScheduler.cancel(JOB_ID);
                Log.d(TAG, "Job is cancelled");
            }
        });
    }

    private void scheduleJob() {
        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, componentName)
                //.setRequiresCharging(true)      // Only executes the job when device is pluggedin or charging.
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // WiFi
                .setPersisted(true) // Make job life even after rebooting of Device.
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job Scheduled");
        } else {
            Log.d(TAG, "Job Scheduling Failed");
        }
    }
}
