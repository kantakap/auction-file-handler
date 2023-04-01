package com.kantakap.auctionfilehandler.controller;

import com.kantakap.auctionfilehandler.model.Player;
import com.kantakap.auctionfilehandler.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = {"*/*"})
    public Flux<Player> uploadCsvFile(@RequestHeader("Authorization") String authorization, @RequestParam String auctionId, @RequestPart(value = "file", required = false) FilePart file) {
        try {
            File f = fileService.convertFilePartToFile(file);
            return fileService.getCreatorId(authorization)
                    .flatMap(id -> fileService.save(auctionId, id, f))
                    .flatMapMany(csv -> fileService.processPlayersData(auctionId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
