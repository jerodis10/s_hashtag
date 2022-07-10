package com.s_hashtag.batch.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class BatchApiController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @GetMapping("/run")
    public String handle() throws Exception{
        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time",System.currentTimeMillis())
                        .toJobParameters();
        jobLauncher.run(job, jobParameters);

        return "redirect:/";
    }
}
