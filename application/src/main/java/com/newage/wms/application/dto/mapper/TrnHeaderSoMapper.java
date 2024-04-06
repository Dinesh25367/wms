package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.requestdto.TrnSoRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnSoResponseDTO;
import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.entity.TrnHeaderFreight;
import com.newage.wms.entity.TrnHeaderSo;
import com.newage.wms.service.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class TrnHeaderSoMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CurrencyMasterService currencyMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private CustomerMasterService customerMasterService;

    @Autowired
    private MovementTypeMasterService movementTypeMasterService;

    @Autowired
    private TrnHeaderAsnService trnHeaderAsnService;

    /*
     * Method to convert TrnHeaderAsnDTO to TrnHeaderAsn
     * @Return TrnHeaderAsn
     */
    public TrnHeaderAsn convertRequestToEntity(TrnSoRequestDTO trnSoRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY-EXIT - TrnHeaderAsnDTO to TrnHeaderAsn mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnHeaderAsn trnHeaderAsn = modelMapper.map(trnSoRequestDTO.getTrnHeaderSo(), TrnHeaderAsn.class);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderAsn);
        trnHeaderAsn.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(trnSoRequestDTO.getBranchId())));
        trnHeaderAsn.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(trnSoRequestDTO.getCompanyId())));
        trnHeaderAsn.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(trnSoRequestDTO.getGroupCompanyId())));
        trnHeaderAsn.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(trnSoRequestDTO.getTrnHeaderSo().getWareHouse().getId())));
        trnHeaderAsn.setCustomerMaster(customerMasterService.getCustomerById(Long.parseLong(trnSoRequestDTO.getTrnHeaderSo().getCustomer().getId())));
        trnHeaderAsn.setMovementTypeMaster(movementTypeMasterService.getById(Long.parseLong(trnSoRequestDTO.getTrnHeaderSo().getMovementType().getId())));
        if (trnSoRequestDTO.getTrnHeaderSo().getTransaction() != null) {
            trnHeaderAsn.setTransactionId(Long.parseLong(trnSoRequestDTO.getTrnHeaderSo().getTransaction().getId()));
        }
        trnHeaderAsn.setNullInEmptyString();
        return trnHeaderAsn;
    }

    /*
     * Method to convert TrnHeaderAsnDTO to TrnHeaderAsn
     */
    public void convertUpdateRequestToEntity(TrnSoRequestDTO trnSoRequestDTO, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        modelMapper.map(trnSoRequestDTO.getTrnHeaderSo(), trnHeaderAsnToBeUpdated);
        modelMapper.map(dateAndTimeRequestDto, trnHeaderAsnToBeUpdated);
        trnHeaderAsnToBeUpdated.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(trnSoRequestDTO.getBranchId())));
        trnHeaderAsnToBeUpdated.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(trnSoRequestDTO.getCompanyId())));
        trnHeaderAsnToBeUpdated.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(trnSoRequestDTO.getGroupCompanyId())));
        if (trnSoRequestDTO.getTrnHeaderSo().getWareHouse() != null){
            trnHeaderAsnToBeUpdated.setWareHouseMaster(
                    wareHouseService.getWareHouseById(Long.parseLong(trnSoRequestDTO.getTrnHeaderSo().getWareHouse().getId()))
            );
        }else {
            trnHeaderAsnToBeUpdated.setWareHouseMaster(null);}
        if (trnSoRequestDTO.getTrnHeaderSo().getCustomer() != null){
            trnHeaderAsnToBeUpdated.setCustomerMaster(
                    customerMasterService.getCustomerById(Long.parseLong(trnSoRequestDTO.getTrnHeaderSo().getCustomer().getId()))
            );
        }else {
            trnHeaderAsnToBeUpdated.setMovementTypeMaster(null);}
        if (trnSoRequestDTO.getTrnHeaderSo().getMovementType() != null){
            trnHeaderAsnToBeUpdated.setMovementTypeMaster(
                    movementTypeMasterService.getById(Long.parseLong(trnSoRequestDTO.getTrnHeaderSo().getMovementType().getId()))
            );
        }else {
            trnHeaderAsnToBeUpdated.setMovementTypeMaster(null);}
        if (trnSoRequestDTO.getTrnHeaderSo().getTransaction() != null) {
            trnHeaderAsnToBeUpdated.setTransactionId(Long.parseLong(trnSoRequestDTO.getTrnHeaderSo().getTransaction().getId()));
        }
        trnHeaderAsnToBeUpdated.setNullInEmptyString();
    }

    /*
     * Method to convert TrnHeaderAsn to TrnHeaderAsn Response
     * @Return TrnResponseDTO.TrnHeaderAsnDTO
     */
    public TrnSoResponseDTO.TrnHeaderSoDTO convertEntityToResponse(TrnHeaderAsn trnHeaderAsn) {
        log.info("ENTRY-EXIT - TrnHeaderAsn to TrnHeaderAsn Response mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnSoResponseDTO.TrnHeaderSoDTO trnHeaderSoDTO = modelMapper.map(trnHeaderAsn, TrnSoResponseDTO.TrnHeaderSoDTO.class);
        if (trnHeaderAsn.getBranchMaster() != null){
            TrnSoResponseDTO.TrnHeaderSoDTO.BranchDTO branchDTO = modelMapper.map(trnHeaderAsn.getBranchMaster(),TrnSoResponseDTO.TrnHeaderSoDTO.BranchDTO.class);
            trnHeaderSoDTO.setBranch(branchDTO);
        }
        if (trnHeaderAsn.getCompanyMaster() != null){
            TrnSoResponseDTO.TrnHeaderSoDTO.CompanyDTO companyDTO = modelMapper.map(trnHeaderAsn.getCompanyMaster(),TrnSoResponseDTO.TrnHeaderSoDTO.CompanyDTO.class);
            trnHeaderSoDTO.setCompany(companyDTO);
        }
        if (trnHeaderAsn.getGroupCompanyMaster() != null){
            TrnSoResponseDTO.TrnHeaderSoDTO.GroupCompanyDTO groupCompanyDTO = modelMapper.map(trnHeaderAsn.getGroupCompanyMaster(),TrnSoResponseDTO.TrnHeaderSoDTO.GroupCompanyDTO.class);
            trnHeaderSoDTO.setGroupCompany(groupCompanyDTO);
        }
        if (trnHeaderAsn.getWareHouseMaster() != null){
            TrnSoResponseDTO.TrnHeaderSoDTO.WareHouseDTO wareHouseDTO = modelMapper.map(trnHeaderAsn.getWareHouseMaster(),TrnSoResponseDTO.TrnHeaderSoDTO.WareHouseDTO.class);
            trnHeaderSoDTO.setWareHouse(wareHouseDTO);
        }
        if (trnHeaderAsn.getCustomerMaster() != null){
            TrnSoResponseDTO.TrnHeaderSoDTO.CustomerDTO customerDTO = modelMapper.map(trnHeaderAsn.getCustomerMaster(),TrnSoResponseDTO.TrnHeaderSoDTO.CustomerDTO.class);
            trnHeaderSoDTO.setCustomer(customerDTO);
        }
        if (trnHeaderAsn.getMovementTypeMaster() != null){
            TrnSoResponseDTO.TrnHeaderSoDTO.MovementTypeDTO movementTypeDTO = modelMapper.map(trnHeaderAsn.getMovementTypeMaster(),TrnSoResponseDTO.TrnHeaderSoDTO.MovementTypeDTO.class);
            trnHeaderSoDTO.setMovementType(movementTypeDTO);
        }
        if (trnHeaderAsn.getTransactionId() != null){
            TrnHeaderAsn trnHeaderAsn1 = trnHeaderAsnService.findById(trnHeaderAsn.getTransactionId());
            TrnSoResponseDTO.TrnHeaderSoDTO.TransactionDTO transactionDTO = modelMapper.map(trnHeaderAsn1,TrnSoResponseDTO.TrnHeaderSoDTO.TransactionDTO.class);
            trnHeaderSoDTO.setTransaction(transactionDTO);
        }
        trnHeaderSoDTO.setCreatedUser(trnHeaderAsn.getCreatedBy());
        trnHeaderSoDTO.setUpdatedUser(trnHeaderAsn.getLastModifiedBy());
        trnHeaderSoDTO.setUpdatedDate(trnHeaderAsn.getLastModifiedDate());
        return trnHeaderSoDTO;
    }

}
