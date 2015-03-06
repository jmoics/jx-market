package pe.com.jx_market.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.persistence.ClienteMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * Servicio de Administracion de Contactos
 *
 * @author jorge
 *
 */
@Service
public class ClienteService 
    implements BusinessService 
{
    static Log logger = LogFactory.getLog(ClienteService.class);
    @Autowired
    private ClienteMapper clienteMapper;
    @Autowired
    private BusinessService usuarioService;

    /**
     * El DTO_Input contendrá como verbo un String: para realizar una consulta
     * se usará el verbo "list" y un string con el codigo de la institucion a
     * la que pertenece el contacto, para ingresar o actualizar datos se usará
     * el verbo "register" conteniendo además un objeto DTO_Contacto con los
     * datos nuevos a ingresar, y para eliminar datos se usará el verbo
     * "delete" adeḿas de contener un string con el codigo del contacto a
     * eliminar. El DTO_Output tiene codigo de error OK; y si el verbo es "list"
     * contendra una lista de objetos DTO_Contacto con todos los campos leidos
     * de la Base de Datos.
     *
     * @param Objeto
     *            estandar de entrada
     * @return Objeto estandar de salida
     */
    @Override
    @Transactional
    public DTO_Output execute (final DTO_Input input) {

        final DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            final DTO_Cliente cliente = (DTO_Cliente) input.getObject();
            output.setLista(clienteMapper.getClientes(cliente));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            final DTO_Cliente cliente = (DTO_Cliente) input.getObject();
            List<DTO_Cliente> cliLstTmp = clienteMapper.getClientes(cliente);
            if (cliLstTmp != null && !cliLstTmp.isEmpty()) {
                output.setObject(cliLstTmp.get(0));
                output.setErrorCode(Constantes.OK);
            }
            return output;
        }else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            final Map map = input.getMapa();
            final DTO_Cliente cliente = (DTO_Cliente) map.get("cliente");
            DTO_Usuario usuario = (DTO_Usuario) map.get("usuario");
            final DTO_Input inp = new DTO_Input(usuario);
            if(usuario != null) {
                if (usuario.getCodigo() == null) {
                    inp.setVerbo(Constantes.V_REGISTER);
                    final DTO_Output out = usuarioService.execute(inp);
                    if (out.getErrorCode() == Constantes.OK) {
                        usuario = getUsuario(usuario);
                        cliente.setUsuario(usuario.getCodigo());
                    }
                } else {
                    usuario = getUsuario(usuario);
                    final Map<String, String> map2 = new HashMap<String, String>();
                    map2.put("nonPass", "nonPass");
                    map2.put("oldPass", null);
                    inp.setMapa(map2);
                    inp.setVerbo("chgpass");
                    final DTO_Output out = usuarioService.execute(inp);
                    if (out.getErrorCode() == Constantes.OK) {
                        logger.info("El password fue cambiado correctamente");
                    } else {
                        logger.error("Error al cambiar el password");
                        return output;
                    }
                }
            }
            final DTO_Cliente clTmp = (DTO_Cliente) clienteMapper.getClientes(cliente);
            if (clTmp == null) {
                clienteMapper.insertCliente(cliente);
            } else {
                clienteMapper.updateCliente(cliente);
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            clienteMapper.eliminaCliente((DTO_Cliente) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    private DTO_Usuario getUsuario(final DTO_Usuario user) {
        final DTO_Input inp = new DTO_Input(user);
        inp.setVerbo(Constantes.V_GET);
        final DTO_Output out = usuarioService.execute(inp);
        if(out.getErrorCode() == Constantes.OK) {
            return (DTO_Usuario) out.getObject();
        } else {
            return null;
        }
    }

    public ClienteMapper getDao () {
        return clienteMapper;
    }

    public void setDao (final ClienteMapper clienteMapper) {
        this.clienteMapper = clienteMapper;
    }

    public BusinessService getUsuarioService()
    {
        return usuarioService;
    }

    public void setUsuarioService(final BusinessService usuarioService)
    {
        this.usuarioService = usuarioService;
    }
}
