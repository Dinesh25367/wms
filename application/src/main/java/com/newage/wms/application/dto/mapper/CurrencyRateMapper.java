package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.CurrencyRateRequestDTO;
import com.newage.wms.application.dto.responsedto.CurrencyRateResponseDTO;
import com.newage.wms.entity.CompanyMaster;
import com.newage.wms.entity.CurrencyMaster;
import com.newage.wms.entity.CurrencyRateMaster;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.CurrencyMasterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyRateMapper {

    @Autowired
    CurrencyMasterService currencyMasterService;

    @Autowired
    CompanyMasterService companyMasterService;

    @Autowired
    private ModelMapper modelMapper;

    public CurrencyRateResponseDTO convertEntityToResponseDTO(CurrencyRateMaster currencyRateMaster) {
        CurrencyRateResponseDTO currencyRateResponseDTO = modelMapper.map(currencyRateMaster, CurrencyRateResponseDTO.class);
        currencyRateResponseDTO.setId(currencyRateResponseDTO.getId());
        if (currencyRateMaster.getCompany() != null) {
            currencyRateResponseDTO.setCompany(new CurrencyRateResponseDTO.CompanyMasterDTO(currencyRateMaster.getCompany().getId(),
                    currencyRateMaster.getCompany().getCode(),
                    currencyRateMaster.getCompany().getName()));
        }
        if (currencyRateMaster.getAccountingCurrency() != null) {
            currencyRateResponseDTO.setAccountingCurrency(new CurrencyRateResponseDTO.CurrencyMasterDTO(currencyRateMaster.getAccountingCurrency().getId(),
                    currencyRateMaster.getAccountingCurrency().getCode(),
                    currencyRateMaster.getAccountingCurrency().getName()));
        }
        if (currencyRateMaster.getToCurrency() != null) {
            currencyRateResponseDTO.setToCurrency(new CurrencyRateResponseDTO.CurrencyMasterDTO(currencyRateMaster.getToCurrency().getId(),
                    currencyRateMaster.getToCurrency().getCode(),
                    currencyRateMaster.getToCurrency().getName()));
        }
        return currencyRateResponseDTO;
    }

    public CurrencyRateMaster convertCreateRequestToEntity(CurrencyRateRequestDTO currencyRateRequestDTO) {
        CurrencyRateMaster currencyRateMaster = modelMapper.map(currencyRateRequestDTO, CurrencyRateMaster.class);
        if (currencyRateRequestDTO.getAccountingCurrencyId() != null) {
            CurrencyMaster currencyMaster = currencyMasterService.getCurrencyById(currencyRateRequestDTO.getAccountingCurrencyId());
            currencyRateMaster.setAccountingCurrency(currencyMaster);
        }
        if (currencyRateRequestDTO.getCompanyId() != null) {
            CompanyMaster companyMaster = companyMasterService.getCompanyById(currencyRateRequestDTO.getCompanyId());
            currencyRateMaster.setCompany(companyMaster);
        }
        if (currencyRateRequestDTO.getToCurrencyId() != null) {
            CurrencyMaster currencyMaster = currencyMasterService.getCurrencyById(currencyRateRequestDTO.getToCurrencyId());
            currencyRateMaster.setToCurrency(currencyMaster);
        }
        return currencyRateMaster;
    }

    public void convertUpdateRequestToEntity(CurrencyRateRequestDTO currencyRateRequestDTO, CurrencyRateMaster currencyRateMaster) {
        if (currencyRateRequestDTO.getAccountingCurrencyId() != null) {
            CurrencyMaster currencyMaster = currencyMasterService.getCurrencyById(currencyRateRequestDTO.getAccountingCurrencyId());
            currencyRateMaster.setAccountingCurrency(currencyMaster);
        }
        if (currencyRateRequestDTO.getCompanyId() != null) {
            CompanyMaster companyMaster = companyMasterService.getCompanyById(currencyRateRequestDTO.getCompanyId());
            currencyRateMaster.setCompany(companyMaster);
        }
        if (currencyRateRequestDTO.getToCurrencyId() != null) {
            CurrencyMaster currencyMaster = currencyMasterService.getCurrencyById(currencyRateRequestDTO.getToCurrencyId());
            currencyRateMaster.setToCurrency(currencyMaster);
        }
        modelMapper.map(currencyRateRequestDTO, currencyRateMaster);
    }

    public Page<CurrencyRateResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<CurrencyRateMaster> currencyRateMasters) {
        List<CurrencyRateResponseDTO> dtos = new ArrayList<>();
        currencyRateMasters.forEach(e -> dtos.add(convertEntityToResponseDTO(e)));
        return new PageImpl<>(dtos, pageRequest, currencyRateMasters.getTotalElements());
    }

}
