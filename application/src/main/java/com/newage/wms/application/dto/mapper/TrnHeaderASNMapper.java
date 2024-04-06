package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.GrnHeaderRequestDTO;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.service.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class TrnHeaderASNMapper {

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
    private UserWareHouseService userWareHouseService;

    @Autowired
    private GrnHeaderService grnHeaderService;

    /*
     * Method to convert TrnHeaderAsnDTO to TrnHeaderAsn
     * @Return TrnHeaderAsn
     */
    public TrnHeaderAsn convertRequestToEntity(TrnRequestDTO trnRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY-EXIT - TrnHeaderAsnDTO to TrnHeaderAsn mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnHeaderAsn trnHeaderAsn = modelMapper.map(trnRequestDTO.getTrnHeaderAsn(), TrnHeaderAsn.class);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderAsn);
        trnHeaderAsn.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(trnRequestDTO.getBranchId())));
        trnHeaderAsn.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(trnRequestDTO.getCompanyId())));
        trnHeaderAsn.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(trnRequestDTO.getGroupCompanyId())));
        trnHeaderAsn.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getWareHouse().getId())));
        trnHeaderAsn.setCustomerMaster(customerMasterService.getCustomerById(Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getCustomer().getId())));
        trnHeaderAsn.setMovementTypeMaster(movementTypeMasterService.getById(Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getMovementType().getId())));
        if (trnRequestDTO.getTrnHeaderAsn().getCustomerOrderNo() != null){
            trnHeaderAsn.setCustomerOrderNo(trnRequestDTO.getTrnHeaderAsn().getCustomerOrderNo().toUpperCase());
        }
        if (trnRequestDTO.getTrnHeaderAsn().getCustomerInvoiceNo() != null){
            trnHeaderAsn.setCustomerInvoiceNo(trnRequestDTO.getTrnHeaderAsn().getCustomerInvoiceNo().toUpperCase());

        }
        trnHeaderAsn.setCreatedDate(trnRequestDTO.getTrnHeaderAsn().getCreatedDate());
        trnHeaderAsn.setNullInEmptyString();
        return trnHeaderAsn;
    }

    /*
     * Method to convert TrnHeaderAsnDTO to TrnHeaderAsn
     */
    public void convertUpdateRequestToEntity(TrnRequestDTO trnRequestDTO, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        modelMapper.map(trnRequestDTO.getTrnHeaderAsn(), trnHeaderAsnToBeUpdated);
        modelMapper.map(dateAndTimeRequestDto, trnHeaderAsnToBeUpdated);
        trnHeaderAsnToBeUpdated.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(trnRequestDTO.getBranchId())));
        trnHeaderAsnToBeUpdated.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(trnRequestDTO.getCompanyId())));
        trnHeaderAsnToBeUpdated.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(trnRequestDTO.getGroupCompanyId())));
        if (trnRequestDTO.getTrnHeaderAsn().getMovementType() != null){
            trnHeaderAsnToBeUpdated.setMovementTypeMaster(
                    movementTypeMasterService.getById(Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getMovementType().getId()))
            );
        }else {
            trnHeaderAsnToBeUpdated.setMovementTypeMaster(null);}
        if (trnRequestDTO.getTrnHeaderAsn().getCustomerInvoiceNo() != null) {
            trnHeaderAsnToBeUpdated.setCustomerInvoiceNo(trnRequestDTO.getTrnHeaderAsn().getCustomerInvoiceNo().toUpperCase()
            );
        }
        if (trnRequestDTO.getTrnHeaderAsn().getCustomerOrderNo() != null){
            trnHeaderAsnToBeUpdated.setCustomerOrderNo(trnRequestDTO.getTrnHeaderAsn().getCustomerOrderNo().toUpperCase()
            );
        }
        trnHeaderAsnToBeUpdated.setNullInEmptyString();
    }

    /*
     * Method to convert TrnHeaderAsn to TrnHeaderAsn Response
     * @Return TrnResponseDTO.TrnHeaderAsnDTO
     */
    public TrnResponseDTO.TrnHeaderAsnDTO convertEntityToResponse(TrnHeaderAsn trnHeaderAsn) {
        log.info("ENTRY-EXIT - TrnHeaderAsn to TrnHeaderAsn Response mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnResponseDTO.TrnHeaderAsnDTO trnHeaderAsnDTO = modelMapper.map(trnHeaderAsn, TrnResponseDTO.TrnHeaderAsnDTO.class);
        if (trnHeaderAsn.getBranchMaster() != null){
            TrnResponseDTO.TrnHeaderAsnDTO.BranchDTO branchDTO = modelMapper.map(trnHeaderAsn.getBranchMaster(),TrnResponseDTO.TrnHeaderAsnDTO.BranchDTO.class);
            trnHeaderAsnDTO.setBranch(branchDTO);
        }
        if (trnHeaderAsn.getCompanyMaster() != null){
            TrnResponseDTO.TrnHeaderAsnDTO.CompanyDTO companyDTO = modelMapper.map(trnHeaderAsn.getCompanyMaster(),TrnResponseDTO.TrnHeaderAsnDTO.CompanyDTO.class);
            trnHeaderAsnDTO.setCompany(companyDTO);
        }
        if (trnHeaderAsn.getGroupCompanyMaster() != null){
            TrnResponseDTO.TrnHeaderAsnDTO.GroupCompanyDTO groupCompanyDTO = modelMapper.map(trnHeaderAsn.getGroupCompanyMaster(),TrnResponseDTO.TrnHeaderAsnDTO.GroupCompanyDTO.class);
            trnHeaderAsnDTO.setGroupCompany(groupCompanyDTO);
        }
        if (trnHeaderAsn.getWareHouseMaster() != null){
            TrnResponseDTO.TrnHeaderAsnDTO.WareHouseDTO wareHouseDTO = modelMapper.map(trnHeaderAsn.getWareHouseMaster(),TrnResponseDTO.TrnHeaderAsnDTO.WareHouseDTO.class);
            trnHeaderAsnDTO.setWareHouse(wareHouseDTO);
        }
        if (trnHeaderAsn.getCustomerMaster() != null){
            TrnResponseDTO.TrnHeaderAsnDTO.CustomerDTO customerDTO = modelMapper.map(trnHeaderAsn.getCustomerMaster(),TrnResponseDTO.TrnHeaderAsnDTO.CustomerDTO.class);
            trnHeaderAsnDTO.setCustomer(customerDTO);
        }
        if (trnHeaderAsn.getMovementTypeMaster() != null){
            TrnResponseDTO.TrnHeaderAsnDTO.MovementTypeDTO movementTypeDTO = modelMapper.map(trnHeaderAsn.getMovementTypeMaster(),TrnResponseDTO.TrnHeaderAsnDTO.MovementTypeDTO.class);
            trnHeaderAsnDTO.setMovementType(movementTypeDTO);
        }
        trnHeaderAsnDTO.setCreatedUser(trnHeaderAsn.getCreatedBy());
        trnHeaderAsnDTO.setUpdatedUser(trnHeaderAsn.getLastModifiedBy());
        trnHeaderAsnDTO.setUpdatedDate(trnHeaderAsn.getLastModifiedDate());
        return trnHeaderAsnDTO;
    }

    public Iterable<GrnHeaderRequestDTO.TrnHeaderAsnDTO.TransactionDTO> convertEntityIterableToDtoIterable(Iterable<TrnHeaderAsn> trnHeaderAsnIterable) {
        return StreamSupport.stream(trnHeaderAsnIterable.spliterator(), false)
                .map(this::convertEntityToAutoCompleteResponseDTO)
                .collect(Collectors.toList());
    }

    private GrnHeaderRequestDTO.TrnHeaderAsnDTO.TransactionDTO convertEntityToAutoCompleteResponseDTO(TrnHeaderAsn trnHeaderAsn) {
        GrnHeaderRequestDTO.TrnHeaderAsnDTO.TransactionDTO transactionDTO = modelMapper.map(trnHeaderAsn,GrnHeaderRequestDTO.TrnHeaderAsnDTO.TransactionDTO.class);
        return transactionDTO;
    }

    public TrnHeaderAsn convertGrnRequestToTrnEntity(GrnHeaderRequestDTO grnHeaderRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnHeaderAsn trnHeaderAsn = modelMapper.map(grnHeaderRequestDTO.getTrnHeaderAsn(), TrnHeaderAsn.class);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderAsn);
        trnHeaderAsn.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(grnHeaderRequestDTO.getBranchId())));
        trnHeaderAsn.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(grnHeaderRequestDTO.getCompanyId())));
        trnHeaderAsn.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(grnHeaderRequestDTO.getGroupCompanyId())));
        trnHeaderAsn.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(grnHeaderRequestDTO.getTrnHeaderAsn().getWarehouse().getId())));
        trnHeaderAsn.setCustomerMaster(customerMasterService.getCustomerById(Long.parseLong(grnHeaderRequestDTO.getTrnHeaderAsn().getCustomer().getId())));
        trnHeaderAsn.setMovementTypeMaster(movementTypeMasterService.getByCode());
        trnHeaderAsn.setExpectedReceivingDate(new Date());
        trnHeaderAsn.setIsEdiTransaction("No");
        trnHeaderAsn.setNullInEmptyString();
        return trnHeaderAsn;
    }

    public void setCancelUpdateDeleteInformation(TrnHeaderAsn trnHeaderAsn,TrnResponseDTO trnResponseDTO, Long userId) {
        boolean hasCancelUserRights = userWareHouseService.userIdCheckForAsn(userId);
        boolean isCancellableFromGrn =grnHeaderService.checkIfAsnCanBeCancelledFromGrn(trnHeaderAsn.getId());
        boolean isUncompletedGrnNotPresent = grnHeaderService.isUnCompletedGrnPresent(trnHeaderAsn.getId());
        if (hasCancelUserRights && isCancellableFromGrn){
            trnResponseDTO.setIsCancellable("Yes");
        }else {
            trnResponseDTO.setIsCancellable("No");
        }
        boolean isHeaderEditable = grnHeaderService.checkIfAsnHeaderCanBeEditableFromGrn(trnHeaderAsn.getId());
        if (isHeaderEditable){
            trnResponseDTO.setIsHeaderEditable("No");
        }else {
            trnResponseDTO.setIsHeaderEditable("Yes");
        }
        if (trnHeaderAsn.getTransactionStatus().toLowerCase().contains("received") || trnHeaderAsn.getTransactionStatus().toLowerCase().contains("booked")){
            trnResponseDTO.setIsRowAddable("Yes");
        }else {
            trnResponseDTO.setIsRowAddable("No");
        }
        if (trnHeaderAsn.getTransactionStatus().equalsIgnoreCase("cancelled")){
            trnResponseDTO.setIsCancellable("No");

        }
        if (trnHeaderAsn.getTransactionStatus().equalsIgnoreCase("booked")
                || isUncompletedGrnNotPresent) {
            trnResponseDTO.setAllowGrn("Yes");
        }else {
            trnResponseDTO.setAllowGrn("No");
        }
        if (trnHeaderAsn.getTransactionStatus().equalsIgnoreCase("generated")
                || trnHeaderAsn.getTransactionStatus().equalsIgnoreCase("cancelled")){
            trnResponseDTO.setAllowGrn("No");
        }
    }

}
