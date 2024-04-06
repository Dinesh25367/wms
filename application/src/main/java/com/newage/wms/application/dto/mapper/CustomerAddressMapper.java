package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.CustomerAddressResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.service.CityMasterService;
import com.newage.wms.service.CountryMasterService;
import com.newage.wms.service.CustomerMasterService;
import com.newage.wms.service.StateMasterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public final class CustomerAddressMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CustomerMasterService customerMasterService;

    @Autowired
    CityMasterService cityMasterService;

    @Autowired
    StateMasterService stateMasterService;

    @Autowired
    CountryMasterService countryMasterService;

    public CustomerAddressResponseDTO convertEntityToResponseDTO(CustomerAddressMaster customerAddressMaster) {
        CustomerAddressResponseDTO customerAddressResponseDTO = modelMapper.map(customerAddressMaster, CustomerAddressResponseDTO.class);
        customerAddressResponseDTO.setId(customerAddressResponseDTO.getId());
        customerAddressResponseDTO.setLandmark(customerAddressMaster.getLandMark());
        if (customerAddressMaster.getCustomer() != null) {
            customerAddressResponseDTO.setCustomer(new CustomerAddressResponseDTO.CustomerMasterDTO(customerAddressMaster.getCustomer().getId(), customerAddressMaster.getCustomer().getCode(), customerAddressMaster.getCustomer().getName()));
        }
        if (customerAddressMaster.getCity() != null) {
            customerAddressResponseDTO.setCity(new CustomerAddressResponseDTO.CityMasterDTO(customerAddressMaster.getCity().getId(), customerAddressMaster.getCity().getName()));
        }
        if (customerAddressMaster.getState() != null) {
            customerAddressResponseDTO.setState(new CustomerAddressResponseDTO.StateMasterDTO(customerAddressMaster.getState().getId(), customerAddressMaster.getState().getCode(), customerAddressMaster.getState().getName()));
        }
        if (customerAddressMaster.getCountry() != null) {
            customerAddressResponseDTO.setCountry(new CustomerAddressResponseDTO.CountryMasterDTO(customerAddressMaster.getCountry().getId(), customerAddressMaster.getCountry().getCode(), customerAddressMaster.getCountry().getName()));
        }
        if (customerAddressMaster.getMobileNo() != null) {
            List<CustomerAddressResponseDTO.MobileNumberDTO> mobileNumberDTOS = new ArrayList<>();
            String mobileNos = customerAddressMaster.getMobileNo();
            while (mobileNos.length() > 0) {
                CustomerAddressResponseDTO.MobileNumberDTO mobileNumberDTO = new CustomerAddressResponseDTO.MobileNumberDTO();
                String separator = "-";
                int sepPos = mobileNos.indexOf(separator);
                mobileNumberDTO.setMobilePrefix(mobileNos.substring(0, sepPos));
                mobileNos = mobileNos.substring(sepPos + separator.length());
                String separator1 = ";";
                int sepPos1 = mobileNos.indexOf(separator1);
                mobileNumberDTO.setMobileNumber(mobileNos.substring(0, sepPos1));
                mobileNos = mobileNos.substring(sepPos1 + separator.length());
                mobileNumberDTOS.add(mobileNumberDTO);
            }
            customerAddressResponseDTO.setMobileNumbers(mobileNumberDTOS);
        }
        if (customerAddressMaster.getPhone() != null) {
            List<CustomerAddressResponseDTO.PhoneNumberDTO> phoneNumberDTOS = new ArrayList<>();
            String phoneNos = customerAddressMaster.getPhone();
            while (phoneNos.length() > 0) {
                CustomerAddressResponseDTO.PhoneNumberDTO phoneNumberDTO = new CustomerAddressResponseDTO.PhoneNumberDTO();
                String separator = "-";
                int sepPos = phoneNos.indexOf(separator);
                phoneNumberDTO.setPhonePrefix(phoneNos.substring(0, sepPos));
                phoneNos = phoneNos.substring(sepPos + separator.length());
                String separator1 = ";";
                int sepPos1 = phoneNos.indexOf(separator1);
                phoneNumberDTO.setPhoneNumber(phoneNos.substring(0, sepPos1));
                phoneNos = phoneNos.substring(sepPos1 + separator.length());
                phoneNumberDTOS.add(phoneNumberDTO);
            }
            customerAddressResponseDTO.setPhoneNumbers(phoneNumberDTOS);
        }
        if (customerAddressMaster.getEmail() != null && !customerAddressMaster.getEmail().equalsIgnoreCase("[]")) {
            List<String> emailList = new ArrayList<>();
            String emails = customerAddressMaster.getEmail();
            while (emails.length() > 0) {
                String separator = ";";
                int sepPos = emails.indexOf(separator);
                emailList.add(emails.substring(0, sepPos));
                emails = emails.substring(sepPos + separator.length());
            }
            customerAddressResponseDTO.setEmail(emailList);
        }
        return customerAddressResponseDTO;
    }

    public CustomerAddressResponseDTO convertEntityListToResponseList(List<CustomerAddressMaster> customerAddressMasters) {
        List<CustomerAddressResponseDTO> dtos = new ArrayList<>();
        customerAddressMasters.forEach(e -> dtos.add(convertEntityToResponseDTO(e)));
        dtos.sort(Comparator.comparing(CustomerAddressResponseDTO::getId));
        if (!CollectionUtils.isEmpty(dtos)) {
            CustomerAddressResponseDTO customerAddressResponseDTO = dtos.get(0);
            return customerAddressResponseDTO;
        }
        else {
            return new CustomerAddressResponseDTO();
        }
    }

}

