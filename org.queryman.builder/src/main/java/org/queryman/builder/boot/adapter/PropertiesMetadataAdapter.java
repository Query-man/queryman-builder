/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License.
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.boot.adapter;

import org.queryman.builder.Metadata;
import org.queryman.builder.impl.MetadataImpl;

import java.util.Properties;

/**
 * @author Timur Shaidullin
 */
public class PropertiesMetadataAdapter implements MetadataAdapter {
    private final Properties properties;

    public PropertiesMetadataAdapter(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Metadata convert() {
        return new MetadataImpl().addProperties(properties);
    }
}
