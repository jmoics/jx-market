package pe.com.jx_market.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.jx_market.dao.UsuarioDAO;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * Servicio de Autenticacion de Usuarios.
 * 
 */

public class AuthService
    implements BusinessService
{

    static Log logger = LogFactory.getLog(AuthService.class);
    private UsuarioDAO dao;
    private BusinessService passwordHashService;

    /**
     * El DTO_Input debe contener un objeto de tipo DTO_Login el cual contiene
     * solo los atributos username y password. En caso de autenticacion
     * positiva, el DTO_Output tiene codigo de error OK
     * 
     * @param Objeto estandar de entrada
     * @return Objeto estandar de salida
     */
    @Override
    public DTO_Output execute(final DTO_Input input)
    {
        final DTO_Output output = new DTO_Output();
        final DTO_Usuario suposedUser = (DTO_Usuario) input.getObject();
        final String password = suposedUser.getContrasena();
        suposedUser.setContrasena("erased");
        final DTO_Usuario us = dao.leeUsuario(suposedUser);

        if (us == null) {
            logger.error("No se proporciono usuario valido");
            output.setErrorCode(Constantes.NOT_FOUND);
            return output;
        }
        if (password == null || password.length() == 0) {
            logger.error("No se proporciono password valido");
            output.setErrorCode(Constantes.NOT_FOUND);
            return output;
        }

        if (encriptacion(password).equals(us.getContrasena())) {
            output.setObject(us);
            output.setErrorCode(Constantes.OK);
            return output;
            // por mientras ya que no hay encriptacion....
        } else if (password.equals(us.getContrasena())) {
            output.setObject(us);
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            output.setErrorCode(Constantes.AUTH_ERROR);
            return output;
        }
    }

    private String encriptacion(final String pass)
    {
        String passEncriptada = "";
        DTO_Output output;

        output = passwordHashService.execute(new DTO_Input(pass));
        if (output.getErrorCode() == Constantes.OK) {
            passEncriptada = (String) output.getObject();
        } else {
            throw new RuntimeException("Error en encriptacion hash de password");
        }
        return passEncriptada;
    }

    public UsuarioDAO getDao()
    {
        return dao;
    }

    public void setDao(final UsuarioDAO dao)
    {
        this.dao = dao;
    }

    public BusinessService getPasswordHashService()
    {
        return passwordHashService;
    }

    public void setPasswordHashService(final BusinessService passwordHashService)
    {
        this.passwordHashService = passwordHashService;
    }

}