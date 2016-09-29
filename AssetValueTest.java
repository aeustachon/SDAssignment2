package assetValue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AssetValueTest
{
    AssetValue assetValue;

    @Before
    public void setUp()
    {
        assetValue = new AssetValue();
    }

    @Test
    public void Canary()
    {
        assertTrue(true);
    }

    @Test
    public void computeAssetValueCorrectValueIsComputed()
    {
        assertEquals(12500000, assetValue.computeAssetValue(5000, 2500));
    }
//Venkat: The above test was for computeAssetValue. The next test should continue with
//computeAssetValue. Should not jump to something else suddenly. Start with something,
//stay with that through a few tests: positive, negative, and may be exception test if needed.

    @Test
    public void computeAssetValueWithNegativeStockPrice()
    {
        assertEquals(-1, assetValue.computeAssetValue(2500, -1));
    }

    @Test
    public void computeAssetValueWithNegativeNumOfShares()
    {
        assertEquals(-1, assetValue.computeAssetValue(-1, 2500));
    }

    @Test public void computeAssetValueVeryLargeNumber()
    {
        assertEquals(-1, assetValue.computeAssetValue(1500000000, 2500));
    }

}
