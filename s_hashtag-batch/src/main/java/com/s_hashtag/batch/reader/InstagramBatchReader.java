package com.s_hashtag.batch.reader;

import com.s_hashtag.common.place.domain.model.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InstagramBatchReader implements ItemReader<Place> {

    @Override
    public Place read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
