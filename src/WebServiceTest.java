import java.net.*;
import java.io.*;
import java.util.*;

public class WebServiceTest {

    public static void main (String args[]) {
        try
        {
            URL              url;
            URLConnection    urlConn;
            DataOutputStream printout;
            BufferedReader   input;

            // URL of CGI-Bin script.
            url = new URL ("https://toxgate.nlm.nih.gov/cgi-bin/sis/search2/");
            // URL connection channel.
            urlConn = url.openConnection();
            // Let the run-time system (RTS) know that we want input.
            urlConn.setDoInput (true);
            // Let the RTS know that we want to do output.
            urlConn.setDoOutput (true);
            // No caching, we want the real thing.
            urlConn.setUseCaches (false);
            // Specify the content type.
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // Send POST output.
            printout = new DataOutputStream (urlConn.getOutputStream ());
            String content = "queryxxx=benzene&database=hsdb&Stemming=1&" + "and=1&second_search=1&gateway=1&chemsyn=1";
            printout.writeBytes (content);
            printout.flush ();
            printout.close ();
            // Get response data.
            input =new BufferedReader(new InputStreamReader (urlConn.getInputStream()));
            String str;
            while (null != ((str = input.readLine())))
            {
                System.out.println (str);
            }
            input.close ();
        }

        catch (MalformedURLException me)
        {
            System.err.println("MalformedURLException: " + me);
        }
        catch (IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}