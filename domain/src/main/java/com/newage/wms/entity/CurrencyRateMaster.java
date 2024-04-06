package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "efs_currency_rate_master",schema = "tenant_default")
public class CurrencyRateMaster extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_COMPANY_ID"), updatable = false)
    CompanyMaster company;

    @NotNull
    @OneToOne
    @JoinColumn(name = "accounting_currency_id", foreignKey = @ForeignKey(name = "FK_ACCOUNTING_CURRENCY_ID"), updatable = false)
    CurrencyMaster accountingCurrency;

    @NotNull
    @OneToOne
    @JoinColumn(name = "to_currency_id", foreignKey = @ForeignKey(name = "FK_TO_CURRENCY_ID"), updatable = false)
    CurrencyMaster toCurrency;

    @NotNull
    @Column(name = "currency_date", updatable = false)
    Date currencyDate;

    @NotNull
    @Column(name = "sell_rate")
    Double sellRate;

    @NotNull
    @Column(name = "buy_rate")
    Double buyRate;

    @Column(name = "revaluation_rate")
    Double revaluationRate;

    @Version
    @Column(name = "version")
    Long version;

}
