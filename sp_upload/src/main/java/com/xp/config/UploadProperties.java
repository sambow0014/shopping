package com.xp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
@Data
@ConfigurationProperties(prefix = "sp.upload")
public class UploadProperties {
    private String baseUrl;
    private List<String> allowTypes;
}
