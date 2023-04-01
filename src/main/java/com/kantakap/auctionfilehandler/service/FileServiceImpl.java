package com.kantakap.auctionfilehandler.service;

import com.kantakap.auctionfilehandler.gql.GraphQLRequest;
import com.kantakap.auctionfilehandler.gql.me.MeResponseWrapper;
import com.kantakap.auctionfilehandler.gql.processPlayersData.PlayersResponseWrapper;
import com.kantakap.auctionfilehandler.model.CSV;
import com.kantakap.auctionfilehandler.model.Player;
import com.kantakap.auctionfilehandler.repository.CSVRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final CSVRepository csvRepository;
    private final WebClient webClient;

    public Mono<CSV> save(String auctionId, String creatorId, MultipartFile file) {
        try {
            CSV csv = CSV.builder()
                    .creatorId(creatorId)
                    .auctionId(auctionId)
                    .csv(new Binary(BsonBinarySubType.BINARY, file.getBytes()))
                    .build();
            return csvRepository.save(csv);
        } catch (IOException e) {
            return Mono.error(new RuntimeException("Server failed to process file."));
        }
    }

    @Override
    public Mono<String> getCreatorId(String token) {
        var request = new GraphQLRequest("query { me { id } }", Collections.emptyMap());
        return me(request, token)
                .map(response -> response.getData().getMe().getId());
//        return sendGraphQLRequest(request, authorization)
//                .map(response -> response.getData().getMe().getId())
//                .doOnSuccess(creatorId -> fileService.save(auctionId, creatorId, file));
    }

    @Override
    public Flux<Player> processPlayersData(String auctionId) {
        var request = new GraphQLRequest("query { processPlayersData(auctionId: \"" + auctionId + "\") { username } }", Collections.emptyMap());
        return processPlayersData(request, "Bearer " + "token")
                .map(response -> response.getData().getProcessPlayersData())
                .flatMapMany(Flux::fromIterable);
    }

    private Mono<MeResponseWrapper> me(GraphQLRequest request, String authorization) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorization)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request.toString())
                .retrieve()
                .bodyToMono(MeResponseWrapper.class)
                .onErrorResume(throwable -> Mono.error(new RuntimeException("Error sending GraphQL request.")));
    }

    private Mono<PlayersResponseWrapper> processPlayersData(GraphQLRequest request, String authorization) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorization)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request.toString())
                .retrieve()
                .bodyToMono(PlayersResponseWrapper.class)
                .onErrorResume(throwable -> Mono.error(new RuntimeException("Error sending GraphQL request.")));
    }

}
