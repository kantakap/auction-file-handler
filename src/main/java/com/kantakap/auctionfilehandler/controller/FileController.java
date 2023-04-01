package com.kantakap.auctionfilehandler.controller;

import com.kantakap.auctionfilehandler.model.Player;
import com.kantakap.auctionfilehandler.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data", "application/x-www-form-urlencoded", "text/tab-separated-values"})
    public Flux<Player> uploadCsvFile(@RequestHeader("Authorization") String authorization, @RequestParam String auctionId, @RequestPart MultipartFile file) {
        return fileService.getCreatorId(authorization)
                .flatMap(id -> fileService.save(auctionId, id, file))
                .flatMapMany(csv -> fileService.processPlayersData(auctionId));
    }
}
