package az.edu.turing.msidentity.service.inter;

import az.edu.turing.msidentity.model.dto.request.UserRequest;
import az.edu.turing.msidentity.model.dto.response.PageResponse;
import az.edu.turing.msidentity.model.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<UserResponse> createUser(UserRequest userRequest);

    PageResponse<UserResponse> getAllUsers(Pageable pageable);

    ResponseEntity<UserResponse> getUserById(String id);

    ResponseEntity<UserResponse> updateUser(String id, UserRequest userRequest);

    ResponseEntity<Void> deleteUser(String id);
}
