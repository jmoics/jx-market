package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Area
    implements Serializable
{
    private Integer id;
    private Integer companyId;
    private String name;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(final Integer _companyId)
    {
        this.companyId = _companyId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }
}
