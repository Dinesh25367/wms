package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.CustomerContactRequestDTOWareHouse;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.WareHouseAutoCompleteDTO;
import com.newage.wms.application.dto.requestdto.WareHouseRequestDTO;
import com.newage.wms.application.dto.responsedto.*;
import com.newage.wms.entity.*;
import com.newage.wms.service.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class WareHouseMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CityMasterService cityMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DesignationMasterService designationMasterService;

    /*
     * Method to convert WareHouse Page to WareHouseResponse Page
     * @Return WareHouse Response Page
     */
    public Page<WareHouseResponseDTO> convertWareHousePageToWareHouseResponsePage(Page<WareHouseMaster> wareHousePage){
        log.info("ENTRY - WareHouse Page to WareHouseResponse Page mapper");
        List<WareHouseResponseDTO> wareHouseResponseDtoList = wareHousePage.getContent()
                .stream()
                .map(this::convertEntitytoResponseDto)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(wareHouseResponseDtoList,wareHousePage.getPageable(),wareHousePage.getTotalElements());

    }

    /*
     * Method to convert WareHouseIterable to WareHouseAutoCompleteResponseDtoIterable
     * @Return Iterable<CityMasterDto>
     */
    public Iterable<WareHouseAutoCompleteDTO> convertWareHouseIterableToWareHouseAutoCompleteResponseDtoIterable(Iterable<WareHouseMaster> wareHouseIterable){
        log.info("ENTRY -  WareHouseIterable to WareHouseAutoCompleteResponseDtoIterable");
        return StreamSupport.stream(wareHouseIterable.spliterator(), false)
                .map(this::convertEntitytoAutoCompleteResponseDto)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert WareHouse Request to WareHouse in Save
     * @Return WareHouse
     */
    public WareHouseMaster convertWareHouseRequestToWareHouseSave(WareHouseRequestDTO wareHouseRequestDto,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - WareHouse Request to WareHouse mapper in Save");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        wareHouseRequestDto.setPartyAddressDetail(WareHouseRequestDTO.PartyAddressDetailRequestDTO.builder()            //Initialize PartyAddressDetail in request, so that it is mapped to WareHouse. To avoid Null Exception
                .addressLine1("")
                .addressLine2("")
                .addressLine3("")
                .build());
        WareHouseMaster wareHouse = modelMapper.map(wareHouseRequestDto, WareHouseMaster.class);
        modelMapper.map(dateAndTimeRequestDto,wareHouse);                                           //Map date, time and version to all child class
        modelMapper.map(dateAndTimeRequestDto,wareHouse.getPartyAddressDetail());
        if (wareHouseRequestDto.getCrossDockLocation() != null){
            wareHouse.setCrossDockLocationMaster(locationService.getLocationById(Long.parseLong(wareHouseRequestDto.getCrossDockLocation().getId())));
        }
        if (wareHouse.getCode() != null){
            String code = wareHouse.getCode().toUpperCase();
            wareHouse.setCode(code);
        }
        if (wareHouse.getName() != null) {
            String name = wareHouse.getName().toUpperCase();
            wareHouse.setName(name);
        }
        wareHouse.getPartyAddressDetail().setAddressLine1(wareHouseRequestDto.getAddressLine1());
        wareHouse.getPartyAddressDetail().setAddressLine2(wareHouseRequestDto.getAddressLine2());
        wareHouse.getPartyAddressDetail().setAddressLine3(wareHouseRequestDto.getAddressLine3());
        Character isBonded = wareHouseRequestDtoStringToCharacter(wareHouseRequestDto.getIsBonded()); //Convert 'Yes/No' string to 'Y/N' character
        Character isThirdPartyWareHouse = wareHouseRequestDtoStringToCharacter(wareHouseRequestDto.getIsThirdPartyWareHouse());
        wareHouse.setIsBonded(isBonded);
        wareHouse.setIsThirdPartyWareHouse(isThirdPartyWareHouse);
        wareHouse.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(wareHouseRequestDto.getBranchId())));
        wareHouse.setCode(wareHouseRequestDto.getCode().toUpperCase());
        wareHouse.setName(wareHouseRequestDto.getName().toUpperCase());
        log.info("EXIT");
        return wareHouse;
    }

    /*
     * Method to convert WareHouse Request to WareHouse Contact Detail in Save
     * @Return WareHouseContactDetail
     */
    public WareHouseContactDetail convertWareHouseRequestToWareHouseContactDetailSave(WareHouseRequestDTO wareHouseRequestDto,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - WareHouse Request to WareHouseContactDetail mapper in Save");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        WareHouseContactDetail wareHouseContactDetail = new WareHouseContactDetail();
        List<CustomerContactRequestDTOWareHouse> customerContactRequestList = wareHouseRequestDto.getCustomerContactMasterList();
        List<CustomerContactMaster> customerContactMasterList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(customerContactRequestList)) {
            for (CustomerContactRequestDTOWareHouse customerContactRequest : customerContactRequestList) {       //mapping customerContactRequestList to customerContactMasterList by iteration
                CityMaster cityMaster = null;
                DesignationMaster designationMaster = null;
                if (customerContactRequest.getCity() != null) {
                    Long cityId = Long.parseLong(customerContactRequest.getCity().getId());
                    cityMaster = cityMasterService.getCityById(cityId);
                }                                                                                       //convert Phone, Mobile, Email objects to respective ";" seperated strings
                if (customerContactRequest.getDesignation() != null) {
                    Long designationId = Long.parseLong(customerContactRequest.getDesignation().getId());
                    designationMaster = designationMasterService.getDesignationById(designationId);
                }
                String phone = wareHouseRequestDtoPhoneListToString(customerContactRequest.getPhoneList());
                String mobile = wareHouseRequestDtoMobileListToString(customerContactRequest.getMobileList());
                String email = wareHouseRequestDtoEmailListToString(customerContactRequest.getEmailList());
                CustomerContactMaster customerContactMaster = CustomerContactMaster.builder()
                        .officePhone(phone)
                        .mobileNo(mobile)
                        .email(email)
                        .firstName(customerContactRequest.getContactPerson())
                        .serialNo(0)
                        .lastName(" ")
                        .designation(designationMaster)
                        .city(cityMaster)
                        .build();
                customerContactMaster.setCreatedBy(dateAndTimeRequestDto.getCreatedBy());
                customerContactMaster.setCreatedDate(dateAndTimeRequestDto.getCreatedDate());
                customerContactMaster.setLastModifiedBy(dateAndTimeRequestDto.getLastModifiedBy());
                customerContactMaster.setLastModifiedDate(dateAndTimeRequestDto.getLastModifiedDate());
                customerContactMasterList.add(customerContactMaster);
            }
            wareHouseContactDetail.setCustomerContactMasterList(customerContactMasterList);
        }
        log.info("EXIT");
        return wareHouseContactDetail;
    }

    /*
     * Method to convert update WareHouseRequestDto to WareHouse entity
     * @Return WareHouse
     */
    public WareHouseMaster convertWareHouseUpdateRequestToWareHouse(WareHouseRequestDTO wareHouseRequestDto, WareHouseMaster wareHouse,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - update WareHouseRequestDto to WareHouse mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        modelMapper.map(wareHouseRequestDto,wareHouse);
        wareHouse.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(wareHouseRequestDto.getBranchId())));
        modelMapper.map(dateAndTimeRequestDto,wareHouse);
        modelMapper.map(dateAndTimeRequestDto,wareHouse.getWareHouseContactDetail());
        if (wareHouse.getPartyAddressDetail() != null){                                 // Check for null and set PartyAddressDetail
            wareHouse.getPartyAddressDetail().setAddressLine1(wareHouseRequestDto.getAddressLine1());
            wareHouse.getPartyAddressDetail().setAddressLine2(wareHouseRequestDto.getAddressLine2());
            wareHouse.getPartyAddressDetail().setAddressLine3(wareHouseRequestDto.getAddressLine3());
        }
        if (wareHouseRequestDto.getCrossDockLocation() != null){
            wareHouse.setCrossDockLocationMaster(locationService.getLocationById(Long.parseLong(wareHouseRequestDto.getCrossDockLocation().getId())));
        }else{
            wareHouse.setCrossDockLocationMaster(null);
        }
        if (wareHouse.getCode() != null){
            String code = wareHouse.getCode().toUpperCase();
            wareHouse.setCode(code);
        }
        if (wareHouse.getName() != null) {
            String name = wareHouse.getName().toUpperCase();
            wareHouse.setName(name);
        }
        if (wareHouseRequestDto.getCrossDockLocation() != null){
            wareHouse.setCrossDockLocationMaster(locationService.getLocationById(Long.parseLong(wareHouseRequestDto.getCrossDockLocation().getId())));
        }else {
            wareHouse.setCrossDockLocationMaster(null);
        }
        Character isThirdPartyWareHouse = wareHouseRequestDtoStringToCharacter(wareHouseRequestDto.getIsThirdPartyWareHouse());
        Character isBonded = wareHouseRequestDtoStringToCharacter(wareHouseRequestDto.getIsBonded());   //convert 'Yes/No' string to 'Y/N' character
        wareHouse.setIsThirdPartyWareHouse(isThirdPartyWareHouse);
        wareHouse.setIsBonded(isBonded);
        wareHouse.setCode(wareHouseRequestDto.getCode().toUpperCase());
        wareHouse.setName(wareHouseRequestDto.getName().toUpperCase());
        log.info("EXIT");
        return wareHouse;
    }

    /*
     * Method to convert update WareHouseRequestDto to WareHouse Contact Detail
     * @Return WareHouseContactDetail
     */
    public WareHouseContactDetail convertWareHouseUpdateRequestToWareHouseContactDetail(WareHouseRequestDTO wareHouseRequestDto, WareHouseMaster wareHouse,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - update WareHouseRequestDto to WareHouse Contact Detail mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        WareHouseContactDetail wareHouseContactDetail = modelMapper.map(wareHouse.getWareHouseContactDetail(),WareHouseContactDetail.class);
        List<CustomerContactRequestDTOWareHouse> customerContactRequestList = wareHouseRequestDto.getCustomerContactMasterList();
        List<CustomerContactMaster> updatedCustomerContactMasterList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(customerContactRequestList)) {
            for (CustomerContactRequestDTOWareHouse customerContactRequest : customerContactRequestList) {               //map updated Customer Contact request list to Customer Contact List by iteration
                CityMaster cityMaster = null;
                DesignationMaster designationMaster = null;
                if (customerContactRequest.getCity() != null) {
                    Long cityId = Long.parseLong(customerContactRequest.getCity().getId());
                    cityMaster = cityMasterService.getCityById(cityId);
                }
                if (customerContactRequest.getDesignation() != null) {
                    Long designationId = Long.parseLong(customerContactRequest.getDesignation().getId());
                    designationMaster = designationMasterService.getDesignationById(designationId);
                }
                String phone = wareHouseRequestDtoPhoneListToString(customerContactRequest.getPhoneList());     //convert Phone, Mobile, Email objects to respective ";" seperated strings
                String mobile = wareHouseRequestDtoMobileListToString(customerContactRequest.getMobileList());
                String email = wareHouseRequestDtoEmailListToString(customerContactRequest.getEmailList());
                CustomerContactMaster customerContactMaster = CustomerContactMaster.builder()
                        .officePhone(phone)
                        .mobileNo(mobile)
                        .email(email)
                        .firstName(customerContactRequest.getContactPerson())
                        .lastName(" ")
                        .serialNo(0)
                        .designation(designationMaster)
                        .city(cityMaster)
                        .build();
                customerContactMaster.setCreatedBy(dateAndTimeRequestDto.getCreatedBy());
                customerContactMaster.setCreatedDate(dateAndTimeRequestDto.getCreatedDate());
                customerContactMaster.setLastModifiedBy(dateAndTimeRequestDto.getLastModifiedBy());
                customerContactMaster.setLastModifiedDate(dateAndTimeRequestDto.getLastModifiedDate());
                updatedCustomerContactMasterList.add(customerContactMaster);
            }
            wareHouseContactDetail.setCustomerContactMasterList(updatedCustomerContactMasterList);
        }
        log.info("EXIT");
        return wareHouseContactDetail;
    }
    /*
     * Method to map source to target
     * @Return TargetClass
     */
    private <T, U> U mapToDTOIfNotNull(T source, Class<U> targetClass) {
        log.info("ENTRY - source to target mapper");
        if (source != null) {
            return modelMapper.map(source, targetClass);
        }
        return null;
    }

    /*
     * Method to convert WareHouse entity to WareHouseResponseDto
     * @Return WareHouseResponseDto
     */
    public WareHouseResponseDTO convertEntitytoResponseDto(WareHouseMaster wareHouse){
        log.info("ENTRY - WareHouse to WareHouseResponseDto mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        WareHouseResponseDTO wareHouseResponseDto = modelMapper.map(wareHouse, WareHouseResponseDTO.class);
        wareHouseResponseDto.setCrossDockLocation(mapToDTOIfNotNull(wareHouse.getCrossDockLocationMaster(), WareHouseResponseDTO.LocationDTO.class));
        wareHouseResponseDto.setCreatedDate(wareHouse.getCreatedDate());
        wareHouseResponseDto.setCreatedUser(wareHouse.getCreatedBy());
        wareHouseResponseDto.setUpdatedUser(wareHouse.getLastModifiedBy());
        wareHouseResponseDto.setUpdatedDate(wareHouse.getLastModifiedDate());
        if (wareHouse.getPartyAddressDetail() != null){
            wareHouseResponseDto.setAddressLine1(wareHouse.getPartyAddressDetail().getAddressLine1());
            wareHouseResponseDto.setAddressLine2(wareHouse.getPartyAddressDetail().getAddressLine2());
            wareHouseResponseDto.setAddressLine3(wareHouse.getPartyAddressDetail().getAddressLine3());
        }
        if (wareHouse.getBranchMaster() != null && wareHouse.getBranchMaster().getCountryMaster() != null){
            wareHouseResponseDto.getBranchMaster().setCountry(new BranchResponseDTO.CountryMasterDTO());
            if (wareHouseResponseDto.getBranchMaster() != null){
                wareHouseResponseDto.getBranchMaster().getCountry().setId(wareHouse.getBranchMaster().getCountryMaster().getId());
                wareHouseResponseDto.getBranchMaster().getCountry().setName(wareHouse.getBranchMaster().getCountryMaster().getName());
                wareHouseResponseDto.getBranchMaster().getCountry().setCode(wareHouse.getBranchMaster().getCountryMaster().getCode());
            }
        }
        wareHouseResponseDto.setCustomerContactMasterList(setCustomerContactResponseDtoList(wareHouse));
        String isBonded = wareHouseCharacterToString(wareHouse.getIsBonded());                      //convert 'Y/N' character to 'Yes/No'
        String isThirdPartyWareHouse = wareHouseCharacterToString(wareHouse.getIsThirdPartyWareHouse());
        wareHouseResponseDto.setIsBonded(isBonded);
        wareHouseResponseDto.setIsThirdPartyWareHouse(isThirdPartyWareHouse);
        log.info("EXIT");
        return wareHouseResponseDto;
    }

    /*
     * Method to set CustomerContactResponse Dto List
     * @Return WareHouseResponseDto
     */
    private List<CustomerContactResponseDtoWareHouse> setCustomerContactResponseDtoList(WareHouseMaster wareHouse){
        log.info("ENTRY - set CustomerContactResponse Dto List");
        List<CustomerContactResponseDtoWareHouse> customerContactResponseDtoList = new ArrayList<>();
        List<CustomerContactMaster> customerContactMasterList = wareHouse.getWareHouseContactDetail().getCustomerContactMasterList();
        if (!CollectionUtils.isEmpty(customerContactMasterList)) {
            customerContactResponseDtoList.addAll(getCustomerContactResponseDtoList(customerContactMasterList));
        }
        log.info("EXIT");
        return customerContactResponseDtoList;
    }

    private List<CustomerContactResponseDtoWareHouse> getCustomerContactResponseDtoList(List<CustomerContactMaster> customerContactMasterList) {
        List<CustomerContactResponseDtoWareHouse> customerContactResponseDtoList = new ArrayList<>();
        for (CustomerContactMaster customerContactMaster : customerContactMasterList) {              //mapping customerContactMasterList to customerContactMasterResponseList by iteration
            CustomerContactResponseDtoWareHouse.CityMasterDTO cityMasterDto = new CustomerContactResponseDtoWareHouse.CityMasterDTO();
            DesignationResponseDTO designationResponseDTO = new DesignationResponseDTO();
            if (customerContactMaster.getCity() != null) {
                cityMasterDto = CustomerContactResponseDtoWareHouse.CityMasterDTO.builder()
                        .id(customerContactMaster.getCity().getId())
                        .name(customerContactMaster.getCity().getName())
                        .build();                                                                                       //convert ";" seperated phone, mobile and email to respective object list
            }
            if (customerContactMaster.getDesignation() != null) {
                modelMapper.map(customerContactMaster.getDesignation(), designationResponseDTO);
            }
            List<CustomerContactRequestDTOWareHouse.PhoneDTO> phoneList = wareHousePhoneStringToPhoneList(customerContactMaster.getOfficePhone());
            List<CustomerContactRequestDTOWareHouse.MobileDTO> mobileList = wareHouseMobileStringToMobileList(customerContactMaster.getMobileNo());
            List<CustomerContactRequestDTOWareHouse.EmailDTO> emailList = wareHouseEmailStringToEmailList(customerContactMaster.getEmail());
            if (phoneList != null && phoneList.isEmpty()) {
                phoneList = Arrays.asList(new CustomerContactRequestDTOWareHouse.PhoneDTO("", ""));
            }
            if (mobileList != null && mobileList.isEmpty()) {
                mobileList = Arrays.asList(new CustomerContactRequestDTOWareHouse.MobileDTO("",""));
            }
            if (emailList != null && emailList.isEmpty()) {
                emailList = Arrays.asList(new CustomerContactRequestDTOWareHouse.EmailDTO(""));
            }
            CustomerContactResponseDtoWareHouse customerContactResponseDto = CustomerContactResponseDtoWareHouse.builder()
                    .phoneList(phoneList)
                    .mobileList(mobileList)
                    .emailList(emailList)
                    .contactPerson(customerContactMaster.getFirstName())
                    .designation(designationResponseDTO)
                    .city(cityMasterDto)
                    .build();
            customerContactResponseDtoList.add(customerContactResponseDto);
        }
        return customerContactResponseDtoList;
    }

    /*
     * Method to convert WareHouse to WareHouseAutoCompleteResponseDto
     * @Return WareHouseAutoCompleteResponseDto
     */
    private WareHouseAutoCompleteDTO convertEntitytoAutoCompleteResponseDto(WareHouseMaster wareHouse) {
        log.info("ENTRY - WareHouse to WareHouseAutoCompleteResponseDto mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        WareHouseAutoCompleteDTO wareHouseAutoCompleteResponseDto = modelMapper.map(wareHouse, WareHouseAutoCompleteDTO.class);
        log.info("EXIT");
        return wareHouseAutoCompleteResponseDto;
    }

    /*
     * Method to convert Phone List object to string
     * @Return Phone number string with concatenated ";"
     */
    public String wareHouseRequestDtoPhoneListToString(List<CustomerContactRequestDTOWareHouse.PhoneDTO> phoneList){
        log.info("ENTRY - Conversion of Phone List object to string");
        String phoneString = "";
        //To validate phoneList
        if (phoneList != null && phoneList.size() > 0) {
            for (CustomerContactRequestDTOWareHouse.PhoneDTO currentPhone : phoneList) {
                //To validate current phone object and concatenate with ";" seperator
                if (currentPhone.toString().equals("")){
                    continue;
                }
                phoneString =phoneString + currentPhone.toString();
            }
        }
        log.info("EXIT");
        return phoneString;
    }

    /*
     * Method to convert WareHouse phone string to List of Phone object
     * @Return Phone List
     */
    private List<CustomerContactRequestDTOWareHouse.PhoneDTO> wareHousePhoneStringToPhoneList(String phoneString) {
        log.info("ENTRY - Convert WareHouse phone string to List of Phone object");
        List<CustomerContactRequestDTOWareHouse.PhoneDTO> phoneList = new ArrayList<>();
        //To validate phoneString
        if (checkNullOrEmptyOrBlank(phoneString)){
            return Collections.emptyList();
        }
        String[] phoneArray = phoneString.split(";");
        //To validate phoneArray and iterate
        if (phoneArray.length>0 && phoneArray != null) {
            phoneList = setPhoneList(phoneList,phoneArray);
        }else {
            return Collections.emptyList();
        }
        return phoneList;
    }

    /*
     * Method to set Phone List
     * @Return List<CustomerContactRequestDTOWareHouse.PhoneDTO>
     */
    private List<CustomerContactRequestDTOWareHouse.PhoneDTO> setPhoneList(List<CustomerContactRequestDTOWareHouse.PhoneDTO> phoneList,String[] phoneArray){
        log.info("ENTRY - Convert WareHouse phone string to List of Phone object");
        for (String phoneWithPrefixAndNum : phoneArray) {
            //To skip empty and null strings
            if (checkNullOrEmptyOrBlank(phoneWithPrefixAndNum)){
                continue;
            }
            //To seperate phoneWithPrefixAndNum into two strings based on "_" and add it to phoneList
            if (phoneWithPrefixAndNum.contains("_")){
                String[] phoneWithPrefixAndNumArray = phoneWithPrefixAndNum.split("_");
                if (phoneWithPrefixAndNum.lastIndexOf("_")< phoneWithPrefixAndNum.length()-1){
                    phoneList.add(new CustomerContactRequestDTOWareHouse.PhoneDTO(phoneWithPrefixAndNumArray[1],phoneWithPrefixAndNumArray[0]));
                }else {
                    phoneList.add(new CustomerContactRequestDTOWareHouse.PhoneDTO("",phoneWithPrefixAndNumArray[0]));
                }
            }else {
                phoneList.add(new CustomerContactRequestDTOWareHouse.PhoneDTO(phoneWithPrefixAndNum,""));
            }
        }
        return phoneList;
    }

    /*
     * Method to convert Mobile List object to string
     * @Return Mobile number string with concatenated ";"
     */
    public String wareHouseRequestDtoMobileListToString(List<CustomerContactRequestDTOWareHouse.MobileDTO> mobileList){
        log.info("ENTRY - Conversion of Mobile List object to string");
        String mobileString = "";
        //To validate phoneList
        if (mobileList != null && mobileList.size() > 0) {
            for (CustomerContactRequestDTOWareHouse.MobileDTO currentMobile : mobileList) {
                //To validate current phone object and concatenate with ";" seperator
                if (currentMobile.toString().equals("")){
                    continue;
                }
                mobileString =mobileString + currentMobile.toString();
            }
        }
        log.info("EXIT");
        return mobileString;
    }

    /*
     * Method to convert string to Mobile List
     * @Return Mobile List
     */
    private List<CustomerContactRequestDTOWareHouse.MobileDTO> wareHouseMobileStringToMobileList(String mobileString) {
        log.info("ENTRY - Convert WareHouse mobile string to List of Mobile object");
        List<CustomerContactRequestDTOWareHouse.MobileDTO> mobileList = new ArrayList<>();
        //To validate mobileString
        if (checkNullOrEmptyOrBlank(mobileString)){
            return Collections.emptyList();
        }
        String[] mobileArray = mobileString.split(";");
        //To validate mobileArray and iterate
        if (mobileArray.length>0 && mobileArray != null) {
            mobileList = setMobileList(mobileList,mobileArray);
        }else {
            return Collections.emptyList();
        }
        return mobileList;
    }

    /*
     * Method to set Mobile List
     * @Return List<CustomerContactRequestDTOWareHouse.MobileDTO>
     */
    private List<CustomerContactRequestDTOWareHouse.MobileDTO> setMobileList(List<CustomerContactRequestDTOWareHouse.MobileDTO> mobileList,String[] mobileArray){
        log.info("ENTRY - Convert WareHouse mobile string to List of Mobile object");
        for (String mobileWithPrefixAndNum : mobileArray) {
            //To skip empty and null strings
            if (checkNullOrEmptyOrBlank(mobileWithPrefixAndNum)){
                continue;
            }
            //To seperate mobileWithPrefixAndNum into two strings based on "_" and add it to mobileList
            if (mobileWithPrefixAndNum.contains("_")){
                String[] mobileWithPrefixAndNumArray = mobileWithPrefixAndNum.split("_");
                if (mobileWithPrefixAndNum.lastIndexOf("_")< mobileWithPrefixAndNum.length()-1){
                    mobileList.add(new CustomerContactRequestDTOWareHouse.MobileDTO(mobileWithPrefixAndNumArray[1],mobileWithPrefixAndNumArray[0]));
                }else {
                    mobileList.add(new CustomerContactRequestDTOWareHouse.MobileDTO("",mobileWithPrefixAndNumArray[0]));
                }
            }else {
                mobileList.add(new CustomerContactRequestDTOWareHouse.MobileDTO(mobileWithPrefixAndNum,""));
            }
        }
        return mobileList;
    }

    /*
     * Method to convert Email List object to string
     * @Return Email string with concatenated ";"
     */
    private String wareHouseRequestDtoEmailListToString(List<CustomerContactRequestDTOWareHouse.EmailDTO> emailList) {
        log.info("ENTRY - Conversion of Email List object to string");
        String emailString = "";
        //To validate emailList
        if (emailList != null && emailList.size() > 0) {
            for (CustomerContactRequestDTOWareHouse.EmailDTO currentEmail : emailList) {
                //To validate current email object and concatenate with ";" seperator
                if (currentEmail.toString()==null){
                    continue;
                }
                emailString =emailString + currentEmail.toString();
            }
        }
        log.info("EXIT");
        return emailString;
    }

    /*
     * Method to convert string to Email List
     * @Return Email List
     */
    private List<CustomerContactRequestDTOWareHouse.EmailDTO> wareHouseEmailStringToEmailList(String emailString) {
        log.info("ENTRY - Convert string to Email List");
        List<CustomerContactRequestDTOWareHouse.EmailDTO> emailList = new ArrayList<>();
        //To validate emailString
        if (checkNullOrEmptyOrBlank(emailString)){
            return Collections.emptyList();
        }
        String[] emailArray = emailString.split(";");
        //To validate emailArray and iterate
        if (emailArray.length>0 && emailArray != null) {
            for (String email : emailArray) {
                //To skip empty and null strings
                if (checkNullOrEmptyOrBlank(email)){
                    continue;
                }
                emailList.add((new CustomerContactRequestDTOWareHouse.EmailDTO(email)));
            }
        }else {
            return Collections.emptyList();
        }
        return emailList;
    }

    /*
     * Convert string "Yes"/"No" to char
     * @Return Character 'Y'/'N'
     */
    private Character wareHouseRequestDtoStringToCharacter(String wareHouseRequestDtoString) {
        log.info("ENTRY - Convert string to Character");
        if (checkNullOrEmptyOrBlank(wareHouseRequestDtoString)){
            return null;
        }else {
            if (wareHouseRequestDtoString.contains("Yes")){
                return 'Y';
            } else if (wareHouseRequestDtoString.contains("No")) {
                return 'N';
            }
        }
        return null;
    }


    /*
     * Method to convert Boolean to "Yes" or "No" string
     * @Return "Yes" or "No" string
     */
    private String wareHouseCharacterToString(Character inputCharacter) {
        log.info("ENTRY - convert Character to \"Yes\" or \"No\" string");
        if (inputCharacter != null) {
            if (inputCharacter.equals('Y')){
                return "Yes";
            }else if (inputCharacter.equals('N')){
                return "No";
            }else {
                return null;
            }
        }
        log.info("EXIT");
        return null;
    }

    /*
     * Method to check if a string is Null or Empty or Blank
     * @Return Boolean
     */
    private Boolean checkNullOrEmptyOrBlank(String inputString){
        log.info("ENTRY - check null or empty or blank");
        if (inputString == null || inputString.isBlank() || inputString.isEmpty()){
            log.info("EXIT");
            return true;
        }
        else {
            log.info("EXIT");
            return false;
        }
    }

}