package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.service.CustomerMasterService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class TrnHeaderCustomsDocumentMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerMasterService customerMasterService;

    /*
     * Method to convert TrnHeaderCustomsDTO to TrnHeaderCustoms
     * @Return TrnHeaderCustoms
     */
    public TrnHeaderCustomsDocument convertRequestToEntity(TrnRequestDTO.TrnHeaderCustomsDocumentDTO trnHeaderCustomsDocumentDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY-EXIT - TrnHeaderCustomsDTO to TrnHeaderCustoms mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnHeaderCustomsDocument trnHeaderCustomsDocument = modelMapper.map(trnHeaderCustomsDocumentDTO, TrnHeaderCustomsDocument.class);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderCustomsDocument);
        if (trnHeaderCustomsDocumentDTO.getIor() != null){
            CustomerMaster ior = customerMasterService.getCustomerById(Long.parseLong(trnHeaderCustomsDocumentDTO.getIor().getId()));
            trnHeaderCustomsDocument.setIorMaster(ior);
        }
        if (trnHeaderCustomsDocumentDTO.getDocPassedCompany() != null){
            CustomerMaster docPassedCompany = customerMasterService.getCustomerById(Long.parseLong(trnHeaderCustomsDocumentDTO.getDocPassedCompany().getId()));
            trnHeaderCustomsDocument.setDocPassedCompanyMaster(docPassedCompany);
        }
        trnHeaderCustomsDocument.setNullInEmptyString();
        return trnHeaderCustomsDocument;
    }

    /*
     * Method to convert TrnHeaderCustomsDTO to TrnHeaderCustoms
     */
    public void convertUpdateRequestToEntity(TrnRequestDTO.TrnHeaderCustomsDocumentDTO trnHeaderCustomsDocumentDTO, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        modelMapper.map(trnHeaderCustomsDocumentDTO, trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument());
        modelMapper.map(dateAndTimeRequestDto, trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument());
        if (trnHeaderCustomsDocumentDTO.getIor() != null){
            CustomerMaster ior = customerMasterService.getCustomerById(Long.parseLong(trnHeaderCustomsDocumentDTO.getIor().getId()));
            trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument().setIorMaster(ior);
        }else {trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument().setIorMaster(null);}
        if (trnHeaderCustomsDocumentDTO.getDocPassedCompany() != null){
            CustomerMaster docPassedCompany = customerMasterService.getCustomerById(Long.parseLong(trnHeaderCustomsDocumentDTO.getDocPassedCompany().getId()));
            trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument().setDocPassedCompanyMaster(docPassedCompany);
        }else {trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument().setDocPassedCompanyMaster(null);}
        trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument().setNullInEmptyString();
    }

    public TrnResponseDTO.TrnHeaderCustomsDocumentDTO convertEntityToResponse(TrnHeaderAsn trnHeaderAsn) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnHeaderAsn.getTrnHeaderCustomsDocument() != null) {
            TrnResponseDTO.TrnHeaderCustomsDocumentDTO trnHeaderCustomsDocumentDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderCustomsDocument(), TrnResponseDTO.TrnHeaderCustomsDocumentDTO.class);
            if (trnHeaderAsn.getTrnHeaderCustomsDocument().getIorMaster() != null) {
                TrnResponseDTO.TrnHeaderCustomsDocumentDTO.CustomerDTO iorDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderCustomsDocument().getIorMaster(),
                        TrnResponseDTO.TrnHeaderCustomsDocumentDTO.CustomerDTO.class);
                trnHeaderCustomsDocumentDTO.setIor(iorDTO);
            }
            if (trnHeaderAsn.getTrnHeaderCustomsDocument().getDocPassedCompanyMaster() != null) {
                TrnResponseDTO.TrnHeaderCustomsDocumentDTO.CustomerDTO docPassedCompany = modelMapper.map(trnHeaderAsn.getTrnHeaderCustomsDocument().getDocPassedCompanyMaster(),
                        TrnResponseDTO.TrnHeaderCustomsDocumentDTO.CustomerDTO.class);
                trnHeaderCustomsDocumentDTO.setDocPassedCompany(docPassedCompany);
            }
            trnHeaderCustomsDocumentDTO.setCreatedUser(trnHeaderAsn.getTrnHeaderCustomsDocument().getCreatedBy());
            trnHeaderCustomsDocumentDTO.setUpdatedUser(trnHeaderAsn.getTrnHeaderCustomsDocument().getLastModifiedBy());
            trnHeaderCustomsDocumentDTO.setUpdatedDate(trnHeaderAsn.getTrnHeaderCustomsDocument().getLastModifiedDate());
            return trnHeaderCustomsDocumentDTO;
        }else {return null;}
    }


}
