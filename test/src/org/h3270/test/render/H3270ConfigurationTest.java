package org.h3270.test.render;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import junit.framework.TestCase;

import org.h3270.render.H3270Configuration;

/*
 * Copyright (C) 2004-2008 akquinet tech@spree
 *
 * This file is part of h3270.
 *
 * h3270 is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * h3270 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with h3270; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA
 */

public class H3270ConfigurationTest extends TestCase {

    public void testCreateEmptyConfig()
    {
        try
        {
            createConfig("");
            fail();
        } catch (RuntimeException e)
        {
            // expected
        }
    }

    public void testColorschemeWithoutDefault() throws Exception {
        final String colorscheme = "Dark Background";
        final String content = "<colorschemes>"
            + getScheme(colorscheme)
            + "</colorschemes>"
            + "<fonts>"
            + getFont("terminal")
            + "</fonts>"
            + getLogicalUnitConfig(false, "");

        final H3270Configuration config = createConfig(content);

        assertEquals(colorscheme, config.getColorSchemeDefault());
    }

    public void testColorschemeWithDefault() throws Exception
    {
        final String colorscheme = "Second Color";
        final String content = "<colorschemes default=\"" + colorscheme + "\">"
            + getScheme("First Color")
            + getScheme(colorscheme)
            + getScheme("Third Color")
            + "</colorschemes>"
            + "<fonts>"
            + getFont("terminal")
            + "</fonts>"
            + getLogicalUnitConfig(false, "");

        final H3270Configuration config = createConfig(content);

        assertEquals(colorscheme, config.getColorSchemeDefault());
    }

    public void testFontWithoutDefault() throws Exception
    {
        final String fontname = "terminal";
        final String content = "<colorschemes>"
            + getScheme("Dark Background")
            + "</colorschemes>"
            + "<fonts>"
            + getFont(fontname)
            + "</fonts>"
            + getLogicalUnitConfig(false, "");

        final H3270Configuration config = createConfig(content);
        assertEquals(fontname, config.getFontnameDefault());
    }

    public void testFontWithDefault() throws Exception
    {
        final String fontname = "terminal";
        final String content = "<colorschemes>"
            + getScheme("Dark Background")
            + "</colorschemes>"
            + "<fonts default=\"" + fontname + "\">"
            + getFont("first font")
            + getFont(fontname)
            + getFont("third font")
            + "</fonts>"
            + getLogicalUnitConfig(false, "");

        final H3270Configuration config = createConfig(content);
        assertEquals(fontname, config.getFontnameDefault());
    }

    public void testLogicalUnits() throws Exception
    {
      final boolean usePool = true;
      final String luBuilder = "builder";
      final String content = getLogicalUnitConfig(usePool, luBuilder)
        + "<colorschemes>"
        + getScheme("Dark Background")
        + "</colorschemes>"
        + "<fonts>"
        + getFont("terminal")
        + "</fonts>";

      final H3270Configuration config = createConfig(content);
      assertEquals(usePool, config.getChild("logical-units").getChild("use-pool").getValueAsBoolean());
      assertEquals(luBuilder, config.getChild("logical-units").getChild("lu-builder").getValue());
    }

    private String getLogicalUnitConfig(final boolean usePool,
        final String luBuilder) {
      String logicalUnits = "<logical-units>"
        + "<use-pool>"
        + usePool
        + "</use-pool>"
        + "<lu-builder>"
        + luBuilder
        + "</lu-builder>"
        + "</logical-units>";
      return logicalUnits;
    }

    private H3270Configuration createConfig(String content) {
        String data = "<h3270>" + content + "</h3270>";

        ByteArrayInputStream stream = new ByteArrayInputStream(data.getBytes());
        DataInputStream in = new DataInputStream(stream);
        return H3270Configuration.create(in);
    }

    private String getScheme(String name)
    {
        return "<scheme name=\"" + name + "\" pnfg=\"cyan\" pnbg=\"black\" "
        + "pifg=\"white\" pibg=\"black\" phfg=\"black\" phbg=\"black\" unfg=\"lime\" "
        + "unbg=\"#282828\" uifg=\"red\" uibg=\"#282828\" uhfg=\"red\" "
        + "uhbg=\"#282828\" />";
    }

    private String getFont(String name)
    {
        return "<font name=\"" + name + "\" description=\"Terminal\" />";
    }
}
