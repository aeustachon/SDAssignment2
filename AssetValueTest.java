package assetValue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class AssetValueTest
{
    AssetValue assetValue;
    StockPriceFetcher stockPriceFetcher;

    @Before
    public void setUp()
    {
        stockPriceFetcher = Mockito.mock(StockPriceFetcher.class);
        assetValue = new AssetValue(stockPriceFetcher);
    }

    @Test
    public void Canary()
    {
        assertTrue(true);
    }

    @Test
    public void correctValueIsComputed()
    {
        assertEquals(12500000, assetValue.computeAssetValue(5000, 2500));
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
        try
        {
            assertEquals(81496, assetValue.getStockPrice("YHOO"));
        }
        catch(Exception e) {}
    }

    @Test
    public void getStockPriceWithDifferentStockTickerSymbols()
    {
        when(stockPriceFetcher.getPrice("AAPL")).thenReturn(2058);
        try
        {
            assertEquals(2058, assetValue.getStockPrice("AAPL"));
        }
        catch (Exception e) {}
    }

    @Test
    public void getStockPriceWithInvalidStockTickerSymbolReturnsZero()
    {
        when(stockPriceFetcher.getPrice("YHOOO")).thenReturn(-1);
        try
        {
            assertEquals(-1, assetValue.getStockPrice("YHOOO"));
            fail();
        }
        catch(Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void getStockPriceWithNetworkErrorReturnsZero()
    {
        StockPriceFetcher stockPriceFetcherWithError = Mockito.mock(StockPriceFetcher.class);
        when(stockPriceFetcherWithError.getPrice("YHOO"))
                .thenThrow(new RuntimeException("Network error"));
        try {
            assertEquals(0, assetValue.getStockPrice("YHOO"));
        }
        catch(Exception e){}
    }
}
