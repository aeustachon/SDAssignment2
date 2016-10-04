package assetValue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class AssetValueTest
{
    AssetValue assetValue;
    StockPriceFetcher stockPriceFetcher;
    Map<String, Object> assetValuesMapTest;

    @Before
    public void setUp()
    {
        stockPriceFetcher = Mockito.mock(StockPriceFetcher.class);
        assetValue = new AssetValue(stockPriceFetcher);
        assetValuesMapTest = new HashMap<String, Object>();
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
                .thenThrow(new NullPointerException("Invalid ticker symbol"));

        try
        {
            assetValue.getStockPrice("YHOOO");
            fail("Expected exception for invalid stock ticker symbol.");
        }
        catch(NullPointerException e)
        {
            assertEquals(e.getMessage(), "Invalid ticker symbol");
        }
    }

    @Test
    public void getStockPriceWithNetworkErrorThrowsException()
    {
        StockPriceFetcher stockPriceFetcherWithNetworkError = Mockito.mock(StockPriceFetcher.class);
        when(stockPriceFetcherWithNetworkError.getPrice("YHOO"))
                .thenThrow(new RuntimeException("Network error"));

        assetValue = new AssetValue(stockPriceFetcherWithNetworkError);

        try
        {
            assetValue.getStockPrice("YHOO");
            fail("Expected exception for network error.");
        }
        catch(RuntimeException e){
            assertEquals(e.getMessage(), "Network error");
        }
    }

    @Test
    public void computeNetAssetValuesCorrectlyComputesAssetValue() {
        AssetValue assetValue = new AssetValue(stockPriceFetcher) {
            @Override
            public int getStockPrice(String stock) {
                return 5015;
            }
        };

        assetValue.computeNetAssetValues("YHOO");
        assetValue.computeNetAssetValues("GOOG");

        assertEquals(10030000, assetValue.assetValuesMap.get("YHOO"));
        assertEquals(10030000, assetValue.assetValuesMap.get("GOOG"));
    }

    @Test
    public void computeNetAssetValueCorrectlyComputesNetAssetValue() {
        AssetValue assetValue = new AssetValue(stockPriceFetcher) {
            @Override
            public int getStockPrice(String stock) {
                return 5015;
            }
        };

        assetValue.computeNetAssetValues("YHOO");
        assetValue.computeNetAssetValues("GOOG");

        assertEquals(20060000, assetValue.assetValuesMap.get("Net asset values"));
    }






}
