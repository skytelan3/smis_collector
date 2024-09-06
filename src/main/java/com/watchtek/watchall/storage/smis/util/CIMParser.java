package com.watchtek.watchall.storage.smis.util;

import javax.cim.CIMDataType;
import javax.cim.CIMDateTimeAbsolute;
import javax.cim.CIMDateTimeInterval;
import javax.cim.CIMObjectPath;
import javax.cim.CIMProperty;
import javax.cim.UnsignedInteger16;
import javax.cim.UnsignedInteger32;
import javax.cim.UnsignedInteger64;
import javax.cim.UnsignedInteger8;

import org.apache.commons.lang.BooleanUtils;

public class CIMParser
{
    private CIMParser()
    {

    }

    @SuppressWarnings({ "rawtypes" })
    public static CIMProperty<?> getCIMProperty(String line)
    {
        int firstBlank = line.indexOf(" ");
        int eqIdx = line.indexOf("=");
        String type = line.substring(0, firstBlank);
        String name = line.substring(firstBlank+1, eqIdx - 1);
        String value = line.substring(eqIdx + 2, line.length() - 1);
        CIMProperty cimProperty = getCIMProperty(name, value, type, value.startsWith("["));

        return cimProperty;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static CIMProperty getCIMProperty(String name, String value, String type, boolean isArray)
    {
        CIMDataType cimDataType = CIMDataType.STRING_T;

        Object cimValue = value;
        String[] arr = null;

        if (isArray)
        {
            String csv = value.replaceAll("\\[|\\]", "");
            arr = csv.split(", ");
        }
        switch (type)
        {
            case "string":
                if (isArray)
                {
                    cimDataType = CIMDataType.STRING_ARRAY_T;
                    cimValue = arr;
                }
                break;
            case "datetime":
                if (isArray)
                {
                    cimDataType = CIMDataType.DATETIME_ARRAY_T;
                    boolean isInterval = true;

                    CIMDateTimeInterval temp1[] = new CIMDateTimeInterval[arr.length];
                    CIMDateTimeAbsolute temp2[] = new CIMDateTimeAbsolute[arr.length];
                    if (arr.length != 0)
                    {
                        for (int i = 0; i < arr.length; i++)
                        {
                            if (arr[i].contains(":"))
                            {
                                temp1[i] = new CIMDateTimeInterval(arr[i]);
                            }
                            else
                            {
                                temp2[i] = new CIMDateTimeAbsolute(arr[i]);
                                isInterval = false;
                            }
                        }
                    }
                    if (isInterval)
                    {
                        cimValue = temp1;
                    }
                    else
                    {
                        cimValue = temp2;
                    }
                }
                else
                {
                    cimDataType = CIMDataType.DATETIME_T;
                    if (value.contains(":"))
                    {
                        cimValue = new CIMDateTimeInterval(value);
                    }
                    else
                    {
                        cimValue = new CIMDateTimeAbsolute(value);
                    }
                }
                break;
            case "boolean":
                if (isArray)
                {
                    cimDataType = CIMDataType.BOOLEAN_ARRAY_T;
                    Boolean temp[] = new Boolean[arr.length];
                    if (arr.length != 0)
                    {
                        for (int i = 0; i < arr.length; i++)
                        {
                            if (arr[i] != null && arr[i] != "")
                            {
                                temp[i] = new Boolean(arr[i]);
                            }
                        }
                    }
                    cimValue = temp;
                }
                else
                {
                    cimDataType = CIMDataType.BOOLEAN_T;
                    cimValue = BooleanUtils.toBooleanObject(value);
                }
                break;
            case "uint8":
                if (isArray)
                {
                    cimDataType = CIMDataType.UINT8_ARRAY_T;
                    UnsignedInteger8 temp[] = new UnsignedInteger8[arr.length];
                    if (arr.length != 0)
                    {
                        for (int i = 0; i < arr.length; i++)
                        {
                            if (arr[i] != null && !"".equals(arr[i]))
                            {
                                temp[i] = new UnsignedInteger8(arr[i]);
                            }
                        }
                    }
                    cimValue = temp;
                }
                else
                {
                    cimDataType = CIMDataType.UINT8_T;
                    cimValue = new UnsignedInteger8(value);
                }
                break;
            case "uint16":
                if (isArray)
                {
                    cimDataType = CIMDataType.UINT16_ARRAY_T;
                    UnsignedInteger16 temp[] = new UnsignedInteger16[arr.length];
                    if (arr.length != 0)
                    {
                        for (int i = 0; i < arr.length; i++)
                        {
                            if (arr[i] != null && arr[i] != "" && arr[i].length() != 0)
                            {
                                temp[i] = new UnsignedInteger16(arr[i]);
                            }
                        }
                    }
                    cimValue = temp;
                }
                else
                {
                    cimDataType = CIMDataType.UINT16_T;
                    cimValue = new UnsignedInteger16(value);
                }
                break;
            case "uint32":
                if (isArray)
                {
                    cimDataType = CIMDataType.UINT32_ARRAY_T;
                    UnsignedInteger32 temp[] = new UnsignedInteger32[arr.length];
                    if (arr.length != 0)
                    {
                        for (int i = 0; i < arr.length; i++)
                        {
                            if (arr[i] != null && arr[i] != "")
                            {
                                temp[i] = new UnsignedInteger32(arr[i]);
                            }
                        }
                    }
                    cimValue = temp;
                }
                else
                {
                    cimDataType = CIMDataType.UINT32_T;
                    cimValue = new UnsignedInteger32(value);
                }
                break;
            case "uint64":
                if (isArray)
                {
                    cimDataType = CIMDataType.UINT64_ARRAY_T;
                    UnsignedInteger64 temp[] = new UnsignedInteger64[arr.length];
                    if (arr.length != 0)
                    {
                        for (int i = 0; i < arr.length; i++)
                        {
                            if (arr[i] != null && arr[i] != "")
                            {
                                temp[i] = new UnsignedInteger64(arr[i]);
                            }
                        }
                    }
                    cimValue = temp;
                }
                else
                {
                    cimDataType = CIMDataType.UINT64_T;
                    cimValue = new UnsignedInteger64(value);
                }
                break;
            default:
                break;
        }

        return new CIMProperty(name, cimDataType, cimValue);
    }

    public static CIMObjectPath getObjectPath(String path)
    {
        String namespace = path.substring(0, path.indexOf(":"));
        String name = path.substring(path.indexOf(":") + 1, path.indexOf("."));
        return new CIMObjectPath(null, null, null, namespace, name, null);
    }
}
