package assetValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssetValue
{
    private final int SIZE = 10;
    private StockPriceFetcher stockPriceFetcher;
    protected Map<String, Object> assetValuesMap = new HashMap<String, Object>();
    private int netAssetValue = 0;
    ArrayList<String> listOfStocksWithInvalidTickers = new ArrayList<String>();
    ArrayList<String> listOfStocksWithNetworkErrors = new ArrayList<String>();

    public AssetValue(StockPriceFetcher theStockPriceFetcher)
    {
        stockPriceFetcher = theStockPriceFetcher;
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
        if(getStockPrice(stock) == 0)
        {
            listOfStocksWithInvalidTickers.add(stock);
            assetValuesMap.put("Invalid symbol", listOfStocksWithInvalidTickers);
        }
        else if (getStockPrice(stock) == -1)
        {
            listOfStocksWithNetworkErrors.add(stock);
            assetValuesMap.put("Network error", listOfStocksWithNetworkErrors);
        }
        int assetValue = computeAssetValue(2000,getStockPrice(stock));
        assetValuesMap.put(stock, assetValue);

        netAssetValue += assetValue;
        assetValuesMap.put("Net asset values", netAssetValue);
        return assetValuesMap;
    }
}



