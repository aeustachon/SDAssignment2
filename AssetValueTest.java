package assetValue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class AssetValueTest
{
    AssetValue assetValue;
    StockPriceFetcher stockPriceFetcher;
    Map<String, Integer> assetValuesMapTest;
    ArrayList<String> listOfStocksWithErrors;
    AssetValue assetValueMock;
    AssetValue assetValueSpy;

    @Before
    public void setUp()
    {
        assetValueSpy = Mockito.spy(new AssetValue(stockPriceFetcher));
        stockPriceFetcher = Mockito.mock(StockPriceFetcher.class);
        assetValue = new AssetValue(stockPriceFetcher);
        assetValuesMapTest = new HashMap<String, Integer>();
        listOfStocksWithErrors = new ArrayList<String>();
        assetValueMock = Mockito.mock(AssetValue.class);
    }

    @Test
    public void Canary()
    {
        assertTrue(true);
    }

    @Test
    public void correctValueIsComputedUsingGetStockPrice()
    {
        assertEquals(10030000, assetValue.computeAssetValue(2000, 5015));
    }

    @Test
    public void computeAssetValueWithNegativeStockPrice()
    {
        assertEquals(0, assetValue.computeAssetValue(2500, -1));
    }

    @Test
    public void computeAssetValueWithNegativeNumOfShares()
    {
        assertEquals(0, assetValue.computeAssetValue(-1, 2500));
    }

    @Test
    public void computeAssetValueWhenStockPriceIsZero()
    {
        assertEquals(0,assetValue.computeAssetValue(2500, 0));
    }

    @Test
    public void getStockPriceWithOnlyOneStockTickerSymbol()
    {
        when(stockPriceFetcher.getPrice("YHOO")).thenReturn(81496);

        assertEquals(81496, assetValue.getStockPrice("YHOO"));
    }

    @Test
    public void getStockPriceWithDifferentStockTickerSymbols()
    {
        when(stockPriceFetcher.getPrice("AAPL")).thenReturn(2058);

        assertEquals(2058, assetValue.getStockPrice("AAPL"));
    }

    @Test
    public void getStockPriceWithInvalidStockTickerSymbolThrowsException()
    {
        when(stockPriceFetcher.getPrice("YHOOO"))
                .thenThrow(new IllegalArgumentException("Invalid ticker symbol"));

        try
        {
            assetValue.getStockPrice("YHOOO");
            fail("Expected exception for invalid stock ticker symbol.");
        }
        catch(IllegalArgumentException e)
        {
            assertEquals(e.getMessage(), "Invalid ticker symbol");
        }
    }

    @Test
    public void getStockPriceWithNetworkErrorThrowsException()
    {
        StockPriceFetcher stockPriceFetcherWithNetworkError = Mockito.mock(StockPriceFetcher.class);
        when(stockPriceFetcherWithNetworkError.getPrice("YHOO"))
                .thenThrow(new IllegalStateException("Network error"));

        assetValue = new AssetValue(stockPriceFetcherWithNetworkError);

        try
        {
            assetValue.getStockPrice("YHOO");
            fail("Expected exception for network error.");
        }
        catch(IllegalStateException e)
        { //Venkat: This exception makes little sense. Something like IOException or ConnectionException would make more sense.
            assertEquals(e.getMessage(), "Network error");
        }
    }

    @Test
    public void computeNetAssetValuesCorrectlyComputesAssetValue() {

        when(assetValueMock.computeAssetValue(2000,5015)).thenCallRealMethod();
        when(assetValueMock.getStockPrice("YHOO")).thenReturn(5015);
        when(assetValueMock.getStockPrice("GOOG")).thenReturn(5015);
        when(assetValueMock.computeNetAssetValues(assetValuesMapTest)).thenCallRealMethod();

        assetValuesMapTest.put("GOOG", 2000);
        assetValuesMapTest.put("YHOO", 2000);

        assertEquals(10030000, assetValueMock.computeNetAssetValues(assetValuesMapTest).get("YHOO"));
        assertEquals(10030000, assetValueMock.computeNetAssetValues(assetValuesMapTest).get("GOOG"));
        assertEquals(20060000, assetValueMock.computeNetAssetValues(assetValuesMapTest).get("TOTAL"));
    }

    @Test
    public void computeNetAssetValueStoresInvalidSymbolsInList() {
        when(assetValueMock.computeAssetValue(2000,5015)).thenCallRealMethod();
        when(assetValueMock.getStockPrice("YHOO")).thenReturn(5015);
        when(assetValueMock.getStockPrice("GOOG")).thenReturn(5015);
        when(assetValueMock.getStockPrice("INVALID1")).thenThrow(new IllegalArgumentException("Invalid ticker symbol"));
        when(assetValueMock.getStockPrice("INVALID2")).thenThrow(new IllegalArgumentException("Invalid ticker symbol"));
        when(assetValueMock.computeNetAssetValues(assetValuesMapTest)).thenCallRealMethod();

        assetValuesMapTest.put("GOOG", 2000);
        assetValuesMapTest.put("YHOO", 2000);
        assetValuesMapTest.put("INVALID1", 0);
        assetValuesMapTest.put("INVALID2", 0);
        listOfStocksWithErrors = (ArrayList) assetValueMock.computeNetAssetValues(assetValuesMapTest).get("INVALID SYMBOLS");

        assertEquals(10030000, assetValueMock.computeNetAssetValues(assetValuesMapTest).get("GOOG"));
        assertEquals(10030000, assetValueMock.computeNetAssetValues(assetValuesMapTest).get("YHOO"));
        assertTrue(listOfStocksWithErrors.contains("INVALID1"));
        assertTrue(listOfStocksWithErrors.contains("INVALID2"));
    }

    @Test
    public void computeNetAssetValueStoresNetworkErrorSymbolsInList() {
        when(assetValueMock.computeAssetValue(2000,5015)).thenCallRealMethod();
        when(assetValueMock.getStockPrice("YHOO")).thenReturn(5015);
        when(assetValueMock.getStockPrice("GOOG")).thenReturn(5015);
        when(assetValueMock.getStockPrice("NETERROR1")).thenThrow(new IllegalStateException("Network error"));
        when(assetValueMock.getStockPrice("NETERROR2")).thenThrow(new IllegalStateException("Network error"));
        when(assetValueMock.computeNetAssetValues(assetValuesMapTest)).thenCallRealMethod();

        assetValuesMapTest.put("NETERROR1", 0);
        assetValuesMapTest.put("NETERROR2", 0);
        assetValuesMapTest.put("GOOG", 2000);
        assetValuesMapTest.put("YHOO", 2000);
        listOfStocksWithErrors = (ArrayList) assetValueMock.computeNetAssetValues(assetValuesMapTest).get("NETWORK ERRORS");


        assertEquals(10030000, assetValueMock.computeNetAssetValues(assetValuesMapTest).get("GOOG"));
        assertEquals(10030000, assetValueMock.computeNetAssetValues(assetValuesMapTest).get("YHOO"));
        assertTrue(listOfStocksWithErrors.contains("NETERROR1"));
        assertTrue(listOfStocksWithErrors.contains("NETERROR2"));
    }
}
