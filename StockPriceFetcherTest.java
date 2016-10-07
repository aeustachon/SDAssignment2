package assetValue;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
public class StockPriceFetcherTest
{
    StockPriceFetcher stockPriceFetcher = new StockPriceFetcher();
    @Before
    public void setUp()
    {
        stockPriceFetcher = new StockPriceFetcher();
    }

    @Test
    public void Canary()
    {
        stockPriceFetcher.getPrice("YHOO");
        assertTrue(true);
    }

    @Test
    public void getStockPriceReturns0WithInvalidTicker()
    {
        stockPriceFetcher.getPrice("YHOOO");
        assertEquals(0,stockPriceFetcher.getPrice("YHOOO"));
    }

    @Test
    public void parseCSVReturnsCorrectlyParsed()
    {
        List<String> testList = new ArrayList<String>();
        testList.add("a");
        testList.add("string");

        assertEquals(testList.get(0), stockPriceFetcher.parseCSV("a, string").get(0));
        assertEquals(testList.get(1), stockPriceFetcher.parseCSV("a, string").get(1));
    }
}
