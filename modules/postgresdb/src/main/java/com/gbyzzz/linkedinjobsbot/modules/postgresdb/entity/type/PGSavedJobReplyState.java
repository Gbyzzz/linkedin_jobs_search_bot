package com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.type;

import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SavedJob;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PGSavedJobReplyState implements UserType<SavedJob.ReplyState> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<SavedJob.ReplyState> returnedClass() {
        return SavedJob.ReplyState.class;
    }

    @Override
    public boolean equals(SavedJob.ReplyState x, SavedJob.ReplyState y) {
        return x.name().equals(y.name());
    }

    @Override
    public int hashCode(SavedJob.ReplyState x) {
        return x.hashCode();
    }

    @Override
    public SavedJob.ReplyState nullSafeGet(ResultSet rs, int position,
                                           SharedSessionContractImplementor session,
                                           Object owner) throws SQLException {
        String value = rs.getString(position);
        return (value != null) ? SavedJob.ReplyState.valueOf(value) : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, SavedJob.ReplyState value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if(value!=null) {
            st.setObject(index, value.name(), Types.OTHER);
        }
    }

    @Override
    public SavedJob.ReplyState deepCopy(SavedJob.ReplyState value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(SavedJob.ReplyState value) {
        return null;
    }

    @Override
    public SavedJob.ReplyState assemble(Serializable cached, Object owner) {
        return null;
    }

    @Override
    public SavedJob.ReplyState replace(SavedJob.ReplyState detached, SavedJob.ReplyState managed,
                                       Object owner) {
        return detached;
    }
}
