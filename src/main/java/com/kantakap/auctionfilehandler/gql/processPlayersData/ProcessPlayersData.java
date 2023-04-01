package com.kantakap.auctionfilehandler.gql.processPlayersData;

import com.kantakap.auctionfilehandler.model.Player;
import lombok.Data;

import java.util.List;

@Data
public class ProcessPlayersData {
    private List<Player> processPlayersData;
}
