package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.SkuRequestDTO;
import com.newage.wms.application.dto.responsedto.SkuResponseDTO;
import com.newage.wms.entity.SkuLotDetails;
import com.newage.wms.entity.SkuMaster;
import com.newage.wms.service.SkuLotDetailsService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class SkuLotDetailsMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SkuLotDetailsService skuLotDetailsService;

    public List<SkuLotDetails> convertRequestLotDtoToEntityLotList(SkuRequestDTO skuRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - Sku Request to SkuEntity mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        List<SkuLotDetails> skuLotDetailsList = new ArrayList<>();
        if (skuRequestDTO.getSkuLotDetails() != null){
            SkuLotDetails skuLotDetails01 = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
            modelMapper.map(skuRequestDTO.getSkuLotDetails().getLot01(),skuLotDetails01);
            skuLotDetails01.setFieldName("Lot01");
            skuLotDetailsList.add(skuLotDetails01);
            SkuLotDetails skuLotDetails02 = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
            modelMapper.map(skuRequestDTO.getSkuLotDetails().getLot02(),skuLotDetails02);
            skuLotDetails02.setFieldName("Lot02");
            skuLotDetailsList.add(skuLotDetails02);
            SkuLotDetails skuLotDetails03 = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
            modelMapper.map(skuRequestDTO.getSkuLotDetails().getLot03(),skuLotDetails03);
            skuLotDetails03.setFieldName("Lot03");
            skuLotDetailsList.add(skuLotDetails03);
            SkuLotDetails skuLotDetails04 = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
            modelMapper.map(skuRequestDTO.getSkuLotDetails().getLot04(),skuLotDetails04);
            skuLotDetails04.setFieldName("Lot04");
            skuLotDetailsList.add(skuLotDetails04);
            SkuLotDetails skuLotDetails05 = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
            modelMapper.map(skuRequestDTO.getSkuLotDetails().getLot05(),skuLotDetails05);
            skuLotDetails05.setFieldName("Lot05");
            skuLotDetailsList.add(skuLotDetails05);
            SkuLotDetails skuLotDetails06 = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
            modelMapper.map(skuRequestDTO.getSkuLotDetails().getLot06(),skuLotDetails06);
            skuLotDetails06.setFieldName("Lot06");
            skuLotDetailsList.add(skuLotDetails06);
            SkuLotDetails skuLotDetails07 = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
            modelMapper.map(skuRequestDTO.getSkuLotDetails().getLot07(),skuLotDetails07);
            skuLotDetails07.setFieldName("Lot07");
            skuLotDetailsList.add(skuLotDetails07);
            SkuLotDetails skuLotDetails08 = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
            modelMapper.map(skuRequestDTO.getSkuLotDetails().getLot08(),skuLotDetails08);
            skuLotDetails08.setFieldName("Lot08");
            skuLotDetailsList.add(skuLotDetails08);
            SkuLotDetails skuLotDetails09 = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
            modelMapper.map(skuRequestDTO.getSkuLotDetails().getLot09(),skuLotDetails09);
            skuLotDetails09.setFieldName("Lot09");
            skuLotDetailsList.add(skuLotDetails09);
            SkuLotDetails skuLotDetails10 = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
            modelMapper.map(skuRequestDTO.getSkuLotDetails().getLot10(),skuLotDetails10);
            skuLotDetails10.setFieldName("Lot10");
            skuLotDetailsList.add(skuLotDetails10);
            if (!CollectionUtils.isEmpty(skuRequestDTO.getSkuLotDetails().getLotArray())){
                for (SkuRequestDTO.LotDTO skuLotDTOArrayItem : skuRequestDTO.getSkuLotDetails().getLotArray()){
                    SkuLotDetails skuLotDetailsArrayItem = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
                    if (skuLotDTOArrayItem.getName() == null){
                        throw new ServiceException(ServiceErrors.LOT_NAME_SHOULD_NOT_BE_NULL.CODE,
                                ServiceErrors.LOT_NAME_SHOULD_NOT_BE_NULL.KEY);
                    }
                    modelMapper.map(skuLotDTOArrayItem,skuLotDetailsArrayItem);
                    skuLotDetailsArrayItem.setFieldName(skuLotDTOArrayItem.getName());
                    skuLotDetailsList.add(skuLotDetailsArrayItem);
                }
            }
            return skuLotDetailsList;
        }
        return Collections.emptyList();
    }

    public List<SkuLotDetails> convertUpdateRequestLotDtoToEntityLotList(SkuRequestDTO skuRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - Sku Update Request to SkuEntity mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (skuRequestDTO.getSkuLotDetails() != null){
            List<SkuLotDetails> skuLotDetailsList = mapFirstTenSkuLotDetails(skuRequestDTO,dateAndTimeRequestDto);
            if (!CollectionUtils.isEmpty(skuRequestDTO.getSkuLotDetails().getLotArray())){
                for (SkuRequestDTO.LotDTO skuLotDTOArrayItem : skuRequestDTO.getSkuLotDetails().getLotArray()){
                    if (skuLotDTOArrayItem.getId() != null && !skuLotDTOArrayItem.getId().isEmpty() && !skuLotDTOArrayItem.getId().isBlank()) {
                        SkuLotDetails skuLotDetailsArrayItem = skuLotDetailsService.findById(Long.parseLong(skuLotDTOArrayItem.getId()));
                        modelMapper.map(dateAndTimeRequestDto, skuLotDetailsArrayItem);
                        modelMapper.map(skuLotDTOArrayItem, skuLotDetailsArrayItem);
                        skuLotDetailsArrayItem.setFieldName(skuLotDTOArrayItem.getName());
                        skuLotDetailsList.add(skuLotDetailsArrayItem);
                    } else if (skuLotDTOArrayItem.getId() != null) {
                        SkuLotDetails skuLotDetailsArrayItem = modelMapper.map(dateAndTimeRequestDto,SkuLotDetails.class);
                        modelMapper.map(skuLotDTOArrayItem,skuLotDetailsArrayItem);
                        skuLotDetailsArrayItem.setFieldName(skuLotDTOArrayItem.getName());
                        skuLotDetailsList.add(skuLotDetailsArrayItem);
                    }
                }
            }
            return skuLotDetailsList;
        }
        return Collections.emptyList();
    }

    public SkuResponseDTO.LotDetailsDTO convertEntityLotListToLotResponse(SkuMaster skuMaster) {
        log.info("ENTRY - Sku Request to SkuEntity mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        List<SkuLotDetails> skuLotDetailsList = skuMaster.getSkuLotDetailsList();
        if (!CollectionUtils.isEmpty(skuLotDetailsList)){
            SkuResponseDTO.LotDetailsDTO lotDetailsDTO = new SkuResponseDTO.LotDetailsDTO();
            List<SkuLotDetails> sortedList = skuLotDetailsList.stream()
                    .sorted(Comparator.comparing(SkuLotDetails::getFieldName, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());
            SkuResponseDTO.LotDTO lotDTO01 = modelMapper.map(sortedList.get(0),SkuResponseDTO.LotDTO.class);
            lotDTO01.setName(sortedList.get(0).getFieldName());
            lotDetailsDTO.setLot01(lotDTO01);
            SkuResponseDTO.LotDTO lotDTO02 = modelMapper.map(sortedList.get(1),SkuResponseDTO.LotDTO.class);
            lotDTO02.setName(sortedList.get(1).getFieldName());
            lotDetailsDTO.setLot02(lotDTO02);
            SkuResponseDTO.LotDTO lotDTO03 = modelMapper.map(sortedList.get(2),SkuResponseDTO.LotDTO.class);
            lotDTO03.setName(sortedList.get(2).getFieldName());
            lotDetailsDTO.setLot03(lotDTO03);
            SkuResponseDTO.LotDTO lotDTO04 = modelMapper.map(sortedList.get(3),SkuResponseDTO.LotDTO.class);
            lotDTO04.setName(sortedList.get(3).getFieldName());
            lotDetailsDTO.setLot04(lotDTO04);
            SkuResponseDTO.LotDTO lotDTO05 = modelMapper.map(sortedList.get(4),SkuResponseDTO.LotDTO.class);
            lotDTO05.setName(sortedList.get(4).getFieldName());
            lotDetailsDTO.setLot05(lotDTO05);
            SkuResponseDTO.LotDTO lotDTO06 = modelMapper.map(sortedList.get(5),SkuResponseDTO.LotDTO.class);
            lotDTO06.setName(sortedList.get(5).getFieldName());
            lotDetailsDTO.setLot06(lotDTO06);
            SkuResponseDTO.LotDTO lotDTO07 = modelMapper.map(sortedList.get(6),SkuResponseDTO.LotDTO.class);
            lotDTO07.setName(sortedList.get(6).getFieldName());
            lotDetailsDTO.setLot07(lotDTO07);
            SkuResponseDTO.LotDTO lotDTO08 = modelMapper.map(sortedList.get(7),SkuResponseDTO.LotDTO.class);
            lotDTO08.setName(sortedList.get(7).getFieldName());
            lotDetailsDTO.setLot08(lotDTO08);
            SkuResponseDTO.LotDTO lotDTO09 = modelMapper.map(sortedList.get(8),SkuResponseDTO.LotDTO.class);
            lotDTO09.setName(sortedList.get(8).getFieldName());
            lotDetailsDTO.setLot09(lotDTO09);
            SkuResponseDTO.LotDTO lotDTO10 = modelMapper.map(sortedList.get(9),SkuResponseDTO.LotDTO.class);
            lotDTO10.setName(sortedList.get(9).getFieldName());
            lotDetailsDTO.setLot10(lotDTO10);
            List<SkuResponseDTO.LotDTO> lotArray = new ArrayList<>();
            if (sortedList.size() > 10){
                List<SkuLotDetails> remainingList = sortedList.stream()
                        .skip(10)  // Skip the first 10 elements
                        .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(sortedList)){
                    for (SkuLotDetails skuLotDetails: remainingList){
                        SkuResponseDTO.LotDTO lotDTO = modelMapper.map(skuLotDetails,SkuResponseDTO.LotDTO.class);
                        lotDTO.setName(skuLotDetails.getFieldName());
                        lotArray.add(lotDTO);
                    }
                }
            }
            lotDetailsDTO.setLotArray(lotArray);
            return lotDetailsDTO;
        }
        return null;
    }

    public void deleteNonExistingIds(List<SkuLotDetails> skuLotDetailsToBeDeleted,SkuRequestDTO skuRequestDTO){
        log.info("ENTRY - Sku Update Request to SkuEntity mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (skuRequestDTO.getSkuLotDetails() != null){
            Set<Long> newIdsList = collectFirstTenExistingLotIdsOne(skuRequestDTO);
            if (!CollectionUtils.isEmpty(skuRequestDTO.getSkuLotDetails().getLotArray())){
                for (SkuRequestDTO.LotDTO skuLotDTOArrayItem : skuRequestDTO.getSkuLotDetails().getLotArray()){
                    if (skuLotDTOArrayItem.getId() != null && !skuLotDTOArrayItem.getId().isEmpty() && !skuLotDTOArrayItem.getId().isBlank()) {
                        newIdsList.add(Long.parseLong(skuLotDTOArrayItem.getId()));
                    }
                }
            }
            skuLotDetailsService.deleteAllInIterable(getSkuLotDetailListToBeDeleted(newIdsList,skuLotDetailsToBeDeleted));
        }
    }

    public List<SkuLotDetails> mapFirstTenSkuLotDetails(SkuRequestDTO skuRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        List<SkuLotDetails> skuLotDetailsList = new ArrayList<>();
        if (skuRequestDTO.getSkuLotDetails().getLot01() != null) {
            skuLotDetailsList.add(getSkuLotEntity(skuRequestDTO.getSkuLotDetails().getLot01(),dateAndTimeRequestDto));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot02() != null) {
            skuLotDetailsList.add(getSkuLotEntity(skuRequestDTO.getSkuLotDetails().getLot02(),dateAndTimeRequestDto));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot03() != null) {
            skuLotDetailsList.add(getSkuLotEntity(skuRequestDTO.getSkuLotDetails().getLot03(),dateAndTimeRequestDto));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot04() != null) {
            skuLotDetailsList.add(getSkuLotEntity(skuRequestDTO.getSkuLotDetails().getLot04(),dateAndTimeRequestDto));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot05() != null) {
            skuLotDetailsList.add(getSkuLotEntity(skuRequestDTO.getSkuLotDetails().getLot05(),dateAndTimeRequestDto));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot06() != null) {
            skuLotDetailsList.add(getSkuLotEntity(skuRequestDTO.getSkuLotDetails().getLot06(),dateAndTimeRequestDto));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot07() != null) {
            skuLotDetailsList.add(getSkuLotEntity(skuRequestDTO.getSkuLotDetails().getLot07(),dateAndTimeRequestDto));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot08() != null) {
            skuLotDetailsList.add(getSkuLotEntity(skuRequestDTO.getSkuLotDetails().getLot08(),dateAndTimeRequestDto));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot09() != null) {
            skuLotDetailsList.add(getSkuLotEntity(skuRequestDTO.getSkuLotDetails().getLot09(),dateAndTimeRequestDto));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot10() != null) {
            skuLotDetailsList.add(getSkuLotEntity(skuRequestDTO.getSkuLotDetails().getLot10(),dateAndTimeRequestDto));
        }
        return skuLotDetailsList;
    }

    public SkuLotDetails getSkuLotEntity(SkuRequestDTO.LotDTO lotDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        SkuLotDetails skuLotDetails = new SkuLotDetails();
        if (lotDTO.getId() != null
                && !lotDTO.getId().isEmpty()
                && !lotDTO.getId().isBlank() ) {
            skuLotDetails = skuLotDetailsService.findById(
                    Long.parseLong(lotDTO.getId()));
        }
        modelMapper.map(dateAndTimeRequestDto,skuLotDetails);
        modelMapper.map(lotDTO, skuLotDetails);
        return  skuLotDetails;
    }

    public Set<Long> collectFirstTenExistingLotIdsOne(SkuRequestDTO skuRequestDTO){
        Set<Long> newIdsList = new HashSet<>();
        if (skuRequestDTO.getSkuLotDetails().getLot01() != null && skuRequestDTO.getSkuLotDetails().getLot01().getId() != null &&
                !skuRequestDTO.getSkuLotDetails().getLot01().getId().isBlank() && !skuRequestDTO.getSkuLotDetails().getLot01().getId().isEmpty() ) {
            newIdsList.add(Long.parseLong(skuRequestDTO.getSkuLotDetails().getLot01().getId()));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot02() != null && skuRequestDTO.getSkuLotDetails().getLot02().getId() != null &&
                !skuRequestDTO.getSkuLotDetails().getLot02().getId().isBlank() && !skuRequestDTO.getSkuLotDetails().getLot02().getId().isEmpty()) {
            newIdsList.add(Long.parseLong(skuRequestDTO.getSkuLotDetails().getLot02().getId()));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot03() != null && skuRequestDTO.getSkuLotDetails().getLot03().getId() != null &&
                !skuRequestDTO.getSkuLotDetails().getLot03().getId().isBlank() && !skuRequestDTO.getSkuLotDetails().getLot03().getId().isEmpty()) {
            newIdsList.add(Long.parseLong(skuRequestDTO.getSkuLotDetails().getLot03().getId()));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot04() != null && skuRequestDTO.getSkuLotDetails().getLot04().getId() != null &&
                !skuRequestDTO.getSkuLotDetails().getLot04().getId().isBlank() && !skuRequestDTO.getSkuLotDetails().getLot04().getId().isEmpty()) {
            newIdsList.add(Long.parseLong(skuRequestDTO.getSkuLotDetails().getLot04().getId()));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot05() != null && skuRequestDTO.getSkuLotDetails().getLot05().getId() != null &&
                !skuRequestDTO.getSkuLotDetails().getLot05().getId().isBlank() && !skuRequestDTO.getSkuLotDetails().getLot05().getId().isEmpty()) {
            newIdsList.add(Long.parseLong(skuRequestDTO.getSkuLotDetails().getLot05().getId()));
        }
        return collectFirstTenExistingLotIdsTwo(skuRequestDTO,newIdsList);
    }

    public Set<Long> collectFirstTenExistingLotIdsTwo(SkuRequestDTO skuRequestDTO,Set<Long> newIdsList){
        if (skuRequestDTO.getSkuLotDetails().getLot06() != null && skuRequestDTO.getSkuLotDetails().getLot06().getId() != null &&
                !skuRequestDTO.getSkuLotDetails().getLot06().getId().isBlank() && !skuRequestDTO.getSkuLotDetails().getLot06().getId().isEmpty()) {
            newIdsList.add(Long.parseLong(skuRequestDTO.getSkuLotDetails().getLot06().getId()));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot07() != null && skuRequestDTO.getSkuLotDetails().getLot07().getId() != null &&
                !skuRequestDTO.getSkuLotDetails().getLot07().getId().isBlank() && !skuRequestDTO.getSkuLotDetails().getLot07().getId().isEmpty()) {
            newIdsList.add(Long.parseLong(skuRequestDTO.getSkuLotDetails().getLot07().getId()));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot08() != null && skuRequestDTO.getSkuLotDetails().getLot08().getId() != null &&
                !skuRequestDTO.getSkuLotDetails().getLot08().getId().isBlank() && !skuRequestDTO.getSkuLotDetails().getLot08().getId().isEmpty()) {
            newIdsList.add(Long.parseLong(skuRequestDTO.getSkuLotDetails().getLot08().getId()));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot09() != null && skuRequestDTO.getSkuLotDetails().getLot09().getId() != null &&
                !skuRequestDTO.getSkuLotDetails().getLot09().getId().isBlank() && !skuRequestDTO.getSkuLotDetails().getLot09().getId().isEmpty()) {
            newIdsList.add(Long.parseLong(skuRequestDTO.getSkuLotDetails().getLot09().getId()));
        }
        if (skuRequestDTO.getSkuLotDetails().getLot10() != null && skuRequestDTO.getSkuLotDetails().getLot10().getId() != null &&
                !skuRequestDTO.getSkuLotDetails().getLot10().getId().isBlank() && !skuRequestDTO.getSkuLotDetails().getLot10().getId().isEmpty()) {
            newIdsList.add(Long.parseLong(skuRequestDTO.getSkuLotDetails().getLot10().getId()));
        }
        return newIdsList;
    }

    public List<SkuLotDetails> getSkuLotDetailListToBeDeleted(Set<Long> newIdsList ,List<SkuLotDetails> skuLotDetailsToBeDeleted){
        Iterator<SkuLotDetails> iterator = skuLotDetailsToBeDeleted.iterator();
        while (iterator.hasNext()) {
            SkuLotDetails skuLotDetails = iterator.next();
            Long id = skuLotDetails.getId();
            // Check if the id exists in firstIds set
            if (newIdsList.contains(id)) {
                iterator.remove(); // Remove the element from secondList
            }
        }
        return skuLotDetailsToBeDeleted;
    }

}
