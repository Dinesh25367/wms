package com.newage.wms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="wms_warehouse_contact_detail")
public class WareHouseContactDetail extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;

    @OneToOne
    @JoinColumn(name = "warehouse_id",nullable = false,foreignKey = @ForeignKey(name = "FK_WAREHOUSE_CONTACT_WAREHOUSE_ID"))
    @JsonIgnore
    private WareHouseMaster wareHouse;

    @OneToMany(targetEntity = CustomerContactMaster.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_contact_detail_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_CUSTOMER_CONTACT_WAREHOUSE_ID"))
    private List<CustomerContactMaster> customerContactMasterList;

}
