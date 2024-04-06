package com.newage.wms.application.dto.requestdto;

import lombok.*;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Component
public class CustomerContactRequestDTOWareHouse {

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "^[a-zA-Z\\s]*$")
    private String contactPerson;

    @Valid
    private DesignationRequestDTOWareHouse designation;

    @Valid
    private List<PhoneDTO> phoneList;

    @Valid
    private List<MobileDTO> mobileList;

    @Valid
    private List<EmailDTO> emailList;

    @Valid
    private CityMasterDto city;

    @Data
    @Component
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailDTO {

        @javax.validation.constraints.Email
        private String email;

        /*
         * To convert this email object in String format"abc@gmail.com;"
         * @Return email with ";" concatenated
         */
        @Override
        public String toString() {
            if (email != null && !email.isEmpty() && !email.isBlank()){
                return email + ";";
            }
            return "";
        }

    }

    @Data
    @Component
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MobileDTO {

        @Pattern(regexp = "^[0-9]*$")
        private String mobileNumber;
        private String mobilePrefix;

        /*
         * To convert this mobile object in String format"+91-999999;"
         * @Return mobile number with prefix and ";" concatenated
         */
        @Override
        public String toString() {
            if (mobileNumber != null && !mobileNumber.isEmpty() && !mobileNumber.isBlank()){
                if (mobilePrefix ==null || mobilePrefix.isBlank() || mobilePrefix.isEmpty()){
                    return mobileNumber + ";";
                }
                return mobilePrefix + "_" + mobileNumber + ";";
            }
            return "";
        }

    }

    @Data
    @Component
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhoneDTO {

        @Pattern(regexp = "^[0-9]*$")
        private String phoneNumber;
        private String phonePrefix;

        /*
         * To convert this phone object in String format"+91-999999;"
         * @Return phone number with prefix and ";" concatenated
         */
        @Override
        public String toString() {
            if (phoneNumber != null && !phoneNumber.isEmpty() && !phoneNumber.isBlank()){
                if (phonePrefix ==null || phonePrefix.isBlank() || phonePrefix.isEmpty()){
                    return phoneNumber + ";";
                }
                return phonePrefix + "_" + phoneNumber + ";";
            }
            return "";
        }

    }

    @Getter
    @Setter
    @Component
    public class CityMasterDto {

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;
        private String name;

    }

}
