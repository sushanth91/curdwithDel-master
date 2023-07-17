package com.crudoperationRestApi;



import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringJsonUserType implements UserType {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public int[] sqlTypes() {
        return new int[] {Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass() {
        return Object.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return (x != null) ? x.hashCode() : 0;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        String jsonValue = rs.getString(names[0]);
        if (jsonValue == null) {
            return null;
        }
        try {
            return objectMapper.readValue(jsonValue, Object.class);
        } catch (IOException e) {
            throw new HibernateException("Failed to deserialize JSON value: " + jsonValue, e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            try {
                String jsonValue = objectMapper.writeValueAsString(value);
                st.setString(index, jsonValue);
            } catch (JsonProcessingException e) {
                throw new HibernateException("Failed to serialize object to JSON: " + value, e);
            }
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            return objectMapper.readValue(jsonValue, Object.class);
        } catch (IOException e) {
            throw new HibernateException("Failed to deep copy object: " + value, e);
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object deepCopy = deepCopy(value);
        if (deepCopy instanceof Serializable) {
            return (Serializable) deepCopy;
        }
        throw new HibernateException("Value is not serializable: " + value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}

