package com.newage.wms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wms_trn_header_customs_document",schema = "tenant_default")
public class TrnHeaderCustomsDocument extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "document_type",length = 50)
    private String documentType;

    @Column(name = "doc_ref1",length = 100)
    private String docRef1;

    @Column(name = "doc_ref2",length = 100)
    private String docRef2;

    @Column(name = "doc_date")
    private Date docDate;

    @ManyToOne
    @JoinColumn(name = "ior_id",foreignKey = @ForeignKey(name = "FK_IOR_ID"))
    private CustomerMaster iorMaster;

    @ManyToOne
    @JoinColumn(name = "doc_passed_company_id",foreignKey = @ForeignKey(name = "FK_DOC_PASSED_COMPANY_ID"))
    private CustomerMaster docPassedCompanyMaster;

    @Column(name = "doc_passed_person",length = 100)
    private String docPassedPerson;

    @Column(name = "document_value",length = 100)
    private String documentValue;

    @Column(name = "accepted_tolerance",length = 100)
    private String accepted_tolerance;

    @Column(name = "fta_reference_no",length = 100)
    private String ftaReferenceNo;

    @OneToOne
    @JoinColumn(name = "transaction_id",nullable = false,foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    private TrnHeaderAsn trnHeaderAsn;

    /*
     * Method to set null values in empty string
     */
    public void setNullInEmptyString(){
        setNullInEmptyStringForFirstFourFields();
        setNullInEmptyStringForLastThreeFields();
    }

    private void setNullInEmptyStringForFirstFourFields(){
        if (documentType != null && (documentType.isEmpty() || documentType.isBlank())) {
            this.documentType = null;
        }
        if (docRef1 != null && (docRef1.isEmpty() || docRef1.isBlank())) {
            this.docRef1 = null;
        }
        if (docRef2 != null && (docRef2.isEmpty() || docRef2.isBlank())) {
            this.docRef2 = null;
        }
        if (docPassedPerson != null && (docPassedPerson.isEmpty() || docPassedPerson.isBlank())) {
            this.docPassedPerson = null;
        }
    }

    private void setNullInEmptyStringForLastThreeFields(){
        if (documentValue != null && (documentValue.isEmpty() || documentValue.isBlank())) {
            this.documentValue = null;
        }
        if (accepted_tolerance != null && (accepted_tolerance.isEmpty() || accepted_tolerance.isBlank())) {
            this.accepted_tolerance = null;
        }
        if (accepted_tolerance != null && (ftaReferenceNo.isEmpty() || ftaReferenceNo.isBlank())) {
            this.ftaReferenceNo = null;
        }
    }

}
