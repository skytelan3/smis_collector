package com.watchtek.watchall.storage.smis.vo;

public class SmisServer
{
    private String addr;
    private String id;
    private String password;
    private String namespace;

    public SmisServer(String addr, String id, String password, String namespace)
    {
        this.addr = addr;
        this.id = id;
        this.password = password;
        this.namespace = namespace;
    }

    public String getAddr()
    {
        return addr;
    }

    public void setAddr(String addr)
    {
        this.addr = addr;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNamespace()
    {
        return namespace;
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }
}
