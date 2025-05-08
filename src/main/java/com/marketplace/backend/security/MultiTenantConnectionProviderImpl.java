package com.marketplace.backend.security;


import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<UUID> {

    private final DataSource dataSource;

    public MultiTenantConnectionProviderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(UUID tenantIdentifier) throws SQLException {
        Connection connection = getAnyConnection();
        connection.setSchema("tenant_" + tenantIdentifier.toString().replace("-", "_"));
        return connection;
    }

    @Override
    public void releaseConnection(UUID tenantIdentifier, Connection connection) throws SQLException {
        connection.setSchema(null);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        throw new UnsupportedOperationException("Unwrapping not supported");
    }
}
