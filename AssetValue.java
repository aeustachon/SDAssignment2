package assetValue;

import java.util.HashMap;
import java.util.Map;

public class AssetValue
{
    private StockPriceFetcher stockPriceFetcher;
    protected Map<String, Object> assetValuesMap = new HashMap<String, Object>();
    private int netAssetValue = 0;

    public AssetValue(StockPriceFetcher theStockPriceFetcher)
    {
        stockPriceFetcher = theStockPriceFetcher;
        assetValuesMap.put("Net asset values", 0);
    }

    public int computeAssetValue(int numberOfShares, int stockPriceWithCents)
    {
        if(numberOfShares < 0 || stockPriceWithCents <= 0)
        {
            return 0;
        }
        return numberOfShares * stockPriceWithCents;
    }

    protected int getStockPrice(String stock)
    {
        return stockPriceFetcher.getPrice(stock);
    }

    public Map<String, Object> computeNetAssetValues(String stock) {

        int assetValue = computeAssetValue(2000,getStockPrice(stock));
        assetValuesMap.put(stock, assetValue);

        netAssetValue += assetValue;
        assetValuesMap.put("Net asset values", netAssetValue);
        return assetValuesMap;
    }
}



