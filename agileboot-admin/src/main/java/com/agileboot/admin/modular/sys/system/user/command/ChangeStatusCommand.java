package com.agileboot.admin.modular.sys.system.user.command;

import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class ChangeStatusCommand {

    private Long userId;
    private String status;

}
