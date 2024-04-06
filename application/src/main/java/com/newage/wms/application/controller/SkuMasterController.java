package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.SkuMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.SkuBulkDTO;
import com.newage.wms.application.dto.requestdto.SkuRequestDTO;
import com.newage.wms.application.dto.requestdto.SkuRequestDTOList;
import com.newage.wms.application.dto.responsedto.*;
import com.newage.wms.entity.*;
import com.newage.wms.entity.QSkuMaster;
import com.newage.wms.repository.SkuMasterRepository;
import com.newage.wms.service.SkuMasterService;
import com.newage.wms.service.TrnDetailService;
import com.newage.wms.service.TrnHeaderAsnService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@RestController
@RequestMapping("/sku")
@CrossOrigin("*")
public class SkuMasterController {

    @Autowired
    private SkuMasterService skuMasterService;

    @Autowired
    private TrnDetailService trnDetailService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SkuMasterRepository skuMasterRepository;

    /*
     * Method to fetch all Sku with sort, filter and pagination
     */
    @ApiOperation(value = "Fetch all Sku with pagination", notes = " Api is used to fetch all Sku with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAll(@QuerydslPredicate(root = SkuMaster.class) Predicate predicate,
                                                @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) Pageable pageable){
        log.info("ENTRY - Fetch all Sku with sort, filter and pagination");
        Page<SkuMaster> skuPage = skuMasterService.getAll(predicate,pageable);
        Page<SkuResponseDTO> skuResponseDTOPage = skuMapper.convertPageToResponsePage(skuPage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,skuResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new Sku Master
     */
    @ApiOperation(value ="Create Sku", notes="Api is used to create Sku")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody SkuRequestDTO skuRequestDTO){
        log.info("ENTRY - Create new Sku");
        skuMasterService.validateUniqueSkuAttributeSave(skuRequestDTO.getSkuDetails().getCode().toUpperCase()
                , Long.valueOf(skuRequestDTO.getSkuDetails().getCustomer().getId()));
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(skuRequestDTO);
        SkuMaster skuMaster=skuMapper.convertRequestDtoToEntity(skuRequestDTO,dateAndTimeRequestDto);
        skuMaster = skuMasterService.save(skuMaster);
        SkuResponseDTO skuResponseDTO = skuMapper.convertEntityToDTO(skuMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,skuResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to update Sku by id
     */
    @ApiOperation(value = "Update Sku", notes = " Api is used to update Sku ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated") })
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateById(@Valid @RequestBody SkuRequestDTO skuRequestDTO,
                                                  @PathVariable ("id") Long id){
        log.info("ENTRY - Update Sku by Id");
        skuMasterService.validateUniqueSkuAttributeUpdate(skuRequestDTO.getSkuDetails().getCode().toUpperCase()
                ,id, Long.valueOf(skuRequestDTO.getSkuDetails().getCustomer().getId()));
        SkuMaster skuMaster = skuMasterService.getById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(skuRequestDTO,skuMaster);
        skuMapper.convertUpdateRequestToEntity(skuRequestDTO,skuMaster,dateAndTimeRequestDto);
        skuMaster = skuMasterService.update(skuMaster);
        SkuResponseDTO skuResponseDTO = skuMapper.convertEntityToDTO(skuMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,skuResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch Sku by id
     */
    @ApiOperation(value = "Fetch Sku by Id", notes = " Api is used to fetch Sku by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Sku by Id");
        SkuMaster skuMaster = skuMasterService.getById(id);
        SkuResponseDTO skuResponseDTO = skuMapper.convertEntityToDTO(skuMaster);
        List<SkuResponseDTO.LotDTO> lotDTOList = skuMapper.convertLotDetailsListToDTOList(skuMaster.getSkuLotDetailsList());
        skuResponseDTO.setSkulotDetailsList(lotDTOList);
        List<SkuResponseDTO.LotDTO> lotDTOList1 = skuMapper.convertLotDetailsListToDTOList(skuMaster.getSkuLotDetailsList());
        skuResponseDTO.setSkulotDetailsListForAsn(lotDTOList1);
        if (trnDetailService.getSkuEditFlag(id)){
            skuResponseDTO.setIsEditable("No");
        }else {
            skuResponseDTO.setIsEditable("Yes");
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,skuResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all Sku Autocomplete
     */
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(required = false) String query,
                                                            @RequestParam(required = false) Long customerId,
                                                            @RequestParam(required = false) String code,
                                                            @RequestParam(name = "branchId",required = false) Long branchId,
                                                            @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all Sku Sku Autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QSkuMaster.skuMaster.id.isNotNull());
        Boolean codeGiven = false;
        if (code != null && !code.isEmpty() && !code.isBlank()){
            codeGiven = true;
        }
        if (customerId != null) {
            predicates.add(QSkuMaster.skuMaster.customerMaster.id.eq(customerId));
        }
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QSkuMaster.skuMaster.name.containsIgnoreCase(query)
                    .or(QSkuMaster.skuMaster.code.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QSkuMaster.skuMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QSkuMaster.skuMaster.lovStatus.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = Pageable.unpaged();
        Page<SkuMaster> skuPage = skuMasterService.getAll(predicateAll,pageable);
        Iterable<TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.SkuDTO> skuDTOIterable = skuMapper.convertEntityIterableToAutoCompleteIterable(skuPage.getContent());
        if (codeGiven){
            skuDTOIterable = StreamSupport.stream(skuDTOIterable.spliterator(), false)
                    .filter(skuDTO -> skuDTO.getCode().equals(code))
                    .collect(Collectors.toList());
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,skuDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all Pack uom for Sku
     */
    @GetMapping("/getUomBySku/{id}")
    public ResponseEntity<ResponseDTO> fetchAllUomBySkuId(@PathVariable ("id") Long id,
                                                          @RequestParam (required = false) Long categoryId,
                                                          @RequestParam(required = false) String query,
                                                          @RequestParam(required = false) String restrictionOfUom,
                                                          @RequestParam(required = false) Long uomId){
        log.info("ENTRY - fetch all Pack uom for Sku");
        SkuResponseDTO skuResponseDTO = skuMapper.convertEntityToDTO(skuMasterService.getById(id));
        List<UomSkuPackDTO> uomDTOList = new ArrayList<>();
        if (skuResponseDTO.getPackDetails().getSkuPackDetailDTOList() != null && !skuResponseDTO.getPackDetails().getSkuPackDetailDTOList().isEmpty()) {
            uomDTOList = skuMapper.convertResponseToUomSkuPackDTOList(skuResponseDTO.getPackDetails().getSkuPackDetailDTOList());
            // Filter the uomDTOList
            if (query != null) {
                uomDTOList = uomDTOList.stream()
                        .filter(dto -> dto.getCode().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
            }
            if (restrictionOfUom != null && restrictionOfUom.equals("Yes") &&
                    uomId != null && !uomId.toString().isEmpty() && !uomId.toString().isBlank() ){
                uomDTOList = uomDTOList.stream()
                        .filter(dto -> dto.getRestrictionOfUom().equals("No") || dto.getId().equals(uomId))
                        .collect(Collectors.toList());
            }
        }
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,uomDTOList,null);
        if (categoryId != null) {
            List<UomSkuPackDTO> filteredList = uomDTOList.stream()
                    .filter(dto -> dto.getCategory() != null && dto.getCategory().getId().equals(categoryId))
                    .collect(Collectors.toList());
            responseDTO.setResult(filteredList);
        }
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to validate unique Sku Code
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code,
                                                 @RequestParam("customerId") Long customerId){
        log.info("ENTRY - validate unique Sku Code");
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank() ){
                skuMasterService.validateUniqueSkuAttributeSave(codeCaps,customerId);
            }else {
                skuMasterService.validateUniqueSkuAttributeUpdate(codeCaps,Long.parseLong(id),customerId);
            }
        }catch (ServiceException serviceException){
            exists=true;
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteAll(@PathVariable Long id){
        log.info("ENTRY - Fetch TRN Header by id");
        skuMasterRepository.deleteById(id);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,true,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid SkuRequestDTO skuRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(skuRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(skuRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(SkuRequestDTO skuRequestDTO, SkuMaster skuMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(skuMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(skuMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(skuRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(skuMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to fetch Sku lot api
     */
    @ApiOperation(value = "Fetch Sku by Id", notes = " Api is used to fetch Sku by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/skulotdetail/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchSkuLotDetailById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Sku by Id");
        SkuMaster skuMaster =skuMasterService.getById(id);
        SkuResponseDTO skuResponseDTO = skuMapper.convertEntityToDTO(skuMasterService.getById(id));
        List<SkuResponseDTO.LotDTO> lotDTOList = skuMapper.convertLotDetailsListToDTOList(skuMaster.getSkuLotDetailsList());
        skuResponseDTO.setSkulotDetailsList(lotDTOList);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,skuResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to  Sku Bulk Save api
     */
    @ApiOperation(value = "Bulk save all SKU", notes = " Api is used to bulk save all SKU ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/saveAll",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> bulkSaveAllSku(@Valid @RequestBody SkuRequestDTOList skuRequestDTOList){
        log.info("ENTRY Bulk Save All Sku");
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreateBulk(skuRequestDTOList.getSkuBulkDTOList().get(0));
        //Check if bulk save data has duplicate values(locationUid and warehouse code)
        SkuBulkResponseDTO skuBulkResponseDTO = skuMapper.convertRequestListToEntityList(skuRequestDTOList,dateAndTimeRequestDto);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,skuBulkResponseDTO,null);
        if (skuBulkResponseDTO.getSkuList() != null){
            skuMasterService.saveAll(skuBulkResponseDTO.getSkuList());
            responseDTO.setResult(true);
        }
        if (skuBulkResponseDTO.getErrorList() != null){
            List<String> errorList = skuBulkResponseDTO.getErrorList();
            ResponseError responseError = new ResponseError();
            responseError.setCode("ERROR");
            responseError.setMessage(errorList);
            responseDTO.setResult(null);
            responseDTO.setError(responseError);
        }
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);

    }

    /*
     * Method to set Date, Time and Version in Bulk create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreateBulk(SkuBulkDTO skuBulkDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in bulk create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(skuBulkDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(skuBulkDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }
}
