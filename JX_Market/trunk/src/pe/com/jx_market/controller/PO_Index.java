package pe.com.jx_market.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zhtml.Li;
import org.zkoss.zhtml.Ul;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PO_Index
    extends Div
{
    static Log logger = LogFactory.getLog(PO_Index.class);
    private NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private BusinessService articuloService, categoriaService;
    private Ul ulCategorias;
    private Label lbUser, lbTotal, lbItems;

    public void onCreate()
    {
        categoriaService = Utility.getService(this, "categoriaService");
        ulCategorias = (Ul) getFellow("ulCategorias");
        lbUser = (Label) getFellow("lbUser");
        lbTotal = (Label) getFellow("lbTotal");
        lbItems = (Label) getFellow("lbItems");
        ulCategorias.getSclass();
        ulCategorias.setSclass("left_menu");
        //incluir("cEProductos.zul");
        listarCategorias();

        if(getDesktop().getSession().getAttribute("actualizar") == null){
            incluir("cEProductos.zul");
        }

        if (getDesktop().getSession().getAttribute("cliente") != null) {
            final DTO_Cliente cliente = (DTO_Cliente) getDesktop().getSession().getAttribute("cliente");
            lbUser.setValue(cliente.getApellido() + " " + cliente.getNombre());

            @SuppressWarnings("unchecked")
            final Map<Integer, Map<DTO_Articulo, Integer>> map = (Map<Integer, Map<DTO_Articulo, Integer>>)
                                                                    getDesktop().getSession().getAttribute("carrito");

            actualizarCarrito();
        }

        getPage().addEventListener(Events.ON_BOOKMARK_CHANGE,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event arg0) throws Exception {
                                try {
                                    incluir(getDesktop().getBookmark());
                                } catch (final org.zkoss.zk.ui.ComponentNotFoundException ex) {
                                    incluir("cEProductos.zul");
                                }
                            }
                      });
    }

    public void listarCategorias()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        final DTO_Input input = new DTO_Input(cat);
        input.setVerbo(Constantes.V_LIST);
        final DTO_Output output = categoriaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat = output.getLista();
            int cont = 1;
            for (final DTO_Categoria categ : lstCat) {
                final Li item = new Li();
                if (cont % 2 == 0) {
                    item.setSclass("even");
                } else {
                    item.setSclass("odd");
                }
                item.appendChild((new Label(categ.getNombre())));
                item.setAttribute("categoria", categ);
                item.addEventListener("onClick",
                                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(final Event e)
                        throws UiException
                    {
                        getDesktop().getSession().setAttribute("categoria", e.getTarget().getAttribute("categoria"));
                        Executions.sendRedirect(null);
                    }
                });
                ulCategorias.appendChild(item);
                cont++;
            }
        } else {
            alertaError("Error inesperado, por favor contacte al administrador", "Error cargando categorias", null);
        }
    }

    @SuppressWarnings("unchecked")
    private void actualizarCarrito () {
        final Map<String, Object> map2 = (Map<String, Object>) getDesktop().getSession().getAttribute("totales");
        final Integer cantTot = (Integer) map2.get("cantidad");
        final BigDecimal precTotal = (BigDecimal) map2.get("total");
        lbItems.setValue(""+cantTot);
        lbTotal.setValue(formateador.format(precTotal));

    }

    public void incluir(final String txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        getDesktop().getSession().setAttribute("actualizar", "actualizar");
        Utility.saltar(this, txt);
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, "JX_Market", 1, Messagebox.INFORMATION);
            if (t != null) {
                logger.info(txt2, t);
            } else {
                logger.info(txt2);
            }
        } catch (final InterruptedException ex) {
        }
    }

    public void alertaError(final String txt,
                            final String txt2,
                            final Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, "JX_Market", 1, Messagebox.EXCLAMATION);
            if (t != null) {
                logger.error(txt2, t);
            } else {
                logger.error(txt2);
            }
        } catch (final InterruptedException ex) {
        }

    }

}
