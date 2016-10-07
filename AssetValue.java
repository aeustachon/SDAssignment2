package assetValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssetValue {
    private StockPriceFetcher stockPriceFetcher;

    public AssetValue(StockPriceFetcher theStockPriceFetcher) {
        stockPriceFetcher = theStockPriceFetcher;
    }

    public int computeAssetValue(int numberOfShares, int stockPriceWithCents) {
        if (numberOfShares < 0 || stockPriceWithCents <= 0) {
            return 0;
        }
        return numberOfShares * stockPriceWithCents;
    }

    protected int getStockPrice(String stock) {
        return stockPriceFetcher.getPrice(stock);
    }

    public Map<String, Object> computeNetAssetValues(Map<String, Integer> stocksAndShares) {

        int totalAssetValue = 0;

        Map<String, Object> assetValuesMap = new HashMap<String, Object>();
        ArrayList<String> listOfStocksWithInvalidTickers = new ArrayList<String>();
        ArrayList<String> listOfStocksWithNetworkErrors = new ArrayList<String>();

        for (String stock : stocksAndShares.keySet()) {
            try {
                getStockPrice(stock);
                int assetValue = computeAssetValue(stocksAndShares.get(stock), getStockPrice(stock));
                assetValuesMap.put(stock, assetValue);
                totalAssetValue += assetValue;
            }catch (IllegalArgumentException e) {
                listOfStocksWithInvalidTickers.add(stock);
            }
            catch(IllegalStateException e) {
                listOfStocksWithNetworkErrors.add(stock);
            }
        }
        assetValuesMap.put("INVALID SYMBOLS", listOfStocksWithInvalidTickers);
        assetValuesMap.put("NETWORK ERRORS", listOfStocksWithNetworkErrors);
        assetValuesMap.put("TOTAL", totalAssetValue);
        return assetValuesMap;
    }
}
