package com.watchtek.watchall;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.cim.CIMClass;
import javax.cim.CIMInstance;
import javax.net.ssl.X509TrustManager;
import javax.wbem.client.WBEMClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.watchtek.watchall.storage.smis.manager.CIMClientManager;
import com.watchtek.watchall.storage.smis.manager.PropertiesManager;
import com.watchtek.watchall.storage.smis.util.ClassMapUtils;
import com.watchtek.watchall.storage.smis.util.FileUtils;
import com.watchtek.watchall.storage.smis.vo.SmisServer;

@SpringBootApplication
public class SmisCollectorApplication
{
    private static final Logger LOG = LoggerFactory.getLogger(SmisCollectorApplication.class);
    
    static Boolean isFileWrite = PropertiesManager.getInstance().getProperty("isFileWrite", false);
    static String addr = PropertiesManager.getInstance().getProperty("smis.server.addr", "localhost:5989");
    static String id = PropertiesManager.getInstance().getProperty("smis.server.id", "");
    static String password = PropertiesManager.getInstance().getProperty("smis.server.password", "");
    static String namespace = PropertiesManager.getInstance().getProperty("smis.server.namespace", "");
    static String vendorKey = PropertiesManager.getInstance().getProperty("smis.server.vendorkey", "");
    static String savePath = PropertiesManager.getInstance().getProperty("default.save.path", "./src/main/resources/instances/");

    public static void main(String[] args)
    {
        SpringApplication.run(SmisCollectorApplication.class, args);
        SmisServer server = new SmisServer(addr, id, password, namespace);
        
        
        // 분석을 위한 인스턴스 파일 저장. 프로퍼티의 isFileWrite 항목이 true일 경우, 텍스트 및 바이너리 파일을 생성함.
        printExistInstances(server);

        // 분석을 위한 인스턴스 트리 저장
        printClassMap(server);

        closeServers();
    }
    

    private static void printExistInstances(SmisServer server)
    {
        LOG.info("[1/3][Start] : Print Instance To Text");
        
        // 서버의 url, 로그인 정보를 바탕으로 client, namespace 생성
        WBEMClient client = CIMClientManager.getInstance().getClient(server);

        Map<String, List<CIMInstance>> instanceMap = new HashMap<String, List<CIMInstance>>();
        int cnt = 0;

        try
        {
            // 네임스페이스 하위의 모든 클래스 가져오기
            List<CIMClass> classList = CIMClientManager.getInstance().enumerateClasses(client, server.getNamespace());

            // 각 클래스별 인스턴스 출력
            for (CIMClass cimClass : classList)
            {
                String className = cimClass.getName();

                LOG.info("(" + ++cnt + "/" + classList.size() + ") " + className);

                List<CIMInstance> instanceList = CIMClientManager.getInstance().getInstanceList(client, server.getNamespace(), className);
                if (isFileWrite)
                {
                    CIMClientManager.getInstance().printWriteInstances(instanceList); // 인스턴스 리스트 txt 파일 출력
                    instanceMap.put(className, instanceList);
                }
                else
                {
                    CIMClientManager.getInstance().printInstances(instanceList);
                }
            }

            if (isFileWrite)
            {
                FileUtils.saveAsFile(instanceMap, savePath + vendorKey); // 모든 인스턴스 리스트 binary 파일 출력
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        LOG.info("[1/3][End] : Print Instance To Text");
    }

    @SuppressWarnings("unused")
    private static void printInstance(SmisServer server, String className)
    {
        // 서버의 url, 로그인 정보를 바탕으로 client, namespace 생성
        WBEMClient client = CIMClientManager.getInstance().getClient(server);

        try
        {
            List<CIMInstance> instanceList = CIMClientManager.getInstance().getInstanceList(client, server.getNamespace(), className);
            if (isFileWrite)
            {
                CIMClientManager.getInstance().printWriteInstances(instanceList);
            }
            else
            {
                CIMClientManager.getInstance().printInstances(instanceList);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void printClassMap(SmisServer server)
    {
        LOG.info("[2/3][Start] : Print Class Map To Text");
        
        // 서버의 url, 로그인 정보를 바탕으로 client, namespace 생성
        WBEMClient client = CIMClientManager.getInstance().getClient(server);

        try
        {
            // 네임스페이스 하위의 모든 클래스 가져오기
            List<CIMClass> classList = CIMClientManager.getInstance().enumerateClasses(client, server.getNamespace());

            // 클래스맵 생성 및 출력
            Map<String, Object> classMap = ClassMapUtils.createClassMap(classList);
            if (isFileWrite)
            {
                CIMClientManager.getInstance().printWriteClassMap(classMap, vendorKey);
            }
            else
            {
                CIMClientManager.getInstance().printClassMap(classMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        LOG.info("[2/3][End] : Print Class Map To Text");
    }

    @SuppressWarnings("unused")
    private static void saveCimInstances(SmisServer server)
    {
        LOG.info("[3/3][Start] : Print Instance To Binary");
        
        // 서버의 url, 로그인 정보를 바탕으로 client, namespace 생성
        WBEMClient client = CIMClientManager.getInstance().getClient(server);

        Map<String, List<CIMInstance>> instanceMap = new HashMap<String, List<CIMInstance>>();

        String targetVendor = PropertiesManager.getInstance().getProperty("targetVendorClasses", "");
        String[] smisClasses = PropertiesManager.getInstance().getProperty(targetVendor, "").replace(" ", "").split(",");

        List<String> targetClassList = Arrays.asList(smisClasses);
        if (targetClassList.isEmpty() || targetClassList.get(0).equals(""))
        {
            targetClassList = CIMClientManager.getInstance().enumerateClasses(client, server.getNamespace()).stream().map(v -> v.getName()).collect(Collectors.toList());
        }

        for (String className : targetClassList)
        {
            try
            {
                List<CIMInstance> instanceList = CIMClientManager.getInstance().getInstanceList(client, server.getNamespace(), className);

                instanceMap.put(className, instanceList);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        FileUtils.saveAsFile(instanceMap, savePath + vendorKey);
        LOG.info("[3/3][End] : Print Instance To Binary");
    }

    private static void closeServers()
    {
        WBEMClient client = CIMClientManager.getInstance().getClient();
        if (client != null)
        {
            client.close();
        }
    }

    private void notUsedMethod(Object obj)
    {
        int a = 1;
        Integer b = null;
        boolean c = obj.equals("");
    }
}
