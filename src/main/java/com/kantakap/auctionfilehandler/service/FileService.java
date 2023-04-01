package com.kantakap.auctionfilehandler.service;

import com.kantakap.auctionfilehandler.model.CSV;
import com.kantakap.auctionfilehandler.model.Player;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;

@Service
public interface FileService {
    Mono<CSV> save(String auctionId, String creatorId, File file);
    Mono<String> getCreatorId(String token);
    Flux<Player> processPlayersData(String auctionId);
    File convertFilePartToFile(FilePart filePart) throws IOException;
}
