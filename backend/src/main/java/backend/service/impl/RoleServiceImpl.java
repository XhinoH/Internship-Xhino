package backend.service.impl;

import backend.exception.CustomRequestException;
import backend.exception.InvalidRequestException;
import backend.model.dto.RoleDto;
import backend.model.entity.Role;
import backend.repository.RoleRepository;
import backend.service.RoleService;
import backend.util.DtoConversion;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final RoleRepository roleRepository;
    private DtoConversion dtoConversion = new DtoConversion();

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Saving a new role
    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role;

        // Updating the role if the roleDto has an id
        if (roleDto.getId() != null){
            Optional<Role> roleOptional = roleRepository.findById(roleDto.getId());
            if (roleOptional.isPresent()){
                role = roleOptional.get();
            } else {
                logger.error("Role not found");
                throw new NullPointerException("Role not found");
            }
        } else {
            role = new Role();
        }

        logger.info("Saved role with name: " + role.getDescription());
        return dtoConversion.convertRole(roleRepository.save(role));
    }

    // Finding all the roles
    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map(dtoConversion::convertRole).collect(Collectors.toList());
    }

    // Finding a role by id
    @Override
    public RoleDto findById(Integer id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()){
            return dtoConversion.convertRole(roleOptional.get());
        }else {
            throw new NullPointerException("Role not found");
        }
    }

}
