package com.kiarie.votingsystemapi.blockchain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotingService {

    private final Web3Config web3Config;

    public TransactionReceipt sendVote(Long electionId, List<Long> candidateIds) throws Exception {
        System.out.println("Connecting to blockchain...");
        Web3j web3j = web3Config.web3j();
        Credentials credentials = web3Config.adminCredentials();

        VoteContract contract = VoteContract.load(
                web3Config.contractAddress(),
                web3j,
                credentials,
                new StaticGasProvider(
                        BigInteger.valueOf(2_000_000_000L), // gas price
                        BigInteger.valueOf(300_000)         // gas limit
                )
        );

        // Convert candidateIds to BigInteger
        List<BigInteger> candidateBigInts = candidateIds.stream()
                .map(BigInteger::valueOf)
                .toList();

        System.out.println("Storing vote on chain...");
        TransactionReceipt receipt = contract
                .storeVote(BigInteger.valueOf(electionId), candidateBigInts)
                .send();

        System.out.println("Transaction successful!");
        System.out.println("Tx Hash: " + receipt.getTransactionHash());
        System.out.println("Block Number: " + receipt.getBlockNumber());

        return receipt;
    }
}