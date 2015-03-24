package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.utilities.AdvancedTreeModel;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.CategoriaTreeNode;
import pe.com.jx_market.utilities.CategoriaTreeNodeCollection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAConsultaCategoria
extends SelectorComposer<Window>
{

    private static final long serialVersionUID = -1481359887612974294L;

    static Log logger = LogFactory.getLog(PO_EAConsultaCategoria.class);
    @Wire
    private Window wEACC;
    @Autowired
    private BusinessService<DTO_Categoria> categoriaService;
    private DTO_Empresa empresa;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Tree tree;
    private CategoriaTreeNode categoriaTreeNode;
    private AdvancedTreeModel categoriaTreeModel;

    @SuppressWarnings("unchecked")
    @Override
    public void doAfterCompose(final Window comp)
        throws Exception
    {
        super.doAfterCompose(comp);

        categoriaService = (BusinessService<DTO_Categoria>) ContextLoader.getService(comp, "categoriaService");

        empresa = (DTO_Empresa) desktop.getSession().getAttribute("empresa");

        listarCategorias();
        categoriaTreeModel = new AdvancedTreeModel(categoriaTreeNode);
        tree.setItemRenderer(new CategoriaTreeRenderer());
        tree.setModel(categoriaTreeModel);
    }

    public void listarCategorias()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final ServiceInput<DTO_Categoria> input = new ServiceInput<DTO_Categoria>(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_Categoria> output = categoriaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            // alertaInfo("", "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat = output.getLista();
            construirArbolCategorias(lstCat);
        } else {
            // alertaError("Error inesperado, por favor catege al administrador",
            // "Error cargando categorias", null);
        }
    }

    private void construirArbolCategorias(final List<DTO_Categoria> _categorias)
    {
        final Map<Integer, DTO_Categoria> mapCateg = new TreeMap<Integer, DTO_Categoria>();
        final Map<Integer, DTO_Categoria> roots = new TreeMap<Integer, DTO_Categoria>();
        final Map<Integer, DTO_Categoria> childs = new TreeMap<Integer, DTO_Categoria>();
        final Set<Integer> setPadres = new HashSet<Integer>();
        for (final DTO_Categoria cat : _categorias) {
            mapCateg.put(cat.getCodigo(), cat);
            setPadres.add(cat.getCodigoPadre());
            if (cat.getCodigoPadre() == null) {
                roots.put(cat.getCodigo(), cat);
            } else {
                childs.put(cat.getCodigo(), cat);
            }
        }

        categoriaTreeNode = new CategoriaTreeNode(null,
                        new CategoriaTreeNodeCollection()
        {

            private static final long serialVersionUID = -8249078122595873454L;
            {
                for (final DTO_Categoria root : roots.values()) {
                    if (!setPadres.contains(root.getCodigo())) {
                        add(new CategoriaTreeNode(root));
                    } else {
                        add(new CategoriaTreeNode(root,
                                        new CategoriaTreeNodeCollection()
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
    }

    @Listen("onClick = #btnEditar")
    public void lanzarWindowEditar(final MouseEvent event) {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        final Window w = (Window) Executions.createComponents("eAEditaCategoria.zul", null, dataArgs);
        w.setPage(wEACC.getPage());
        //w.setParent(wEACC);
        //w.doOverlapped();
        w.doModal();
        //w.doEmbedded();
    }

    @Listen("onClick = #btnNuevo")
    public void lanzarWindowNuevo(final MouseEvent event) {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        final Window w = (Window) Executions.createComponents("eAIngresaCategoria.zul", null, dataArgs);
        w.setPage(wEACC.getPage());
        //w.setParent(wEACC);
        //w.doOverlapped();
        w.doModal();
        //w.doEmbedded();
    }

    private final class CategoriaTreeRenderer
        implements TreeitemRenderer<CategoriaTreeNode>
    {
        @Override
        public void render(final Treeitem treeItem,
                           final CategoriaTreeNode treeNode,
                           final int index)
            throws Exception
        {
            final CategoriaTreeNode ctn = treeNode;
            final DTO_Categoria categ = ctn.getData();
            final Treerow dataRow = new Treerow();
            dataRow.setParent(treeItem);
            treeItem.setValue(ctn);
            treeItem.setOpen(ctn.isOpen());

            final Hlayout hl = new Hlayout();
            hl.appendChild(new Image("/widgets/tree/dynamic_tree/img/" + categ.getImagen()));
            hl.appendChild(new Label(categ.getNombre()));
            hl.setSclass("h-inline-block");
            final Treecell treeCell = new Treecell();
            treeCell.appendChild(hl);
            dataRow.appendChild(treeCell);
            final Treecell treeCell2 = new Treecell();
            final Hlayout h2 = new Hlayout();
            h2.appendChild(new Label(categ.getEstado() ? Constantes.STATUS_ACTIVO : Constantes.STATUS_INACTIVO));
            treeCell2.appendChild(h2);
            dataRow.appendChild(treeCell2);
        }
    }
}
