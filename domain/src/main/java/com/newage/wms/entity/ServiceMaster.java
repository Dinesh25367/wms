package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Where(clause = "deleted <> true ")
@Getter
@Setter
@Table(name = "efs_service_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "updated_date"}, name = "UK_SERVICE_CODE"),
        @UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_SERVICE_NAME")})
public class ServiceMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @NotEmpty
    @Size(max = 10, message = "Size must be between 0 to 10")
    @Column(name = "code", nullable = false, length = 10)
    String code;

    @NotNull
    @NotEmpty
    @Size(max = 100, message = "Only 100 characters are allowed")
    @Column(name = "name", nullable = false, length = 100)
    String name;

    @NotNull(message = "Accepted Values (Air, Ocean, Rail, Road, NA;)")
    @Column(name = "transport_mode", nullable = false, length = 10)
    String transportMode;

    @NotNull(message = "Accepted Values (Import, Export, NA)")
    @Column(name = "import_export", nullable = false, length = 10)
    private String importExport;

    @NotNull(message = "Accepted Values ( Full, Groupage, NA)")
    @Column(name = "full_groupage", nullable = false, length = 10)
    private String fullGroupage;

    @NotNull(message = "Accepted Values ( Yes, No)")
    @Column(name = "clearance", nullable = false, length = 10)
    private String clearance;

    @NotNull(message = "Accepted Values (Active, InActive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @Column(name = "deleted")
    boolean deleted = false;

    @Column(name = "primary_service")
    boolean primaryService = false;

    @Version
    @Column(name = "version")
    Long version;

}
