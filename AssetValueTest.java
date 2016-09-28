package assetValue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StocksTest
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
    public void correctValueIsComputed()
    {
        assertEquals(12500000, assetValue.computeAssetValue(5000, 2500));
    }

    @Test
    public void computeAssetValueAddsToNetAssetValue()
    {
        assetValue.computeAssetValue(2500,100);
        assetValue.computeAssetValue(2500,100);

        assertEquals (500000, assetValue.getNetAssetValue());
    }

}
