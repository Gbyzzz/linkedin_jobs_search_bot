package com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.type;

import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PGUserProfileUserRole implements UserType<UserProfile.UserRole> {
    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<UserProfile.UserRole> returnedClass() {
        return UserProfile.UserRole.class;
    }

    @Override
    public boolean equals(UserProfile.UserRole x, UserProfile.UserRole y) {
        return x.name().equals(y.name());
    }

    @Override
    public int hashCode(UserProfile.UserRole x) {
        return x.hashCode();
    }

    @Override
    public UserProfile.UserRole nullSafeGet(ResultSet rs, int position,
                                            SharedSessionContractImplementor session,
                                            Object owner) throws SQLException {
        return UserProfile.UserRole.valueOf(rs.getString(position));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, UserProfile.UserRole value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if(value!=null) {
            st.setObject(index, value.name(), Types.OTHER);
        }
    }

    @Override
    public UserProfile.UserRole deepCopy(UserProfile.UserRole value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(UserProfile.UserRole value) {
        return null;
    }

    @Override
    public UserProfile.UserRole assemble(Serializable cached, Object owner) {
        return null;
    }

    @Override
    public UserProfile.UserRole replace(UserProfile.UserRole detached, UserProfile.UserRole managed,
                                        Object owner) {
        return detached;
    }
}
