# Check Missing Homework
Hackathon Project for the Sarasota Software Engineers User Group on 4/17/15

Total Development Time = around 2 hours

## Objective:
Create an automated periodic job which logs into the "Parent Portal" website, checks to see if my son has any missing homework assignments in math or language arts, and e-mails me the results.  

## Technical Overview:
The Selenium WebDriver (http://www.seleniumhq.org/projects/webdriver) is used to automate interaction with the website and "scrape" the desired content.  Relevent parts of the HTML are then extracted using jsoup (http://jsoup.org) or XPath and regular expressions.  Finally, the extracted HTML is sent to me in an e-mail.  The overall program can be called by a daily cron job.  

## Usage:
`mvn exec:java -Dexec.mainClass="org.srqsoftware.CheckMissingHomework"`
