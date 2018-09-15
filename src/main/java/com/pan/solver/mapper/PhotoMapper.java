package com.pan.solver.mapper;

import com.pan.solver.data.Photo;
import com.pan.solver.dto.PhotoDto;
import org.mapstruct.Mapper;

/**
 * @author yemingfeng
 */
@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoDto toDto(Photo photo);

}
