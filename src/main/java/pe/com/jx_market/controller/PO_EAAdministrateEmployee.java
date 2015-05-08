package pe.com.jx_market.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Employee;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateEmployee
    extends SecuredComposer<Window>
{

    static Log logger = LogFactory.getLog(PO_EAAdministrateEmployee.class);
    @Wire
    private Grid lstEmp;
    @Wire
    private Popup popDetails;
    @Wire
    private Label lblUser, lblName, lblLastName, lblSecLastName, lblDocType, lblDocument, lblRole, lblPhone, lblMail, lblCity, lblStatus;
    private Combobox cmbRole, cmbEstado;
    private DTO_Company company;
    @WireVariable
    private BusinessService<DTO_Employee> employeeService;
    @WireVariable
    private BusinessService<DTO_Role> roleService;
    @WireVariable
    private BusinessService<DTO_User> userService;
    @WireVariable
    private Desktop desktop;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        company = (DTO_Company) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        cargarRoles();
        CargarTabla();
    }

    public void limpiarCrear()
    {
        txtNombre.setValue("");
        txtApellidos.setValue("");
        txtTelefono.setValue("");
        txtMail.setValue("");
        txtUser.setValue("");
        txtPass.setValue("");
        cmbEstado.setSelectedItem(null);
        cmbRole.setSelectedItem(null);
    }

    @SuppressWarnings("unchecked")
    public void cargarInformacionContacto(final DTO_Employee employee)
    {
        capInfo.setLabel("Información del Contacto");
        btnCrear.setVisible(false);
        txtUser.setAttribute("user", employee);
        txtUser.setReadonly(true);
        txtUser.setValue((getUser(employee.getUserId())).getUsername());
        txtNombre.setValue(employee.getEmployeeName());
        txtApellidos.setValue(employee.getEmployeeLastName());
        txtDNI.setValue(employee.getDocumentNumber());
        txtDireccion.setValue(employee.getAddress() != null ? employee.getAddress() : "");
        txtCelular.setValue(employee.getPhone() != null ? employee.getPhone() : "");
        txtCiudad.setValue(employee.getCity() != null ? employee.getCity() : "");
        txtRegion.setValue(employee.getUbigeo() != null ? employee.getUbigeo() : "");
        txtTelefono.setValue(employee.getCellPhone() != null ? employee.getCellPhone() : "");
        txtMail.setValue(employee.getEmail());
        txtPass.setValue("");

        final List<Comboitem> roles = cmbRole.getItems();
        for(final Comboitem item : roles) {
            final DTO_Role role = (DTO_Role) item.getAttribute("role");
            /*if(role.getId().equals(employee.getRoleId())) {
                cmbRole.setSelectedItem(item);
            }*/
        }
        final List<Comboitem> estados = cmbEstado.getItems();
        for(final Comboitem item : estados) {
            final Integer stat = Integer.parseInt((String) item.getValue());
            if(stat.equals(employee.getActive())) {
                cmbEstado.setSelectedItem(item);
            }
        }
    }

    public void cargarPop(final DTO_Employee employee)
    {
        lblUser.setValue((getUser(employee.getUserId())).getUsername());
        lblNombre.setValue(employee.getEmployeeName());
        lblApellidos.setValue(employee.getEmployeeLastName());
        //lblRole.setValue((getRole(employee.getRoleId()).getRoleName()));
        lblCiudad.setValue(employee.getCity());
        lblDNI.setValue(employee.getDocumentNumber());
        final String estado = Constantes.ST_ACTIVO.equals(employee.getActive()) ? Constantes.STATUS_ACTIVO
                                                                                     : Constantes.STATUS_INACTIVO;
        lblEstado.setValue(estado);
        lblMail.setValue(employee.getEmail());
        lblTelefono.setValue(employee.getPhone());
    }

    public void CargarTabla()
    {
        grdEmp.getRows().getChildren().clear();
        final DTO_Employee emp = new DTO_Employee();
        emp.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(emp);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput output = employeeService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Employee> ulist = output.getLista();
            for (final DTO_Employee uOut : ulist) {
                final Row fila = new Row();
                fila.setAttribute("employee", uOut);
                // Fecha Creacion
                /*
                 fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                 .format(uOut.getFecha_creacion())));
                 */
                // Username
                // fila.appendChild(new Label(uOut.getUser()));
                fila.appendChild(new Label(uOut.getEmployeeName()));
                fila.appendChild(new Label(uOut.getEmployeeLastName()));
                fila.appendChild(new Label(getUser(uOut.getUserId()).getUsername()));
                //fila.appendChild(new Label((getRole(uOut.getRoleId())).getRoleName()));

                final Image ImDetalles = new Image("media/details.png");
                ImDetalles.setStyle("cursor:pointer");
                ImDetalles.setPopup(popDetails);
                ImDetalles.addEventListener("onClick",
                                new org.zkoss.zk.ui.event.EventListener() {
                                    @Override
                                    public void onEvent(final Event e)
                                        throws UiException
                                    {
                                         cargarPop((DTO_Employee) ((Row)
                                         e.getTarget().getParent()).getAttribute("employee"));

                                    }
                                });
                fila.appendChild(ImDetalles);
                final Image ImEditar = new Image("media/edit.png");
                ImEditar.setStyle("cursor:pointer");
                ImEditar.addEventListener("onClick",
                                new org.zkoss.zk.ui.event.EventListener() {
                                    @Override
                                    public void onEvent(final Event e)
                                        throws UiException
                                    {
                                        for (int i = 0; i < grdEmp.getRows()
                                                        .getChildren().size(); i++) {

                                            ((Image) ((Div) (((Row) (((Rows) e
                                                            .getTarget().getParent()
                                                            .getParent().getParent())
                                                            .getChildren().get(i)))
                                                            .getChildren().get(6)))
                                                            .getChildren().get(0))
                                                            .setVisible(false);
                                            ((Image) ((Div) (((Row) (((Rows) e
                                                            .getTarget().getParent()
                                                            .getParent().getParent())
                                                            .getChildren().get(i)))
                                                            .getChildren().get(6)))
                                                            .getChildren().get(1))
                                                            .setVisible(true);
                                            ((Image) ((Div) (((Row) (((Rows) e
                                                            .getTarget().getParent()
                                                            .getParent().getParent())
                                                            .getChildren().get(i)))
                                                            .getChildren().get(5)))
                                                            .getChildren().get(0))
                                                            .setVisible(false);
                                            ((Image) ((Div) (((Row) (((Rows) e
                                                            .getTarget().getParent()
                                                            .getParent().getParent())
                                                            .getChildren().get(i)))
                                                            .getChildren().get(5)))
                                                            .getChildren().get(1))
                                                            .setVisible(true);
                                        }
                                        btnCancel.setVisible(true);
                                        btnInfo.setVisible(true);

                                        cargarInformacionContacto((DTO_Employee) ((Row) e
                                        .getTarget().getParent().getParent())
                                        .getAttribute("employee"));
                                    }
                                });

                final Image ImEditarDisab = new Image("media/editdelete.png");
                ImEditarDisab.setVisible(false);
                final Div Diveditar = new Div();
                Diveditar.appendChild(ImEditar);
                Diveditar.appendChild(ImEditarDisab);
                fila.appendChild(Diveditar);
                final Image ImEliminar = new Image("media/cancel.png");
                ImEliminar.setStyle("cursor:pointer");
                ImEliminar.addEventListener("onClick",
                                new org.zkoss.zk.ui.event.EventListener() {
                                    @Override
                                    public void onEvent(final Event e)
                                        throws UiException
                                    {
                                        final int msg = Messagebox.show("¿Está seguro de eliminar el Employee?",
                                                                    company.getBusinessName(),
                                                                    Messagebox.YES | Messagebox.NO,
                                                                    Messagebox.QUESTION);
                                        if (msg == Messagebox.YES) {
                                            elimina((DTO_Employee) ((Row) e
                                                            .getTarget().getParent()
                                                            .getParent())
                                                            .getAttribute("employee"));
                                        }

                                    }
                                });
                final Image ImDisable = new Image("media/fileclose.png");
                ImDisable.setVisible(false);

                final Div Diveliminar = new Div();
                Diveliminar.appendChild(ImEliminar);
                Diveliminar.appendChild(ImDisable);
                fila.appendChild(Diveliminar);
                grdEmp.getRows().appendChild(fila);
            }
        } else {

        }
    }

    private DTO_User getUser(final Integer codigo)
    {
        final DTO_User user = new DTO_User();
        user.setId(codigo);
        user.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(user);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput output = userService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return (DTO_User) output.getLista().get(0);
        } else {
            return null;
        }
    }

    private DTO_Role getRole(final Integer codigo)
    {
        final DTO_Role role = new DTO_Role();
        role.setId(codigo);
        role.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(role);
        input.setAction(Constantes.V_GET);
        final ServiceOutput output = roleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return (DTO_Role) output.getObject();
        } else {
            return null;
        }
    }

    public void cargarRoles()
    {
        final DTO_Role per = new DTO_Role();
        per.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(per);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput output = roleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Role> listado = output.getLista();
            for (final DTO_Role role : listado) {
                final Comboitem item = new Comboitem(role.getRoleName());
                item.setAttribute("role", role);
                cmbRole.appendChild(item);
            }
        } else {
            alertaError("Error en la carga de roles",
                            "error al cargar los roles", null);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[]{Constantes.MODULE_ADM_EMPLOYEE };
    }
}
