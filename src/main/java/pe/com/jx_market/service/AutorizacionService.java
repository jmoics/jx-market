package pe.com.jx_market.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.persistence.PerfilModuloMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * Servicio de Autorizacion de recursos por perfil
 *
 * @author americati
 *
 */
@Service
public class AutorizacionService<T>
    implements BusinessService<T>
{
    @Autowired
    private PerfilModuloMapper perfilModuloMapper;

    /**
     * El ServiceInput contendrá un objeto DTO_Contacto y un objeto con un recurso
     * especifico o varios, devolviendo en el ServiceOutput OK si tiene
     * autorizacion para acceder a ese recurso y AUTH_ERROR si no la tiene.
     *
     * @param Objeto estándar de entrada
     * @return Objeto estándar de salida
     */
    @Override
    @Transactional
    public ServiceOutput<T> execute(final ServiceInput<T> input)
    {
        final ServiceOutput<T> output = new ServiceOutput<T>();
        final DTO_Empleado empleado = (DTO_Empleado) input.getMapa().get("empleado");
        final DTO_Perfil perfil = new DTO_Perfil();
        perfil.setCodigo(empleado.getPerfil());
        String[] modulos;
        if (input.getMapa().containsKey("modulo")) {
            final String modulo = (String) input.getMapa().get("modulo");
            modulos = new String[1];
            modulos[0] = modulo;
        } else {
            modulos = (String[]) input.getMapa().get("modulo-array");
        }
        final Set<String> modulosDelPerfil = perfilModuloMapper.getModuloStringPorPerfil(perfil);
        // debemos validar que todos los recursos solicitados estan en el array
        for (int z = 0; z < modulos.length; z++) {
            if (!modulosDelPerfil.contains(modulos[z])) {
                output.setErrorCode(Constantes.AUTH_ERROR);
                return output;
            }
        }
        output.setErrorCode(Constantes.OK);
        return output;
    }

    public PerfilModuloMapper getDao()
    {
        return perfilModuloMapper;
    }

    public void setDao(final PerfilModuloMapper perfilModuloMapper)
    {
        this.perfilModuloMapper = perfilModuloMapper;
    }

}