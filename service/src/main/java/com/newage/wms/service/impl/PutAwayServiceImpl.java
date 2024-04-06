package com.newage.wms.service.impl;

import com.newage.wms.entity.PutAwayTaskHeader;
import com.newage.wms.repository.PutAwayRepository;
import com.newage.wms.service.PutAwayService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PutAwayServiceImpl implements PutAwayService {

    @Autowired
    private PutAwayRepository putAwayRepository;


    @Override
    public PutAwayTaskHeader save(PutAwayTaskHeader putAwayTaskHeader) {
        return putAwayRepository.save(putAwayTaskHeader);
    }
}
