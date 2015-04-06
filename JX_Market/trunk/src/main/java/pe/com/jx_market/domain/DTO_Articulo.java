/**
 *
 */
package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author George
 *
 */
public class DTO_Articulo implements Serializable
{
    private Integer id;
    private Integer empresa;
    private List<DTO_Categoria> categories;
    private String productName;
    private String productDescription;
    //private String marca;
    //private BigDecimal precio;
    //private Integer stock;
    private Boolean activo;
    private byte[] imagen;
    private String nomimg;


    public String getNomimg()
    {
        return nomimg;
    }
    public void setNomimg(final String nomImg)
    {
        this.nomimg = nomImg;
    }
    public byte[] getImagen()
    {
        return imagen;
    }
    public void setImagen(final byte[] imagen)
    {
        this.imagen = imagen;
    }
    public Integer getId()
    {
        return id;
    }
    public void setId(final Integer id)
    {
        this.id = id;
    }
    public Integer getEmpresa()
    {
        return empresa;
    }
    public void setEmpresa(final Integer empresa)
    {
        this.empresa = empresa;
    }
    public List<DTO_Categoria> getCategories()
    {
        return categories;
    }
    public void setCategories(final List<DTO_Categoria> categories)
    {
        this.categories = categories;
    }
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(final String productName)
    {
        this.productName = productName;
    }
    public String getProductDescription()
    {
        return productDescription;
    }
    public void setProductDescription(final String productDescription)
    {
        this.productDescription = productDescription;
    }
    /*public String getMarca()
    {
        return marca;
    }
    public void setMarca(String marca)
    {
        this.marca = marca;
    }
    public BigDecimal getPrecio()
    {
        return precio;
    }
    public void setPrecio(BigDecimal precio)
    {
        this.precio = precio;
    }
    public Integer getStock()
    {
        return stock;
    }
    public void setStock(Integer stock)
    {
        this.stock = stock;
    }*/
    public Boolean getActivo()
    {
        return activo;
    }
    public void setActivo(final Boolean activo)
    {
        this.activo = activo;
    }
}
