package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.CategoryMapper;
import com.newage.wms.application.dto.requestdto.CategoryRequestDTO;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.responsedto.CategoryResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.CategoryMaster;
import com.newage.wms.service.CategoryMasterService;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.Date;

@Log4j2
@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryMasterController {

    @Autowired
    private CategoryMasterService categoryMasterService;

    @Autowired
    private CategoryMapper categoryMapper;

    /*
     * Method to fetch all CategoryMaster with filter
     */
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllCategory(@RequestParam(name = "query",required = false) String query,
                                                        @RequestParam(name = "status",required = false) String status,
                                                        @RequestParam(name = "branchId",required = false) Long branchId){
        log.info("ENTRY - Fetch all Category with filter");
        Iterable<CategoryMaster> categoryMasterIterable = categoryMasterService.fetchAllCategory(query,status,branchId);
        Iterable<CategoryResponseDTO> categoryResponseDTOIterable = categoryMapper.convertCategoryMasterIterableToCategoryDTOIterable(categoryMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,categoryResponseDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new  Category Master
     */
    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        log.info("ENTRY - Create new Category Master");
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(categoryRequestDTO);
        CategoryMaster categoryMaster = categoryMapper.convertRequestToEntity(categoryRequestDTO,dateAndTimeRequestDto);
        categoryMaster = categoryMasterService.saveCategory(categoryMaster);
        CategoryResponseDTO categoryResponseDTO = categoryMapper.convertEntityToDTO(categoryMaster);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,categoryResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping
    public ResponseEntity<ResponseDTO>fetchAllCategory(@QuerydslPredicate(root = CategoryMaster.class) Predicate predicate,
                                                       @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) @Parameter(hidden = true) Pageable pageable){
        log.info("ENTRY - Fetch all Category with sort, filter and Pagination");
        Page<CategoryMaster> categoryMasterPage = categoryMasterService.fetchAll(predicate, pageable);
        Page<CategoryResponseDTO> categoryResponseDTOPage = categoryMapper.convertCategoryPageTOResponsePage(categoryMasterPage);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,categoryResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO> fetchCategoryById(@PathVariable  ("id")Long id){
        log.info("ENTRY - Fetch Category By Id");
        CategoryResponseDTO categoryResponseDTO = categoryMapper.convertEntityToDTO(categoryMasterService.getCategoryById(id));
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,categoryResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }
    /*
     * Method to update CategoryMaster by id
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO> updateCategoryById(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO,
                                                          @PathVariable ("id")Long id){
        log.info("ENTRY - Update Category By id");
        CategoryMaster categoryMaster= categoryMasterService.getCategoryById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(categoryRequestDTO,categoryMaster);
        categoryMapper.convertUpdateRequestToEntity(categoryRequestDTO,categoryMaster,dateAndTimeRequestDto);
        categoryMaster = categoryMasterService.saveCategory(categoryMaster);
        CategoryResponseDTO categoryResponseDTO = categoryMapper.convertEntityToDTO(categoryMaster);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,categoryResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }


    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid CategoryRequestDTO categoryRequestDTO ){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(categoryRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(categoryRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(CategoryRequestDTO categoryRequestDTO, CategoryMaster categoryMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(categoryMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(categoryMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(categoryRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(categoryMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to validate unique Category Code
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code){
        log.info("ENTRY - Validate unique Aisle Code");
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank() ){
                categoryMasterService.validateUniqueCategoryAttributeSave(codeCaps);
            }else {
                categoryMasterService.validateUniqueCategoryAttributeUpdate(codeCaps,Long.parseLong(id));
            }
        }catch (ServiceException serviceException){
            exists=true;
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }


}