package com.kiarie.votingsystemapi.blockchain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3Config {

    @Value("${blockchain.ganache.url}")
    private String rpcUrl;

    @Value("${blockchain.admin.privateKey}")
    private String privateKey;

    @Value("${blockchain.contract.address}")
    private String contractAddress;

    public Web3j web3j() {
        return Web3j.build(new HttpService(rpcUrl));
    }

    public Credentials adminCredentials() {
        return Credentials.create(privateKey);
    }

    public String contractAddress() {
        return contractAddress;
    }
}
