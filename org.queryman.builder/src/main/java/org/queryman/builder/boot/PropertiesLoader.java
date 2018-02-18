/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.boot;

import org.queryman.builder.boot.adapter.PropertiesMetadataAdapter;
import org.queryman.builder.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Load a persistence set from a properties file
 *
 * @author Timur Shaidullin
 */
public final class PropertiesLoader extends AbstractConfigLoader {

    protected final Properties properties = new Properties();

    public PropertiesLoader(String cfgFile) {
        super(cfgFile);
    }

    @Override
    public boolean load() throws IOException, ClassNotFoundException {
        if (StringUtils.isEmpty(cfgFile)) {
            throw new IllegalStateException("Properties file is not specified");
        }
        InputStream stream = getResource(cfgFile);

        try {
            properties.load(stream);
            return true;
        } catch (IOException e) {
            e.printStackTrace();  //todo log stacktrace
            return false;
        }
    }

    @Override
    public Properties getConfiguration() {
        return PropertiesMetadataAdapter.convert(properties);
    }
}
