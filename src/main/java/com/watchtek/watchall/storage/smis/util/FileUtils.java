package com.watchtek.watchall.storage.smis.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.cim.CIMInstance;
import javax.cim.CIMObjectPath;
import javax.cim.CIMProperty;

public class FileUtils
{
    private final static String pattern = "[instance]";

    public static File[] getFileList(String dirPath)
    {
        File fileList[] = null;

        try
        {
            File dir = new File(dirPath);

            fileList = dir.listFiles(new FilenameFilter()
            {
                @Override
                public boolean accept(File dir, String name)
                {
                    return name.startsWith(pattern);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fileList;
    }

    public static Set<String> getCIMTypes(File file)
    {
        Set<String> result = new HashSet<String>();
        BufferedReader bReader = null;
        try
        {
            String s;
            bReader = new BufferedReader(new FileReader(file));

            while ((s = bReader.readLine()) != null)
            {
                if (!s.matches("^(//|Instance|}|\\s).*"))
                {
                    result.add(s.substring(0, s.indexOf(" ")));
                }
            }

        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    public static List<CIMInstance> getCIMInstances(File file)
    {
        List<CIMInstance> result = new ArrayList<CIMInstance>();

        BufferedReader bReader = null;
        String cimObjectPathStr = "// path=";
        CIMProperty<?>[] cimProperties = null;
        CIMObjectPath cimObjectPath = null;
        List<CIMProperty> cimPropsList = new ArrayList<CIMProperty>();
        
        try
        {
            String s;
            bReader = new BufferedReader(new FileReader(file));

            while ((s = bReader.readLine()) != null)
            {
                if (s.startsWith("/"))
                {
                    cimObjectPathStr = s.replace("// path=", "");
                    cimObjectPath = CIMParser.getObjectPath(cimObjectPathStr);
                    cimPropsList = new ArrayList<CIMProperty>();
                }
                else if (!(s.startsWith("Instance") || s.startsWith("}")))
                {
                    CIMProperty<?> cimProperty = CIMParser.getCIMProperty(s);
                    cimPropsList.add(cimProperty);
                }
                
                if (s.startsWith("}"))
                {
                    cimProperties = new CIMProperty[cimPropsList.size()];
                    cimProperties = cimPropsList.toArray(cimProperties);
                    CIMInstance cimInstance = new CIMInstance(cimObjectPath, cimProperties);
                    result.add(cimInstance);
                }
            }

        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return result;
    }

    /**
     * Object를 파일로 저장
     *
     * @param data
     * @author G1
     * @create-date : 2020. 4. 29.
     */
    public static void saveAsFile(Object data, String path)
    {
        ObjectOutputStream objOut = null;
        FileOutputStream fileOut = null;
        try
        {
            fileOut = new FileOutputStream(path);
            objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeQuietly(objOut);
            closeQuietly(fileOut);
        }
    }

    /**
     * 파일을 Object로 로드
     *
     * @return
     * @author G1
     * @create-date : 2020. 4. 29.
     */
    public static Object loadFromFile(String path)
    {
        Object data = null;

        ObjectInputStream objInputStream = null;

        FileInputStream inputStream = null;

        try
        {

            inputStream = new FileInputStream(path);

            objInputStream = new ObjectInputStream(inputStream);

            data = objInputStream.readObject();

            objInputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeQuietly(objInputStream);
            closeQuietly(inputStream);
        }

        return data;
    }

    public static void closeQuietly(Closeable... closeables)
    {
        for (Closeable c : closeables)
        {
            if (c != null)
            {
                try
                {
                    c.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
