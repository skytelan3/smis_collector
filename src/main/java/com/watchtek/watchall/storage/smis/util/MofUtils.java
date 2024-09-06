package com.watchtek.watchall.storage.smis.util;

import java.util.Arrays;

import javax.cim.CIMClassProperty;
import javax.cim.CIMDataType;
import javax.cim.CIMInstance;
import javax.cim.CIMObjectPath;
import javax.cim.CIMProperty;
import javax.cim.CIMQualifier;

public class MofUtils
{
    /**
     * toMof
     * 
     * @param pInstance
     * @return String pInstance
     */
    public static String toMof(CIMInstance pInstance)
    {
        final StringBuffer result = new StringBuffer();
        result.append("// path=");
        result.append(toMof(pInstance.getObjectPath()));
        result.append("\n");
        result.append("Instance of ");
        result.append(pInstance.getClassName());
        result.append(" {\n");
        final CIMProperty<?>[] properties = pInstance.getProperties();
        result.append(toMof(properties));
        result.append("}\n");
        return result.toString();
    }

    /**
     * toMof
     * 
     * @param pObjectPath
     * @return String CIMObjectPath
     */
    public static String toMof(CIMObjectPath pObjectPath)
    {
        final StringBuffer result = new StringBuffer();
        if (pObjectPath.getScheme() != null)
        {
            result.append(pObjectPath.getScheme());
            result.append("://");
        }
        if (pObjectPath.getHost() != null)
        {
            result.append(pObjectPath.getHost());
            if (pObjectPath.getPort() != null)
            {
                result.append(":");
                result.append(pObjectPath.getPort());
            }
            result.append("/");
        }
        if (pObjectPath.getNamespace() != null)
        {
            result.append(pObjectPath.getNamespace());
            result.append(":");
        }
        result.append(pObjectPath.getObjectName());
        final CIMProperty<?>[] keys = pObjectPath.getKeys();
        for (int i = 0; i < keys.length; ++i)
        {
            result.append(i == 0 ? "." : ",");
            result.append(keys[i].getName());
            result.append("=\"");
            result.append(keys[i].getValue());
            result.append("\"");
        }
        return result.toString();
    }

    /**
     * toMof
     * 
     * @param pProperties
     * @return String pProperties
     */
    public static String toMof(CIMProperty<?>[] pProperties)
    {
        final StringBuffer result = new StringBuffer();
        for (int i = 0; i < pProperties.length; ++i)
        {
            if (pProperties[i].getValue() != null)
            {
                result.append(toMof(pProperties[i]));
                result.append(";\n");
            }
        }
        return result.toString();
    }

    /**
     * toMof
     * 
     * @param pProperty
     * @return String CIMProperty
     */
    public static String toMof(CIMProperty<?> pProperty)
    {
        final StringBuffer result = new StringBuffer();
        if (pProperty instanceof CIMClassProperty)
        {
            result.append(toMof(((CIMClassProperty<?>) pProperty).getQualifiers()));
        }
        result.append(pProperty.getDataType().toString());
        result.append(" ");
        result.append(pProperty.getName());
        if (pProperty.getValue() != null)
        {
            result.append(" = ");
            if (pProperty.getDataType().isArray())
            {
                result.append(Arrays.asList((Object[]) pProperty.getValue()));
            }
            else
            {
                result.append(String.valueOf(pProperty.getValue()));
            }
        }
        return result.toString();
    }

    /**
     * toMof
     * 
     * @param qualifiers
     * @return String CIMQualifier[]
     */
    public static String toMof(CIMQualifier<?>[] qualifiers)
    {
        final StringBuffer result = new StringBuffer();
        result.append("[");
        for (int i = 0; i < qualifiers.length; ++i)
        {
            final CIMQualifier<?> qualifier = qualifiers[i];
            if (i > 0)
            {
                result.append(",");
            }
            result.append(toMof(qualifier));
        }
        result.append("]\n");
        return result.toString();
    }

    /**
     * toMof
     * 
     * @param pQualifier
     * @return String CIMQualifier
     */
    public static String toMof(CIMQualifier<?> pQualifier)
    {
        final StringBuffer result = new StringBuffer();
        result.append(pQualifier.getName());
        result.append("(");
        if (pQualifier.getDataType().equals(CIMDataType.STRING_T))
        {
            result.append('"');
            String value = (String) pQualifier.getValue();
            value = value.replaceAll("\"", "\\\"");
            result.append(value.length() < 15 ? value : value.substring(0, 15) + "...");
            result.append('"');
        }
        else if (pQualifier.getDataType().isArray())
        {
            result.append(Arrays.asList((Object[]) pQualifier.getValue()));
        }
        else
        {
            result.append(pQualifier.getValue());
        }
        result.append(")");
        return result.toString();
    }
}
