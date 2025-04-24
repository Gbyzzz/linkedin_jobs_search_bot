package com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.type;

import com.gbyzzz.linkedinjobsbotentityservice.entity.UserProfile;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PGUserProfileBotState implements UserType<UserProfile.BotState> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<UserProfile.BotState> returnedClass() {
        return UserProfile.BotState.class;
    }

    @Override
    public boolean equals(UserProfile.BotState x, UserProfile.BotState y) {
        return x.name().equals(y.name());
    }

    @Override
    public int hashCode(UserProfile.BotState x) {
        return x.hashCode();
    }

    @Override
    public UserProfile.BotState nullSafeGet(ResultSet rs, int position,
                                            SharedSessionContractImplementor session,
                                            Object owner) throws SQLException {
            return UserProfile.BotState.valueOf(rs.getString(position));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, UserProfile.BotState value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if(value!=null) {
            st.setObject(index, value.name(), Types.OTHER);
        }
    }

    @Override
    public UserProfile.BotState deepCopy(UserProfile.BotState value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(UserProfile.BotState value) {
        return null;
    }

    @Override
    public UserProfile.BotState assemble(Serializable cached, Object owner) {
        return null;
    }

    @Override
    public UserProfile.BotState replace(UserProfile.BotState detached, UserProfile.BotState managed,
                                        Object owner) {
        return detached;
    }
}