//package com.s_hashtag.batch.config;
//
//import com.s_hashtag.batch.processor.InstagramBatchProcessor;
//import com.s_hashtag.batch.writer.InstagramBatchWriter;
//import com.s_hashtag.common.place.domain.model.Place;
//import com.s_hashtag.instagram.dto.CrawlingDto;
//import com.songpapeople.hashtagmap.instagram.processor.InstagramBatchProcessor;
//import com.songpapeople.hashtagmap.instagram.writer.InstagramBatchWriter;
//import com.songpapeople.hashtagmap.place.domain.model.Place;
//import com.songpapeople.hashtagmap.service.CrawlingResult;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.database.JpaPagingItemReader;
//import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.persistence.EntityManagerFactory;
//
//@RequiredArgsConstructor
//@Configuration
//public class InstagramCrawlingJobConfiguration {
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
////    private final EntityManagerFactory entityManagerFactory;
//    private final InstagramBatchProcessor instagramBatchProcessor;
//    private final InstagramBatchWriter instagramBatchWriter;
//    private final BatchConfiguration batchConfiguration;
//
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
//}
