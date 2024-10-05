package demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }


    public void testCase01() {
        System.out.println("Start Test case: testCase01");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.flipkart.com/");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Washing Machine");
        searchBox.submit();
        WebElement sortByPopularity = driver.findElement(By.xpath("//div[text()='Popularity']"));
        sortByPopularity.click();
        List<WebElement> washingMachines = driver.findElements(By.xpath("//div[contains(@class,'_1AtVbE')]//div[@class='_3pLy-c row']//span[contains(@class,'_2_R_DZ')]"));
        int count = 0;
        for (WebElement machine : washingMachines) {
            String ratingText = machine.getText();
            double rating = Double.parseDouble(ratingText.split(" ")[0]);
            if (rating >= 4.0) {
                count++;
            }
        }
        System.out.println("Number of washing machines with rating >= 4 stars: " + count);

        driver.get("https://www.google.com");
        System.out.println("end Test case: testCase01");
    }

    public void testCase02() throws InterruptedException {
        System.out.println("Start Test case: testCase02");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("https://www.flipkart.com");

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("iPhone");
        searchBox.submit();


        Thread.sleep(3000);


        List<WebElement> iphoneItems = driver.findElements(By.xpath("//div[contains(@class,'_1AtVbE')]"));


        for (WebElement item : iphoneItems) {
            try {

                WebElement titleElement = item.findElement(By.xpath(".//a[contains(@class,'IRpwTa')]"));
                String title = titleElement.getText();

                WebElement discountElement = item.findElement(By.xpath(".//div[contains(@class,'_3Ay6Sb')]/span"));
                String discountText = discountElement.getText(); // Format is like "18% off"

                int discount = Integer.parseInt(discountText.replaceAll("[^0-9]", ""));

                if (discount > 17) {
                    System.out.println("Title: " + title);
                    System.out.println("Discount: " + discount + "% off");
                }
            } catch (Exception e) {
                continue;
            }
        }
        driver.get("https://www.google.com");
        System.out.println("end Test case: testCase02");
    }

     public void testCase03() throws InterruptedException {
        System.out.println("Start Test case: testCase03");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("https://www.flipkart.com");

        Thread.sleep(3000);

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Coffee Mug");
        searchBox.submit();

        Thread.sleep(3000);

        WebElement fourStarsAndAboveFilter = driver.findElement(By.xpath("//div[text()='4★ & above']"));
        fourStarsAndAboveFilter.click();

        Thread.sleep(3000);

        List<WebElement> coffeeMugItems = driver.findElements(By.xpath("//div[contains(@class,'_1AtVbE')]"));

        List<Item> mugItemsWithReviews = new ArrayList<>();

        for (WebElement item : coffeeMugItems) {
            try {

                WebElement titleElement = item.findElement(By.xpath(".//a[contains(@class,'IRpwTa')]"));
                String title = titleElement.getText();


                WebElement imageElement = item.findElement(By.xpath(".//img[@class='_396cs4']"));
                String imageUrl = imageElement.getAttribute("src");


                WebElement reviewElement = item.findElement(By.xpath(".//span[contains(@class,'_2_R_DZ')]"));
                String reviewText = reviewElement.getText();

                int numberOfReviews = Integer.parseInt(reviewText.split(" ")[0].replaceAll(",", ""));


                mugItemsWithReviews.add(new Item(title, imageUrl, numberOfReviews));
            } catch (Exception e) {

                continue;
            }
        }
        Collections.sort(mugItemsWithReviews, (a, b) -> b.numberOfReviews - a.numberOfReviews);

        System.out.println("Top 5 Coffee Mugs with highest number of reviews:");
        for (int i = 0; i < Math.min(5, mugItemsWithReviews.size()); i++) {
            Item mug = mugItemsWithReviews.get(i);
            System.out.println("Title: " + mug.title);
            System.out.println("Image URL: " + mug.imageUrl);
            System.out.println("Number of Reviews: " + mug.numberOfReviews);
            System.out.println("------------------------------");
        }
    }
        class Item {
        String title;
        String imageUrl;
        int numberOfReviews;

        public Item(String title, String imageUrl, int numberOfReviews) {
            this.title = title;
            this.imageUrl = imageUrl;
            this.numberOfReviews = numberOfReviews;
            driver.get("https://www.google.com");
            System.out.println("end Test case: testCase03");
        }
}

}


