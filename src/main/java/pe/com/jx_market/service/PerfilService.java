package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.persistence.PerfilMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@Service
public class PerfilService
    implements BusinessService
{
    @Autowired
    private PerfilMapper perfilMapper;

    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input)
    {
        final ServiceOutput output = new ServiceOutput();
        if (Constantes.V_LIST.equals(input.getAccion())) {
            output.setLista(perfilMapper.getPerfiles((DTO_Perfil) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            DTO_Perfil perfTmp = perfilMapper.getPerfilXCodigo((DTO_Perfil) input.getObject());
            if (perfTmp == null) {
                final Integer cod = perfilMapper.insertPerfil((DTO_Perfil) input.getObject());
                output.setObject(cod);
            } else {
                perfilMapper.updatePerfil((DTO_Perfil) input.getObject());
                output.setObject(new Integer(-1));
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAccion())) {
            final DTO_Perfil art = perfilMapper.getPerfilXCodigo((DTO_Perfil) input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getAccion())) {
            perfilMapper.deletePerfil((DTO_Perfil) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public PerfilMapper getDao()
    {
        return perfilMapper;
    }

    public void setDao(final PerfilMapper perfilMapper)
    {
        this.perfilMapper = perfilMapper;
    }

}