/*
 *  Queryman. Java tools for working with queries of PostgreSQL database.
 *
 *  License: MIT License.
 *  To see license follow by http://queryman.org/license.txt
 */
package org.queryman.builder.boot.adapter;

import org.queryman.builder.Metadata;
import org.queryman.builder.boot.jaxb.JaxbCfg;
import org.queryman.builder.cfg.Settings;
import org.queryman.builder.impl.MetadataImpl;

/**
 * @author Timur Shaidullin
 */
public final class JaxbMetadataAdapter {
    public static Metadata convert(JaxbCfg jaxbCfg) {
        Metadata metadata = new MetadataImpl();
        metadata.addProperty(Settings.USE_UPPERCASE, String.valueOf(jaxbCfg.useUppercase));

        return metadata;
    }
}
