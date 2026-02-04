package com.agileboot.admin.modular.sys.common.dto;

import com.agileboot.admin.modular.sys.system.user.dto.UserDTO;
import java.util.Set;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class CurrentLoginUserDTO {

    private UserDTO userInfo;
    private String roleKey;
    private Set<String> permissions;


}
