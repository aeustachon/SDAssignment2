package assetValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StockPriceFetcher
{
    int getPrice(String stock)
    {
        try
        {
            URL priceListing = new URL("http://ichart.finance.yahoo.com/table.csv?s=" + stock);
            InputStream inStream = priceListing.openStream();
        }
        catch (MalformedURLException e)
        {
            return 0;
        }
        catch (IOException e)
        {
            return 1;
        }

        return 3;
    }

    protected List<String> parseCSV(String line)
    {
        List<String> elementList = new ArrayList<String>();

        return elementList;
    }

}
