package pe.com.jx_market.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zul.Textbox;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EACambiarContrasenia
    extends SecuredWindow
{

    static Log logger = LogFactory.getLog(PO_EACambiarContrasenia.class);
    private Textbox txtPassActual, txtPassNew1, txtPassNew2;
    private BusinessService userService, passwordHashService;
    private DTO_Company company;

    @Override
    public void realOnCreate()
    {
        txtPassActual = (Textbox) getFellow("txtPassActual");
        txtPassNew1 = (Textbox) getFellow("txtPassNew1");
        txtPassNew2 = (Textbox) getFellow("txtPassNew2");
        userService = ContextLoader.getService(this, "userService");
        passwordHashService = ContextLoader.getService(this, "passwordHashService");

        company = (DTO_Company) getDesktop().getSession().getAttribute("company");
    }

    public String encriptacion(final String pass)
    {
        String passEncriptada = "";
        ServiceOutput output;

        output = passwordHashService.execute(new ServiceInput(pass));
        if (output.getErrorCode() == Constantes.OK) {
            passEncriptada = (String) output.getObject();
        } else {

        }

        return passEncriptada;
    }

    public void cambiarContrasenia()
    {
        final DTO_User userLog = (DTO_User) getDesktop().getSession().getAttribute("login");
        if (encriptacion(txtPassActual.getValue()).equals(userLog.getPassword())) {
            if (txtPassNew1.getValue().equals(txtPassNew2.getValue())) {
                final ServiceInput input = new ServiceInput();
                final DTO_User user = new DTO_User();
                user.setUsername(userLog.getUsername());
                user.setCompanyId(company.getId());
                user.setPassword(txtPassNew1.getValue());
                input.setObject(user);
                input.setAction("chgpass");
                final ServiceOutput output = userService.execute(input);
                if (output.getErrorCode() == Constantes.OK) {
                    alertaInfo("Contraseña cambiada satisfactoriamente", "se cambio la contraseña satisfactoriamente", null);
                    onLimpiar();
                } else if (output.getErrorCode() == Constantes.BAD_PASS) {
                    alertaInfo("La contraseña nueva debe tener más de 6 caracteres",
                                    "error al cambiar la contraseña, longitud minima no alcanzada", null);
                    onLimpiar();
                } else {
                    alertaError("Error al cambiar la contraseña, consultar a Soporte", "error al cambiar la contraseña", null);
                    onLimpiar();
                }
            } else {
                alertaInfo("Las contraseñas nuevas ingresadas no concuerdan", "error al repetir la nueva contraseña", null);
                onLimpiar();
            }
        } else {
            alertaInfo("La contraseña actual ingresada es incorrecta", "contraseña actual incorrecta", null);
            onLimpiar();
        }
    }

    public void onLimpiar()
    {
        txtPassActual.setValue("");
        txtPassNew1.setValue("");
        txtPassNew2.setValue("");
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, company.getBusinessName(), 1, Messagebox.EXCLAMATION);
        }
        if (t != null) {
            logger.info(txt2, t);
        } else {
            logger.info(txt2);
        }
    }

    public void alertaError(final String txt,
                            final String txt2,
                            final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, company.getBusinessName(), 1, Messagebox.EXCLAMATION);
        }
        if (t != null) {
            logger.error(txt2, t);
        } else {
            logger.error(txt2);
        }

    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULE_CHANGE_PASS };
    }
}
