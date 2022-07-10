package com.s_hashtag.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class InstagramCrawlingJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
//    private final EntityManagerFactory entityManagerFactory;
//    private final InstagramBatchProcessor instagramBatchProcessor;
//    private final InstagramBatchWriter instagramBatchWriter;
//    private final BatchConfiguration batchConfiguration;


    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1())
                .build();
    }

    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

//    @Bean
//    public Job crawlingJob() {
//        return jobBuilderFactory.get("crawlingJob")
//                .start(crawlingStep())
//                .build();
//    }
//
//    @Bean
//    public Step crawlingStep() {
//        return stepBuilderFactory.get("crawlingStep")
//                .<Place, CrawlingDto>chunk(batchConfiguration.getChunk())
//                .reader(placeReader())
////                .processor(instagramBatchProcessor)
////                .writer(instagramBatchWriter)
//                .build();
//    }
//
//    @Bean
//    public JpaPagingItemReader<Place> placeReader() {
//        return new JpaPagingItemReaderBuilder<Place>()
//                .name("placeReader")
////                .entityManagerFactory(entityManagerFactory)
//                .pageSize(batchConfiguration.getChunk())
//                .queryString("SELECT p FROM Place p order by place_id")
//                .build();
//    }
}
