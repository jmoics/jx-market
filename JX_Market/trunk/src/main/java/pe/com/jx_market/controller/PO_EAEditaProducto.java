/**
 *
 */
package pe.com.jx_market.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
public class PO_EAEditaProducto
    extends SecuredWindow
{
    static Log logger = LogFactory.getLog(PO_EAEditaProducto.class);
    private Combobox cmbCateg, cmbEstado;
    private Textbox txtNombre, txtDesc, txtMarca;
    private Decimalbox decPrec;
    private Image imgFoto;
    private byte[] imgProducto;
    private BusinessService articuloService, categoriaService;
    private DTO_Empresa empresa;
    private DTO_Articulo articulo;

    @Override
    public void realOnCreate()
    {
        imgFoto = (Image) getFellow("imgFoto");
        cmbCateg = (Combobox) getFellow("cmbCateg");
        cmbEstado = (Combobox) getFellow("cmbEstado");
        txtNombre = (Textbox) getFellow("txtNombre");
        txtDesc = (Textbox) getFellow("txtDesc");
        txtMarca = (Textbox) getFellow("txtMarca");
        decPrec = (Decimalbox) getFellow("decPrec");
        decPrec.setValue(BigDecimal.ZERO);

        articuloService = ContextLoader.getService(this, "articuloService");
        categoriaService = ContextLoader.getService(this, "categoriaService");

        empresa = (DTO_Empresa) getDesktop().getSession().getAttribute("empresa");

        articulo = (DTO_Articulo) getDesktop().getSession().getAttribute("producto");
        final ServiceInput input = new ServiceInput(articulo);
        input.setAccion(Constantes.V_GETIMG);
        final ServiceOutput output = articuloService.execute(input);
        if (output.getErrorCode() != Constantes.OK) {
            alertaInfo("", "El articulo" + articulo.getProductName() + "no posee imagen", null);
        }

        if (articulo == null) {
            alertaInfo("", "No se encontro producto, retornando a busqueda", null);
            incluir("eAConsultaProducto.zul");
        } else {
            getDesktop().getSession().removeAttribute("producto");
            cargarDatos();
        }
    }

    public void editarProducto()
    {
        if (cmbCateg.getSelectedItem() != null && cmbEstado.getSelectedItem() != null && !txtNombre.getValue().equals("")
                        && !txtDesc.getValue().equals("") && !txtMarca.getValue().equals("")
                        && decPrec.getValue() != null && decPrec.getValue() != BigDecimal.ZERO) {
            //articulo.setCategoria(((DTO_Categoria) cmbCateg.getSelectedItem().getAttribute("categoria")).getCodigo());
            articulo.setActivo((Boolean) cmbEstado.getSelectedItem().getValue());
            articulo.setProductDescription(txtDesc.getValue());
            articulo.setEmpresa(empresa.getCodigo());
            //articulo.setMarca(txtMarca.getValue());
            articulo.setProductName(txtNombre.getValue());
            //articulo.setPrecio(decPrec.getValue());
            if (imgProducto != null && !imgProducto.equals(articulo.getImagen())) {
                articulo.setImagen(imgProducto);
                articulo.setNomimg(null);
            }
            final ServiceInput input = new ServiceInput(articulo);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput output = articuloService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                alertaInfo("Los datos del producto se actualizaron correctamente",
                                "Los datos del producto se actualizaron correctamente", null);
            } else {
                alertaError("Ocurrio un error inesperado al guardar el producto, consulte al Administrador",
                                "Ocurrio un error inesperado al guardar el producto, consulte al Administrador", null);
            }
        } else {
            alertaInfo("Debe ingresar datos en todos los campos", "No se ingreso data en todos los campos", null);
        }
    }

    public void volverConsulta()
    {
        incluir("eAConsultaProducto.zul");
    }

    private void cargarDatos()
    {
        listarEstados();
        listarCategorias();
        txtNombre.setValue(articulo.getProductName());
        //txtMarca.setValue(articulo.getMarca());
        txtDesc.setValue(articulo.getProductDescription());
        //decPrec.setValue(articulo.getPrecio());
        imgProducto = articulo.getImagen();
        setGraficoFoto();
    }

    private void setGraficoFoto()
    {
        if (imgProducto != null) {
            try {
                imgFoto.setContent(new AImage("foto", imgProducto));
                return;
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        imgFoto.setSrc("/media/imagProd.gif");
    }

    public void cargaFoto(final UploadEvent event)
        throws Exception
    {
        org.zkoss.util.media.Media media;
        try {
            media = event.getMedia();
            if (media == null) {
                return;
            }
        } catch (final Exception ex) {
            Messagebox.show("Hubo un problema con el archivo proporcionado.", empresa.getRazonsocial(), Messagebox.OK, Messagebox.ERROR);
            return;
        }
        // System.out.println(media.getName());
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 102400) {
                Messagebox.show("El archivo seleccionado es muy grande. Maximo permitido = 100k", empresa.getRazonsocial(), Messagebox.OK,
                                Messagebox.ERROR);
                return;
            }
            imgProducto = media.getByteData();
            setGraficoFoto();

            // imgFoto.setContent((org.zkoss.image.Image)media);

        } else {
            Messagebox.show("El archivo seleccionado " + media + " no es una imagen", empresa.getRazonsocial(), Messagebox.OK,
                            Messagebox.ERROR);
            return;
        }
    }

    private void listarCategorias()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final ServiceInput input = new ServiceInput(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = categoriaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat = output.getLista();
            for (final DTO_Categoria categ : lstCat) {
                final Comboitem item = new Comboitem();
                item.setLabel(categ.getCategoryName());
                item.setAttribute("categoria", categ);
                cmbCateg.appendChild(item);
                /*if (articulo.getCategoria().equals(categ.getCodigo())) {
                    cmbCateg.setSelectedItem(item);
                }*/
            }
        } else {
            alertaError("Error inesperado, por favor contacte al administrador", "Error cargando categorias", null);
        }
    }

    private void listarEstados()
    {
        Comboitem item = new Comboitem();
        item.setLabel("Activo");
        item.setValue(Constantes.ST_ACTIVO);
        cmbEstado.appendChild(item);
        if (articulo.getActivo() == Constantes.STB_ACTIVO) {
            cmbEstado.setSelectedItem(item);
        }
        item = new Comboitem();
        item.setLabel("Inactivo");
        item.setValue(Constantes.ST_INACTIVO);
        cmbEstado.appendChild(item);
        if (articulo.getActivo() == Constantes.STB_INACTIVO) {
            cmbEstado.setSelectedItem(item);
        }
    }

    public void incluir(final String txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        getDesktop().getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.saltar(this, txt);
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.INFORMATION);
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
            Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.EXCLAMATION);
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
        return new String[] { Constantes.MODULO_PROD_PRODUCT };
    }
}
