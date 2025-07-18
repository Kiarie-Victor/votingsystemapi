package com.kiarie.votingsystemapi.blockchain;

import java.util.List;
import lombok.Data;

@Data
public class VoteRequestdto {
    private List<Long> candidateIds;
}
