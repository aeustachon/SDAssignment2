package stocks;

public class AssetValue {

    private int netAssetValue = 0;

    public int computeAssetValue(int numberOfShares, int stockPrice)
    {
        int assetValue = numberOfShares * stockPrice;
        netAssetValue += assetValue;
        return assetValue;
    }

    public int getNetAssetValue() {
        return netAssetValue;
    }



}
