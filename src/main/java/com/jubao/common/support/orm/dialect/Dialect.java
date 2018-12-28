package com.jubao.common.support.orm.dialect;

/**
 * 数据库方言抽象类
 *
 */
public abstract class Dialect {

    /**
     * 得到分页sql
     * @param sql
     * @param offset
     * @param limit
     * @return
     */
    public abstract String getLimitString(String sql, int offset, int limit);

    /**
     * 得到分页sql
     * @param sql
     * @return
     */
    public abstract String getCountString(String sql);

}
