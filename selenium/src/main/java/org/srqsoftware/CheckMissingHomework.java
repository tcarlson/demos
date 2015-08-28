package org.srqsoftware;

import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CheckMissingHomework
{
    public static final String LOGIN_USER = "myuser";
    public static final String LOGIN_PASSWORD = "mypassword";
    public static final String EMAIL_DESTINATION = "me@myemail.com";
    
    public static void main(String[] args) throws Exception
    {
        String html = scrapeWebContent();
        sendMail("system@localhost", EMAIL_DESTINATION, "Missing Homework Report", html);
    }    
    
    public static String scrapeWebContent() throws Exception
    {
        String html = "";
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        try
        {
            // Log into "Parent Portal" website
            driver.get("https://www.plusportals.com/ManateeSchoolforArts");
            driver.findElement(By.id("UserName")).clear();
            driver.findElement(By.id("UserName")).sendKeys(LOGIN_USER);
            driver.findElement(By.id("Password")).clear();
            driver.findElement(By.id("Password")).sendKeys(LOGIN_PASSWORD);
            driver.findElement(By.xpath("//input[@value='Sign in']")).click();
            
            // Navigate to "Scores" section
            driver.findElement(By.linkText("More...")).click();
            driver.findElement(By.id("tab-4")).click();
            driver.findElement(By.xpath("//div[@id='tbScores']/div/span/span/span[2]/span")).click();
            
            // Change subject to "Math" and open a printable view of scores
            driver.findElement(By.xpath("//ul[@id='scoresSectionDropDownList_listbox']/li[8]")).click();
            driver.findElement(By.xpath("//img[@onclick='printActivityScores()']")).click();
            Thread.sleep(3000);
            // Switch to newly-opened window and capture HTML
            driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
            String scoresHtml = driver.getPageSource();
            html += "<h1>Math</h1>";
            html += extractMissingHomework(scoresHtml);
            // Close newly-opened window and return to original window
            driver.close();
            driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);

            // Change subject to "Language Arts" and open a printable view of scores
            driver.findElement(By.xpath("//div[@id='tbScores']/div/span/span/span")).click();
            driver.findElement(By.xpath("//ul[@id='scoresSectionDropDownList_listbox']/li[7]")).click();
            driver.findElement(By.xpath("//img[@onclick='printActivityScores()']")).click();
            Thread.sleep(3000);
            // Switch to newly-opened window and capture HTML
            driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
            scoresHtml = driver.getPageSource();        
            html += "<h1>Language Arts</h1>";
            html += extractMissingHomework(scoresHtml);
            driver.close();

            return html;
        }
        finally
        {
            driver.quit();
        }
    }

    public static String extractMissingHomework(String html)
    {
        // TODO Use jsoup or xpath and regular expressions to extract relevant rows from the HTML table of homework assignments
        return html;
    }

    private static void sendMail(String from, String to, String subject, String body) throws EmailException
    {
        SimpleEmail email = new SimpleEmail();
        email.setHostName("localhost");
        email.setFrom(from);
        email.addTo(to);
        email.setSubject(subject);
        email.setMsg(body);
        email.send();
    }
}


