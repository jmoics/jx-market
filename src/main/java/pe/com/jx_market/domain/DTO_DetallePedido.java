package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_DetallePedido implements Serializable
{
    private Integer pedido;
    private Integer product;
    private Integer cantidad;

    public Integer getPedido()
    {
        return pedido;
    }
    public void setPedido(final Integer pedido)
    {
        this.pedido = pedido;
    }
    public Integer getProduct()
    {
        return product;
    }
    public void setProduct(final Integer product)
    {
        this.product = product;
    }
    public Integer getCantidad()
    {
        return cantidad;
    }
    public void setCantidad(final Integer cantidad)
    {
        this.cantidad = cantidad;
    }
}
