package assetValue;

public class AssetValue
{
    private StockPriceFetcher stockPriceFetcher;


    public AssetValue(StockPriceFetcher theStockPriceFetcher)
    {
        stockPriceFetcher = theStockPriceFetcher;
    }

    public int computeAssetValue(int numberOfShares, int stockPriceWithCents)
    {
        if(numberOfShares < 0 || stockPriceWithCents <= 0)
            return 0;
        return numberOfShares * stockPriceWithCents;
    }

    protected int getStockPrice(String stock)
    {
        return stockPriceFetcher.getPrice(stock);
    }
}
