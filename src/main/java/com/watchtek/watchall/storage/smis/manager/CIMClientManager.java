package com.watchtek.watchall.storage.smis.manager;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.cim.CIMClass;
import javax.cim.CIMInstance;
import javax.cim.CIMObjectPath;
import javax.security.auth.Subject;
import javax.wbem.CloseableIterator;
import javax.wbem.WBEMException;
import javax.wbem.client.PasswordCredential;
import javax.wbem.client.UserPrincipal;
import javax.wbem.client.WBEMClient;
import javax.wbem.client.WBEMClientConstants;
import javax.wbem.client.WBEMClientFactory;

import com.watchtek.watchall.storage.smis.define.StorageCIMDefine;
import com.watchtek.watchall.storage.smis.util.ClassMapUtils;
import com.watchtek.watchall.storage.smis.util.MofUtils;
import com.watchtek.watchall.storage.smis.vo.SmisServer;

public class CIMClientManager
{
    static String savePath = PropertiesManager.getInstance().getProperty("default.save.path", "./src/main/resources/instances/");
    static String vendorKey = PropertiesManager.getInstance().getProperty("smis.server.vendorkey", "");

    private static class Singleton
    {
        public static final CIMClientManager INSTANCE = new CIMClientManager();
    }

    public static CIMClientManager getInstance()
    {
        return Singleton.INSTANCE;
    }

    private static WBEMClient client = null;

    public CIMClientManager()
    {

    }

