package org.srqsoftware;



import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ExtractHtmlTestCase extends TestCase
{
    @Test
    public void testExtractHtml() throws Exception
    {
        String html = FileUtils.readFileToString(new File("src/test/resources/testpage.html"));
        html = CheckMissingHomework.extractMissingHomework(html);
        System.out.println(html);
        // TODO Verify content
    }
}
