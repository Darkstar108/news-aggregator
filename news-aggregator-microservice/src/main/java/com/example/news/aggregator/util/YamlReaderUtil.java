package com.example.news.aggregator.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YamlReaderUtil {

    public static final String SOURCE_CREDIBILITY_YAML = "src/main/resources/source-credibility.yaml";

    public static Map<String, String> getSourceCredibilityMap() {
        Map<String, String> sourceCredibilityMap = null;
        try {
            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream(SOURCE_CREDIBILITY_YAML);

            // Map the root structure of the YAML into a generic Java Map
            sourceCredibilityMap = yaml.load(inputStream);

        } catch (FileNotFoundException e) {
            log.atError().setCause(e).log("Error reading SourceCredibility yaml");
        }
        return sourceCredibilityMap;
    }
}
