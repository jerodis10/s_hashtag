package com.s_hashtag.instagram.crawler;

import com.google.gson.JsonElement;
import lombok.NoArgsConstructor;

@NoArgsConstructor
class JsonExplorer {
    public static final String PARENT_KEY = "node";

    public static String findByKey(JsonElement edge, String key) {
        return edge.getAsJsonObject()
                .get(PARENT_KEY).getAsJsonObject()
                .get(key).getAsString();
    }
}
