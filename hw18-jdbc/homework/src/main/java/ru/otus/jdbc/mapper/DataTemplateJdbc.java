package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private static final Logger log = LoggerFactory.getLogger(DataTemplateJdbc.class);

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData
    ) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    public Object[] getObjectByResultSet(ResultSet rs) throws SQLException {
        Object[] objects = new Object[entityClassMetaData.getAllFields().size()];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = rs.getObject(i+1);
        }
        log.debug("list values is - {}", Arrays.toString(objects));
        return objects;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        log.debug("select by Id sql - {}", entitySQLMetaData.getSelectByIdSql());
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    log.debug("select by Id view constructor parameter types {}", (Object) this.entityClassMetaData.getConstructor().getParameterTypes());
                    return this.entityClassMetaData.getConstructor().newInstance(this.getObjectByResultSet(rs));
                }
                return null;
            } catch (SQLException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new UnsupportedOperationException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        log.debug("selectAll sql - {}", entitySQLMetaData.getSelectAllSql());
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    clientList.add(this.entityClassMetaData.getConstructor().newInstance(this.getObjectByResultSet(rs)));
                }
                return clientList;
            } catch (SQLException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new UnsupportedOperationException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    public List<Object> getObject(T client)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        log.debug("fields - "+fields);
        List<Object> objects = new ArrayList<>();
        log.debug("declared methods - " + Arrays.toString(client.getClass().getDeclaredMethods()));
        for (Field field : fields) {
            log.debug("invoke method - " + this.getMethodName(field.getName()));
            objects.add(client.getClass().getDeclaredMethod(this.getMethodName(field.getName())).invoke(client));
        }
        log.debug("returned object for insert"+objects);
        return objects;
    }

    public String getMethodName(String name) {
        return "get".concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));
    }

    @Override
    public long insert(Connection connection, T client) {
        String sql = entitySQLMetaData.getInsertSql();
        log.debug("insert sql - {}", entitySQLMetaData.getInsertSql());
        log.debug("insert client - {}", client);
        try {
            return dbExecutor.executeStatement(connection, sql, this.getObject(client));
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        String sql = entitySQLMetaData.getUpdateSql();
        log.debug("update sql - {}", sql);
        log.debug("update client - {}", client);
        try {
            List<Object> objects = this.getObject(client);
            objects.add(client.getClass().getDeclaredMethod(this.getMethodName(entityClassMetaData.getIdField().getName()))
                    .invoke(client));
            dbExecutor.executeStatement(connection, sql, objects);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }
}
