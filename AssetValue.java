package assetValue;

public class AssetValue
{
    private StockPriceFetcher stockPriceFetcher;

    public AssetValue(StockPriceFetcher theStockPriceFetcher)
    {
        stockPriceFetcher = theStockPriceFetcher;
    }

    //Venkat: How are we going to deal with cents?
    public int computeAssetValue(int numberOfShares, int stockPrice)
    {
        if(numberOfShares < 0 || stockPrice <= 0)
        {
            return 0;
        }

        return numberOfShares * stockPrice;
    }

    protected int getStockPrice(String stock) throws Exception
    {
        int price = stockPriceFetcher.getPrice(stock);
        if(price == -1)
            throw new Exception();
        else
            return stockPriceFetcher.getPrice(stock);
    }

}
