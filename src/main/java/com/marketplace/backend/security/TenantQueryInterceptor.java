package com.marketplace.backend.security;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TenantQueryInterceptor implements StatementInspector {

    @Override
    public String inspect(String sql) {
        UUID tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            return sql;
        }

        String[] entityTables = {"establishment_tbl", "waiter_tbl", "customer_tbl"};
        for (String table : entityTables) {
            if (sql.toLowerCase().contains("from " + table)) {
                sql = sql.replace("FROM " + table, "FROM " + table + " t JOIN tenant_entity tm ON t.id = tm.entity_id AND tm.entity_type = '" + table.replace("_entity", "").toUpperCase() + "' AND tm.tenant_id = '" + tenantId + "'");
                sql = sql.replace("from " + table, "from " + table + " t JOIN tenant_entity tm ON t.id = tm.entity_id AND tm.entity_type = '" + table.replace("_entity", "").toUpperCase() + "' AND tm.tenant_id = '" + tenantId + "'");
            }
        }

        return sql;
    }
}
