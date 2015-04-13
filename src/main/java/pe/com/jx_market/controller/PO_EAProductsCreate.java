/**
 *
 */
package pe.com.jx_market.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_ArticuloImage;
import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Marca;
import pe.com.jx_market.utilities.AdvancedTreeModel;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.CategoryTreeNode;
import pe.com.jx_market.utilities.CategoryTreeNodeCollection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
@VariableResolver(DelegatingVariableResolver.class)
public class PO_EAProductsCreate
    extends SecuredComposer<Window>
{
    private static final long serialVersionUID = -5294436527686469836L;

    static Log logger = LogFactory.getLog(PO_EAProductsCreate.class);
    @Wire
    private Combobox cmbStatus, cmbMarca;
    @Wire
    private Textbox txtNombre, txtDesc;
    @Wire
    private Image imgFoto;
    @Wire
    private Label lbImageName, lbImageSize;
    @Wire
    private Window wEAPC;
    @Wire
    private Tree tree;
    @Wire
    private Listbox lstImages;
    @WireVariable
    private BusinessService<DTO_Articulo> productService;
    @WireVariable
    private BusinessService<DTO_Categoria> categoryService;
    @WireVariable
    private BusinessService<DTO_Marca> marcaService;
    @WireVariable
    private Desktop desktop;
    private DTO_Empresa empresa;
    private DTO_Articulo articulo;
    private CategoryTreeNode categoryTreeNode;
    private AdvancedTreeModel categoryTreeModel;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        empresa = (DTO_Empresa) desktop.getSession().getAttribute("empresa");
        buildActiveCombo(cmbStatus);
        articulo = null;

        desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_PRODUCT);
        tree.setCheckmark(true);
        tree.setMultiple(true);
        categoryTreeNode = getCategories();
        categoryTreeModel = new AdvancedTreeModel(categoryTreeNode);
        categoryTreeModel.setMultiple(true);
        tree.setItemRenderer(new CategoriaTreeRenderer());
        tree.setModel(categoryTreeModel);

        buildMarcaCombo();
    }

    private void buildMarcaCombo()
    {
        final DTO_Marca marFi = new DTO_Marca();
        marFi.setEmpresa(empresa.getCodigo());
        final ServiceInput<DTO_Marca> input = new ServiceInput<DTO_Marca>(marFi);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_Marca> output = marcaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo(logger, "", "Exito al cargar categorias", null);
            final List<DTO_Marca> lstMar = output.getLista();
            for (final DTO_Marca marIt : lstMar) {
                final Comboitem item = new Comboitem();
                item.setLabel(marIt.getMarcaName());
                item.setValue(marIt);
                cmbMarca.appendChild(item);
            }
        } else {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.Error.Label"), "Error cargando marcas", null);
        }
    }

    @Listen("onClick = #btnSave")
    public void createProduct()
    {
        if (!txtNombre.getValue().equals("") && !txtDesc.getValue().equals("")
                        && cmbMarca.getSelectedItem() != null && cmbStatus.getSelectedItem() != null) {
            final DTO_Articulo articulo = new DTO_Articulo();
            //articulo.setCategoria(((DTO_Categoria) cmbCateg.getSelectedItem().getAttribute("categoria")).getCodigo());
            articulo.setActivo((Boolean) cmbStatus.getSelectedItem().getValue());
            articulo.setMarca((DTO_Marca) cmbMarca.getSelectedItem().getValue());
            articulo.setProductDescription(txtDesc.getValue());
            articulo.setEmpresa(empresa.getCodigo());
            //articulo.setMarca(txtMarca.getValue());
            articulo.setProductName(txtNombre.getValue());
            //articulo.setPrecio(decPrec.getValue());
            final ServiceInput<DTO_Articulo> input = new ServiceInput<DTO_Articulo>(articulo);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput<DTO_Articulo> output = productService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info.Label"), null);
                this.articulo = articulo;
                this.articulo.setCategories(new ArrayList<DTO_Categoria>());
                this.articulo.setImages(new ArrayList<DTO_ArticuloImage>());
                //limpiarCampos();
            } else {
                alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Error.Label"), null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info2.Label"), null);
        }
    }

    @Listen("onClick = #btnClose, #btnClose2")
    public void accionCerrar(final Event _event) {
        wEAPC.detach();
    }

    @Listen("onClick = #btnSave2")
    public void saveCategories(final Event _event)
    {
        if (articulo != null) {
            final Map<Integer, DTO_Categoria> mapCat4Prod = new HashMap<Integer, DTO_Categoria>();
            for (final DTO_Categoria cat : articulo.getCategories()) {
                mapCat4Prod.put(cat.getId(), cat);
            }
            if (tree.getItems() != null && !tree.getItems().isEmpty()) {
                final DTO_Articulo prod4DelCat = new DTO_Articulo();
                prod4DelCat.setId(articulo.getId());
                prod4DelCat.setEmpresa(articulo.getEmpresa());
                prod4DelCat.setCategories(new ArrayList<DTO_Categoria>());
                for (final Treeitem item : tree.getItems()) {
                    saveCategories(item, mapCat4Prod, prod4DelCat);
                }

                ServiceInput<DTO_Articulo> input = new ServiceInput<DTO_Articulo>(articulo);
                input.setAccion(Constantes.V_REGISTERCAT4PROD);
                ServiceOutput<DTO_Articulo> output = productService.execute(input);
                boolean correct = true;
                if (output.getErrorCode() != Constantes.OK) {
                    alertaError(logger, "", "Error al guardar nuevas categorias para el producto", null);
                    correct = false;
                }
                input = new ServiceInput<DTO_Articulo>(prod4DelCat);
                input.setAccion(Constantes.V_DELETECAT4PROD);
                output = productService.execute(input);
                if (output.getErrorCode() != Constantes.OK) {
                    alertaError(logger, "", "Error al eliminar categorias para el producto", null);
                    correct = false;
                }
                if (correct) {
                    alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveCategories.Info.Label"),
                                    "", null);
                } else {
                    alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveCategories.Error.Label"),
                                    "Error al guardar o eliminar categorias para el producto", null);
                }
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveCategories.Info2.Label"),
                            "Ningun producto a sido creado previamente", null);
        }
    }

    private void saveCategories(final Treeitem _item,
                                final Map<Integer, DTO_Categoria> _mapCat4Prod,
                                final DTO_Articulo _prod4DelCat)
    {
        final CategoryTreeNode ctn = _item.getValue();
        final DTO_Categoria categ = ctn.getData();

        if (_item.isSelected() && !_mapCat4Prod.containsKey(categ.getId())) {
            articulo.getCategories().add(categ);
        } else if (!_item.isSelected() && _mapCat4Prod.containsKey(categ.getId())) {
            for (int cnt = 0; cnt < articulo.getCategories().size(); cnt++) {
                if (articulo.getCategories().get(cnt).getId().equals(categ.getId())) {
                    _prod4DelCat.getCategories().add(articulo.getCategories().get(cnt));
                    articulo.getCategories().remove(cnt);
                }
            }
        }
        if (_item.getTreechildren() != null) {
            for (int i = 0; i < _item.getTreechildren().getChildren().size(); i++) {
                saveCategories((Treeitem) _item.getTreechildren().getChildren().get(i), _mapCat4Prod, _prod4DelCat);
            }
        }
    }

    public void limpiarCampos()
    {
        cmbStatus.setSelectedItem(null);
        cmbStatus.setValue(null);
        txtNombre.setValue("");
        txtDesc.setValue("");
        //imgProducto = null;
        setGraficoFoto(null);
    }

    public CategoryTreeNode getCategories()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final ServiceInput<DTO_Categoria> input = new ServiceInput<DTO_Categoria>(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_Categoria> output = categoryService.execute(input);
        CategoryTreeNode categoryTreeNode = null;
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Categoria> lstCat = output.getLista();
            categoryTreeNode = buildCategoriesTree(lstCat);
        } else {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.Error.Label"),
                            "Error cargando categorias", null);
        }
        return categoryTreeNode;
    }

    private CategoryTreeNode buildCategoriesTree(final List<DTO_Categoria> _categorias)
    {
        final Map<Integer, DTO_Categoria> mapCateg = new TreeMap<Integer, DTO_Categoria>();
        final Map<Integer, DTO_Categoria> roots = new TreeMap<Integer, DTO_Categoria>();
        final Map<Integer, DTO_Categoria> childs = new TreeMap<Integer, DTO_Categoria>();
        final Set<Integer> setPadres = new HashSet<Integer>();
        for (final DTO_Categoria cat : _categorias) {
            mapCateg.put(cat.getId(), cat);
            setPadres.add(cat.getCategoryParentId());
            if (cat.getCategoryParentId() == null) {
                roots.put(cat.getId(), cat);
            } else {
                childs.put(cat.getId(), cat);
            }
        }

        final CategoryTreeNode categoryTreeNode = new CategoryTreeNode(null,
                        new CategoryTreeNodeCollection()
        {

            private static final long serialVersionUID = -8249078122595873454L;
            {
                for (final DTO_Categoria root : roots.values()) {
                    if (!setPadres.contains(root.getId())) {
                        add(new CategoryTreeNode(root));
                    } else {
                        add(new CategoryTreeNode(root,
                                        new CategoryTreeNodeCollection()
                        {
                            private static final long serialVersionUID = -5643408533240445491L;
                            {
                                construirJerarquia(childs.values(), root, setPadres, false);
                            }
                        }, true));
                    }
                }
            }
        },
        true);
        return categoryTreeNode;
    }

    private final class CategoriaTreeRenderer
        implements TreeitemRenderer<CategoryTreeNode>
    {
        @Override
        public void render(final Treeitem _treeItem,
                           final CategoryTreeNode _treeNode,
                           final int _index)
            throws Exception
        {
            final Map<Integer, DTO_Categoria> mapCat4Prod = new HashMap<Integer, DTO_Categoria>();
            if (articulo != null) {
                for (final DTO_Categoria cat : articulo.getCategories()) {
                    mapCat4Prod.put(cat.getId(), cat);
                }
            }
            final CategoryTreeNode ctn = _treeNode;
            final DTO_Categoria categ = ctn.getData();
            final Treerow dataRow = new Treerow();
            dataRow.setParent(_treeItem);
            _treeItem.setValue(ctn);
            _treeItem.setOpen(ctn.isOpen());
            if (mapCat4Prod.containsKey(categ.getId())) {
                _treeItem.setSelected(true);
            }

            final Hlayout hl = new Hlayout();
            hl.appendChild(new Image("/widgets/tree/dynamic_tree/img/" + categ.getImagen()));
            hl.appendChild(new Label(categ.getCategoryName()));
            hl.setSclass("h-inline-block");
            final Treecell treeCell = new Treecell();
            treeCell.appendChild(hl);
            dataRow.appendChild(treeCell);
            final Treecell treeCell2 = new Treecell();
            final Hlayout h2 = new Hlayout();
            h2.appendChild(new Label(categ.getEstado() ? Constantes.STATUS_ACTIVO : Constantes.STATUS_INACTIVO));
            treeCell2.appendChild(h2);
            dataRow.appendChild(treeCell2);

            _treeItem.addEventListener(Events.ON_CLICK, new EventListener<Event>()
            {
                @Override
                public void onEvent(final Event event)
                    throws Exception
                {
                    final Treeitem item = (Treeitem) event.getTarget();
                    if (item.isSelected() && item.getParentItem() != null
                                            && item.getParentItem().getValue() != null) {
                        modificarPadres(item.getParentItem(), item.isSelected());
                    }
                    if (!item.isSelected() && item.getTreechildren() != null) {
                        for (int i = 0; i < item.getTreechildren().getChildren().size(); i++) {
                            final Treeitem comp = (Treeitem) item.getTreechildren().getChildren().get(i);
                            if (comp instanceof Treeitem) {
                                final Treeitem itemChild = comp;
                                modificarHijos(itemChild, item.isSelected());
                            }
                        }
                    }
                }

                private void modificarHijos(final Treeitem _item,
                                            final boolean _checked)
                {
                    _item.setSelected(_checked);
                    if (_item.getTreechildren() != null) {
                        for (int i = 0; i < _item.getTreechildren().getItemCount(); i++) {
                            modificarHijos((Treeitem) _item.getTreechildren().getChildren().get(i), _checked);
                        }
                    }
                }
                private void modificarPadres(final Treeitem _item,
                                             final boolean _checked) {
                    _item.setSelected(_checked);
                    if (_checked && _item.getParentItem() != null
                            && _item.getParentItem().getValue() != null) {
                        modificarPadres(_item.getParentItem(), _checked);
                    }
                }
            });
        }
    }

    private void setGraficoFoto(final Media _media)
    {
        if (_media != null) {
            final byte[] imgProducto = _media.getByteData();
            if (imgProducto != null) {
                try {
                    imgFoto.setContent(new AImage("foto", imgProducto));
                    lbImageName.setValue(_media.getName());
                    lbImageSize.setValue("" + _media.getByteData().length);

                    final DTO_ArticuloImage img4Prod = new DTO_ArticuloImage();
                    img4Prod.setCompany(empresa.getCodigo());
                    img4Prod.setImage(imgFoto.getContent().getByteData());
                    img4Prod.setImageName(_media.getName());

                    final Listitem item = new Listitem();
                    item.setValue(img4Prod);
                    Listcell cell = new Listcell();
                    cell.setLabel(_media.getName());
                    item.appendChild(cell);
                    cell = new Listcell();
                    cell.setLabel("" + _media.getByteData().length);
                    item.appendChild(cell);
                    item.setParent(lstImages);
                    item.addEventListener(Events.ON_CLICK, new EventListener<Event>()
                    {
                        @Override
                        public void onEvent(final Event event)
                            throws Exception
                        {
                            final Listitem lItem = (Listitem) event.getTarget();
                            final DTO_ArticuloImage img = lItem.getValue();
                            lbImageName.setValue(img.getImageName());
                            lbImageSize.setValue("" + img.getImage().length);
                            imgFoto.setContent(new AImage("foto", img.getImage()));
                        }
                    });
                    return;
                } catch (final IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        imgFoto.setSrc("/media/silueta.gif");
    }

    @Listen("onUpload = #btnUpload")
    public void uploadPhoto(final UploadEvent _event)
        throws Exception
    {
        Media media;
        try {
            media = _event.getMedia();
            if (media == null) {
                return;
            }
        } catch (final Exception ex) {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.uploadPhoto.Error.Label"),
                            "Hubo un problema con el archivo proporcionado.", ex);
            return;
        }
        System.out.println(media.getName());
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 102400) {
                alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.uploadPhoto.Info.Label"),
                                "El archivo seleccionado es muy grande. Maximo permitido = 100k", null);
                return;
            }
            setGraficoFoto(media);

            // imgFoto.setContent((org.zkoss.image.Image)media);
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.uploadPhoto.Info2.Label",
                                                media.getName()),
                            "El archivo seleccionado " + media.getName() + " no es una imagen", null);
            return;
        }
    }

    @Listen("onClick = #btnSave3")
    public void saveImages(final Event _event) {
        if (articulo != null) {
            if (lstImages.getItems() != null && !lstImages.getItems().isEmpty()) {
                for (final Listitem imgIt : lstImages.getItems()) {
                    final DTO_ArticuloImage img4Prod = imgIt.getValue();
                    img4Prod.setProductId(articulo.getId());
                    articulo.getImages().add(img4Prod);
                }
                final ServiceInput<DTO_Articulo> input = new ServiceInput<DTO_Articulo>(articulo);
                input.setAccion(Constantes.V_REGISTERIMG4PROD);
                final ServiceOutput<DTO_Articulo> output = productService.execute(input);
                if (output.getErrorCode() == Constantes.OK) {
                    alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info.Label"),
                                    Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info.Label"), null);
                    articulo.setImages(new ArrayList<DTO_ArticuloImage>());
                    cleanImagePanel();
                } else {
                    alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Error.Label"),
                                    "Erro al guardar la imagen.", null);
                }
            } else {
                alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info3.Label"),
                                "No hay imagenes para cargar", null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.saveImage.Info2.Label"),
                            "Ningun producto a sido creado previamente", null);
        }
    }

    private void cleanImagePanel() {
        lbImageName.setValue(null);
        lbImageSize.setValue(null);
        setGraficoFoto(null);
        lstImages.getItems().clear();
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULO_PROD_PRODUCT };
    }
}
