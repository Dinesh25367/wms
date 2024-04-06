package com.newage.wms.service.impl;

import com.newage.wms.entity.PutAwayTaskDetails;
import com.newage.wms.repository.PutAwayTaskDetailsRepository;
import com.newage.wms.service.PutAwayTaskDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Log4j2
@Service
public class PutAwayTaskDetailsServiceImpl implements PutAwayTaskDetailService {

    @Autowired
    PutAwayTaskDetailsRepository putAwayTaskDetailsRepository;

    @Override
    public List<PutAwayTaskDetails> findAll() {
        return putAwayTaskDetailsRepository.findAll();
    }
}
