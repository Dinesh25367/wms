package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.GrnHeaderRequestDTO;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.responsedto.CustomerAddressResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.CustomerMaster;
import com.newage.wms.entity.PartyAddressDetail;
import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.entity.TrnHeaderParty;
import com.newage.wms.service.CityMasterService;
import com.newage.wms.service.CountryMasterService;
import com.newage.wms.service.CustomerMasterService;
import com.newage.wms.service.StateMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import static com.newage.wms.service.exception.ServiceErrors.*;
import static com.newage.wms.service.exception.ServiceErrors.TRANSPORTER_MUST_NOT_BE_NULL;

@Log4j2
@Component
public class TrnHeaderPartyMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerMasterService customerMasterService;

    @Autowired
    private CityMasterService cityMasterService;

    @Autowired
    private CountryMasterService countryMasterService;

    @Autowired
    private StateMasterService stateMasterService;

    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    /*
     * Method to convert TrnHeaderPartyDTO to TrnHeaderParty
     * @Return TrnHeaderParty
     */
    public TrnHeaderParty convertRequestToEntity(List<TrnRequestDTO.PartyCustomerDTO> party, DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag) {
        log.info("ENTRY-EXIT - TrnHeaderPartyDTO to TrnHeaderParty mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnHeaderParty trnHeaderParty = new TrnHeaderParty();
        modelMapper.map(dateAndTimeRequestDto,trnHeaderParty);
        if (party == null || party.size() != 4){
            throw new ServiceException(ServiceErrors.PARTY_DATA_FORMAT_INVALID.CODE,
                    ServiceErrors.PARTY_DATA_FORMAT_INVALID.KEY);
        }
        TrnRequestDTO.PartyCustomerDTO shipper = new TrnRequestDTO.PartyCustomerDTO();
        TrnRequestDTO.PartyCustomerDTO consignee = new TrnRequestDTO.PartyCustomerDTO();
        TrnRequestDTO.PartyCustomerDTO forwarder = new TrnRequestDTO.PartyCustomerDTO();
        TrnRequestDTO.PartyCustomerDTO transporter = new TrnRequestDTO.PartyCustomerDTO();
        for (int i = 0; i < 4; i++) {
            TrnRequestDTO.PartyCustomerDTO partyItem = party.get(i);                                 // Check the index and add the item to the corresponding PartyCustomerDTO object
            switch (i) {
                case 0:
                    shipper = partyItem;
                    break;
                case 1:
                    consignee = partyItem;
                    break;
                case 2:
                    forwarder = partyItem;
                    break;
                case 3:
                    transporter = partyItem;
                    break;
                default:
                    break;
            }
        }
        setShipperInSave(shipper,trnHeaderParty,dateAndTimeRequestDto,transactionTypeFlag);
        setConsigneeInSave(consignee,trnHeaderParty,dateAndTimeRequestDto,transactionTypeFlag);
        setForwarderInSave(forwarder,trnHeaderParty,dateAndTimeRequestDto,transactionTypeFlag);
        setTransporterInSave(transporter,trnHeaderParty,dateAndTimeRequestDto,transactionTypeFlag);
        return trnHeaderParty;
    }

    /*
     * Method to convert TrnHeaderPartyDTO to TrnHeaderParty
     */
    public void convertUpdateRequestToEntity(List<TrnRequestDTO.PartyCustomerDTO> party, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (party == null || party.size() != 4){
            throw new ServiceException(ServiceErrors.PARTY_DATA_FORMAT_INVALID.CODE,
                    ServiceErrors.PARTY_DATA_FORMAT_INVALID.KEY);
        }
        TrnRequestDTO.PartyCustomerDTO shipper = new TrnRequestDTO.PartyCustomerDTO();
        TrnRequestDTO.PartyCustomerDTO consignee = new TrnRequestDTO.PartyCustomerDTO();
        TrnRequestDTO.PartyCustomerDTO forwarder = new TrnRequestDTO.PartyCustomerDTO();
        TrnRequestDTO.PartyCustomerDTO transporter = new TrnRequestDTO.PartyCustomerDTO();
        for (int i = 0; i < 4; i++) {
            TrnRequestDTO.PartyCustomerDTO partyItem = party.get(i);                                 // Check the index and add the item to the corresponding PartyCustomerDTO object
            switch (i) {
                case 0:
                    shipper = partyItem;
                    break;
                case 1:
                    consignee = partyItem;
                    break;
                case 2:
                    forwarder = partyItem;
                    break;
                case 3:
                    transporter = partyItem;
                    break;
                default:
                    break;
            }
        }
        setShipperInUpdate(shipper,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto,transactionTypeFlag);
        setConsigneeInUpdate(consignee,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto,transactionTypeFlag);
        setForwarderInUpdate(forwarder,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto,transactionTypeFlag);
        setTransporterInUpdate(transporter,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto,transactionTypeFlag);
    }

    /*
     * Method to convert TrnHeaderPary to TrnHeaderParty ResponseDTO
     * @Return TrnResponseDTO.TrnHeaderPartyDTO
     */
    public List<TrnResponseDTO.PartyCustomerDTO> convertEntityToResponseList(TrnHeaderAsn trnHeaderAsn) {
        log.info("ENTRY-EXIT - TrnHeaderParty to TrnHeaderParty ResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        List<TrnResponseDTO.PartyCustomerDTO> party = new ArrayList<>();
        TrnResponseDTO.PartyCustomerDTO shipper = new TrnResponseDTO.PartyCustomerDTO();
        TrnResponseDTO.PartyCustomerDTO consignee = new TrnResponseDTO.PartyCustomerDTO();
        TrnResponseDTO.PartyCustomerDTO forwarder = new TrnResponseDTO.PartyCustomerDTO();
        TrnResponseDTO.PartyCustomerDTO transporter = new TrnResponseDTO.PartyCustomerDTO();
        if (trnHeaderAsn.getTrnHeaderParty().getShipperMaster() != null){
            TrnResponseDTO.PartyCustomerDTO.CustomerDTO customer = modelMapper.map(trnHeaderAsn.getTrnHeaderParty().getShipperMaster(),TrnResponseDTO.PartyCustomerDTO.CustomerDTO.class);
            shipper.setShipper(customer);
            shipper.setShipperName(null);
        }else {
            shipper.setShipper(null);
            shipper.setShipperName(trnHeaderAsn.getTrnHeaderParty().getShipperName());
        }
        if (trnHeaderAsn.getTrnHeaderParty().getShipperAddressDetail() != null){
            setPartyDTO(trnHeaderAsn.getTrnHeaderParty().getShipperAddressDetail(),shipper);
        }
        shipper.setIndex("0");
        setIsEditedName(shipper);
        party.add(shipper);
        if (trnHeaderAsn.getTrnHeaderParty().getConsigneeMaster() != null){
            TrnResponseDTO.PartyCustomerDTO.CustomerDTO customer = modelMapper.map(trnHeaderAsn.getTrnHeaderParty().getConsigneeMaster(),TrnResponseDTO.PartyCustomerDTO.CustomerDTO.class);
            consignee.setShipper(customer);
            consignee.setShipperName(null);
        }else {
            consignee.setShipper(null);
            consignee.setShipperName(trnHeaderAsn.getTrnHeaderParty().getConsigneeName());
        }
        if (trnHeaderAsn.getTrnHeaderParty().getConsigneeAddressDetail() != null){
            setPartyDTO(trnHeaderAsn.getTrnHeaderParty().getConsigneeAddressDetail(),consignee);
        }
        consignee.setIndex("1");
        setIsEditedName(consignee);
        party.add(consignee);
        if (trnHeaderAsn.getTrnHeaderParty().getForwarderMaster() != null){
            TrnResponseDTO.PartyCustomerDTO.CustomerDTO customer = modelMapper.map(trnHeaderAsn.getTrnHeaderParty().getForwarderMaster(),TrnResponseDTO.PartyCustomerDTO.CustomerDTO.class);
            forwarder.setShipper(customer);
            forwarder.setShipperName(null);
        }else {
            forwarder.setShipper(null);
            forwarder.setShipperName(trnHeaderAsn.getTrnHeaderParty().getForwarderName());
        }
        if (trnHeaderAsn.getTrnHeaderParty().getForwarderAddressDetail() != null){
            setPartyDTO(trnHeaderAsn.getTrnHeaderParty().getForwarderAddressDetail(),forwarder);
        }
        forwarder.setIndex("2");
        setIsEditedName(forwarder);
        party.add(forwarder);
        if (trnHeaderAsn.getTrnHeaderParty().getTransporterMaster() != null){
            TrnResponseDTO.PartyCustomerDTO.CustomerDTO customer = modelMapper.map(trnHeaderAsn.getTrnHeaderParty().getTransporterMaster(),TrnResponseDTO.PartyCustomerDTO.CustomerDTO.class);
            transporter.setShipper(customer);
            transporter.setShipperName(null);
        }else {
            transporter.setShipper(null);
            transporter.setShipperName(trnHeaderAsn.getTrnHeaderParty().getTransporterName());
        }
        if (trnHeaderAsn.getTrnHeaderParty().getTransporterAddressDetail() != null){
            setPartyDTO(trnHeaderAsn.getTrnHeaderParty().getTransporterAddressDetail(),transporter);
        }
        transporter.setIndex("3");
        setIsEditedName(transporter);
        party.add(transporter);
        return party;
    }

    private TrnResponseDTO.PartyCustomerDTO setIsEditedName(TrnResponseDTO.PartyCustomerDTO partyDTO) {
        if (partyDTO.getShipper() != null ){
            partyDTO.setIsEditedName(false);
        }
        if (partyDTO.getShipperName() != null && !partyDTO.getShipperName().isEmpty() && !partyDTO.getShipperName().isBlank() ){
            partyDTO.setIsEditedName(true);
            return partyDTO;
        }
        if (partyDTO.getShipper() == null && (partyDTO.getShipperName() == null || !partyDTO.getShipperName().isEmpty() || !partyDTO.getShipperName().isBlank())){
            partyDTO.setIsEditedName(false);
        }
        return partyDTO;
    }

    private TrnHeaderParty setShipperInSave(TrnRequestDTO.PartyCustomerDTO shipper,TrnHeaderParty trnHeaderParty,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (shipper != null) {
            if (shipper.getShipper() != null &&
                    (shipper.getShipperName() == null ||
                            shipper.getShipperName().isBlank() ||
                            shipper.getShipperName().isEmpty())) {
                trnHeaderParty.setShipperMaster(
                        customerMasterService.getCustomerById(Long.parseLong(shipper.getShipper().getId())));
                trnHeaderParty.setShipperName(null);
            } else {
                trnHeaderParty.setShipperName(shipper.getShipperName());
                trnHeaderParty.setShipperMaster(null);
            }
            if (transactionTypeFlag.equals("asn") && (shipper.getCity() == null || shipper.getState() == null || shipper.getCountry() == null)) {
                throw new ServiceException(ServiceErrors.SHIPPER_MUST_NOT_BE_NULL.CODE,
                        ServiceErrors.SHIPPER_MUST_NOT_BE_NULL.KEY);
            }
            trnHeaderParty.setShipperAddressDetail(setPartyAddressDetail(shipper,dateAndTimeRequestDto));
            return trnHeaderParty;
        }
        return trnHeaderParty;
    }

    private TrnHeaderParty setConsigneeInSave(TrnRequestDTO.PartyCustomerDTO consignee,TrnHeaderParty trnHeaderParty,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (consignee != null) {
            if (consignee.getShipper() != null &&
                    (consignee.getShipperName() == null ||
                            consignee.getShipperName().isBlank() ||
                            consignee.getShipperName().isEmpty())) {
                trnHeaderParty.setConsigneeMaster(
                        customerMasterService.getCustomerById(Long.parseLong(consignee.getShipper().getId())));
                trnHeaderParty.setConsigneeName(null);
            } else {
                trnHeaderParty.setConsigneeName(consignee.getShipperName());
                trnHeaderParty.setConsigneeMaster(null);
            }
            if (transactionTypeFlag.equals("so") && (consignee.getCity() == null || consignee.getState() == null || consignee.getCountry() == null)) {
                throw new ServiceException(ServiceErrors.CONSIGNEE_MUST_NOT_BE_NULL.CODE,
                        ServiceErrors.CONSIGNEE_MUST_NOT_BE_NULL.KEY);
            }
            trnHeaderParty.setConsigneeAddressDetail(setPartyAddressDetail(consignee,dateAndTimeRequestDto));
            return trnHeaderParty;
        }
        return trnHeaderParty;
    }

    private TrnHeaderParty setForwarderInSave(TrnRequestDTO.PartyCustomerDTO forwarder,TrnHeaderParty trnHeaderParty,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (forwarder != null) {
            if (forwarder.getShipper() != null &&
                    (forwarder.getShipperName() == null ||
                            forwarder.getShipperName().isBlank() ||
                            forwarder.getShipperName().isEmpty())) {
                trnHeaderParty.setForwarderMaster(
                        customerMasterService.getCustomerById(Long.parseLong(forwarder.getShipper().getId())));
                trnHeaderParty.setForwarderName(null);
            } else {
                trnHeaderParty.setForwarderName(forwarder.getShipperName());
                trnHeaderParty.setForwarderMaster(null);
            }
            if (transactionTypeFlag.equals("po") && (forwarder.getCity() == null || forwarder.getState() == null || forwarder.getCountry() == null)) {
                throw new ServiceException(ServiceErrors.FORWARDER_MUST_NOT_BE_NULL.CODE,
                        ServiceErrors.FORWARDER_MUST_NOT_BE_NULL.KEY);
            }
            trnHeaderParty.setForwarderAddressDetail(setPartyAddressDetail(forwarder,dateAndTimeRequestDto));
            return trnHeaderParty;
        }
        return trnHeaderParty;
    }

    private TrnHeaderParty setTransporterInSave(TrnRequestDTO.PartyCustomerDTO transporter,TrnHeaderParty trnHeaderParty,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (transporter != null) {
            if (transporter.getShipper() != null &&
                    (transporter.getShipperName() == null ||
                            transporter.getShipperName().isBlank() ||
                            transporter.getShipperName().isEmpty())) {
                trnHeaderParty.setTransporterMaster(
                        customerMasterService.getCustomerById(Long.parseLong(transporter.getShipper().getId())));
                trnHeaderParty.setTransporterName(null);
            } else {
                trnHeaderParty.setTransporterName(transporter.getShipperName());
                trnHeaderParty.setTransporterMaster(null);
            }
            if (transactionTypeFlag.equals("grn") && (transporter.getCity() == null || transporter.getState() == null || transporter.getCountry() == null)) {
                throw new ServiceException(ServiceErrors.TRANSPORTER_MUST_NOT_BE_NULL.CODE,
                        ServiceErrors.TRANSPORTER_MUST_NOT_BE_NULL.KEY);
            }
            trnHeaderParty.setTransporterAddressDetail(setPartyAddressDetail(transporter,dateAndTimeRequestDto));
            return trnHeaderParty;
        }
        return trnHeaderParty;
    }

    private void setShipperInUpdate(TrnRequestDTO.PartyCustomerDTO shipper,TrnHeaderAsn trnHeaderAsnToBeUpdated,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (shipper != null) {
            if (shipper.getShipper() != null &&
                    (shipper.getShipperName() == null ||
                            shipper.getShipperName().isBlank() ||
                            shipper.getShipperName().isEmpty())) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setShipperMaster(
                        customerMasterService.getCustomerById(Long.parseLong(shipper.getShipper().getId())));
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setShipperName(null);
            } else {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setShipperName(shipper.getShipperName());
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setShipperMaster(null);
            }
            setShipperAddressDetailInUpdate(shipper,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto,transactionTypeFlag);
        }
    }

    private void setShipperAddressDetailInUpdate(TrnRequestDTO.PartyCustomerDTO shipper,TrnHeaderAsn trnHeaderAsnToBeUpdated,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail() != null) {
            modelMapper.map(dateAndTimeRequestDto, trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setMobile(shipper.getMobile());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setPhone(shipper.getPhone());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setStreetName(shipper.getStreetName());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setBuildingNo(shipper.getBuildingNoName());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setBuildingNo(shipper.getBuildingNoName());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setEmail(shipper.getEmailId());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setPoBox(shipper.getPoBoxNo());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setPreMobileNo(shipper.getMobilePrefix());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setPrePhoneNo(shipper.getPhonePrefix());
            validateShipper(shipper,transactionTypeFlag);
            if (shipper.getCity() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setCity(cityMasterService.getCityById(Long.parseLong(shipper.getCity().getId())));
            }
            if (shipper.getState() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setState(stateMasterService.getStateById(Long.parseLong(shipper.getState().getId())));
            }
            if (shipper.getCountry() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getShipperAddressDetail().setCountry(countryMasterService.getCountryById(Long.parseLong(shipper.getCountry().getId())));
            }
        }else {
            validateShipper(shipper,transactionTypeFlag);
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().setShipperAddressDetail(setPartyAddressDetail(shipper,dateAndTimeRequestDto));
        }
    }

    private void setConsigneeInUpdate(TrnRequestDTO.PartyCustomerDTO consignee,TrnHeaderAsn trnHeaderAsnToBeUpdated,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (consignee != null) {
            if (consignee.getShipper() != null &&
                    (consignee.getShipperName() == null ||
                            consignee.getShipperName().isBlank() ||
                            consignee.getShipperName().isEmpty())) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setConsigneeMaster(
                        customerMasterService.getCustomerById(Long.parseLong(consignee.getShipper().getId())));
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setConsigneeName(null);
            } else {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setConsigneeName(consignee.getShipperName());
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setConsigneeMaster(null);
            }
            setConsigneeAddressDetail(consignee,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto,transactionTypeFlag);
        }
    }

    private void setConsigneeAddressDetail(TrnRequestDTO.PartyCustomerDTO consignee,TrnHeaderAsn trnHeaderAsnToBeUpdated,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail() != null) {
            modelMapper.map(dateAndTimeRequestDto, trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setMobile(consignee.getMobile());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setPhone(consignee.getPhone());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setStreetName(consignee.getStreetName());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setBuildingNo(consignee.getBuildingNoName());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setEmail(consignee.getEmailId());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setPoBox(consignee.getPoBoxNo());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setPreMobileNo(consignee.getMobilePrefix());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setPrePhoneNo(consignee.getPhonePrefix());
            validateConsignee(consignee,transactionTypeFlag);
            if (consignee.getCity() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setCity(cityMasterService.getCityById(Long.parseLong(consignee.getCity().getId())));
            }
            if (consignee.getState() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setState(stateMasterService.getStateById(Long.parseLong(consignee.getState().getId())));
            }
            if (consignee.getCountry() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getConsigneeAddressDetail().setCountry(countryMasterService.getCountryById(Long.parseLong(consignee.getCountry().getId())));
            }
        }else {
            validateConsignee(consignee,transactionTypeFlag);
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().setConsigneeAddressDetail(setPartyAddressDetail(consignee,dateAndTimeRequestDto));
        }
    }

    private void setForwarderInUpdate(TrnRequestDTO.PartyCustomerDTO forwarder,TrnHeaderAsn trnHeaderAsnToBeUpdated,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag) {
        if (forwarder != null) {
            if (forwarder.getShipper() != null &&
                    (forwarder.getShipperName() == null ||
                            forwarder.getShipperName().isBlank() ||
                            forwarder.getShipperName().isEmpty())) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setForwarderMaster(
                        customerMasterService.getCustomerById(Long.parseLong(forwarder.getShipper().getId())));
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setForwarderName(null);
            } else {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setForwarderName(forwarder.getShipperName());
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setForwarderMaster(null);
            }
            setForwarderAddressDetail(forwarder,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto,transactionTypeFlag);
        }
    }

    private void setForwarderAddressDetail(TrnRequestDTO.PartyCustomerDTO forwarder,TrnHeaderAsn trnHeaderAsnToBeUpdated,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail() != null) {
            modelMapper.map(dateAndTimeRequestDto, trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setMobile(forwarder.getMobile());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setPhone(forwarder.getPhone());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setStreetName(forwarder.getStreetName());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setBuildingNo(forwarder.getBuildingNoName());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setEmail(forwarder.getEmailId());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setPoBox(forwarder.getPoBoxNo());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setPreMobileNo(forwarder.getMobilePrefix());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setPrePhoneNo(forwarder.getPhonePrefix());
            validateForwarder(forwarder,transactionTypeFlag);
            if (forwarder.getCity() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setCity(cityMasterService.getCityById(Long.parseLong(forwarder.getCity().getId())));
            }
            if (forwarder.getState() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setState(stateMasterService.getStateById(Long.parseLong(forwarder.getState().getId())));
            }
            if (forwarder.getCountry() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getForwarderAddressDetail().setCountry(countryMasterService.getCountryById(Long.parseLong(forwarder.getCountry().getId())));
            }
        } else {
            validateForwarder(forwarder,transactionTypeFlag);
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().setForwarderAddressDetail(setPartyAddressDetail(forwarder, dateAndTimeRequestDto));
        }
    }

    private void setTransporterInUpdate(TrnRequestDTO.PartyCustomerDTO transporter,TrnHeaderAsn trnHeaderAsnToBeUpdated,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (transporter != null) {
            if (transporter.getShipper() != null &&
                    (transporter.getShipperName() == null ||
                            transporter.getShipperName().isBlank() ||
                            transporter.getShipperName().isEmpty())) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setTransporterMaster(
                        customerMasterService.getCustomerById(Long.parseLong(transporter.getShipper().getId())));
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setTransporterName(null);
            } else {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setTransporterName(transporter.getShipperName());
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().setTransporterMaster(null);
            }
            setTransporterAddressDetail(transporter,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto,transactionTypeFlag);
        }
    }

    private void setTransporterAddressDetail(TrnRequestDTO.PartyCustomerDTO transporter,TrnHeaderAsn trnHeaderAsnToBeUpdated,DateAndTimeRequestDto dateAndTimeRequestDto,String transactionTypeFlag){
        if (trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail() != null) {
            modelMapper.map(dateAndTimeRequestDto, trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setMobile(transporter.getMobile());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setPhone(transporter.getPhone());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setStreetName(transporter.getStreetName());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setBuildingNo(transporter.getBuildingNoName());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setEmail(transporter.getEmailId());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setPoBox(transporter.getPoBoxNo());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setPreMobileNo(transporter.getMobilePrefix());
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setPrePhoneNo(transporter.getPhonePrefix());
            validateTransporter(transporter,transactionTypeFlag);
            if (transporter.getCity() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setCity(cityMasterService.getCityById(Long.parseLong(transporter.getCity().getId())));
            }
            if (transporter.getState() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setState(stateMasterService.getStateById(Long.parseLong(transporter.getState().getId())));
            }
            if (transporter.getCountry() != null) {
                trnHeaderAsnToBeUpdated.getTrnHeaderParty().getTransporterAddressDetail().setCountry(countryMasterService.getCountryById(Long.parseLong(transporter.getCountry().getId())));
            }
        }else {
            validateTransporter(transporter,transactionTypeFlag);
            trnHeaderAsnToBeUpdated.getTrnHeaderParty().setTransporterAddressDetail(setPartyAddressDetail(transporter,dateAndTimeRequestDto));
        }
    }

    private TrnResponseDTO.PartyCustomerDTO setPartyDTO(PartyAddressDetail partyAddressDetail ,TrnResponseDTO.PartyCustomerDTO partyCustomerDTO ){
        modelMapper.map(partyAddressDetail,partyCustomerDTO);
        if (partyAddressDetail.getCity() != null){
            TrnResponseDTO.PartyCustomerDTO.CityDTO cityDTO = modelMapper.map(partyAddressDetail.getCity(),
                    TrnResponseDTO.PartyCustomerDTO.CityDTO.class);
            partyCustomerDTO.setCity(cityDTO);
        }
        if (partyAddressDetail.getState() != null){
            TrnResponseDTO.PartyCustomerDTO.StateDTO stateDTO = modelMapper.map(partyAddressDetail.getState(),
                    TrnResponseDTO.PartyCustomerDTO.StateDTO.class);
            partyCustomerDTO.setState(stateDTO);
        }
        if (partyAddressDetail.getCountry() != null){
            TrnResponseDTO.PartyCustomerDTO.CountryDTO countryDTO = modelMapper.map(partyAddressDetail.getCountry(),
                    TrnResponseDTO.PartyCustomerDTO.CountryDTO.class);
            partyCustomerDTO.setCountry(countryDTO);
        }
        partyCustomerDTO.setEmailId((partyAddressDetail.getEmail()));
        partyCustomerDTO.setPhonePrefix((partyAddressDetail.getPrePhoneNo()));
        partyCustomerDTO.setMobilePrefix((partyAddressDetail.getPreMobileNo()));
        partyCustomerDTO.setPoBoxNo((partyAddressDetail.getPoBox()));
        partyCustomerDTO.setBuildingNoName((partyAddressDetail.getBuildingNo()));
        return partyCustomerDTO;
    }

    private PartyAddressDetail setPartyAddressDetail(TrnRequestDTO.PartyCustomerDTO party, DateAndTimeRequestDto dateAndTimeRequestDto){
        PartyAddressDetail partyAddressDetail = new PartyAddressDetail();
        modelMapper.map(dateAndTimeRequestDto, partyAddressDetail);
        partyAddressDetail.setMobile(party.getMobile());
        partyAddressDetail.setPhone(party.getPhone());
        partyAddressDetail.setStreetName(party.getStreetName());
        partyAddressDetail.setBuildingNo(party.getBuildingNoName());
        partyAddressDetail.setEmail(party.getEmailId());
        partyAddressDetail.setPoBox(party.getPoBoxNo());
        partyAddressDetail.setPreMobileNo(party.getMobilePrefix());
        partyAddressDetail.setPrePhoneNo(party.getPhonePrefix());
        if (party.getCity() != null) {
            partyAddressDetail.setCity(cityMasterService.getCityById(Long.parseLong(party.getCity().getId())));
        }
        if (party.getState() != null) {
            partyAddressDetail.setState(stateMasterService.getStateById(Long.parseLong(party.getState().getId())));
        }
        if (party.getCountry() != null) {
            partyAddressDetail.setCountry(countryMasterService.getCountryById(Long.parseLong(party.getCountry().getId())));
        }
        return partyAddressDetail;
    }

    private void validateShipper(TrnRequestDTO.PartyCustomerDTO shipper,String transactionTypeFlag){
        if (transactionTypeFlag.equals("asn") && (shipper.getCity() == null || shipper.getState() == null || shipper.getCountry() == null)){
            throw new ServiceException(SHIPPER_MUST_NOT_BE_NULL.CODE,
                    SHIPPER_MUST_NOT_BE_NULL.KEY);
        }
    }

    private void validateConsignee(TrnRequestDTO.PartyCustomerDTO consignee,String transactionTypeFlag){
        if (transactionTypeFlag.equals("so") && (consignee.getCity() == null || consignee.getState() == null || consignee.getCountry() == null)){
            throw new ServiceException(CONSIGNEE_MUST_NOT_BE_NULL.CODE,
                    CONSIGNEE_MUST_NOT_BE_NULL.KEY);
        }
    }

    private void validateForwarder(TrnRequestDTO.PartyCustomerDTO forwarder,String transactionTypeFlag){
        if (transactionTypeFlag.equals("po") && (forwarder.getCity() == null || forwarder.getState() == null || forwarder.getCountry() == null)){
            throw new ServiceException(FORWARDER_MUST_NOT_BE_NULL.CODE,
                    FORWARDER_MUST_NOT_BE_NULL.KEY);
        }
    }

    private void validateTransporter(TrnRequestDTO.PartyCustomerDTO transporter,String transactionTypeFlag){
        if (transactionTypeFlag.equals("grn") && (transporter.getCity() == null || transporter.getState() == null || transporter.getCountry() == null)){
            throw new ServiceException(TRANSPORTER_MUST_NOT_BE_NULL.CODE,
                    TRANSPORTER_MUST_NOT_BE_NULL.KEY);
        }
    }

    public TrnHeaderParty convertGrnRequestToTrnEntity(GrnHeaderRequestDTO grnHeaderRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnHeaderParty trnHeaderParty = new TrnHeaderParty();
        modelMapper.map(dateAndTimeRequestDto,trnHeaderParty);
        if (grnHeaderRequestDTO.getTrnHeaderAsn().getSupplier() != null &&
                (grnHeaderRequestDTO.getTrnHeaderAsn().getSupplierName() == null ||
                        grnHeaderRequestDTO.getTrnHeaderAsn().getSupplierName().isEmpty() ||
                        grnHeaderRequestDTO.getTrnHeaderAsn().getSupplierName().isBlank())) {
            CustomerMaster customerMaster = customerMasterService.getCustomerById(Long.parseLong(grnHeaderRequestDTO.getTrnHeaderAsn().getSupplier().getId()));
            trnHeaderParty.setShipperMaster(customerMaster);
            CustomerAddressResponseDTO customerAddressResponseDTO = customerAddressMapper.convertEntityListToResponseList(customerMaster.getCustomerAddressMasterList());
            trnHeaderParty.setShipperName(null);
            trnHeaderParty.setShipperAddressDetail(getPartyAddressDetail(customerAddressResponseDTO,dateAndTimeRequestDto));
        } else {
            trnHeaderParty.setShipperName(grnHeaderRequestDTO.getTrnHeaderAsn().getSupplierName());
            trnHeaderParty.setShipperMaster(null);
        }
        return trnHeaderParty;
    }

    private PartyAddressDetail getPartyAddressDetail(CustomerAddressResponseDTO customerAddressResponseDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        PartyAddressDetail partyAddressDetail = new PartyAddressDetail();
        modelMapper.map(dateAndTimeRequestDto, partyAddressDetail);
        if (!CollectionUtils.isEmpty(customerAddressResponseDTO.getMobileNumbers())) {
            partyAddressDetail.setMobile(customerAddressResponseDTO.getMobileNumbers().get(0).getMobileNumber());
            partyAddressDetail.setPreMobileNo(customerAddressResponseDTO.getMobileNumbers().get(0).getMobilePrefix());
        }
        if (!CollectionUtils.isEmpty(customerAddressResponseDTO.getPhoneNumbers())) {
            partyAddressDetail.setPhone(customerAddressResponseDTO.getPhoneNumbers().get(0).getPhoneNumber());
            partyAddressDetail.setPrePhoneNo(customerAddressResponseDTO.getPhoneNumbers().get(0).getPhonePrefix());
        }
        partyAddressDetail.setStreetName(customerAddressResponseDTO.getStreetName());
        partyAddressDetail.setBuildingNo(customerAddressResponseDTO.getBuildingNoName());
        partyAddressDetail.setPoBox(customerAddressResponseDTO.getPoBoxNo());
        if (!CollectionUtils.isEmpty(customerAddressResponseDTO.getEmail())) {
            partyAddressDetail.setEmail(customerAddressResponseDTO.getEmail().get(0));
        }
        if (customerAddressResponseDTO.getCity() != null) {
            partyAddressDetail.setCity(cityMasterService.getCityById(customerAddressResponseDTO.getCity().getId()));
        }
        if (customerAddressResponseDTO.getState() != null) {
            partyAddressDetail.setState(stateMasterService.getStateById(customerAddressResponseDTO.getState().getId()));
        }
        if (customerAddressResponseDTO.getCountry() != null) {
            partyAddressDetail.setCountry(countryMasterService.getCountryById(customerAddressResponseDTO.getCountry().getId()));
        }
        return partyAddressDetail;
    }

}
