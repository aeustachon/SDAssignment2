package assetValue;

public class AssetValue
{

    private int netAssetValue;

    public void AssetValue()
    {
        netAssetValue = 0;
    }

    public int computeAssetValue(int numberOfShares, int stockPrice)
    {
        if(numberOfShares < 0 || stockPrice < 0)
        {
            return -1;
        }

        int assetValue = numberOfShares * stockPrice;
        netAssetValue += assetValue;
        return assetValue;
    }

    public int getNetAssetValue()
    {
        return netAssetValue;
    }
}