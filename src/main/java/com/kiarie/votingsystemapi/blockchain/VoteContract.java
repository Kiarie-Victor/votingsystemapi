package com.kiarie.votingsystemapi.blockchain;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class VoteContract extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_STOREVOTE = "storeVote";

    public static final String FUNC_GETVOTESCOUNT = "getVotesCount";

    public static final String FUNC_GETVOTE = "getVote";

    public static final Event VOTESTORED_EVENT = new Event("VoteStored", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
    ;

    @Deprecated
    protected VoteContract(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VoteContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VoteContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VoteContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<VoteStoredEventResponse> getVoteStoredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VOTESTORED_EVENT, transactionReceipt);
        ArrayList<VoteStoredEventResponse> responses = new ArrayList<VoteStoredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VoteStoredEventResponse typedResponse = new VoteStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.voteIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.electionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.candidateIds = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(1)).getNativeValueCopy();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VoteStoredEventResponse getVoteStoredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTESTORED_EVENT, log);
        VoteStoredEventResponse typedResponse = new VoteStoredEventResponse();
        typedResponse.log = log;
        typedResponse.voteIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.electionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.candidateIds = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(1)).getNativeValueCopy();
        return typedResponse;
    }

    public Flowable<VoteStoredEventResponse> voteStoredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVoteStoredEventFromLog(log));
    }

    public Flowable<VoteStoredEventResponse> voteStoredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTESTORED_EVENT));
        return voteStoredEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> storeVote(BigInteger electionId,
            List<BigInteger> candidateIds) {
        final Function function = new Function(
                FUNC_STOREVOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(electionId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(candidateIds, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getVotesCount() {
        final Function function = new Function(FUNC_GETVOTESCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple2<BigInteger, List<BigInteger>>> getVote(BigInteger index) {
        final Function function = new Function(FUNC_GETVOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<Tuple2<BigInteger, List<BigInteger>>>(function,
                new Callable<Tuple2<BigInteger, List<BigInteger>>>() {
                    @Override
                    public Tuple2<BigInteger, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, List<BigInteger>>(
                                (BigInteger) results.get(0).getValue(), 
                                convertToNative((List<Uint256>) results.get(1).getValue()));
                    }
                });
    }

    @Deprecated
    public static VoteContract load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new VoteContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VoteContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VoteContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VoteContract load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new VoteContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VoteContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VoteContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class VoteStoredEventResponse extends BaseEventResponse {
        public BigInteger voteIndex;

        public BigInteger electionId;

        public List<BigInteger> candidateIds;
    }
}
