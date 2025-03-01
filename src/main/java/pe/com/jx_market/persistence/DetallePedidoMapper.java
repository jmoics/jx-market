/**
 *
 */
package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_DetallePedido;

/**
 * @author George
 *
 */
public interface DetallePedidoMapper
{
    public List<DTO_DetallePedido> getDetallePedidos (DTO_DetallePedido ped);
    public DTO_DetallePedido getDetallePedidoXCodigo (DTO_DetallePedido ped);
    public boolean insertDetallePedido (DTO_DetallePedido ped);
    public boolean updateDetallePedido (DTO_DetallePedido ped);
    public boolean deleteDetallePedido (DTO_DetallePedido ped);
}
