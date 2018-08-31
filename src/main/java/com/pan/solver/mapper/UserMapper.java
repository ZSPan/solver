package com.pan.solver.mapper;

import com.pan.solver.dto.UserDto;
import com.pan.solver.entity.User;
import org.mapstruct.Mapper;

/**
 * @author yemingfeng
 */
@Mapper(componentModel = "spring", uses = CommonMapper.class)
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

}