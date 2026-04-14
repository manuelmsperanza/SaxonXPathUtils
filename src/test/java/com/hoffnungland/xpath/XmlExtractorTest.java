package com.hoffnungland.xpath;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class XmlExtractorTest {

    @Test
    void extractStringSupportsNamespaces() throws Exception {
        XmlExtractor extractor = new XmlExtractor();
        extractor.init("""
                <root xmlns:bk=\"urn:book\">
                  <bk:title>XPath in Action</bk:title>
                </root>
                """);

        String value = extractor.extractString("/root/bk:title/text()", "xmlns:bk=\"urn:book\"");

        assertEquals("XPath in Action", value);
    }
}
