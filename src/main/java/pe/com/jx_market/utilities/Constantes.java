package pe.com.jx_market.utilities;


/**
 * Constantes a utilizar
 *
 */
public class Constantes {

    /*
     *
     * CONSTANTES RETORNADAS POR LOS SERVICIOS: ERROR_CODES
     */
    /** Exito en la ejecucion del servicio */
    public static final int OK = 17;
    /**
     * Problema interno: no se definio valor de retorno. Avisar al
     * desarrollador.
     */
    public static final int UNDEFINED = 0;
    /** Error de autenticación */
    public static final int AUTH_ERROR = 100;
    /** Parametro suministrado es invalido o nulo */
    public static final int INVALID_PARAM = 101;
    /** No se encontro lo que se solicita */
    public static final int NOT_FOUND = 102;
    /** Error descriptivo */
    public static final int ERROR_MSG = 103;
    /** Error de codigo de respuesta de transaccion */
    public static final int ERROR_RC = 103;
    /** Error por excepcion */
    public static final int ERROR_EXCEPTION = 104;
    /** Password muy debil */
    public static final int BAD_PASS = 105;
    /** Switch desconectado */
    public static final int CONN_ERROR = 106;
    /** Registro ya inscrito */
    public static final int ALREADY_USED = 107;
    /** Error de validacion */
    public static final int VALIDATION_ERROR = 108;

    /**
     * Institucion Jx-Market
     *
     * Este valor se asume que siempre esta en la base de datos del SICF
     */
    public static final int INSTITUCION_JX_MARKET = 1;

    /*
     * Constantes para verbos
     */
    public static final String V_REGISTER = "register";
    public static final String V_REGISTERCAT4PROD = "registerCat4Prod";
    public static final String V_REGISTERPM = "registerPM";
    public static final String V_GET = "get";
    public static final String V_LIST = "list";
    public static final String V_LISTEMP = "listEmp";
    public static final String V_DELETE = "delete";
    public static final String V_DELETECAT4PROD = "deleteCat4Prod";
    public static final String V_USTOCK = "upStock";
    public static final String V_GETIMG = "getimg";
    public static final String V_UPDATE = "update";

    /*
     * Constantes para estados
     */
    public static final Integer ST_ACTIVO = 1;
    public static final Integer ST_INACTIVO = 2;
    public static final Integer ST_CANCELADO = 3;
    public static final Integer ST_PENDIENTE = 4;

    public static final Boolean STB_ACTIVO = true;
    public static final Boolean STB_INACTIVO = false;

    /*
     * Strings para estados
     */
    public static final String STATUS_ACTIVO = "Activo";
    public static final String STATUS_INACTIVO = "Inactivo";
    public static final String STATUS_CANCELADO = "Cancelado";
    public static final String STATUS_PENDIENTE = "Pendiente";

    /*
     * Temas configurables
     */

    public static final String TEMA_ROJO = "rojo";
    public static final String TEMA_PLOMO = "plomo";
    public static final String TEMA_VERDE = "verde";
    public static final String TEMA_AZUL = "azul";
    public static final String TEMA_NARANJA = "naranja";
    public static final String TEMA_LILA = "lila";
    public static final String TEMA_FUCSIA = "fucsia";
    public static final String TEMA_AMARILLO = "amarillo";
    public static final String TEMA_CELESTE = "celeste";
    public static final String TEMA_NEGRO = "negro";

    /*
     * imagenes
     */
    public static final String RUTA_IMAGENES = "/home/imagenesJXMarket";
    public static final String RUTA_IMAGENES_WINDOWS = "C:/imagenesJXMarket";

    /*
     * Modulos para accesos
     */
    public static final String MODULO_PRODUCTS = "MODULO_PRODUCTS";
    public static final String MODULO_PROD_CATEGORY = "MODULO_PROD_CATEGORY";
    public static final String MODULO_PROD_PRODUCT = "MODULO_PROD_PRODUCT";
    public static final String MODULO_PROD_AMOUNT = "MODULO_PROD_AMOUNUT";
    public static final String MODULO_PROD_INVENTORY = "MODULO_PROD_INVENTORY";

    public static final String MODULO_ADMINISTRACION = "MODULO_ADMINISTRACION";
    public static final String MODULO_ADM_PERFIL = "MODULO_ADM_PERFIL";
    public static final String MODULO_ADM_AREA = "MODULO_ADM_AREA";
    public static final String MODULO_ADM_EMPLEADO = "MODULO_ADM_EMPLEADO";
    public static final String MODULO_ADM_MODULO = "MODULO_ADM_MODULO";
    public static final String MODULO_ADM_PERFILMODULO = "MODULO_ADM_PERFILMODULO";
    public static final String MODULO_CHANGE_PASS = "MODULO_CHANGE_PASS";

    /*VALIDACION DE CARACTERES*/
    public static final String VALID_TELEFONO = "^[0-9]*$";

    /*
     * Tipos de Pedidos
     */
    public static final String TIPO_PEDIDO_ENTREGA = "Entrega a Domicilio";
    public static final String TIPO_PEDIDO_RECOGE = "Recoger Producto";

    public static final String TREE_EDITABLE_RAIZ = "Raiz";
    public static final String TREE_EDITABLE_TEXTBOX = "textBox";

    public static final String EMPTY_STRING = "";
    public static final String SEPARATOR_STRING = "..";

    public enum Form {
        PRODUCTS_FORM("eAProducts.zul"),
        PRODUCTS_EDIT_FORM("eAProductsEdit.zul"),
        PRODUCTS_CREATE_FORM("eAProductsCreate.zul");

        private final String form;

        private Form(final String _form) {
            this.form = _form;
        }

        public String getForm() {
            return this.form;
        }
    }

    public static final String ATTRIBUTE_COMPANY = "empresa";
    public static final String ATTRIBUTE_PRODUCT = "product";
}
