package com.kantakap.auctionfilehandler.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class CSV {
    @Id
    private String id;
    private String creatorId;
    private String auctionId;
    private Binary csv;
}
