package com.s_hashtag.batch.config;

import com.s_hashtag.batch.processor.InstagramBatchProcessor;
import com.s_hashtag.batch.reader.KakaoBatchReader;
import com.s_hashtag.batch.writer.InstagramBatchWriter;
import com.s_hashtag.common.place.domain.model.Place;
import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
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
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class InstagramCrawlingJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final KakaoBatchReader kakaoBatchReader;

    private final InstagramBatchProcessor instagramBatchProcessor;
    private final InstagramBatchWriter instagramBatchWriter;
    private final BatchConfiguration batchConfiguration;
    private final DataSource dataSource;

    @Bean
    public Job kakaoJob() {
        return jobBuilderFactory.get("kakaoJob")
                .start(kakaoStep())
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
//    public Job kakaoCrawlingJob() {
//        return jobBuilderFactory.get("kakaoCrawlingJob")
//                .start(kakaoStep())
//                .next(crawlingStep())
//                .build();
//    }

    @Bean
    public Step kakaoStep() {
        return stepBuilderFactory.get("kakaoStep")
                .<List<KakaoPlaceDto>, KakaoPlaceDto>chunk(batchConfiguration.getChunk())
                .reader(kakaoBatchReader)
                .processor(instagramBatchProcessor)
                .writer(instagramBatchWriter)
                .build();
    }

    @Bean
    public Step crawlingStep() {
        return stepBuilderFactory.get("crawlingStep")
                .<Place, CrawlingDto>chunk(batchConfiguration.getChunk())
                .reader(InstagramBatchReader())
                .processor(instagramBatchProcessor)
                .writer(instagramBatchWriter)
                .build();
    }


//    @Bean
//    public JdbcCursorItemReader<KakaoPlaceDto> KakaoBatchReader() {
//        return new JdbcCursorItemReaderBuilder<KakaoPlaceDto>()
//                .name("kakaoJdbcCursorItemReader")
//                .fetchSize(batchConfiguration.getChunk())
//                .dataSource(dataSource)
//                .rowMapper(new BeanPropertyRowMapper<>(KakaoPlaceDto.class))
//                .sql("select category, placeName from ")
//                .build();
//    }

    @Bean
    public JdbcCursorItemReader<Place> InstagramBatchReader() {
        return new JdbcCursorItemReaderBuilder<Place>()
            .name("jdbcCursorItemReader")
            .fetchSize(batchConfiguration.getChunk())
            .dataSource(dataSource)
            .rowMapper(new BeanPropertyRowMapper<>(Place.class))
            .sql("select category, placeName from ")
            .build();
    }




//    @Bean
//    public Job simpleJob() {
//        return jobBuilderFactory.get("simpleJob")
//                .start(simpleStep1())
//                .build();
//    }

//    @Bean
//    public Step simpleStep1() {
//        return stepBuilderFactory.get("simpleStep1")
//                .tasklet((contribution, chunkContext) -> {
//                    log.info(">>>>> This is Step1");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }


}
