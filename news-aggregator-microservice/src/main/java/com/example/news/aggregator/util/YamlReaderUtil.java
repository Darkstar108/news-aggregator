package com.example.news.aggregator.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.news.aggregator.exception.ServerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YamlReaderUtil {
    public static Map<String, String> getSourceCredibilityMap() {
        try {
            Yaml yaml = new Yaml();
          InputStream inputStream =
                  YamlReaderUtil.class
                          .getClassLoader()
                          .getResourceAsStream(
                                  "source-credibility.yaml"
                          );

            return yaml.load(inputStream);
        } catch (Exception e) {
            log.atError().setCause(e).log("Error reading SourceCredibility yaml");
            throw new ServerException(e.getMessage());
        }
    }
}
