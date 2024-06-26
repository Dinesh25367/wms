package com.newage.wms.service.exception;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Log4j2
public enum ServiceErrors {

    REGION_ID_NOT_FOUND("WMS-1001", "region.id.not.found"),
    COUNTRY_ID_NOT_FOUND("WMS-1002", "country.id.not.found"),
    STATE_ID_NOT_FOUND("WMS-1003", "state.id.not.found"),
    CITY_ID_NOT_FOUND("WMS-1004", "city.id.not.found"),
    CURRENCY_ID_NOT_FOUND("WMS-1005", "currency.id.not.found"),
    CURRENCY_RATE_ID_NOT_FOUND("WMS-1006", "currency.rate.id.not.found"),
    CURRENCY_RATE_LINE_ITEM_IS_ALREADY_EXIST("WMS-1007", "currency.rate.line.item.duplicate"),
    HS_ID_NOT_FOUND("WMS-1008", "hs.id.not.found"),
    HS_CODE_ALREADY_EXIST("WMS-1009", "hs.code.duplicate"),
    HS_NAME_ALREADY_EXIST("WMS-1010", "hs.name.duplicate"),
    ZONE_ID_NOT_FOUND("WMS-1011", "zone.id.not.found"),
    ZONE_CODE_ALREADY_EXIST("WMS-1012", "zone.code.duplicate"),
    ZONE_NAME_ALREADY_EXIST("WMS-1013", "zone.name.duplicate"),
    GROUP_COMPANY_ID_NOT_FOUND("WMS-1014", "group.company.id.not.found"),
    GROUP_COMPANY_CODE_ALREADY_EXIST("WMS-1015", "group.company.code.duplicate"),
    GROUP_COMPANY_NAME_ALREADY_EXIST("WMS-1016", "group.company.name.duplicate"),
    GROUP_COMPANY_REPORTING_NAME_ALREADY_EXISTS("WMS-1099", "group.company.reportingName.duplicate"),
    BRANCH_ID_NOT_FOUND("WMS-1017", "branch.id.not.found"),
    BRANCH_CODE_ALREADY_EXIST("WMS-1018", "branch.code.duplicate"),
    BRANCH_NAME_ALREADY_EXIST("WMS-1019", "branch.name.duplicate"),
    DIVISION_ID_NOT_FOUND("WMS-1020", "division.id.not.found"),
    DIVISION_CODE_ALREADY_EXIST("WMS-1021", "division.code.duplicate"),
    DIVISION_NAME_ALREADY_EXIST("WMS-1022", "division.name.duplicate"),
    COMPANY_ID_NOT_FOUND("WMS-1023", "company.id.not.found"),
    COMPANY_CODE_ALREADY_EXIST("WMS-1024", "company.code.duplicate"),
    COMPANY_NAME_ALREADY_EXIST("WMS-1025", "company.name.duplicate"),
    COMPANY_REPORTING_NAME_ALREADY_EXIST("WMS-1100", "company.reportingName.duplicate"),
    TOS_ID_NOT_FOUND("WMS-1026", "tos.id.not.found"),
    TOS_CODE_ALREADY_EXIST("WMS-1027", "tos.code.duplicate"),
    TOS_NAME_ALREADY_EXIST("WMS-1028", "tos.name.duplicate"),
    DEPARTMENT_ID_NOT_FOUND("WMS-1029", "department.id.not.found"),
    DEPARTMENT_NAME_ALREADY_EXIST("WMS-1030", "department.name.duplicate"),
    GRADE_ID_NOT_FOUND("WMS-1031", "grade.id.not.found"),
    GRADE_PRIORITY_ALREADY_EXIST("WMS-1032", "grade.priority.duplicate"),
    PORT_ID_NOT_FOUND("WMS-1033", "port.id.not.found"),
    PORT_CODE_ALREADY_EXIST("WMS-1034", "port.code.duplicate"),
    PORT_NAME_ALREADY_EXIST("WMS-1035", "port.name.duplicate"),
    SERVICE_ID_NOT_FOUND("WMS-1036", "service.id.not.found"),
    SERVICE_CODE_ALREADY_EXIST("WMS-1037", "service.code.duplicate"),
    SERVICE_NAME_ALREADY_EXIST("WMS-1038", "service.name.duplicate"),
    COMPETITOR_ID_NOT_FOUND("WMS-1039", "competitor.id.not.found"),
    COMPETITOR_CODE_ALREADY_EXIST("WMS-1040", "competitor.code.duplicate"),
    COMPETITOR_NAME_ALREADY_EXIST("WMS-1041", "competitor.name.duplicate"),
    CUSTOMER_TYPE_ID_NOT_FOUND("WMS-1042", "customer.type.id.not.found"),
    CUSTOMER_TYPE_CODE_ALREADY_EXIST("WMS-1043", "customer.type.code.duplicate"),
    CUSTOMER_TYPE_NAME_ALREADY_EXIST("WMS-1044", "customer.type.name.duplicate"),
    CONTAINER_ID_NOT_FOUND("WMS-1045", "container.id.not.found"),
    CONTAINER_CODE_ALREADY_EXIST("WMS-1046", "container.code.duplicate"),
    CONTAINER_NAME_ALREADY_EXIST("WMS-1047", "container.name.duplicate"),
    VALUE_ADDED_SERVICE_ID_NOT_FOUND("WMS-1048", "vas.id.not.found"),
    VALUE_ADDED_SERVICE_NAME_ALREADY_EXIST("WMS-1049", "vas.name.duplicate"),

