package com.newage.wms.service.impl;

import com.newage.wms.entity.GrnDetail;
import com.newage.wms.entity.GrnLotDetail;
import com.newage.wms.repository.GrnLotDetailRepository;
import com.newage.wms.service.GrnLotDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class GrnLotDetailServiceImpl implements GrnLotDetailService {

    @Autowired
    private GrnLotDetailRepository grnLotDetailRepository;

    @Override
    public void deleteAllIterables(Iterable<GrnLotDetail> grnLotDetailIterable) {
        grnLotDetailRepository.deleteAll(grnLotDetailIterable);
    }

}