    public WBEMClient getClient(SmisServer server)
    {
        try
        {
            client = connect(new URL(server.getAddr()), server.getId(), server.getPassword());
            return client;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public WBEMClient getClient()
    {
        return client;
    }

    // CIM 클라이언트 생성
    public WBEMClient connect(final URL wbemUrl, final String user, final String password) throws WBEMException
    {
        WBEMClient client = WBEMClientFactory.getClient(WBEMClientConstants.PROTOCOL_CIMXML);
        final CIMObjectPath path = new CIMObjectPath(wbemUrl.getProtocol(), wbemUrl.getHost(), String.valueOf(wbemUrl.getPort()), null, null, null);
        final Subject subject = new Subject();
        subject.getPrincipals().add(new UserPrincipal(user));
        subject.getPrivateCredentials().add(new PasswordCredential(password));
        client.initialize(path, subject, new Locale[] { Locale.KOREA });
        return client;
    }

    // 대상 네임스페이스의 모든 클래스 가져오기
    public List<CIMClass> enumerateClasses(WBEMClient client, String namespace)
    {
        List<CIMClass> classList = new ArrayList<CIMClass>();

        CloseableIterator<CIMClass> classIter;
        try
        {
            classIter = client.enumerateClasses(new CIMObjectPath(null, null, null, namespace, null, null), true, false, false, false);
            while (classIter.hasNext())
            {
                classList.add(classIter.next());
            }
        }
        catch (WBEMException e)
        {
            e.printStackTrace();
        }

        return classList;
    }

    // 대상 클래스의 인스턴스 리스트 가져오기
    public List<CIMInstance> getInstanceList(WBEMClient client, String namespace, String className)
    {
        List<CIMInstance> instanceList = new ArrayList<CIMInstance>();
        final CIMObjectPath path = new CIMObjectPath(null, null, null, namespace, className, null);
        try
        {
            CloseableIterator<CIMInstance> iter = client.enumerateInstances(path, true, false, true, null);
            while (iter.hasNext())
            {
                CIMInstance instance = iter.next();
                if (className.equals(instance.getClassName()))
                {
                    instanceList.add(instance);
                }
            }
        }
        catch (WBEMException e)
        {
            e.printStackTrace();
        }

        return instanceList;
    }

    // 대상 클래스와 연관된 클래스 리스트 가져오기
    public List<CIMClass> getAssociatedClassList(WBEMClient client, String namespace, String className)
    {
        List<CIMClass> cimClassList = new ArrayList<CIMClass>();
        final CIMObjectPath path = new CIMObjectPath(null, null, null, namespace, className, null);
        try
        {
            CloseableIterator<CIMClass> iter = client.associatorClasses(path, className, null, null, null, false, false, null);
            while (iter.hasNext())
            {
                cimClassList.add(iter.next());
            }
        }
        catch (WBEMException e)
        {
            e.printStackTrace();
        }

        return cimClassList;
    }

    // 대상 클래스와 연관된 인스턴스 리스트 가져오기
    public List<CIMInstance> getAssociatedInstanceList(WBEMClient client, String namespace, String className)
    {
        List<CIMInstance> instanceList = new ArrayList<CIMInstance>();
        final CIMObjectPath path = new CIMObjectPath(null, null, null, namespace, className, null);
        try
        {
            CloseableIterator<CIMClass> iter = client.associatorClasses(path, className, null, null, null, false, false, null);
            while (iter.hasNext())
            {
                instanceList.addAll(getInstanceList(client, namespace, iter.next().getName()));
            }
        }
        catch (WBEMException e)
        {
            e.printStackTrace();
        }

        return instanceList;
    }

    // 대상 클래스를 참조하는 클래스 리스트 가져오기
    public List<CIMClass> getReferencedClassList(WBEMClient client, String namespace, String className)
    {
        List<CIMClass> cimClassList = new ArrayList<CIMClass>();
        final CIMObjectPath path = new CIMObjectPath(null, null, null, namespace, className, null);
        try
        {
            CloseableIterator<CIMClass> iter = client.referenceClasses(path, null, null, false, false, null);
            while (iter.hasNext())
            {
                cimClassList.add(iter.next());
            }
        }
        catch (WBEMException e)
        {
            e.printStackTrace();
        }

        return cimClassList;
    }

    // 대상 클래스를 참조하는 인스턴스 리스트 가져오기
    public List<CIMInstance> getReferencedInstanceList(WBEMClient client, String namespace, String className)
    {
        List<CIMInstance> instanceList = new ArrayList<CIMInstance>();
        final CIMObjectPath path = new CIMObjectPath(null, null, null, namespace, className, null);
        try
        {
            CloseableIterator<CIMClass> iter = client.referenceClasses(path, null, null, false, false, null);
            while (iter.hasNext())
            {
                instanceList.addAll(getInstanceList(client, namespace, iter.next().getName()));
            }
        }
        catch (WBEMException e)
        {
            e.printStackTrace();
        }

        return instanceList;
    }

    // 클래스 리스트 출력
    public void printClasses(List<CIMClass> cimClassList)
    {
        for (CIMClass cimClass : cimClassList)
        {
            System.out.println(cimClass);
        }
    }

    // 클래스 리스트 파일 출력
    public void printWriteClasses(List<CIMClass> cimClassList)
    {
        for (CIMClass cimClass : cimClassList)
        {
            File file = new File(savePath + "[Class]" + cimClass.getName() + ".txt");

            try
            {
                FileWriter fw = new FileWriter(file, true);
                fw.write(cimClass.toString());
                fw.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    // 인스턴스 리스트 출력
    public void printInstances(List<CIMInstance> instanceList)
    {
        for (CIMInstance instance : instanceList)
        {
            System.out.println(MofUtils.toMof(instance));
        }
    }

    // 인스턴스 리스트 파일 출력
    public void printWriteInstances(List<CIMInstance> instanceList)
    {
        for (CIMInstance instance : instanceList)
        {
            File file = new File(savePath + "[instance]" + instance.getClassName() + ".txt");

            try
            {
                FileWriter fw = new FileWriter(file, true);
                fw.write(MofUtils.toMof(instance));
                fw.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    // 클래스맵 출력
    public void printClassMap(Map<String, Object> classMap)
    {
        System.out.println(ClassMapUtils.toString(classMap));
    }

    // 클래스맵 파일 출력
    public void printWriteClassMap(Map<String, Object> classMap, String serverName)
    {
        File file = new File(savePath + "[ClassMap]" + serverName + ".txt");

        try
        {
            FileWriter fw = new FileWriter(file, true);
            fw.write(ClassMapUtils.toString(classMap));
            fw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // 이름으로 인스턴스 찾기
    public CIMInstance selectInstanceByElementName(String elementName, List<CIMInstance> instanceList)
    {
        List<CIMInstance> targetInstanceList = new ArrayList<CIMInstance>();

        for (CIMInstance cimInstance : instanceList)
        {
            if (elementName.equals(cimInstance.getPropertyValue(StorageCIMDefine.ELEMENT_NAME).toString()))
            {
                targetInstanceList.add(cimInstance);
            }
        }

        if (targetInstanceList.isEmpty())
        {
            System.out.println("[getInstanceByElementName : " + elementName + "] There's no instance named \"" + elementName + "\"");
            return null;
        }
        else
        {
            System.out.println("[getInstanceByElementName : " + elementName + "] Found " + targetInstanceList.size() + " instances. Return first one.");
            return targetInstanceList.get(0);
        }
    }

    // 첫번째 인스턴스만 출력
    public void printFirstInstance(List<CIMInstance> instanceList)
    {
        for (CIMInstance instance : instanceList)
        {
            System.out.println(MofUtils.toMof(instance));
            return;
        }
    }

    // 첫번째 인스턴스만 파일 출력
    public void printWriteFirstInstance(List<CIMInstance> instanceList)
    {
        for (CIMInstance instance : instanceList)
        {
            File file = new File(savePath + "[Instance]" + instance.getClassName() + ".txt");

            try
            {
                FileWriter fw = new FileWriter(file, true);
                fw.write(MofUtils.toMof(instance));
                fw.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return;
        }
    }
}
