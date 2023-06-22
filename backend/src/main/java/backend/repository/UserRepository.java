package backend.repository;

import backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameAndIsDeleted(String username, Boolean isDeleted);
    Optional<User> findByIdAndIsDeleted(Integer id, Boolean isDeleted);

    @Query(nativeQuery = true, value = "select * from user u inner join user_roles ur on u.id=ur.user_id inner join role r on ur.role_id=r.id where r.name= :roleName and u.is_deleted = false")
    List<User> findAllByRole(@Param("roleName") String roleName);

    @Query(nativeQuery = true, value = "select * from user u inner join user_roles ur on u.id=ur.user_id inner join role r on ur.role_id=r.id where r.name != 'ROLE_ADMIN' and u.is_deleted = false")
    List<User> findAllWithoutAdmin();

    @Query(value = "SELECT * FROM user WHERE first_name = :firstName", nativeQuery = true)
    List<User> findByFirstName(String firstName);

    Optional<User> findByUsername(String username);

}
