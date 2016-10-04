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
    boolean getStockPriceIsCalled;

    @Before
    public void setUp()
    {
        stockPriceFetcher = Mockito.mock(StockPriceFetcher.class);
        assetValue = new AssetValue(stockPriceFetcher);
        getStockPriceIsCalled = false;
    }

    @Test
    public void Canary()
    {
        assertTrue(true);
    }

    @Test
    public void correctValueIsComputed()
    {
        assertEquals(12537500, assetValue.computeAssetValue(5015, 2500));
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
        when(stockPriceFetcher.getPrice("YHOOO")).thenThrow(new RuntimeException("Invalid ticker symbol"));

        try
        {
            assetValue.getStockPrice("YHOOO");
            fail("Expected exception for invalid stock ticker symbol.");
        }
        catch(RuntimeException e)
        {
            assertEquals(e.getMessage(), "Invalid ticker symbol");
        }
    }

    @Test
    public void getStockPriceWithNetworkErrorThrowsException()
    {
        StockPriceFetcher stockPriceFetcherWithNetworkError = Mockito.mock(StockPriceFetcher.class);
        when(stockPriceFetcherWithNetworkError.getPrice("YHOO")).thenThrow(new RuntimeException("Network error"));

        assetValue = new AssetValue(stockPriceFetcherWithNetworkError);

        try
        {
            assetValue.getStockPrice("YHOO");
            fail("Expected exception for network error.");
        }
        catch(RuntimeException e){ //Venkat: Use a different exception here than the one used for invalid symbol so we clearly know the difference about what happended.
            assertEquals(e.getMessage(), "Network error");
        }
    }

    @Test
    public void computeNetAssetValuesCallsGetStockPriceFunction()
    {
        AssetValue assetValue = new AssetValue(stockPriceFetcher)
        {
            @Override
            protected int getStockPrice(String stock)
            {
                getStockPriceIsCalled = true;
                return 0;
            }
        };

        assetValue.getStockPrice("YHOO");

        assertTrue(getStockPriceIsCalled);
    }
}
