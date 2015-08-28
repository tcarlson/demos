package org.srqsoftware;



import junit.framework.TestCase;

import org.junit.Test;

public class WebScrapingTestCase extends TestCase
{
    @Test
    public void testWebScraping() throws Exception
    {
        String html = CheckMissingHomework.scrapeWebContent();
        System.out.println(html);
        // TODO Verify content
    }
}
