package com.watchtek.watchall.storage.smis.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.cim.CIMClass;

public class ClassMapUtils
{
    /**
     * 클래스 리스트를 구조화하여 Map으로 만든다.
     *
     * @param classList
     * @return
     * @author G1
     * @create-date : 2020. 4. 29.
     */
    public static Map<String, Object> createClassMap(List<CIMClass> classList)
    {
        Set<CIMClass> classSet = new HashSet<CIMClass>(classList);

        Map<String, Object> rootMap = new HashMap<String, Object>();

        Set<String> doneClassSet = new HashSet<String>();
        int prevSize = -1;
        int stopCnt = 0;

        while (classSet.size() > 0)
        {
            if (prevSize == classSet.size())
            {
                // 무한루프 방지. 3번 연속으로 총 개수 변화가 없을 시 메소드 종료
                stopCnt++;
                System.out.println("stop creating classMap... stopCnt : " + stopCnt);
                if (stopCnt > 2)
                {
                    break;
                }
            }
            else
            {
                stopCnt = 0;
            }

            prevSize = classSet.size();

            for (CIMClass cimClass : new HashSet<CIMClass>(classSet))
            {
                String className = cimClass.getName();
                String superClassName = cimClass.getSuperClassName();
                try
                {
                    if (superClassName == null || superClassName.equalsIgnoreCase("null"))
                    {
                        // 부모 클래스가 없으면 rootMap의 최상단에 넣고, doneClassSet에 추가함
                        rootMap.put(className, new HashMap<String, Object>());
                        classSet.remove(cimClass);
                        doneClassSet.add(cimClass.getName());
                    }
                    else
                    {
                        // 부모 클래스가 rootMap에 들어있는지 찾아봄
                        if (doneClassSet.contains(superClassName))
                        {
                            // 1.들어있으면 그 밑에 넣음
                            Map<String, Object> subMap = getSubMap(rootMap, superClassName);
                            subMap.put(className, new HashMap<String, Object>());
                            classSet.remove(cimClass);
                            doneClassSet.add(cimClass.getName());
                        }
                        else
                        {
                            // 2.안들어있으면 패스
                            continue;
                        }

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        return rootMap;
    }

    /**
     * className이랑 일치하는 entry가 있으면, value 반환 <br>
     * 없으면 null 반환
     *
     * @param map
     * @param className
     * @return
     * @author G1
     * @create-date : 2020. 4. 29.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getSubMap(Map<String, Object> map, String className)
    {
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            if (className.equalsIgnoreCase(entry.getKey()))
            {
                return (Map<String, Object>) entry.getValue();
            }
            else
            {
                Map<String, Object> subMap = getSubMap((Map<String, Object>) entry.getValue(), className);
                if (subMap != null)
                {
                    return subMap;
                }
                else
                {
                    continue;
                }
            }
        }

        return null;
    }

    /**
     * tap으로 구분하여 클래스 맵 스트링 변환
     *
     * @param classMap
     * @return
     * @author G1
     * @create-date : 2020. 5. 6.
     */
    public static String toString(Map<String, Object> classMap)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(toString(classMap, 0));

        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static String toString(Map<String, Object> classMap, int depth)
    {
        StringBuilder sb = new StringBuilder();

        if (!classMap.isEmpty())
        {
            for (Map.Entry<String, Object> entry : classMap.entrySet())
            {
                sb.append("\n");
                sb.append(createTap(entry.getKey(), depth));
                sb.append(toString((Map<String, Object>) entry.getValue(), depth + 1));
            }
        }

        return sb.toString();
    }

    public static String createTap(String className, int depth)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++)
        {
            sb.append("\t");
        }
        sb.append(className);

        return sb.toString();
    }
}
