package com.heiye.clims.user.biz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heiye.clims.user.biz.domain.dos.UserDO;


public interface UserDOMapper extends BaseMapper<UserDO> {
    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
}