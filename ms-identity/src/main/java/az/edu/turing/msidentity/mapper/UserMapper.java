package az.edu.turing.msidentity.mapper;

import az.edu.turing.msidentity.model.dto.request.RegisterRequest;
import az.edu.turing.msidentity.model.dto.request.UserRequest;
import az.edu.turing.msidentity.model.dto.response.UserResponse;
import az.edu.turing.msidentity.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity requestToEntity(UserRequest userRequest);

    UserEntity requestToEntity(RegisterRequest registerRequest);

    UserResponse entityToResponse(UserEntity userEntity);

    List<UserResponse> entityToResponseList(List<UserEntity> userEntity);




}
