package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.SkuBulkDTO;
import com.newage.wms.application.dto.requestdto.SkuRequestDTO;
import com.newage.wms.application.dto.requestdto.SkuRequestDTOList;
import com.newage.wms.application.dto.responsedto.*;
import com.newage.wms.entity.*;
import com.newage.wms.entity.QUomMaster;
import com.newage.wms.service.*;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.newage.wms.service.impl.GrnCalculation;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class SkuMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;


    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private CustomerMasterService customerMasterService;

    @Autowired
    private UomMasterService uomMasterService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private GrnCalculation grnCalculation;

    @Autowired
    private StorageAreaMasterService storageAreaMasterService;

    @Autowired
    private StorageTypeMasterService storageTypeMasterService;

    @Autowired
    private HsCodeMasterService hsCodeMasterService;

    @Autowired
    private ImcoCodeMasterService imcoCodeMasterService;

    @Autowired
    private CurrencyMasterService currencyMasterService;

    @Autowired
    private SkuPackDetailService skuPackDetailService;

    @Autowired
    private SkuLotDetailsMapper skuLotDetailsMapper;

    @Autowired
    private SkuMasterService skuMasterService;

    private Integer rowNo1;

    private List<String> errorList;

    private List<SkuMaster> skuMasterList;

    private Boolean shouldReturnNullSave;

    private String secondary="Secondary";

    /*
     * Method to convert Sku Page to SkuResponse Page
     * @Return Page<SkuResponseDTO>
     */
    public Page<SkuResponseDTO> convertPageToResponsePage(Page<SkuMaster> skuPage) {
        log.info("ENTRY - Sku Page to SkuResponse Page mapper");
        List<SkuResponseDTO> skuResponseDTOList = skuPage.getContent()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(skuResponseDTOList,skuPage.getPageable(),skuPage.getTotalElements());
    }

    public List<UomSkuPackDTO> convertResponseToUomSkuPackDTOList(List<SkuResponseDTO.SkuPackDetailDTO> skuPackDetailDTOList) {
        return skuPackDetailDTOList.stream()
                .map(this::convertResponseToUomSkuPackDTO)
                .collect(Collectors.toList());
    }

    private UomSkuPackDTO convertResponseToUomSkuPackDTO(SkuResponseDTO.SkuPackDetailDTO skuPackDetailDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        UomSkuPackDTO uomSkuPackDTO = new UomSkuPackDTO();
        if (skuPackDetailDTO.getUom() != null) {
            modelMapper.map(skuPackDetailDTO.getUom(), uomSkuPackDTO);
        }
        uomSkuPackDTO.setSkuPackId(skuPackDetailDTO.getId());
        uomSkuPackDTO.setUomType(skuPackDetailDTO.getUomType().toLowerCase());
        return uomSkuPackDTO;
    }

    public Iterable<TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.SkuDTO> convertEntityIterableToAutoCompleteIterable(Iterable<SkuMaster> skuMasterIterable) {
        return StreamSupport.stream(skuMasterIterable.spliterator(),false)
                .map(this::convertEntityToAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert Sku Request to SkuEntity
     * @Return SkuMaster
     */
    public SkuMaster convertRequestDtoToEntity(SkuRequestDTO skuRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - Sku Request to SkuEntity mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        SkuMaster skuMaster = modelMapper.map(skuRequestDTO.getSkuDetails(),SkuMaster.class);
        modelMapper.map(dateAndTimeRequestDto,skuMaster);
        if (skuRequestDTO.getSkuMoreDetails() != null){
            modelMapper.map(skuRequestDTO.getSkuMoreDetails(),skuMaster);
        }
        skuMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(skuRequestDTO.getBranchId())));
        skuMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(skuRequestDTO.getCompanyId())));
        skuMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(skuRequestDTO.getGroupCompanyId())));
        skuMaster.setCustomerMaster(customerMasterService.getCustomerById(Long.parseLong(skuRequestDTO.getSkuDetails().getCustomer().getId())));
        skuMaster.setBaseUnitOfMeasurement(uomMasterService.getUomById(Long.parseLong(skuRequestDTO.getSkuDetails().getBaseUnitOfMeasure().getId())));
        skuMaster.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterById(Long.parseLong(skuRequestDTO.getSkuDetails().getStorageArea().getId())));
        skuMaster.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterById(Long.parseLong(skuRequestDTO.getSkuDetails().getStorageType().getId())));
        if (skuRequestDTO.getSkuDetails().getHsCode() != null) {
            skuMaster.setHsCodeMaster(hsCodeMasterService.getById(Long.parseLong(skuRequestDTO.getSkuDetails().getHsCode().getId())));
        }
        if (skuRequestDTO.getSkuDetails().getImcoCode() != null) {
            skuMaster.setImcoCodeMaster(imcoCodeMasterService.getById(Long.parseLong(skuRequestDTO.getSkuDetails().getImcoCode().getId())));
        }
        if (skuRequestDTO.getSkuDetails().getCurrency() != null) {
            skuMaster.setCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(skuRequestDTO.getSkuDetails().getCurrency().getId())));
        }
        byte[] skuImage = convertStringToByteStream(skuRequestDTO);
        skuMaster.setSkuImageByte(skuImage);
        List<SkuRequestDTO.SkuPackDetailDTO> skuPackDetailDTOList;
        List<SkuPackDetail> skuPackDetailList = new ArrayList<>();
        if (skuRequestDTO.getPackDetails().getSkuPackDetailDTOList() != null){
            skuPackDetailDTOList = skuRequestDTO.getPackDetails().getSkuPackDetailDTOList();
            for (SkuRequestDTO.SkuPackDetailDTO skuPackDetailDTO : skuPackDetailDTOList){
                SkuPackDetail skuPackDetail = modelMapper.map(skuPackDetailDTO,SkuPackDetail.class);
                modelMapper.map(dateAndTimeRequestDto,skuPackDetail);
                skuPackDetail.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(skuRequestDTO.getBranchId())));
                skuPackDetail.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(skuRequestDTO.getCompanyId())));
                skuPackDetail.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(skuRequestDTO.getGroupCompanyId())));
                skuPackDetail.setUomMaster(uomMasterService.getUomById(Long.parseLong(skuPackDetailDTO.getUom().getId())));
                //check the uom
                UomMaster uomMasterByCode = uomMasterService.getUomByCode(skuPackDetailDTO.getUom().getCode());
                SkuPackDetail skuPackDetail1 = modelMapper.map(skuPackDetailDTO,SkuPackDetail.class);
                modelMapper.map(dateAndTimeRequestDto, skuPackDetail1);
                skuPackDetail1.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(skuRequestDTO.getBranchId())));
                skuPackDetail1.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(skuRequestDTO.getCompanyId())));
                skuPackDetail1.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(skuRequestDTO.getGroupCompanyId())));
                skuPackDetail1.setUomMaster(uomMasterService.getUomById(Long.parseLong(skuPackDetailDTO.getUom().getId())));
                String reference= skuPackDetail.getUomMaster().getReference();
                if(reference!=null) {
                    if ("BR".equalsIgnoreCase(reference) || "SR".equalsIgnoreCase(reference)) {
                        Long categoryId = uomMasterByCode.getCategoryMaster().getId();
                        //Filter by Category Id based on get the reference
                        Collection<Predicate> predicates = new ArrayList<>();
                        predicates.add(QUomMaster.uomMaster.categoryMaster.id.eq(categoryId));
                        Predicate predicateAll = ExpressionUtils.allOf(predicates);
                        Page<UomMaster> uomMasterPage = uomMasterService.findAll(predicateAll, Pageable.unpaged());
                        UomMaster filteredUom = uomMasterPage.getContent().stream()
                                .filter(uom -> "R".equalsIgnoreCase(uom.getReference()))
                                .findFirst()
                                .orElse(null);
                        String code = filteredUom.getCode();

                        Boolean isAvailable = skuPackDetailDTOList.stream()
                                .anyMatch(dto -> code.equals(dto.getUom().getCode()));
                        Boolean isAvailable1 = skuPackDetailList.stream()
                                .anyMatch(sku2 -> code.equals(sku2.getUomMaster().getCode()));
                        skuPackDetail1.setUomMaster(filteredUom);
                        skuPackDetail1.setUomType(secondary);
                        skuPackDetail1.setRatio(String.valueOf(filteredUom.getRatio()));
                        double givenLenght = Double.parseDouble(skuPackDetailDTO.getLength());
                        double ratio = Double.parseDouble(skuPackDetail.getRatio());
                        double length = givenLenght / ratio;
                        double height = Double.parseDouble(skuPackDetailDTO.getHeight());
                        double width = Double.parseDouble(skuPackDetailDTO.getWidth());
                        double volume = (length * height * width);
                        String roundedVolume = String.valueOf(grnCalculation.getRoundedValue(5, volume));
                        String roundedLength = String.valueOf( grnCalculation.getRoundedValue(5, length));
                        skuPackDetail1.setVol(roundedVolume);
                        skuPackDetail1.setLength(roundedLength);
                        if (!isAvailable && !isAvailable1) {
                            skuPackDetailList.add(skuPackDetail1);
                        }
                    }
                }
                skuPackDetailList.add(skuPackDetail);
            }
        }
        skuMaster.setSkuPackDetailList(skuPackDetailList);
        skuMaster.setSkuLotDetailsList(skuLotDetailsMapper.convertRequestLotDtoToEntityLotList(skuRequestDTO,dateAndTimeRequestDto));
        skuMaster.setName(skuRequestDTO.getSkuDetails().getName().toUpperCase());
        skuMaster.setCode(skuRequestDTO.getSkuDetails().getCode().toUpperCase());
        log.info("EXIT");
        return skuMaster;
    }

    /*
     * Method to convert Sku Update Request to SkuEntity
     */
    public void convertUpdateRequestToEntity(SkuRequestDTO skuRequestDTO, SkuMaster skuMaster, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - Sku Update Request to SkuEntity mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        modelMapper.map(skuRequestDTO.getSkuDetails(),skuMaster);
        modelMapper.map(dateAndTimeRequestDto,skuMaster);
        List<SkuLotDetails> skuLotDetailsToBeDeleted = skuMaster.getSkuLotDetailsList();
        if (skuRequestDTO.getSkuMoreDetails() != null){
            modelMapper.map(skuRequestDTO.getSkuMoreDetails(),skuMaster);
        }
        skuMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(skuRequestDTO.getGroupCompanyId())));
        skuMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(skuRequestDTO.getBranchId())));
        skuMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(skuRequestDTO.getCompanyId())));
        skuMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(skuRequestDTO.getGroupCompanyId())));
        skuMaster.setCustomerMaster(customerMasterService.getCustomerById(Long.parseLong(skuRequestDTO.getSkuDetails().getCustomer().getId())));
        skuMaster.setBaseUnitOfMeasurement(uomMasterService.getUomById(Long.parseLong(skuRequestDTO.getSkuDetails().getBaseUnitOfMeasure().getId())));
        skuMaster.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterById(Long.parseLong(skuRequestDTO.getSkuDetails().getStorageArea().getId())));
        skuMaster.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterById(Long.parseLong(skuRequestDTO.getSkuDetails().getStorageType().getId())));
        if(skuRequestDTO.getSkuDetails().getHsCode()!=null){
            skuMaster.setHsCodeMaster(hsCodeMasterService.getById(Long.parseLong(skuRequestDTO.getSkuDetails().getHsCode().getId())));
        }
        if(skuRequestDTO.getSkuDetails().getImcoCode()!=null){
            skuMaster.setImcoCodeMaster(imcoCodeMasterService.getById(Long.parseLong(skuRequestDTO.getSkuDetails().getImcoCode().getId())));
        }
        if(skuRequestDTO.getSkuDetails().getCurrency()!=null){
            skuMaster.setCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(skuRequestDTO.getSkuDetails().getCurrency().getId())));
        }
        byte[] skuImage = convertStringToByteStream(skuRequestDTO);
        skuMaster.setSkuImageByte(skuImage);
        List<SkuRequestDTO.SkuPackDetailDTO> skuPackDetailDTOList;
        List<SkuPackDetail> skuPackDetailList = new ArrayList<>();
        if (skuRequestDTO.getPackDetails().getSkuPackDetailDTOList() != null){
            skuPackDetailDTOList = skuRequestDTO.getPackDetails().getSkuPackDetailDTOList();
            for (SkuRequestDTO.SkuPackDetailDTO skuPackDetailDTO : skuPackDetailDTOList){
                SkuPackDetail skuPackDetail = new SkuPackDetail();
                if (skuPackDetailDTO.getId() != null && !skuPackDetailDTO.getId().isBlank() && !skuPackDetailDTO.getId().isEmpty()){
                    skuPackDetail = skuPackDetailService.getById(Long.parseLong(skuPackDetailDTO.getId()));
                }
                modelMapper.map(skuPackDetailDTO,skuPackDetail);
                modelMapper.map(dateAndTimeRequestDto,skuPackDetail);
                skuPackDetail.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(skuRequestDTO.getBranchId())));
                skuPackDetail.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(skuRequestDTO.getCompanyId())));
                skuPackDetail.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(skuRequestDTO.getGroupCompanyId())));
                skuPackDetail.setUomMaster(uomMasterService.getUomById(Long.parseLong(skuPackDetailDTO.getUom().getId())));
                //check the uom
                UomMaster uomMasterByCode = uomMasterService.getUomByCode(skuPackDetailDTO.getUom().getCode());
                SkuPackDetail skuPackDetail1 = modelMapper.map(skuPackDetailDTO,SkuPackDetail.class);
                modelMapper.map(dateAndTimeRequestDto, skuPackDetail1);
                skuPackDetail1.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(skuRequestDTO.getBranchId())));
                skuPackDetail1.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(skuRequestDTO.getCompanyId())));
                skuPackDetail1.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(skuRequestDTO.getGroupCompanyId())));
                skuPackDetail1.setUomMaster(uomMasterService.getUomById(Long.parseLong(skuPackDetailDTO.getUom().getId())));
                String reference= skuPackDetail.getUomMaster().getReference();
                if(reference!=null) {
                    if ("BR".equalsIgnoreCase(reference) || "SR".equalsIgnoreCase(reference)) {
                        Long categoryId = uomMasterByCode.getCategoryMaster().getId();
                        //Filter by Category Id based on get the reference
                        Collection<Predicate> predicates = new ArrayList<>();
                        predicates.add(QUomMaster.uomMaster.categoryMaster.id.eq(categoryId));
                        Predicate predicateAll = ExpressionUtils.allOf(predicates);
                        Page<UomMaster> uomMasterPage = uomMasterService.findAll(predicateAll, Pageable.unpaged());
                        UomMaster filteredUom = uomMasterPage.getContent().stream()
                                .filter(uom -> "R".equalsIgnoreCase(uom.getReference()))
                                .findFirst()
                                .orElse(null);
                        String code = filteredUom.getCode();

                        Boolean isAvailable = skuPackDetailDTOList.stream()
                                .anyMatch(dto -> code.equals(dto.getUom().getCode()));
                        Boolean isAvailable1 = skuPackDetailList.stream()
                                .anyMatch(sku2 -> code.equals(sku2.getUomMaster().getCode()));
                        skuPackDetail1.setUomMaster(filteredUom);
                        skuPackDetail1.setUomType(secondary);
                        skuPackDetail1.setRatio(String.valueOf(filteredUom.getRatio()));
                        double givenLenght = Double.parseDouble(skuPackDetailDTO.getLength());
                        double ratio = Double.parseDouble(skuPackDetail.getRatio());
                        double length = givenLenght / ratio;
                        double height = Double.parseDouble(skuPackDetailDTO.getHeight());
                        double width = Double.parseDouble(skuPackDetailDTO.getWidth());
                        double volume = (length * height * width);
                        String roundedVolume = String.valueOf(grnCalculation.getRoundedValue(5, volume));
                        String roundedLength = String.format("%10f", grnCalculation.getRoundedValue(5, length));
                        skuPackDetail1.setVol(roundedVolume);
                        skuPackDetail1.setLength(roundedLength);
                        if (!isAvailable && !isAvailable1) {
                            skuPackDetailList.add(skuPackDetail1);
                        }
                    }
                }
                skuPackDetailList.add(skuPackDetail);
            }
        }
        skuMaster.setSkuPackDetailList(skuPackDetailList);
        skuMaster.setSkuLotDetailsList(skuLotDetailsMapper.convertUpdateRequestLotDtoToEntityLotList(skuRequestDTO,dateAndTimeRequestDto));
        skuLotDetailsMapper.deleteNonExistingIds(skuLotDetailsToBeDeleted,skuRequestDTO);
        skuMaster.setName(skuRequestDTO.getSkuDetails().getName().toUpperCase());
        skuMaster.setCode(skuRequestDTO.getSkuDetails().getCode().toUpperCase());
        log.info("EXIT");
    }

    /*
     * Method to convert SkuEntity to SkuResponseDTO
     * @Return SkuResponseDTO
     */
    public SkuResponseDTO convertEntityToDTO(SkuMaster skuMaster) {
        log.info("ENTRY - Sku Entity to SkuResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        SkuResponseDTO skuResponseDTO = modelMapper.map(skuMaster, SkuResponseDTO.class);
        SkuResponseDTO.MoreDetailsDTO moreDetailsDTO = modelMapper.map(skuMaster,SkuResponseDTO.MoreDetailsDTO.class);
        moreDetailsDTO.setSkuImage(convertByteStreamToString(skuMaster));
        skuResponseDTO.setSkuMoreDetails(moreDetailsDTO);
        SkuResponseDTO.SkuDetailsDTO skuDetailsDTO = modelMapper.map(skuMaster, SkuResponseDTO.SkuDetailsDTO.class);
        skuDetailsDTO.setBranch(mapToDTOIfNotNull(skuMaster.getBranchMaster(), SkuResponseDTO.BranchDTO.class));
        skuDetailsDTO.setCompany(mapToDTOIfNotNull(skuMaster.getCompanyMaster(), SkuResponseDTO.CompanyDTO.class));
        skuDetailsDTO.setGroupCompany(mapToDTOIfNotNull(skuMaster.getGroupCompanyMaster(), SkuResponseDTO.GroupCompanyDTO.class));
        skuDetailsDTO.setCustomer(mapToDTOIfNotNull(skuMaster.getCustomerMaster(), SkuResponseDTO.CustomerDTO.class));
        if (skuMaster.getBaseUnitOfMeasurement() != null){
            SkuResponseDTO.UomDTO uomDTO = modelMapper.map(skuMaster.getBaseUnitOfMeasurement(),SkuResponseDTO.UomDTO.class);
            skuDetailsDTO.setBaseUnitOfMeasure(uomDTO);
            if (skuMaster.getBaseUnitOfMeasurement().getCategoryMaster() != null){
                SkuResponseDTO.UomDTO.CategoryDTO categoryDTO = modelMapper.map(skuMaster.getBaseUnitOfMeasurement().getCategoryMaster(),SkuResponseDTO.UomDTO.CategoryDTO.class);
                skuDetailsDTO.getBaseUnitOfMeasure().setCategory(categoryDTO);
            }
        }
        skuDetailsDTO.setStorageArea(mapToDTOIfNotNull(skuMaster.getStorageAreaMaster(), SkuResponseDTO.StorageAreaDTO.class));
        skuDetailsDTO.setStorageType(mapToDTOIfNotNull(skuMaster.getStorageTypeMaster(), SkuResponseDTO.StorageTypeDTO.class));
        skuDetailsDTO.setHsCode(mapToDTOIfNotNull(skuMaster.getHsCodeMaster(), SkuResponseDTO.HsCodeDTO.class));
        skuDetailsDTO.setImcoCode(mapToDTOIfNotNull(skuMaster.getImcoCodeMaster(), SkuResponseDTO.ImcoCodeDTO.class));
        skuDetailsDTO.setCurrency(mapToDTOIfNotNull(skuMaster.getCurrencyMaster(), SkuResponseDTO.CurrencyDTO.class));
        if (skuMaster.getBreakPackForPick() != null){
            skuDetailsDTO.setBreakPackForPick(characterToString(skuMaster.getBreakPackForPick()));
        }
        skuResponseDTO.setSkuDetails(skuDetailsDTO);
        skuResponseDTO.setCreatedUser(skuMaster.getCreatedBy());
        skuResponseDTO.setUpdatedDate(skuMaster.getLastModifiedDate());
        skuResponseDTO.setUpdatedUser(skuMaster.getLastModifiedBy());
        if (skuMaster.getSkuPackDetailList() != null){
            List<SkuPackDetail> skuPackDetailList = skuMaster.getSkuPackDetailList();
            List<SkuResponseDTO.SkuPackDetailDTO> skuPackDetailDTOList = new ArrayList<>();
            for (SkuPackDetail skuPackDetail : skuPackDetailList){
                SkuResponseDTO.SkuPackDetailDTO skuPackDetailDTO = modelMapper.map(skuPackDetail,SkuResponseDTO.SkuPackDetailDTO.class);
                if (skuPackDetail.getUomMaster() != null) {
                    SkuResponseDTO.SkuPackDetailDTO.UomDTO uomDTO = modelMapper.map(skuPackDetail.getUomMaster(), SkuResponseDTO.SkuPackDetailDTO.UomDTO.class);
                    if (skuPackDetail.getUomMaster().getCategoryMaster() != null){
                        SkuResponseDTO.SkuPackDetailDTO.UomDTO.CategoryDTO categoryDTO = modelMapper.map(skuPackDetail.getUomMaster().getCategoryMaster(),SkuResponseDTO.SkuPackDetailDTO.UomDTO.CategoryDTO.class);
                        uomDTO.setCategory(categoryDTO);
                    }
                    skuPackDetailDTO.setUom(uomDTO);
                }
                skuPackDetailDTO.setCreatedUser(skuPackDetail.getCreatedBy());
                skuPackDetailDTO.setUpdatedDate(skuPackDetail.getLastModifiedDate());
                skuPackDetailDTO.setUpdatedUser(skuPackDetail.getLastModifiedBy());
                skuPackDetailDTOList.add(skuPackDetailDTO);
            }
            SkuResponseDTO.PackDetailsDTO packDetailsDTO = new SkuResponseDTO.PackDetailsDTO();
            packDetailsDTO.setSkuPackDetailDTOList(skuPackDetailDTOList);
            skuResponseDTO.setPackDetails(packDetailsDTO);
        }
        skuResponseDTO.setSkuLotDetails(skuLotDetailsMapper.convertEntityLotListToLotResponse(skuMaster));
        log.info("EXIT");
        return skuResponseDTO;
    }

    public TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.SkuDTO convertEntityToAutoCompleteDTO(SkuMaster skuMaster){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        return modelMapper.map(skuMaster, TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.SkuDTO.class);
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

    private byte[] convertStringToByteStream(SkuRequestDTO skuRequestDTO) {
        if (skuRequestDTO.getSkuMoreDetails() != null && skuRequestDTO.getSkuMoreDetails().getSkuImage() != null &&
                !skuRequestDTO.getSkuMoreDetails().getSkuImage().isBlank()){
            String stringImage = skuRequestDTO.getSkuMoreDetails().getSkuImage();
            byte[] byteImage = stringImage.getBytes();
            return Base64.getMimeDecoder().decode(byteImage);
        }
        return new byte[0];
    }

    public String convertByteStreamToString(SkuMaster skuMaster) {
        if (skuMaster.getSkuImageByte() != null){
            byte[] byteaData = skuMaster.getSkuImageByte();
            String skuImageString = Base64.getEncoder().encodeToString(byteaData);
            if (skuImageString.isBlank() || skuImageString.isEmpty()){
                return null;
            }
            return skuImageString;
        }
        return null;
    }

    /*
     * Method to convert Boolean to "Yes" or "No" string
     * @Return "Yes" or "No" string
     */
    private String characterToString(Character inputCharacter) {
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

    public List<SkuResponseDTO.LotDTO> convertLotDetailsListToDTOList(List<SkuLotDetails> lotDetailsList) {
        List<SkuResponseDTO.LotDTO> lotDTOListAll = new ArrayList<>();
        int i=1;
        for (SkuLotDetails lotDetails : lotDetailsList){
            SkuResponseDTO.LotDTO lotDetailsDTO = new SkuResponseDTO.LotDTO();
            lotDetailsDTO.setId(lotDetails.getId());
            lotDetailsDTO.setIsMandatory(lotDetails.getIsMandatory());
            lotDetailsDTO.setLabel(lotDetails.getLabel());
            lotDetailsDTO.setIsStaticLot("No");
            lotDetailsDTO.setName(lotDetails.getFieldName());
            if (lotDetailsDTO.getName() == null){
                lotDetailsDTO.setName("lot0"+i);
            }
            if (lotDetailsDTO.getName() == null && i== 10){
                lotDetailsDTO.setName("lot10");
            }
            if (lotDetails.getLabel() != null && !lotDetails.getLabel().isEmpty() && !lotDetails.getLabel().isBlank()){
                lotDTOListAll.add(lotDetailsDTO) ;
            }
            i++;
        }
        return lotDTOListAll;
    }

    public SkuBulkResponseDTO convertRequestListToEntityList(SkuRequestDTOList skuRequestDTOList, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("Entry - SkuBulkRequest List to Sku List Mapper");
        rowNo1 = 1;
        List<SkuBulkDTO> skuBulkDTOS = skuRequestDTOList.getSkuBulkDTOList();
        for (SkuBulkDTO bulkDTO : skuBulkDTOS) {
            String currentCode = bulkDTO.getCode().toUpperCase();
            String name = bulkDTO.getName().toUpperCase();
            String customerCode = bulkDTO.getCustomerCode().toUpperCase();
            String storageAreaCode = bulkDTO.getStorageAreaCode().toUpperCase();
            String storageTypeCode = bulkDTO.getStorageTypeCode().toUpperCase();
            String baseUnitOfMeasureCode = bulkDTO.getBaseUnitOfMeasureCode().toUpperCase();
            if (bulkDTO.getHsCodeCode() != null && !bulkDTO.getHsCodeCode().isEmpty() && !bulkDTO.getHsCodeCode().isBlank()) {
                String hsCodeCode = bulkDTO.getHsCodeCode().toUpperCase();
                bulkDTO.setHsCodeCode(hsCodeCode);
            }
            if (bulkDTO.getImcoCodeCode() != null && !bulkDTO.getImcoCodeCode().isEmpty() && !bulkDTO.getImcoCodeCode().isBlank()) {
                String imcoCodeCode = bulkDTO.getImcoCodeCode().toUpperCase();
                bulkDTO.setImcoCodeCode(imcoCodeCode);
            }
            if (bulkDTO.getCurrencyCode() != null && !bulkDTO.getCurrencyCode().isEmpty() && !bulkDTO.getCurrencyCode().isBlank()) {
                String currencyCode = bulkDTO.getCurrencyCode().toUpperCase();
                bulkDTO.setCurrencyCode(currencyCode);
            }
            String uomCode = bulkDTO.getUomCode().toUpperCase();
            bulkDTO.setCode(currentCode);
            bulkDTO.setName(name);
            bulkDTO.setCustomerCode(customerCode);
            bulkDTO.setStorageAreaCode(storageAreaCode);
            bulkDTO.setStorageTypeCode(storageTypeCode);
            bulkDTO.setBaseUnitOfMeasureCode(baseUnitOfMeasureCode);
            bulkDTO.setUomCode(uomCode);
        }
        Map<String, Map<String, List<SkuBulkDTO>>> skuCodeMap = skuRequestDTOList.getSkuBulkDTOList().stream()
                .collect(Collectors.groupingBy(SkuBulkDTO::getCode,
                        Collectors.groupingBy(SkuBulkDTO::getCustomerCode)));

        errorList = new ArrayList<>();
        skuMasterList = skuMasterService.findAll();
        List<SkuMaster> skuEntityList = new ArrayList<>();

        // Accessing the nested map entries
        for (Map.Entry<String, Map<String, List<SkuBulkDTO>>> codeEntry : skuCodeMap.entrySet()) {
            String code = codeEntry.getKey();
            Map<String, List<SkuBulkDTO>> customerCodeMap = codeEntry.getValue();

            for (Map.Entry<String, List<SkuBulkDTO>> customerCodeEntry : customerCodeMap.entrySet()) {
                String customerCode = customerCodeEntry.getKey();
                List<SkuBulkDTO> skuBulkDTOList = customerCodeEntry.getValue();

                // Check if the combination of code and customerCode already exists
                if (skuAlreadyExits(code, customerCode, skuMasterList)) {
                    throw new ServiceException(
                            ServiceErrors.SKU_CODE_CANNOT_BE_UPDATE.CODE,
                            ServiceErrors.SKU_CODE_CANNOT_BE_UPDATE.KEY);

                } else {
                    Set<String> duplicateSet = new HashSet<>();
                    Boolean isDuplicate = !skuBulkDTOList.stream()
                            .map(skuBulkDTO -> skuBulkDTO.getUomCode().toLowerCase() + "-" + skuBulkDTO.getUomType().toLowerCase() + "-"
                                    + skuBulkDTO.getCode().toLowerCase() + "-" + skuBulkDTO.getCustomerCode().toLowerCase())
                            .anyMatch(duplicate -> !duplicateSet.add(duplicate));
                    if (!isDuplicate) {
                        throw new ServiceException(
                                ServiceErrors.DATA_HAS_DUPLICATE_VALUES.CODE,
                                ServiceErrors.DATA_HAS_DUPLICATE_VALUES.KEY);
                    } else {
                        skuEntityList.add(convertBulkRequestToSave(skuBulkDTOList, dateAndTimeRequestDto));
                    }
                }

            }
        }

        SkuBulkResponseDTO skuBulkResponseDTO = new SkuBulkResponseDTO();
        skuEntityList.removeIf(Objects::isNull);
        if (errorList.isEmpty()) {
            skuBulkResponseDTO.setSkuList(skuEntityList);
            skuBulkResponseDTO.setErrorList(null);
        } else {
            skuBulkResponseDTO.setSkuList(null);
            skuBulkResponseDTO.setErrorList(errorList);
        }
        log.info("EXIT");
        return skuBulkResponseDTO;
    }

    private SkuPackDetail setBranchCompanyGroupCompanyInPack(SkuPackDetail skuPackDetail, SkuBulkDTO skuBulkDTO) {
        try {
            skuPackDetail.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(skuBulkDTO.getGroupCompanyId())));
        } catch (ServiceException serviceException) {
            errorList.add("Row" + rowNo1 + "GroupCompany Id does not exist");
        }
        try {
            skuPackDetail.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(skuBulkDTO.getCompanyId())));
        } catch (ServiceException serviceException) {
            errorList.add("Row" + rowNo1 + "Company Id does not exist");
        }
        try {
            skuPackDetail.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(skuBulkDTO.getBranchId())));
        } catch (ServiceException serviceException) {
            errorList.add("Row" + rowNo1 + "Branch Id does not exist");
        }
        return skuPackDetail;

    }

    private SkuMaster convertBulkRequestToSave(List<SkuBulkDTO> skuBulkDTOList, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - Bulk Request to Entity mapper save");
        shouldReturnNullSave = false;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        int listSize = skuBulkDTOList.size();
        SkuBulkDTO skuBulkDTO = skuBulkDTOList.get(listSize-1);
        SkuMaster skuMaster = modelMapper.map(skuBulkDTO, SkuMaster.class);
        modelMapper.map(dateAndTimeRequestDto, skuMaster);
        skuMaster = setBranchCompanyGroupCompany(skuMaster, skuBulkDTO);
        skuMaster = setStorageArea(skuMaster, skuBulkDTO);
        skuMaster = setStorageType(skuMaster, skuBulkDTO);
        skuMaster = setCustomerMaster(skuMaster,skuBulkDTO);
        skuMaster = setImcoCode(skuMaster,skuBulkDTO);
        skuMaster = setCurrencyMaster(skuMaster,skuBulkDTO);
        skuMaster = setUomMaster(skuMaster,skuBulkDTO);
        skuMaster.setSkuLotDetailsList(convertSkuLotList(dateAndTimeRequestDto));
        List<SkuPackDetail> skuPackDetailList = new ArrayList<>();
        for (SkuBulkDTO skuBulkDTO1 : skuBulkDTOList) {
            SkuPackDetail skuPackDetail = modelMapper.map(skuBulkDTO1, SkuPackDetail.class);
            modelMapper.map(dateAndTimeRequestDto, skuPackDetail);
            skuPackDetail = setUomMasterInPack(skuPackDetail,skuBulkDTO1);
            skuPackDetail = setBranchCompanyGroupCompanyInPack(skuPackDetail,skuBulkDTO1);
            SkuPackDetail skuPackDetail1 = modelMapper.map(skuBulkDTO1, SkuPackDetail.class);
            modelMapper.map(dateAndTimeRequestDto, skuPackDetail1);
            skuPackDetail1 = setBranchCompanyGroupCompanyInPack(skuPackDetail1,skuBulkDTO1);
            UomMaster uomMasterByCode = uomMasterService.getUomByCode(skuBulkDTO1.getUomCode());
            // If Given reference is BR
            if(uomMasterByCode!=null) {
                String reference = uomMasterByCode.getReference();
                if ("BR".equalsIgnoreCase(reference)||"SR".equalsIgnoreCase(reference)) {
                    Long categoryId = uomMasterByCode.getCategoryMaster().getId();
                    //Filter by Category Id based on get the reference
                    Collection<Predicate> predicates = new ArrayList<>();
                    predicates.add(QUomMaster.uomMaster.categoryMaster.id.eq(categoryId));
                    Predicate predicateAll = ExpressionUtils.allOf(predicates);
                    Page<UomMaster> uomMasterPage = uomMasterService.findAll(predicateAll, Pageable.unpaged());
                    UomMaster filteredUom = uomMasterPage.getContent().stream()
                            .filter(uom -> "R".equalsIgnoreCase(uom.getReference()))
                            .findFirst()
                            .orElse(null);
                    String code = filteredUom.getCode();

                    Boolean isAvailable = skuBulkDTOList.stream()
                            .anyMatch(dto -> code.equals(dto.getUomCode()));
                    Boolean isAvailable1 = skuPackDetailList.stream()
                            .anyMatch(sku2 -> code.equals(sku2.getUomMaster().getCode()));
                    skuPackDetail1.setUomMaster(filteredUom);
                    skuPackDetail1.setUomType(secondary);
                    skuPackDetail1.setRatio(String.valueOf(filteredUom.getRatio()));
                    double givenLength = Double.parseDouble(skuBulkDTO1.getLength());
                    double ratio = Double.parseDouble(skuPackDetail1.getRatio());
                    double length = givenLength / ratio;
                    double height = Double.parseDouble(skuBulkDTO1.getHeight());
                    double width = Double.parseDouble(skuBulkDTO1.getWidth());
                    double volume = (length * height * width);
                    String roundedVolume = String.valueOf(grnCalculation.getRoundedValue(5, volume));
                    String roundedLength = String.valueOf(grnCalculation.getRoundedValue(5, length));
                    skuPackDetail1.setVol(roundedVolume);
                    skuPackDetail1.setLength(roundedLength);
                    if (!isAvailable && !isAvailable1) {
                        skuPackDetailList.add(skuPackDetail1);
                    }
                }
            }
            skuPackDetailList.add(skuPackDetail);
        }
        skuMaster.setSkuPackDetailList(skuPackDetailList);
        log.info("EXIT");
        if (shouldReturnNullSave) {
            return null;
        } else {
            return skuMaster;
        }
    }

    private List<SkuLotDetails> convertSkuLotList(DateAndTimeRequestDto dateAndTimeRequestDto) {
        SkuLotDetails skuLotDetails=new SkuLotDetails();
        modelMapper.map(dateAndTimeRequestDto, skuLotDetails);

        List<SkuLotDetails> skuLotDetailsList= new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            SkuLotDetails skuLotDetail = new SkuLotDetails();
            modelMapper.map(dateAndTimeRequestDto, skuLotDetail);
            skuLotDetail.setFieldName("Lot0"+i);
            skuLotDetail.setIsMandatory("No");
            skuLotDetail.setLabel("");
            skuLotDetailsList.add(skuLotDetail);
        }
        skuLotDetails.setFieldName("Lot10");
        skuLotDetails.setIsMandatory("No");
        skuLotDetails.setLabel("");
        skuLotDetailsList.add(skuLotDetails);

        return skuLotDetailsList;
    }


    private SkuPackDetail setUomMasterInPack(SkuPackDetail skuPackDetail, SkuBulkDTO skuBulkDTO) {
        if (skuBulkDTO.getUomCode() != null && !skuBulkDTO.getUomCode().isBlank() && !skuBulkDTO.getUomCode().isEmpty()) {
            UomMaster uomMaster = uomMasterService.getUomByCode(skuBulkDTO.getUomCode());
            if (uomMaster != null) {
                skuPackDetail.setUomMaster(uomMaster);
                skuPackDetail.setRatio(String.valueOf(uomMaster.getRatio()));
            } else {
                errorList.add("Row " + rowNo1 + " Uom does not exist");
                shouldReturnNullSave = true;
            }
        } else {
            errorList.add("Row " + rowNo1 + " Uom is missing");
            shouldReturnNullSave = true;
        }
        return skuPackDetail;
    }


    private SkuMaster setUomMaster(SkuMaster skuMaster, SkuBulkDTO skuBulkDTO){
        if (skuBulkDTO.getBaseUnitOfMeasureCode() != null && !skuBulkDTO.getBaseUnitOfMeasureCode().isEmpty() && !skuBulkDTO.getBaseUnitOfMeasureCode().isBlank()){
            skuMaster.setBaseUnitOfMeasurement(uomMasterService.getUomByCode(skuBulkDTO.getBaseUnitOfMeasureCode()));
            if (skuMaster.getBaseUnitOfMeasurement() == null){
                errorList.add("Row " + rowNo1 + " Base Unit Of Measure does not exist");
                shouldReturnNullSave = true;
            }
        }else {
            errorList.add("Row " + rowNo1 + " Base Unit Of Measure code is missing");
            shouldReturnNullSave = true;
        }
        return skuMaster;
    }

    private SkuMaster setCurrencyMaster(SkuMaster skuMaster,SkuBulkDTO skuBulkDTO){
        if (skuBulkDTO.getCurrencyCode() != null && !skuBulkDTO.getCurrencyCode().isEmpty() && !skuBulkDTO.getCurrencyCode().isBlank()){
            skuMaster.setCurrencyMaster(currencyMasterService.getByCode(skuBulkDTO.getCurrencyCode()));
            if (skuMaster.getCurrencyMaster() == null){
                errorList.add("Row " + rowNo1 + " Purchase currency does not exist");
                shouldReturnNullSave = true;
            }
        }
        return skuMaster;
    }

    private SkuMaster setImcoCode(SkuMaster skuMaster,SkuBulkDTO skuBulkDTO){
        if (skuBulkDTO.getImcoCodeCode() != null && !skuBulkDTO.getImcoCodeCode().isEmpty() && !skuBulkDTO.getImcoCodeCode().isBlank()){
            skuMaster.setImcoCodeMaster(imcoCodeMasterService.getByCode(skuBulkDTO.getImcoCodeCode()));
            if (skuMaster.getImcoCodeMaster() == null){
                errorList.add("Row " + rowNo1 + " IMCO code does not exist");
                shouldReturnNullSave = true;
            }
        }
        return skuMaster;
    }

    public SkuMaster setCustomerMaster(SkuMaster skuMaster, SkuBulkDTO  skuBulkDTO){
        if (skuBulkDTO.getCustomerCode() != null && !skuBulkDTO.getCustomerCode().isEmpty() && !skuBulkDTO.getCustomerCode().isBlank()){
            skuMaster.setCustomerMaster(customerMasterService.getCustomerByCode(skuBulkDTO.getCustomerCode()));
            if (skuMaster.getCustomerMaster() == null){
                errorList.add("Row " + rowNo1 + " Customer does not exist");
                shouldReturnNullSave = true;
            }
        }else {
            errorList.add("Row " + rowNo1 + " Customer is missing");
            shouldReturnNullSave = true;
        }
        return skuMaster;
    }

    public SkuMaster setStorageArea(SkuMaster skuMaster, SkuBulkDTO skuBulkDTO) {
        if (skuBulkDTO.getStorageAreaCode() != null && !skuBulkDTO.getStorageAreaCode().isEmpty() && !skuBulkDTO.getStorageAreaCode().isBlank()) {
            skuMaster.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterByCode(skuBulkDTO.getStorageAreaCode()));
            log.info("hello hi");
            log.info(skuBulkDTO.getStorageAreaCode());
            if (skuMaster.getStorageAreaMaster() == null){
                errorList.add("Row " + rowNo1 + " Storage Area does not exist");
                shouldReturnNullSave = true;
            }
        }else {
            errorList.add("Row " + rowNo1 + " Storage Area is missing");
            shouldReturnNullSave = true;
        }
        return skuMaster;
    }

    public SkuMaster setStorageType(SkuMaster skuMaster, SkuBulkDTO skuBulkDTO) {
        if (skuBulkDTO.getStorageTypeCode() != null && !skuBulkDTO.getStorageTypeCode().isEmpty() && !skuBulkDTO.getStorageTypeCode().isBlank()) {
            skuMaster.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterByCode(skuBulkDTO.getStorageTypeCode()));
            if (skuMaster.getStorageTypeMaster() == null){
                errorList.add("Row " + rowNo1 + " Storage Type does not exist");
                shouldReturnNullSave = true;
            }
        }else {
            errorList.add("Row " + rowNo1 + " Storage Type is missing");
            shouldReturnNullSave = true;
        }
        return skuMaster;
    }


    private SkuMaster setBranchCompanyGroupCompany(SkuMaster skuMaster, SkuBulkDTO skuBulkDTO) {
        try {
            skuMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(skuBulkDTO.getGroupCompanyId())));
        } catch (ServiceException serviceException) {
            errorList.add("Row" + rowNo1 + "GroupCompany Id does not exist");
        }
        try {
            skuMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(skuBulkDTO.getCompanyId())));
        } catch (ServiceException serviceException) {
            errorList.add("Row" + rowNo1 + "Company Id does not exist");
        }
        try {
            skuMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(skuBulkDTO.getBranchId())));
        } catch (ServiceException serviceException) {
            errorList.add("Row" + rowNo1 + "Branch Id does not exist");
        }
        return skuMaster;
    }

    /*
     * Method to find if an entry in bulk data is to be saved or updated
     * @Return Boolean
     */
    private Boolean skuAlreadyExits(String skuCode, String customerCode, List<SkuMaster> skuMasterList){
        log.info("ENTRY - Find if an entry in bulk data is to be saved or updated");
        rowNo1++;
        Boolean skuExists = false;
        skuExists = skuMasterList.stream()
                .anyMatch(skuMaster ->
                        Objects.equals(skuMaster.getCode(), skuCode) &&
                                Objects.equals(skuMaster.getCustomerMaster().getCode(), customerCode)
                );
        log.info("EXIT");
        return skuExists;
    }

    public List<SkuResponseDTO.LotDTO> convertStaticLotToDTOList(SkuMaster skuMaster) {
        String mandatory = "Mandatory";
        List<SkuResponseDTO.LotDTO> lotDTOList = new ArrayList<>();
        SkuResponseDTO.LotDTO cooLotDTO = new SkuResponseDTO.LotDTO();
        cooLotDTO.setName("coo");
        cooLotDTO.setLabel("coo");
        cooLotDTO.setIsStaticLot("Yes");
        if (skuMaster.getCoo() != null && skuMaster.getCoo().equalsIgnoreCase(mandatory)){
            cooLotDTO.setIsMandatory("Yes");
        }else {
            cooLotDTO.setIsMandatory("No");
        }
        lotDTOList.add(cooLotDTO);
        SkuResponseDTO.LotDTO batchLotDTO = new SkuResponseDTO.LotDTO();
        batchLotDTO.setName("batch");
        batchLotDTO.setLabel("batch");
        batchLotDTO.setIsStaticLot("Yes");
        if (skuMaster.getBatch() != null && skuMaster.getBatch().equalsIgnoreCase(mandatory)){
            batchLotDTO.setIsMandatory("Yes");
        }else {
            batchLotDTO.setIsMandatory("No");
        }
        lotDTOList.add(batchLotDTO);
        SkuResponseDTO.LotDTO serialNumberLotDTO = new SkuResponseDTO.LotDTO();
        serialNumberLotDTO.setName("serialNumber");
        serialNumberLotDTO.setLabel("serialNumber");
        serialNumberLotDTO.setIsStaticLot("Yes");
        if (skuMaster.getSerialNo() != null && skuMaster.getSerialNo().equalsIgnoreCase(mandatory)){
            serialNumberLotDTO.setIsMandatory("Yes");
        }else {
            serialNumberLotDTO.setIsMandatory("No");
        }
        lotDTOList.add(serialNumberLotDTO);
        SkuResponseDTO.LotDTO mfgDateLotDTO = new SkuResponseDTO.LotDTO();
        mfgDateLotDTO.setName("mfgDate");
        mfgDateLotDTO.setLabel("mfgDate");
        mfgDateLotDTO.setIsStaticLot("Yes");
        if (skuMaster.getMfgDate() != null && skuMaster.getMfgDate().equalsIgnoreCase(mandatory)){
            mfgDateLotDTO.setIsMandatory("Yes");
        }else {
            mfgDateLotDTO.setIsMandatory("No");
        }
        lotDTOList.add(mfgDateLotDTO);
        SkuResponseDTO.LotDTO expDateLotDTO = new SkuResponseDTO.LotDTO();
        expDateLotDTO.setName("expDate");
        expDateLotDTO.setLabel("expDate");
        expDateLotDTO.setIsStaticLot("Yes");
        if (skuMaster.getExpDate() != null && skuMaster.getExpDate().equalsIgnoreCase(mandatory)){
            expDateLotDTO.setIsMandatory("Yes");
        }else {
            expDateLotDTO.setIsMandatory("No");
        }
        lotDTOList.add(expDateLotDTO);
        if (skuMaster.getHsCodeMaster() != null) {
            SkuResponseDTO.LotDTO hsCodeLotDTO = new SkuResponseDTO.LotDTO();
            hsCodeLotDTO.setLabel("hsCode");
            hsCodeLotDTO.setId(skuMaster.getHsCodeMaster().getId());
            hsCodeLotDTO.setCode(skuMaster.getHsCodeMaster().getCode());
            hsCodeLotDTO.setName(skuMaster.getHsCodeMaster().getName());
            hsCodeLotDTO.setIsStaticLot("Yes");
            lotDTOList.add(hsCodeLotDTO);
        }
        return lotDTOList;
    }

}
