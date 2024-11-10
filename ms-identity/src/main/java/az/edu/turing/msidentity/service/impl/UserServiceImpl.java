package az.edu.turing.msidentity.service.impl;

import az.edu.turing.msidentity.exception.BaseException;
import az.edu.turing.msidentity.model.dto.request.UserRequest;
import az.edu.turing.msidentity.model.dto.response.PageResponse;
import az.edu.turing.msidentity.model.dto.response.UserResponse;
import az.edu.turing.msidentity.entity.UserEntity;
import az.edu.turing.msidentity.mapper.UserMapper;
import az.edu.turing.msidentity.repository.UserRepository;
import az.edu.turing.msidentity.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static az.edu.turing.msidentity.model.enums.ErrorMessages.USER_NOT_FOUND;
import static az.edu.turing.msidentity.model.enums.ErrorMessages.USER_REQUEST_CANNOT_BE_NULL;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    UserMapper mapper = UserMapper.INSTANCE;

    @Override
    public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
        if (userRequest != null) {
            UserEntity user = toUserEntity(userRequest);
            user.setEnabled(true);
            user.setId(UUID.randomUUID());
            return ResponseEntity.ok(toUserResponse(userRepository.save(user)));
        }
        throw BaseException.of(USER_REQUEST_CANNOT_BE_NULL);
    }

    @Override
    public PageResponse<UserResponse> getAllUsers(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);
        return getPageResponse(page);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(String id) {
        UUID uuid = UUID.fromString(id);
        UserEntity user = userRepository.findById(uuid).
                orElseThrow(() -> BaseException.of(USER_NOT_FOUND));

        return ResponseEntity.ok(toUserResponse(user));
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(String id, UserRequest userRequest) {
        UserEntity user = userRepository.findById(UUID.fromString(id)).
                orElseThrow(() -> BaseException.of(USER_NOT_FOUND));

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        return ResponseEntity.ok(toUserResponse(userRepository.save(user)));
    }

    @Override
    public ResponseEntity<Void> deleteUser(String id) {
        UUID userId = UUID.fromString(id);
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    private UserEntity toUserEntity(UserRequest userRequest) {
        return mapper.requestToEntity(userRequest);
    }

    private UserResponse toUserResponse(UserEntity userEntity) {
        return mapper.entityToResponse(userEntity);
    }

    private List<UserResponse> toUserResponseList(List<UserEntity> userEntities) {
        return mapper.entityToResponseList(userEntities);
    }

    private PageResponse<UserResponse> getPageResponse(Page<UserEntity> page) {
        return PageResponse.<UserResponse>builder().
                pageNo(page.getNumber()).
                pageSize(page.getSize()).
                totalPages(page.getTotalPages()).
                content(toUserResponseList(page.getContent())).
                totalElements(page.getTotalElements()).
                last(page.isLast()).build();
    }

}
