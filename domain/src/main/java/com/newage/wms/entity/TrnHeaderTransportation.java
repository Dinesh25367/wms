package com.newage.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wms_trn_header_transportation",schema = "tenant_default")
public class TrnHeaderTransportation extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "is_our_transport",length = 15)
    private String isOurTransport;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "container_type")
    private String containerType;

    @Column(name = "container_number")
    private String containerNumber;

    @Column(name = "seal")
    private String seal;

    @Column(name = "driver")
    private String driver;

    @Column(name = "driver_mobile")
    private String driverMobile;

    @Column(name = "driver_id")
    private String driverId;

    @Column(name = "gate_pass_number")
    private String gatePassNumber;

    /*
     * Method to set null values in empty string
     */
    public void setNullInEmptyString(){
        setNullInEmptyStringForFirstFiveFields();
        setNullInEmptyStringForLastFiveFields();
    }

    private void setNullInEmptyStringForFirstFiveFields(){
        if (isOurTransport != null && (isOurTransport.isEmpty() || isOurTransport.isBlank())) {
            this.isOurTransport = null;
        }
        if (vehicleType != null && (vehicleType.isEmpty() || vehicleType.isBlank())) {
            this.vehicleType = null;
        }
        if (vehicleNumber != null && (vehicleNumber.isEmpty() || vehicleNumber.isBlank())) {
            this.vehicleNumber = null;
        }
        if (containerType != null && (containerType.isEmpty() || containerType.isBlank())) {
            this.containerType = null;
        }
        if (containerNumber != null && (containerNumber.isEmpty() || containerNumber.isBlank())) {
            this.containerNumber = null;
        }
    }

    private void setNullInEmptyStringForLastFiveFields(){
        if (seal != null && (seal.isEmpty() || seal.isBlank())) {
            this.seal = null;
        }
        if (driver != null && (driver.isEmpty() || driver.isBlank())) {
            this.driver = null;
        }
        if (driverMobile != null && (driverMobile.isEmpty() || driverMobile.isBlank())) {
            this.driverMobile = null;
        }
        if (driverId != null && (driverId.isEmpty() || driverId.isBlank())) {
            this.driverId = null;
        }
        if (gatePassNumber != null && (gatePassNumber.isEmpty() || gatePassNumber.isBlank())) {
            this.gatePassNumber = null;
        }
    }

}
