/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.db.jdbc.mapper;
import net.hasor.db.jdbc.RowMapper;
import net.hasor.db.types.TypeHandler;
import net.hasor.db.types.TypeHandlerRegistry;
import net.hasor.utils.ResourcesUtils;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author 赵永春 (zyc@byshell.org)
 * @version : 2014年5月23日
 */
public abstract class AbstractRowMapper<T> implements RowMapper<T> {
    private final TypeHandlerRegistry handlerRegistry;

    public AbstractRowMapper() {
        this(TypeHandlerRegistry.DEFAULT);
    }

    public AbstractRowMapper(TypeHandlerRegistry typeHandler) {
        this.handlerRegistry = Objects.requireNonNull(typeHandler, "typeHandler is null.");
    }

    public TypeHandlerRegistry getHandlerRegistry() {
        return this.handlerRegistry;
    }

    /**获取列的值*/
    protected Object getResultSetValue(ResultSet rs, int columnIndex) throws SQLException {
        return getResultSetTypeHandler(rs, columnIndex, null).getResult(rs, columnIndex);
    }

    /**获取列的值*/
    protected Object getResultSetValue(ResultSet rs, int columnIndex, Class<?> targetType) throws SQLException {
        TypeHandler<?> typeHandler = getResultSetTypeHandler(rs, columnIndex, targetType);
        return typeHandler.getResult(rs, columnIndex);
    }

    /**获取读取列用到 的 TypeHandler列的值*/
    public TypeHandler<?> getResultSetTypeHandler(ResultSet rs, int columnIndex, Class<?> targetType) throws SQLException {
        int columnType = rs.getMetaData().getColumnType(columnIndex);
        String columnClassName = rs.getMetaData().getColumnClassName(columnIndex);
        JDBCType jdbcType = JDBCType.valueOf(columnType);
        Class<?> columnTypeClass = targetType;
        if (columnTypeClass == null) {
            try {
                columnTypeClass = ResourcesUtils.classForName(columnClassName);
            } catch (ClassNotFoundException e) {
                /**/
            }
        }
        TypeHandler<?> typeHandler = this.handlerRegistry.getTypeHandler(columnTypeClass, jdbcType);
        if (typeHandler == null) {
            String message = "jdbcType=" + jdbcType.getVendorTypeNumber() + " ,columnTypeClass=" + columnTypeClass;
            throw new SQLException("no typeHandler is matched to any available " + message);
        }
        return typeHandler;
    }
}