package com.github.fluent.hibernate.strategy;

import static com.github.fluent.hibernate.strategy.NamingStrategyUtils.concat;

/**
 * A naming strategy for column and table names.
 *
 * @author S.Samsonov
 * @author V.Ladynev
 */
public class HibernateNamingStrategy {

    private static final String COLUMN_NAME_PREFIX = "f_";

    private static final String FOREIGN_KEY_COLUMN_PREFIX = "fk_";

    private static final String FOREIGN_KEY_PREFIX = "fk_";

    private static final String UNIQUE_KEY_PREFIX = "uk_";

    /** Table's prefix. */
    private String tablePrefix;

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String classToTableName(String className) {
        return addTablePrefix(NamingStrategyUtils.classToTableName(className));
    }

    public String propertyToColumnName(String propertyName) {
        return COLUMN_NAME_PREFIX + NamingStrategyUtils.propertyToColumnName(propertyName);
    }

    public String embeddedPropertyToColumnName(String propertyName, String embeddedPropertyName) {
        return COLUMN_NAME_PREFIX
                + concat(NamingStrategyUtils.propertyToColumnName(propertyName),
                        NamingStrategyUtils.propertyToColumnName(embeddedPropertyName));
    }

    public String tableName(String tableName) {
        return NamingStrategyUtils.tableName(tableName);
    }

    public String collectionTableName(String ownerEntityTable, String associatedEntityTable) {
        return collectionTableName(ownerEntityTable, associatedEntityTable, null);
    }

    public String collectionTableName(String ownerEntityTable, String associatedEntityTable,
            String ownerProperty) {
        return addTablePrefix(NamingStrategyUtils.collectionTableName(ownerEntityTable,
                associatedEntityTable, ownerProperty));
    }

    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        return columnName(joinedColumn) + "_id";
    }

    public String foreignKeyColumnName(String propertyName, String propertyTableName) {
        String header = propertyName != null ? NamingStrategyUtils.unqualify(propertyName)
                : propertyTableName;
        return FOREIGN_KEY_COLUMN_PREFIX + columnName(header);
    }

    public String columnName(String columnName) {
        return NamingStrategyUtils.addUnderscores(columnName);
    }

    public String foreignKeyName(String tableName, String columnName) {
        return FOREIGN_KEY_PREFIX + concat(tableName, columnName);
    }

    public String uniqueKeyName(String tableName, String columnName) {
        return UNIQUE_KEY_PREFIX + concat(tableName, columnName);
    }

    private String addTablePrefix(String name) {
        return tablePrefix == null ? name : tablePrefix + name;
    }

}
