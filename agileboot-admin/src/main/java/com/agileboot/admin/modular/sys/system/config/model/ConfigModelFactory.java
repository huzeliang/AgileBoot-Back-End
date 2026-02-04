package com.agileboot.admin.modular.sys.system.config.model;

import com.agileboot.admin.modular.sys.system.config.db.SysConfigEntity;
import com.agileboot.admin.modular.sys.system.config.db.SysConfigService;
import com.agileboot.common.exception.ApiException;
import com.agileboot.common.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 配置模型工厂
 * @author valarchie
 */
@Component
@RequiredArgsConstructor
public class ConfigModelFactory {

    private final SysConfigService configService;

    public ConfigModel loadById(Long configId) {
        SysConfigEntity byId = configService.getById(configId);
        if (byId == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, configId, "参数配置");
        }
        return new ConfigModel(byId, configService);
    }

    public ConfigModel create() {
        return new ConfigModel(configService);
    }


}
