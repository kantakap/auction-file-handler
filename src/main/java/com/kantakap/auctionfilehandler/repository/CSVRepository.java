package com.kantakap.auctionfilehandler.repository;

import com.kantakap.auctionfilehandler.model.CSV;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CSVRepository extends ReactiveMongoRepository<CSV, String> {
}
