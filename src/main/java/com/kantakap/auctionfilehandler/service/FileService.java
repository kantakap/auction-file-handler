package com.kantakap.auctionfilehandler.service;

import com.kantakap.auctionfilehandler.model.CSV;
import com.kantakap.auctionfilehandler.model.Player;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface FileService {
    Mono<CSV> save(String auctionId, String creatorId, MultipartFile file);
    Mono<String> getCreatorId(String token);
    Flux<Player> processPlayersData(String auctionId);
}
