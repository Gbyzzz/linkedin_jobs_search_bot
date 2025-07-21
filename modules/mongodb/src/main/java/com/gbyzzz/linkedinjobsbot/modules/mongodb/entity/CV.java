package com.gbyzzz.linkedinjobsbot.modules.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("cv")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CV {
    @Id
    private ObjectId id;
    private Long userId;
    private String description;
}
