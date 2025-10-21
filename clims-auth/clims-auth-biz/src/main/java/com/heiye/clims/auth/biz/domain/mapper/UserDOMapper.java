package com.heiye.clims.auth.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heiye.clims.auth.biz.domain.dos.UserDO;
import com.heiye.clims.auth.biz.model.dto.RegisterCheckRspDTO;

public interface UserDOMapper extends BaseMapper<UserDO> {
    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);

    RegisterCheckRspDTO checkRegisterConflicts(String username, String email);
}