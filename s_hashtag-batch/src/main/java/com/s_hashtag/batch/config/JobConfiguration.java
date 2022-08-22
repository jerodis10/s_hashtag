package com.s_hashtag.batch.config;

import com.s_hashtag.batch.processor.InstagramBatchProcessor;
import com.s_hashtag.batch.reader.KakaoBatchReader;
import com.s_hashtag.batch.writer.InstagramBatchWriter;
import com.s_hashtag.batch.writer.KakaoBatchWriter;
import com.s_hashtag.common.place.domain.model.Place;
import com.s_hashtag.common.instagram.dto.external.CrawlingDto;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BatchConfiguration batchConfiguration;
    private final DataSource dataSource;

    private final KakaoBatchReader kakaoBatchReader;
    private final KakaoBatchWriter kakaoBatchWriter;

    private final InstagramBatchProcessor instagramBatchProcessor;
    private final InstagramBatchWriter instagramBatchWriter;


    @Bean
    public Job kakaoPlaceJob() {
        return jobBuilderFactory.get("kakaoPlaceJob")
                .start(kakaoStep())
                .build();
    }

    @Bean
    public Job InstagramCrawlingJob() {
        return jobBuilderFactory.get("InstagramCrawlingJob")
                .start(crawlingStep())
                .build();
    }

    @Bean
    public Job kakaoInstagramCrawlingJob() {
        return jobBuilderFactory.get("kakaoInstagramCrawlingJob")
                .start(kakaoStep())
                .next(crawlingStep())
                .build();
    }

    @Bean
//    @StepScope
    public Step kakaoStep() {
        return stepBuilderFactory.get("kakaoStep")
                .<KakaoPlaceDto, KakaoPlaceDto>chunk(batchConfiguration.getChunk())
                .reader(kakaoBatchReader)
                .writer(kakaoBatchWriter)
                .build();
    }

    @Bean
//    @StepScope
    public Step crawlingStep() {
        return stepBuilderFactory.get("crawlingStep")
                .<Place, CrawlingDto>chunk(batchConfiguration.getChunk())
                .reader(InstagramBatchReader())
                .processor(instagramBatchProcessor)
                .writer(instagramBatchWriter)
                .build();
    }


    @Bean
//    @StepScope
    public JdbcCursorItemReader<KakaoPlaceDto> KakaoBatchReader() {
        return new JdbcCursorItemReaderBuilder<KakaoPlaceDto>()
                .name("kakaoJdbcCursorItemReader")
                .fetchSize(batchConfiguration.getChunk())
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(KakaoPlaceDto.class))
                .sql("select category, placeName from KAKAO_DOCUMENT")
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Place> InstagramBatchReader() {
//        JdbcCursorItemReaderBuilder jdbcCursorItemReaderBuilder = new JdbcCursorItemReaderBuilder<Place>();
//        jdbcCursorItemReaderBuilder
//                .name("jdbcCursorInstagramItemReader")
//                .fetchSize(batchConfiguration.getChunk())
//                .dataSource(dataSource)
//                .rowMapper(new BeanPropertyRowMapper<>(Place.class))
//                .sql("select KAKAO_ID, PLACE_NAME from KAKAO_DOCUMENT")
//                .build();
//        return jdbcCursorItemReaderBuilder;

        return new JdbcCursorItemReaderBuilder<Place>()
            .name("jdbcCursorInstagramItemReader")
            .fetchSize(batchConfiguration.getChunk())
            .dataSource(dataSource)
            .rowMapper(new BeanPropertyRowMapper<>(Place.class))
            .sql("select KAKAO_ID, PLACE_NAME from KAKAO_DOCUMENT")
            .build();
    }

}
