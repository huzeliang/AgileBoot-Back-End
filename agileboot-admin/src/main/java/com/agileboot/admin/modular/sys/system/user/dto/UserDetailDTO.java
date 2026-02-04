package com.agileboot.admin.modular.sys.system.user.dto;

import com.agileboot.admin.modular.sys.system.post.dto.PostDTO;
import com.agileboot.admin.modular.sys.system.role.dto.RoleDTO;
import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class UserDetailDTO {

    private UserDTO user;

    /**
     * 返回所有role
     */
    private List<RoleDTO> roleOptions;

    /**
     * 返回所有posts
     */
    private List<PostDTO> postOptions;

    private Long postId;

    private Long roleId;

    private Set<String> permissions;

}
