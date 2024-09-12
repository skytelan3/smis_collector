package com.watchtek.watchall.storage.smis.codeql;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class InsecureTest
{
    public static void main(String[] args) throws Exception
    {
        SSLContext context = SSLContext.getInstance("TLS");
        TrustManager[] trustManager = new TrustManager[] { new InsecureTrustManager() };
        context.init(null, trustManager, null);
    }
}
