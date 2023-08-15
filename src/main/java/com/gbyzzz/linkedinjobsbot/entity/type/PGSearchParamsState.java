package com.gbyzzz.linkedinjobsbot.entity.type;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PGSearchParamsState implements UserType<SearchParams.SearchState> {
    
    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<SearchParams.SearchState> returnedClass() {
        return SearchParams.SearchState.class;
    }

    @Override
    public boolean equals(SearchParams.SearchState x, SearchParams.SearchState y) {
        return x.name().equals(y.name());
    }

    @Override
    public int hashCode(SearchParams.SearchState x) {
        return x.hashCode();
    }

    @Override
    public SearchParams.SearchState nullSafeGet(ResultSet rs, int position,
                                            SharedSessionContractImplementor session,
                                            Object owner) throws SQLException {
        return SearchParams.SearchState.valueOf(rs.getString(position));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, SearchParams.SearchState value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if(value!=null) {
            st.setObject(index, value.name(), Types.OTHER);
        }
    }

    @Override
    public SearchParams.SearchState deepCopy(SearchParams.SearchState value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(SearchParams.SearchState value) {
        return null;
    }

    @Override
    public SearchParams.SearchState assemble(Serializable cached, Object owner) {
        return null;
    }

    @Override
    public SearchParams.SearchState replace(SearchParams.SearchState detached, SearchParams.SearchState managed,
                                        Object owner) {
        return detached;
    }
}
