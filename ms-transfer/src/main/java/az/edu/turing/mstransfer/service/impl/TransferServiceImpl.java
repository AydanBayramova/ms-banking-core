package az.edu.turing.mstransfer.service.impl;

import az.edu.turing.mstransfer.domain.repository.AccountRepository;
import az.edu.turing.mstransfer.model.mapper.TransferMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TransferServiceImpl {

    private final AccountRepository accountRepository;
    private final TransferMapper transferMapper;



}
