package pe.com.jx_market.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Category;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.AdvancedTreeModel;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.CategoryTreeNode;
import pe.com.jx_market.utilities.CategoryTreeNodeCollection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class PO_EACategoryEdit
    extends SelectorComposer<Window>
{
    private static final long serialVersionUID = 6046074628719265175L;

    static Log logger = LogFactory.getLog(PO_EACategoryEdit.class);
    @Autowired
    private BusinessService<DTO_Category> categoryService;
    private DTO_Company company;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Tree tree;
    @Wire
    private Window wEAEC;
    private CategoryTreeNode categoryTreeNode;
    private AdvancedTreeModel categoryTreeModel;

    @SuppressWarnings("unchecked")
    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        this.categoryService = (BusinessService<DTO_Category>) ContextLoader.getService(_comp, "categoryService");

        this.company = (DTO_Company) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);

        this.categoryTreeNode = listarCategories();
        this.categoryTreeModel = new AdvancedTreeModel(this.categoryTreeNode);
        this.tree.setItemRenderer(new CategoryTreeRenderer());
        this.tree.setModel(this.categoryTreeModel);
        this.tree.getRoot().setVisible(true);
    }

    /**
     * @return Structure of categories.
     */
    public CategoryTreeNode listarCategories()
    {
        final DTO_Category cat = new DTO_Category();
        cat.setCompanyId(this.company.getId());
        final ServiceInput<DTO_Category> input = new ServiceInput<DTO_Category>(cat);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Category> output = this.categoryService.execute(input);
        CategoryTreeNode categoryTreeNode = null;
        if (output.getErrorCode() == Constantes.OK) {
            // alertaInfo("", "Exito al cargar categories", null);
            final List<DTO_Category> lstCat = output.getLista();
            categoryTreeNode = construirArbolCategories(lstCat);
        } else {
            // alertaError("Error inesperado, por favor catege al administrador",
            // "Error cargando categories", null);
        }
        return categoryTreeNode;
    }

    /**
     * @param _categories List of categories.
     * @return Structure of categories.
     */
    private CategoryTreeNode construirArbolCategories(final List<DTO_Category> _categories)
    {
        final Map<Integer, DTO_Category> mapCateg = new TreeMap<Integer, DTO_Category>();
        final Map<Integer, DTO_Category> roots = new TreeMap<Integer, DTO_Category>();
        final Map<Integer, DTO_Category> childs = new TreeMap<Integer, DTO_Category>();
        final Set<Integer> setPadres = new HashSet<Integer>();
        for (final DTO_Category cat : _categories) {
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
                    // Agregamos esta Raiz ficticia para poder
                    // convertir un nodo hijo en Raiz de categories
                    add(new CategoryTreeNode(new DTO_Category(Constantes.TREE_EDITABLE_RAIZ),
                        new CategoryTreeNodeCollection()
                        {

                            private static final long serialVersionUID = 3800210198277431722L;
                            {
                                for (final DTO_Category root : roots.values()) {
                                    if (!setPadres.contains(root.getId())) {
                                        add(new CategoryTreeNode(root,
                                                        new CategoryTreeNodeCollection(), true));
                                    } else {
                                        add(new CategoryTreeNode(root,
                                            new CategoryTreeNodeCollection()
                                            {

                                                private static final long serialVersionUID = -5643408533240445491L;
                                                {
                                                    construirJerarquia(
                                                                    childs.values(),
                                                                    root,
                                                                    setPadres, true);
                                                }
                                            }, true));
                                    }
                                }
                            }
                        }, true));
                }
            },
            true);
        return categoryTreeNode;
    }

    /**
     * TODO comment!
     *
     * @author jcuevas
     * @version $Id$
     */
    private final class CategoryTreeRenderer
        implements TreeitemRenderer<CategoryTreeNode>
    {

        @Override
        public void render(final Treeitem _treeItem,
                           final CategoryTreeNode _treeNode,
                           final int _index)
            throws Exception
        {
            final CategoryTreeNode ctn = _treeNode;
            final DTO_Category categ = ctn.getData();
            final Treerow dataRow = new Treerow();
            dataRow.setParent(_treeItem);
            _treeItem.setValue(ctn);
            _treeItem.setOpen(ctn.isOpen());

            final Hlayout hl = new Hlayout();
            if (categ != null && !Constantes.TREE_EDITABLE_RAIZ.equals(categ.getCategoryName())) {
                hl.appendChild(new Image("/widgets/tree/dynamic_tree/img/" + categ.getImagen()));
                final Textbox textBoxCat = new Textbox(categ.getCategoryName());
                textBoxCat.setId(Constantes.TREE_EDITABLE_TEXTBOX + categ.getId());
                hl.appendChild(textBoxCat);
            } else {
                hl.appendChild(new Label(categ.getCategoryName()));
            }
            hl.setSclass("h-inline-block");
            final Treecell treeCell = new Treecell();
            treeCell.appendChild(hl);
            dataRow.setDraggable("true");
            dataRow.appendChild(treeCell);
            dataRow.setDroppable("true");

            if (categ != null && !Constantes.TREE_EDITABLE_RAIZ.equals(categ.getCategoryName())) {
                final Treecell treeCell2 = new Treecell();
                // final Hlayout h2 = new Hlayout();
                final Checkbox checkBoxEstado = new Checkbox(categ.isActive()
                                ? Constantes.STATUS_ACTIVO : Constantes.STATUS_INACTIVO);
                checkBoxEstado.setValue(categ.isActive());
                checkBoxEstado.setChecked(categ.isActive());
                treeCell2.appendChild(checkBoxEstado);
                treeCell2.setSclass("h-inline-block");
                checkBoxEstado.addEventListener(Events.ON_CHECK, new EventListener<Event>()
                {

                    @Override
                    public void onEvent(final Event _event)
                        throws Exception
                    {
                        final Checkbox checkBox = (Checkbox) _event.getTarget();
                        categ.setActive(checkBox.isChecked());
                        checkBox.setValue(checkBoxEstado.isChecked());
                        if (checkBox.isChecked()) {
                            checkBox.setLabel(Constantes.STATUS_ACTIVO);
                        } else {
                            checkBox.setLabel(Constantes.STATUS_INACTIVO);
                        }
                        // Modificamos los hijos siempre y cuando se este
                        // desactivando
                        if (!checkBox.isChecked() && !_treeItem.getChildren().isEmpty()) {
                            for (int i = 0; i < _treeItem.getTreechildren().getChildren().size(); i++) {
                                final Treeitem comp = (Treeitem) _treeItem.getTreechildren().getChildren().get(i);
                                if (comp instanceof Treeitem) {
                                    final Treeitem itemChild = comp;
                                    modificarHijos(itemChild, checkBox.isChecked());
                                }
                            }
                        }
                        // Modificamos los padres siempre y cuando se este
                        // activando
                        if (checkBox.isChecked()
                                        && _treeItem.getParentItem() != null
                                        && _treeItem.getParentItem().getValue() != null
                                        && !Constantes.TREE_EDITABLE_RAIZ.equals(((CategoryTreeNode) _treeItem
                                                        .getParentItem().getValue()).getData().getCategoryName())) {
                            modificarPadres(_treeItem.getParentItem(), checkBox.isChecked());
                        }
                    }

                    private void modificarHijos(final Treeitem _item,
                                                final boolean _checked)
                    {
                        modificarRow(_item, _checked);
                        for (int i = 0; i < _item.getTreechildren().getChildren().size(); i++) {
                            modificarHijos((Treeitem) _item.getTreechildren().getChildren().get(i), _checked);
                        }
                    }

                    private void modificarPadres(final Treeitem _item,
                                                 final boolean _checked)
                    {
                        modificarRow(_item, _checked);
                        if (_checked
                                        && _item.getParentItem() != null
                                        && _item.getParentItem().getValue() != null
                                        && !Constantes.TREE_EDITABLE_RAIZ.equals(((CategoryTreeNode) _item
                                                        .getParentItem()
                                                        .getValue()).getData().getCategoryName())) {
                            modificarPadres(_item.getParentItem(), _checked);
                        }
                    }

                    private void modificarRow(final Treeitem _item,
                                              final boolean _checked)
                    {
                        final Treerow row = _item.getTreerow();
                        final Treecell cell = (Treecell) row.getChildren().get(1);
                        final Checkbox checkb = (Checkbox) cell.getChildren().get(0);
                        ((CategoryTreeNode) _item.getValue()).getData().setActive(_checked);
                        checkb.setChecked(_checked);
                        checkb.setValue(_checked);
                        if (_checked) {
                            checkb.setLabel(Constantes.STATUS_ACTIVO);
                        } else {
                            checkb.setLabel(Constantes.STATUS_INACTIVO);
                        }
                    }
                });
                // treeCell2.appendChild(h2);
                dataRow.appendChild(treeCell2);
            }

            dataRow.addEventListener(Events.ON_DROP, new EventListener<Event>()
            {

                @Override
                public void onEvent(final Event _event)
                    throws Exception
                {
                    // The dragged target is a TreeRow belongs to an
                    // Treechildren of TreeItem.
                    final Treeitem draggedItem = (Treeitem) ((DropEvent) _event).getDragged().getParent();
                    final CategoryTreeNode draggedValue = (CategoryTreeNode) draggedItem.getValue();
                    if (!isAncestor(draggedValue, ctn)) {
                        categoryTreeModel.remove(draggedValue);
                        categoryTreeModel.add((CategoryTreeNode) _treeItem.getValue(),
                            new CategoryTreeNodeCollection()
                            {

                                private static final long serialVersionUID = -4941224185260321214L;
                                {
                                    add(draggedValue);
                                }
                            });
                        draggedValue.getData().setCategoryParentId(categ.getId());
                    } else {
                        logger.info("No puede ingresar un padre dentro de su hijo");
                        Messagebox.show("No puede ingresar un padre dentro de su hijo", "JX_Market",
                                        Messagebox.OK, Messagebox.ERROR);
                    }
                }
            });
        }

        /**
         * @param _p
         * @param _c
         * @return ret.
         */
        private boolean isAncestor(final CategoryTreeNode _p,
                                   final CategoryTreeNode _c)
        {
            boolean ret = false;
            CategoryTreeNode tmp = _c;
            do {
                if (_p == tmp) {
                    ret = true;
                }
            } while ((tmp = (CategoryTreeNode) tmp.getParent()) != null);
            return ret;
        }
    }

    /**
     * @param _event Event Parameter.
     */
    @Listen("onClick = #btnClose")
    public void accionCerrar(final Event _event)
    {
        this.wEAEC.detach();
    }

    /**
     * @param _event Event parameter.
     */
    @Listen("onClick = #btnSave")
    public void accionGrabar(final MouseEvent _event)
    {
        final CategoryTreeNode raiz = this.categoryTreeNode;
        grabarData(raiz);

        this.desktop.getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.recargar(this.desktop, "eACategory.zul");

        this.wEAEC.detach();
    }

    /**
     * @param _nodo Nodo of tree.
     */
    private void grabarData(final CategoryTreeNode _nodo)
    {
        final DTO_Category categ = _nodo.getData();
        final ServiceInput<DTO_Category> input = new ServiceInput<DTO_Category>();
        input.setAction(Constantes.V_REGISTER);
        input.setObject(categ);
        ServiceOutput<DTO_Category> output = null;
        if (categ != null && !Constantes.TREE_EDITABLE_RAIZ.equals(categ.getCategoryName())) {
            final String strIdTxtCateg = new StringBuilder()
                .append(Constantes.TREE_EDITABLE_TEXTBOX).append(categ.getId()).toString();
            final Textbox txtCategName = (Textbox) this.wEAEC.getFellow(strIdTxtCateg);
            categ.setCategoryName(txtCategName.getValue());
            output = this.categoryService.execute(input);
            if (Constantes.OK == output.getErrorCode()) {
                logger.debug("Category '" + categ.getCategoryName() + "' actualizada");
            }
        }

        if (_nodo.getChildren() != null && !_nodo.getChildren().isEmpty()) {
            final List<TreeNode<DTO_Category>> hijos = _nodo.getChildren();
            for (int i = 0; i < hijos.size(); i++) {
                final CategoryTreeNode hijo = (CategoryTreeNode) hijos.get(i);
                grabarData(hijo);
            }
        }
    }
}
