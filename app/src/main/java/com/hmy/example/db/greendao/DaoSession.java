package com.hmy.example.db.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.hmy.example.bean.ApiInfo;

import com.hmy.example.db.greendao.ApiInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig apiInfoDaoConfig;

    private final ApiInfoDao apiInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        apiInfoDaoConfig = daoConfigMap.get(ApiInfoDao.class).clone();
        apiInfoDaoConfig.initIdentityScope(type);

        apiInfoDao = new ApiInfoDao(apiInfoDaoConfig, this);

        registerDao(ApiInfo.class, apiInfoDao);
    }
    
    public void clear() {
        apiInfoDaoConfig.clearIdentityScope();
    }

    public ApiInfoDao getApiInfoDao() {
        return apiInfoDao;
    }

}
