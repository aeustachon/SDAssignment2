package assetValue;

public class AssetValue
{
    //Venkat: Is the stockPrice in cents or whole dollars?
    public int computeAssetValue(int numberOfShares, int stockPrice)
    {
        if(numberOfShares < 0 || stockPrice < 0)
        {
            return -1;
        }

        return numberOfShares * stockPrice;
    }
}