    EMPLOYMENT_STATUS_ID_NOT_FOUND("WMS-1050", "employment.status.id.not.found"),
    EMPLOYMENT_STATUS_NAME_ALREADY_EXIST("WMS-1051", "employment.status.name.duplicate"),
    CHARGE_ID_NOT_FOUND("WMS-1052", "charge.id.not.found"),
    CHARGE_CODE_ALREADY_EXIST("WMS-1053", "charge.code.duplicate"),
    CHARGE_NAME_ALREADY_EXIST("WMS-1054", "charge.name.duplicate"),
    YEAR_ID_NOT_FOUND("WMS-1055", "year.id.not.found"),
    FREQUENCY_ID_NOT_FOUND("WMS-1056", "frequency.id.not.found"),
    FREQUENCY_NAME_ALREADY_EXIST("WMS-1057", "frequency.name.duplicate"),
    MEASUREMENT_ID_NOT_FOUND("WMS-1058", "measurement.id.not.found"),
    MEASUREMENT_CODE_ALREADY_EXIST("WMS-1059", "measurement.code.duplicate"),
    MEASUREMENT_NAME_ALREADY_EXIST("WMS-1060", "measurement.name.duplicate"),
    BUSINESS_UNIT_ID_NOT_FOUND("WMS-1061", "bu.id.not.found"),
    BUSINESS_UNIT_NAME_ALREADY_EXIST("WMS-1062", "bu.name.duplicate"),
    BUSINESS_UNIT_CODE_ALREADY_EXIST("WMS-1063", "bu.code.duplicate"),
    DESIGNATION_ID_NOT_FOUND("WMS-1064", "designation.id.not.found"),
    DESIGNATION_NAME_ALREADY_EXIST("WMS-1065", "designation.name.duplicate"),
    DESIGNATION_CODE_ALREADY_EXIST("WMS-1154", "designation.code.duplicate"),
    CUSTOMER_ID_NOT_FOUND("WMS-1066", "customer.id.not.found"),
    CUSTOMER_CODE_ALREADY_EXIST("WMS-1067", "customer.code.duplicate"),
    ADDRESS_TYPE_ID_NOT_FOUND("WMS-1068", "address.type.id.not.found"),
    ADDRESS_TYPE_ALREADY_EXIST("WMS-1069", "address.type.duplicate"),
    EVENT_NAME_ALREADY_EXIST("WMS-1070", "event.name.duplicate"),
    EVENT_ID_NOT_FOUND("WMS-1071", "event.id.not.found"),
    EVENT_CODE_ALREADY_EXIST("WMS-1072", "event.code.duplicate"),
    EMPLOYEE_ID_NOT_FOUND("WMS-1073", "employee.id.not.found"),
    EMPLOYEE_ALIAS_NAME_ALREADY_EXIST("WMS-1074", "employee.alias.name.duplicate"),
    CUSTOMER_RELATIONSHIP_ID_NOT_FOUND("WMS-1075", "customer.relation.id.not.found"),
    CUSTOMER_CONTACT_ID_NOT_FOUND("WMS-1076", "customer.contact.id.not.found"),
    CUSTOMER_CONTACT_NAME_ALREADY_EXIST("WMS-1077", "customer.contact.name.duplicate"),
    CUSTOMER_BUSINESS_DETAIL_ID_NOT_FOUND("WMS-1078", "customer.business.id.not.found"),
    CUSTOMER_BUSINESS_DETAIL_LINE_ITEM_ALREADY_EXIST("WMS-1079", "customer.business.line.item.duplicate"),
    CUSTOMER_GST_ID_ALREADY_EXIST("WMS-1080", "customer.gst.id.duplicate"),
    CUSTOMER_GST_ID_NOT_FOUND("WMS-1081", "customer.gst.id.not.found"),
    CUSTOMER_NAME_ALREADY_EXIST("WMS-1082", "customer.name.duplicate"),
    CUSTOMER_EVENT_ID_NOT_FOUND("WMS-1083", "customer.event.id.not.found"),
    CUSTOMER_EVENT_TRADE_ID_NOT_FOUND("WMS-1084", "customer.event.trade.id.not.found"),
    CUSTOMER_ADDRESS_TYPE_PRIMARY_ALREADY_EXIST("WMS-1085", "customer.address.primary.duplicate"),
    CUSTOMER_ADDRESS_TYPE_CORPORATE_ALREADY_EXIST("WMS-1086", "customer.address.corporate.duplicate"),
    CUSTOMER_ADDRESS_ID_NOT_FOUND("WMS-1087", "customer.address.id.not.found"),
    CUSTOMER_VAT_ID_ALREADY_EXIST("WMS-1088", "customer.vat.id.duplicate"),
    CUSTOMER_VAT_ID_NOT_FOUND("WMS-1089", "customer.vat.not.found"),
    CUSTOMER_TAX_ID_ALREADY_EXIST("WMS-1090", "customer.tax.id.duplicate"),
    CUSTOMER_TAX_ID_NOT_FOUND("WMS-1091", "customer.tax.id.not.found"),
    CALL_TYPE_STATUS_ID_NOT_FOUND("WMS-ERR-1092", "call.type.status.id.not.found"),
    CALL_TYPE_STATUS_NAME_ALREADY_EXIST("WMS-ERR-1093", "call.type.status.name.duplicate"),
    CALL_TYPE_ID_NOT_FOUND("WMS-1094", "call.type.id.not.found"),
    FOLLOWUP_ID_NOT_FOUND("WMS-1095", "follow.up.id.not.found"),
    FOLLOWUP_NAME_ALREADY_EXIST("WMS-1096", "follow.up.name.duplicate"),
    AGENT_PORT_ID_NOT_FOUND("WMS-1097", "agent.port.id.duplicate"),
    AGENT_MASTER_LINE_ITEM_IS_ALREADY_EXIST("WMS-1098", "agent.master.line.item.duplicate"),
    RECORD_DELETE_RESTRICTED("WMS-1900", "record.delete.restricted"),
    CARRIER_MASTER_LINE_ITEM_IS_ALREADY_EXIST("WMS-1102", "carrier.master.line.item.duplicate"),
    CARRIER_NAME_ALREADY_EXIST("WMS-1103", "carrier.name.duplicate"),
    CARRIER_CODE_ALREADY_EXIST("WMS-1104", "carrier.code.duplicate"),
    CARRIER_ID_NOT_FOUND("WMS-1105", "carrier.id.duplicate"),
    TRIGGER_TYPE_ID_NOT_FOUND("WMS-1106", "trigger.type.id.not.found"),
    TRIGGER_TYPE_NAME_ALREADY_EXIST("WMS-1107", "trigger.type.name.duplicate"),
    TRIGGER_ID_NOT_FOUND("WMS-1108", "trigger.id.not.found"),
    TRIGGER_NAME_ALREADY_EXIST("WMS-1109", "trigger.name.duplicate"),
    TRIGGER_CODE_ALREADY_EXIST("WMS-1110", "trigger.code.duplicate"),
    PACK_ID_NOT_FOUND("WMS-1111", "pack.id.not.found"),
    PACK_CODE_ALREADY_EXIST("WMS-1112", "pack.code.already.exist"),
    PACK_NAME_ALREADY_EXIST("WMS-1113", "pack.name.already.exist"),
    REFERENCE_TYPE_NAME_ALREADY_EXIST("WMS-1114", "reference.type.name.already.exist"),
    REFERENCE_TYPE_SHORTNAME_ALREADY_EXIST("WMS-1115", "reference.type.shortname.already.exist"),
    REFERENCE_ID_NOT_EXIST("WMS-1116", "reference.id.not.exist"),
    VESSEL_ID_NOT_FOUND("WMS-1117", "vessel.id.not.found"),
    VESSEL_NAME_DUPLICATE("WMS-1118", "vessel.name.duplicate"),
    VESSEL_SHORT_NAME_DUPLICATE("WMS-1119", "vessel.shortname.duplicate"),
    VESSEL_IMO_DUPLICATE("WMS-1120", "vessel.imo.duplicate"),
    VESSEL_CALL_SIGN_DUPLICATE("WMS-1121", "vessel.call.sign.duplicate"),
    GROUP_ID_NOT_FOUND("WMS-1122", "group.id.not.found"),
    GROUP_NAME_ALREADY_EXIST("WMS-1123", "group.name.duplicate"),
    GROUP_CODE_ALREADY_EXIST("WMS-1124", "group.code.duplicate"),
    AGENT_ID_NOT_FOUND("WMS-1125", "agent.id.not.found"),
    AGENT_NAME_ALREADY_EXIST("WMS-1126", "agent.name.duplicate"),
    MEMBERSHIP_ID_NOT_FOUND("WMS-1127", "membership.id.not.found"),
    MEMBERSHIP_NAME_ALREADY_EXIST("WMS-1128", "membership.name.duplicate"),
    MEMBERSHIP_ID_ALREADY_EXIST("WMS-1129", "membership.id.duplicate"),
    MEASUREMENT_CALCULATION_TYPE_NOT_NULL("WMS-1130", "measurement.calculation.type.not.null"),
    MEASUREMENT_CALCULATION_VALUE_NOT_NULL("WMS-1131", "measurement.calculation.value.not.null"),
    MEASUREMENT_DECIMAL_REQUIRED_NOT_NULL("WMS-1132", "measurement.decimal.required.not.null"),
    MEASUREMENT_ROUND_OFF_NOT_NULL("WMS-1133", "measurement.round.off.not.null"),
    AGENT_ADDRESS_ID_NOT_FOUND("WMS-1134", "agent.address.id.not.found"),
    AGENT_ADDRESS_TYPE_PRIMARY_ALREADY_EXIST("WMS-1135", "agent.address.primary.duplicate"),
    AGENT_ADDRESS_TYPE_CORPORATE_ALREADY_EXIST("WMS-1136", "agent.address.corporate.duplicate"),
    AGENT_CONTACT_ID_NOT_FOUND("WMS-1137", "agent.contact.id.not.found"),
    AGENT_CONTACT_NAME_ALREADY_EXIST("WMS-1138", "agent.contact.name.duplicate"),
    AGENT_MASTER_TRADE_ID_NOT_EXIST("WMS-1139", "agent.master.trade.id.not.exist"),
    AGENT_MASTER_EVENT_EVENT_SERVICE_CITY_ARE_DUPLICATE("WMS-1140", "agent.master.event.event.service.city.are.duplicate"),
    AGENT_MASTER_EVENT_ID_NOT_EXIST("WMS-1141", "agent.master.event.id.not.exist"),
    AGENT_MASTER_TRADE_TRADE_AND_CITY_ARE_DUPLICATE("WMS-1142", "agent.master.trade.trade.and.city.are.duplicate"),
    AGENT_MEMBERSHIP_ID_NOT_FOUND("WMS-1143", "agent.membership.id.not.found"),
    CFS_CODE_ALREADY_EXIST("WMS-1144", "cfs.code.already.exist"),
    CFS_NAME_ALREADY_EXIST("WMS-1145", "cfs.name.already.exist"),
    CFS_ID_NOT_FOUND("WMS-1146", "cfs.id.not.found"),
    CANNOT_BE_PRIMARY("WMS-1147", "cannot.be.primary"),
    ACCOUNTING_CURRENCY_ID_NOT_EXIST("WMS-1148", "accounting.currency.id.not.exist"),
    TRIGGER_TYPE_ALREADY_EXIST("WMS-1149", "trigger.type.duplicate"),
    DOCUMENT_TYPE_ID_NOT_FOUND("WMS-1150", "document.type.id.not.found"),
    DOCUMENT_TYPE_NAME_ALREADY_EXIST("WMS-1151", "document.type.name.duplicate"),
    DOCUMENT_TYPE_SHORT_NAME_ALREADY_EXIST("WMS-1152", "document.type.short.name.duplicate"),
    TRIGGER_TYPE_STATUS_INACTIVE("WMS-1153", "trigger.type.status.inactive"),
    REASON_ID_NOT_FOUND("WMS-1155", "reason.id.not.found"),
    CUSTOMER_MASTER_EMPLOYEE_ID_NOT_EXISTS("WMS-1156", "customer.master.employee.id.not.exists"),
    CUSTOMER_MASTER_EMPLOYEE_COMBINATION_IS_DUPLICATE("WMS-1157", "customer.master.employee.combination.is.duplicate"),
    YEAR_MASTER_LINE_ITEM_IS_ALREADY_EXIST("WMS-1158", "year.master.line.item.duplicate"),
    CUSTOMER_MASTER_EMPLOYEE_IS_WMSMAN("WMS-1159", "customer.master.employee.is.WMSman"),
    AGENT_MASTER_EMPLOYEE_ID_NOT_EXISTS("WMS-1160", "agent.master.employee.id.not.exists"),
    AGENT_MASTER_EMPLOYEE_COMBINATION_IS_DUPLICATE("WMS-1161", "agent.master.employee.combination.is.duplicate"),
    AGENT_MASTER_EMPLOYEE_IS_WMSMAN("WMS-1162", "agent.master.employee.is.WMSman"),
    BUSINESS_ENTITY_ID_NOT_FOUND("WMS-1163", "business.entity.id.not.found"),
    QUESTION_ID_NOT_FOUND("WMS-1164", "qtionnaireMaster.entity.id.not.found"),
    PRIMARY_SERVICE_NOT_FOUND("WMS-1165", "service.code.trade.not.found"),
    RATE_REQUEST_ID_NOT_FOUND("WMS-1166", "rate.request.id.not.found"),
    CHARGE_DETAILS_ID_NOT_FOUND("WMS-1167", "charge.details.id.not.found"),
    RATE_REQUEST_ALREADY_EXIST("WMS-1168", "rate.request.duplicate"),
    CUSTOMER_CONTAIN_ONLY_ONE_PRIMARY_ADDRESS("WMS-1169", "customer.should.contain.only.one.primary.address"),
    CUSTOMER_CONTAIN_ONLY_ONE_PRIMARY_CONTACT("WMS-1170", "customer.should.contain.only.one.primary.contact"),
    SERVICE_MAPPING_ID_NOT_FOUND("WMS-1171", "service.mapping.id.not.found"),
    DUPLICATE_SERVICE_MAPPING_COMBINATION("WMS-1172", "duplicate.service.mapping.combination"),
    AGENT_ALREADY_MAPPED_WITH_DIFFERENT_BRANCH("WMS-1173", "agent.already.mapped.with.different.branch"),
    NO_EXPORT_SERVICE_MAPPING_FOUND("WMS-1174", "no.mapped.export.service.found"),
    ENTITY_NAME_ALREADY_EXIST("WMS-1175", "entity.name.already.exist"),
    EXPIRY_DATE_SHOULD_GREATER_THAN_CURRENT_DATE("WMS-1176", "expiry.date.should.be.greater.than.current.date"),
    DRIVER_MASTER_ID_NOT_FOUND("WMS-1177", "driver.master.id.not.found"),
    DRIVER_MASTER_ATTACHMENT_ID_NOT_FOUND("WMS-1178", "driver.master.attachment.id.not.found"),
    EXPIRY_DATE_SHOULD_NOT_NULL_OR_EMPTY("WMS-1179", "expiry.date.should.be.not.be.null.or.empty"),
    DRIVER_MOBILE_NUMBER_ALREADY_EXIST("WMS-1180", "driver.master.mobilenum.duplicate"),
    DRIVER_NAME_ALREADY_EXIST("WMS-1181", "driver.master.name.duplicate"),
    CUSTOMER_ORIGIN_DESTINATION_ID_SHOULD_NOT_SAME("WMS-1182", "customer.origin.destination.same"),
    NO_IMPORT_SERVICE_MAPPING_FOUND("WMS-1183", "no.import.service.mapping.found"),
    CUSTOMER_SERVICE_ID_DOES_NOT_EXIST("WMS-1184", "no.service.id.found"),
    AGENT_NOT_EXIST_FOR_SELECTED_SERVICE_AND_PORT("WMS-1185", "agent.not.exist.for.selected.service.and.port"),
    FLIGHT_TYPE_ID_NOT_FOUND("WMS-1186", "flight_type_id_not_found"),
    FLIGHT_TYPE_ALREADY_EXIST("WMS-1187", "flight.type.duplicate"),
    CARRIER_PREFIX_ALREADY_EXIST("WMS-1188", "carrier.prefix.already.exist"),
    PICKUP_ID_NOT_FOUND("WMS-1189", "pickup.id.not.found"),
    SERVICE_AND_DESTINATION_PAIRING_ALREADY_EXISTS("WMS-1190", "service.and.destination.pairing.already.exists"),
    CUSTOMER_CODE_DOEST_NOT_EXIST("WMS-1191", "customer.code.does.not.exist"),
    REPORT_TEMPLATE_DOES_NOT_EXIST("WMS-1192", "report.template.not.exist"),
    UNIT_ID_NOT_FOUND("WMS-1193", "unit.id.not.found"),
    UNIT_CODE_ALREADY_EXIST("WMS-1194", "unit.code.duplicate"),
    UNIT_NAME_ALREADY_EXIST("WMS-1195", "unit.name.duplicate"),
    REPORT_DOES_NOT_EXIST("WMS-1197", "report.not.exist"),
    CONTAINER_TYPE_NOT_FOUND("WMS-1196", "container.type.id.not.found"),
    CFS_TARIFF_ID_NOT_FOUND("WMS-1198","cfs.tariff.id.not.found"),
    CONFIGURATION_ID_NOT_FOUND("WMS-1199", "configuration.id.not.found"),
    CONFIGURATION_KEY_ID_NOT_FOUND("WMS-1200", "configuration.key.id.not.found"),
    PLEASE_SELECT_PROPER_CONTAINER("WMS-1201","this.container.not.supported.by.carrier"),
    CONFIGURATION_DUPLICATION("WMS-1202","configuration.duplication"),
    INTERNAL_SERVER_ERROR("WMS-1203", "internal.server.error"),
    UNABLE_TO_GENERATE_NEW_WAREHOUSE_ID("WMS-1204","unable.to.generate.new.warehouse.id"),
    WAREHOUSE_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1205","warehouse.attribute.should.be.unique"),
    WAREHOUSE_ID_NOT_FOUND("WMS-1206","warehouse.id.not.found"),
    STORAGE_TYPE_ID_NOT_FOUND("WMS-1207","storage.type.id.not.found"),
    STORAGE_AREA_ID_NOT_FOUND("WMS-1208", "storage.area.id.not.found"),
    ZONE_MASTER_ID_NOT_FOUND("WMS-1209","zone.master.id.not.found"),
    RACK_ID_NOT_FOUND("WMS-1210","rack.id.not.found"),
    LOC_TYPE_ID_NOT_FOUND("WMS-1211","loc.type.id.not.found"),
    AISLE_MASTER_ID_NOT_FOUND("WMS-1212","aisle.master.id.not.found"),
    UOM_MASTER_ID_NOT_FOUND("WMS-1213","uom.master.id.not.found"),
    CATEGORY_MASTER_ID_NOT_FOUND("WMS-1214","category.master.id.not.found"),
    MASTER_LOCATION_MUST_BE_INACTIVE_AND_IT_SHOULD_NOT_BIN_LOCATION("WMS-1215","master.location.must.be.inactive.and.it.should.not.be.bin.location" ),
    MASTER_LOCATION_MUST_NOT_BE_EMPTY_FOR_BIN_LOCATION("WMS-1216","master.location.must.not.be.empty.for.bin.location" ),
    LOCATION_ALREADY_EXISTS_FOR_THIS_WAREHOUSE("WMS-1217","location.already.exists.for.this.warehouse.code" ),
    DATA_HAS_DUPLICATE_VALUES("WMS-1218","data.has.duplicate.values" ),
    SKU_ID_NOT_FOUND("WMS-1219","sku.id.not.found" ),
    HS_CODE_ID_NOT_FOUND("WMS-1220","hs.code.id.not.found" ),
    IMCO_CODE_ID_NOT_FOUND("WMS-1221","imco.code.id.not.found" ),
    SKU_PACK_ID_NOT_FOUND("WMS-1227", "sku.pack.id.not.found"),
    SKU_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1228", "sku.attribute.must.be.unique"),
    LOCATION_ID_NOT_FOUND("WMS-1229","location.id.not.found" ),
    ZONE_NOT_FOUND_FOR_WAREHOUSE("WMS-1230","zone.not.found.for.given.warehouse" ),
    USER_WAREHOUSE_ID_NOT_FOUND("WMS-1231", "user.warehouse.id.not.found"),
    USER_CUSTOMER_ID_NOT_FOUND("WMS-1232", "user.customer.id.not.found"),
    AISLE_NOT_FOUND_FOR_ZONE("WMS-1233","aisle.not.found.for.given.zone" ),
    RACK_NOT_FOUND_FOR_AISLE("WMS-1234","rack.not.found.for.given.aisle" ),
    ZONE_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1235","zone.attribute.should.be.unique"),
    AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1236","aisle.attribute.should.be.unique"),
    DOOR_ID_DOES_NOT_EXIST("WMS-1237","door.id.does.not.exist"),
    RACK_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1238","rack.attribute.should.be.unique"),
    BIN_LOCATION_SHOULD_HAVE_LESSER_VOLUME_THAN_MASTER_LOCATION("WMS-1239","bin.location.should.have.lesser.volume.than.master.location"),
    RACK_SHOULD_BE_MANDATORY_FOR_RACK_TYPE_STORAGE("WMS-1240","rack.should.be.mandatory.for.rack.type.storage"),
    COLUMN_CODE_SHOULD_BE_MANDATORY_FOR_RACK_TYPE_STORAGE("WMS-1241","column.code.should.be.mandatory.for.rack.type.storage"),
    AISLE_SHOULD_BE_MANDATORY_FOR_RACK_TYPE_STORAGE("WMS-1242","aisle.should.be.mandatory.for.rack.type.storage"),
    QC_STATUS_ID_NOT_FOUND("WMS-1243","qc.status.id.not.found"),
    CONFIGURATION_FIELD_NOT_FOUND("WMS-1244", "configuration.field.not.found"),
    CUSTOMER_CONFIGURATION_FIELD_NOT_FOUND("WMS-1245", "customer.configuration.field.not.found"),
    MOVEMENT_TYPE_ID_NOT_FOUND("WMS-1246", "movement.type.id.not.found" ),
    ORIGIN_CODE_ALREADY_EXIST("WMS-1247", "origin.code.duplicate"),
    ORIGIN_NAME_ALREADY_EXIST("WMS-1248", "origin.name.duplicate"),
    ORIGIN_ID_NOT_FOUND("WMS-1249","origin.id.not.found"),
    SHIPMENT_ID_NOT_FOUND("WMS-1250","shipment.id.not.found"),
    TRN_HEADER_CUSTOMS_ADDL_DETAILS_ID_NOT_FOUND("WMS-1251","trn.header.customs.addl.details.id.not.found"),
    TRN_HEADER_ADDL_DETAILS_ID_NOT_FOUND("WMS-1252","trn.header.addl.details.id.not.found"),
    TRANSACTION_DETAIL_ID_NOT_FOUND("WMS-1253","trn.detail.id.not.found"),
    TRN_HEADER_ID_NOT_FOUND("WMS-1254","trn.header.id.not.found"),
    UNABLE_TO_GENERATE_TRANSACTION_UID("WMS-1255","unable.to.generate.transaction.uid"),
    TRN_HEADER_TRANSPORTATION_ID_NOT_FOUND("WMS-1256", "trn.header.transportation.id.not.found"),
    USER_ID_NOT_FOUND("WMS-1257", "user.id.not.found"),
    ADDL_DETAILS_MANDATORY_FIELD_IS_EMPTY("WMS-1258", "addl.details.mandatory.field.is.empty"),
    PARTY_DATA_FORMAT_INVALID("WMS-1259", "party.data.format.invalid"),
    SHIPPER_MUST_NOT_BE_NULL("WMS-1260","shipper.must.not.be.null"),
    USER_DOES_NOT_HAVE_ACCESS_TO_WAREHOUSE("WMS-1261", "user.does.not.have.to.warehouse"),
    USER_DOES_NOT_HAVE_ACCESS_TO_CUSTOMER("WMS-1262", "user.does.not.have.to.customer"),
    UOM_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1263","uom.attribute.should.be.unique"),
    CURRENCY_RATE_NOT_FOUND("WMS-1264","currency.rate.not.found"),
    STORAGE_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1265","storage.type.attribute.should.be.unique"),
    STORAGE_AREA_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1266","storage.area.attribute.should.be.unique"),
    LOC_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1267","loc.type.attribute.should.be.unique"),
    CONSIGNEE_MUST_NOT_BE_NULL("WMS-1269","consignee.must.not.be.null"),
    FORWARDER_MUST_NOT_BE_NULL("WMS-1270","forwarder.must.not.be.null"),
    TRANSPORTER_MUST_NOT_BE_NULL("WMS-1271","transporter.must.not.be.null"),
    DOOR_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1272","door.attribute.should.be.unique"),
    GRN_HEADER_MASTER_ID_NOT_FOUND("WMS-1273","grn.header.id.not.found"),
    SKU_LOT_ID_NOT_FOUND("WMS-1274","sku.lot.id.not.found"),
    TRN_HEADER_SO_ID_NOT_FOUND("WMS-1275","trn.so.id.not.found"),
    LOT_NAME_SHOULD_NOT_BE_NULL("WMS-1276","lot.name.should.not.be.null"),
    MOVEMENT_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1277","lot.name.should.not.be.null"),
    CUSTOMER_INVOICE_NO_SHOULD_BE_UNIQUE("WMS-1278","customer.invoice.no.should.be.unique"),
    CUSTOMER_ORDER_NO_SHOULD_BE_UNIQUE("WMS-1279","customer.order.no.should.be.unique"),
    UNABLE_TO_GENERATE_GRN_REF("WMS-1280","unable.to.generate.grn.ref"),
    INVENTORY_STATUS_MASTER_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1281","inventory.status.master.code.unique"),
    INVENTORY_STATUS_MASTER_ID_NOT_FOUND("WMS-1282","inventory.status.master.id.not.found"),
    COMPLETE_ALREADY_EXISTING_GRN("WMS-1283","complete.already.existing.grn"),
    GRN_CANNOT_BE_CANCELLED("WMS-1284","grn.cannot.be.cancelled"),
    GRN_ALREADY_CANCELLED("WMS-1285","grn.already.cancelled"),
    SKU_CODE_CANNOT_BE_UPDATE("WMS-1286","sku.cannot.be.updated"),
    CANCELLED_OR_GRN_CANNOT_BE_EDITED("WMS-1287","cancelled.or.grn.cannot.be.edited"),
    GRN_DETAIL_ID_NOT_FOUND("WMS-1288","grn.detail.id.not.found"),
    PUTAWAY_HEADER_NOT_FOUND("WMS-1289","putaway.header.not.found"),
    ORDER_TYPE_ID_NOT_FOUND("WMS-1280","order.type,id.not.found"),
    ORDER_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1281","configuration.flag.code.should.be.unique"),
    CONFIGURATION_FLAG_ATTRIBUTE_SHOULD_BE_UNIQUE("WMS-1282","configuration.flag.code.should.be.unique"),
    CONFIGURATION_FLAG_ID_NOT_FOUND("WMS-1283","configuration.flag,id.not.found");

    
    public final String CODE;
    public final String KEY;

    ServiceErrors(String code, String key) {
        this.CODE = code;
        this.KEY = key;
    }

    public static Set<String> getMessageKeys() {
        return Arrays.stream(ServiceErrors.values())
                .map(n -> n.KEY)
                .collect(Collectors.toSet());
    }
}