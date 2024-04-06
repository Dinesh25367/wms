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
@Table(name="wms_transaction_lot_more",schema = "tenant_default")
public class TransactionLotMore extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lot_field",length = 100,nullable = false)
    private String lotField;

    @Column(name = "label",length = 100)
    private String label;

    @Column(name = "value",length = 100)
    private String value;

}
