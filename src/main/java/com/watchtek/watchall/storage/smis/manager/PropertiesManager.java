package com.watchtek.watchall.storage.smis.manager;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;

public class PropertiesManager
{
    protected Log LOG = LogFactory.getLog(PropertiesManager.class);

    private static PropertiesManager instance = new PropertiesManager();

    public static PropertiesManager getInstance()
    {
        return instance;
    }

    private PropertiesManager()
    {
        if (properties == null)
        {
            properties = loadPropertiesFiles();
        }
    }

    private Properties loadPropertiesFiles()
    {
        Properties properties = new Properties();
        
        try
        {
            File dir = Resources.getResourceAsFile("./properties");
            FileFilter fileFilter = new WildcardFileFilter("*.properties");
            File[] files = dir.listFiles(fileFilter);
            
            for (File file : Arrays.asList(files))
            {
                Properties temp = Resources.getResourceAsProperties("./properties/" + file.getName());

                properties.putAll(temp);
            }

        }
        catch (IOException e)
        {
            LOG.error(e, e);
        }
        
        return properties;
    }

    private Properties properties;

    public Properties getProperties()
    {
        return properties;
    }

    public void setProperties(Properties properties)
    {
        this.properties = properties;
    }

    /**
     * key에 해당하는 value를 가져온다.<br>
     * Default값은 null
     *
     * @param key
     * @return
     * @author parkjongwon
     * @create-date : 2017. 1. 20.
     */
    public <T> T getProperty(String key)
    {
        return getProperty(key, null);
    }

    /**
     * default value로 들어온 Type을 기준으로<br>
     * Property 값으로 자주 사용하는 int, String, boolean형태에 대해<br>
     * value를 자동 형변환하여 반환한다.<br>
     * <br>
     * 그 외의 형태는 Object로 반환
     * 
     * @param key
     * @param defaultValue(String, int, Integer, String, bool, Boolean)
     * @return String -> String<br>
     *         int, Integer -> Integer<br>
     *         boolean, Boolean -> Boolean<br>
     *         그외 -> Object
     * @author parkjongwon
     * @create-date : 2017. 1. 18.
     */
    public <T> T getProperty(String key, T defaultValue)
    {
        Object propertyValue = getProperties().get(key);

        return castValue(key, propertyValue, defaultValue);
    }

    @SuppressWarnings("unchecked")
    protected <T> T castValue(String key, Object value, T defaultValue)
    {
        try
        {
            Object val = value;
            if (val == null || "".equals(val.toString()))
            {
                return defaultValue;
            }
            if (val instanceof String)
            {
                Object result = defaultValue instanceof Boolean ? Boolean.parseBoolean(((String) val).trim()) : (defaultValue instanceof Integer ? Integer.parseInt(((String) val).trim()) : ((String) val).trim());

                return (T) result;
            }
            return (T) val;
        }
        catch (ClassCastException e)
        {
            LOG.debug(e, e);
        }
        return defaultValue;
    }

}
